import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *  The initial use of the brush 
 * 
 * @author M_WBCG
 * 
 */
public class Contours extends JFrame implements MouseListener {
	Graphics g;
	//  Initialization of coordinates starting and ending points 
	int x1, y1, x2, y2;

	public Contours() {
		// TODO Auto-generated constructor stub
		//  Create the palette to get the brush 
		JPanel drawPanel = new JPanel();
		//  Add a panel to a form 
		add(drawPanel);
		setTitle("Melodic Contours");
		setSize(500, 500);
		setVisible(true);
		//  The brush is acquired after it is visible , Set to global properties 
		g = drawPanel.getGraphics();
		drawPanel.addMouseListener(this);
	}

	public static void main(String[] args) {
		new Contours();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		x1 = e.getX();
		y1 = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		x2 = e.getX();
		y2 = e.getY();
		g.drawLine(x1, y1, x2, y2);
		x1 = x2 = y1 = y2 = 0;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}