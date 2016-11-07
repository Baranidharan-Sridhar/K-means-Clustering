import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;


public class km {
public static void main(String args[]) throws IOException{
	int k= 3;
	//Scanner iFile = new Scanner(new FileReader("C:\\Users\\barae\\Desktop\\data.txt"));
	 BufferedReader br = new BufferedReader(new FileReader("data.txt"));
     String line=null;
     ArrayList<Points> dataList= new ArrayList<Points>();
     ArrayList<Points> centroid = new ArrayList<Points>();
     while( (line=br.readLine()) != null) {
         Points pt = new Points();
    	 String [] s= line.split("	");
    	 pt.setX(Double.parseDouble(s[0]));
    	 pt.setY(Double.parseDouble(s[1]));
    	 pt.setZ(Double.parseDouble(s[2]));
    	 pt.setW(Double.parseDouble(s[3]));
    	 dataList.add(pt);
     }
     centroid.add(dataList.get(0));
     centroid.add(dataList.get(100));
     centroid.add(dataList.get(200));
	
     int d=1;
    while(d==1){
     for(int i=0;i<dataList.size();i++){
    	 double min=9999.00;
    	 for(int j=0;j<centroid.size();j++){
    		 double dist= distance(dataList.get(i),centroid.get(j));
    		 if(dist<min){
    			 min= dist;
    			 dataList.get(i).setClusterNo(j);
    		 }
    	 }
     }
     
    		 centroidCalc(dataList,centroid);
    		 
    			 if(centroid.get(0).getX()!=centroid.get(3).getX()){
    				 d=1;
    			 }
    			 else{
    				 d=0;
    			 }
    			 centroid.remove(0);centroid.remove(1);centroid.remove(2);
    			
    			}	
    System.out.println("Clustering the seed dataset...");
    for(int i=0;i<dataList.size();i++)
    	System.out.println(dataList.get(i).x+" "+dataList.get(i).y+" "+dataList.get(i).z+" "+dataList.get(i).w+" "+dataList.get(i).getClusterNo());
    
    ArrayList<Points> testData = new ArrayList<Points>();
    
    	Random rand = new Random();
    	int record= rand.nextInt(210);
    	Points dat= dataList.get(record);
    	dataList.remove(record);
    knn(dat,dataList);
}

private static void knn(Points dat, ArrayList<Points> dataList) {
	
	int K = 5;           // K Neighbor
	
    int nAttrib = 4;      // Number of Attributes or Features
    int nTestData = 209;    // Number of Test Data
    Vector<DataObject> data = new Vector<DataObject>();
    for(int i = 0; i < nTestData; i++) {
        DataObject obj = new DataObject(nAttrib);

        
        obj.attributes[0] = dataList.get(i).getX();
        obj.attributes[1] = dataList.get(i).getY();
        obj.attributes[2] = dataList.get(i).getZ();
        obj.attributes[3] = dataList.get(i).getW();

        // Read the classification of the object
        obj.c =String.valueOf(dataList.get(i).getClusterNo());

        data.add(obj);
    }

    // Read the instance object
    DataObject inst = new DataObject(nAttrib);
    
    inst.attributes[0] = dat.getX();
    inst.attributes[1] = dat.getY();
    inst.attributes[2] = dat.getZ();
    inst.attributes[3] = dat.getW();

    // Compute for the Distance of all the Test Data
    for(int i = 0; i < nTestData; i++)
        for(int j = 0; j < nAttrib; j++)
            data.elementAt(i).dist += Math.pow(data.elementAt(i).attributes[j]-inst.attributes[j], 2);
    		
    // Sort all the test data according to distance
    
    Collections.sort(data);

    //for(int i = 0; i < nTestData; i++)
    //  System.out.println(data.elementAt(i));

    // Rank all the K neighbors
    
    ArrayList<String> list = new ArrayList<String>();
    HashMap<String,Integer> map = new HashMap<String,Integer>();
    double val = data.elementAt(0).dist;
    for(int i = 0, rank = 1; i < nTestData && rank <= K; i++) {
        if(val < data.elementAt(i).dist) rank++;
        //System.out.println(data.elementAt(i)+" "+ rank);}
        //******
        //gMode.add(data.elementAt(i).c);
       list.add(data.elementAt(i).c);
      /* System.out.println(list.get(i)+ " "+rank);
        if(map.containsKey(data.elementAt(i).c)){
        	map.put(data.elementAt(i).c,map.get(data.elementAt(i).c)+1);
        }
        else{
        	map.put(data.elementAt(i).c,0);
        }*/
        //System.out.println(data[i] + " " + rank);
    }
    
    
    // Classify the new object
    // If the classification is qualitative, use the MODE
   inst.c = getMode(list);        // Find the mode of the neighbors
   // inst.c = getMode(list);
    // If the classification is quantitative, use the AVERAGE
    //inst.c = getAverage(gMode);       // Find the average of the neighbors

    System.out.println("The new object is classified as: ");
    System.out.println(inst);}
public static String getMode(ArrayList<String> data) {
    HashMap<String, Integer> dict = new HashMap<String, Integer>();
    for(int i = 0; i < data.size(); i++) {
        String d = data.get(i);

        if(dict.containsKey(d))
            dict.put(d, dict.get(d)+1);
        else
            dict.put(d, 1);
    }

    String maxMode = "";
    int maxCount = 0;
    Set<String> keys = dict.keySet();
    for(String d : keys) {
        //System.out.println("Key: " + d + " : " + dict.get(d));
        int tCount = dict.get(d);
        if(tCount > maxCount) {
            maxCount = tCount;
            maxMode = d;
            //System.out.println("\tSetting mode to " + d);
        }
    }

    return maxMode;
}

public static double getAverage(Vector<Double> data) {
    double sum = 0.0;

    for(int i = 0; i < data.size(); i++)
        sum = sum + data.elementAt(i);

    return sum / data.size();
}
private static ArrayList<Points> centroidCalc(ArrayList<Points> dataList,
		ArrayList<Points> centroid) {
	double x=0;double y=0;double z=0;double w=0;int counter=0;int counter1=0;int counter2=0;
	double x1=0;double y1=0;double z1=0;double w1=0;
	double x2=0;double y2=0;double z2=0;double w2=0;
	for(int i=0;i<dataList.size();i++){
	if(dataList.get(i).getClusterNo()==0){
		x+=dataList.get(i).getX();
		y+=dataList.get(i).getY();
		z+=dataList.get(i).getZ();
		w+=dataList.get(i).getW();
		counter++;
	}
	if(dataList.get(i).getClusterNo()==1){
		x1+=dataList.get(i).getX();
		y1+=dataList.get(i).getY();
		z1+=dataList.get(i).getZ();
		w1+=dataList.get(i).getW();
		counter1++;
	}
	if(dataList.get(i).getClusterNo()==2){
		x2+=dataList.get(i).getX();
		y2+=dataList.get(i).getY();
		z2+=dataList.get(i).getZ();
		w2+=dataList.get(i).getW();
		counter2++;
	}
	}
	x=(double)x/counter;y=(double)y/counter;z=(double)z/counter;w=(double)w/counter;
	x1=(double)x1/counter1;y1=(double)y1/counter1;z1=(double)z1/counter1;w1=(double)w/counter1;
	x2=(double)x2/counter2;y2=(double)y2/counter2;z2=(double)z2/counter2;w2=(double)w2/counter2;
	Points point= new Points(x,y,z,w);Points point1= new Points(x1,y1,z1,w1);Points point2= new Points(x2,y2,z2,w2);
	centroid.add(point);centroid.add(point1);centroid.add(point2);
	return centroid;
	
}

private static double distance(Points points, Points points2) {
	return Math.sqrt(Math.pow((points.getX() - points2.getX()), 2)+Math.pow((points.getY() - points2.getY()), 2)+
			Math.pow((points.getZ() - points2.getZ()), 2)+Math.pow((points.getW() - points2.getW()), 2));
	

}
}


class DataObject implements Comparable<DataObject> {

public double attributes[];
public double dist;
String c;

public DataObject(int nAttrib) {
    this.attributes = new double[nAttrib];
    this.dist = 0;
    this.c = "";
}

public int compareTo(DataObject ob) {
    return Double.compare(this.dist, ob.dist);
}

public String toString() {
    String out = new String();

    for(int i = 0; i < attributes.length; i++)
        out = out + attributes[i] + " ";

    return out + this.c;
}
}	



