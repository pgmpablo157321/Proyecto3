package Split;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Binarize{

	public static BufferedImage getImagen(int[] pixels, int w, int h){

		BufferedImage img=new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
		for(int i=0;i<img.getHeight();i++){
			for(int j=0;j<img.getWidth();j++){
				img.setRGB(j, i, pixels[i*w+j]);
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
				if(pixel.getBlue()>217 && pixel.getRed()>217 && pixel.getGreen()>217) {
					pixels[i*a.getWidth()+j]=rgbNegro;
				}else{
					pixels[i*a.getWidth()+j]=rgbBlanco;
				}
			}
		}
		return pixels;
	}

	public static void main(String[] args) throws IOException {
		File inputfile=new File("path");
		BufferedImage image = ImageIO.read(inputfile);	
		int pixels[] = Binarize.Initialize(image);
		BufferedImage output=getImagen(pixels, image.getWidth(), image.getHeight());
		ImageIO.write(output, "png", new File ("path"));
		
	}
}
