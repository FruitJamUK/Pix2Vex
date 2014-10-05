package main;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import svg.SvgElement;

public class ShapeIndex {
 int rgb;
 //int[] start = new int[2];
 static int width; //of shape
 static int height;//of shape
 Boolean[][] points;
 BufferedImage image;
 
 public ShapeIndex(int colour, int w, int h, BufferedImage i){
	 rgb=colour; width=w; height=h; image=i;
	 points=new Boolean[width][height];
	 
	 for(int y=0;y<height;y++){ //for each row
		 for(int x=0;x<width;x++){ //for each pixel in a row
			 points[x][y]=(image.getRGB(x,y)==colour)?true:false; //if colours match, set true
			 //System.out.print(points[x][y]+" ");
			 }
		 }//System.out.print("\n");
	 }
 
 //public int getColour(){return rgb;}
 
 public SvgElement trace(){ //finds point at which 
	 ArrayList<Edge> a = new ArrayList<Edge>(); //Edge ArrayList
	 ArrayList<Edge> bin = new ArrayList<Edge>(); //Edges to be binned from ArrayList
	 ArrayList<Point> p = new ArrayList<Point>(); //Points sequence recorded from edges
	 ArrayList<ArrayList<Point>> s = new ArrayList<ArrayList<Point>>(); //SvgElements of this colour
	 
	 //if top row/false = top edge follow edge	 
	 for(int y=0;y<height;y++){ //for each row
		 for(int x=0;x<width;x++){ //for each pixel in a row
			 //System.out.println(x+","+y+","+points[x][y]);
			if(points[x][y]){ //if pixel exists
			 if(x==0)a.add(new Edge(x,y,x,y+1)); //leftmost column
			 	else if(!points[x-1][y])a.add(new Edge(x,y,x,y+1));//check left
			 if(x==width-1)a.add(new Edge(x+1,y,x+1,y+1)); //rightmost column
			 	else if(!points[x+1][y])a.add(new Edge(x+1,y,x+1,y+1));//check right		 

			 if(y==0)a.add(new Edge(x,y,x+1,y)); //topmost row
				else if(!points[x][y-1])a.add(new Edge(x,y,x+1,y));//check up
			 if(y==height-1)a.add(new Edge(x,y+1,x+1,y+1)); //bottommost row
				else if(!points[x][y+1])a.add(new Edge(x,y+1,x+1,y+1));//check down
			 }
		 	}
		 }
	 
	 //arraylist of pairs of points of top, bottom, left, right, compare points
	 
	 for (Edge e: a) {
		 for(Edge f: a){
			 if(e.append(f)) bin.add(f);}//if appended, added to the bin
		}
		 a.removeAll(bin);bin.removeAll(bin);
		 
	 int aSize=a.size();
	 int index=0;
	 //identify point sequence
		 //for (int i=0;i<aSize;i++) { //last edge ignored, as final points should match up
	 	while(!a.isEmpty()){
	 		int size=p.size();
	 		//int index=0;
			 for(Edge e:a){
				 if(p.size()==index){//p.isEmpty()){
					 p.add(e.getFirst()); System.out.print(e.getFirst());
					 p.add(e.getSecond());System.out.println(e.getSecond());
					 bin.add(e);}
				 
				 else if(p.get(p.size()-1).equals(e.getFirst())){
					 p.add(e.getSecond()); System.out.println(e.getFirst());
					 bin.add(e);}
				 else if(p.get(p.size()-1).equals(e.getSecond())){
					 p.add(e.getFirst()); System.out.println(e.getSecond());
					 bin.add(e);}
				 }
			 a.removeAll(bin);
			 //if(size==p.size()){
				 //s.add(p);
				 //p.removeAll(p);}
			 if(size==p.size()){ //ie, there has been no change
				 ArrayList<Point> shape = new ArrayList<Point>(p.subList(index, size));
				 s.add(shape);
				 index=size; //therefore new shape is counted
			 }
			}
	 
	 //new SvgElement
		 //s=new SvgElement(p,rgb);
	 return new SvgElement(s,rgb);}
}
 
 
 /*public static void add(coords){
	 //if coloured point is further left than 0, width++, start[0]--,
 }
 
 public static void discover(image , colour){}
}*/
