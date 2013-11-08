
   import javax.swing.*;

    public class LOCSDriver
   {
      JFrame window;
      LegendPanel l;
   
       public LOCSDriver()
      {
         window = new JFrame("LOCS");
         l = new LegendPanel();
         window.setSize(500, 550);
         window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         window.add(l);
         window.setVisible(true);
         l.go();
      }
       public static void main(String[] args)
      {
         LOCSDriver locs = new LOCSDriver();
      }
   }