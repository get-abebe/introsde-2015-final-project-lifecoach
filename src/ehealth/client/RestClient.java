/**
 * 
 */
package ehealth.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import ehealth.models.Goalsetting;
import ehealth.models.Userprofile;

/**
 * @author getch
 *
 */
public class RestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(UriBuilder.fromUri(
				"http://localhost:8001/healthsolutions/").build());

		System.out.println("==========Step 1: Checking login==========");

		WebTarget path = target.path("/rest/checklogin")
				.queryParam("username", "getch1");

		Builder req = path.request().accept(MediaType.APPLICATION_JSON);
	//	System.out.println("==========Step 1: Checking login t==========" + req);
		

		Response res = req.get(Response.class);
		/*System.out.println("==========Step 1: Checking login t==========" + res);
		int i = res.getStatus();
		System.out.println(i);
*/
		
		String responseString = res.readEntity(String.class);
		if ((res.getStatus() == 200 || res.getStatus() == 201 || res
				.getStatus() == 202)) {
			// if (d != 0) {
			System.out.println(responseString);
			// }
		}
		//else
			//System.out.println("noo");

		WebTarget pathProfile = target.path("/rest/profile")
				.queryParam("username", "getch1");

		Builder reqProfile = pathProfile.request().accept(
				MediaType.APPLICATION_JSON);

		Response resProfile = reqProfile.get(Response.class);

		Userprofile userProfile = null;
		if ((resProfile.getStatus() == 200)) {
			userProfile = resProfile.readEntity(Userprofile.class);
			System.out.println(userProfile);

			WebTarget pathGoal = target
					.path("/rest/goal")
					.queryParam("username", "getch1").queryParam("userid", userProfile.getUPid());

			Builder reqGoal = pathGoal.request().accept(
					MediaType.APPLICATION_JSON);

			Response resGoal = reqGoal.get(Response.class);

			if (resGoal.getStatus() == 200) {
				Goalsetting goal = resGoal.readEntity(Goalsetting.class);
				System.out.println(goal);
			}
		}
		
		

	}
	
	/*private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8001/rest/healthsolutions/").build();
		//return UriBuilder.fromUri("https://sheltered-river-7549.herokuapp.com/sdelab").build()
	}*/

}
