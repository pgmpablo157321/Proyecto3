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
				if (pixel.getBlue() > 210 && pixel.getRed() > 210 && pixel.getGreen() > 210) {
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
			for (int i = 0; i < 1024; i++) {
				bw.write("@attribute pixel" + i + "{0,1}");
				bw.newLine();
			}
			bw.write("@attribute label {a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}");
			bw.newLine();
			bw.write("@data");
			bw.newLine();
			for (int i = 0; i < list.length; i++) {
				for (int j = 0; j < 32; j++) {
					for (int k = 0; k < 32; k++) {
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
	
	public static void getDivitions(BufferedImage a) throws IOException {
		
		
		Color myWhite=Color.WHITE;
		Color myBlack=Color.BLACK;
		Color myRed=Color.RED;
		int rgbBlanco=myWhite.getRGB();
		int rgbNegro=myBlack.getRGB();
		int rgbRojo=myRed.getRGB();
		Queue<Integer>dH=new LinkedList<Integer>();
		Queue<Integer>dV=new LinkedList<Integer>();
		boolean x=true;
		for(int i=0; i<a.getHeight();i++) {
			boolean y=x;
			if(x) {
				for(int j=0;j<a.getWidth();j++) {
					if(a.getRGB(j, i)==rgbNegro) {
						x=!x;
						j=a.getHeight();
						
					}
				}
				if(x!=y) {
					dH.add(i-2);
				}
			}else {
				x=!x;
				for(int j=0;j<a.getWidth();j++) {
					if(a.getRGB(j, i)==rgbNegro) {
						x=!x;
						j=a.getHeight();
						
					}
				}
				if(x!=y) {
					dH.add(i+2);
				}
			}
			
			
			
		}
		
		BufferedImage l[]=new BufferedImage[dH.size()/2];
		int cont=0;
		for(int i=0;i<l.length;i++) {
			l[i]=a.getSubimage(0, dH.peek(), a.getWidth(), -dH.poll()+dH.poll());
			x=true;
			for(int k=0; k<l[i].getWidth();k++) {
				boolean y=x;
				if(x) {
					for(int j=0;j<l[i].getHeight();j++) {
						if(l[i].getRGB(k, j)==rgbNegro) {
							x=!x;
							j=a.getHeight();
							
						}
					}
					if(x!=y) {
						dV.add(k-2);
					}
				}else {
					x=!x;
					for(int j=0;j<l[i].getHeight();j++) {
						if(l[i].getRGB(k, j)==rgbNegro) {
							x=!x;
							j=a.getHeight();
							
						}
					}
					if(x!=y) {
						dV.add(k+2);
					}
				}
			}
			while(!dV.isEmpty()) {
				BufferedImage aux=l[i].getSubimage(dV.peek(), 0, -dV.poll()+dV.poll(), l[i].getHeight());
				ImageIO.write(redimencion(aux,32), "png", new File ("C:/Users/pgmpa/eclipse-workspace/Proyecto3/data/Imagenes/c/output"+cont+".png"));
				cont++;
			}
			
			
		}	
		
	}
	
	
	public static BufferedImage redimencion(BufferedImage a, int n) {
		BufferedImage temp=new BufferedImage(n, n, BufferedImage.TYPE_BYTE_BINARY);
		boolean v=true;
		boolean h=true;
		int V1, V2 = 0, H1, H2 = 0;
		for(int i=0;i<n;i++) {
			for(int j=0; j<n; j++) {
				temp.setRGB(i, j, Color.WHITE.getRGB());
			}
		}
		
		
		if(a.getWidth()<=n) {
			V1=(n-a.getWidth())/2;
		}else {
			V1=0;
			V2=a.getWidth()-1;
			while(V2-V1+1>n) {
				if(count(a,V1,true)>count(a, V2, true)) {
					V2--;
				}else if(count(a,V1,true)<count(a, V2, true)) {
					V1++;
				}else {
					if((int)(Math.random()*2)==1) {
						V2--;
					}else {
						V1++;
					}
				}
			}
			v=false;
		}
		if(a.getHeight()<=n) {
			H1=(n-a.getHeight())/2;
		}else {
			H1=0;
			H2=a.getHeight()-1;
			while(H2-H1+1>n) {
				if(count(a,H1,false)>count(a, H2, false)) {
					H2--;
				}else if(count(a,H1,false)<count(a, H2, false)) {
					H1++;
				}else {
					if((int)(Math.random()*2)==1) {
						H2--;
					}else {
						H1++;
					}
				}
			}
			h=false;
		}
		
		if(v&&h) {
			for(int i=V1;i<a.getWidth()+V1;i++) {
				for(int j=H1;j<a.getHeight()+H1;j++) {
					temp.setRGB(i, j, a.getRGB(i-V1, j-H1));
				}
			}
		}else if(v && !h){
			for(int i=V1;i<a.getWidth()+V1;i++) {
				for(int j=H1;j<H2;j++) {
					temp.setRGB(i, j-H1, a.getRGB(i-V1, j));
				}
			}
		}else if(!v && h){
			for(int i=V1;i<V2;i++) {
				for(int j=H1;j<a.getHeight()+H1;j++) {
					temp.setRGB(i-V1, j, a.getRGB(i, j-H1));
				}
			}
		}else if(!v && !h){
			for(int i=V1;i<V2;i++) {
				for(int j=H1;j<H2;j++) {
					temp.setRGB(i-V1, j-H1, a.getRGB(i, j));
				}
			}
		}
		
		
		
		
		return temp;
		
	}
	
	
	public static int count(BufferedImage a, int p, boolean f) {
		int ans=0;
		if(f) {
			for(int i=0;i<a.getHeight();i++) {
				if(a.getRGB(p, i)==Color.BLACK.getRGB()) {
					ans++;
				}
			}
		}else {
			for(int i=0;i<a.getWidth();i++) {
				if(a.getRGB(i, p)==Color.BLACK.getRGB()) {
					ans++;
				}
			}
		}
		return ans;
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
		/*File inputfile=new File("C:/Users/pgmpa/eclipse-workspace/Proyecto3/data/Imagenes/c.jpg");
		BufferedImage image = ImageIO.read(inputfile);	
		int pixels[] = Binarize.Initialize(image);
		BufferedImage output=Binarize.getImagen(pixels, image.getWidth(), image.getHeight());
		getDivitions(output);*/
	}
}
