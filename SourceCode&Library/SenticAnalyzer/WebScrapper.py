import requests
from bs4 import BeautifulSoup as bs




class WebScrapper:
#get extracted concepts from sentences using sentinet concept extractor
    def get_concepts(self, searchsentens):
        concept=list()
        concepts=list()
        sentencnt = 0
        try:
            with requests.Session() as s:
                r = s.get('http://sentiment.gelbukh.com/sentiment/concepts.html')
                for senten in searchsentens:
                    input=dict(input=senten)
                    r = s.post('http://sentiment.gelbukh.com/sentiment/run.php', data=input)
                    soup = bs(r.content, 'lxml')
                    items=soup.body.span.text
                    for item in items.childGenerator():
                        if str(item)!='<br/>':
                            concept.append(str(item))
                    concepts.append(concept)
                    sentencnt+=1
                    print("extracted: %d sentences" %sentencnt)

        except Exception as err:
            concepts[-1]=('neutral', 'unknown')
            with requests.Session() as s:
                r = s.get('http://sentiment.gelbukh.com/sentiment/concepts.html')
                for senten in searchsentens[sentencnt+1:]:
                    input = dict(input=senten)
                    r = s.post('http://sentiment.gelbukh.com/sentiment/run.php', data=input)
                    soup = bs(r.content, 'lxml')
                    items = soup.body.span.text
                    for item in items.childGenerator():
                        if str(item) != '<br/>':
                            concept.append(str(item))
                    concepts.append(concept)
                    sentencnt += 1
                    print("extracted: %d sentences" % sentencnt)
        return concepts


    def get_polarity(self, searchsentens):
        polarities=list()
        with requests.Session() as s:
            r = s.get('http://sentiment.gelbukh.com/sentiment/concepts.html')
            sentencnt = 0
            for senten in searchsentens:
                input = dict(input=senten)
                r = s.post('http://sentiment.gelbukh.com/sentiment/run.php', data=input)
                soup = bs(r.content, 'lxml')
                result = soup.body.span.text
                result=result.split('-')
                polarity=result[-1][1:]
                sentencnt += 1
                print("predicted: %d sentence" % sentencnt)

                if (polarity=='' or polarity[0]=='0' ):
                    #remove score
                    polarity='neutral'
                print(polarity)
                polarities.append(polarity)
                #with open(r'C:\Users\User\Desktop\labels.txt', 'a') as r1:
                   #r1.write(polarity+'\n')

        return polarities