import java.awt.Point;
import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import jm.music.data.*;
import jm.music.tools.Mod;
import jm.JMC;
import jm.util.*;

public class Generation implements JMC {
	
	static Score s = new Score("Melodic Idea Generator");
	static Note restNoteQuaver = new Note(REST, 0.5);
	static Note restNoteCrotchet = new Note(REST, 1.0);
	static Note restNoteDotCrotchet = new Note(REST, 1.5);
	static Note restNoteMinim = new Note(REST, 2.0);
	static Note restNoteDotMinim = new Note(REST, 3.0);
	static Note restNoteSemibreve = new Note(REST, 4.0);
	boolean kickOn, snareOn, closedHatsOn, openHatsOn, rideOn, cymbalOn, tomsOn = true;
	
	public Generation() {
		
		// Clears whatever notes were in the score.
		s.removeAllParts();
		
		ParaGUI paragui = new ParaGUI();
		// Getting values from ParaGUI.
		int tempoValue = paragui.getTempo();
		int octaveValue = paragui.getOctave();
		int durationValue = paragui.getDuration();
		int stepValue = paragui.getStep();
		int voiceValue = paragui.getVoice();
		String rootNoteChoice = paragui.getRootNote();
		String modeChoice = paragui.getMode();
		String instrumentChoice = paragui.getInstrument();
		boolean metronomeChoice = paragui.getMetronome();
		// If any values are null, set them to default.
		if (intDefault(tempoValue)) {tempoValue = 120;}
		if (intDefault(octaveValue)) {octaveValue = 3;}
		if (intDefault(durationValue)) {durationValue = 8;}
		if (intDefault(stepValue)) {stepValue = 6;}
		if (intDefault(voiceValue)) {voiceValue = 1;}
		if (stringDefault(rootNoteChoice)) {rootNoteChoice = "C";}
		if (stringDefault(modeChoice)) {modeChoice = "Pentatonic Major";}
		if (stringDefault(instrumentChoice)) {instrumentChoice = "Piano";}
		
		DrumsGUI drumsgui = new DrumsGUI();
		// Getting values from DrumsGUI.
		int kickVol = drumsgui.getKickVol();
		int snareVol = drumsgui.getSnareVol();
		int closedHatsVol = drumsgui.getClosedHatsVol();
		int openHatsVol = drumsgui.getOpenHatsVol();
		int rideVol = drumsgui.getRideVol();
		int cymbalVol = drumsgui.getCymbalVol();
		int tomsVol = drumsgui.getTomsVol();
		int drumsComp = drumsgui.getDrumsComp();
		// Checking if any aren't 0.
		if (intDefault(kickVol) == false) {generateKick(kickVol, durationValue, drumsComp);}
		if (intDefault(snareVol) == false) {generateSnare(snareVol, durationValue, drumsComp);}
		if (intDefault(closedHatsVol) == false) {closedHatsOn = false;}
		if (intDefault(openHatsVol) == false) {openHatsOn = false;}
		if (intDefault(rideVol) == false) {rideOn = false;}
		if (intDefault(cymbalVol) == false) {cymbalOn = false;}
		if (intDefault(tomsVol) == false) {tomsOn = false;}
		
		Blueprint blueprint = new Blueprint();
		// Getting values from Blueprint.
		boolean applied = blueprint.getApplied();
		int precision = blueprint.getPrecision();
		List<Point> appliedPath = blueprint.getPath();
		// Checking if any are null.
		if (intDefault(precision)) {precision = 8;}
		
		// Cleanup.
		int finalInstrumentChoice = setupInstruments(instrumentChoice);
		int[] finalModeChoice = setupModes(modeChoice);
		int finalRootNoteChoice = setupRootNote(rootNoteChoice, octaveValue);
		// Generation.
		for (int f = 1; f <= voiceValue; f++) {
			if (applied) {
				generateMelodyWithBlueprint(durationValue, finalModeChoice, finalRootNoteChoice, metronomeChoice, precision, appliedPath, finalInstrumentChoice);
			} else {
				generateMelody(durationValue, finalModeChoice, finalRootNoteChoice, stepValue, metronomeChoice, finalInstrumentChoice);
			}
		}
		s.setTempo(tempoValue);
	}
	
