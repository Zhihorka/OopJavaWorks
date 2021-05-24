import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


//класс для алгоритма AStar
public class AStarApp {
    //количество ячеек
    private int width;
    private int height;
    //стартовая локация
    private Location startLoc;
    //точка назначения
    private Location finishLoc;
    //карта из ячеек
    private JMapCell[][] mapCells;

    
    //обработчик событий кликов на карту ячеек
    private class MapCellHandler implements MouseListener {
        //нажата ли клавиша мыши true/false
        private boolean modifying;
        //является ли ячейка стеной ?
        private boolean makePassable;
        

        public void mousePressed(MouseEvent e) {
            modifying = true;
            JMapCell cell = (JMapCell) e.getSource();
            //если мы кликаем по стенке - она перестает быть стеной
            //если кликаем по пустому метсу - оно становится стенкой
            makePassable = !cell.isPassable();
            cell.setPassable(makePassable);
        }

        public void mouseReleased(MouseEvent e) {
            modifying = false;
        }
        
        //чтобы зажать мышь и непрерывно рисовать стены
        public void mouseEntered(MouseEvent e) {
            if (modifying){
                JMapCell cell = (JMapCell) e.getSource();
                cell.setPassable(makePassable);
            }
        }


        public void mouseExited(MouseEvent e){
            // ??написано не обращать внимание
        }

        public void mouseClicked(MouseEvent e){
            // ??написано не обращать внимание
        }
    }
    
    
    //для того чтобы созздавать карты из ячеек заданого размера
    public AStarApp(int w, int h) {
        if (w <= 0)
            throw new IllegalArgumentException("w must be > 0; got " + w);
        
        if (h <= 0)
            throw new IllegalArgumentException("h must be > 0; got " + h);
        
        width = w;
        height = h;
        startLoc = new Location( 5,  5);
        finishLoc = new Location(w -5, h- 5);
    }
    
    
    //для интерфейса
    private void initGUI() {
        JFrame frame = new JFrame("AStar 2021 супер алгоритм");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        GridBagLayout gbLayout = new GridBagLayout();
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.BOTH;
        gbConstraints.weightx = 1;
        gbConstraints.weighty = 1;
        gbConstraints.insets.set(0, 0, 1, 1);
        JPanel mapPanel = new JPanel(gbLayout);
        mapPanel.setBackground(Color.GRAY);
        mapCells = new JMapCell[width][height];
        MapCellHandler cellHandler = new MapCellHandler();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapCells[x][y] = new JMapCell();
                gbConstraints.gridx = x;
                gbConstraints.gridy = y;
                gbLayout.setConstraints(mapCells[x][y], gbConstraints);
                mapPanel.add(mapCells[x][y]);
                mapCells[x][y].addMouseListener(cellHandler);
            }
        }
        
        contentPane.add(mapPanel, BorderLayout.CENTER);
        JButton findPathButton = new JButton("Рассчитать путь");
        findPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { findAndShowPath(); }
        });
        
        contentPane.add(findPathButton, BorderLayout.SOUTH);
        
        frame.pack();
        frame.setVisible(true);

        mapCells[startLoc.xCoord][startLoc.yCoord].setEndpoint(true);
        mapCells[finishLoc.xCoord][finishLoc.yCoord].setEndpoint(true);
    }

    //создаем поле с ячейками и алгоритмом поиска
    private void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { initGUI(); }
        });
    }
    

    private void findAndShowPath() {
        Map2D map = new Map2D(width, height);
        map.setStart(startLoc);
        map.setFinish(finishLoc);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapCells[x][y].setPath(false);

                if (mapCells[x][y].isPassable())
                    map.setCellValue(x, y, 0);
                else
                    map.setCellValue(x, y, Integer.MAX_VALUE);
            }
        }
        
        //если путь найден, то можно отметить все точки
        Waypoint wp = AStarPathfinder.computePath(map);
        while (wp != null) {
            Location loc = wp.getLocation();
            mapCells[loc.xCoord][loc.yCoord].setPath(true);
            
            wp = wp.getPrevious();
        }
    }
    
    
    //входная точка для программы
    public static void main(String[] args) {
        AStarApp app = new AStarApp(30, 30);
        app.start();
    }   
}
