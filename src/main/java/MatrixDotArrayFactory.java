import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatrixDotArrayFactory {
    public static void main(String[] args) throws IOException {

        //BufferedImage image = Thumbnails.of("1.png").asBufferedImage();
//
//                .set

        String fileName = "chars-sample.png";
//        BufferedImage image = ImageIO.read(new File(fileName));

        BufferedImage image = new BufferedImage(340,80,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        int fontSize = 32;
        Font font = new Font("STSong", Font.PLAIN, fontSize);
        g.setFont(font);
        g.drawString("鎏光之翼加油",0,image.getHeight()-3-3);
        g.drawString("曙光就在前面，要挺住",0,image.getHeight()-3-fontSize-3-3);


//        BufferedImage image = Thumbnails.of(MatrixDotArrayFactory.class.getResourceAsStream(fileName))
//                .scale(0.2f)
//                .asBufferedImage();

        int height = image.getHeight();
        int width = image.getWidth();
        Raster data = image.getData();
        System.out.println(width + " × " + height);
        int[][] outMatrix = new int[height][width];
        for (int i = 0; i < height; i++) {//行
            for (int j = 0; j < width; j++) {//列
                int[] rgb = new int[3];
                data.getPixel(j, i, rgb);//第一个参数为列 第二个参数为行
                outMatrix[i][j] = calcBinaryValue(rgb);
            }
        }

        for (int i = 0; i < height; i++) {//行
            System.out.print("[");
            for (int j = 0; j < width; j++) {//列
                System.out.print(outMatrix[i][j]);
                if(j != width -1) {
                    System.out.print(", ");
                }
            }

            System.out.print("], \n");
        }

    }

    private static int calcBinaryValue(int[] rgb) {
        int gray = (rgb[0] * 19595 + rgb[1] * 38469 + rgb[2] * 7472) >> 16;
        int THRESHOLD = 127;
        if (gray >= THRESHOLD) {
            return 1;
        } else {
            return 0;
        }
    }


}