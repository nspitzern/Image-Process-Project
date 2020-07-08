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
![building](https://user-images.githubusercontent.com/33936159/86952373-912c3680-c15b-11ea-89a4-b828b208268e.jpg)


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
<!--- add grey image of building --->
![grey](https://user-images.githubusercontent.com/33936159/86952496-c173d500-c15b-11ea-8696-cff08a84201b.jpg)


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
![flip90](https://user-images.githubusercontent.com/33936159/86952601-ea946580-c15b-11ea-8e41-0be75a842c8f.jpg)

**180 degrees flip:**</br>
Flips the image 180 degrees around a chosen axis (x/y).</br>
Example:
```java
Image img = new Image("src/images/panda.jpg");
ImageProcess ip = new ImageProcess();
Image newImg = ip.flip180X(img);
```

<!--- add 180degX flip panda --->
![flip180X](https://user-images.githubusercontent.com/33936159/86952685-07309d80-c15c-11ea-89c9-4fdc775541a8.jpg)

</br>

We can also flip only one channel of colors around an axis:
```java
Image img = new Image("src/images/panda.jpg");
ImageProcess ip = new ImageProcess();
Image newImg = ip.flip180YBlue(img);
```

<!--- add 180degYBlue flip panda --->
![flip180YBlue](https://user-images.githubusercontent.com/33936159/86952691-0a2b8e00-c15c-11ea-9a06-b8a5d9ad8a29.jpg)
