import numpy as np
import re
import os
from sklearn.externals import joblib
import matplotlib.image as mpimg

def rgb2gray(rgb):
    return np.dot(rgb[...,:3], [0.299, 0.587, 0.114])

directory = 'C://xampp//htdocs//ImageUpload//uploads'
for file in os.listdir(directory):
    filename = os.fsdecode(file) 

    if filename.endswith(".jpg"): 
        clf = joblib.load('C:\\xampp\\htdocs\\ImageUpload\\python\\knn10.pkl')
        img = directory+'//'+str(filename)
        #print(img)
        test_img = mpimg.imread(img)
        gray = rgb2gray(test_img)
        y_pred = clf.predict_proba(gray.flatten().reshape(1, -1))
        #print(y_pred)
        #print(np.argmax(y_pred))
        #print(y_test[1])
        if(np.argmax(y_pred)) == 0:
            print('Malaria Negative')
        else:
            print('Malaria Positive')
        # index +=1
        os.remove(img)
    else:
        continue
