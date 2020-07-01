/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Chris
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class UtilBase64Image {
  public static String encoder(String imagePath) {
      File file = new File(imagePath);
      try (FileInputStream imageInFile = new FileInputStream(file)) {
          // Reading a Image file from file system
        String base64Image = "";
          byte imageData[] = new byte[(int) file.length()];
          imageInFile.read(imageData);
          base64Image = Base64.getEncoder().encodeToString(imageData);
          return base64Image;
      } catch (FileNotFoundException e) {
          System.out.println("Image not found" + e);
      } catch (IOException ioe) {
          System.out.println("Exception while reading the Image " + ioe);
      }
      return null;
  }
  
  public static void decoder(String base64Image, String pathFile) {
      try {                  
          FileOutputStream imageOutFile = new FileOutputStream(pathFile, false);
          // Converting a Base64 String into Image byte array          
          byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
          imageOutFile.write(imageByteArray);
      } catch (FileNotFoundException e) {
          System.out.println("Image not found" + e);
      } catch (IOException ioe) {
          System.out.println("Exception while reading the Image " + ioe);
      }
  }
}
