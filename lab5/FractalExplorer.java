package lab5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @author esorey
 *
 * This class provides a GUI for displaying fractals. It also houses
 * a main function that runs the application.
 */
public class FractalExplorer {
    /** Side-length of our square display area. **/
    private int dispSize;
    /** Image area for our fractal. **/
    private JImageDisplay jImage;
    /** Used to generate fractals of a specified kind. **/
    private FractalGenerator fractal;
    private FractalGenerator fractalMandelbrot;
    private FractalGenerator fractalBurningShip;
    private FractalGenerator fractalTricorn;
    /** The current viewing area in our image. **/
    private Rectangle2D.Double range;
    
    /** Basic constructor. Initializes the display image, fractal generator,
     * and initial viewing area.
     */
    public FractalExplorer(int dispSize) {
        this.dispSize = dispSize;        
        this.fractalBurningShip = new BurningShip();
        this.fractalMandelbrot = new Mandelbrot();
        this.fractalTricorn = new Tricorn();
        this.fractal = fractalTricorn; // init fractal is Mandelbrot
        this.range = new Rectangle2D.Double(0, 0, 0, 0);
        fractal.getInitialRange(this.range);        
    }
    
    /**
     * Sets up and displays the GUI.
     */
    public void createAndShowGUI() {
        /** Create the GUI components. **/
        JFrame frame = new JFrame("Fractal Explorer");
        jImage = new JImageDisplay(dispSize, dispSize); 
        
        // conntainer to contain Label and combobox
        JPanel fractalPanel = new JPanel(new FlowLayout());
        JComboBox<String> comboBox = new JComboBox<>();
        JLabel comboboxLabel = new JLabel("fractal : ");
        
        // Tạo container để chứa 2 button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton resetButton = new JButton("Reset");
        JButton saveButton = new JButton("Save");

        
        /** Add listeners to components. **/
        resetButtonHandler resetHandler = new resetButtonHandler();
        saveButtonHandler saveHandler = new saveButtonHandler();
        MouseHandler mHandler = new MouseHandler();
        ComboboxHandler cHandler = new ComboboxHandler();
        
        resetButton.addActionListener(resetHandler);
        saveButton.addActionListener(saveHandler);
        comboBox.addActionListener(cHandler);
        
        jImage.addMouseListener(mHandler);
        
        
        /** add container **/        
        // container button
        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);
        
        // container combobox
        fractalPanel.add(comboboxLabel);
        fractalPanel.add(comboBox);      
        comboBox.addItem("Tricorn");
        comboBox.addItem("Mandelbrot");
        comboBox.addItem("Burning Ship");
        
        
        /** Put all of the components into the Frame. **/
        frame.setLayout(new BorderLayout());
        frame.add(jImage, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(fractalPanel,BorderLayout.NORTH );
        
        
        /** Display the image. **/
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /** Use the fractal generator to draw the fractal pixel by pixel. **/
    private void drawFractal() {
        // each row j
        for (int j = 0; j < dispSize; j++) {
            FractalWorker drawRowJ = new FractalWorker(j);
            drawRowJ.execute();
        }

    }

    
    /** Simple Class for RESET BUTTON **/
    public class resetButtonHandler implements ActionListener { 
        public void actionPerformed(ActionEvent e) {
        	fractal.getInitialRange(range);
            drawFractal();
        }
    }
    
    /** simple class for SAVE BUTTON : save to file**/
    public class saveButtonHandler implements ActionListener {    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	JFileChooser fileChooser = new JFileChooser();

            // Set up the file filter for PNG images
            FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);

            // Show the "Save file" dialog
            int userChoice = fileChooser.showSaveDialog(jImage);
           
            if (userChoice == JFileChooser.APPROVE_OPTION) {
                // User selected a file to save
                File selectedFile = fileChooser.getSelectedFile();
                try {
                	BufferedImage image = jImage.img;

                    // Save the image to disk as a PNG file
                    ImageIO.write(image, "png", selectedFile);
                    // Show a success message dialog
                    String message = "Image saved successfully!";
                    String title = "Image Saved";
                    JOptionPane.showMessageDialog(jImage, message, title, JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    // Error occurred while saving the image
                    String errorMessage = ex.getMessage();
                    String title = "Cannot Save Image";
                    System.out.println(title + errorMessage);
                    JOptionPane.showMessageDialog(jImage, errorMessage, title, JOptionPane.ERROR_MESSAGE);
                }
            }
            drawFractal();
        }
    }
    
    /** simple class for COMBO BOX : switch fractal **/
    public class ComboboxHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	JComboBox<String> source = (JComboBox<String>) e.getSource();
            String selectedOption = (String) source.getSelectedItem();  
            System.out.println("selectedOption is : " + selectedOption);
            
            switch (selectedOption) {
            case "Mandelbrot":
                fractal =  fractalMandelbrot;
                break;
            case "Tricorn":
            	fractal = fractalTricorn;
            	break;
            case "Burning Ship" : 
            	fractal = fractalBurningShip;
            	break;
            }
            
            drawFractal();
        }
    }
    
    /** Simple class handler to zoom in on the clicked pixel on a fractal. **/
    public class MouseHandler extends MouseAdapter {
    	
        @Override
        public void mouseClicked(MouseEvent e) {
            long startTime = System.currentTimeMillis();

            double xCoord = FractalGenerator.getCoord(range.x, 
                    range.x + range.width, dispSize, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, 
                    range.y + range.width, dispSize, e.getY());
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
            

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Total time taken: " + elapsedTime + " milliseconds");
        }
    }
    /** fractal worker for multithread : COMPUTE and DRAW ONE ROW **/
    private class FractalWorker extends SwingWorker<Object, Object> {
    	private int rowY;
    	private int[] arrayY;
    	
    	FractalWorker(int rowY) {
    		this.rowY = rowY;
    	}
    	
    	public Object doInBackground() {
    		arrayY = new int[dispSize];
            for (int i = 0; i < dispSize; i++) {
                double xCoord = FractalGenerator.getCoord(range.x, 
                                range.x + range.width, dispSize, i);
                double yCoord = FractalGenerator.getCoord(range.y, 
                        range.y + range.width, dispSize, rowY);
                double numIters = fractal.numIterations(xCoord, yCoord);
                
                if (numIters == -1) {
                    /** The pixel is not in the set. Color it black. **/
                    arrayY[i] = 0;
                }
                else {
                    /** The pixel is in the fractal set.
                     *  Color the pixel based on the number of iterations
                     *  it took to escape. 
                     */
                    float hue = 0.7f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    arrayY[i] = rgbColor;
                }
            }
            
    		return null;
    	}
    	
    	public void done() {
    		for (int i = 0; i < dispSize; i++) 
    			jImage.drawPixel(i, rowY, arrayY[i]);
    		jImage.repaint(0, rowY, dispSize, 1);
    	}
    }
    
    /** Run the application. **/
    public static void main(String[] args) {
        FractalExplorer fracExp = new FractalExplorer(900);
        fracExp.createAndShowGUI();
        fracExp.drawFractal();
    }
}