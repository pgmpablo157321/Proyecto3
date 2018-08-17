package Split;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;


public class SplitImage {
	
	public static void getDivitions(BufferedImage a[], char label) throws IOException {
		
		int con=0;
		Color myWhite=Color.WHITE;
		Color myBlack=Color.BLACK;
		Color myRed=Color.RED;
		int rgbBlanco=myWhite.getRGB();
		int rgbNegro=myBlack.getRGB();
		int rgbRojo=myRed.getRGB();
		for(int cont=0; cont<a.length; cont++) {
		Queue<Integer>dH=new LinkedList<Integer>();
		Queue<Integer>dV=new LinkedList<Integer>();
		boolean x=true;
		for(int i=0; i<a[cont].getHeight();i++) {
			boolean y=x;
			if(x) {
				for(int j=0;j<a[cont].getWidth();j++) {
					if(a[cont].getRGB(j, i)==rgbNegro) {
						x=!x;
						j=a[cont].getHeight();
						
					}
				}
				if(x!=y) {
					dH.add(i-2);
				}
			}else {
				x=!x;
				for(int j=0;j<a[cont].getWidth();j++) {
					if(a[cont].getRGB(j, i)==rgbNegro) {
						x=!x;
						j=a[cont].getHeight();
						
					}
				}
				if(x!=y) {
					dH.add(i+2);
				}
			}
			
			
			
		}
		
		BufferedImage l[]=new BufferedImage[dH.size()/2];
		
		for(int i=0;i<l.length;i++) {
			l[i]=a[cont].getSubimage(0, dH.peek(), a[cont].getWidth(), -dH.poll()+dH.poll());
			x=true;
			for(int k=0; k<l[i].getWidth();k++) {
				boolean y=x;
				if(x) {
					for(int j=0;j<l[i].getHeight();j++) {
						if(l[i].getRGB(k, j)==rgbNegro) {
							x=!x;
							j=a[cont].getHeight();
							
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
							j=a[cont].getHeight();
							
						}
					}
					if(x!=y) {
						dV.add(k+2);
					}
				}
			}
			while(!dV.isEmpty()) {
				BufferedImage aux=l[i].getSubimage(dV.peek(), 0, -dV.poll()+dV.poll(), l[i].getHeight());
				ImageIO.write(redimencion(aux,32), "png", new File ("C:/Users/pgmpa/eclipse-workspace/Proyecto3/data/Imagenes/"+label+"/output"+con+".png"));
				con++;
			}
			
			
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
		// TODO Auto-generated method stub
		char[] c= {'a', 'b', 'c', 'd', 'e'};
		int cont=0;
		BufferedImage image[]=new BufferedImage[10];
		
		for(char x: c) {
			BufferedImage output[]=new BufferedImage[2];
			for(int i=0;i<2;i++) {
				File inputfile=new File("C:/Users/pgmpa/eclipse-workspace/Proyecto3/data/Imagenes/"+x+""+(i+1)+".jpg");	
				image[cont]=ImageIO.read(inputfile);
				int pixels[] = Binarize.Initialize(image[cont]);
				output[i]=Binarize.getImagen(pixels, image[cont].getWidth(), image[cont].getHeight());
				cont++;
			}
			getDivitions(output, x);
		}
		
		}

}
