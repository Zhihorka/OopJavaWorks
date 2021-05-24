import java.util.Objects;

public class Location
{
    public int xCoord;
    public int yCoord;

    public Location(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    public Location()
    {
        this(0, 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Location location = (Location) obj;
        return xCoord == location.xCoord && yCoord == location.yCoord;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + xCoord;
        result = 37 * result + yCoord;
        return result;
    }
}
