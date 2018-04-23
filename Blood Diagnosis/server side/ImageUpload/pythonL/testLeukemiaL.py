import cv2
import numpy as np
import os
import pandas as pd

#img = cv2.imread('C:/Users/krishna/Downloads/Dataset/ALL_IDB1/im/Im024_1.jpg')



#testing
directory = 'C://xampp//htdocs//ImageUpload//uploads'
for file in os.listdir(directory):
    filename = os.fsdecode(file) 
    if filename.endswith(".jpg"): 
        img = directory+'//'+str(filename)
        img1 = cv2.imread(img)
        ORANGE_MIN = np.array([90, 120, 70],np.uint8)
        ORANGE_MAX = np.array([180, 255, 255],np.uint8)
        hsv_img = cv2.cvtColor(img1,cv2.COLOR_BGR2HSV)
        segment = cv2.inRange(hsv_img, ORANGE_MIN, ORANGE_MAX)
        imS = cv2.resize(segment, (960, 540))
        c = cv2.countNonZero(imS)
        if c>5000:
            print("Leukemia Positive")
        else:
            print("Leukemia Negative")

        os.remove(img)
    else:
        continue



