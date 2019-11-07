package com.meijie.study.itext;

import java.io.FileNotFoundException;
import java.util.Date;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	//https://www.tutorialspoint.com/itext/itext_formatting_cell_contents.htm
    	
    	Rectangle rect = new Rectangle(PageSize.A4);
    	
        System.out.println( "Hello World!" );
        
        Date date = new Date();
        System.out.println(date.toString());
        
        long ms = System.currentTimeMillis();
        String filePath = "./"+ms+"test.pdf";
        
        //PdfFontFactory.registerDirectory("c:\\windows\\fonts\\");
//        PdfFontFactory.register("./simhei.ttf"
//        		//, "myfont"
//        		);
        
        for (String str: PdfFontFactory.getRegisteredFonts()) {
        	System.out.println(str);
        }
        
        PdfFont font = 
        		//PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        		PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        		//PdfFontFactory.createRegisteredFont("黑体");
        		//PdfFontFactory.createRegisteredFont("微軟正黑體");
        
                
        demo1(filePath, font);
        //demo2(filePath, font);
                   
        System.out.println("Pdf created"); 
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
	      
	   // Creating a new page       
	      PdfPage pdfPage = 
	    		  //pdfDoc.addNewPage();
	    		  pdfDoc.getPage(1);
	      
	   // Creating a PdfCanvas object       
	      PdfCanvas canvas = new PdfCanvas(pdfPage);              
	      
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
	      canvas.moveTo(100, 300);              
	      
	      // Drawing the line
	      canvas.setLineDash(5,  3, 2.5f);
	      canvas.lineTo(500, 300);           
	      canvas.setLineDash(5,  3, 2.5f);
	      canvas.lineTo(500, 600);
	      
	      // Closing the path stroke       
	      canvas.closePathStroke();
	      
	      canvas.beginText();
	      canvas.setFontAndSize(font, 16);
	      canvas.setColor(myColor, false);
	      canvas.moveText(20, 120);
	      canvas.showText("abcde中文2222222222");
	      canvas.endText();
	      
	      Rectangle rect = new Rectangle(30, 30);
	      canvas.addImage(data, 30, 30, 40, true, true);
	      canvas.addImage(data, rect, true);
	      	      
	      canvas.restoreState();
	      
        // Closing the document       
        document.close();
    }

    public static void demo2(String filePath, PdfFont font) throws Exception {
    	PdfWriter writer = new PdfWriter(filePath);           
        
        // Creating a PdfDocument object       
        PdfDocument pdfDoc = new PdfDocument(writer);                   
        
        // Creating a Document object       
        Document doc = new Document(pdfDoc);                
        
        // Creating a new page       
        PdfPage pdfPage = pdfDoc.addNewPage();               
        
        // Creating a PdfCanvas object       
        PdfCanvas canvas = new PdfCanvas(pdfPage);              
        
        // Initial point of the line       
        canvas.moveTo(100, 300);              
        
        // Drawing the line       
        canvas.lineTo(500, 300);           
        
        
        // Closing the path stroke       
        canvas.closePathStroke();
        
        canvas.beginText();
	      canvas.setFontAndSize(font, 10);
	      canvas.moveText(20, 20);
	      canvas.showText("abcde中文");
	      canvas.endText();
        
        // Closing the document       
        doc.close();  
    }
}
