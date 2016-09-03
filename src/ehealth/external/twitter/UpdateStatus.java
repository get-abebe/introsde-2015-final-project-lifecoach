/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ehealth.external.twitter;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Uses OAuth method to acquire access to your account.<br>
 * This also have an example that illustrates how to use OAuth method with Twitter4J.<br>
 *
 * @author getch
 */
public final class UpdateStatus {
	
	Twitter twitter = new TwitterFactory().getInstance();
	
	private void loginTwitter(){
		File file = new File("twitter4j.properties");
		Properties prop = new Properties();
		InputStream is = null;
		OutputStream os = null;
		try {
			if (file.exists()) {
				is = new FileInputStream(file);
				prop.load(is);
				
			}
			
			prop.setProperty("oauth.consumerKey", "2JaAM5RaAv56zT3HxI6fpp6Nq");
			prop.setProperty("oauth.consumerSecret",
					"SRzpW5f4vk4QYeFPtJR7whQvjmM75D1JuBHTP4blTPCWu77Acl");
			os = new FileOutputStream("twitter4j.properties");
			prop.store(os, "twitter4j.properties");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignore) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException ignore) {
				}
			}
		}
		try {

			try {
				// get request token.
				// this will throw IllegalStateException if access token is
				// already available
				RequestToken requestToken = twitter.getOAuthRequestToken();
				System.out.println("Got request token.");
				System.out.println("Request token: " + requestToken.getToken());
				System.out.println("Request token secret: "
						+ requestToken.getTokenSecret());
				AccessToken accessToken = null;

				BufferedReader br = new BufferedReader(new InputStreamReader(
						System.in));
				while (null == accessToken) {
					System.out
							.println("Open the following URL and grant access to your account:");
					System.out.println(requestToken.getAuthorizationURL());
					try {
						Desktop.getDesktop().browse(
								new URI(requestToken.getAuthorizationURL()));
					} catch (UnsupportedOperationException ignore) {
					} catch (IOException ignore) {
					} catch (URISyntaxException e) {
						throw new AssertionError(e);
					}
					System.out
							.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
					String pin = br.readLine();
					try {
						if (pin.length() > 0) {
							accessToken = twitter.getOAuthAccessToken(
									requestToken, pin);
						} else {
							accessToken = twitter
									.getOAuthAccessToken(requestToken);
						}
					} catch (TwitterException te) {
						if (401 == te.getStatusCode()) {
							System.out
									.println("Unable to get the access token.");
						} else {
							te.printStackTrace();
						}
					}
				}
				System.out.println("Got access token.");
				System.out.println("Access token: " + accessToken.getToken());
				System.out.println("Access token secret: "
						+ accessToken.getTokenSecret());

				try {
					prop.setProperty("oauth.accessToken",
							accessToken.getToken());
					prop.setProperty("oauth.accessTokenSecret",
							accessToken.getTokenSecret());
					os = new FileOutputStream(file);
					prop.store(os, "twitter4j.properties");
					os.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
					System.exit(-1);
				} finally {
					if (os != null) {
						try {
							os.close();
						} catch (IOException ignore) {
						}
					}
				}
			} catch (IllegalStateException ie) {
				// access token is already available, or consumer key/secret is
				// not set.
				if (!twitter.getAuthorization().isEnabled()) {
					System.out.println("OAuth consumer key/secret is not set.");
				}
			}
			
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("Failed to read the system input.");
		} catch (Exception e) {

		}
	}

	/**
	 * Post tweets on Twitter
	 *
	 * @param tweet
	 * 
	 */
	public int postTwitterStatus(String tweet) {

		try {
			
			loginTwitter();

			Status status = twitter.updateStatus(tweet);
			System.out.println("Successfully updated the status to ["
					+ status.getText() + "].");
			return 1;
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		} catch (Exception e) {

		}
		return 0;
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out
					.println("Usage: java twitter4j.examples.tweets.UpdateStatus [text]");
			System.exit(-1);
		}
		UpdateStatus obj = new UpdateStatus();
		obj.postTwitterStatus(args[0]);
	}

}
