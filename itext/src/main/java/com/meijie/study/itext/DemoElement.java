package com.meijie.study.itext;

import java.io.IOException;
import java.util.Date;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

public class DemoElement {

	public void run() {
		Rectangle rect = new Rectangle(PageSize.A4);
    	
        System.out.println(String.format("Hello World! A4 size width:%g, height:%g", 
        		rect.getWidth(), rect.getHeight()));
        
        Date date = new Date();
        System.out.println(date.toString());
        
        long ms = System.currentTimeMillis();
        String filePath = "./"+ms+"test.pdf";
        
        try {
			
        	demo1(filePath, DemoFont.get());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    
    public static void demo1(String filePath,PdfFont font) throws Exception {
    	//Creating a PdfWriter
        PdfWriter writer = new PdfWriter(filePath);
        
        // Creating a PdfDocument  
        PdfDocument pdfDoc = new PdfDocument(writer); 
        
        // Creating a Document   
        Document document = new Document(pdfDoc);
        
     // Creating an Area Break          
        //AreaBreak aB = new AreaBreak();           
     
        // Adding area break to the PDF       
        //document.add(aB);              
        
        
        String para1 = "Tutorials Point originated from trts of their drawing rooms.";  
	      // Creating Paragraphs       
	      Paragraph paragraph1 = new Paragraph(para1);             
	      
	      // Adding paragraphs to document       
	      document.add(paragraph1);       
	      
	   // Creating a Paragraph       
	      Paragraph paragraph = new Paragraph("Tutorials Point provides the following tutorials");
	      
	      // Creating a list
	      List list = new List();  
	      list.setFont(font);
	      // Add elements to the list       
	      list.add("Java");       
	      list.add("JavaFX");      
	      list.add("Apache Tika");       
	      list.add("OpenCV");       
	      list.add("WebGL");       
	      list.add("Coffee Script");       
	      list.add("Java RMI");       
	      list.add("Apache Pig");
	      list.add("中文支持否？");      
	      
	      // Adding paragraph to the document       
	      document.add(paragraph);                    
	     
	      // Adding list to the document       
	      document.add(list);
     
	   // Creating a table       
	      float [] pointColumnWidths = {150F, 150F, 150F};   
	      Table table = new Table(pointColumnWidths);    
	      table.setFont(font);
	      // Adding cells to the table       
	      table.addCell(new Cell().add("Name"));       
	      table.addCell(new Cell().add("Raju"));       
	      table.addCell(new Cell().add("Id"));       
	      table.addCell(new Cell().add("1001"));       
	      table.addCell(new Cell().add("Designation"));       
	      table.addCell(new Cell().add("Programmer"));
	      table.addCell(new Cell().add("士大夫"));
	         
	      // Adding Table to document        
	      document.add(table);                  
	      
	   // Creating an ImageData object
	      ImageData data = ImageDataFactory.create(App.class.getClassLoader().getResource("logo.png").getPath());              
	      
	      // Creating an Image object        
	      Image image = new Image(data);                        
	      image.setFixedPosition(40, 400);
	      image.scaleAbsolute(200, 111);
	      // Adding image to the document       
	      document.add(image); 
	      
	   // Creating text object       
	      Text text1 = new Text("Tutorialspoint 支持中文不？");              
	   
	      // Setting font of the text       
	             
	      text1.setFont(font);                 
	      
	      //Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
	   
	      // Setting font color
	      text1.setFontColor(Color.GREEN);
	   
	      // Creating text object
	      Text text2 = new Text("Simply Easy Learning");
	      text2.setFont(PdfFontFactory.createFont(FontConstants.HELVETICA));         
	      
	      // Setting font color
	      text2.setFontColor(Color.BLUE);
	      
	      // Creating Paragraph
	      Paragraph paragraph2 = new Paragraph();
	      
	      // Adding text1 to the paragraph
	      paragraph2.add(text1);
	      paragraph2.add(text2);
	      
	      paragraph2.setFixedPosition(50, 60, 330);
	      
	      // Adding paragraphs to the document
	      document.add(paragraph2);
	      	      	      
        // Closing the document       
        document.close();
    }


}
