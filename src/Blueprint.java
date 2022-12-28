import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Blueprint extends JFrame {

	JButton apply, clear, remove;
	JLabel information, precisionLabel;
	boolean invalidLength, invalidPath, invalidEmpty;
	static boolean clearBoard, applied;
	String lengthMsg, pathMsg, emptyMsg, errorMsg;
	static int precisionValue, range;
	JSlider precision;
	static List<Point> appliedPath;

    public Blueprint() {
    	
    	Container pane = this.getContentPane();
    	pane.setLayout(new GridBagLayout());
    	pane.setBackground(new Color(44, 47, 51));
    	GridBagConstraints c = new GridBagConstraints();
    	
        JPanel board = new JPanel();
        board.setBackground(new Color(87, 242, 135));
        JPanel blueprintBtns = new JPanel();
        blueprintBtns.setBackground(new Color(87, 242, 135));
        blueprintBtns.setLayout(new GridBagLayout());
        
        clearBoard = false;
        information = new JLabel("Welcome to the Melody Blueprint Editor!");
        apply = new JButton("Apply Blueprint");
        clear = new JButton("Clear Board");
        remove = new JButton("Remove Blueprint");
        precisionLabel = new JLabel("Precision: - ");
        precision = new JSlider(JSlider.HORIZONTAL, 1, 15, 5);
        precision.setPaintTrack(true);
        precision.setPaintTicks(true);
        precision.setPaintLabels(true);
        precision.setMajorTickSpacing(1);
        precision.setBackground(new Color(87, 242, 135));
        precision.setForeground(Color.black);
        
        // For all buttons
        c.ipady = 10;
        c.ipadx = 10;
        c.weightx = 0.3;
        c.weighty = 0.3;
        c.fill = GridBagConstraints.NONE;
        
        c.gridy = 0;
        c.gridwidth = 6;
        blueprintBtns.add(information, c);
        
        c.gridwidth = 2;
        c.gridy = 1;
        c.gridx = 0;
        blueprintBtns.add(apply, c);
        c.gridx = 2;
        blueprintBtns.add(clear, c);
        c.gridx = 4;
        blueprintBtns.add(remove, c);
        
        c.gridwidth = 2;
        c.gridy = 2;
        c.gridx = 0;
        blueprintBtns.add(precisionLabel, c);
        
        c.gridwidth = 4;
        c.gridx = 2;
        c.ipadx = 100;
        blueprintBtns.add(precision, c);
        
        c.ipady = 0;
        c.ipadx = 0;
        c.weightx = 0;
        c.weighty = 0.3;
        board.add(new TestPane());
        c.gridy = 1;
        pane.add(board, c);
        c.gridy = 0;
        c.ipady = 30;
        c.ipadx = 100;
        pane.add(blueprintBtns, c);
        
        apply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	invalidLength = false;
            	invalidPath = false;
            	invalidEmpty = false;
            	lengthMsg = "Blueprint too short";
            	pathMsg = "Blueprint goes back on itself";
            	emptyMsg = "Blueprint does not exist";
            	errorMsg = "Error(s): ";
            	List<Point> newTemporaryPath = new ArrayList<Point>(TestPane.getTemporaryPath());
            	if (newTemporaryPath == null || newTemporaryPath.size() == 0) {
            		invalidEmpty = true;
            	} else {
	            	range = Math.abs((int)((newTemporaryPath.get(newTemporaryPath.size() - 1)).getX() - (newTemporaryPath.get(0)).getX()));
	            	if (range < 200) {
	            		invalidLength = true;
	            	}
		            for (int x = 0; x < (newTemporaryPath.size() - 1); x++) {
		            	Point last = newTemporaryPath.get(x);
		            	Point now = newTemporaryPath.get(x + 1);
		            	if (last.getX() > now.getX()) {
		            		invalidPath = true;
		            	}
		            }
            	}
            	if (invalidLength && invalidPath) {
            		errorMsg = (errorMsg + lengthMsg + " // " + pathMsg);
            	} else if (invalidLength) {
            		errorMsg = (errorMsg + lengthMsg);
            	} else if (invalidPath) {
            		errorMsg = (errorMsg + pathMsg);
            	} else if (invalidEmpty) {
            		errorMsg = (errorMsg + emptyMsg);
            	} else {
            		errorMsg = "Blueprint applied!";
            		applied = true;
            		appliedPath = new ArrayList<Point>(newTemporaryPath);
            		ParaGUI.blueprintApplied(applied);
            	}
            	information.setText(errorMsg);
            }
        });
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
	            	clearBoard = true;
	            	repaint();
	            	information.setText("Board cleared!");
            }
        });
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if (applied) {
            		information.setText("Blueprint removed.");
            		applied = false;
            		ParaGUI.blueprintApplied(applied);
            	} else {
            		information.setText("Error(s): No blueprint to remove");
            	}
            }
        });
        precision.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                precisionValue = 20 - precision.getValue();
                precisionLabel.setText("Precision: " + precision.getValue());
            }
        });
    }
    
    public boolean getApplied() {
    	return applied;
    }
    public int getPrecision() {
    	return precisionValue;
    }
    public List<Point> getPath() {
    	return appliedPath;
    }
    
    public void returnToNormal() {
    	information.setText("Welcome to the Melody Blueprint Editor!");
    	clearBoard = true;
    	repaint();
    	applied = false;
    }

    public static class TestPane extends JPanel {

        private List<List<Point>> points;
        static List<Point> currentPath;
        static List<Point> temporaryPath;
        
        public TestPane() {
            points = new ArrayList<>(25);
            
            MouseAdapter ma = new MouseAdapter() {

                int i;

                @Override
                public void mousePressed(MouseEvent e) {
                    i = 1;
                    points.clear();
                    currentPath = new ArrayList<>(25);
                    currentPath.add(e.getPoint());
                    points.add(currentPath);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                	clearBoard = false;
                    Point dragPoint = e.getPoint();
                    currentPath.add(dragPoint);
                    if (currentPath.get(i).getX() > 900) {
                    	currentPath.get(i).setLocation(900, currentPath.get(i).getY());
                    }
                    if (currentPath.get(i).getX() < 0) {
                    	currentPath.get(i).setLocation(0, currentPath.get(i).getY());
                    }
                    if (currentPath.get(i).getY() > 300) {
                    	currentPath.get(i).setLocation(currentPath.get(i).getX(), 300);
                    }
                    if (currentPath.get(i).getY() < 0) {
                    	currentPath.get(i).setLocation(currentPath.get(i).getX(), 0);
                    }
                    i++;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    temporaryPath = currentPath;
                }
            };

            addMouseListener(ma);
            addMouseMotionListener(ma);
        }
        
        public static List<Point> getTemporaryPath() {
        	return temporaryPath;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(900, 300);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            
            if (clearBoard && !(currentPath == null)) {
            	points.clear();
            	currentPath.clear();
            }
            
			g2d.setColor(new Color(44, 47, 51));
			g2d.fill(new Rectangle2D.Float(0,0,900,300));
			
			g2d.setColor(new Color(153, 170, 181));
	        
	        for (List<Point> path: points) {
	            Point from = null;
	            for (Point p: path) {
	                if (from != null) {
	                    g2d.setStroke(new BasicStroke(5));
	                    g2d.drawLine(from.x, from.y, p.x, p.y);
	                }
	                from = p;
	            }
	        }
            
            g2d.setColor(new Color(87, 242, 135));
			g2d.setStroke(new BasicStroke(3));
			g2d.drawRect(0,0,900,300);
			
            g2d.dispose();
        }
    }
}