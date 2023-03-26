//Hamza Bouzoubaa 300259902

public class Point3D {
    //Instance variables
    private double x;
    private double y;
    private double z;

    int label = 0;
//Point3D constructor, Assigns Coordinates x,y,z for each POINT3D
    public Point3D(double X,double Y,double Z){
        x= X;
        y= Y;
        z= Z;
    }

// Getter method for Point3D, this method return X
    public double getX(){
        return x;
    }

    // Getter method for Point3D, this method return Y
    public double getY(){
        return y;
    }

    // getter method for Point3D, this method return Z
    public double getZ(){
        return z;
    }


    //  this method returns the distance between two points.
    public double distance(Point3D pt){
        return Math.sqrt(((this.x - pt.getX())*(this.x - pt.getX())) + ((this.y - pt.getY())*(this.y - pt.getY()))+ ((this.z - pt.getZ())*(this.z - pt.getZ())));
    }

    // Print  method, changes the way Point3D objects are printed
    public String toString(){
        return ("Point3D "+x+" , "+ y+" , "+z);
    }


}
