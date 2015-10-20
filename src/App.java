import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class App {
	private static final ConfigPersistence persitence = new ConfigPersistence("user_keys.xml");
	private static final TwitterFactory factory = new TwitterFactory();
	private static final String PUBLIC_KEY = "b2v4SEzUjHmN0iIFs4WtII61g";
	private static final String PRIVATE_KEY = "DMotwFJAGolBxNiMQjh6Hp93cA6PtWKuCGgtrBx4jg30EcVPGu";
    private static final ConfigGetter configGetter = new ConfigGetter(persitence);

	public static void main(String[] args) {
		Twitter twitter = factory.getInstance();
		twitter.setOAuthConsumer(PUBLIC_KEY, PRIVATE_KEY);
		ConfigInfo info = configGetter.getConfig(twitter);
		
		twitter.setOAuthAccessToken(new AccessToken(info.getPublicKey(), info.getPrivateKey()));
		RESTfulClient client = new RESTfulClient(twitter);
		
		new TwitterW(client).setVisible(true);
	}
}
