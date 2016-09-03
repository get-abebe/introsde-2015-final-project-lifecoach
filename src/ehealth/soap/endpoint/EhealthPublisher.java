/**
 * 
 */
package ehealth.soap.endpoint;
import javax.xml.ws.Endpoint;

import ehealth.soap.ws.HealthProfileSolutionImpl;

/**
 * <p>
 * Endpoint class generates and starts the HealthProfileSolution SOAP server and allows
 * client to communicate with it.
 * <p>
 * 
 * @author getch
 *
 */
public class EhealthPublisher {

	public static String SERVER_URL = "http://localhost";
	public static String PORT = "8000";
	public static String BASE_URL = "/healthsolutions";

	public static String getEndpointURL() {
		return SERVER_URL + ":" + PORT + BASE_URL;
	}

	public static void main(String[] args) {
		String endpointUrl = getEndpointURL();
		System.out.println("Starting lifecoach Service...");
		System.out.println("--> Published at = " + endpointUrl);
		Endpoint.publish(endpointUrl, new HealthProfileSolutionImpl());
	}

}
