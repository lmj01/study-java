package com.meijie.study.itext;

import java.io.OutputStream;

import com.itextpdf.io.font.PdfEncodings;
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
import com.itextpdf.layout.element.Image;

public class TemplatePdf {
	
	private PdfDocument pdfDoc;
	private Document document;
	private PdfWriter writer;
	private PdfPage page;
	private PdfCanvas canvas;
	private int pageCount;
	private float lineHeight;
	private Rectangle a4;
	private PdfFont font;
	
	public static boolean isWin32 = false;
    
    public static float[][][] sOffset = {
        // 1：矫治要求:上颌图标，下颌图标，上颌虚线，下颌虚线
        // 2：开窗下颌舌侧        // 3：开窗下颌颊侧
        // 4：开窗上颌颊侧        // 5：开窗上颌舌侧
        // 6: 牵引上颌颊侧        // 7：牵引下颌颊侧
        // 8：压力槽下颌舌侧        // 9：压力槽下颌颊侧
        // 10：压力槽上颌舌侧        // 11：压力槽上颌颊侧
        {
            {18,42},{41,42,38,38},{41,26.5f},{42,30.5f},{38,26},{38,22},
            {38,26},{46,32},
            {36,4},{36,4},{38,4},{38,4}
        }, // 8 -- 0
        {
            {17,78},{72,78,58,62},{77,27.5f},{78,31},{70,26},{70,23},
            {70,26},{78,32},
            {68,4},{68,4},{74,4},{74,4}
        }, // 7 -- 1
        {
            {16,116},{110,116,93,99},{115,28},{116,31},{106,28},{104,25},
            {106,28},{118,32},
            {106,4},{106,4},{110,4},{110,4}
        }, // 6 -- 2
        {
            {15,148},{140,150,130,142},{146,27.5f},{148.5f,31},{135,28},{136,26},
            {135,28},{150,33},
            {138,3},{138,3},{146,3},{146,3}
        }, // 5 -- 3
        {
            {14,180},{166,176,155,165},{174,27.5f},{174.5f,31},{158,30},{159,29},
            {158,30},{180,33},
            {162,3},{162,3},{170,4},{170,4}
        }, // 4 -- 4
        {
            {13,200},{190,204,176,189},{199,27.5f},{200,31},{186,41},{186,38},
            {186,41},{206,33},
            {190,4},{190,4},{198,3},{198,3}
        }, // 3 -- 5
        {
            {12,226},{218,226,208,218},{223,26},{223,30},{212,33},{214,30},
            {212,33},{228,33},
            {214,3},{214,3},{222,3},{222,3}
        }, // 2 -- 6
        {
            {11,246},{244,246,229,240},{244,25},{244,29},{238,39},{239,37},
            {238,39},{248,30},
            {236,4},{236,4},{242,3},{242,3}
        }, // 1 -- 7
        {
            {21,266},{268,266,260,260},{264,25},{263,29},{268,39},{268,37},
            {268,39},{262,31},
            {264,4},{264,4},{262,3},{262,3}
        }, // 1 -- 8
        {
            {22,286},{298,286,290,280},{285,26},{283,30},{296,33},{292,30},
            {296,33},{280,31},
            {294,3},{294,3},{284,3},{284,3}
        }, // 2 -- 9
        {
            {23,306},{322,312,311,300},{308,27},{305,31.5f},{322,41},{320,38},
            {322,41},{302,33},
            {318,4},{318,4},{304,4},{304,4}
        }, // 3 -- 10
        {
            {24,330},{346,336,342,327},{332.5f,27.5f},{331.5f,31.5f},{348,31.5f},{346.5f,28.5f},
            {348,31.5f},{329,31.5f},
            {346,3},{346,3},{332,3},{332,3}
        }, // 4 -- 11
        {
            {25,360},{372,362,364,354},{361,27},{359,31.5f},{372,28.5f},{368,26.5f},
            {372,28.5f},{358,29},
            {368,3},{368,3},{358,3},{358,3}
        }, // 5 -- 12
        {
            {26,390},{402,398,388,378},{394,27.5f},{391,31},{402,28},{400,25},
            {402,28},{388,32},
            {398,4},{398,4},{390,4},{390,4}
        }, // 6 -- 13
        {
            {27,430},{438,434,426,420},{428,27.5f},{428,31},{439,26},{436,23},
            {439,26},{426,32},
            {434,4},{434,4},{424,4},{424,4}
        }, // 7 -- 14
        {
            {28,464},{470,468,460,456},{466,26.5f},{466,30.5f},{466,26},{463,22},
            {466,26},{456,32},
            {465,4},{465,4},{462,4},{462,4}
        }  // 8 -- 15
    };

    public float toy(float y) {
    	return PageSize.A4.getTop() - y;
    }
    
