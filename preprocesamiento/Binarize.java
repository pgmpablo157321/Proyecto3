package Split;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Binarize{

	public static BufferedImage getImagen(int[] pixels){

		BufferedImage img=new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_BINARY);
		for(int i=0;i<img.getHeight();i++){
			for(int j=0;j<img.getWidth();j++){
				img.setRGB(j, i, pixels[i*2048+j]);
			}
		}
		return img;
	}

	public static int[] Initialize(BufferedImage a) {
		int[]pixels=new int[a.getWidth()*a.getHeight()];
		Color myWhite=new Color(0,0,0);
		Color myBlack=new Color(255,255,255);
		int rgbBlanco=myWhite.getRGB();
		int rgbNegro=myBlack.getRGB();
		for(int i=0;i<a.getHeight();i++) {
			for(int j=0; j<a.getWidth();j++) {
				Color pixel = new Color(a.getRGB(j,i));
				if(pixel.getBlue()==255 && pixel.getRed()==255 && pixel.getGreen()==255) {
					pixels[i*a.getWidth()+j]=rgbBlanco;
				}else{
					pixels[i*a.getWidth()+j]=rgbNegro;
				}
			}
		}
		return pixels;
	}

	public static void main(String[] args) throws IOException {
		File outputfile=new File("/home/oquendo/Desktop/Proyecto3/images/At_100x100_iconjake.jpg");
		BufferedImage image = ImageIO.read(outputfile);	
		int pixels[] = Binarize.Initialize(image);
		
	}
}
