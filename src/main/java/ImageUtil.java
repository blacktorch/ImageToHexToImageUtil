import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Author: Chidiebere Onyedinma
 * Date: 01-04-2020
 */

public class ImageUtil {

    public static void main(String[] args) {
        String imageFile = "";
        String hexFile = "";
        int width = 0;
        int height = 0;
        String operation = "I-H";
        int filter = 0;

        if (args.length == 0){
            printUsage();
            return;
        }

        try {
            for (int i = 0; i < args.length; i++){
                if (args[i].equals("-i")){
                    imageFile = args[i+1];
                } else if (args[i].equals("-b")){
                    hexFile = args[i+1];
                } else if (args[i].equals("-w")){
                    width = Integer.parseInt(args[i+1]);
                } else if (args[i].equals("-h")){
                    height = Integer.parseInt(args[i+1]);
                } else if (args[i].equals("-o")){
                    operation = args[i+1];
                } else if (args[i].equals("-f")){
                    if (args[i+1].equals("s")){
                        filter = Constants.SOBEL;
                    } else if (args[i+1].equals("k")){
                        filter = Constants.KMEANS;
                    }
                }
            }

            if (operation.equals("I-H")){
                BufferedImage inputImage = ImageIO.read(new File(imageFile));
                ImageToHex imageToHex = new ImageToHex(inputImage);
                imageToHex.convertToHexFile();
                System.out.println("Image Width: " + inputImage.getWidth());
                System.out.println("Image Height: " + inputImage.getHeight());
            } else if (operation.equals("H-I")){
                HexToImage hexToImage = new HexToImage(hexFile, width, height, filter);
                hexToImage.convertToImage();
            }
        } catch (Exception e){
            printUsage();
            e.printStackTrace();
        }

    }

    private static void printUsage(){
        System.out.println("Run with the following arguments:\n");
        System.out.println("-i Path to image\n" +
                "-b Path to hex file\n" +
                "-w Image width\n" +
                "-h Image height\n" +
                "-o Operation type eg: image to hex or hex to image, denoted by 'I-H' and 'H-I'\n" +
                "-f Filter conversion eg: 's' for hex file gotten from a sobel operation and 'k' for kmeans hex file\n" +
                "Note: For Hex to Image conversion, you must specify the path to hex file, image width, height and filter\n");

        System.out.println("Examples\n" +
                "Image to Hex conversion:\n" +
                "java -jar ImageUtil.jar -i /home/user/sample_images/bike.jpg -o I-H\n" +
                "\n" +
                "Hex to Image conversion:\n" +
                "java -jar ImageUtil.jar -b /home/user/output/output.hex -o H-I -w 500 -h 450 -f s\n" +
                "\n" +
                "All converted files will be in the same directory as the ImageUtil jar.");
    }
}
