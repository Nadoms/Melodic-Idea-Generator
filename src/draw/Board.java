package draw;

import java.awt.*;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;

import javax.swing.*;


public class Board  extends JPanel {
	private int [][]x,y;
	private int [] n;
	
	public Board() {
		n = new int[3];
		x = new int[3][10000];
		y = new int[3][10000];
		addMouseMotionListener(new MList());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (Canvas.dm) {
			GradientPaint p1 = new GradientPaint(0,0,Color.BLACK,0,300,Color.GRAY);
			g2.setPaint(p1);
			g2.fill(new Rectangle2D.Float(0,0,1200,300));
			
			GradientPaint p2 = new GradientPaint(0,300,Color.GRAY,0,600,Color.BLACK);
			g2.setPaint(p2);
			g2.fill(new Rectangle2D.Float(0,300,1200,600));
			
			g.setColor(Color.WHITE);
			g.fillRect(10,10,5,520);
			
			g.setColor(Color.WHITE);
			g.fillRect(10,260,1160,5);
			
			
		} else {
			GradientPaint p1 = new GradientPaint(0,0,Color.LIGHT_GRAY,0,300,Color.WHITE);
			g2.setPaint(p1);
			g2.fill(new Rectangle2D.Float(0,0,1200,300));
			
			GradientPaint p2 = new GradientPaint(0,300,Color.WHITE,0,600,Color.LIGHT_GRAY);
			g2.setPaint(p2);
			g2.fill(new Rectangle2D.Float(0,300,1200,600));
			
			g.setColor(Color.BLACK);
			g.fillRect(10,10,5,520);
			
			g.setColor(Color.BLACK);
			g.fillRect(10,260,1160,5);
		}
		
		if (Canvas.db) {
			g2.setStroke(new BasicStroke(6));
			if (Canvas.dm) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawPolyline(x[0],y[0],n[0]);
			// if LATEST X COORD < BIGGEST X COORD, current x coord = biggest x coord
			// START FROM ORIGIN
			// FIX DRAW/PAUSING
			// add restrictions (10<x<1170) (10<y<530)
			// grid lines & snapping
		}
		
	}
	
	class MList extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			if(Canvas.db) {
				x[0][n[0]] = e.getX();
				y[0][n[0]] = e.getY();
				n[0]++;
			}
			repaint();
		}
	}
}
