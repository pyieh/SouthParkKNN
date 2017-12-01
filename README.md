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
# Result
Our output is the prediction of what character would most likely say the input string. This is useful if you were writing dialogue for South Park and wanted to check that your phrasing style was in keeping with the characters standard style. 
Also our program could easily be used for other data sets, such as for other shows or presidents.
# Work Log
Everyone did bug fixing and connecting everything together. Each person did roughly equal amount of work.
Main focuses:
Pierson: KNN, Okapi Distance
Michael: Dataset, Interface
Daniel: Documentation, Slides
