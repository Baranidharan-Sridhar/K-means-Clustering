
public class Points {
	
	 double x;
	 double y;
	 double z;
	 double w;
	 int clusterNo;
	 
	 public Points(){
		 
	 }
	 public Points(double x, double y, double z, double w){
		 this.x= x;
		 this.y=y;
		 this.z=z;
		 this.w=w;
		 
	 }
	public int getClusterNo() {
		return clusterNo;
	}
	public void setClusterNo(int clusterNo) {
		this.clusterNo = clusterNo;
	}
	public double getX() {
		return x;
	}
	public void setX(double d) {
		this.x = d;
	}
	public double getY() {
		return y;
	}
	public  void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public  void setZ(double z) {
		this.z = z;
	}
	public double getW() {
		return w;
	}
	public  void setW(double w) {
		this.w = w;
	}

}
