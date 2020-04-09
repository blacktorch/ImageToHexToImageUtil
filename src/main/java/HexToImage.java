/**
 * Author: Chidiebere Onyedinma
 * Date: 01-04-2020
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class HexToImage {

    private BufferedImage outputImage;
    private String hexFilePath;
    private int width;
    private int height;
    private int conversionType;
    private String[] lines;

    private static String FILE_NAME = "output.png";

    public HexToImage(String hexFilePath, int width, int height, int conversionType) {
        this.outputImage = new BufferedImage(width, height, TYPE_INT_RGB);
        this.hexFilePath = hexFilePath;
        this.width = width;
        this.height = height;
        this.conversionType = conversionType;

        switch (conversionType) {

            case Constants.SOBEL:
                lines = new String[(width - 2) * (height - 2)];
                break;
            case Constants.KMEANS:
                lines = new String[width * height];
                break;
            default:
                lines = null;
        }

        try {
            FileReader reader = new FileReader(hexFilePath);
            BufferedReader bufferedReader = new BufferedReader(reader);

            for (int i = 0; i < lines.length; i++) {
                lines[i] = bufferedReader.readLine();
            }

            bufferedReader.close();

        } catch (IOException ex) {
            System.err.println("Unable to read input hex file");
            ex.printStackTrace();
        }
    }

    public boolean convertToImage() {

        try {

            switch (conversionType) {
                case Constants.SOBEL:
                    sobelImage();
                    break;
                case Constants.KMEANS:
                    kmeansImage();
                    break;
            }

            File outputFile = new File(FILE_NAME);
            ImageIO.write(outputImage, "png", outputFile);

        } catch (IOException ex) {
            System.err.println("Unable to convert hex data to image");
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void sobelImage() {

        int p = 0;
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                int edge = Integer.parseInt(lines[p], 16);
                outputImage.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                p += 1;

            }
        }

    }

    private void kmeansImage() {

        int p = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int seg = Integer.parseInt(lines[p], 16);
                outputImage.setRGB(i, j, (seg << 16 | seg << 8 | seg));
                p++;
            }
        }

    }
}
