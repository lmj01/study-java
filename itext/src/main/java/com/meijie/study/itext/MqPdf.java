package com.meijie.study.itext;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;

public class MqPdf extends TemplatePdf {

	public void draw() throws Exception {
		
		this.init(this.getFilename(), null);
		
		this.fontFromPath(null);
		
		this.newPage();
		
		float x = 10, y = 20;
		Color fontColor = new DeviceRgb(187, 56, 107);
		
		int width = 108, height = 48;
		BarCode.toByteArray(BarCode.encodeBarcode("123456789098", width, height));
				
		this.getCanvas().addImage(
				ImageDataFactory.create(BarCode.toByteArray(BarCode.encodeBarcode("123456789098", width, height))),
				x, y, height, true, false);
		
		this.drawText("test", x, y, 10, fontColor);
		
		this.close();
	}
}
