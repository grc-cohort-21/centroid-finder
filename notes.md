## ImageGroupFinder:

This finds all groups given and sorts the groups from largest to smallest, like the CSV that was shown to us in class.

## Group:

A Group class that holds the size of each pixel that meets the requirements of touching another similar pixel and keeps the size of that group for comparison. Centroid is the average of the coordinates of that group. Has overridden compareTo to help determine order

## DistanceImageBinarizer:

This looks at how different the pixels are and whether or not we can consider them to be similar enough to the reference color. If they are close, we can consider changing the value to white; if not, we change it to black.

## Coordinate:

class record that holds the x and y values. This represents its place in the 2d array or the pixel value.

## BinaryGroupFinder:

This is the method that takes in the 2d array of the image and returns a group of the highest to lowest islands of white pixels that are touching vertically or horizontally.
