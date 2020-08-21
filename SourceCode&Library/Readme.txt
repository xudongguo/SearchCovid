Crawling: TwitterCrawler
Sentiment Analysis: SenticAnalyzer
Solr server: solr-8.5.0
Website: SearchCovidWebsite
xampp installer: for installing xampp


To run TwitterCrawler:
      see readme in the ./TwitterCrawler first

To run SenticAnalyzer:
      
      pip install the required library(senticnet, bs4, requests)

      make sure you have active internet
      run SenticAnalyzer.py

Launch Solr Server:
      1. open cmd
      2. cd path /solr-8.5.0/bin
      3. type command solr start -p 8983
      4. solr admin available at http://localhost:8983/solr/#/SearchCovid

Launch Apache Server:
      1. install Xampp
      2. put SearchCovidWebsite into htdocs subfolders of Xampp
      3. start apache
      4. launch website at http://localhost/searchcovid-19/