
from senticnet.senticnet import SenticNet
from WebScrapper import*
import json
import re
sn = SenticNet()

'''reference https://ww.sentic.net/hourglass-of-emotions.pdf'''

'''emotion labeling of sentence by second level Hourglass model'''
def emotionAnalyzer(sentensentics):

    #counter for five emotion
    optimistic=regretful=promising=worrying=frustrated=0
    for wordsentics in sentensentics:

        for k,v in wordsentics:
            pl=at=sen=apt=False
            if k=='pleasantness':
                if v>0:
                    pl=True
            elif k=='attention':
                if v>0:
                    at=True
            elif k=='sensitivity':
                if v>0:
                    sen=True
            elif k=='aptitude':
                if v>0:
                    apt=True

            if pl and at:
                optimistic+=1
            if (not pl) and at:
                frustrated+=1
            if  at and (not sen):
                worrying+=1
            if  pl and apt:
                promising+=1
            if  (not pl) and (not apt):
                regretful+=1
    emos={'optimistic':optimistic, 'frustrated':frustrated,
      'promising': promising, 'worrying': worrying, 'regretful':regretful}
    return (max(emos))


#return lists of emotion labels
def sentenceLabler(sentences):

    #results
    emolist=list()

    for sentence in sentences:
        #sentence senticlists
        senticlist=list()

        for concepts in sentence:
            # concept_info=sn.concept(concepts)
            '''get polarity'''
            try:
                '''get emotion'''
                sentics=sn.sentics(concepts)
                senticlist.append(sentics)
            except KeyError as err:
                continue

        #sentence emotion
        emo=emotionAnalyzer(senticlist)
        emolist.append(emo)

    print(emolist)
    return emolist



"""---------------main--------------------"""

#read in tweeter json file
rawtweets=[json.loads(line) for line in open('processedtweets.json', 'r')]

#list containing all twitter text
twtTextlist=list()
for tweets in rawtweets:
    for fields in tweets:
        if fields=='full_text':
            twtText=tweets[fields]
            #remove url links in text
            twtText= re.sub(r'http\S+', '', twtText)
            #print(twtText)
            twtTextlist.append(twtText)

"""get labels"""
scrapper=WebScrapper()
#get sentences of concept lists
#emotionlabels=sentenceLabler(sentences)
scrapper.get_polarity(twtTextlist)

#label the tweets
polarities=list()

tweetcnt=0
for tweet in rawtweets:

    tweet['polarity'] = polarities[tweetcnt]
    print (polarities[tweetcnt].rstrip("\n"))
    tweetcnt+=1

#write back
with open("labeledtweets.json", "w") as f:
    json.dump(rawtweets,f)
    f.close()