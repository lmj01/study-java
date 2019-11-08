package com.meijie.study.itext;

import java.io.IOException;
import java.util.Date;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
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
	      
	      canvas.beginText();
	      canvas.setFontAndSize(font, 16);
	      canvas.setColor(myColor, false);
	      canvas.moveText(20, toy(120));
	      canvas.showText("abcde中文2222222222");
	      canvas.endText();
	      
	      canvas.addImage(data, 30, toy(30), 40, true, true);
	      	      
	      canvas.restoreState();

        
        // Closing the document       
        doc.close();  
    }
}
