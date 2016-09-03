package ehealth.external.forismatic;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.json.simple.*;

/**
 * Communicate with ForismaticQuote server and gets the random quote as
 * JSONObject and converts this web object into ForismaticQuote java
 * object class and returns
 * 
 * @author getch
 *
 */

public class GetForismaticQuotes {

	public GetForismaticQuotes() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the random quote from ForismaticQuotes server
	 * 
	 * @param quoteText
	 * @param quoteAuther
	 * @param quoteLink
	 * 
	 * @return
	 * 
	 * @see ehealth.external.forismatic.ForismaticQuote
	 */
	public ForismaticQuote get() {

		Client client = ClientBuilder.newClient();

		WebTarget resource = client
				.target("http://api.forismatic.com/api/1.0/")
				.queryParam("method", "getQuote").queryParam("lang", "en")
				.queryParam("format", "json");

		// resource.queryParam("method", "getQuote");
		// resource.queryParam("format", "json");
		// resource.queryParam("key", "");
		// resource.queryParam("lang", "en");

		Builder req = resource.request().accept(MediaType.APPLICATION_JSON);

		Response response = req.post(null);

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			System.out.println("Success! " + response.getStatus());

			try {
				String jsonRes = response.readEntity(String.class);
				System.out.println("Response: " + jsonRes.toString());

				Object jsonObj = JSONValue.parse(jsonRes);

				JSONObject rootObj = (JSONObject) jsonObj;

				ForismaticQuote quoteObj = new ForismaticQuote();

				quoteObj.quoteText = rootObj.get("quoteText").toString();
				quoteObj.quoteAuther = rootObj.get("quoteAuthor").toString();
				quoteObj.quoteLink = rootObj.get("quoteLink").toString();

				System.out.println("Quote: " + quoteObj.quoteText);

				return quoteObj;

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("ERROR! " + response.getStatus());
			System.out.println(response.toString());
		}
		return null;
	}

	public static void main(String args[]) {
		GetForismaticQuotes obj = new GetForismaticQuotes();
		obj.get();
	}

}
