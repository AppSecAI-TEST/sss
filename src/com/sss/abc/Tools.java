package com.sss.abc;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class Tools {
	public static void shenchen(String filename,String content) throws Exception{
		 String text = "content";  
         int width = 300;  
         int height = 300;  
         //��ά���ͼƬ��ʽ  
         String format = "gif";  
         Hashtable hints = new Hashtable();  
         //������ʹ�ñ���  
         hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
         BitMatrix bitMatrix = new MultiFormatWriter().encode(text,  
                 BarcodeFormat.QR_CODE, width, height, hints);  
         //���ɶ�ά��  
         File outputFile = new File("d:"+File.separator+"er"+File.separator+filename+".gif");  
         MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);  
	}
}