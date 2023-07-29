package lab1;

public class Point3d {	
	private double x, y, z;
	
	Point3d (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	Point3d() {
		this(0.0, 0.0, 0.0);
	}
	public double getX() { 
		return x;
	}
	public double getY() {
	  	return y;
	}
	public double getZ() {
		return z;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	public double distanceTo(Point3d other) {
		return Math.sqrt(this.x*other.x + this.y*other.y + this.z*other.z);
	}

}
