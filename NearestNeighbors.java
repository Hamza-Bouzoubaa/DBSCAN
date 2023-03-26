import java.util.ArrayList;
import java.util.List;

public class NearestNeighbors {
// Instance Variables
    private List<Point3D> DB;


    // Constructor of the class, takes as parameter a list of point3D
    public NearestNeighbors(List<Point3D> db ){
        DB = db;
    }


    // This Method is used to find all nearby point of a certain point. All Point that are the eps distance of the main point.
    public List<Point3D> rangeQuery(Point3D p, double eps){

        List<Point3D> N = new ArrayList<Point3D>();

        for (int i = 0; i < DB.size(); i++) {
            if ((p.distance(DB.get(i)))<=eps){  // finding if distance is less or equal to eps
                N.add(DB.get(i));
            }
        }


        // Returning a list of all points that are close to the main point
        return N;

    }

}
