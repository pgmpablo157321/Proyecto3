import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManagement {

	public static BufferedImage makeImage(int[] pixels, int w, int h) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				img.setRGB(j, i, pixels[i * w + j]);
			}
		}
		return img;
	}

	public static int[] binarizeImage(BufferedImage img) {
		int[] pixels = new int[img.getWidth() * img.getHeight()];
		Color myWhite = new Color(0, 0, 0);
		Color myBlack = new Color(255, 255, 255);
		int rgbBlanco = myWhite.getRGB();
		int rgbNegro = myBlack.getRGB();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color pixel = new Color(img.getRGB(j, i));
				if (pixel.getBlue() > 217 && pixel.getRed() > 217 && pixel.getGreen() > 217) {
					pixels[i * img.getWidth() + j] = rgbNegro;
				} else {
					pixels[i * img.getWidth() + j] = rgbBlanco;
				}
			}
		}
		return pixels;
	}

	public static boolean imageToArf(BufferedImage[] list, char[] labels) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter("C:/Users/pgmpa/eclipse-workspace/Proyecto3/data/train.txt");
			bw = new BufferedWriter(fw);
			bw.write("@relation train");
			bw.newLine();
			for (int i = 0; i < 768; i++) {
				bw.write("@attribute pixel" + i + "{0,1}");
				bw.newLine();
			}
			bw.write("@attribute label {a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}");
			bw.newLine();
			bw.write("@data");
			bw.newLine();
			for (int i = 0; i < list.length; i++) {
				for (int j = 0; j < 28; j++) {
					for (int k = 0; k < 28; k++) {
						Color mycolor = new Color(list[i].getRGB(k, j));
						if (mycolor.getBlue() == 0 && mycolor.getRed() == 0 && mycolor.getGreen() == 0) {// Negro
							bw.write("1,");
						} else if (mycolor.getBlue() == 255 && mycolor.getRed() == 255 && mycolor.getGreen() == 255) {// Blanco
							bw.write("0,");
						}
					}
				}
				bw.write(labels[i]);
				bw.newLine();
			}
		} catch (IOException e) {

		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		//Main de Binarize
		File inputfile=new File("path");
		BufferedImage image = ImageIO.read(inputfile);	
		int pixels[] = ImageManagement.binarizeImage(image);
		BufferedImage output=ImageManagement.makeImage(pixels, image.getWidth(), image.getHeight());
		ImageIO.write(output, "png", new File ("path"));
		//
		//Main de ImgToArf
		
		//
	}
}
