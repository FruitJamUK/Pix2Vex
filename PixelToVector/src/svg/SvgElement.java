package svg;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class SvgElement {
	private StringBuilder svg= new StringBuilder();
	private final String start="<polygon points=\" ";
	private final String style="\" style=\"fill:rgb(";
	private final String end=");stroke-width:0\" />";
	
 public SvgElement(ArrayList<ArrayList<Point>> shapes, int colour){
	 System.out.println(shapes.size());
	 for(ArrayList<Point> points:shapes){ //for each shape
	 svg.append(start);
	 //add points
	 for(Point p:points){svg.append(p.x+","+p.y+" ");}
	 svg.append(style);
	 //add colour
	 svg.append((colour >> 16 & 0xff) +","+ //red
				(colour >> 8 & 0xff) +","+	//green
				(colour & 0xff) 			//blue
				);
	 svg.append(end);
 }
 }

 public String getText(){return svg.toString();}
}
