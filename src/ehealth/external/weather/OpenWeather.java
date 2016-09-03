package ehealth.external.weather;

import java.io.IOException;
import java.net.MalformedURLException;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import org.json.JSONException;

/**
 * Lets you get the current weather information from OpenWeather server<br>
 * This also have an example that illustrates how to use and access current weather information.<br>
 *
 * @author getch
 */
public class OpenWeather {

	/**
	 * @param city
	 * @return {@link net.aksingh.owmjapis.CurrentWeather}
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws JSONException
	 */
	public CurrentWeather getWeatherByCity(String city) throws IOException,
			MalformedURLException, JSONException {

		// declaring object of "OpenWeatherMap" class
		OpenWeatherMap owm = new OpenWeatherMap("");

		// getting current weather data for the "London" city
		CurrentWeather cwd = owm.currentWeatherByCityName(city);

		// checking data retrieval was successful or not
		if (cwd.isValid()) {

			// checking if city name is available
			if (cwd.hasCityName()) {
				// printing city name from the retrieved data
				System.out.println("City: " + cwd.getCityName());
//				System.out.println(" rain:" + cwd.getRainInstance().hasRain());
//				System.out.println(" rain:" + cwd.getRainInstance().getRain());
				System.out.println(" clound: "
						+ cwd.getCloudsInstance().getPercentageOfClouds());
			}

			// checking if max. temp. and min. temp. is available
			if (cwd.getMainInstance().hasMaxTemperature()
					&& cwd.getMainInstance().hasMinTemperature()) {
				// printing the max./min. temperature
				System.out.println("Temperature: "
						+ cwd.getMainInstance().getMaxTemperature() + "/"
						+ cwd.getMainInstance().getMinTemperature() + "\'F");

			}
		}
		return cwd;
	}

	/**
	 * @param latitude
	 * @param longitude
	 * @return {@link net.aksingh.owmjapis.CurrentWeather}
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws JSONException
	 */
	public CurrentWeather getWeatherByQuardinates(float latitude,
			float longitude) throws IOException, MalformedURLException,
			JSONException {

		// declaring object of "OpenWeatherMap" class
		OpenWeatherMap owm = new OpenWeatherMap("");

		// getting current weather data for the "London" city
		CurrentWeather cwd = owm.currentWeatherByCoordinates(latitude,
				longitude);

		// checking data retrieval was successful or not
		if (cwd.isValid()) {

			// checking if city name is available
			if (cwd.hasCityName()) {
				// printing city name from the retrieved data
				System.out.println("City: " + cwd.getCityName());
			}

			// checking if max. temp. and min. temp. is available
			if (cwd.getMainInstance().hasMaxTemperature()
					&& cwd.getMainInstance().hasMinTemperature()) {
				// printing the max./min. temperature
				System.out.println("Temperature: "
						+ cwd.getMainInstance().getMaxTemperature() + "/"
						+ cwd.getMainInstance().getMinTemperature() + "\'F");
			}
		}

		return cwd;
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws JSONException
	 */
	public static void main(String[] args) throws IOException,
			MalformedURLException, JSONException {

		OpenWeather obj = new OpenWeather();
		obj.getWeatherByCity("Trento, IT");
	}
}
