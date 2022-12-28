import java.awt.*;
import java.awt.event.*;
import jm.JMC;
import jm.music.data.*;
import jm.util.*;
import javax.swing.*;

public class GUI extends JFrame {
	
	JButton composeBtn, saveBtn, loadBtn;
	JButton showBtn, notateBtn, sketchBtn, histogramBtn, printBtn;
	JLabel welcomeMsg;
	JMenuBar menubar;
	JMenu file;
	JMenuItem reset, exit;
	
	
	public static void main(String [] args) {
		GUI gui = new GUI();
		gui.setVisible(true);
		gui.setSize(400,300);
		gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gui.setTitle("Melodic Idea Generator");
	}
	
	public GUI() {
		Container pane = this.getContentPane();
		pane.setLayout(new GridLayout(3,1,20,20));
		
		menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		file = new JMenu("File");
		menubar.add(file);
		
		exit = new JMenuItem ("Quit");
		file.add(exit);
		
		JPanel welcomeLine = new JPanel(); 
		welcomeLine.setBackground(Color.gray);  
		welcomeLine.setLayout(new GridLayout(1,1,20,20));
		JPanel mainBtns = new JPanel();
		mainBtns.setBackground(Color.black); 
		mainBtns.setLayout(new GridLayout(1,3,20,20));
		
		
		welcomeMsg = new JLabel(asHtml("Welcome to the Melodic Idea Generator!"), SwingConstants.CENTER);
		welcomeLine.add(welcomeMsg);
		
		composeBtn = new JButton("Compose");
		mainBtns.add(composeBtn);
		
		saveBtn = new JButton("Save");
		mainBtns.add(saveBtn);
		
		loadBtn = new JButton("Load");
		mainBtns.add(loadBtn);
		
		pane.add(welcomeLine);
		pane.add(mainBtns);
		
		event e = new event();
		exit ex = new exit();
		
		exit.addActionListener(ex);
		composeBtn.addActionListener(e);
		saveBtn.addActionListener(e);
		loadBtn.addActionListener(e);
		
		
	}
	
	public class event implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
		
	}
	
	private String asHtml(String text) {
	    return "<HTML>" + text.replace("\n", "<br>") + "</HTML>";
	}
	
	public class exit implements ActionListener {
		public void actionPerformed(ActionEvent ex) {
			System.exit(0);
		}
	}
	
	
	
}
