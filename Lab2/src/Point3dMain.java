public class Point3dMain {


    public static void main(String[] args) {
        Extended2dTo3d FirstPoint = new Extended2dTo3d(2.424, 6.153, 4.832);
        Extended2dTo3d SecondPoint = new Extended2dTo3d(5.012, 9.732, 2.512);
        Extended2dTo3d ThirdPoint = new Extended2dTo3d(1.612, 8.215, 10.561);
        Extended2dTo3d FirstPointShadow = new Extended2dTo3d(2.424, 6.153, 4.832);

        if (FirstPoint.comparePoints(SecondPoint) || FirstPoint.comparePoints(ThirdPoint) || SecondPoint.comparePoints(ThirdPoint)){
            System.out.println("Какие-то из точек равны между собой !");

        }else{
        System.out.println(FirstPoint.comparePoints(SecondPoint));
        System.out.println(FirstPoint.comparePoints(FirstPointShadow));
        System.out.println(FirstPoint.distanceTo(SecondPoint));
        System.out.println(FirstPoint.heronsArea(SecondPoint, ThirdPoint));
    }
    }
}