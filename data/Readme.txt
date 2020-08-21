rawtweets: tweets crawled from twitter
processedtweets: extracted useful fields from rawtweets
labeletweets.json: labelled processedtweets with polarity labels, this is the corpus for indexing in solr
stopwords: stopwords used for stopfilter in solr
synonym: synonyms used for synonymgraphfilter in solr
Queries: contains url for 5 example queries
Results1.json,Results2.json,Results3.json,Results4.json,Results5.json: Query results for queries in Queries.txt