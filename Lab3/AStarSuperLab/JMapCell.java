import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


public class JMapCell extends JComponent {
    private static final Dimension CELL_SIZE = new Dimension(20, 20);
    boolean endpoint = false;
    boolean passable = true;

    boolean path = false;

    public JMapCell(boolean pass) {
        setPreferredSize(CELL_SIZE);
        setPassable(pass);
    }

    public JMapCell() {
        this(true);
    }

    public void setEndpoint(boolean end) {
        endpoint = end;
        updateAppearance();
    }
    public void setPassable(boolean pass) {
        passable = pass;
        updateAppearance();
    }

    public boolean isPassable()
    {
        return passable;
    }
    public void togglePassable()
    {
        setPassable(!isPassable());
    }

    public void setPath(boolean path) {
        this.path = path;
        updateAppearance();
    }

    private void updateAppearance() {
        if (passable) {
            setBackground(Color.BLACK);
            if (endpoint)
                setBackground(Color.RED);
            else if (path)
                setBackground(Color.RED);
        }
        else {
            // Impassable cell.  Make it all red.
            setBackground(Color.ORANGE);
        }
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
