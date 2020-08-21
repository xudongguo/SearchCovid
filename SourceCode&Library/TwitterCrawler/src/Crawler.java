import java.io.BufferedReader;
import java.io.FileReader;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Crawler
{
	public Twitter twitter;
	
	
	
	public Crawler(String cKey, String cSecret, String aToken, String aSecret) throws Exception
	{
		ConfigurationBuilder cb=new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setJSONStoreEnabled(true);
		cb.setOAuthConsumerKey(cKey);
		cb.setOAuthConsumerSecret(cSecret);
		cb.setOAuthAccessToken(aToken);
		cb.setOAuthAccessTokenSecret(aSecret);
		twitter=new TwitterFactory(cb.build()).getInstance();
	}
}
