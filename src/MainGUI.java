import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class MainGUI extends JFrame {
    JButton composeBtn, saveBtn, loadBtn, playBtn, stopBtn;
    JButton paraBtn, generateBtn, drumsBtn;
    JButton showBtn, notateBtn, sketchBtn;
    JLabel welcomeMsg, musicOptions, genOptions, viewOptions;
    JMenuBar menubar;
    JMenu file, view;
    JMenuItem reset, exit, lightMode, darkMode, fullscreen;
    static ParaGUI paragui = new ParaGUI();
    static DrumsGUI drumsgui = new DrumsGUI();
    static boolean initPara, initDrums = false;
    
    public static void main(String[] args) {
        MainGUI gui = new MainGUI();
        gui.setVisible(true);
        gui.setSize(500, 700);
        gui.setDefaultCloseOperation(HIDE_ON_CLOSE);
        gui.setTitle("Melodic Idea Generator");
        gui.setLocation(0,0);
    }
    
    public MainGUI() {
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        menubar = new JMenuBar();
        setJMenuBar(menubar);
        file = new JMenu("File");
        menubar.add(file);
        exit = new JMenuItem("Quit");
        file.add(exit);
        
        JPanel welcomeLine = new JPanel();
        welcomeLine.setBackground(Color.green);
        welcomeLine.setLayout(new GridBagLayout());
        JPanel mainBtns = new JPanel();
        mainBtns.setBackground(Color.lightGray);
        mainBtns.setLayout(new GridBagLayout());
        JPanel genBtns = new JPanel();
        genBtns.setBackground(Color.pink);
        genBtns.setLayout(new GridBagLayout());
        JPanel viewBtns = new JPanel();
        viewBtns.setBackground(Color.cyan);
        viewBtns.setLayout(new GridBagLayout());

        welcomeMsg = new JLabel("Welcome to the Melodic Idea Generator!", SwingConstants.CENTER);
        c.fill = GridBagConstraints.NONE;
        c.ipady = 10;
        c.ipadx = 100;
        c.weightx = 0.3;
        c.weighty = 0.3;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        welcomeLine.add(welcomeMsg, c);

        musicOptions = new JLabel("Music Options", SwingConstants.CENTER);
        c.ipady = 10;
        c.ipadx = 10;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        mainBtns.add(musicOptions, c);

        playBtn = new JButton("Play");
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        mainBtns.add(playBtn, c);

        stopBtn = new JButton("Stop");
        c.gridx = 1;
        c.gridy = 1;
        mainBtns.add(stopBtn, c);
        
        saveBtn = new JButton("Save");
        c.gridx = 0;
        c.gridy = 2;
        mainBtns.add(saveBtn, c);
        
        loadBtn = new JButton("Load");
        c.gridx = 1;
        c.gridy = 2;
        mainBtns.add(loadBtn, c);

        composeBtn = new JButton("Compose");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        mainBtns.add(composeBtn, c);

        genOptions = new JLabel("Generator Options", SwingConstants.CENTER);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        genBtns.add(genOptions, c);

        paraBtn = new JButton("Parameters");
        paraBtn.setBackground(Color.ORANGE);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        genBtns.add(paraBtn, c);

        drumsBtn = new JButton("Drums");
        drumsBtn.setBackground(Color.ORANGE);
        c.gridx = 1;
        c.gridy = 1;
        genBtns.add(drumsBtn, c);
        
        generateBtn = new JButton("Generate");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        genBtns.add(generateBtn, c);

        viewOptions = new JLabel("View Options", SwingConstants.CENTER);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        viewBtns.add(viewOptions, c);

        showBtn = new JButton("Piano Roll");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        viewBtns.add(showBtn, c);

        sketchBtn = new JButton("Sketch Melody");
        c.gridx = 1;
        c.gridy = 1;
        viewBtns.add(sketchBtn, c);

        notateBtn = new JButton("Sheet Music");
        c.gridx = 2;
        c.gridy = 1;
        viewBtns.add(notateBtn, c);
        
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 50;
        c.ipadx = 50;
        pane.add(welcomeLine, c);
        c.gridy = 1;
        pane.add(mainBtns, c);
        c.gridy = 2;
        pane.add(genBtns, c);
        c.gridy = 3;
        pane.add(viewBtns, c);
        
        exit ex = new exit();
        mainBtnPress m = new mainBtnPress();
        genBtnPress g = new genBtnPress();
        viewBtnPress v = new viewBtnPress();
        
        playBtn.addActionListener(m);
        stopBtn.addActionListener(m);
        saveBtn.addActionListener(m);
        loadBtn.addActionListener(m);
        composeBtn.addActionListener(m);
        
        paraBtn.addActionListener(g);
        generateBtn.addActionListener(g);
        drumsBtn.addActionListener(g);
        
        showBtn.addActionListener(v);
        sketchBtn.addActionListener(v);
        notateBtn.addActionListener(v);
        
        exit.addActionListener(ex);
    }
    
    public class mainBtnPress implements ActionListener {
        public void actionPerformed(ActionEvent m) {
        	if (m.getSource() == playBtn) {
                Generation.play();
            } else if (m.getSource() == stopBtn) {
                Generation.stop();
            } else if (m.getSource() == saveBtn) {
                Generation.save();
            } else if (m.getSource() == loadBtn) {
                Generation.load();
            } else if (m.getSource() == composeBtn) {
                Generation.notate();
            }
        }
    }
    
    public class genBtnPress implements ActionListener {
        public void actionPerformed(ActionEvent g) {
            if (g.getSource() == paraBtn) {
            	if (initPara) {
                    paragui.setDefaultCloseOperation(HIDE_ON_CLOSE);
                    paragui.setTitle("Parameters");
                    paragui.setVisible(false);
            		initPara = false;
            	}
            	if (paragui.isVisible() == true) {
            		paraBtn.setBackground(Color.ORANGE);
                    paragui.setVisible(false);
            	} else if (paragui.isVisible() == false) {
            		paraBtn.setBackground(Color.CYAN);
                    paragui.setVisible(true);
                    paragui.setSize(500, 700);
                    paragui.setLocation(500,0);
            	}
            } else if (g.getSource() == drumsBtn) {
            	if (initDrums) {
                    drumsgui.setDefaultCloseOperation(HIDE_ON_CLOSE);
                    drumsgui.setTitle("Drums Editor");
                    drumsgui.setVisible(false);
            		initDrums = false;
            	}
            	if (drumsgui.isVisible() == true) {
            		drumsBtn.setBackground(Color.ORANGE);
                    drumsgui.setVisible(false);
            	} else if (drumsgui.isVisible() == false) {
            		drumsBtn.setBackground(Color.CYAN);
                    drumsgui.setVisible(true);
                    drumsgui.setSize(500, 700);
                    drumsgui.setLocation(1000,0);
            	}
            } else if (g.getSource() == generateBtn) {
                Generation generation = new Generation();
            }
        }
    }
    
    public class viewBtnPress implements ActionListener {
        public void actionPerformed(ActionEvent v) {
        	if (v.getSource() == showBtn) {
                Generation.show();
            } else if (v.getSource() == sketchBtn) {
                Generation.sketch();
            } else if (v.getSource() == notateBtn) {
                Generation.notate();
            }
        }
    }
    public class exit implements ActionListener {
        public void actionPerformed(ActionEvent ex) {
            System.exit(0);
        }
    }
}