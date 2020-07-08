# Image-Process-Project
My impelmentation of Image Processing Algorithms (in Java)
</br>
# Image
The basic object to work with is an implementation of an Image object.</br>
Each image contains 2D array for each channel (R, G, B) based on the *Height* and *Width*.</br>

</br>

Basic load of an image:
```java
Image img = new Image("src/images/building.jpg");
img.show();
```

<!--- add image of building here --->

</br>

You can also save the image:
```java
img.save("src/images/newImage.jpg");
```

# ImageProcess
The ImageProcess object contains many algorithms for image manipulation.</br>
The ImageProcess object can be created woth the following code:</br>
```java
ImageProcess ip = new ImageProcess();
```

</br>

Usage example:
```java
Image img = new Image("src/images/building.jpg");
ImageProcess ip = new ImageProcess();
Image newImg = ip.RGB2GreyScale(img);
newImg.save("src/images/grey.jpg");
```

</br>
<!--- add grey image of building --->

# Algorithms
## Basic image manipulation
### Flips

**90 degrees flip:**</br>
Flips the image 90 degrees (clockwise).</br>
Example:</br>

```java
Image img = new Image("src/images/panda.jpg");
ImageProcess ip = new ImageProcess();
Image newImg = ip.flip90(img);
```

<!--- add 90deg flip panda --->

**180 degrees flip:**</br>
Flips the image 180 degrees around a chosen axis (x/y).</br>
Example:
```java
Image img = new Image("src/images/panda.jpg");
ImageProcess ip = new ImageProcess();
Image newImg = ip.flip180X(img);
```

<!--- add 180degX flip panda --->

</br>

We can also flip only one channel of colors around an axis:
```java
Image img = new Image("src/images/panda.jpg");
ImageProcess ip = new ImageProcess();
Image newImg = ip.flip180YBlue(img);
```

<!--- add 180degYBlue flip panda --->
