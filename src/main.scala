/* @author Utilisateur
 */

import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

object main extends App {

  import com.tncy.top.image.ImageWrapper;

  def codeGrey() {
    for (i <- 0 to image2D.length - 1) {
      for (j <- 0 to image2D(1).length - 1) {
        var T = (image2D(i)(j) & 0xFF000000) // to les bit T à 1
        var R = (image2D(i)(j) & 0x00FF0000) >> 16 // to les bit R à 1
        var G = (image2D(i)(j) & 0x0000FF00) >> 8 // to les bit G à 1
        var B = (image2D(i)(j) & 0x000000FF) // to les bit B à 1
        var RGB = (0.299 * R + 0.587 * G + 0.114 * B).toInt; // FF + FF + FF = 7f     
        image2D(i)(j) = T + (RGB * 65536 + RGB * 256 + RGB); // 192  000 000 -> 127 127 127
      }
    }
  }
  def printImg(name: String) {
    var outputFile: String = name;
    wrappedImage.saveImage(outputFile);
  }
  def sobleBy3() {
    var sobel_x: Array[Array[Double]] = Array(
      Array(-1, 0, 1),
      Array(-2, 0, 2),
      Array(-1, 0, 1));

    var sobel_y: Array[Array[Double]] = Array(
      Array(1, 2, 1),
      Array(0, 0, 0),
      Array(-1, -2, -1));

    for (x <- 1 to wrappedImage.height - 2) {
      for (y <- 1 to wrappedImage.width - 2) {

        var a = (image2D(x - 1)(y - 1) & 0x000000FF)
        var b = (image2D(x)(y - 1) & 0x000000FF)
        var c = (image2D(x + 1)(y - 1) & 0x000000FF)
        var d = (image2D(x - 1)(y) & 0x000000FF)
        var e = (image2D(x)(y) & 0x000000FF)
        var f = (image2D(x + 1)(y) & 0x000000FF)
        var g = (image2D(x - 1)(y + 1) & 0x000000FF)
        var h = (image2D(x)(y + 1) & 0x000000FF)
        var i = (image2D(x + 1)(y + 1) & 0x000000FF)

        // println(a + " | " + b + " | " + c)

        var pixel_x =
          (sobel_x(0)(0) * a) + (sobel_x(0)(1) * b) + (sobel_x(0)(2) * c) +
            (sobel_x(1)(0) * d) + (sobel_x(1)(1) * e) + (sobel_x(1)(2) * f) +
            (sobel_x(2)(0) * g) + (sobel_x(2)(1) * h) + (sobel_x(2)(2) * i);
        var pixel_y =
          (sobel_y(0)(0) * a) + (sobel_x(0)(1) * b) + (sobel_x(0)(2) * c) +
            (sobel_y(1)(0) * d) + (sobel_x(1)(1) * e) + (sobel_x(1)(2) * f) +
            (sobel_y(2)(0) * g) + (sobel_x(2)(1) * h) + (sobel_x(2)(2) * i);

        var lol = (Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y)).ceil).toInt

        image2D2(x)(y) = 0xFF000000 + (lol * 65536 + lol * 256 + lol).toInt;

        //println((image2D(x)(y) & 0xFF000000)>>24);
      }
    }
  }
  def noiseRemove() {
    for (x <- 1 to wrappedImage.height - 2) {
      for (y <- 1 to wrappedImage.width - 2) {

        var a = (image2D(x - 1)(y - 1) & 0x000000FF)
        var b = (image2D(x)(y - 1) & 0x000000FF)
        var c = (image2D(x + 1)(y - 1) & 0x000000FF)
        var d = (image2D(x - 1)(y) & 0x000000FF)
        var e = (image2D(x)(y) & 0x000000FF)
        var f = (image2D(x + 1)(y) & 0x000000FF)
        var g = (image2D(x - 1)(y + 1) & 0x000000FF)
        var h = (image2D(x)(y + 1) & 0x000000FF)
        var i = (image2D(x + 1)(y + 1) & 0x000000FF)

        var moy = (a+b+d+c+e+f+g+h+i)/9;

        image2D(x)(y) = moy;
      }
    }
  }
  
  // Note ~A = complement à 2 de A, 00 00 00 11 & color = code B

  var fileName: String = "imgs/ImagesReelles/2_GoogleMaps.png"; // Source de l'image
  //var fileName: String = "imgs/ImagesTests/8.jpg"; // Source de l'image
  //var fileName: String = "imgs/Valve_original.png"; 
  var wrappedImage: ImageWrapper = new ImageWrapper(fileName); // Chargement
  var image2D: Array[Array[Int]] = wrappedImage.getImage(); // Mise en matrice

  var wrappedImage2: ImageWrapper = new ImageWrapper(fileName); // Chargement
  var image2D2: Array[Array[Int]] = wrappedImage2.getImage(); // Mise en matrice

  println("Informations : " + wrappedImage.height + " px par " + wrappedImage.width + " px.");

  noiseRemove()
  
  sobleBy3()

  var outputFile: String = "outputImage.jpg";
  wrappedImage.saveImage(outputFile);

  var outputFile2: String = "outputImage2.jpg";
  wrappedImage2.saveImage(outputFile2);

  //printImg("outputImage.jpg");

}
