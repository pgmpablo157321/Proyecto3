package Split;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Binarizate {
	
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
	int rgb1=myWhite.getRGB();
	int rgb2=myBlack.getRGB();
	for(int i=0;i<a.getHeight();i++) {
		for(int j=0; j<a.getWidth();j++) {
			//Oquendo completar este if
			if() {
				//si es blanco
				pixels[i*a.getWidth()+j]=rgb1;	
			}else {
				//si es negro
				pixels[i*a.getWidth()+j]=rgb2;
			}
			
		}
	}
	return pixels;
}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*int []pixels=new int[768];
		Color myWhite=new Color(0,0,0);
		Color myBlack=new Color(255,255,255);
		int rgb1=myWhite.getRGB();
		int rgb2=myBlack.getRGB();
		File outputfile=new File("C:/Users/pgmpa/eclipse-workspace/Proyecto3/data/Imagenes/prueba1.jpg");
	    try {
			ImageIO.write(getImagen(pixels), "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}
}
