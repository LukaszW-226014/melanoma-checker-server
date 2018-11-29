package com.wojcik.lukasz.melanomacheckerserver.service;

import com.wojcik.lukasz.melanomacheckerserver.model.criteria.CriteriaService;
import com.wojcik.lukasz.melanomacheckerserver.model.detector.ColorDetection;
import com.wojcik.lukasz.melanomacheckerserver.model.detector.DiameterDetection;
import com.wojcik.lukasz.melanomacheckerserver.model.detector.EvolutionDetection;
import org.apache.commons.io.FileUtils;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CriteriaServiceImpl implements CriteriaService {

    private static final String DATA_WRITE_PATH = "/Users/lukaszwojcik/Development/melanoma-checker-server/src/main" +
            "/resources/static/write/";

    private static final String DATA_READ_PATH = "/Users/lukaszwojcik/Development/melanoma-checker-server/src/main" +
            "/resources/static/read/";

    private static BufferedImage Mat2BufferedImage(Mat matrix) throws Exception {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        byte ba[] = mob.toArray();

        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
        return bi;
    }

    private static BufferedImage createBufferedImage(Mat mat) {
        BufferedImage image = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        mat.get(0, 0, data);
        return image;
    }

    private void printImage(BufferedImage image) {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new Printable() {
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex != 0) {
                    return NO_SUCH_PAGE;
                }
                graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                return PAGE_EXISTS;
            }
        });
        try {
            printJob.print();
        } catch (PrinterException e1) {
            e1.printStackTrace();
        }
    }

    public void testCV() throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("mat = " + mat.dump());

        File file = new File(DATA_WRITE_PATH + "test1.jpg");
        Mat image = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        printImage(createBufferedImage(image));

    }

    private void initCvLib() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private void clearWriteDirectory() throws IOException {
        FileUtils.cleanDirectory(new File(DATA_WRITE_PATH));
    }

    public void testDetectBorderMethod(String inputFileName) throws IOException {
        clearWriteDirectory();
        initCvLib();
        try {
            // read image
            Mat image = Imgcodecs.imread(DATA_READ_PATH + inputFileName);
            // convert to grayscale
            Mat gray = new Mat();
            Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "1gray.png", gray);
//            // enhance contrast
//            Mat contrast = new Mat();
//            Imgproc.equalizeHist(gray, contrast);
//            Imgcodecs.imwrite(DATA_WRITE_PATH + "contrast.png", contrast);
            // gaussian filter
            Mat gaussian = new Mat();
            Imgproc.GaussianBlur(gray, gaussian, new Size(5, 5), 2);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "2gaussian.png", gaussian);
            // threshold
            Mat threshold = new Mat();
            Imgproc.threshold(gaussian, threshold, 127, 255, Imgproc.THRESH_BINARY_INV);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "3threshold.png", threshold);
//            // gaus
//            Mat threshold = new Mat();
//            Imgproc.GaussianBlur(threshold, threshold, new Size(5, 5), 2);
//            Imgcodecs.imwrite(DATA_WRITE_PATH + "4denoisedThresh.png", threshold);
            // invert
            Mat mask = new Mat(threshold.rows() + 2, threshold.cols() + 2, CvType.CV_8U, Scalar.all(0));
            Mat floodFill = threshold.clone();
            Imgproc.floodFill(floodFill, mask, new Point(0, 0), new Scalar(255), new Rect(), new Scalar(0),
                    new Scalar(0), 4 | Imgproc.FLOODFILL_MASK_ONLY);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "5floodFill.png", floodFill);
            Mat invertedFloodFill = new Mat();
            Core.bitwise_not(floodFill, invertedFloodFill);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "6inverted.png", invertedFloodFill);
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(threshold, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
            System.out.println(contours.toString());
            double maxArea = 0;
            MatOfPoint max_contour = new MatOfPoint();
            Iterator<MatOfPoint> iterator = contours.iterator();
            while (iterator.hasNext()) {
                MatOfPoint contour = iterator.next();
                double area = Imgproc.contourArea(contour);
                if (area > maxArea) {
                    maxArea = area;
                    max_contour = contour;
                }
            }
            // fill mole
            Mat filledMole = new Mat();
            Core.bitwise_or(threshold, invertedFloodFill, filledMole);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "7filled.png", filledMole);
            // laplacian
            Mat laplacian = new Mat();
            Imgproc.Laplacian(filledMole, laplacian, CvType.CV_64F);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "8laplacian.png", laplacian);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void testDetectAsymetryMethod(String inputFileName) throws IOException {
        clearWriteDirectory();
        initCvLib();
        try {
            Mat image1 = Imgcodecs.imread(DATA_READ_PATH + inputFileName);
            Mat image = new Mat();
            Imgproc.GaussianBlur(image1, image, new Size(5, 5), 0);


            Imgcodecs.imwrite(DATA_WRITE_PATH + "8end.png", image);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void testDetectBorderMethod2(String inputFileName) throws IOException {
        clearWriteDirectory();
        initCvLib();
        try {
            Mat image = Imgcodecs.imread(DATA_READ_PATH + inputFileName);
            // convert to grayscale
            Mat gray = new Mat();
            Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "1gray.png", gray);
            // gaussian filter
            Mat gaussian = new Mat();
            Imgproc.GaussianBlur(gray, gaussian, new Size(5, 5), 2);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "2gaussian.png", gaussian);
            // threshold
            Mat threshold = new Mat();
            Imgproc.threshold(gaussian, threshold, 127, 255, Imgproc.THRESH_BINARY_INV);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "3threshold.png", threshold);

            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarhy = new Mat();
            Imgproc.findContours(threshold, contours, hierarhy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
            Mat img = new Mat();
            Mat color = new Mat();
            Imgproc.cvtColor(img, color, Imgproc.COLOR_GRAY2RGB);
            Imgproc.drawContours(color, contours, -1, new Scalar(0, 255, 0), 2);
            Imgcodecs.imwrite(DATA_WRITE_PATH + "4contours.png", color);
            for (MatOfPoint point : contours) {
                Imgproc.boundingRect(point);
            }


            //Imgcodecs.imwrite(DATA_WRITE_PATH + "8end.png", image);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //FIXME take photo from mobile request
    public void testDetectColorMethod(String inputFileName) throws IOException {
        clearWriteDirectory();
        new ColorDetection().detect(inputFileName);
    }

    @Override
    public Integer detectAsymmetry(File file) {
        return 2;
    }

    @Override
    public Integer detectBorder(File file) {
        return 0;
    }

    @Override
    public Integer detectColour(File file) {
        return new ColorDetection().detect("xx");
    }

    @Override
    public Integer detectDiameter(Integer sizeValue) {
        return new DiameterDetection().detect(sizeValue);
    }

    @Override
    public Integer detectEvolution(Boolean isEvolve) {
        return new EvolutionDetection().detect(isEvolve);
    }
}
