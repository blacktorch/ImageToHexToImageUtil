/**
 * Author: Chidiebere Onyedinma
 * Date: 01-04-2020
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ImageToHex {
    private BufferedImage inputImage;
    private int width;
    private int height;

    public ImageToHex(BufferedImage inputImage){
        this.inputImage = inputImage;
        this.width = inputImage.getWidth();
        this.height = inputImage.getHeight();
    }

    public boolean convertToHexFile(){
        try {
            FileWriter writer = new FileWriter("input.hex");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            int[] pixels = new int[width * height * 3];

            int p = 0;
            for(int i=0;i<width;i++){
                for(int j=0;j<height;j++){
                    Color color = new Color(inputImage.getRGB(i,j));
                    pixels[p] = color.getRed();
                    pixels[p+1] = color.getGreen();
                    pixels[p+2] = color.getBlue();
                    p+=3;

                }
            }

            for (int pix : pixels){
                bufferedWriter.write(Integer.toHexString(pix));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();

        } catch (IOException ex) {
            System.err.println("Unable to create output Hex file.");
            return false;
        }


        return true;
    }
}
