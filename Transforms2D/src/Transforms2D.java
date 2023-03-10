import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Transforms2D extends JPanel {

	private class Display extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			int whichTransform = transformSelect.getSelectedIndex();
			AffineTransform transform = new AffineTransform();
			
			switch(whichTransform) {
			  case 1:
				  	double scale = 0.5;
				  	transform.scale(scale, scale);
				  	transform.translate(display.getWidth()/(2*scale), display.getWidth()/(2*scale));
				  	break;
			  case 2:
				  	double angle = 45;
				  	transform.rotate(Math.toRadians(angle),display.getWidth()/2,display.getHeight()/2);
				  	transform.translate(display.getWidth()/2,display.getHeight()/2);
				  	break;
			  case 3:
				  	double scale3 = 0.5;
				  	transform.translate(display.getWidth()/2,display.getHeight()/2);
				  	transform.scale(-scale3, scale3*1.5);
				  	break;
			  case 4:
				  	double skew = 15;
				  	transform.translate(display.getWidth()/2,display.getHeight()/2);
				  	transform.shear(Math.tan(Math.toRadians(skew)),0);
					break;
			  case 5:
				  	double scale5 = 0.5;
				  	transform.translate(display.getWidth()/2,pic.getHeight()/2.925);
				  	transform.scale(1, scale5);
					break;
			  case 6:
				  	double skew6 = 15;
				  	double angle6 = 90;
				  	transform.rotate(Math.toRadians(angle6),display.getWidth()/2,display.getHeight()/2);
				  	transform.translate(display.getWidth()/2,display.getHeight()/2);
				  	transform.shear(Math.tan(Math.toRadians(skew6)),0);
					break;
			  case 7:
				  double scale7 = 0.5;
				  	transform.translate(display.getWidth()/2,display.getHeight()/2);
				  	transform.scale(-scale7, -scale7*1.5);
					break;
			  case 8:
				  	double angle8 = 30;
				  	double scale8 = 0.5;
				  	transform.rotate(Math.toRadians(angle8),display.getWidth()/2,display.getHeight()/2);
				  	transform.translate(display.getWidth()*0.6,display.getHeight()*0.66);
				  	transform.scale(1, scale8);
					break;
			  case 9:
				  	double angle9 = 180;
				  	double shear9 = 15;
				  	transform.rotate(Math.toRadians(angle9),display.getWidth()/2,display.getHeight()/2);
				  	transform.translate(display.getWidth()*0.33,display.getHeight()*0.45);
				  	transform.shear(0,Math.tan(Math.toRadians(shear9)));
					break;
			  default:
				  transform.translate(display.getWidth()/2,display.getHeight()/2);  // Moves (0,0) to the center of the display.
			}
			
		  	g2.setTransform(transform);
			g2.drawImage(pic, -200, -200, null); // Draw image with center at (0,0).
		}
	}

	private Display display;
	private BufferedImage pic;
	private JComboBox<String> transformSelect;

	public Transforms2D() throws IOException {
				
		Polygon polygon = new Polygon();
		
		int sides = 15;
		int x = 200;
		int y = 200;
		int r = 150;
		
		for(int i=0;i<sides;i++) {
			double angle = i * 2 * Math.PI/sides;
			int tempX = (int)(x + r * Math.cos(angle));
			int tempY = (int)(y + r * Math.sin(angle));
			polygon.addPoint(tempX, tempY);
		}
		
		pic = new BufferedImage (400,400,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = pic.createGraphics();
		g.setColor(Color.yellow);
		g.fill(getBounds());
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(7));
		g.draw(polygon);
		g.drawRect(0, 0, pic.getWidth(), pic.getHeight());
		
		display = new Display();
		display.setBackground(Color.YELLOW);
		display.setPreferredSize(new Dimension(600,600));
		transformSelect = new JComboBox<String>();
		transformSelect.addItem("None");
		for (int i = 1; i < 10; i++) {
			transformSelect.addItem("No. " + i);
		}
		transformSelect.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.repaint();
			}
		});
		JButton printpict = new JButton("Print");
		printpict.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				BufferedImage image = new BufferedImage(
			            display.getBounds().width, 
			            display.getBounds().height,
			            BufferedImage.TYPE_INT_ARGB);
			    display.print(image.getGraphics());
				
				File outputfile = new File("image" + transformSelect.getSelectedIndex() + ".png");
				try {
					ImageIO.write(image, "png", outputfile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setLayout(new BorderLayout(3,3));
		setBackground(Color.GRAY);
		setBorder(BorderFactory.createLineBorder(Color.GRAY,10));
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.CENTER));
		top.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		top.add(new JLabel("Transform: "));
		top.add(transformSelect);
		top.add(printpict);
		add(display,BorderLayout.CENTER);
		add(top,BorderLayout.NORTH);
	}


	public static void main(String[] args) throws IOException {
		JFrame window = new JFrame("2D Transforms");
		window.setContentPane(new Transforms2D());
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation( (screen.width - window.getWidth())/2, (screen.height - window.getHeight())/2 );
		window.setVisible(true);
	}

}