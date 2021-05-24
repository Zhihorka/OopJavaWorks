import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay imageDisplay;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;
    private JComboBox comboBox;

    private FractalExplorer (int displaySize) {
        this.displaySize = displaySize;
        this.fractalGenerator = new Mandelbrot();
        this.range = new Rectangle2D.Double(0,0,0,0);
        fractalGenerator.getInitialRange(this.range);
    }

    // точка входа в программу
    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(450);
        fractalExplorer.setGUI();
        fractalExplorer.drawFractal();
    }

    // здесь интерфейс
    public void setGUI() {
        JFrame frame = new JFrame("Разные фракталы 2021");
        JButton buttonReset = new JButton("Очистить");
        JButton buttonSave = new JButton("Сохранить изображение");
        JPanel jPanel_1 = new JPanel();
        JPanel jPanel_2 = new JPanel();
        JLabel label = new JLabel("Фрактал:");

        imageDisplay = new JImageDisplay(displaySize, displaySize);
        imageDisplay.addMouseListener(new MouseListener());

        // здесь список фракталов
        comboBox = new JComboBox();
        comboBox.addItem(new Mandelbrot());
        comboBox.addItem(new Tricorn());
        comboBox.addItem(new BurningShip());
        comboBox.addActionListener(new ActionHandler());

        // кнопка удаления фрактала
        buttonReset.setActionCommand("Очистить");
        buttonReset.addActionListener(new ActionHandler());

        // кнопка сохранения фрактала
        buttonSave.setActionCommand("Сохранить");
        buttonSave.addActionListener(new ActionHandler());

        jPanel_1.add(label, BorderLayout.CENTER);
        jPanel_1.add(comboBox, BorderLayout.CENTER);
        jPanel_2.add(buttonReset, BorderLayout.CENTER);
        jPanel_2.add(buttonSave, BorderLayout.CENTER);

        frame.setLayout(new BorderLayout());
        frame.add(imageDisplay, BorderLayout.CENTER);
        frame.add(jPanel_1, BorderLayout.NORTH);
        frame.add(jPanel_2, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // здесь картинка с фракталом
    private void drawFractal() {
        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {
                int counter = fractalGenerator.numIterations(FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x),
                        fractalGenerator.getCoord(range.y, range.y + range.width, displaySize, y));
                if (counter == -1) {
                    imageDisplay.drawPixel(x, y, 0);
                }
                else {
                    float hue = 0.1f + (float) counter / 100f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    imageDisplay.drawPixel(x, y, rgbColor);
                }
            }
        }
        imageDisplay.repaint();
    }

    // здесь обратотчик кнопок
    public class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Очистить")) {
                // перерисовка фрактала
                fractalGenerator.getInitialRange(range);
                drawFractal();
            } else if (e.getActionCommand().equals("Сохранить")) {
                // сохранение
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("ПНГ картинка", "png");
                fileChooser.setFileFilter(fileFilter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int t = fileChooser.showSaveDialog(imageDisplay);
                if (t == JFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(imageDisplay.getImage(), "png", fileChooser.getSelectedFile());
                    } catch (NullPointerException | IOException ee) {
                        JOptionPane.showMessageDialog(imageDisplay, ee.getMessage(), "Не удалось сохранить!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                fractalGenerator = (FractalGenerator) comboBox.getSelectedItem();
                range = new Rectangle2D.Double(0,0,0,0);
                fractalGenerator.getInitialRange(range);
                drawFractal();
            }
        }
    }


    //обработчик собыйти клика мышкой
    public class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            double x = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, e.getX());
            double y = FractalGenerator.getCoord(range.y, range.y + range.width, displaySize, e.getY());
            fractalGenerator.recenterAndZoomRange(range, x, y, 0.5);
            drawFractal();
        }
    }
}
