package com.meijie.study.itext;

import java.io.IOException;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

public class DemoFont {

	public static PdfFont get() {
		
        //PdfFontFactory.registerDirectory("c:\\windows\\fonts\\");
        
//        for (String str: PdfFontFactory.getRegisteredFonts()) {
//        	System.out.println(str);
//        }
        
        
        
        PdfFont font = null;
        try {
			font = 
				//PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
				PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
				//PdfFontFactory.createRegisteredFont("黑体");
	    		//PdfFontFactory.createRegisteredFont("微軟正黑體");
	    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		
        return font;
	}
}
