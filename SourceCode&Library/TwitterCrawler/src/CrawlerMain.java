import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.spec.KeySpec;
import java.util.ArrayList;

public class CrawlerMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		ArrayList<String> keys=new ArrayList<String>();
		BufferedReader reader=new BufferedReader(new FileReader("data\\KeyFile.txt"));
		String nextline;
		//read in keys
		while ((nextline=reader.readLine())!=null)
		{   
			keys.add(nextline);
			
		}
		reader.close();
		
		String APIKey=keys.get(0);
		String APISecret=keys.get(1);
		String accToken=keys.get(2);
	    String accSecret=keys.get(3);
	    //authenticate
		TwitterCrawler newCrawler=new TwitterCrawler(APIKey, APISecret, accToken, accSecret);
		
		
		//crawl raw tweets
		System.out.println("crawling...")	;	
		newCrawler.crawlByUserID("data\\TidFile.txt","data\\rawtweets.json",4,100);
		//newCrawler.crawlTweetByUserIDFile();
        System.out.println("finished crawling")	;
    
		//process raw tweet files
        System.out.println("post processing files...")	;	
        TweetProcessor tp = new TweetProcessor();
		tp.convertTweetFile("data\\rawtweets.json", "data\\processedtweets.json");
		System.out.println("processing finished")	;	
				
	}

}
