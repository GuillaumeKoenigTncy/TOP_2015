/**
 * @author Utilisateur
 */
object main extends App{
  
    // Import the API library
    // v 11.0
   import com.tncy.top.image.ImageWrapper;

   // Source image file
   var fileName : String = "imgs/ImagesTests/2.jpg";

   // Load wrapped image
   var wrappedImage : ImageWrapper = new ImageWrapper(fileName);
   // Get the image
   var image2D : Array[Array[Int]] = wrappedImage.getImage();

   // Print image height and width
   println("The image height is: " + wrappedImage.height + " px.");
   println("The image width is: " + wrappedImage.width + " px.");

   // Modify it
   for (row <- 0 to 40) {
     for (col <-0 to 80) {
       image2D(row)(col) = 0x000000FF; // Set these pixels to RGB blue
     }
   }

   // Destination image file - Note that the image file name must contain the file type extension for the image to be saved correctly.
   var outputFile : String = "outputImage.jpg";
   // Save the result
   wrappedImage.saveImage(outputFile);
  
}