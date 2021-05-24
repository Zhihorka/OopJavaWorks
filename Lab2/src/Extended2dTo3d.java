import java.lang.Math;


public class Extended2dTo3d extends Point2d{
    private double xCoord;
    private double yCoord;
    private double zCoord;

    public Extended2dTo3d ( double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }

    public Extended2dTo3d () {
        //Вызовите конструктор с тремя параметрами и определите источник.
        this(0, 0,0);
    }

    //новый функционал для нового третьего измерения
    public double getZ () {
        return zCoord;
    }

    public void setZ ( double val) {
        zCoord = val;
    }


    public boolean comparePoints (Extended2dTo3d point) {
        return (xCoord == point.getX() || yCoord == point.getY() || zCoord == point.getZ());
    }

    public double distanceTo(Extended2dTo3d point) {
        return Math.sqrt(Math.pow(xCoord - point.getX(), 2) + Math.pow(yCoord - point.getY(), 2) + Math.pow(zCoord - point.getZ(), 2));
    }

    public double heronsArea(Extended2dTo3d firstPoint, Extended2dTo3d secondPoint){
        double p1 = distanceTo(firstPoint);
        double BubleX = xCoord;
        double BubleY = yCoord;
        double BubleZ = zCoord;
        double p3 = distanceTo(secondPoint);
        xCoord = firstPoint.getX();
        yCoord = firstPoint.getY();
        zCoord = firstPoint.getZ();
        double p2 = distanceTo(secondPoint);
        xCoord = BubleX;
        yCoord = BubleY;
        zCoord = BubleZ;
        double p = (p1 + p2 + p3)/2;
        return  Math.sqrt(p*(p-p1)*(p-p2)*(p-p3));
    }
}
