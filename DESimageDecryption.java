import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class DESimageDecryption
{
	static String Output="";
	static byte K[]=new byte[64];
	static String KEY[]=new String[16];
	static Scanner in=new Scanner(System.in);
    public static void main(String args[])throws IOException
    {	
        BufferedImage img1 = null;
        File f1 = null;
        BufferedImage img2 = null;
        File f2 = null;
    	System.out.println("Enter the Key");
    	String key1=in.next();
        try
        {
            f1 = new File("C:\\Users\\Saurabh Singh\\Desktop\\Enc1.png");
            f2 = new File("C:\\Users\\Saurabh Singh\\Desktop\\Enc2.png");
            img1 = ImageIO.read(f1);
            img2 = ImageIO.read(f2);
	        int width1 = img1.getWidth();
	        int height1 = img1.getHeight();
	        int width2 = img2.getWidth();
	        int height2 = img2.getHeight();
			BufferedImage im1=new BufferedImage(img1.getWidth(),img1.getHeight(),BufferedImage.TYPE_INT_ARGB);
			BufferedImage im2=new BufferedImage(img2.getWidth(),img2.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D gd1=im1.createGraphics();
			Graphics2D gd2=im2.createGraphics();
			try
			{
				gd1.drawImage(img1, 0, 0,null);
				gd2.drawImage(img2, 0, 0,null);    			
			}
			finally
			{
				gd1.dispose();
				gd2.dispose();
			}
	        String ares,rres,gres,bres;
	        ares=rres=gres=bres="";
	        int c=0;
	        for(int i=0;i<height1;i++)
	        {
	        	for(int j=0;j<width1;j++)
	        	{
	      	       Color  c1 = new Color(img1.getRGB(j,i),true);
	     	       int a=c1.getAlpha();
	     	       int r=c1.getRed();
	     	       int g=c1.getGreen();
	     	       int b=c1.getBlue();
			        String a1=DecToBinary(a);
			        String r1=DecToBinary(r);
			        String g1=DecToBinary(g);
			        String b1=DecToBinary(b);
			        String pixel=a1+r1+g1+b1;
		      	    a=r=g=b=0;  
			        Color  c2 = new Color(img2.getRGB(j,i),true);
	     	        a=c2.getAlpha();
	     	        r=c2.getRed();
	     	        g=c2.getGreen();
	     	        b=c2.getBlue();
			        a1=DecToBinary(a);
			        r1=DecToBinary(r);
			        g1=DecToBinary(g);
			        b1=DecToBinary(b);
			        pixel=pixel+a1+r1+g1+b1;
			        DES(pixel,key1);
			        System.out.println("Decrypted for "+c+"th pixel");c++;
			        for(int k=0;k<32;k++)
			        {
			        	if(k<8)
			        	{
			        		ares=ares+Output.charAt(k);
			        	}
			        	else if(k>=8 && k<16)
			        	{
			        		rres=rres+Output.charAt(k);
			        	}
			        	else if(k>=16 && k<24)
			        	{
			        		gres=gres+Output.charAt(k);
			        	}
			        	else if(k>=24 && k<32)
			        	{
			        		bres=bres+Output.charAt(k);
			        	}
			        }
			        a=r=b=g=0;
			        a=Integer.parseInt(ares,2);
			        r=Integer.parseInt(rres,2);
		            g=Integer.parseInt(gres,2);
		            b=Integer.parseInt(bres,2);
		            Color col=new Color(r,g,b,a);
		            im1.setRGB(j,i, col.getRGB());
			        im2.setRGB(j,i, col.getRGB());
			        ares=rres=gres=bres="";
	        	}
	        }
            f1 = new File("C:\\Users\\Saurabh Singh\\Desktop\\Dec2.png");
            ImageIO.write(im1, "png", f1);
            System.out.println("Success");
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        
    }
static void DES(String p,String key1)
{
	byte IP[]=new byte[]{ 
			58, 50, 42, 34, 26, 18, 10, 2,
			60, 52, 44, 36, 28, 20, 12, 4,
			62, 54, 46, 38, 30, 22, 14, 6,
			64, 56, 48, 40, 32, 24, 16, 8,
			57, 49, 41, 33, 25, 17, 9,  1,
			59, 51, 43, 35, 27, 19, 11, 3,
			61, 53, 45, 37, 29, 21, 13, 5,
			63, 55, 47, 39, 31, 23, 15, 7
		};
	byte EP[]=new byte[] {
			32, 1,  2,  3,  4,  5,
			4,  5,  6,  7,  8,  9,
			8,  9,  10, 11, 12, 13,
			12, 13, 14, 15, 16, 17,
			16, 17, 18, 19, 20, 21,
			20, 21, 22, 23, 24, 25,
			24, 25, 26, 27, 28, 29,
			28, 29, 30, 31, 32, 1
		};
	byte PF[]=new byte[] {
			16, 7,  20, 21,
			29, 12, 28, 17,
			1,  15, 23, 26,
			5,  18, 31, 10,
			2,  8,  24, 14,
			32, 27, 3,  9,
			19, 13, 30, 6,
			22, 11, 4,  25
		};
	byte InverseP[]=new byte[]  {
			40, 8, 48, 16, 56, 24, 64, 32,
			39, 7, 47, 15, 55, 23, 63, 31,
			38, 6, 46, 14, 54, 22, 62, 30,
			37, 5, 45, 13, 53, 21, 61, 29,
			36, 4, 44, 12, 52, 20, 60, 28,
			35, 3, 43, 11, 51, 19, 59, 27,
			34, 2, 42, 10, 50, 18, 58, 26,
			33, 1, 41, 9, 49, 17, 57, 25
		};
	String s,key,Enc;
	key=s=Enc="";
	byte A1[];
	s=p;
	key=HexToBinary(key1);
    A1=new byte[64];			
	for(int i=0;i<s.length();i++)
	{
		A1[i]=(byte) ((byte)(s.charAt(i))-48);
	}
	for(int i=0;i<key.length();i++)
	{
		K[i]=(byte) ((byte)(key.charAt(i))-48);
	}
	RoundKey();
	Enc="";
		byte A[]=new byte[64];
		byte B[]=new byte[64];
		byte F[]=new byte[64];
		for(int i=0;i<64;i++)
		{
			A[i]=A1[i];
		}
		for(int i=0;i<64;i++)
		{
			B[i]=A[IP[i]-1];
		}
		for(int i=1;i<=16;i++)
		{
			byte LH[]=new byte[32];
			byte RH[]=new byte[32];
			byte ERH[]=new byte[48];
			byte ROUNDKEY[]=new byte[48];
			byte TEMP[]=new byte[32];
			byte TEMP1[]=new byte[32];
			for(int j=0;j<32;j++)
			{
				LH[j]=B[j];
				RH[j]=B[j+32];
			}
			String RoundKey=KEY[15-(i-1)];
			for(int j=0;j<48;j++)
				ROUNDKEY[j]=(byte)(RoundKey.charAt(j)-48);
			for(int j=0;j<48;j++)
				ERH[j]=(byte)(RH[EP[j]-1]^ROUNDKEY[j]);
			int z=0;
			for(int j=0;j<48;j=j+6)
			{	
				byte bit1=ERH[j];
				byte lastbit=ERH[j+5];
				byte a=getDecimal(Byte.toString(bit1)+Byte.toString(lastbit));
				byte b=getHexadecimal(Byte.toString(ERH[j+1])+Byte.toString(ERH[j+2])+Byte.toString(ERH[j+3])+Byte.toString(ERH[j+4]));
				String m=SBox(a,b,j/6);
				for(int k=0;k<4;k++)
					TEMP[k+4*z]=(byte) ((byte) m.charAt(k)-48);
				z++;
			}
			for(int j=0;j<32;j++)
			{
				TEMP1[j]=(byte)(TEMP[PF[j]-1]^LH[j]);
				LH[j]=RH[j];
				RH[j]=TEMP1[j];
				B[j]=LH[j];
				B[j+32]=RH[j];
			}
		}
		for(int i=0;i<32;i++)
		{
			byte t=B[i];
			B[i]=B[i+32];
			B[i+32]=t;
		}
		for(int i=0;i<64;i++)
		{
			F[i]=B[InverseP[i]-1];
			Enc=Enc+Byte.toString(F[i]);
		}
		Output="";
		Output=Enc;
}
static String HexToBinary(String s)
{	
	String str="";
	for(int i=0;i<s.length();i++)
	{
		char P=s.charAt(i);
		str=str+DecEq(P);
	}
	return str;
}
static String DecEq(char P)
{
	if(P=='0')
		return "0000";
	else if(P=='1')
		return "0001";
	else if(P=='2')
		return "0010";
	else if(P=='3')
		return "0011";
	else if(P=='4')
		return "0100";
	else if(P=='5')
		return "0101";
	else if(P=='6')
		return "0110";
	else if(P=='7')
		return "0111";
	else if(P=='8')
		return "1000";
	else if(P=='9')
		return "1001";
	else if(P=='A')
		return "1010";
	else if(P=='B')
		return "1011";
	else if(P=='C')
		return "1100";
	else if(P=='D')
		return "1101";
	else if(P=='E')
		return "1110";
	return "1111";
}
static String DecToBinary(int n)
{
    int[] binaryNum = new int[8];
    int i = 0;
    String bin="";
    while (n > 0) 
    {
        binaryNum[i] = n % 2;
        n = n / 2;
        i++;
    }
    for(int j=i;j<8;j++)
    	binaryNum[j]=0;
    for (int j = 7; j >= 0; j--)
        bin=bin+Integer.toString(binaryNum[j]);
    return bin;
}
static byte getDecimal(String a)
{
	if(a.equals("00"))
	return 0;
	else if(a.equals("01"))
		return 1;
	else if(a.equals("10"))
		return 2;
	else
		return 3;
}
static byte getHexadecimal(String a)
{
	if(a.equals("0000"))
		return 0;
	else if(a.equals("0001"))
		return 1;
	else if(a.equals("0010"))
		return 2;
	else if(a.equals("0011"))
		return 3;
	else if(a.equals("0100"))
		return 4;
	else if(a.equals("0101"))
		return 5;
	else if(a.equals("0110"))
		return 6;
	else if(a.equals("0111"))
		return 7;
	else if(a.equals("1000"))
		return 8;
	else if(a.equals("1001"))
		return 9;
	else if(a.equals("1010"))
		return 10;
	else if(a.equals("1011"))
		return 11;
	else if(a.equals("1100"))
		return 12;
	else if(a.equals("1101"))
		return 13;
	else if(a.equals("1110"))
		return 14;
	else
		return 15;
}
static String SBox(byte a,byte b,int j)
{
	String S[][]={ {
	        "1110", "0100",  "1101", "0001",  "0010",  "1111", "1011", "1000",  "0011",  "1010", "0110",  "1100", "0101",  "1001",  "0000",  "0111",
	        "0000",  "1111", "0111",  "0100",  "1110", "0010",  "1101", "0001",  "1010", "0110",  "1100", "1011", "1001",  "0101",  "0011",  "1000",
	        "0100",  "0001",  "1110", "1000",  "1101", "0110",  "0010",  "1011", "1111", "1100", "1001",  "0111",  "0011",  "1010", "0101",  "0000",
	        "1111", "1100", "1000",  "0010",  "0100",  "1001",  "0001",  "0111",  "0101",  "1011", "0011",  "1110", "1010", "0000",  "0110",  "1101"
	    }, {
	        "1111", "0001",  "1000",  "1110", "0110",  "1011", "0011",  "0100",  "1001",  "0111",  "0010",  "1101", "1100", "0000",  "0101",  "1010",
	        "0011",  "1101", "0100",  "0111",  "1111", "0010",  "1000",  "1110", "1100", "0000",  "0001",  "1010", "0110",  "1001",  "1011", "0101",
	        "0000",  "1110", "0111",  "1011", "1010", "0100",  "1101", "0001",  "0101",  "1000",  "1100", "0110",  "1001",  "0011",  "0010",  "1111",
	        "1101", "1000",  "1010", "0001",  "0011",  "1111", "0100",  "0010",  "1011", "0110",  "0111",  "1100", "0000",  "0101",  "1110", "1001"
	    }, {
	        "1010", "0000",  "1001",  "1110", "0110",  "0011",  "1111", "0101",  "0001",  "1101", "1100", "0111",  "1011", "0100",  "0010",  "1000",
	        "1101", "0111",  "0000",  "1001",  "0011",  "0100",  "0110",  "1010", "0010",  "1000",  "0101",  "1110", "1100", "1011", "1111", "0001",
	        "1101", "0110",  "0100",  "1001",  "1000",  "1111", "0011",  "0000",  "1011", "0001",  "0010",  "1100", "0101",  "1010", "1110", "0111",
	        "0001",  "1010", "1101", "0000",  "0110",  "1001",  "1000",  "0111",  "0100",  "1111", "1110", "0011",  "1011", "0101",  "0010",  "1100"
	    }, {
	        "0111",  "1101", "1110", "0011",  "0000",  "0110",  "1001",  "1010", "0001",  "0010",  "1000",  "0101",  "1011", "1100", "0100",  "1111",
	        "1101", "1000",  "1011", "0101",  "0110",  "1111", "0000",  "0011",  "0100",  "0111",  "0010",  "1100", "0001",  "1010", "1110", "1001",
	        "1010", "0110",  "1001",  "0000",  "1100", "1011", "0111",  "1101", "1111", "0001",  "0011",  "1110", "0101",  "0010",  "1000",  "0100",
	        "0011",  "1111", "0000",  "0110",  "1010", "0001",  "1101", "1000",  "1001",  "0100",  "0101",  "1011", "1100", "0111",  "0010",  "1110"
	    }, {
	        "0010",  "1100", "0100",  "0001",  "0111",  "1010", "1011", "0110",  "1000",  "0101",  "0011",  "1111", "1101", "0000",  "1110", "1001",
	        "1110", "1011", "0010",  "1100", "0100",  "0111",  "1101", "0001",  "0101",  "0000",  "1111", "1010", "0011",  "1001",  "1000",  "0110",
	        "0100",  "0010",  "0001",  "1011", "1010", "1101", "0111",  "1000",  "1111", "1001",  "1100", "0101",  "0110",  "0011",  "0000",  "1110",
	        "1011", "1000",  "1100", "0111",  "0001",  "1110", "0010",  "1101", "0110",  "1111", "0000",  "1001",  "1010", "0100",  "0101",  "0011"
	    }, {
	        "1100", "0001",  "1010", "1111", "1001",  "0010",  "0110",  "1000",  "0000",  "1101", "0011",  "0100",  "1110", "0111",  "0101",  "1011",
	        "1010", "1111", "0100",  "0010",  "0111",  "1100", "1001",  "0101",  "0110",  "0001",  "1101", "1110", "0000",  "1011", "0011",  "1000",
	        "1001",  "1110", "1111", "0101",  "0010",  "1000",  "1100", "0011",  "0111",  "0000",  "0100",  "1010", "0001",  "1101", "1011", "0110",
	        "0100",  "0011",  "0010",  "1100", "1001",  "0101",  "1111", "1010", "1011", "1110", "0001",  "0111",  "0110",  "0000",  "1000",  "1101"
	    }, {
	        "0100",  "1011", "0010",  "1110", "1111", "0000",  "1000",  "1101", "0011",  "1100", "1001",  "0111",  "0101",  "1010", "0110",  "0001",
	        "1101", "0000",  "1011", "0111",  "0100",  "1001",  "0001",  "1010", "1110", "0011",  "0101",  "1100", "0010",  "1111", "1000",  "0110",
	        "0001",  "0100",  "1011", "1101", "1100", "0011",  "0111",  "1110", "1010", "1111", "0110",  "1000",  "0000",  "0101",  "1001",  "0010",
	        "0110",  "1011", "1101", "1000",  "0001",  "0100",  "1010", "0111",  "1001",  "0101",  "0000",  "1111", "1110", "0010",  "0011",  "1100"
	    }, {
	        "1101", "0010",  "1000",  "0100",  "0110",  "1111", "1011", "0001",  "1010", "1001",  "0011",  "1110", "0101",  "0000",  "1100", "0111",
	        "0001",  "1111", "1101", "1000",  "1010", "0011",  "0111",  "0100",  "1100", "0101",  "0110",  "1011", "0000",  "1110", "1001",  "0010",
	        "0111",  "1011", "0100",  "0001",  "1001",  "1100", "1110", "0010",  "0000",  "0110",  "1010", "1101", "1111", "0011",  "0101",  "1000",
	        "0010",  "0001",  "1110", "0111",  "0100",  "1010", "1000",  "1101", "1111", "1100", "1001",  "0000",  "0011",  "0101",  "0110",  "1011"
	    } };
	    
	    String hex=S[j][b+16*a];
	return hex;
}
static void RoundKey()
{
	byte PC_1[]=new byte[]{
			57, 49, 41, 33, 25, 17, 9,
			1,  58, 50, 42, 34, 26, 18,
			10, 2,  59, 51, 43, 35, 27,
			19, 11, 3,  60, 52, 44, 36,
			63, 55, 47, 39, 31, 23, 15,
			7,  62, 54, 46, 38, 30, 22,
			14, 6,  61, 53, 45, 37, 29,
			21, 13, 5,  28, 20, 12, 4
		};
	byte PC_2[]=new byte[] {
			14, 17, 11, 24, 1,  5,
			3,  28, 15, 6,  21, 10,
			23, 19, 12, 4,  26, 8,
			16, 7,  27, 20, 13, 2,
			41, 52, 31, 37, 47, 55,
			30, 40, 51, 45, 33, 48,
			44, 49, 39, 56, 34, 53,
			46, 42, 50, 36, 29, 32
		};			
	byte KK[]=new byte[56];
	byte KK1[]=new byte[28];
	byte KK2[]=new byte[28];
	byte SubKey[]=new byte[48];
	for(int j=0;j<56;j++)
	{
		KK[j]=K[PC_1[j]-1];
	}
	for(int p=1;p<=16;p++)
	{
		for(int j=0;j<28;j++)
		{
			KK1[j]=KK[j];
			KK2[j]=KK[j+28];
		}
		int bitsrotate=getbitsrotate(p);
		for(int k=1;k<=bitsrotate;k++)
		{	
			byte temp1=KK1[0];
			byte temp2=KK2[0];
			for(int j=0;j<27;j++)
			{
				KK1[j]=KK1[j+1];
				KK2[j]=KK2[j+1];
			}
			KK1[27]=temp1;
			KK2[27]=temp2;
		}	
		for(int j=0;j<28;j++)
		{
			KK[j]=KK1[j];
			KK[j+28]=KK2[j];
		}
		String s="";
		for(int j=0;j<48;j++)
		{	
			SubKey[j]=KK[PC_2[j]-1];
			s=s+Byte.toString(SubKey[j]);
		}
		KEY[p-1]=s;
	}
}
static int getbitsrotate(int i)
{	
	if(i==1)
		return 1;
	else if(i==2)
		return 1;
	else if(i==3)
		return 2;
	else if(i==4)
		return 2;
	else if(i==5)
		return 2;
	else if(i==6)
		return 2;
	else if(i==7)
		return 2;
	else if(i==8)
		return 2;
	else if(i==9)
		return 1;
	else if(i==10)
		return 2;
	else if(i==11)
		return 2;
	else if(i==12)
		return 2;
	else if(i==13)
		return 2;
	else if(i==14)
		return 2;
	else if(i==15)
		return 2;
		return 1;
}
}
