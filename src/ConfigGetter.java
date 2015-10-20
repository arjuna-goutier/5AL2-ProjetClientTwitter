import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class ConfigGetter {
	private final ConfigPersistence persistence;

	public ConfigGetter(ConfigPersistence persistence) {
		super();
		this.persistence = persistence;
	}

	public ConfigInfo getConfig(Twitter twitter) {
		ConfigInfo info = persistence.loadConfig();
		if (info != null)
			return info;

		RequestToken requestToken;
		try {
			requestToken = twitter.getOAuthRequestToken();
			AccessToken accessToken = null;
			while (null == accessToken) {
			    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    desktop.browse(new URI(requestToken.getAuthorizationURL()));
			    String pin = JOptionPane.showInputDialog("Veuillez entrez le code de confirmation.");
				try {
					if (pin.length() > 0) {
						accessToken = twitter
								.getOAuthAccessToken(requestToken, pin);
					} else {
						accessToken = twitter.getOAuthAccessToken();
					}
				} catch (TwitterException te) {
					if (401 == te.getStatusCode()) {
						System.out.println("Unable to get the access token.");
					} else {
						te.printStackTrace();
					}
				}
			}
			// persist to the accessToken for future reference.
			info = new ConfigInfo(accessToken.getToken(), accessToken.getTokenSecret());
			persistence.saveConfig(info);
		} catch (TwitterException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
}
