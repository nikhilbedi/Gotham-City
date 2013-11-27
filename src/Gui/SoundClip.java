package Gui;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
   
// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class SoundClip {
   
   // Constructor
   public SoundClip() {
      try {
         // Open an audio input stream.
         URL url = this.getClass().getClassLoader().getResource("Gui/The_Dark_Knight.wav");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      } catch (IllegalArgumentException e) {
          e.printStackTrace();
       }
      
   }

}