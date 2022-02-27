import nltk
#nltk.download('all')
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk import ngrams
import matplotlib.pyplot as plt
import time
start_time = time.time()

file = open("xxx.txt","r")

turkish_stopwords = stopwords.words('turkish')
english_stopwords = set(stopwords.words('english'))
#since stopwords for turkish ,s not enough, added more stopwords
extra_tr_sw = ['a','altı','altmış','ancak','arada','artık','asla','ayrıca',
                'bana','bazen','bazıları','ben','benden','beni','benim','beri',
                'beş','bile','bilhassa','bin','bir','biraz','birçoğu','birçok',
                'birisi','bizden','bize','bizi','bizim','böyle','böylece','buna',
                'bunda','bundan','bunlar','bunları','bunların','bunu','bunun',
                'burada','bütün','çoğu','çoğunu','dahi','dan','değil','diğer',
                'diğeri','diğerleri','doksan','dokuz','dolayı','dolayısıyla',
                'dört','e','edecek','eden','ederek','edilecek','ediliyor',
                'edilmesi','ediyor','elbette','elli','etmesi','etti','ettiği',
                'ettiğini','fakat','falan','filan','gene','gereği','gerek','göre',
                'hala','halde','halen','hangi','hangisi','hani','hatta','henüz',
                'herhangi','herkes','herkese','herkesi','herkesin','hiçbir',
                'hiçbiri','i','ı','içinde','iki','ilgili','işte','itibaren',
                'itibariyle','kaç','kadar','karşın','kendi','kendilerine',
                'kendine','kendini','kendisi','kendisine','kendisini','kime',
                'kimi','kimin','kimisi','kimse','kırk','madem','mi','milyar',
                'milyon','nedenle','neyse','nin','nın','nun','nün','öbür','olan',
                'olarak','oldu','olduğu','olduğunu','olduklarını','olmadı',
                'olmadığı','olmak','olması','olmayan','olmaz','olsa','olsun',
                'olup','olur','olursa','oluyor','on','ön','ona','önce','ondan',
                'onlar','onlara','onlardan','onları','onların','onu','onun',
                'orada','öte','ötürü','otuz','öyle','oysa','pek','rağmen','sana',
                'şayet','şekilde','sekiz','seksen','sen','senden','seni','senin',
                'şeyden','şeye','şeyi','şeyler','şimdi','sizden','size','sizi',
                'sizin','sonra','şöyle','şuna','şunları','şunu','ta','tabii',
                'tam','tamam','tamamen','tarafından','trilyon','tümü','u','ü',
                'üç','un','ün','üzere','var','vardı','yapacak','yapılan',
                'yapılması','yapıyor','yapmak','yaptı','yaptığı','yaptığını',
                'yaptıkları','ye','yedi','yerine','yetmiş','yi','yı','yine',
                'yirmi','yoksa','yu','yüz','zaten','zira']
turkish_stopwords.extend(extra_tr_sw)

#read file, take tokenize
text = word_tokenize(file.read())

#make the text lowercase
lower_text = [token.lower() for token in text]
#remove not Alphanumeric Characters
new_words= [word for word in lower_text if word.isalnum()]
#remove any digits
new_words_without_num = [c for c in new_words if not c.isdigit()]

#remove stopwords
#if text is in english change 'turkish_stopwords' to 'english_stopwords'
wordsFiltered = []
for w in new_words_without_num:
    if w not in english_stopwords:
        wordsFiltered.append(w)

#find 1-grams frequency
freq_one_gram = nltk.FreqDist(wordsFiltered)
"""
#draw plot of 1-gram
plt.figure(figsize=(25, 15))
freq_one_gram.plot(50,title = "1-grams")
"""
#print 1-grams top 50
print(freq_one_gram.most_common(50))

print("-------------------------------------")

#find 2-grams
two_grams = ngrams(wordsFiltered, 2)
twograms = []
for grams in two_grams:
    twograms.append(grams)
#find 2-grams frequency
freq_two_grams = nltk.FreqDist(twograms)
"""
#draw plot of 2-gram
plt.figure(figsize=(25, 15))
freq_two_grams.plot(50,title = "2-grams")
"""
#print 2-grams top 50
print(freq_two_grams.most_common(50))

print("-------------------------------------")
#find 3-grams
three_grams = ngrams(wordsFiltered, 3)
threegrams = []
for grams in three_grams:
    threegrams.append(grams)
#find 3-grams frequency
freq_three_grams = nltk.FreqDist(threegrams)
"""
#draw plot of 3-gram
plt.figure(figsize=(25, 15))
freq_three_grams.plot(50,title = "3-grams")
"""
#print 3-grams top 50
print(freq_three_grams.most_common(50))

print("--- %s seconds ---" % (time.time() - start_time))
