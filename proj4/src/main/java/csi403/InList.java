package csi403;

import java.util.ArrayList;

//Class mostly used for Json object mapping of the input
//Expected input is {"inList": {"x":3,"y":5}, ....}
public class InList {
	
	private ArrayList<Point> inList = new ArrayList<Point>();
	
	//Defaule Constructor
	public InList() {
		
	}
	
	//Mutator
	public void setInList(ArrayList<Point> list) {
		inList = list;
	}
	
	//Accessor
	public ArrayList<Point> getInList() {
		return inList;
	}
}
