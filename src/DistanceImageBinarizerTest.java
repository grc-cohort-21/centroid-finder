import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;

class DistanceImageBinarizerTest {

    @Test
    void testToBinaryArray_AllPixelsBelowThreshold() {
        // Mock ColorDistanceFinder that always returns a small distance
        ColorDistanceFinder mockFinder = (color1, color2) -> 50.0;

        // Create 2x2 image
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockFinder, 0xFFFFFF, 150);
        int[][] result = binarizer.toBinaryArray(image);

        // Expect all pixels to be white (1)
        for (int r = 0; r < result.length; r++) {
            for (int c = 0; c < result[0].length; c++) {
                assertEquals(1, result[r][c]);
            }
        }
    }

    @Test
    void testToBinaryArray_AllPixelsAboveThreshold() {
        // Mock ColorDistanceFinder that always returns a large distance
        ColorDistanceFinder mockFinder = (color1, color2) -> 255.0;

        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockFinder, 0xFFFFFF, 150);

        int[][] result = binarizer.toBinaryArray(image);

        // Expect all pixels to be black (0)
        for (int[] row : result) {
            for (int value : row) {
                assertEquals(0, value);
            }
        }
    }

    @Test
    void testToBinaryArray_MixedPixels() {
        // Alternating distances
        ColorDistanceFinder mockFinder = (color1, color2) -> (color1 == 0x000000) ? 100.0 : 200.0;

        BufferedImage image = new BufferedImage(2, 1, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, 0x000000); // distance 100 → 1
        image.setRGB(1, 0, 0xFFFFFF); // distance 200 → 0

        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockFinder, 0xFFFFFF, 150);
        int[][] result = binarizer.toBinaryArray(image);

        assertEquals(1, result[0][0]);
        assertEquals(0, result[0][1]);
    }

    @Test
    void testToBinaryArray_ReturnsArrayWithSameDimensions() {
        ColorDistanceFinder mockFinder = (color1, color2) -> 100.0;

        BufferedImage image = new BufferedImage(3, 4, BufferedImage.TYPE_INT_RGB);
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockFinder, 0x000000, 150);
        int[][] result = binarizer.toBinaryArray(image);

        assertEquals(4, result.length);       // height
        assertEquals(3, result[0].length);    // width
    }
}
