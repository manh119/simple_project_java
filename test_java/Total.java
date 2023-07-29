package test_java;


public class Total {
	private int total;
	
	public Total(int t) {
		this.total = t;
	}
	
	public int getTotal() { return total; };
	
	public void add(int x) { total += x; };
	
	public void sub(int x) { total -= x; };
	
	public void clear() { total = 0; };
	
	public String toString() { return "" + this.total; }; 
}
