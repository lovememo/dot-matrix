import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
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

        String fileName = "C:\\Users\\Lovememo\\Desktop\\matrix_dot\\src\\main\\resources\\1.png";//148 × 191
        fileName = "C:\\Users\\Lovememo\\Desktop\\matrix_dot\\src\\main\\resources\\1.png";//148 × 191
//        BufferedImage image = ImageIO.read(new File(fileName));

        BufferedImage image = Thumbnails.of(fileName)
                .scale(0.3f)
                .asBufferedImage();

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
            return 0;
        } else {
            return 1;
        }
    }


    private int getThreshold(int[] inPixels, int height, int width) {
        // maybe this value can reduce the calculation consume;
        int inithreshold = 127;
        int finalthreshold = 0;
        int temp[] = new int[inPixels.length];
        for (int index = 0; index < inPixels.length; index++) {
            temp[index] = (inPixels[index] >> 16) & 0xff;
        }
        List<Integer> sub1 = new ArrayList<Integer>();
        List<Integer> sub2 = new ArrayList<Integer>();
        int means1 = 0, means2 = 0;
        while (finalthreshold != inithreshold) {
            finalthreshold = inithreshold;
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] <= inithreshold) {
                    sub1.add(temp[i]);
                } else {
                    sub2.add(temp[i]);
                }
            }
            means1 = getMeans(sub1);
            means2 = getMeans(sub2);
            sub1.clear();
            sub2.clear();
            inithreshold = (means1 + means2) / 2;
        }
        long start = System.currentTimeMillis();
        System.out.println("Final threshold  = " + finalthreshold);
        long endTime = System.currentTimeMillis() - start;
        System.out.println("Time consumes : " + endTime);
        return finalthreshold;
    }

    private static int getMeans(List<Integer> data) {
        int result = 0;
        int size = data.size();
        for (Integer i : data) {
            result += i;
        }
        return (result / size);
    }
}