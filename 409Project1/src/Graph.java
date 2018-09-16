/* Christine Reed, Keri Chamberlain, Tuhinur Jaman
CMSC 409: Artificial Intelligence
Fall 2018
Project 1*/

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Graph extends JPanel {
	static float height;
	static int heightSize = 4000;
	static float mHeightArray[] = new float[heightSize];
	static float fHeightArray[] = new float[heightSize];

	public static void main(String[] args) throws IOException {
		int i = 0;
		String string;
		String maleStringArray[] = new String[heightSize];
		String femaleStringArray[] = new String[heightSize];
		int graphOffset = 100;
	
		// Read in data.txt from the source folder
		File file = new File("data.txt");
		
		// Shows path being read
        System.out.println("Attempting to read: "+ file.getCanonicalPath());
        
        // Scan in the text file
		try {
			Scanner s = new Scanner(file);
			System.out.println("Now reading...");
			while (s.hasNextLine()) {
				// Read in a single line at a time, while the text file has a next line.
				string = s.nextLine();
				
				// If the line ends with 0, that means the record is for a male, so the height will enter the male height array.
				if (string.endsWith("0")) {
					maleStringArray = string.split(",");
					height = Float.parseFloat(maleStringArray[0]);
					mHeightArray[i] = height*graphOffset;
				}
				
				// If the line ends with 1, that means the record is for a female, so the height will enter the female height array.
				else {
					femaleStringArray = string.split(",");
					height = Float.parseFloat(femaleStringArray[0]);
					fHeightArray[i] = height*graphOffset;
				}
				i++;
			}
			
			// Close the scanner.
			s.close();
		}
		// File not found error
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Create GUI for the graph
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGUI();
			}
		});
       
    }
	
    
	private static void createGUI() {
		//GUI will open in a new window.
		System.out.println("Opening GUI in new window... ");
		
		// Title of window
		JFrame graph = new JFrame("409 Project 1 graph");
		graph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		graph.setSize(1000,1000);
		graph.add(new MyPanel());
		graph.pack();
		graph.setVisible(true);
	}


	public static float[] mPointHeight() {
		return mHeightArray;
	}
	
	public static float[] fPointHeight() {
		return fHeightArray;
	}
	
}

// GUI class
class MyPanel extends JPanel {

	int rectWidth = 800;
	int rectHeight = 800;
	int marginLeft = 100;
	int marginTop = 900;
	int marginBottom = 300;
	static int heightSize = 4000;
	
    public MyPanel() {
        setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(1000,1000);
    }
    
    // Create the axis for the graph.
    void drawAxis(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.setColor(Color.BLACK);
    	Stroke stroke = new BasicStroke(3f);
    	g2d.setStroke(stroke);
    	// Draw height axis.
        g2d.drawLine(900,900,100,900);
        
        // Height label
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Height (ft)",480,970);
        
        // Key label
        g2d.setFont(new Font("Arial", Font.PLAIN, 15));
	    g2d.drawString("Key: ",810,975);
        int y = 0;
        
        // Height labels (number wise, in feet)
        for (int i=100;i<=900;i=i+100) {
           String[] heightLabels = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
           g2d.drawLine(i,890,i,900);
           g2d.drawString(heightLabels[y],i,940);
           y++;
        }

    }
   // Plot the points given by the text file. 
   void plot(Graphics g) {
   		Graphics2D g2d = (Graphics2D) g;
		Stroke stroke = new BasicStroke(5f);
		g2d.setStroke(stroke);
		
		// Create two arrays from the Graph class methods.
   		float mHeightArr[] = Graph.mPointHeight();
   		float fHeightArr[] = Graph.fPointHeight();

   		
   		// Plot male values on the graph.
   		for(int i=0;i<heightSize;i++) {
   	   		g2d.setColor(Color.BLUE);
   	        g2d.drawString("Male",855,965);
   			float heightPlot = mHeightArr[i];
   			if (heightPlot != 0.0) {
   				Shape shape = new Line2D.Float(marginLeft+ heightPlot, marginTop, marginLeft+ heightPlot, marginTop);
   				g2d.draw(shape);
   			}
   		}
   		
   		// Plot female values on the graph.
   		for(int i=0;i<heightSize;i++) {
   	   		g2d.setColor(Color.RED);
   	        g2d.drawString("Female",855,985);
   			float heightPlot = fHeightArr[i];
   			if (heightPlot != 0.0) {
   				Shape shape = new Line2D.Float(marginLeft+ heightPlot, marginTop+2, marginLeft+ heightPlot, marginTop+2);
   				g2d.draw(shape);
   			}
   		}
    }
    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        
        // Title and header of graph
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Project 1 - Scenario 1",400,60);
        g.setFont(new Font("Arial", Font.PLAIN, 15)); 
        g.drawString("CMSC 409 - Artificial Intelligence",415,80);
        
        // Create graph rectangle 
        g.setColor(Color.WHITE);
        g.fillRect(100, 100, rectWidth, rectHeight);
        
        // Graph functions to create graph
        drawAxis(g);
        plot(g);
    }  
}