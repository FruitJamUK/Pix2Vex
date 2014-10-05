package main;
//Scalable Pixel Art converter
//Author: David Sharp

import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import svg.SvgElement;

public class Pix2Vex {
	static BufferedImage image = null;
	static String imageLocation = null; //in file system
	static ColorModel imageCM = null; //colour model of image
	static int height; //of image
	static int width;  //of image
	static int x; //pixel locations
	static int y; //pixel locations
	
	static int multiplier = 1;
	
	static String svg = "";
	
	static ArrayList<ShapeIndex> aList = new ArrayList<ShapeIndex>();
	static HashMap<Integer,ShapeIndex> map = new HashMap<Integer,ShapeIndex>();
	
public static void main(String[] args){
	if(args.length<=0) {System.out.println("Need a file to convert as arg"); return;} //need file to convert
		
		imageLocation = args[0]; // what if not String?
		try{image=ImageIO.read(new File(imageLocation));}
		catch(IOException e){System.out.println("error:"+e); return;}
		
		height = image.getHeight();
		width = image.getWidth();
		svg = "<?xml version=\"1.0\" encoding=\"utf-8\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">"
				+"<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" height=\""+height*multiplier+"\" width=\""+width*multiplier+"\">";
		
		imageCM = image.getColorModel();
		
		//StringBuffer svg //add elements to this
		
		/*int[][] pixelArray = new int[width][height]; //empty. replace with copying colours to svg? no need to store...
		for(int[] line: pixelArray){
			for(int pixel: line){}
		}*/
		
		/*int currentPixel=0;
		//int[] rgb=new int[3];
		int rgb;
		for(int x=0;x<height;x++){
			//if(x>0)currentPixel++;
			for(int y=0;y<width;y++){
				if(x>0)currentPixel++; //sequential through picture (0 indexed) otherwise addition necessary
				//newSvgElement(imageCM.getRed(currentPixel),imageCM.getGreen(currentPixel),imageCM.getBlue(currentPixel),imageCM.getAlpha(currentPixel),x,y);
				rgb=image.getRGB(x, y);
				newSvgElement(rgb >> 16 & 0xff, //red
							rgb >> 8 & 0xff, //green
							rgb & 0xff, //blue
							rgb >> 32 & 0xff, //alpha
							x,y);
				//int colour = image.getRGB(x,y); //colour, x, y : pump into method
				//if colour is not transparent, newSvgElement(colour,x,y); rect x y width height fill=rgb() fill-opacity
				//use color model? (width*(x+1)) + y to find pixel?
				}*/
			
		int rgb;	
		for(int y=0;y<height;y++){
				for(int x=0;x<width;x++){
					rgb=image.getRGB(x, y);
					//System.out.println("pixel rgb,x,y:"+rgb+","+x+","+y);
					
					if(!map.containsKey(rgb)){
						map.put(rgb,createShapeIndex(rgb));
						svg=svg+map.get(rgb).trace().getText();
					}
					}
		}
		save(svg,imageLocation);
		 
}

public static ShapeIndex createShapeIndex(int colour){
	ShapeIndex si = new ShapeIndex(colour, width, height, image);
	return si;
}

//public static int[] convertRGB(){return null;}

public static void newSvgElement(int r,int g,int b,int a,int x, int y) {
	svg = svg +
	"<rect"+
	" x=\""+x*5+
	"\" y=\""+y*5+
	"\" width=\"5\" height=\"5\""+
	" style=\"fill:rgb("+r+","+g+","+b+");\""+
	"/>";
}

//save (removes extension, appends new 'svg' extension), also ends svg tag
public static void save(String svg, String filepath){
	svg=svg+"</svg>";
	int x = filepath.indexOf(".");
	String newFP = filepath.substring(0, x+1) + "svg";
	
	try {
		PrintWriter writer = new PrintWriter(newFP, "UTF-8");
		writer.print(svg);
		writer.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
}

/*add: transparency, GUI, alpha layer, filename based on multiplier*/
}