	public void generateKick(int localKickVol, int localDurationValue, int localComplexityValue) {
		Part kick = new Part("Kick", 0, 9);
		Phrase kickPhr = new Phrase();
		Note kickNote = new Note(36, 0, localKickVol);
		for (int k = 0; k <= (localDurationValue / 2); k++) {
			kickPhr.addNote(kickNote);
			kickPhr.addNote(restNoteSemibreve);
		}
		kick.addPhrase(kickPhr);
		s.addPart(kick);
	}
	
	public void generateSnare(int localSnareVol, int localDurationValue, int localComplexityValue) {
		Part snare = new Part("Snare", 0, 9);
		Phrase snarePhr = new Phrase();
		Note snareNote = new Note(38, 0, localSnareVol);
		snarePhr.addNote(restNoteMinim);
		for (int k = 0; k <= (localDurationValue / 2); k++) {
			snarePhr.addNote(snareNote);
			snarePhr.addNote(restNoteSemibreve);
		}
		snare.addPhrase(snarePhr);
		s.addPart(snare);
	}
	
	public void generateMelody(int localDurationValue, int[] localModeArrayChoice, int localRootNote, int localStepValue, boolean localMetronomeChoice, int localInstrumentChoice) {
		
		// Creating JMusic objects.
		Part inst = new Part("Main Instrument", localInstrumentChoice, 0);
		Part metronomeDownPart = new Part("Drums", 0, 9);
		Part metronomeOtherPart = new Part("Drums", 0, 9);
		Phrase instPhr = new Phrase();
		Phrase metronomeDown = new Phrase();
		Phrase metronomeOther = new Phrase();
		Random rnd = new Random();
		
		boolean inScale = false;
		int[] currentRhythm;
		int offset = 0;
		int initialPitch = 0;
		// While the initial pitch isn't in the mode we've chosen,
		while (inScale == false) {
			// Pick an initial note from within half an octave of the root note.
			initialPitch = localRootNote + rnd.nextInt(13) - 6;
			// Check that this note is in the scale.
			for (int h=0; h < localModeArrayChoice.length; h++) {
				if ((initialPitch - (localRootNote % 12)) % 12 == localModeArrayChoice[h]) {
					inScale = true;
				}
			}
		}
		int currentPitch = initialPitch;
		int latestPitch = initialPitch;
		
		// In every bar k the specified duration of bars,
		for (int k = 0; k < (localDurationValue / 2); k++) {
			// Pick a random rhythm from the array or generate a new one.
			int presetOrRandom = rnd.nextInt(16);
			if (presetOrRandom < 12) {
			currentRhythm = setupRhythms()[rnd.nextInt(12)];
			} else {
				currentRhythm = randomRhythm();
			}
			// Cycle through this chosen array and,
			for (int i = 0; i <= 7;) {
				// If a value is zero, don't continue. i++.
				if (currentRhythm[i] == 0) {
					i++;
				} else {
					// If a certain index in the rhythm array isn't zero,
					for (int j=0; j < localModeArrayChoice.length; j++) {
						// Then generate the "offset" - if it equals 0, regenerate.
						do {
							offset = rnd.nextInt((localStepValue * 2 + 1)) - localStepValue;
						} while (offset == 0);
						// Make the next pitch based on the offset.
						currentPitch += offset;
						// So if this pitch produced is in the mode and in between the initial pitch,
						if ((currentPitch - (localRootNote % 12)) % 12 == localModeArrayChoice[j]) {
							// And if it's within two octave of the initial pitch too,
							if ((currentPitch < (initialPitch + 24)) && (currentPitch > (initialPitch - 24))) {
								// Then add it to the phrase and move to the next note in the phrase.
								Note instNote = new Note(currentPitch, Q * currentRhythm[i]);
								instPhr.addNote(instNote);
								latestPitch = currentPitch;
								i++;
							}
						} else {
							// If not, then ensure the pitch is still the same as the actual last one we placed.
							currentPitch = latestPitch;
						}
					}
				}
			}
			if (localMetronomeChoice) {
				Note metronomeDownNote = new Note(SIDE_STICK, SB, P);
				metronomeDown.addNote(metronomeDownNote);
				Note metronomeOtherNote = new Note(LOW_WOOD_BLOCK, C, P);
				metronomeOther.addNote(metronomeOtherNote);
			}
		}
		Mod.repeat(metronomeOther, 4);
		inst.addPhrase(instPhr);
		metronomeDownPart.addPhrase(metronomeDown);
		metronomeOtherPart.addPhrase(metronomeOther);
		s.addPart(inst);
		s.addPart(metronomeDownPart);
		s.addPart(metronomeOtherPart);
	}
	
