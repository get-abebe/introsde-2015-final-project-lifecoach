/**
 * 
 */
package ehealth.soap.ws;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import net.aksingh.owmjapis.CurrentWeather;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONException;
import ehealth.external.forismatic.ForismaticQuote;
import ehealth.external.forismatic.GetForismaticQuotes;
import ehealth.external.twitter.UpdateStatus;
import ehealth.external.weather.OpenWeather;
import ehealth.models.Advisor;
import ehealth.models.Currentlifestatus;
import ehealth.models.Goalsetting;
import ehealth.models.Healthmeasurehistory;
import ehealth.models.Measuredefinition;
import ehealth.models.PersistanceFunctions;
import ehealth.models.Userprofile;

/**
 * @author getch
 *
 */
@WebService(endpointInterface = "ehealth.soap.ws.HealthProfileSolution", serviceName = "HealthProfileSolution")
public class HealthProfileSolutionImpl implements HealthProfileSolution {
	
	@Override
	public String login(String username, String password) {

		if (!username.isEmpty()) {
			try {
				Userprofile p = PersistanceFunctions
						.getUserProfileByUsername(username);
				if (p != null) {
					if (p.getPassword().equals(password)) {
						UUID uuid = UUID.randomUUID();
						String randomUUIDString = uuid.toString();
						System.out.println("Random UUID String = "
								+ randomUUIDString);

						String guidToken = PersistanceFunctions.saveGUID(
								username, randomUUIDString);

						// return
						// Response.status(200).entity(guidToken).build();
						return guidToken;
					} else {
						// return
						// Response.noContent().entity("password mismatch").build();
						return "password mismatch";
					}
				} else {
					// return
					// Response.noContent().entity("user not found").build();
					return "user not found";
				}
			} catch (Exception e) {
				e.printStackTrace();
				// return Response.serverError().build();
				return "Server Error";
			}
		} else {
			// return
			// Response.noContent().entity("please enter username").build();
			return "please enter username";
		}
	}

	/**
	 * Creates feedback in advice table of database. This function is only used
	 * to advice on weight information.
	 * 
	 * @param username
	 *            username of user
	 * @author getch
	 * 
	 */
	private void CreateFeedback(String username) {
		// TODO Auto-generated method stub
		Userprofile uP = PersistanceFunctions
				.getUserProfileByUsername(username);

		List<Healthmeasurehistory> measurehistory = PersistanceFunctions
				.getAllHistoryByUserId(uP.getUPid());

		// Collections.copy(measurehistory, uP.getHealthmeasurehistories());

		Collections.sort(measurehistory, new MeasureHistoryListComparator());

		List<Healthmeasurehistory> newlist = new ArrayList<Healthmeasurehistory>();

		for (Healthmeasurehistory mhistory : measurehistory) {
			if (mhistory.getMeasuredefinition().getMeasureName()
					.equals("weight")) {
				newlist.add(mhistory);
			}
		}

		if (newlist.size() >= 2) {
			Double h1 = newlist.get(0).getValue();
			Double h2 = newlist.get(1).getValue();
			System.out.println(h1+ newlist.size());
			System.out.println(h2);
			String advice = "";
			Double percentage = ((h2 - h1) / 100) * h2;
			Advisor advisor = new Advisor();
			if (percentage < 5) {
				advice = "Your progress is very slow.";
				advisor.setTags("bad weight progress");
			} else {
				advice = "Good work. Try to maintain nutrition.";
				advisor.setTags("good weight progress");
			}
			advisor.setAdvice(advice);
			advisor.setMeasuredefinition(newlist.get(0).getMeasuredefinition());
			advisor.setUserprofile(uP);
			PersistanceFunctions.saveAdvice(advisor);
		}
	}

	public static class MeasureHistoryListComparator implements
			Comparator<Healthmeasurehistory> {

		@Override
		public int compare(Healthmeasurehistory o1, Healthmeasurehistory o2) {
			// TODO Auto-generated method stub
			return o1.getDateTime().compareTo(o2.getDateTime());
		}

	}

	
	
