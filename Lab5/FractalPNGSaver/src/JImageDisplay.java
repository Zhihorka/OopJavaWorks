import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {
    private BufferedImage image;

    // тут объявляется картинка
    public JImageDisplay(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Dimension dimension = new Dimension(width, height);
        super.setPreferredSize(dimension);
    }

    // тут отрисовывается фрактал
    public void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    //тут удаляется фрактал
    public void clearImage() {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                drawPixel(i, j, 0);
            }
        }
    }

    // тут окрашивается каждый пиксель
    public void drawPixel(int x, int y, int rgbColor) {
        image.setRGB(x, y, rgbColor);
    }

    public BufferedImage getImage() {
        return image;
    }
}