	public void generateMelodyWithBlueprint(int localDurationValue, int[] localModeArrayChoice, int localRootNote, boolean localMetronomeChoice, int localPrecision, List<Point> localPath, int localInstrumentChoice) {
		
		// Creating JMusic objects.
		Part inst = new Part("Main Instrument", localInstrumentChoice, 0);
		Part metronomeDownPart = new Part("Drums", 0, 9);
		Part metronomeOtherPart = new Part("Drums", 0, 9);
		Phrase instPhr = new Phrase();
		Phrase metronomeDown = new Phrase();
		Phrase metronomeOther = new Phrase();
		Random rnd = new Random();
		
		// Creating variables to transform the path.
		int range = (int)((localPath.get(localPath.size() - 1)).getX() - (localPath.get(0)).getX());
		int beginning = (int)(localPath.get(0).getX());
		double newY;
		double newX;
		
		// Transforming the path into something legible.
		for (int g = 0; g < localPath.size(); g++) {
			// Process the path and convert all Y to pitches,
			newY = ((double)(300 - localPath.get(g).getY()) / 5 + 36);
			// Convert all X to percentiles,
			newX = ((double)(localPath.get(g).getX() - beginning) / range * 100);
			// And recreate new arraylist.
			localPath.get(g).setLocation(newX, newY);
		}
		
		// Creating variables to form melody.
		int[] currentRhythm = {};
		int currentPitch = 0;
		int currentTimeIndex = 0;
		int lastPitch = 0;
		int currentDuration = 0;
		int totalDuration = localDurationValue * 4;
		int currentProportion = 0;
		List<Integer> differences = new ArrayList<>();
		List<Integer> originalDifferences = new ArrayList<>();
		
		for (int k = 0; k < (localDurationValue / 2); k++) {
			// Pick a random rhythm from the array or generate a new one.
			int presetOrRandom = rnd.nextInt(16);
			if (presetOrRandom < 12) {
			currentRhythm = setupRhythms()[rnd.nextInt(12)];
			} else {
				currentRhythm = randomRhythm();
			}
			// Cycle through this chosen array and,
			for (int i = 0; i <= 7;) {
				// If a value is zero, don't continue. i++.
				if (currentRhythm[i] == 0) {
					i++;
				} else {
					// Get the percentile through the duration that we're at.
					currentProportion = (int)(((double)currentDuration / (double)totalDuration) * 100);
					// Iterate through the path array and find the X value closest to this percentile.
					for (int l = 0; l < localPath.size(); l++) {
						differences.add((int)Math.abs((localPath.get(l).getX()) - currentProportion));
					}
					// Use a merge sort to find the smallest X value.
					
					originalDifferences = new ArrayList<>(differences);
					splitList(differences);
					// Use this X value to get the index where it occured.
					currentTimeIndex = originalDifferences.indexOf(differences.get(0));
					// Using this X value, find the corresponding Y value.
					currentPitch = (int)localPath.get(currentTimeIndex).getY();
					// Using this X value, find the corresponding Y value.
					differences.clear();
					// Using this and with precision, approximate a note pitch.
					currentPitch += rnd.nextInt(localPrecision);
					if (!(currentPitch == lastPitch)) {
					// Iterate through the mode array,
						for (int j=0; j < localModeArrayChoice.length; j++) {
							// And if this pitch produced is in the mode,
							if ((currentPitch % 12) - (localRootNote % 12) == localModeArrayChoice[j]) {
								// Then add it to the phrase and move to the next note in the phrase.
								Note instNote = new Note(currentPitch, Q * currentRhythm[i]);
								instPhr.addNote(instNote);
								// And increment the current duration value.
								currentDuration += currentRhythm[i];
								lastPitch = currentPitch;
								i++;
							}
						}
					}
				}
			}
			if (localMetronomeChoice) {
				Note metronomeDownNote = new Note(SIDE_STICK, SB, P);
				metronomeDown.addNote(metronomeDownNote);
				Note metronomeOtherNote = new Note(LOW_WOOD_BLOCK, C, P);
				metronomeOther.addNote(metronomeOtherNote);
			}
		}
		Mod.repeat(metronomeOther, 4);
		inst.addPhrase(instPhr);
		metronomeDownPart.addPhrase(metronomeDown);
		metronomeOtherPart.addPhrase(metronomeOther);
		s.addPart(inst);
		s.addPart(metronomeDownPart);
		s.addPart(metronomeOtherPart);
	}
	
