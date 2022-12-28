import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class ParaGUI extends JFrame {
    JLabel imre, spacing, octaveValueLabel, tempoValueLabel, durationValueLabel, stepValueLabel, voiceValueLabel, rootNoteLabel, modeLabel, instrumentLabel;
    static JButton blueprintBtn;
	JButton metronomeBtn;
	JButton advancedBtn;
    JSlider octave, tempo, duration, step;
	JSlider voice;
    JComboBox<String> rootNote, mode, instrument;
    JMenuBar menubar;
    JMenu file, view;
    JMenuItem reset, exit;
    static int octaveValue, tempoValue, durationValue, stepValue, voiceValue;
    static String modeChoice, instrumentChoice, rootNoteChoice;
    static boolean metronomeChoice, initBlueprint = false;
	static Blueprint blueprint = new Blueprint();
    
    
    String modes[] = {
    	"Pentatonic Major",
    	"Pentatonic Minor",
        "Ionian",
        "Dorian",
        "Phrygian",
        "Lydian",
        "Mixolydian",
        "Aeolian",
        "Locrian"
    };
    String instruments[] = {
        "Piano",
        "Electric Piano",
        "Music Box",
        "Marimba",
        "Guitar",
        "Electric Guitar",
        "Acoustic Bass",
        "Violin",
        "Harp",
        "Choir",
        "French Horn",
        "Saxophone",
        "Flute",
        "Recorder",
        "Whistle",
        "Square",
        "Saw"
    };
    String rootNotes[] = {
    	"C",
    	"C#",
    	"D",
    	"D#",
    	"E",
    	"F",
    	"F#",
    	"G",
    	"G#",
    	"A",
    	"Bb",
    	"B"
    };
    
    public static void main(String[] args) {}
    
    public ParaGUI() {
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        file = new JMenu("File");
        menubar.add(file);
        exit = new JMenuItem("Quit");
        file.add(exit);
        JPanel paraWelcome = new JPanel();
        paraWelcome.setBackground(Color.green);
        paraWelcome.setLayout(new GridBagLayout());
        JPanel paraBtns = new JPanel();
        paraBtns.setBackground(Color.pink);
        paraBtns.setLayout(new GridBagLayout());
        JPanel actualParaBtns = new JPanel();
        actualParaBtns.setBackground(Color.pink);
        actualParaBtns.setLayout(new GridBagLayout());

        imre = new JLabel(asHtml("Parameters"), SwingConstants.CENTER);
        c.fill = GridBagConstraints.NONE;
        c.ipady = 10;
        c.ipadx = 200;
        c.weightx = 0.3;
        c.weighty = 0.3;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        paraWelcome.add(imre, c);

        octaveValueLabel = new JLabel("Octave: -", SwingConstants.LEFT);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 0;
        paraBtns.add(octaveValueLabel, c);

        octave = new JSlider(JSlider.HORIZONTAL, 1, 5, 3);
        octave.setPaintTrack(true);
        octave.setPaintTicks(true);
        octave.setPaintLabels(true);
        octave.setMajorTickSpacing(1);
        octave.setBackground(Color.pink);
        octave.setForeground(Color.black);
        c.ipadx = 100;
        c.gridx = 1;
        c.gridy = 0;
        paraBtns.add(octave, c);

        tempoValueLabel = new JLabel("Tempo: -", SwingConstants.LEFT);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 1;
        paraBtns.add(tempoValueLabel, c);

        tempo = new JSlider(JSlider.HORIZONTAL, 60, 240, 120);
        tempo.setPaintTrack(true);
        tempo.setPaintTicks(true);
        tempo.setPaintLabels(true);
        tempo.setMajorTickSpacing(30);
        tempo.setMinorTickSpacing(15);
        tempo.setBackground(Color.pink);
        tempo.setForeground(Color.black);
        c.gridy = 1;
        c.gridx = 1;
        c.ipadx = 100;
        paraBtns.add(tempo, c);
        
        durationValueLabel = new JLabel("Duration: -", SwingConstants.LEFT);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 2;
        paraBtns.add(durationValueLabel, c);

        duration = new JSlider(JSlider.HORIZONTAL, 1, 4, 2);
        duration.setPaintTrack(true);
        duration.setPaintTicks(true);
        duration.setMajorTickSpacing(1);
        duration.setBackground(Color.pink);
        duration.setForeground(Color.black);
        c.gridy = 2;
        c.gridx = 1;
        c.ipadx = 100;
        paraBtns.add(duration, c);
        
        stepValueLabel = new JLabel("Step: -", SwingConstants.LEFT);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 3;
        paraBtns.add(stepValueLabel, c);

        step = new JSlider(JSlider.HORIZONTAL, 3, 12, 6);
        step.setPaintTrack(true);
        step.setPaintTicks(true);
        step.setPaintLabels(true);
        step.setMajorTickSpacing(1);
        step.setBackground(Color.pink);
        step.setForeground(Color.black);
        c.gridy = 3;
        c.gridx = 1;
        c.ipadx = 100;
        paraBtns.add(step, c);
        
        voiceValueLabel = new JLabel("Voices: -", SwingConstants.LEFT);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 4;
        paraBtns.add(voiceValueLabel, c);

        voice = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
        voice.setPaintTrack(true);
        voice.setPaintTicks(true);
        voice.setPaintLabels(true);
        voice.setMajorTickSpacing(1);
        voice.setBackground(Color.pink);
        voice.setForeground(Color.black);
        c.gridy = 4;
        c.gridx = 1;
        c.ipadx = 100;
        paraBtns.add(voice, c);
        
        rootNoteLabel = new JLabel("Root Note: ", SwingConstants.LEFT);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 5;
        paraBtns.add(rootNoteLabel, c);
        
        rootNote = new JComboBox<String>(rootNotes);
        c.gridx = 1;
        c.gridy = 5;
        c.ipadx = 150;
        paraBtns.add(rootNote, c);

        modeLabel = new JLabel("Mode: ", SwingConstants.LEFT);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 6;
        paraBtns.add(modeLabel, c);

        mode = new JComboBox<String>(modes);
        c.gridx = 1;
        c.gridy = 6;
        c.ipadx = 150;
        paraBtns.add(mode, c);

        instrumentLabel = new JLabel("Instrument: ", SwingConstants.LEFT);
        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 7;
        paraBtns.add(instrumentLabel, c);

        instrument = new JComboBox<String>(instruments);
        c.gridx = 1;
        c.gridy = 7;
        c.ipadx = 150;
        paraBtns.add(instrument, c);

        blueprintBtn = new JButton("Blueprint: Inactive");
        blueprintBtn.setBackground(Color.ORANGE);
        c.gridwidth = 1;
        c.gridy = 8;
        c.gridx = 0;
        c.ipady = 10;
        c.ipadx = 20;
        actualParaBtns.add(blueprintBtn, c);
        
        metronomeBtn = new JButton("Metronome: Inactive");
        metronomeBtn.setBackground(Color.ORANGE);
        c.gridwidth = 1;
        c.gridy = 8;
        c.gridx = 1;
        c.ipady = 10;
        c.ipadx = 10;
        actualParaBtns.add(metronomeBtn, c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 50;
        c.ipadx = 50;
        pane.add(paraWelcome, c);
        c.gridy = 1;
        c.ipady = 100;
        pane.add(paraBtns, c);
        c.gridy = 2;
        c.ipady = 50;
        pane.add(actualParaBtns, c);
        exit ex = new exit();

        exit.addActionListener(ex);
        
        blueprintBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if (initBlueprint) {
                    blueprint.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    blueprint.setTitle("Blueprint Editor");
                    blueprint.setVisible(false);
                    blueprint.setResizable(false);
            		initBlueprint = false;
            	}
            	if (blueprint.isVisible() == true) {
            		if (blueprint.getApplied() == false) {
	            		blueprintBtn.setText("Blueprint: Inactive");
	            		blueprintBtn.setBackground(Color.ORANGE);
            		}
            		blueprint.setVisible(false);
            	} else if (blueprint.isVisible() == false) {
            		if (blueprint.getApplied() == false) {
            			blueprintBtn.setText("Blueprint: Active   ");
                		blueprintBtn.setBackground(Color.CYAN);
            		}
                    blueprint.setVisible(true);
                    blueprint.setSize(1000, 600);
                    blueprint.setLocationRelativeTo(null);
                    blueprint.setResizable(false);
            	}
            }
        });
        metronomeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if (metronomeChoice == true) {
            		metronomeChoice = false;
            		metronomeBtn.setText("Metronome: Inactive");
            		metronomeBtn.setBackground(Color.ORANGE);
            	} else if (metronomeChoice == false) {
            		metronomeChoice = true;
            		metronomeBtn.setText("Metronome: Active   ");
            		metronomeBtn.setBackground(Color.CYAN);
            	}
            }
        });
        octave.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                octaveValue = octave.getValue();
                octaveValueLabel.setText("Octave: " + octaveValue);
            }
        });
        tempo.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                tempoValue = tempo.getValue();
                tempoValueLabel.setText("Tempo: " + tempoValue);
            }
        });
        duration.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	switch (duration.getValue()) {
	            	case 1:
	            		durationValue = 4;
	            		break;
	            	case 2:
	            		durationValue = 8;
	            		break;
	            	case 3:
	            		durationValue = 16;
	            		break;
	            	case 4:
	            		durationValue = 32;
	            		break;
            	}
                durationValueLabel.setText("Duration: " + durationValue);
            }
        });
        step.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	stepValue = step.getValue();
                stepValueLabel.setText("Step: " + stepValue);
            }
        });
        voice.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            	voiceValue = voice.getValue();
                voiceValueLabel.setText("Voices: " + voiceValue);
            }
        });
        rootNote.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
            	if (event.getStateChange() == ItemEvent.SELECTED) {
                    rootNoteChoice = (String)event.getItem();
                 }
            }
        });
        mode.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
            	if (event.getStateChange() == ItemEvent.SELECTED) {
                    modeChoice = (String)event.getItem();
                 }
            }
        });
        instrument.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
            	if (event.getStateChange() == ItemEvent.SELECTED) {
            		instrumentChoice = (String)event.getItem();
                 }
            }
        });
        
    }
    
    public static void blueprintApplied(boolean applied) {
    	if (applied) {
	    	blueprintBtn.setText("Blueprint: Applied  ");
	    	blueprintBtn.setBackground(Color.YELLOW);
    	} else {
    		blueprintBtn.setText("Blueprint: Active   ");
	    	blueprintBtn.setBackground(Color.CYAN);
    	}
    }
    
    public int getOctave() {
    	return octaveValue;
    }
    public int getTempo() {
    	return tempoValue;
    }
    public int getDuration() {
    	return durationValue;
    }
    public int getStep() {
    	return stepValue;
    }
    public int getVoice() {
    	return voiceValue;
    }
    public String getRootNote() {
    	return rootNoteChoice;
    }
    public String getMode() {
    	return modeChoice;
    }
    public String getInstrument() {
    	return instrumentChoice;
    }
    public boolean getMetronome() {
    	return metronomeChoice;
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