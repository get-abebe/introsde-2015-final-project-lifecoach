/**
 * 
 */
package ehealth.soap.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import net.aksingh.owmjapis.CurrentWeather;
import ehealth.models.Currentlifestatus;
import ehealth.models.Goalsetting;
import ehealth.models.Measuredefinition;
import ehealth.models.Userprofile;


/**
 * @author getch
 *
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface HealthProfileSolution {
	
	/**
	 * Returns auto generated token of user who logged in
	 * 
	 * @param username
	 * @param password
	 * @return java.lang.String
	 */
	@WebMethod(operationName = "login")
	@WebResult(name = "login")
	public String login(@WebParam(name = "username") String username,
			@WebParam(name = "password") String password);

	/**
	 * Logs out user of specific username and token
	 * 
	 * @param username
	 * @return java.lang.String
	 */
	@WebMethod(operationName = "logout")
	@WebResult(name = "logout")
	public String logout(@WebParam(name = "username") String username);

	/**
	 * Returns user profile
	 * 
	 * @param username
	 * @return {@link ehealth.model.Userprofile}
	 * 
	 * @see ehealth.model.Userprofile
	 */
	@WebMethod(operationName = "getprofile")
	@WebResult(name = "profile")
	public Userprofile getProfile(@WebParam(name = "username") String username);

	/**
	 * Returns profiles of all users
	 * 
	 * @return {@link ehealth.model.Userprofile}
	 * 
	 * @see ehealth.model.Userprofile
	 */
	@WebMethod(operationName = "getusers")
	@WebResult(name = "users")
	public List<Userprofile> getAll();

	/**
	 * Saves and returns the newly created current life status
	 * 
	 * @param username
	 * @param currentlifestatus
	 * @return {@link ehealth.model.Currentlifestatus}
	 * 
	 * @see ehealth.model.Currentlifestatus
	 */
	@WebMethod(operationName = "savemeasure")
	@WebResult(name = "lifestatus")
	public Currentlifestatus addCurrentLifeStatus(
			@WebParam(name = "username") String username,
			@WebParam(name = "value") Double value,
			@WebParam(name = "measureType") String mType);

	/**
	 * Deletes the current life status
	 * 
	 * @param username
	 * @param lifestatus
	 * @return java.lang.String
	 * 
	 * @see ehealth.model.Currentlifestatus
	 */
	@WebMethod(operationName = "deletecurrentlifestatus")
	@WebResult(name = "currentlifestatus")
	public String deleteCurrentLifeStatus(
			@WebParam(name = "username") String username,
		
			@WebParam(name = "lifestatus") Currentlifestatus lifestatus);

	/**
	 * Create and returns the newly created user goal
	 * 
	 * @param goal
	 * @param username
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	@WebMethod(operationName = "setgoal")
	@WebResult(name = "goal")
	public Goalsetting setGoal(@WebParam(name = "goal") Goalsetting goal,
			@WebParam(name = "username") String username
			);

	/**
	 * Returns the information about goal whether it is completed/finished or
	 * not
	 * 
	 * @param username
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	@WebMethod(operationName = "pushreminder")
	@WebResult(name = "push")
	public Goalsetting getPush(@WebParam(name = "username") String username
			);

	/**
	 * Returns user's current/recent goal
	 * 
	 * @param username
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	@WebMethod(operationName = "getcurrentgoal")
	@WebResult(name = "goal")
	public Goalsetting getCurrentGoal(
			@WebParam(name = "username") String username);

	/**
	 * Returns all goals of a specific user
	 * 
	 * @param username
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	@WebMethod(operationName = "getallgoal")
	@WebResult(name = "goals")
	public List<Goalsetting> getAllGoal(
			@WebParam(name = "username") String username);

	/**
	 * Returns the feedback about user's goal specificed by goalId
	 * 
	 * @param username
	 * @param goalId
	 * @return java.lang.String
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	@WebMethod(operationName = "feedback")
	@WebResult(name = "feedback")
	public String persuasiveStrategy(
			@WebParam(name = "username") String username);

	/**
	 * Returns random quote
	 * 
	 * @return java.lang.String
	 * 
	 * @see ehealth.external.forismatic.ForismaticQuote
	 */
	@WebMethod(operationName = "getquote")
	@WebResult(name = "quote")
	public String getQuote();

	/**
	 * Returns the current weather of the area specified by lattitude and
	 * longitude
	 * 
	 * @param latitude
	 * @param longitude
	 * @return {@link net.aksingh.owmjapis.CurrentWeather}
	 * 
	 * @see net.aksingh.owmjapis.CurrentWeather
	 */
	@WebMethod(operationName = "getweather")
	@WebResult(name = "weatherreport")
	public CurrentWeather getWeather(
			@WebParam(name = "latitude") double latitude,
			@WebParam(name = "longitude") double longitude);

	/**
	 * Check weather a user with the username  is already logged in.
	 * 
	 * @param username
	 * @return int
	 */
	@WebMethod(operationName = "checkLoginStatus")
	@WebResult(name = "checklogin")
	public int checkLoginStatus(@WebParam(name = "username") String username);

	/**
	 * Share status on Twitter
	 * 
	 * @param username
	 * @return java.lang.String
	 */
	@WebMethod(operationName = "share")
	@WebResult(name = "share")
	public String shareOnTwitter(@WebParam(name = "username") String username);

	/**
	 * Deletes user's goal
	 * 
	 * @param username
	 * @param goalId
	 * @return java.lang.String
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	@WebMethod(operationName = "deleteUserGoal")
	@WebResult(name = "users")
	public String deleteUserGoal(@WebParam(name = "username") String username,
			@WebParam(name = "goalId") int goalId);

	/**
	 * Updates user's goal
	 * 
	 * @param goal
	 * @param username
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	@WebMethod(operationName = "updateGoal")
	@WebResult(name = "goalsettings")
	public Goalsetting updateGoal(@WebParam(name = "goal") Goalsetting goal,
			@WebParam(name = "username") String username);

	/**
	 * Finishs user's goal
	 * 
	 * @param id
	 * @param username
	 * @return {@link ehealth.model.Goalsetting}
	 * 
	 * @see ehealth.model.Goalsetting
	 */
	@WebMethod(operationName = "finishGoal")
	@WebResult(name = "goalsetting")
	public Goalsetting finishGoal(@WebParam(name = "userId") int id,
			@WebParam(name = "username") String username);

	/**
	 * Add user
	 * 
	 * @param userprofile
	 * @return int
	 */
	@WebMethod(operationName = "createuser")
	@WebResult(name = "profileId")
	public int addUser(@WebParam(name = "profile") Userprofile person);

	/**
	 * Updates user's profile
	 * 
	 * @param userproile
	 * @param username
	 * @return int
	 */
	@WebMethod(operationName = "updateprofile")
	@WebResult(name = "profileId")
	public int updateUserProfile(
			@WebParam(name = "profile") Userprofile person,
			@WebParam(name = "username") String username);

	/**
	 * Delete user's profile
	 * 
	 * @param id
	 * @return int
	 */
	@WebMethod(operationName = "deleteprofile")
	@WebResult(name = "profileId")
	public int deleteUserProfile(@WebParam(name = "userId") int id);

	/**
	 * Returns all measure definitions
	 * 
	 * @param username
	 * @return {@link ehealth.model.Measuredefinition}
	 * 
	 * @see ehealth.model.Measuredefinition
	 */
	@WebMethod(operationName = "getAllMeasureDefinition")
	@WebResult(name = "measuredefinitions")
	public List<Measuredefinition> getAllMeasureDefinition(
			@WebParam(name = "username") String username);

	/**
	 * @param username
	 * @return String Returns advice string saved in table Advisor for related
	 *         user.
	 */
	@WebMethod(operationName = "getadvice")
	@WebResult(name = "adviceString")
	public String getadvice(@WebParam(name = "username") String username);

}
