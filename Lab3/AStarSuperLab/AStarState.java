import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinWorkerThread;

public class AStarState
{
    private Map2D map;
    private HashMap<Location, Waypoint> openWaypoints = new HashMap<>();
    private HashMap<Location, Waypoint> closeWaypoints = new HashMap<>();

    public AStarState(Map2D map) {
        if (map == null)
            throw new NullPointerException("map cannot be null");
        this.map = map;
    }


    public Map2D getMap()
    {
        return map;
    }

    public Waypoint getMinOpenWaypoint() {
        if (numOpenWaypoints() == 0) {
            return null;
        }
        Waypoint minWaypoint = null;
        float min = Float.MAX_VALUE;
        for (Waypoint waypoint : openWaypoints.values()) {
            float cost = waypoint.getTotalCost();
            if (cost < min) {
                min = cost;
                minWaypoint = waypoint;
            }
        }
        return minWaypoint;
    }


    public boolean addOpenWaypoint(Waypoint newWP) {
        Waypoint openWP = openWaypoints.get(newWP.loc);

        if (openWP == null || newWP.getPreviousCost() < openWP.getPreviousCost()) {
            openWaypoints.put(newWP.loc, newWP);
            return true;
        }
        return false;
    }


    public int numOpenWaypoints()
    {
        return openWaypoints.size();
    }

    public void closeWaypoint(Location loc) {
        Waypoint waypoint = openWaypoints.remove(loc);
        if (openWaypoints != null) {
            closeWaypoints.put(loc, waypoint);
        }
    }
    public boolean isLocationClosed(Location loc)
    {
        return closeWaypoints.containsKey(loc);
    }
}

