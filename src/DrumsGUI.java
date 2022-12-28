import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;



public class DrumsGUI extends JFrame {
    JLabel drumsMsg, drumsTypeLabel, drumsVolLabel, drumsCompLabel;
    JButton kickBtn, snareBtn, closedHatsBtn, openHatsBtn, rideBtn, cymbalBtn, tomsBtn;
    JSlider kickVol, snareVol, closedHatsVol, openHatsVol, rideVol, cymbalVol, tomsVol;
    JSlider drumsCompSlider;
    JMenuBar menubar;
    JMenu file, view;
    JMenuItem reset, exit;
    static int kickVolValue, snareVolValue, closedHatsVolValue, openHatsVolValue, rideVolValue, cymbalVolValue, tomsVolValue;
    static int drumsCompSliderValue;
    
    public static void main(String[] args) {}

    public DrumsGUI() {
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        menubar = new JMenuBar();
        setJMenuBar(menubar);
        file = new JMenu("File");
        menubar.add(file);
        exit = new JMenuItem("Quit");
        file.add(exit);

        JPanel drumsWelcome = new JPanel();
        drumsWelcome.setBackground(Color.green);
        drumsWelcome.setLayout(new GridBagLayout());
        JPanel drumsBtns = new JPanel();
        drumsBtns.setBackground(Color.pink);
        drumsBtns.setLayout(new GridBagLayout());
        JPanel drumsComp = new JPanel();
        drumsComp.setBackground(Color.yellow);
        drumsComp.setLayout(new GridBagLayout());

        drumsMsg = new JLabel("Drums Editor", SwingConstants.CENTER);
        c.fill = GridBagConstraints.NONE;
        c.ipady = 10;
        c.ipadx = 200;
        c.weightx = 0.3;
        c.weighty = 0.3;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        drumsWelcome.add(drumsMsg, c);
        
        drumsTypeLabel = new JLabel("Instrument");
        c.ipady = 0;
        c.ipadx = 0;
        drumsBtns.add(drumsTypeLabel, c);

        drumsVolLabel = new JLabel("Volume");
        c.gridx = 1;
        drumsBtns.add(drumsVolLabel, c);
        
        // Instrument rows
        
        kickBtn = new JButton("Kick");
        kickVol = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        instrumentRow(kickBtn, kickVol, drumsBtns, 1);
        
        snareBtn = new JButton("Snare");
        snareVol = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        instrumentRow(snareBtn, snareVol, drumsBtns, 2);
        
        closedHatsBtn = new JButton("Closed Hi-Hat");
        closedHatsVol = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        instrumentRow(closedHatsBtn, closedHatsVol, drumsBtns, 3);
        
        openHatsBtn = new JButton("Open Hi-Hat");
        openHatsVol = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        instrumentRow(openHatsBtn, openHatsVol, drumsBtns, 4);
        
        rideBtn = new JButton("Ride");
        rideVol = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        instrumentRow(rideBtn, rideVol, drumsBtns, 5);
        
        cymbalBtn = new JButton("Cymbal");
        cymbalVol = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        instrumentRow(cymbalBtn, cymbalVol, drumsBtns, 6);
        
        tomsBtn = new JButton("Toms");
        tomsVol = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        instrumentRow(tomsBtn, tomsVol, drumsBtns, 7);
        
        drumsCompLabel = new JLabel("Rhythmic Comp");
        c.gridx = 0;
        c.gridy = 0;
        drumsComp.add(drumsCompLabel, c);
        
        drumsCompSlider = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
        drumsCompSlider.setPaintTrack(true);
        drumsCompSlider.setPaintTicks(true);
        drumsCompSlider.setPaintLabels(true);
        drumsCompSlider.setMajorTickSpacing(1);
        drumsCompSlider.setBackground(Color.yellow);
        drumsCompSlider.setForeground(Color.black);
        drumsCompSlider.setEnabled(true);
        c.ipadx = 50;
        c.gridy = 1;
        drumsComp.add(drumsCompSlider, c);

        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 50;
        c.ipadx = 50;
        pane.add(drumsWelcome, c);
        c.gridy = 1;
        c.ipady = 100;
        pane.add(drumsBtns, c);
        c.gridy = 2;
        c.ipady = 25;
        pane.add(drumsComp, c);
        
        exit ex = new exit();
        exit.addActionListener(ex);
        
        kickVol.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	kickVolValue = kickVol.getValue();
            }
        });
        snareVol.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	snareVolValue = snareVol.getValue();
            }
        });
        closedHatsVol.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	closedHatsVolValue = closedHatsVol.getValue();
            }
        });
        openHatsVol.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	openHatsVolValue = openHatsVol.getValue();
            }
        });
        rideVol.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	rideVolValue = rideVol.getValue();
            }
        });
        cymbalVol.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	cymbalVolValue = cymbalVol.getValue();
            }
        });
        tomsVol.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	tomsVolValue = tomsVol.getValue();
            }
        });
        drumsCompSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	drumsCompSliderValue = drumsCompSlider.getValue();
            }
        });
    }
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	changeInstrument(e, kickBtn, kickVol);
        	changeInstrument(e, snareBtn, snareVol);
        	changeInstrument(e, closedHatsBtn, closedHatsVol);
        	changeInstrument(e, openHatsBtn, openHatsVol);
        	changeInstrument(e, rideBtn, rideVol);
        	changeInstrument(e, cymbalBtn, cymbalVol);
        	changeInstrument(e, tomsBtn, tomsVol);
        }
        public void changeInstrument(ActionEvent e, JButton currentInstrumentButton, JSlider currentInstrumentVol) {
        	if (e.getSource() == currentInstrumentButton && currentInstrumentVol.isEnabled() == false) {
        		currentInstrumentButton.setBackground(Color.CYAN);
        		currentInstrumentVol.setEnabled(true);
        		currentInstrumentVol.setValue(50);
        	} else if (e.getSource() == currentInstrumentButton && currentInstrumentVol.isEnabled() == true) {
        		currentInstrumentButton.setBackground(Color.ORANGE);
        		currentInstrumentVol.setEnabled(false);
        		currentInstrumentVol.setValue(0);
        	}
        }
    }
    public class exit implements ActionListener {
        public void actionPerformed(ActionEvent ex) {
            System.exit(0);
        }
    }
    public void instrumentRow(JButton currentInstrumentButton, JSlider currentInstrumentVol, JPanel currentPanel, int yCoord) {
    	event e = new event();
    	
    	GridBagConstraints o = new GridBagConstraints();
    	currentInstrumentButton.setBackground(Color.ORANGE);
    	o.weightx = 0.3;
    	o.weighty = 0.3;
        o.ipady = 0;
        o.ipadx = 0;
        o.gridx = 0;
        o.gridy = yCoord;
        currentPanel.add(currentInstrumentButton, o);

        currentInstrumentVol.setPaintTrack(true);
        currentInstrumentVol.setPaintTicks(true);
        currentInstrumentVol.setPaintLabels(true);
        currentInstrumentVol.setMajorTickSpacing(20);
        currentInstrumentVol.setMinorTickSpacing(10);
        currentInstrumentVol.setBackground(Color.pink);
        currentInstrumentVol.setForeground(Color.black);
        currentInstrumentVol.setEnabled(false);
        o.gridx = 1;
        o.gridy = yCoord;
        o.ipadx = 50;
        currentPanel.add(currentInstrumentVol, o);
    	
        currentInstrumentButton.addActionListener(e);
    }
    public int getKickVol() {
    	return kickVolValue;
    }
    public int getSnareVol() {
    	return snareVolValue;
    }
    public int getClosedHatsVol() {
    	return closedHatsVolValue;
    }
    public int getOpenHatsVol() {
    	return openHatsVolValue;
    }
    public int getRideVol() {
    	return rideVolValue;
    }
    public int getCymbalVol() {
    	return cymbalVolValue;
    }
    public int getTomsVol() {
    	return tomsVolValue;
    }
    public int getDrumsComp() {
    	return drumsCompSliderValue;
    }
}