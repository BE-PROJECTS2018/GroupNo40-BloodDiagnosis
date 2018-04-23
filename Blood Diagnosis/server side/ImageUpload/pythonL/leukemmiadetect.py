import cv2
import numpy as np
import os
import pandas as pd

img = cv2.imread('C:/Users/krishna/Downloads/Dataset/ALL_IDB1/im/Im001_1.jpg')

#not working
#ORANGE_MIN = np.array([152, 99, 149],np.uint8)
#ORANGE_MAX = np.array([135, 227, 111],np.uint8)

#working
#ORANGE_MIN = np.array([90, 200, 100],np.uint8)
#ORANGE_MAX = np.array([90, 255, 255],np.uint8)

"""
#testing
ORANGE_MIN = np.array([90, 120, 70],np.uint8)
ORANGE_MAX = np.array([180, 255, 255],np.uint8)

hsv_img = cv2.cvtColor(img,cv2.COLOR_BGR2HSV)

segment = cv2.inRange(hsv_img, ORANGE_MIN, ORANGE_MAX)
imS = cv2.resize(segment, (960, 540))
c = cv2.countNonZero(imS)
"""
#print(c)

#cv2.namedWindow("output", cv2.WINDOW_NORMAL)      
#cv2.imshow("output", imS)                            
#cv2.waitKey(0)


directory = os.fsencode(os.getcwd())
data = []
row = []
for file in os.listdir(directory):
    filename = os.fsdecode(file)
    if filename.endswith(".jpg"):
        print(filename)
        img = cv2.imread(filename)
        #testing
        ORANGE_MIN = np.array([90, 120, 70],np.uint8)
        ORANGE_MAX = np.array([180, 255, 255],np.uint8)

        hsv_img = cv2.cvtColor(img,cv2.COLOR_BGR2HSV)

        segment = cv2.inRange(hsv_img, ORANGE_MIN, ORANGE_MAX)
        imS = cv2.resize(segment, (960, 540))
        c = cv2.countNonZero(imS)
        row.append(filename)
        row.append(c)
        data.append(row)
        row = []
    else:
        continue

column  = ['Image', 'Count']
df = pd.DataFrame(data = data, columns = column)
df.to_csv('leukemia.csv')

