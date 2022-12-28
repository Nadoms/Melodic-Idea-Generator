import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import jm.music.data.*;
import jm.music.tools.Mod;
import jm.JMC;
import jm.util.*;

public class DrumsGeneration implements JMC {
	
	boolean kickOn, snareOn, closedHatsOn, openHatsOn, rideOn, cymbalOn, tomsOn = true;
	
	public static void main (String [] args) {
	}
	
	public DrumsGeneration() {
		
		ParaGUI paragui = new ParaGUI();
		
		DrumsGUI drumsgui = new DrumsGUI();
		// Getting values from DrumsGUI.
		int kickVol = drumsgui.getKickVol();
		int snareVol = drumsgui.getKickVol();
		int closedHatsVol = drumsgui.getKickVol();
		int openHatsVol = drumsgui.getKickVol();
		int rideVol = drumsgui.getKickVol();
		int cymbalVol = drumsgui.getKickVol();
		int tomsVol = drumsgui.getKickVol();
		int drumsComp = drumsgui.getDrumsComp();
		// Checking if any aren't 0.
		if (!(intDefault(kickVol))) {generateKick(kickVol);}
		if (intDefault(snareVol)) {snareOn = false;}
		if (intDefault(closedHatsVol)) {closedHatsOn = false;}
		if (intDefault(openHatsVol)) {openHatsOn = false;}
		if (intDefault(rideVol)) {rideOn = false;}
		if (intDefault(cymbalVol)) {cymbalOn = false;}
		if (intDefault(tomsVol)) {tomsOn = false;}
		System.out.println(kickVol);
		System.out.println(kickOn);
		Score t = Generation.getScore();
	}
	
	public void generateDrums() {
		
	}
	
	public void generateKick(int localKickVol) {
		
		Part kick = new Part("Kick", 35, 9);
		Phrase kickPhr = new Phrase();
		Random rnd = new Random();
		
	}
	
	/*
	public void generateMelody(int localTempoValue, int localDurationValue, int[] localModeArrayChoice, int localRootNote, int localStepValue, boolean localMetronomeChoice) {
		
		
		// In every bar k the specified duration of bars,
		for (int k = 0; k <= (localDurationValue / 2); k++) {
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
						if (currentPitch % 12 == localModeArrayChoice[j]) {
							// And if it's within an octave of the initial pitch too,
							if ((currentPitch < (initialPitch + 12)) && (currentPitch > (initialPitch - 12))) {
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
		s.setTempo(localTempoValue);
		Play.midi(s);
		Write.midi(s, "First Melody");
		View.show(s);
	}
	
	*/
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
	
	public boolean intDefault(int isItZero) {
		if (isItZero == 0) {
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
}