package draw;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Canvas extends JFrame {
	Container c;
	Board b;
	JButton draw;
	JMenuBar menubar;
	JMenu file, view;
	JMenuItem clear, exit, lightMode, darkMode, fullscreen;
	
	static boolean cl = true;
	static boolean dm = true;
	static boolean db = false;
	static boolean gr = false;
	
	public Canvas() {
		c = getContentPane();
		b = new Board();
		
		//jmenubar
		menubar = new JMenuBar();
        setJMenuBar(menubar);
        file = new JMenu("File");
        clear = new JMenuItem("Clear");
        exit = new JMenuItem("Quit");
        view = new JMenu("View");
        lightMode = new JMenuItem("Light Mode");
        darkMode = new JMenuItem("Dark Mode");
        fullscreen = new JMenuItem("Fullscreen");
		
		//buttons
		draw = new JButton("Draw");
		
		//listener
		clear.addActionListener(new BListener());
        exit.addActionListener(new BListener());
        lightMode.addActionListener(new BListener());
        darkMode.addActionListener(new BListener());
		draw.addActionListener(new BListener());
		
		//add
		menubar.add(file);
		file.add(clear);
		file.add(exit);
		menubar.add(view);
		view.add(lightMode);
		view.add(darkMode);
		view.add(fullscreen);
		b.add(draw);
		c.add(b);
	}
	
	//Listener
	class BListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clear) {
				cl = true;
				System.exit(0);
				Canvas frame = new Canvas();
				frame.setTitle("God please just work");
				frame.setVisible(true);
				frame.setSize(1200, 600);
				frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			}
			if (e.getSource() == exit) {
				System.exit(0);
			}
			if (e.getSource() == lightMode) {
				dm = false;
			}
			if (e.getSource() == darkMode) {
				dm = true;
			}
			if (e.getSource() == draw && db == false) {
				db = true;
				draw.setText("Pause");
			} else if (e.getSource() == draw && db == true) {
				db = false;
				draw.setText("Draw");
			}
			b.repaint();
		}
	}
	
	public static void main(String[] args) {
		Canvas frame = new Canvas();
		frame.setTitle("Contour Editor");
		frame.setVisible(true);
		frame.setSize(1200, 600);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}