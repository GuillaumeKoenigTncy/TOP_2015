/**
 * @author Utilisateur
 */

object main extends App {

  implicit class IntToBase(val digits: String) extends AnyVal {
    def base(b: Int) = Integer.parseInt(digits, b)
    def b = base(2)
    def o = base(8)
    def x = base(16)
  }

  var array = new Array(10);

  // Import the API library
  // v 11.0
  import com.tncy.top.image.ImageWrapper;

  // Source image file
  var fileName: String = "imgs/ImagesTests/2.jpg";

  // Load wrapped image
  var wrappedImage: ImageWrapper = new ImageWrapper(fileName);
  // Get the image
  var image2D: Array[Array[Int]] = wrappedImage.getImage();

  // Print image height and width
  println("The image height is: " + wrappedImage.height + " px.");
  println("The image width is: " + wrappedImage.width + " px.");

  //println("" + image2D(10)(10) + "");

  // Modify it
  for (row <- 0 to wrappedImage.height - 1) {
    for (col <- 0 to wrappedImage.width - 1) {

      image2D(row)(col) = 0x000000FF; // Set these pixels to RGB blue
    }
  }

  Integer.toString(0x05F1FA, 16);
  Integer.valueOf("100000001000000010000001", 2);

  // println(" " + Integer.valueOf("101", 2) + " ")
  println(Integer.toString(image2D(10)(10), 2));

  // (MSB) [TTTT TTTT] RRRR RRRR GGGG GGGG BBBB BBBB (LSB)
  var col = (image2D(10)(10)).toString();
  var len = col.length();
  // var B = col.substring(len - 2, len);
  // var G = col.substring(len - 4, len - 2);
  // var R = col.substring(len - 6, len - 4);

  // println(" " + col + " ")

  // println("R" + R + "G" + G + "B" + B + " ")

  // Destination image file - Note that the image file name must contain the file type extension for the image to be saved correctly.
  var outputFile: String = "outputImage.jpg";
  // Save the result
  wrappedImage.saveImage(outputFile);

}