	@Override
	public Userprofile getProfile(String username) {
		if (checkLoginStatus(username) == 1) {
			try {
				Userprofile p = PersistanceFunctions
						.getUserProfileByUsername(username);
				return p;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	
	@Override
	public Goalsetting setGoal(Goalsetting g, String username) {
		if (checkLoginStatus(username)  == 1) {
			//System.out.println("test: ");
			try {
				Userprofile uP = PersistanceFunctions
						.getUserProfileByUsername(username);
			//	System.out.println("test: ");
				g.setUserprofile(uP);
				
				Goalsetting goal = PersistanceFunctions.saveGoal(g);
				return goal;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//System.out.println("null: ");
		return null;
	}

	
	@Override
	public Goalsetting getPush(String username) {
		// check user login status
		if (checkLoginStatus(username) == 1) {
			try {
				// getting user profile
				Userprofile p = getProfile(username);

				// getting goals list
				List<Goalsetting> list = PersistanceFunctions
						.getUserGoalsByUserId(p.getUPid());
				for (int i = 0; i < list.size(); i++) {
					Goalsetting goal = list.get(i);
				//	System.out.println(goal);
					DateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					String systemDateTime = dateFormat.format(date.getTime());
				//	System.out.println(systemDateTime);

					Date goalDate = goal.getScheduleDateTime();
					String gDate = dateFormat.format(goalDate);
				//    System.out.println(gDate);

					if (date.after(goalDate)) {
						
						long diff = date.getTime() - goalDate.getTime();
						long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
				//		 System.out.println(minutes);
						if (goal.getWorkProgress().equals("0")) {
							if (minutes > 5 && minutes > 0) {
								goal.setWorkProgress("1");
								PersistanceFunctions.updateGoal(goal);
								return goal;
							}
						} else {
							return null;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	
	@Override
	public Goalsetting getCurrentGoal(String username) {
		if (checkLoginStatus(username) == 1) {
			Userprofile p = getProfile(username);
			List<Goalsetting> goal = PersistanceFunctions
					.getUserGoalsByUserId(p.getUPid());
			return goal.get(0);
		}
		return null;
	}

	@Override
	public List<Goalsetting> getAllGoal(String username) {
		if (checkLoginStatus(username) == 1) {
			Userprofile p = getProfile(username);
			List<Goalsetting> list = PersistanceFunctions
					.getUserGoalsByUserId(p.getUPid());
			return list;
		}
		return null;
	}

	
	@Override
	public String getQuote() {
		GetForismaticQuotes obj = new GetForismaticQuotes();
		ForismaticQuote quote = obj.get();
		if (quote != null) {
			return quote.quoteText;
		}
		return null;
	}
	

	@Override
	public String persuasiveStrategy(String username) {
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target(UriBuilder.fromUri(
				"http://localhost:8001/healthsolutions/").build());

		System.out.println("==========Step 1: Checking login ==========" );

		WebTarget path = target.path("/rest/checklogin")
				.queryParam("username", username);
	//	System.out.println("==========Step 1: Checking login t==========" + path);
		Builder req = path.request().accept(MediaType.APPLICATION_JSON);
	//	System.out.println("==========Step 1: Checking login t==========" + req);
		Response res = req.get(Response.class);
        int response = res.getStatus();
     //   System.out.println(response);
		String responseString = res.readEntity(String.class);
		if (res.getStatus() == 200) {
			System.out.println(responseString);
			if (Integer.parseInt(responseString) == 1) {
				System.out
						.println("==========Step 2: Getting user profile==========");

				WebTarget pathProfile = target.path("/rest/profile")
						.queryParam("username", username);

				Builder reqProfile = pathProfile.request().accept(
						MediaType.APPLICATION_JSON);

				Response resProfile = reqProfile.get(Response.class);

				Userprofile userProfile = null;
				if ((resProfile.getStatus() == 200)) {
					userProfile = resProfile.readEntity(Userprofile.class);
					System.out.println(userProfile);

					System.out
							.println("==========Step 3: Getting current goal==========");
					WebTarget pathGoal = target.path("/rest/goal").queryParam(
							"userid", userProfile.getUPid());

					Builder reqGoal = pathGoal.request().accept(
							MediaType.APPLICATION_JSON);

					Response resGoal = reqGoal.get(Response.class);

					if (resGoal.getStatus() == 200) {
						Goalsetting goal = resGoal
								.readEntity(Goalsetting.class);
						System.out.println(goal);

						System.out
								.println("==========Step 4: Calculating progress and feedback==========");
						WebTarget pathFeedback = target.path("/rest/feedback")
								.queryParam("service", goal.getService())
								.queryParam("work", goal.getWorkProgress());
						System.out.println(goal.getService() + goal.getWorkProgress() );
						Builder reqFeedback = pathFeedback.request().accept(
								MediaType.APPLICATION_JSON);

						Response resFeedback = reqFeedback.get(Response.class);

						if (resFeedback.getStatus() == 200) {
							String feedback = resFeedback
									.readEntity(String.class);
							System.out.println(feedback);
							String goals = goal.getService();
							System.out.println(goals);
							if (goal.getService().equalsIgnoreCase("walk")
									|| goal.getService().equalsIgnoreCase(
											"cycling")) {
								System.out
										.println("==========Step 5: Adding weather forecasting==========");
								WebTarget pathWeather = target
										.path("/rest/weather")
										.queryParam("service",
												goal.getService())
										.queryParam("work",
												goal.getWorkProgress())
										.queryParam("city", "Trento, IT");

								Builder reqWeather = pathWeather.request()
										.accept(MediaType.APPLICATION_JSON);

								Response resWeahter = reqWeather
										.get(Response.class);
								if (resWeahter.getStatus() == 200) {
									String feedbackComplete = resWeahter
											.readEntity(String.class);
									System.out.println(feedbackComplete+ "test");

									return feedbackComplete;
								}
							}
							return feedback;
						}
					} else {
						return " user have no goal";
					}
				}
			} else {
				return "login first";
			}
		}
		return null;
	}

	
	@Override
	public CurrentWeather getWeather(double latitude, double longitude) {
		OpenWeather obj = new OpenWeather();
		CurrentWeather cwd = null;
		try {
			cwd = obj.getWeatherByQuardinates((float) latitude,
					(float) longitude);
			return cwd;
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return cwd;
	}

	
	@Override
	public int checkLoginStatus(String username) {
		if (!username.isEmpty()) {
			Userprofile p = PersistanceFunctions
					.getUserProfileByUsername(username);
			if (!p.getGuid().isEmpty()) {
				return 1;
			}
		}
		return 0;
	}


	@Override
	public String deleteCurrentLifeStatus(String username, 
			Currentlifestatus lifestatus) {
		if (checkLoginStatus(username) == 1) {
			PersistanceFunctions.removeCurrentLifeStatus(lifestatus);
		}
		return null;
	}

	
	@Override
	public Currentlifestatus addCurrentLifeStatus(String username,
			 Double value, String mType) {
		/*// Check if user already login or not
		if (checkLoginStatus(username) == 1) {

			// Get Userprofile to get to use it later
			Userprofile uP = PersistanceFunctions
					.getUserProfileByUsername(username);

			Healthmeasurehistory hmh = new Healthmeasurehistory();
			
			Currentlifestatus currentlifestatus = new Currentlifestatus();
		//	List<Currentlifestatus lifestatus1 = uP.getCurrentlifestatuses();

			for (Currentlifestatus lifestatus : uP.getCurrentlifestatuses()) {
				if (lifestatus
						.getMeasuredefinition()
						.getMeasureName()
						.equals(mType)) {
					
					currentlifestatus = lifestatus;
				
					
				}
			}
			    Date date = currentlifestatus.getDateTime();
			    DateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date dates = new Date();
				String systemDateTime = dateFormat.format(dates.getTime());
				//System.out.println(systemDateTime);
			   System.out.println("The current life status date is:"  + date);
			    
			   
			    
				hmh.setDateTime(date);
				hmh.setMeasuredefinition(currentlifestatus.getMeasuredefinition());
				hmh.setUserprofile(uP);
				hmh.setValue(currentlifestatus.getValue());
				
			
				PersistanceFunctions.removeCurrentLifeStatus(currentlifestatus);
				PersistanceFunctions.saveHealthMeasureHistory(hmh);
			

			currentlifestatus.setMeasuredefinition(currentlifestatus.getMeasuredefinition());
			currentlifestatus.setUserprofile(uP);
			currentlifestatus.setValue(value);
			currentlifestatus.setDateTime(new Date());
			//currentlifestatus.setMeasuredefinition();
			

			// Saving current life status
			Currentlifestatus current = PersistanceFunctions
					.saveCurrentLifeStatus(currentlifestatus);
			System.out.println("==========currentlifestatus==========");

			return current;
		}
		return null;*/
		
		
		// Check if user already login or not
				if (checkLoginStatus(username) == 1) {

					// Get Userprofile to get to use it later
					Userprofile uP = PersistanceFunctions
							.getUserProfileByUsername(username);

					Healthmeasurehistory hmh = new Healthmeasurehistory();
					
					Currentlifestatus currentlifestatus = new Currentlifestatus();

					for (Currentlifestatus lifestatus : uP.getCurrentlifestatuses()) {
						if (lifestatus
								.getMeasuredefinition()
								.getMeasureName()
								.equals(mType)) {
							hmh.setDateTime(lifestatus.getDateTime());
							Date date = lifestatus.getDateTime();
							System.out.println("The current life status date is:"  + date);
							hmh.setMeasuredefinition(lifestatus.getMeasuredefinition());
							hmh.setUserprofile(uP);
							hmh.setValue(lifestatus.getValue());
							PersistanceFunctions.removeCurrentLifeStatus(lifestatus);
							PersistanceFunctions.saveHealthMeasureHistory(hmh);
							currentlifestatus.setMeasuredefinition(lifestatus.getMeasuredefinition());
						}
						System.out.println("The current life status date is:");
					}

					currentlifestatus.setUserprofile(uP);
					currentlifestatus.setValue(value);
					currentlifestatus.setDateTime(new Date());
					currentlifestatus.setMeasuredefinition(currentlifestatus.getMeasuredefinition());//setDateTime(new Date());

					// Saving current life status
					Currentlifestatus current = PersistanceFunctions
							.saveCurrentLifeStatus(currentlifestatus);

					return current;
				}
				return null;
	}

	

	@Override
	public List<Userprofile> getAll() {
		System.out.println("Getting list of people...");
		List<Userprofile> people = PersistanceFunctions.getAllUserprofile();
		return people;
	}

	
	@Override
	public String deleteUserGoal(String username, int goalId) {
		if (checkLoginStatus(username) == 1) {
			Goalsetting goal = PersistanceFunctions.getUserGoalById(goalId);
			if (goal != null) {
				try {
					PersistanceFunctions.removeGoal(goal);
					return "User goal removed";
				} catch (Exception e) {
					return "Server Error";
				}
			} else {
				return "User goal not found";
			}
		}
		return "Please login first";
	}

	
	@Override
	public Goalsetting updateGoal(Goalsetting goal, String username) {
		if (checkLoginStatus(username) == 1) {
			Goalsetting g = PersistanceFunctions.updateGoal(goal);
			return g;
		}
		return null;
	}

	@Override
	public Goalsetting finishGoal(int goalId, String username) {
		if (checkLoginStatus(username) == 1) {
			Goalsetting goal = PersistanceFunctions.getUserGoalById(goalId);
			Goalsetting g = PersistanceFunctions.updateGoal(goal);
			return g;
		}
		return null;
	}

	
	@Override
	public int addUser(Userprofile user) {
		PersistanceFunctions.savePerson(user);
		return user.getUPid();
	}

	@Override
	public int updateUserProfile(Userprofile person, String username) {
		if (checkLoginStatus(username) == 1) {
			PersistanceFunctions.updatePerson(person);
			return person.getUPid();
		}
		return 0;
	}

	@Override
	public int deleteUserProfile(int id) {
		PersistanceFunctions.removePerson(PersistanceFunctions
				.getUserProfileById(id));
		return 0;
	}

	
	@Override
	public List<Measuredefinition> getAllMeasureDefinition(String username) {
		if (checkLoginStatus(username) == 1) {
			List<Measuredefinition> mdef = PersistanceFunctions
					.getAllMeasureDefinition();
			return mdef;
		}
		return null;
	}

	@Override
	public String getadvice(String username) {
		// TODO Auto-generated method stub
		if (checkLoginStatus(username) == 1) {
			CreateFeedback(username);

			Userprofile uP = PersistanceFunctions
					.getUserProfileByUsername(username);
			String Message = uP.getAdvisors().get(0).getAdvice();
			PersistanceFunctions.removeAdvice(uP.getAdvisors().get(0));
			return Message;
		}
		return null;
	}
	
	
	@Override
	public String shareOnTwitter(String username) {
		
		

		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target(UriBuilder.fromUri(
				"http://localhost:8001/healthsolutions/").build());

		System.out.println("==========Step 1: Checking login==========");

		
		
		WebTarget path = target.path("/rest/checklogin")
				.queryParam("username", username);

		Builder req = path.request().accept(MediaType.APPLICATION_JSON);

		Response res = req.get(Response.class);

		String responseString = res.readEntity(String.class);
		
		
		if (res.getStatus() == 200) {
			System.out.println(responseString);
			if (Integer.parseInt(responseString) == 1) {

				System.out
						.println("==========Step 2: Getting user profile==========");

				WebTarget pathProfile = target.path("/rest/profile")
						.queryParam("username", username);

				Builder reqProfile = pathProfile.request().accept(
						MediaType.APPLICATION_JSON);

				Response resProfile = reqProfile.get(Response.class);

				Userprofile userProfile = null;
				if ((resProfile.getStatus() == 200)) {
					userProfile = resProfile.readEntity(Userprofile.class);
					System.out.println(userProfile);

					System.out
							.println("==========Step 3: Getting Current Life Statuses ==========");
					WebTarget pathCurrentLifeStatus = target
							.path("/rest/lifestatuses")
							.queryParam("username", username)
							
							.queryParam("userid", userProfile.getUPid());

					// Builder reqLifeStatuses =
					// pathCurrentLifeStatus.request().accept(MediaType.APPLICATION_JSON);

					// Response resLifeStatuses =
					// reqLifeStatuses.get(Response.class);

					Response resLifeStatuses = pathCurrentLifeStatus.request()
							.accept(MediaType.APPLICATION_JSON)
							.get(Response.class);

					if (resLifeStatuses.getStatus() == 200) {
						// Currentlifestatus currentLifeStatus =
						// resLifeStatuses.readEntity(Currentlifestatus.class);
						List<Currentlifestatus> list = (List<Currentlifestatus>) resLifeStatuses
								.readEntity(new GenericType<List<Currentlifestatus>>() {
								});

						String shareString = "My current life status --> ";
						for (Currentlifestatus lifeStatus : list) {
							shareString += lifeStatus.getMeasuredefinition()
									.getMeasureName();
							shareString += ": ";
							shareString += lifeStatus.getValue();
							shareString += ", ";
						}
						System.out.println(shareString);

						// posting current goal status as tweet on twitter
						System.out
								.println("==========Step 4: Posting on twitter==========");
						UpdateStatus obj = new UpdateStatus();
						int status = obj.postTwitterStatus(shareString);

						if (status == 1) {
							return "Successfully shared.";
						} else {
							return "Status not shared. Status might be duplicate. For more information, please see the server logs.";
						}
                   // return shareString;
					} else {
						return "no goal to share";
					}
				}
			} else {
				return "login first";
			}
		}

		return null;
	}

	
	@Override
	public String logout(String username) {
		if (checkLoginStatus(username) == 1) {
			try {
				Userprofile p = PersistanceFunctions
						.getUserProfileByUsername(username);
				if (p != null) {
					p.setGuid(null);
					PersistanceFunctions.updatePerson(p);
					System.out.println("user logout now");
					return "You are logged out now.";
				} else {
					return "user not found";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "Server Error";
			}
		}
		return null;
	}
}
