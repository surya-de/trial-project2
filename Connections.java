/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package saveLoadFile;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

/**
*
* @author kunnu
* @author Suryadeep
*/

class Connections extends JFrame{
	ArrayList<Point> moves = new ArrayList<Point>();
    public Connections(){
        
    }

    public void DotToDotConnection(Graphics2D g, int x1, int y1, int x2, int y2)
    {
        try{
            drawLines(g, x1, y1, x2, y2);
        }
        catch(Exception ex){
            
        }
    }
    public void DotToBarConnection(Graphics g, int x1, int y1, int x2, int y2)
    {
        try{
        	
        	for (int i = 0; i < moves.size(); i ++) {
        		if ((moves.get(i).x == x1 || moves.get(i).y == y1) && (moves.get(i).x == x1 || moves.get(i).y == y1)) {
        			System.out.println("Already available");
        		}
        		else {
                	moves.add(new java.awt.Point(x1, y1));
                	moves.add(new java.awt.Point(x2, y2));
                	drawLines(g, x1, y1, x2, y2);
        		}
        	}
        }
        catch(Exception ex){
            
        }
    }
    public void BarToBarConnection(Graphics2D g, int x1, int y1, int x2, int y2)
    {
        try{
        }
        catch(Exception ex){
            
        }
    }
    void drawLines(Graphics g, int _x1, int _y1, int _x2, int _y2) {
      try{
         // Graphics2D g2d = (Graphics2D) g;
      
    	  System.out.println(g);
    	  g.drawLine(_x1, _y1, _x2, _y2);
      }
      catch(Exception ex)
      {
          System.out.println(ex.getMessage());
      }
      
    }

}