	public static void play() {
		Play.midi(s);
	}
	public static void stop() {
		Play.stopMidi();
	}
	public static void save() {
		Write.midi(s, "Your Melody.mid");
	}
	public static void load() {
		s.removeAllParts();
		Score l = new Score();
		// read the MIDI files made earlier as input                
		Read.midi(l, "Your Melody.mid");
		                
		// get the part from it                
		Part tempPart = l.getPart(0);
		                
		// add it to the new score                
		s.addPart(tempPart.copy());
	}
	public static void show() {
		View.show(s);
	}
	public static void sketch() {
		View.sketch(s);
	}
	public static void notate() {
		View.notate(s);
	}
	public static Score getScore() {
		return s;
	}
	
	public int setupRootNote(String localRootNote, int localOctave) {
		int magicRootNote = 0;
		int octaveMultiplier = localOctave * 12 + 24;
		
		switch(localRootNote) {
		case "C":
			magicRootNote = 0;
			break;
		case "C#":
			magicRootNote = 1;
			break;
		case "D":
			magicRootNote = 2;
			break;
		case "D#":
			magicRootNote = 3;
			break;
		case "E":
			magicRootNote = 4;
			break;
		case "F":
			magicRootNote = 5;
			break;
		case "F#":
			magicRootNote = 6;
			break;
		case "G":
			magicRootNote = 7;
			break;
		case "G#":
			magicRootNote = 8;
			break;
		case "A":
			magicRootNote = 9;
			break;
		case "Bb":
			magicRootNote = 10;
			break;
		case "B":
			magicRootNote = 11;
			break;
		}
		magicRootNote += octaveMultiplier;
		return magicRootNote;
	}
	
	public int[] setupModes(String localModeChoice) {
		
		int[] localModeArrayChoice = {};
		int[] pentaMajor = {0,2,4,7,9};
		int[] pentaMinor = {0,3,4,7,10};
		int[] ionian = {0,2,4,5,7,9,11};
		int[] dorian = {0,2,3,5,7,9,10};
		int[] phrygian = {0,1,3,5,7,8,10};
		int[] lydian = {0,2,4,6,7,9,11};
		int[] mixolydian = {0,2,3,5,7,8,10};
		int[] aeolian = {0,2,3,5,7,8,10};
		int[] locrian = {0,1,3,5,6,8,10};
		int[][] modeArray = {
				pentaMajor,
				pentaMinor,
				ionian, 
				dorian, 
				phrygian,
				lydian,
				mixolydian,
				aeolian,
				locrian
			};
		
		switch(localModeChoice) {
	        case "Pentatonic Major":
	            localModeArrayChoice = modeArray[0];
	            break;
	        case "Pentatonic Minor":
	            localModeArrayChoice = modeArray[1];
	            break;
			case "Ionian":
				localModeArrayChoice = modeArray[2];
				break;
			case "Dorian":
	            localModeArrayChoice = modeArray[3];
	            break;
	        case "Phrygian":
	            localModeArrayChoice = modeArray[4];
	            break;
	        case "Lydian":
	            localModeArrayChoice = modeArray[5];
	            break;
	        case "Mixolydian":
	            localModeArrayChoice = modeArray[6];
	            break;
	        case "Aeolian":
	            localModeArrayChoice = modeArray[7];
	            break;
	        case "Locrian":
	            localModeArrayChoice = modeArray[8];
	            break;
		}
		return localModeArrayChoice;
	}
	
	public int[][] setupRhythms() {
		int[][] rhythmsArray = {
			{2,1,1,1,2,1,0,0},
			{1,1,2,2,1,1,0,0},
			{1,1,1,1,2,2,0,0},
			{2,2,2,1,1,0,0,0},
			{2,2,4,0,0,0,0,0},
			{3,3,2,0,0,0,0,0},
			{3,1,2,2,0,0,0,0},
			{1,1,2,1,1,2,0,0},
			{3,2,2,1,0,0,0,0},
			{2,2,2,2,0,0,0,0},
			{2,1,1,2,1,1,0,0},
			{2,1,2,1,1,1,0,0}
		};
		return rhythmsArray;
	}
	
