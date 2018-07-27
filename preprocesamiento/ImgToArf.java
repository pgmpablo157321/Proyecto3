package Split;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ImgToArf {
	
	public static boolean ImgToArf(BufferedImage[] list, char [] labels) {
		FileWriter fw = null;
		BufferedWriter bw=null;
		
		try {
			fw= new FileWriter("C:/Users/pgmpa/eclipse-workspace/Proyecto3/data/train.txt");
			bw=new BufferedWriter(fw);
			bw.write("@relation train");
			bw.newLine();
			for(int i=0;i<768;i++) {
				bw.write("@attribute pixel"+i+"{0,1}");
				bw.newLine();
			}
			bw.write("@attribute label {a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}");
			bw.newLine();
			bw.write("@data");
			bw.newLine();
			for(int i=0;i<list.length;i++) {
				for(int j=0;j<28;j++) {
					for(int k=0;k<28;k++) {
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
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
