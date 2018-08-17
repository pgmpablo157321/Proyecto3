package Split;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgToArf {
	
	public static boolean ImgToArf(BufferedImage[] list, char [] labels, String name) {
		FileWriter fw = null;
		BufferedWriter bw=null;
		
		try {
			fw= new FileWriter("C:/Users/pgmpa/eclipse-workspace/Proyecto3/data/"+name+".arff");
			bw=new BufferedWriter(fw);
			bw.write("@relation train");
			bw.newLine();
			for(int i=0;i<1024;i++) {
				bw.write("@attribute pixel"+i+"{0,1}");
				bw.newLine();
			}
			bw.write("@attribute label {a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}");
			bw.newLine();
			bw.write("@data");
			bw.newLine();
			for(int i=0;i<list.length;i++) {
				for(int j=0;j<32;j++) {
					for(int k=0;k<32;k++) {
						Color mycolor = new Color(list[i].getRGB(k, j));
						if (mycolor.getBlue()==0 && mycolor.getRed()==0 && mycolor.getGreen()==0) {//Negro
							bw.write("1,");
						}else if(mycolor.getBlue()==255 && mycolor.getRed()==255 && mycolor.getGreen()==255) {//Blanco
							bw.write("0,");
						}
					}
				}
				bw.write(labels[i]);
				bw.newLine();
			}
				
			
			} catch (IOException e) {
			
		}finally{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	
	public static void create(char[]labels) throws IOException {
		BufferedImage train[]=new BufferedImage[1600];
		char labelsTrain[]=new char[1600];
		BufferedImage test[]=new BufferedImage[760];
		char labelsTest[]=new char[760];
		int cont=0;
		for(char x: labels) {
			for(int i=0;i<400;i++) {
				File inputfile=new File("C:/Users/pgmpa/eclipse-workspace/Proyecto3/data/Imagenes/"+x+"/output"+i+".png");
				train[i+400*cont] = ImageIO.read(inputfile);
				labelsTrain[i+400*cont]=x;
			}
			for(int i=400;i<590;i++) {
				File inputfile=new File("C:/Users/pgmpa/eclipse-workspace/Proyecto3/data/Imagenes/"+x+"/output"+i+".png");
				test[i-400+190*cont] = ImageIO.read(inputfile);
				labelsTest[i-400+190*cont]=x;
			}
			cont++;
		}
		ImgToArf(train, labelsTrain,"train");
		ImgToArf(test, labelsTest,"test");
		
	}
	
	public static String dig(int i, int length) {
		String s=String.valueOf(i);
		while(s.length()<length) {
			s="0"+s;
		}
		return s;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char labels[]= {'a','b','c','d', 'e'};
		try {
			create(labels);
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		
	}

}
