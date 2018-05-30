package csi403;

//Class used like a point on a graph (x,y)
public class Point {

	private int x;
	private int y;
	
	//Default Constructor here for Json mapping recognition
	public Point() {
		
	}
	
	//Constructor that takes x and y coordinates as ints
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//Accessor for x
	public int getX() {
		return x;
	}

	//Mutator for x
	public void setX(int x) {
		this.x = x;
	}

	//Accessor for y
	public int getY() {
		return y;
	}

	//Mutator for y
	public void setY(int y) {
		this.y = y;
	}
}