    public String getFilename() {
    	long ms = System.currentTimeMillis();
        return "./"+ms+"test.pdf";        
    }
    
    public void init(String outputPath, OutputStream os) throws Exception {
    	
    	this.a4 = new Rectangle(PageSize.A4);
    	this.pageCount = 1;
    	if (os != null) {
    		this.writer = new PdfWriter(os);
    	} else {
    		this.writer = new PdfWriter(outputPath);
    	}
    	
    	this.pdfDoc = new PdfDocument(this.writer);
    	
    	this.document = new Document(pdfDoc);
    	
    	this.lineHeight = 18;
    }
    
    public void newPage() {
    	this.page = this.pdfDoc.addNewPage();
    	this.canvas = new PdfCanvas(this.page);
    }
    
    public void close() {
    	this.document.close();
    }
    
    public void fontFromPath(String path) throws Exception {
    	if (path == null) {
    		this.font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
    	} else {
    		this.font = PdfFontFactory.createFont(path, PdfEncodings.IDENTITY_H, true);	
    	}    	
    }
    
    public void drawText(String str, float x, float y, int size, Color color) {
    	this.canvas.saveState()
    		.beginText()
    		.setFontAndSize(this.font, size)
    		.setColor(color, true)
    		.moveText(x,  toy(y))
    		.showText(str)
    		.endText()
    		.restoreState();    
    }
    
    public void drawDashLine(float x1, float y1, float x2, float y2) {
    	this.canvas.saveState()
    		.setColor(new DeviceRgb(99, 143, 165), false)
    		.setLineDash(2, 2, 1.5f)
    		.moveTo(x1, toy(y1))
    		.lineTo(x2, toy(y2))
    		.closePathStroke()
    		.restoreState();
    }
    
    public void drawLine(float x1, float y1, float x2, float y2) {
    	this.canvas.saveState() 
    		.setColor(new DeviceRgb(99, 143, 165), false)
    		.moveTo(x1, toy(y1))
    		.lineTo(x2, toy(y2))
    		.closePathStroke()
    		.restoreState();
    }
    
    // 根据牙位区域转换牙位的顺序
    public int toIndex(int tid) {
    	int idx = -1;
    	if (tid < 20) {
    		idx = 18 - tid;    	
    	} else if (tid > 20 && tid < 30) {
    		idx = 8 + tid - 21;
    	} else if (tid > 30 && tid < 40) {
    		idx = 8 + tid - 31;
    	} else if (tid > 40) {
    		idx = 48 - tid;
    	}
    	return idx;
    }
    
    // 牙位之间需要有高低错位
    public boolean isDenseA(int tid) {
        if (tid == 15 || tid == 13 || tid == 11 || tid == 22 || tid == 24 ||
            tid == 45 || tid == 43 || tid == 41 || tid == 32 || tid == 34)
            return true;
        return false;
    }
    
    public boolean isDenseB(int tid) {
        if (tid == 15 || tid == 12 || tid == 22 || tid == 24)
            return true;
        return false;
    }
    
    public boolean isDenseC(int tid) {
        if (tid == 45 || tid == 43 || tid == 41 || 
            tid == 32 || tid == 34)
            return true;
        return false;
    }
    
    public static Image getImage(String imgPath) throws Exception {
    	return new Image(getImageData(imgPath));
    }
    
    public static ImageData getImageData(String imgPath) throws Exception {
    	return ImageDataFactory.create(imgPath);
    }
    
    public String resize(String str, float maxLine, PdfFont font, int fontSize) {
    	float left = 0;
    	StringBuilder sb = new StringBuilder();
    	for (int i=0; i<str.length(); ++i) {
    		char c = str.charAt(i);
    		float w = font.getWidth(c, fontSize);
    		if (left + w > maxLine) {
    			left = 0; 
    			sb.append("\n");
    		}
    		sb.append(c);
    		left += w;
    	}
    	return sb.toString();
    }
    
    // 生成的函数
    
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public PdfWriter getWriter() {
		return writer;
	}

	public void setWriter(PdfWriter writer) {
		this.writer = writer;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public PdfDocument getPdfDoc() {
		return pdfDoc;
	}

	public void setPdfDoc(PdfDocument pdfDoc) {
		this.pdfDoc = pdfDoc;
	}

	public Rectangle getA4() {
		return a4;
	}

	public void setA4(Rectangle a4) {
		this.a4 = a4;
	}

	public PdfFont getFont() {
		return font;
	}

	public void setFont(PdfFont font) {
		this.font = font;
	}

	public PdfPage getPage() {
		return page;
	}

	public void setPage(PdfPage page) {
		this.page = page;
	}

	public PdfCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(PdfCanvas canvas) {
		this.canvas = canvas;
	}

	public float getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(float lineHeight) {
		this.lineHeight = lineHeight;
	}
        
}
