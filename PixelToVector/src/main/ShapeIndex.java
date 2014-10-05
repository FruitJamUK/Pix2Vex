package main;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import svg.SvgElement;

public class ShapeIndex {
 int rgb; //colour of shapes to be found
 static int width; //of shape
 static int height;//of shape
 Boolean[][] points;//used to record whether pixels in the picture are of the same colour as 'rgb' or not
 BufferedImage image;
 
 public ShapeIndex(int colour, int w, int h, BufferedImage i){
	 rgb=colour; width=w; height=h; image=i;
	 points=new Boolean[width][height];
	 
	 for(int y=0;y<height;y++){ //for each row
		 for(int x=0;x<width;x++){ //for each pixel in a row
			 points[x][y]=(image.getRGB(x,y)==colour)?true:false; //if colours match, set true
			 }
		 }
	 }
 
 public SvgElement trace(){ //finds point at which 
	 ArrayList<Edge> a = new ArrayList<Edge>(); //Edge ArrayList
	 ArrayList<Edge> bin = new ArrayList<Edge>(); //Edges to be binned from ArrayList
	 ArrayList<Point> p = new ArrayList<Point>(); //Points sequence recorded from edges
	 ArrayList<ArrayList<Point>> s = new ArrayList<ArrayList<Point>>(); //shapes of this colour
	 
	 //check edges of pixels to define shape edges
	 for(int y=0;y<height;y++){ //for each row
		 for(int x=0;x<width;x++){ //for each pixel in a row
			 
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
	 
	 //append touching vertical/horizontal edges to each other
	 for (Edge e: a) {
		if(!bin.contains(e)){
		 for(Edge f: a){
			if(!bin.contains(f)){
			 if(e.append(f)) bin.add(f);}//if appended, added to the bin
		 	}
		  }
		}
		 a.removeAll(bin);bin.removeAll(bin);
		 
	 //identify point sequences of shapes
		int index=0; //used to mark the start point of each shape
		int size; //used to record the size of 'p' to see if anything has been added
	 	while(!a.isEmpty()){
	 		 size=p.size();
			 for(Edge e:a){
				 if(p.size()==index){
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
			 a.removeAll(bin); bin.removeAll(bin);//removes all 'used' sides
			 if(size==p.size()){ //ie, there has been no change
				 ArrayList<Point> shape = new ArrayList<Point>(p.subList(index, size));
				 s.add(shape);
				 index=size; //therefore new shape is counted
			 }
			}
	 		s.add( new ArrayList<Point>(p.subList(index, p.size()))); //final shape
	 
	 //new SvgElement giving the list of shapes and colour of the shapes
	 return new SvgElement(s,rgb);}
}
