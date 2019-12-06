package com.meijie.study.itext;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class BarCode {

	// https://sunjs.com/article/detail/7731c19d2bb14a7aafd17076f38947a4.html
	
	// https://www.open-open.com/code/view/1453520722495
	
	/*
	 * 	生成条形码
	 * @param contents 条形码内容
	 * @param width 条形码宽度
	 * @param height 条形码高度
	 */
	public static BufferedImage encodeBarcode(String contents, int width, int height) {
		BufferedImage barcode = null;
		
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
		
		try {
			
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.EAN_13, codeWidth, height, null);
			barcode= MatrixToImageWriter.toBufferedImage(bitMatrix);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return barcode;
	}
	
	public static byte[] toByteArray(BufferedImage image) throws Exception {
		
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		ImageIO.write(image, "png", out);
		
		return out.toByteArray();
	}
	
	public static void toFile(BufferedImage image, String name) throws Exception {
		
		File pngFile = new File(String.format("./%s.png", name));
		
		ImageIO.write(image, "png", pngFile);		
		
	}

	/*
	 * 	解析条形码
	 * @param contents 条形码内容
	 * @param width 条形码宽度
	 * @param height 条形码高度
	 */
	public static String decodeBarcode(String barcodePath){
        BufferedImage image;
        Result result = null;
        try {
            image = ImageIO.read(new File(barcodePath));
            if (image != null) {
                 LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                result = new MultiFormatReader().decode(bitmap, null);
            }
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static int UpperMinAlpha = 10;
	public static int LowerMinAlpha = 37;
	/**
	 * 	条形码编码
	 * @param contents 6位字符串
	 * @return 12位数字串
	 */
	public static String mqEncode(String contents) throws Exception {
		// 6位字符串
		StringBuilder sb = new StringBuilder();
		if (contents.length() == 6) {
			for (int i=0; i<6; i++) {
				char c = contents.charAt(i);
				if (Character.isDigit(c)) {
					sb.append('0');
					sb.append(c);
				} else if (Character.isUpperCase(c)) {
					int val = c - 'A' + UpperMinAlpha;
					sb.append(val / 10);
					sb.append(val % 10);
				} else if (Character.isLowerCase(c)) {
					int val = c - 'a' + LowerMinAlpha;
					sb.append(val / 10);
					sb.append(val % 10);
				} else {
					throw new Exception(" not digit or alphbeit !");
				}
			}
		} else {
			throw new Exception(" 字符串长度不是6 ");			
		}
		System.out.println(String.format("%s encode is %s", contents, sb.toString()));
		return sb.toString();
	}
	
	/**
	 *  	条形码解码
	 * @param contents 12数字字符串
	 * @return 6位字符串
	 */
	public static String mqDecode(String contents) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (contents.length() == 12) {
			for (int i=0; i< contents.length(); i+=2) {
				int val = Integer.parseInt(contents.substring(i, i+2));
				if (val < UpperMinAlpha) {
					sb.append(val);
				} else if (val >= LowerMinAlpha) {
					sb.append((char)(val - LowerMinAlpha + 'a'));
				} else {
					sb.append((char)(val - UpperMinAlpha + 'A'));
				}
			}
		} else {
			throw new Exception(" 字符串长度不是12 ");
		}
		System.out.println(String.format("%s decode is %s", contents, sb.toString()));
		return sb.toString();
	}
	
	public static void test() throws Exception {
		
		int width = 90, height = 48;
		String contents = mqEncode("A0e1ab"); //"123456789098"
		mqDecode(contents);
		toFile(encodeBarcode(contents, width, height), "w90");
		width = 108;
		toFile(encodeBarcode(contents, width, height), "w108");
		width = 104;
		toFile(encodeBarcode(contents, width, height), "w104");
	}
}
