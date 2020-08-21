
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

public class TwitterCrawler extends Crawler
{
	
	public void crawlByUserID(String TidFile, String OutputtweetFile, int numPages, int numTweets) throws Exception
	{
		ArrayList<Long> Tids=new ArrayList<Long>();
		BufferedReader br=new BufferedReader(new FileReader(TidFile));
		String nextline;
		while ((nextline=br.readLine())!=null)
		{
			Tids.add(Long.valueOf(nextline));
		}
		br.close();
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(OutputtweetFile));
		for (int i=0; i<Tids.size(); i++)
		{
			for (int page=1; page<=numPages; page++)
			{
				try
				{
					ResponseList<Status> response=twitter.getUserTimeline(Tids.get(i), new Paging(page, numTweets));
					for (Status status : response)
					{
						bw.write(TwitterObjectFactory.getRawJSON(status));
						bw.newLine();
					}
					bw.flush();
					System.out.println(TidFile+"\t"+(i+1)+"/"+Tids.size()+"\tPage "+page+"/"+numPages+"\t"+response.size());
				}
				catch (TwitterException te)
				{
					System.out.println(TidFile+"\t"+(i+1)+"/"+Tids.size()+"\tPage "+page+"/"+numPages+"\tFailed");
				}
				Thread.sleep(5300);
			}
		}
		bw.close();
	}
	public void crawlByTweetID(String TidFile, String OutputtweetFile) throws Exception
	{
		String nextline;
		ArrayList<String> Tids=new ArrayList<String>();
		BufferedReader br=new BufferedReader(new FileReader(TidFile));
		while ((nextline=br.readLine())!=null)
		{
			Tids.add(nextline);
		}
		br.close();
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(OutputtweetFile));
		nextline="";
		String seg[];
		long crawlTIDs[];
		for (int i=0; i<Tids.size(); i++)
		{
			if (nextline.length()==0)
			{
				nextline+=Tids.get(i);
			}
			else
			{
				nextline+=","+Tids.get(i);
			}
			
			if ((i+1)%100==0 || i==Tids.size()-1)
			{
				seg=nextline.split(",");
				nextline="";
				crawlTIDs=new long[seg.length];
				for (int j=0; j<seg.length; j++)
				{
					crawlTIDs[j]=Long.valueOf(seg[j]);
				}
				
				try
				{
					ResponseList<Status> response=twitter.lookup(crawlTIDs);
					for (Status status : response)
					{
						bw.write(TwitterObjectFactory.getRawJSON(status));
						bw.newLine();
					}
					bw.flush();
					System.out.println(TidFile+"\t"+(i+1)+"/"+Tids.size()+"\t"+response.size()+"/"+seg.length);
				}
				catch (TwitterException e)
				{
					System.out.println(TidFile+"\t"+(i+1)+"/"+Tids.size()+"\tFailed");
				}
				
				Thread.sleep(5300);
			}
		}
		bw.close();
		
	}
		public TwitterCrawler(String cKey, String cSecret, String aToken, String aSecret) throws Exception
		{
			super(cKey, cSecret, aToken, aSecret);
		}
	}
	