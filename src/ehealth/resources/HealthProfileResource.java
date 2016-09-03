/**
 * 
 */
package ehealth.resources;

import java.io.IOException;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.aksingh.owmjapis.CurrentWeather;
import org.json.JSONException;
import ehealth.external.weather.OpenWeather;
import ehealth.models.Goalsetting;
import ehealth.models.PersistanceFunctions;
import ehealth.models.Userprofile;

/**
 * @author getch
 *
 */
@Stateless
@LocalBean
@Path("/rest")
public class HealthProfileResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	/**
	 * Returns user's profile
     * 
	 * @param username
	 * @return 
	 * @see ehealth.model.Userprofile
     */
	
	
	@GET
	@Path("profile")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Userprofile getProfile(@QueryParam("username") String username) {
		Userprofile p = PersistanceFunctions.getUserProfileByUsername(username);
		return p;
	}
	
	/**
	 * Returns user's goals
	 * 
	 * @param userId
	 * @return
	 * @see ehealth.model.Goalsetting
	 */
	@GET
	@Path("goal")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Goalsetting getCurrentGoal(@QueryParam("userid") int userId) {
		List<Goalsetting> goal = PersistanceFunctions
				.getUserGoalsByUserId(userId);
		return goal.get(0);
	}

	/**
	 * Check weather a user with the username and token is already logged in. And use userId to get the user's current life status
	 * 
	 * @param username
	 * @param userId
	 * @return
	 * 
	 * @see ehealth.model.Currentlifestatus
	 */
	@GET
	@Path("lifestatuses")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getLifeStatuses(
			@QueryParam("username") String username, @QueryParam("userid") int userId) {
		if (checkLoginStatus(username) == 1) {
			try {
				Userprofile uP = PersistanceFunctions
						.getUserProfileByUsername(username);
				return Response.status(200).entity(uP.getCurrentlifestatuses())
						.build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Check weather a user with the username and token is already logged in.
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("checklogin")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	//@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int checkLoginStatus(@QueryParam("username") String username) {
		if (!username.isEmpty()) {
			Userprofile p = PersistanceFunctions
					.getUserProfileByUsername(username);
			if (!p.getGuid().isEmpty()) {
				return 1;
			}
		}
	//System.out.println("==========Step 1: Checking login Test==========");
		return 0;
	}

	/**
	 * Generate feedback about weather on the basis of service and work progress  
	 * 
	 * @param feedback
	 * @param city
	 * @param service
	 * @return
	 */
	@GET
	@Path("weather")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getWeather(@QueryParam("service") String service,
			@QueryParam("feedback") String feedback,
			@QueryParam("city") String city) {
		OpenWeather obj = new OpenWeather();
		CurrentWeather cwd = null;

		try {
			cwd = obj.getWeatherByCity(city);
			if (cwd.getCloudsInstance().getPercentageOfClouds() < 50) {
				if (service.equalsIgnoreCase("cycling")) {
					feedback.concat("Tremperature in " + city + " is "
							+ cwd.getMainInstance().getMaxTemperature() + "/"
							+ cwd.getMainInstance().getMinTemperature()
							+ " and it is suitable for cycling.");
				} else if (service.equalsIgnoreCase("walk")) {
					if (cwd.getMainInstance().getMinTemperature() <= 10) {
						feedback.concat("Tremperature in "
								+ city
								+ " is "
								+ cwd.getMainInstance().getMaxTemperature()
								+ "/"
								+ cwd.getMainInstance().getMinTemperature()
								+ " and it is suitable for walk. Wear warm clothes.");
					} else {
						feedback.concat("Tremperature in " + city + " is "
								+ cwd.getMainInstance().getMaxTemperature()
								+ "/"
								+ cwd.getMainInstance().getMinTemperature()
								+ " and it is suitable for walk.");
					}
				}
			}
			return "";
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Generate feedback result on the basis of service and work progress
	 * 
	 * @param service
	 * @param feedback
	 * @return
	 */
	@GET
	@Path("feedback")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getFeedback(@QueryParam("service") String service,
			@QueryParam("work") String workProgress) {

		String response = "";
		if (service.equalsIgnoreCase("cycling")) {
			if (Integer.parseInt(workProgress) <= 25) {
				response = "You need to hard work.";
			} else if (Integer.parseInt(workProgress) > 25
					&& Integer.parseInt(workProgress) <= 50) {
				response = "You need to hard work.";
			} else if (Integer.parseInt(workProgress) > 50
					&& Integer.parseInt(workProgress) <= 75) {
				response = "You are doing well. You have completed your goal more than 50%. You need some more cycling to make it complete.";
			} else if (Integer.parseInt(workProgress) > 75) {
				response = "Happy, your progress is going well. Cycling keeps you always fit.";
			}
		} else if (service.equalsIgnoreCase("walk")) {
			if (Integer.parseInt(workProgress) <= 25) {
				response = "You need to hard work.";
			} else if (Integer.parseInt(workProgress) > 25
					&& Integer.parseInt(workProgress) <= 50) {
				response = "You need to hard work.";
			} else if (Integer.parseInt(workProgress) > 50
					&& Integer.parseInt(workProgress) <= 75) {
				response = "Your progress is good. You have completed your goal more than 50%. Keep it up.";
			} else if (Integer.parseInt(workProgress) > 75) {
				response = "Happy, your progress is going well Walk always keep your healthy, fit and active.";
			}
		} else if (service.equalsIgnoreCase("doctorPrescription")) {
			if (Integer.parseInt(workProgress) <= 25) {
				return "Your progress is very low. Keep your diet according to doctors advice to stay healthy, fit and active";
			} else if (Integer.parseInt(workProgress) > 25
					&& Integer.parseInt(workProgress) <= 50) {
				return "You progress is not good. Keep your diet according to doctors advice to stay healthy, fit and active";
			} else if (Integer.parseInt(workProgress) > 50
					&& Integer.parseInt(workProgress) <= 75) {
				return "Your progress is ok but you have to keep it according to doctors advice to stay healthy, fit and active";
			} else if (Integer.parseInt(workProgress) > 75) {
				return "You are going well. Some more concentration is needed to complete it.";
			}
		} else {
			return "To keep the body in good health is a duty... otherwise we shall not be able to keep our mind strong and clear.";
		}

		return response;

	}

}
