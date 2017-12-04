# Input Data
70k lines from South Park  
Labeled with season, episode and speaker  
Example lines:  
10,1,Stan,"You guys, you guys! Chef is going away.”  
10,1,Kyle,"Ahh, hi, can we speak to the head guy or something?”  
https://www.kaggle.com/tovarischsukhov/southparklines/data 
# Software
## Processing
We consider each quote as a document. First, we strip non-alphanumeric characters. Then we get words by splitting the string delimited by spaces. The words, and the count of each are put in a hashmap, keyed based on speaker.
## Interface
First we prompt the user to enter k-value, which we will use for calculating. Then the user can enter a phrase and we will return which character most likely would say that. Then it loops so the user can enter more phrases.
## KNN
1. Calculate Okapi distance from input phrase to every document
2. Get k-nearest documents
3. Return most common speaker from k-nearest documents
# Output
Our output is the prediction of what character would most likely say the input string. This is useful if you were writing dialogue for South Park and wanted to check that your phrasing style was in keeping with the characters standard style. 
Also our program could easily be used for other data sets, such as for other shows or presidents.
# Testing
Firstwasdid testing to figure out the best Okapi parameters. This testing was done on a smaller subset of data for shorter run time. The best values found were k1=1.2 b=0.2 k2=75.  
Then using those parameters we did K-value testing which found the highest accuracy of 36% at k = 13.
# Work Log
Everyone contributed to bug fixing and connecting everything together.
Main areas of responsibility:
 Pierson: KNN, Okapi Distance, Cosine Distance (normalization)
 Daniel: Documentation, Slides
 Michael: Dataset, Interface, Experiment

