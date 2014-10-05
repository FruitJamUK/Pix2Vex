package main;

import java.awt.Point;

public /*abstract*/ class Edge {
private Point firstPoint; //add getters and setters
private Point secondPoint;
private Boolean vertical;
//private Edge nextEdge;
public Edge (int ax, int ay, int bx, int by){
	firstPoint=new Point(ax,ay);
	secondPoint=new Point(bx,by);
	vertical=(ax==bx);
}

public Boolean isVertical(){return vertical;}
public Point getFirst(){return firstPoint;}
public Point getSecond(){return secondPoint;}

public Boolean append(Edge e){ //Boolean returned so the user knows whether to drop the second edge or not
	if(vertical==e.isVertical()){
		if(firstPoint.equals(e.getSecond())){
			firstPoint=e.getFirst();
			return true;}
		else if(secondPoint.equals(e.getFirst())){
			secondPoint=e.getSecond();
			return true;}
		}
	return false;
}

//public void attach(Edge e){}
}