	public int setupInstruments(String instrumentChoice) {
		int finalInstrumentChoice = 0;
		switch (instrumentChoice) {
			case "Piano":
	            finalInstrumentChoice = 0;
	            break;
	        case "Electric Piano":
	        	finalInstrumentChoice = 4;
	            break;
			case "Music Box":
				finalInstrumentChoice = 10;
				break;
			case "Marimba":
				finalInstrumentChoice = 12;
	            break;
	        case "Guitar":
	        	finalInstrumentChoice = 24;
	            break;
	        case "Electric Guitar":
	        	finalInstrumentChoice = 27;
	            break;
	        case "Acoustic Bass":
	        	finalInstrumentChoice = 32;
	            break;
	        case "Violin":
	        	finalInstrumentChoice = 40;
	            break;
	        case "Harp":
	        	finalInstrumentChoice = 46;
	            break;
	        case "Choir":
	        	finalInstrumentChoice = 52;
	            break;
	        case "French Horn":
	        	finalInstrumentChoice = 60;
	            break;
	        case "Saxophone":
	        	finalInstrumentChoice = 64;
	            break;
	        case "Flute":
	        	finalInstrumentChoice = 73;
	            break;
	        case "Recorder":
	        	finalInstrumentChoice = 74;
	            break;
	        case "Whistle":
	        	finalInstrumentChoice = 78;
	            break;
	        case "Square":
	        	finalInstrumentChoice = 80;
	            break;
	        case "Saw":
	        	finalInstrumentChoice = 81;
	            break;
	        
		}
		return finalInstrumentChoice;
	}
	
	public boolean intDefault(int isItZero) {
		if (isItZero == 0) {
			return true;
		} else {
			return false;
		}
	}
	public boolean stringDefault(String isItNull) {
		if (Objects.isNull(isItNull)) {
			return true;
		} else {
			return false;
		}
	}
	
	public int[] randomRhythm() {
		
		int[] randomRhythmArray = {0,0,0,0,0,0,0,0};
		int sum = 0;
		int randomNoteLength = 0;
		Random rnd = new Random();
		
		for (int m = 0; m < 8; m++) {
			sum = 0;
			for (int n = 0; n < 8; n++) {
				sum += randomRhythmArray[n];
			}
			if (sum == 8) {
				randomNoteLength = 0;
				randomRhythmArray[m] = randomNoteLength;
			} else if (sum == 7) {
				randomNoteLength = 1;
				randomRhythmArray[m] = randomNoteLength;
			} else if (sum == 6) {
				randomNoteLength = rnd.nextInt(2) + 1;
				randomRhythmArray[m] = randomNoteLength;
			} else {
				randomNoteLength = rnd.nextInt(3) + 1;
				randomRhythmArray[m] = randomNoteLength;
			}
		}
		return randomRhythmArray;
	}
	
	public List<Integer> splitList(List<Integer> full) {
        List<Integer> first = new ArrayList<Integer>();
        List<Integer> second = new ArrayList<Integer>();
        int centre;

        if (full.size() == 1) {    
            return full;
        } else {
            centre = full.size() / 2;
            for (int i = 0; i < centre; i++) {
                    first.add(full.get(i));
            }
            for (int i = centre; i < full.size(); i++) {
                    second.add(full.get(i));
            }
            first  = splitList(first);
            second = splitList(second);
            mergeList(first, second, full);
        }
        return full;
    }

	public void mergeList(List<Integer> first, List<Integer> second, List<Integer> full) {
        int firstIndex = 0;
        int secondIndex = 0;
        int wholeIndex = 0;
        
        while (firstIndex < first.size() && secondIndex < second.size()) {
            if ( (first.get(firstIndex).compareTo(second.get(secondIndex))) < 0) {
                full.set(wholeIndex, first.get(firstIndex));
                firstIndex++;
            } else {
                full.set(wholeIndex, second.get(secondIndex));
                secondIndex++;
            }
            wholeIndex++;
        }
        List<Integer> rest;
        int restIndex;
        if (firstIndex >= first.size()) {
            rest = second;
            restIndex = secondIndex;
        } else {
            rest = first;
            restIndex = firstIndex;
        }

        for (int i=restIndex; i<rest.size(); i++) {
            full.set(wholeIndex, rest.get(i));
            wholeIndex++;
        }
    }
}