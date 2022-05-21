# from nltk.tokenize import sent_tokenize
# from textblob import TextBlob
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer

MIN_CORPORA = [
    'brown',  # Required for FastNPExtractor
    'punkt',  # Required for WordTokenizer
    'wordnet',  # Required for lemmatization
    'averaged_perceptron_tagger',  # Required for NLTKTagger
]

def download_lite():
    for each in MIN_CORPORA:
        nltk.download(each)

text1 = '''
The titular threat of The Blob has always struck me as the ultimate movie
monster: an insatiably hungry, amoeba-like mass able to penetrate
virtually any safeguard, capable of--as a doomed doctor chillingly
describes it--"assimilating flesh on contact.
Snide comparisons to gelatin be damned, it's a concept with the most
devastating of potential consequences, not unlike the grey goo scenario
proposed by technological theorists fearful of
artificial intelligence run rampant.
'''

def analyze(text):
    analyzer = SentimentIntensityAnalyzer()
    print(analyzer.polarity_scores(text))
    return analyzer.polarity_scores(text)['compound']


# def compute(text):
#     blob = TextBlob(text)
#     blob.tags           # [('The', 'DT'), ('titular', 'JJ'),
#     #  ('threat', 'NN'), ('of', 'IN'), ...]
#
#     blob.noun_phrases   # WordList(['titular threat', 'blob',
#     #            'ultimate movie monster',
#     #            'amoeba-like mass', ...])
#
#     for sentence in blob.sentences:
#         print("popcorn")
#         print(sentence.sentiment.polarity)
#     # 0.060
#     # -0.341
