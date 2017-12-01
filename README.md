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
<description of our program,a short description of how the algorithm works, 
# Result
Our output is the prediction of what character would most likely say the input string. This is interesting because @TODO
a description of the output and justification of why the result is interesting and useful, 
# Work Log
Each person did roughly equal amount of work.
Pierson: KNN
Michael: Dataset, Interface
Daniel: Documentation, Slides
