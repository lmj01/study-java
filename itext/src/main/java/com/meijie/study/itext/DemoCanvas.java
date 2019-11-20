package com.meijie.study.itext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

public class DemoCanvas {

	public void run() {
		
        Date date = new Date();
        System.out.println(date.toString());
        
        long ms = System.currentTimeMillis();
        String filePath = "./"+ms+"test.pdf";
        
        try {
        	
			demo2(filePath, DemoFont.get());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public float toy(float y) {
		Rectangle rect = new Rectangle(PageSize.A4);
    	
		return rect.getTop() - y;
	}
	
	public String resize(String str, float maxline, PdfFont font, int fontsize) {
//    	List<String> list = new ArrayList();
//    	int total = str.length();
//    	float len = font.getWidth(str, fontsize);    	
//    	int lineSize = (int) Math.ceil(len / maxline);
//    	int size = (int)Math.ceil(total / lineSize);
//        for (int i=0; i<=lineSize;i++) {
//        	int m = i * size + 0;
//        	int n = size * (i + 1);
//        	if (n >= total) n = total;
//        	String substr = str.substring(m, n);
//        	list.add(substr);
//        	System.out.println(String.format("%d, %g, %d ", total, len, lineSize));
//        }        
//        
		float left = 0;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<str.length(); i++) {
	    	  char c = str.charAt(i);
	    	  float w = font.getWidth(c, fontsize);
	    	  if(left + w > maxline) {
	    		  left = 0;
	    		  sb.append("\n");
	    	  }
	    	  sb.append(c);
	    	  left += w;
	      }
    	return sb.toString();
    }
	
	public static void drawText(PdfCanvas canvas, String str, float x, float y, PdfFont font, int size, Color color) {
        canvas.beginText()
        	.setFontAndSize(font, size)
        	.setColor(color, true)
        	.moveText(x, y)
        	.showText(str)
        	.endText();
    }
	
	public void demo2(String filePath, PdfFont font) throws Exception {
    	
		PdfWriter writer = new PdfWriter(filePath);           
        
        // Creating a PdfDocument object       
        PdfDocument pdfDoc = new PdfDocument(writer);                   
        
        // Creating a Document object       
        Document doc = new Document(pdfDoc);                
        
        // Creating a new page       
        PdfPage pdfPage = pdfDoc.addNewPage();               
        
        // Creating a PdfCanvas object       
        PdfCanvas canvas = new PdfCanvas(pdfPage);              
        
        ImageData data = ImageDataFactory.create(App.class.getClassLoader().getResource("logo.png").getPath());
        
        canvas.saveState();
	      
	      canvas.setLineWidth(2.0f);
	      //.setLineDash(new float[] { 6.0f, 2.0f }, 0);
	      //canvas.setLineDash(2.0f);
	      canvas.setLineDash(5, 3, 2.5f);
	      //canvas.setLineDash(new float[] {6.0f, 2.0f}, 4);
	      //canvas.setLineDash(0.5f, 4);
	      
	      //canvas.setColor(Color.RED, false);
	      Color myColor = new DeviceRgb(99,143,165);
	      canvas.setColor(myColor, false);
	      
	      // Initial point of the line       
	      canvas.moveTo(100, toy(300));              
	      
	      // Drawing the line
	      canvas.setLineDash(5,  3, 2.5f);
	      canvas.lineTo(500, toy(300));           
	      canvas.setLineDash(5,  3, 2.5f);
	      canvas.lineTo(500, toy(600));
	      
	      // Closing the path stroke       
	      canvas.closePathStroke();
	      int fontSize = 16;	      
	      float width = 300;
	      float left = 0;
	      StringBuilder sb = new StringBuilder();
	      String str = "拔牙：在佩戴该步矫治器前，请拔除 38 48abcde中文2222222222222222222222222222aa附件：从该步矫治器起，请临床在以下牙位粘接附件 <16> <16> <17> <26> <26> <27> <36> <36> <37> <46> <46> <47>";
	      for (int i=0; i<str.length(); i++) {
	    	  char c = str.charAt(i);
	    	  float w = font.getWidth(c, fontSize);
	    	  if(left + w > width) {
	    		  left = 0;
	    		  sb.append("\n");
	    	  }
	    	  sb.append(c);
	    	  left += w;
	      }
	      System.out.println(sb.toString());
	      
	      //PdfString str2 = str;
	      Color color = new DeviceRgb(187,56,107);
	      System.out.println(String.format("%g, %g, %g, %g", 
	    		  font.getWidth("我", fontSize),
	    		  font.getWidth("a", fontSize),
	    		  font.getWidth("<", fontSize),
	    		  font.getWidth(",", fontSize)
	    		  ));
	      float y = 120;
	      for (String s1 : resize(str, 300, font, fontSize).split("\n")) {
	    	  y += 40;
	    	  drawText(canvas, s1, 20, toy(y), font, fontSize, color);  
	      }
	      for (String s1 : sb.toString().split("\n")) {
	    	  y += 40;
	    	  drawText(canvas, s1, 20, toy(y), font, fontSize, color);  
	      }
	      
	      
	      
	      	
	      canvas.addImage(data, 30, toy(30), 40, true, true);
	      	      
	      canvas.restoreState();

        
        // Closing the document       
        doc.close();  
    }
}
