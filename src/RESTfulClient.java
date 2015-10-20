import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class RESTfulClient {
	public static Map<Long, BufferedImage> images = new HashMap<Long, BufferedImage>();

	public static void main(String[] args) throws TwitterException, IOException {
		/*
		 * AccessToken accessToken = new AccessToken(
		 * "3928870191-KOOcz86Kesq6FkNrOBOAVMW7iWUK7vFGGUNPW9i",
		 * "ZJ5YLNeakOULPAJDzBD7fezmfm88FoKDKXzne3VvMwPA2"); TwitterFactory
		 * factory = new TwitterFactory(); Twitter twitter =
		 * factory.getInstance();
		 * twitter.setOAuthConsumer("b2v4SEzUjHmN0iIFs4WtII61g",
		 * "DMotwFJAGolBxNiMQjh6Hp93cA6PtWKuCGgtrBx4jg30EcVPGu");
		 * twitter.setOAuthAccessToken(accessToken);
		 * 
		 * 
		 * RESTfulClient client = new RESTfulClient(twitter); TwitterW twitterW
		 * = new TwitterW(client); twitterW.setVisible(true);
		 */

	}

	private Twitter twitter;

	public RESTfulClient(Twitter twitter) {
		this.twitter = twitter;
	}

	public Status updateStatus(String text) throws TwitterException {
		return twitter.updateStatus(text);
	}

	public List<Status> getUserTimeLine() throws TwitterException {
		return twitter.getUserTimeline();
	}

	public List<Status> getFriendTimeLine() throws TwitterException {
		return twitter.getHomeTimeline();
	}

	public BufferedImage getUserImageUrl() throws TwitterException,
			MalformedURLException, IllegalStateException, IOException {
		return ImageIO.read(new URL(twitter.showUser(twitter.getId())
				.getProfileImageURL()));
	}

	public BufferedImage getImage(long userId) {
		if (!images.containsKey(userId)) {
			try {
				images.put(userId, ImageIO.read(new URL(twitter
						.showUser(userId).getProfileImageURL())));
			} catch (IllegalStateException | IOException | TwitterException e) {
				return null;
			}
		}
		return images.get(userId);
	}
}
