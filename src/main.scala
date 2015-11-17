/**
 * @author Utilisateur
 */

import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

object main extends App {

  // Import the API library
  // v 11
  import com.tncy.top.image.ImageWrapper;

  // Fonction qui complète un input par un char sur les bits à gauche dans la limite de len
  def completeBy(input: String, char: Char, len: Int): String = {
    var input_tmp = input;
    for (n <- 0 to len - input.length() - 1) {
      input_tmp = char + input_tmp;
    }
    return input_tmp;
  }

  // Fonction qui convertit une chaine input binaire en un Int par la méthode des poids pondérés
  def binaryToInt(input: String): Int = {
    var number = 0;
    var counter = 0;
    var weight = input.length() - 1;

    while (counter <= input.length() - 1) {
      if (input(counter).compare('1') == 0) {
        number = number + math.pow(2, weight).toInt
      }
      counter = counter + 1;
      weight = weight - 1;
    }
    return number;
  }

  // Fonction qui passe l'image en niveau de gris
  // Nécessite que l'image soit définit avant l'appel de la fonction !
  def RGBToGrey(){
    for (row <- 0 to wrappedImage.height - 1) {
      for (col <- 0 to wrappedImage.width - 1) {

        // (MSB) [TTTT TTTT] RRRR RRRR GGGG GGGG BBBB BBBB (LSB)
        var color = Integer.toString(image2D(row)(col), 2);
        var len = color.length();
        var lol = 32 - len

        if (color(0).compare('-') == 0) {
          color = color.replace('-', '1');
          for (n <- 0 to lol - 1) {
            color = completeBy(color, '1', 32);
          }
          color = color.replace('1', '2');
          color = color.replace('0', '1');
          color = color.replace('2', '0');

          if (color(31).compare('0') == 0) {
            color = color.substring(0, 31) + '1';
          } else {
            color = color.substring(0, 31) + '0';
          }
        } else {
          for (n <- 0 to lol - 1) {
            color = completeBy(color, '0', 32);
          }
        }

        var DR = Integer.valueOf(color.substring(8, 16), 2);
        var DG = Integer.valueOf(color.substring(16, 24), 2);
        var DB = Integer.valueOf(color.substring(24, 32), 2);
        var nuanceDeGris = 0.299 * DR + 0.587 * DG + 0.114 * DB;
        var nuanceDeGrisS = completeBy(nuanceDeGris.toInt.toBinaryString, '0', 8)
        var finalNuanceDeGris = "11111111" + nuanceDeGrisS + nuanceDeGrisS + nuanceDeGrisS

        image2D(row)(col) = binaryToInt(finalNuanceDeGris);
      }
    }
  }

  var fileName: String = "imgs/ImagesReelles/1_GoogleMaps.png"; // Source de l'image
  var wrappedImage: ImageWrapper = new ImageWrapper(fileName); // Chargement
  var image2D: Array[Array[Int]] = wrappedImage.getImage(); // Mise en matrice

  println("Informations : " + wrappedImage.height + " px par " + wrappedImage.width + " px.");
  
  RGBToGrey();
  
  // Destination image file - Note that the image file name must contain the file type extension for the image to be saved correctly.
  var outputFile: String = "outputImage.jpg";
  // Save the result
  wrappedImage.saveImage(outputFile);

}
