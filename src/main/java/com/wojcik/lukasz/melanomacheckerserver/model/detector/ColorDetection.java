package com.wojcik.lukasz.melanomacheckerserver.model.detector;

import com.wojcik.lukasz.melanomacheckerserver.model.criteria.ColorType;
import lombok.Data;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

@Data
public class ColorDetection implements DetectionAware<String>, OpenCvAware {

    private static final String DATA_WRITE_PATH = "/Users/lukaszwojcik/Development/melanoma-checker-server/src/main" +
            "/resources/static/write/";

    private static final String DATA_READ_PATH = "/Users/lukaszwojcik/Development/melanoma-checker-server/src/main" +
            "/resources/static/read/";

    @Override
    public Integer detect(String parameter) {
        initCvLib();
        int score = 0;
        try {
            Mat image1 = Imgcodecs.imread(DATA_READ_PATH + parameter);
            Mat image = new Mat();
            Imgproc.GaussianBlur(image1, image, new Size(5, 5), 0);
            int totalNumberOfPixels = image.rows() * image.cols();
            ColorType[] colors = ColorType.values();

            for (ColorType color : colors) {
                Mat mask = new Mat();
                Core.inRange(image, color.getLower(), color.getUpper(), mask);
                Mat output = new Mat();
                Core.bitwise_and(image, image, output, mask);
                Imgproc.cvtColor(output, output, Imgproc.COLOR_RGB2GRAY);
                int nonZeroPixels = Core.countNonZero(output);
                System.out.println("% " + (float) nonZeroPixels / totalNumberOfPixels * 100);
                if ((float) nonZeroPixels / totalNumberOfPixels * 100 > 1F) {
                    score += 1;
                }
                System.out.println("Score: " + score);
                Imgcodecs.imwrite(DATA_WRITE_PATH + "out" + color.getName() + ".png", output);
                //ToDo: Dodac klasyfikacja dla powyzej 1% i ewentualnie zrobic dla maski zmiany + refactor
            }
            Imgcodecs.imwrite(DATA_WRITE_PATH + "colorsDetect.png", image);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (score == 0) {
            return 1;
        } else if (score > 6) {
            throw new OutOfRangeDetectorValueException(score, getClass().getName());
        } else {
            return score;
        }
    }
}
