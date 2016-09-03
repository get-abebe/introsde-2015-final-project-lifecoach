/**
 * 
 */
package ehealth;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;


/**
 * @author getch
 *
 */
@ApplicationPath("healthsolutions")
public class MyApplicationConfig extends ResourceConfig{
	public MyApplicationConfig () {
        packages("ehealth");
        }
}
