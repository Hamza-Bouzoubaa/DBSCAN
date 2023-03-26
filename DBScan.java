import java.io.*;
import java.util.*;


//Hamza Bouzoubaa 300259902
public class DBScan {
// Instance variables
    private double Eps;
    private int MinPts;

    private int Clusters;
    private List<Point3D> DB;

    //Constructor for DBscan, takes as parameter a list of Point3D to analyse.
    public DBScan (List<Point3D> db){
        DB = db;
    }

// Setter method, this method sets the value of Eps
    public double setEps(double eps){
        Eps =eps;
        return Eps;
    }
    // Setter method, this method sets the value of MinPts
    public double setMinPts(int minPts){
        MinPts =minPts;
        return MinPts;
    }
// Getter method, this method returns the total number of Clusters found
    public int getNumberOfClusters(){
        return Clusters;
    }

    // Getter method, this method returns the list of Point3D
    public List<Point3D> getPoints(){
        return DB;
    }

    // This method read a CSV file that you specify with a path, the data in the file
    // is then transformed in a list of Point3D that will be later used to find clusters
    public static List<Point3D> read(String filename) throws FileNotFoundException {
        List<Point3D> db = new ArrayList<Point3D>();

        Point3D p;  // Creating of a Point3D variable
        Scanner sc = new Scanner(new File("C:\\Users\\hamza\\Uottawa\\CSI\\Devoir prog\\"+filename+".csv")); //Finding the CSV file
        sc.useDelimiter("\\r\\n|\\n|\\r"); //Separating the CSV file in blocks of X,Y,Z
        while (sc.hasNext()) {
            //System.out.print(sc.next()+"///");

            String[] z = sc.next().split(",");// Separating the X,Y,Z in 3 parts X Y and Z

            // Building a Point3D object for each X Y Z combination
            try {
                p= new Point3D(Double.parseDouble(z[0]),Double.parseDouble(z[1]),Double.parseDouble(z[2]));
                db.add(p);
            }
            catch (Exception E){
                continue;
            }




        }
        sc.close();

        // Returns a list of Point3D
        return db;
    }


    // This method takes the List of Point3D created in read method and finds clusters
    public void findCluster(){

        // Running a loop on the size of the List of Point3D
        for (int i = 0; i < DB.size(); i++) {
            Point3D point = DB.get(i);                              // Taking each Point3D in the List
            if (point.label == 0){                                 // Making sure that the Point3D hasn't already been assigned to a cluster
                NearestNeighbors Ne = new NearestNeighbors(DB);  // Creating an instance of NearestNeighbors class
                List<Point3D> N = Ne.rangeQuery(point, Eps);    // Finding Neighbors of each Point3D
                if (N.size()< MinPts){                          // Making sure that the group of Neighbors is at least equal to the MinPts
                    point.label = -1;                           // Groups with less than MinPts are marked as Noise
                    continue;
                }
                Clusters++;                                     // Incrementing the cluster number
                point.label = Clusters;                         // assigning a label to The Neighbors with at least MinPts
                Stack<Point3D> S = new Stack<>();               // Creating a stack
                for (int j = 0; j < N.size(); j++) {
                    S.push(N.get(j));                           // adding all the Point3D Neighbors in the stack
                }
                while  (S.size()>0){

                    Point3D Q = S.pop();
                    if (Q.label != 0 && Q.label != -1){         // Making sure that the Point3D hasn't already been assigned to a cluster
                        continue;
                    }

                    Q.label=Clusters;
                    Ne = new NearestNeighbors(DB);          //Finding Neighbors of Neighbors
                    N = Ne.rangeQuery(Q, Eps);
                    if (N.size()>=MinPts){                      // Making sure that the group of Neighbors is at least equal to the MinPts
                        for (int j = 0; j < N.size(); j++) {
                            S.push(N.get(j));               // Adding the new Neighbors to the Stack
                        }
                    }
                }
            }
        }
    }

    public List<Float>  random_RGB(){
        List a = new ArrayList<Float>();    // Creating a list of Floats

        for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            // Generating 3 Random Floats
            float int_random = rand.nextFloat(1);
            a.add(int_random);
        }
        return a; // Returns a List of 3 randomly generated floats.
    }


    public List<List<Integer>> clusterSize(List<Point3D> db){
        List<List<Integer>> ClusterSize= new ArrayList<>();


        for (int i = 0; i <= Clusters; i++) {
            List<Integer> Toadd = new ArrayList<>();
            int counter = 0;
            int l = 0;
            for (int k = 0; k < db.size() - 1; k++) {

                if (db.get(k).label == i) {
                    counter++;
                }

            }
            Toadd.add(i);
            Toadd.add(counter);
            ClusterSize.add(Toadd);

        }





        return ClusterSize;
    }

    // This method helps save the Point3D in a new CSV file
    public void save (String filename) throws FileNotFoundException {

        List<List<Float>> RGB = new ArrayList<>();        // List of List of Floats

        for (int i = 0; i <= Clusters; i++) {

            RGB.add(random_RGB());                      // Adding to the List 3 Random floats
        }
        List<Integer[]> data = new ArrayList<Integer[]>();
        File csvFile = new File(filename+".csv");  // Creating a new CSV file
        PrintWriter out = new PrintWriter(csvFile);

        out.write("x"+ ",");
        out.write("y"+ ",");
        out.write("z"+ ",");
        out.write("C"+ ",");                // Writing on the CSV file
        out.write("R"+ ",");
        out.write("G"+ ",");
        out.write("B"+ ",");
        out.write(System.lineSeparator());





        for (int i = 0; i < DB.size(); i++) {
            if (DB.get(i).label!=-1) {
                out.write(DB.get(i).getX()+ ",");   // Adding to the CSV file X for each Point3D
                out.write(DB.get(i).getY()+ ",");   // Adding to the CSV file Y for each Point3D
                out.write(DB.get(i).getZ()+ ",");   // Adding to the CSV file Z for each Point3D
                out.write(DB.get(i).label+ ",");    // Adding to the CSV file Label of each Point3D

                out.write(RGB.get(DB.get(i).label).get(0)+ ",");   // Adding to the CSV file R color of each Point3D
                out.write(RGB.get(DB.get(i).label).get(1)+ ",");    // Adding to the CSV file G color of each Point3D
                out.write(RGB.get(DB.get(i).label).get(2)+ ",");    // Adding to the CSV file B color of each Point3D
                out.write(System.lineSeparator());
            }




        }
            // Closing the file
        out.close();

    }



}
