package csi403;

import java.util.ArrayList;

/*
*	Class that does calculations to determine what points are inside of
*	A polygon (ArrayList of points) on a 19x19 grid
*	Some functions count points on a line as Inside and others do not
*/
public class Calculate {
	
	//Method calls ContainedPointsNotOnLine with the given ArrayList of points
	//and then returns a count of all the points inside the polygon (not including ones on a line)
	public int getCountInsidePoints(ArrayList<Point> points) {
		ArrayList<Point> insidePoints = this.ContainedPointsNotOnLine(points);
		int count = 0;
		//cycle through points and increment count for each one
		for(Point p: insidePoints) {
			count++;
		}
		return count;
	}
	
	//gets the points on the grid contained within the polygon not including those on a line
	//Takes an ArrayList of points and calls ContainedPoints and then calls onLine to determine if the point is on a line
	//Returns an ArrayList of points inside the polygon that arent on a line
	public ArrayList<Point> ContainedPointsNotOnLine(ArrayList<Point> points) {
		ArrayList<Point> possiblyOnLine;
		possiblyOnLine = this.ContainedPoints(points);
		//possiblyOnline contains points inside the polygon including those on a line
		ArrayList<Point> returnArray = new ArrayList<Point>();
		
		//for each point in possiblyOnLine call onLine for each line in the polygon and if it returns true
		//for any then the point lies on a line and will not be added to returnArray
		for(Point point: possiblyOnLine) {
			boolean onLine = false;
			for(int i = 0, j = points.size() -1; i < points.size(); j = i++) {
				//call onLine. If true, mark boolean onLine as true
				if(OnLine(points.get(i), points.get(j), point)) {
					onLine = true;
				}
			}
			//if point is not on a line then add to returnArray
			if(!onLine) {
				returnArray.add(point);
			}
		}
		return returnArray;
	}
	
	//Calls contains for each possible point in a 19x19 grid and 
	//returns an ArrayList of all points that are contained inside the points (including on a line)
	public ArrayList<Point> ContainedPoints(ArrayList<Point> points) {
		ArrayList<Point> containedPoints = new ArrayList<Point>();
		
		//19x19 grid
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				Point test = new Point(i,j);
				//if point is contained then add to containedPoints
				if(contains(test, points)) {
					containedPoints.add(test);
				}
			}
		}
		return containedPoints;
	}

	//This formula is from the slides and can be found in the link below
	//https://stackoverflow.com/questions/8721406/how-to-determine-if-a-point-is-inside-a-2d-convex-polygon
	
	//Takes an ArrayList of points and returns whether or not the point is contained in the polygon
	//This includes points on a line
	public boolean contains(Point test, ArrayList<Point> points) {
		int i; int j;
		boolean result = false;
		for(i = 0, j = points.size() -1; i < points.size(); j = i++) {
			if((points.get(i).getY() > test.getY()) != (points.get(j).getY() > test.getY())
					&& (test.getX() < (double) (points.get(j).getX() - points.get(i).getX()) * (test.getY() - points.get(i).getY()) 
							/ (points.get(j).getY() - points.get(i).getY()) + points.get(i).getX())) {
				result = !result;
			}
		}
		return result;
	}
	
	//Methods below use formula to test if a point is on a line using the perpendicular dot product
	//http://www.sunshine2k.de/coding/java/PointOnLine/PointOnLine.html#step2
	
	//Calculates the perpendicular dot product of a line (Points a and b) and a point (Point b)
	public double PDP(Point a, Point b, Point c) {
		return (a.getX() - c.getX()) * (b.getY() - c.getY()) - (a.getY() - c.getY()) *
				(b.getX() - c.getX());
	}
	
	//Calculates Epsilon using a line created by Points a and be
	//Epsilon is used in comparison to the perpendicular dot product 
	public double getEpsilon(Point a, Point b) {
		int dx = b.getX() - a.getX();
		int dy = b.getY() - a.getY();
		double epsilon = 0.003 * (dx *dx + dy * dy);
		return epsilon;
	}
	
	//Takes a line (Point a and b) and a point (Point c) and determines if the point lies on the line
	//Returns true for the point being on the line and false otherwise
	public boolean OnLine(Point a, Point b, Point c) {
		//if PDP < Epsilon then the point is on the line
		return (Math.abs(PDP(a,b,c)) < this.getEpsilon(a,b));
	}
}
