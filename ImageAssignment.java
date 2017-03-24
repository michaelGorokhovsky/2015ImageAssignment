import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
public class ImageAssignment {

    /* First, some utility methods that you will need in the methods you write.
       Do not modify these methods in any way. */

    public static int getRed(int rgb) { return (rgb >> 16) & 0xff; }
    public static int getGreen(int rgb) { return (rgb >> 8) & 0xff; }
    public static int getBlue(int rgb) { return rgb & 0xff; }
    public static int rgbColour(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }
    public static double brightness(int rgb) {
        int r = getRed(rgb);
        int g = getGreen(rgb);
        int b = getBlue(rgb);
        return 0.21*r + 0.72*g + 0.07*b;
    }

    public static BufferedImage convertToGrayscale(BufferedImage img) {
        BufferedImage result = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB
            );
        for(int x = 0; x < img.getWidth(); x++) {
            for(int y = 0; y < img.getHeight(); y++) {
                int col = img.getRGB(x, y);
                int gr = (int)brightness(col);
                result.setRGB(x, y, rgbColour(gr, gr, gr));
            }
        }
        return result;
    }

    /* ----------- Methods that you will write in this assignment. */

    public static BufferedImage thresholdImage(BufferedImage img, int threshold) {
        // fill this in
    	BufferedImage result = new BufferedImage( // creating result image
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB
            );  
    	for(int x = 0; x < img.getWidth(); x++) {      // looping through rows
            for(int y = 0; y < img.getHeight(); y++) { // looping through columns
                int col = img.getRGB(x, y);           // getting color at each pixel
                int gr = (int)brightness(col);        // getting brightness for each color at each pixel
                if (gr > threshold)                   // checking if color is greater than threshhold
                	result.setRGB(x, y, rgbColour(255, 255, 255));
                else                                  // setting color to black or white
                	result.setRGB(x, y, rgbColour(0, 0, 0));
                }
            }
        return result;
    }
    	
    public static BufferedImage horizontalMirror(BufferedImage img) {
        // fill this in
    	BufferedImage result = new BufferedImage(  // creating result image
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB
            );
    	for(int x = 0; x < img.getWidth(); x++) {  // looping through rows
            for(int y = 0; y < img.getHeight(); y++) {  // looping through columns
            	int col = img.getRGB(x,  y);         // getting color at each pixel
            	result.setRGB(img.getWidth() - x - 1, y, col);  // setting colour at each pixel to the coulour at original image position width -1-x
            	
            }
    	}
    	return result;
    }

   public static BufferedImage splitToFour(BufferedImage img) {
       //  fill this in
    	BufferedImage quart = new BufferedImage(
                img.getWidth() / 2, img.getHeight() / 2, BufferedImage.TYPE_INT_RGB  //new image to be a quarter the size of original image
            );
    	BufferedImage result = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB   //result image to consist of four "quart" images
            );        
    	for(int x = 0; x < img.getWidth(); x++) {   // looping through rows
            for(int y = 0; y < img.getHeight(); y++) {  // looping through columns
            	int col = img.getRGB(x, y);     // getting color at each pixel
            	if (x % 2 == 0 && y % 2 == 0){   //we only want to print every fourth pixel
            		quart.setRGB(x/2, y/2, col);
            	}
            }
        }

    	for (int i = 0; i < 4; i++) {
    		for(int x = 0; x < quart.getWidth(); x++) {   // looping through rows
                for(int y = 0; y < quart.getHeight(); y++) {  // looping through columns
                	int color = quart.getRGB(x, y);    // getting color at each pixel
                        if (i == 0)              //assigning each of the four quarters to a section of result
                           result.setRGB(x , y , color);
                        else if (i == 1)
                           result.setRGB(x + quart.getWidth(), y, color);
                        else if (i == 2)
                            result.setRGB(x, y + quart.getHeight(), color);
                        else if (i == 3)
                            result.setRGB(x + quart.getWidth(), y + quart.getHeight(), color);
                }
    		}
    	}
    	
    	return result;
    }

    public static BufferedImage imageCorrelation(BufferedImage img, double[][] mask) {
    	BufferedImage result = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB
            );

        for (int y = 1; y < img.getHeight() - 1; y++)  
        {
            for (int x = 1; x < img.getWidth() - 1; x++)
            {   
                int sumR = 0;     // total red value for each pixel
                int sumG = 0;    // total green value for each pixel
                int sumB = 0;    // total blue value for each pixel
                int imageI = x - 1; //x-coordinate of pixel to start process
                int imageJ = y -1;  // y-coordinate of pixel to start process
                int i = 0;       // x-coordinate of first value in mask
                int j = 0;       // y-coordinate of first value in mask
                while (j < 3)
                {
                    while (i<3)
                    {
                        sumR += ((getRed(img.getRGB(imageI,imageJ))) * mask[i][j]);  //getting red
                        sumB += ((getBlue(img.getRGB(imageI,imageJ))) * mask[i][j]);  //getting blue
                        sumG += ((getGreen(img.getRGB(imageI,imageJ))) * mask[i][j]);  //getting green
                        i++;     // moving to mask value to the right
                        imageI++; // next pixel, to the right
                    }
                   j++;    // moving down to next row in mask
                   imageJ ++;  //  // moving down to next row in image
                   i = 0;   // going back to leftmost value in mask
                   imageI = x-1;  // going back to leftmost value in image
                }
                //setting all colours to black or white if they are over 255 or under 0
                if (sumR > 255)   
                {
                    sumR = 255;  
                } else if (sumR <0)
                {
                    sumR = 0;
                }
                 if (sumB > 255)
                {
                    sumB = 255;
                } else if (sumB <0)
                {
                    sumB = 0;
                }
                 if (sumG > 255)
                {
                    sumG = 255;
                } else if (sumG <0)
                {
                    sumG = 0;
                }
                result.setRGB(x, y, rgbColour(sumR, sumG, sumB));
            }
        }
        //setting the edges to black
        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                if (y == 0 || y == img.getHeight() -1 || x == 0 || x == img.getWidth() - 1)
                {
                    result.setRGB(x, y, rgbColour(0, 0, 0));
                }
            }
        }
    	return result;
    }
    
    public static BufferedImage rowPixelSort(BufferedImage img, int n) {
    	BufferedImage result = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB
            );
        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                int col = img.getRGB(x,y);   // getting colour at each pixel
                result.setRGB(x, y, col);   // setting result to same as initial image
            }
        }
    	for(int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth() - 1; x++) {
              
                  for (int i = 1; i <img.getWidth()-x; i++)
                  {
                       int col = result.getRGB(i - 1, y);  //getting colours
                       int oldCol = result.getRGB(i,  y);  //getting colour at position to the left
                       int gr = (int)brightness(col);     // getting brightness at current position
                       int oldGr = (int)brightness(oldCol); //getting brightness at position to the left
                       //sorting colours
                       if (gr > oldGr)
                       {
                           result.setRGB(i - 1, y, oldCol);
                           result.setRGB(i, y, col);
                           
                       }
                  }
                            
            }
           
        }
    	return result;
    }
    // ------------------------------------ end of your code

    /* A utility method we need to convert Image objects to BufferedImage, copied from 
     * http://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) { return (BufferedImage) img;}
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB
            );
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }

    /* A utility method to create a JPanel instance that displays the given Image. */
    public static JPanel createPanel(Image img) {
        // Define a local class from which we create an object to return as result.
        class ImagePanel extends JPanel {
            private Image img;
            public ImagePanel(Image img) {
                this.img = img;
                this.setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
            }

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, this);
            }
        }
        return new ImagePanel(img);
    }

    /* The main method to try out the whole shebang. */
    public static void main(String[] args) {
        Image img = Toolkit.getDefaultToolkit().getImage("ryerson1.jpg");
        MediaTracker m = new MediaTracker(new JPanel());
        m.addImage(img, 0);
        try { m.waitForAll(); } catch(InterruptedException e) { }
        BufferedImage bimg = toBufferedImage(img); 
        JFrame f = new JFrame("CCPS 109 Lab 7");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(new GridLayout(2, 3));
        f.add(createPanel(thresholdImage(bimg, 150)));
        f.add(createPanel(horizontalMirror(bimg)));
        f.add(createPanel(splitToFour(bimg)));
        double wt = 1.0/9;
        double[][] blur = {{wt,wt,wt},{wt,wt,wt},{wt,wt,wt}};
        f.add(createPanel(imageCorrelation(bimg, blur)));
        double[][] edged ={{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
        f.add(createPanel(imageCorrelation(convertToGrayscale(bimg), edged)));
        //double [][] sharpen = {{0,-1,0},{-1,5,-1},{0,-1,0}};
        //f.add(createPanel(imageCorrelation(bimg, sharpen)));
        f.add(createPanel(rowPixelSort(bimg, bimg.getWidth())));
        f.pack();
        f.setVisible(true); 
    }
}
