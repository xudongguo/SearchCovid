	import java.io.FileNotFoundException;
	import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
	import org.json.simple.JSONObject;
	import org.json.simple.parser.JSONParser;
	import org.json.simple.parser.ParseException;

	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileWriter;

	import twitter4j.Status;
	import twitter4j.TwitterObjectFactory;
import twitter4j.User;

public class TweetProcessor {

		public void convertTweetFile(String JsonInput, String JsonOutput) throws Exception
		{
			BufferedReader br=new BufferedReader(new FileReader(JsonInput));
			BufferedWriter bw=new BufferedWriter(new FileWriter(JsonOutput));
	        JSONArray tweetList = new JSONArray();
			String nextline;
			Status status;
			while ((nextline=br.readLine())!=null)
			{
				status=TwitterObjectFactory.createStatus(nextline);
				
				//format raw tweet
		        JSONObject newTweet = new JSONObject();
		        User tweetUser=status.getUser();
		        JSONObject user = new JSONObject();
		        JSONObject entities = new JSONObject();
		        JSONObject mediaentities=new JSONObject();
		        
		        newTweet.put("id",status.getId());
		        newTweet.put("full_text",status.getText());
		        newTweet.put("display_text_range",status.getDisplayTextRangeEnd()-status.getDisplayTextRangeStart());
		        newTweet.put("source",status.getSource());
		        newTweet.put("geolocation",status.getGeoLocation());
		        newTweet.put("place",status.getPlace());
		        newTweet.put("favorite_count",status.getFavoriteCount());
		        newTweet.put("retweeted_count",status.getRetweetCount());
		        newTweet.put("possibly_sensitive",status.isPossiblySensitive());
		        //format created_at attribute to fit solr
		        String createdAt = status.getCreatedAt().toString();
		        System.out.println(createdAt);
		        ZonedDateTime createdDate = ZonedDateTime.parse(createdAt, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy"));
		        createdDate.format(DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()));
		        String strcreatedDate=createdDate.toString().substring(0, 19)+"Z";
		        if(strcreatedDate.charAt(16)=='+' ) {
		        	strcreatedDate=strcreatedDate.substring(0,16)+":00Z";
		        }
		        	
		        	
		        System.out.println(strcreatedDate.toString());
		        newTweet.put("created_at", strcreatedDate);
		        
		        user.put("user_id", tweetUser.getId());
		        user.put("name", tweetUser.getName());
		        user.put("screen_name", tweetUser.getScreenName());
		        user.put("user_location", tweetUser.getLocation());
		        user.put("user_url", tweetUser.getURL());
		        user.put("user_description", tweetUser.getDescription());
		        user.put("profile_url", tweetUser.getOriginalProfileImageURL());
		        user.put("mini_profile_url", tweetUser.getMiniProfileImageURL());
		        user.put("bigger_profile_url", tweetUser.getBiggerProfileImageURL());
		        user.put("400*400_profile_url", tweetUser.get400x400ProfileImageURL());
		        user.put("followers_count", tweetUser.getFollowersCount());
		        user.put("favorites_count", tweetUser.getFavouritesCount());
		        
		        
		        
		        JSONArray hashtags= new JSONArray();
		        JSONArray symbols= new JSONArray();
		        JSONArray urls= new JSONArray();
		        JSONArray mediatype = new JSONArray();
		        JSONArray medialink = new JSONArray();
		        for (int i=0; i<status.getHashtagEntities().length; i++) {
		        	hashtags.add(status.getHashtagEntities()[i].getText());
		        }
		        for (int i=0; i<status.getSymbolEntities().length; i++) {
		        	symbols.add(status.getSymbolEntities()[i].getText());
		        }
		        for (int i=0; i<status.getURLEntities().length; i++) {
		        	urls.add(status.getURLEntities()[i].getText());
		        }
		
		        entities.put("hashtags", hashtags);
		        entities.put("symbols", symbols);
		        entities.put("urls", urls);
		        
		        newTweet.put("entities", entities);
		        newTweet.put("user", user);
		        
		        //Add newtweet to list

		        //tweetList.add(newTweet);
		         
		        //Write JSON file
		        try{
					 
		            bw.write(newTweet.toJSONString());
		            bw.write('\n');
		            
		 
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
			
			br.close();
			bw.close();
		}
		
			
			
		}


