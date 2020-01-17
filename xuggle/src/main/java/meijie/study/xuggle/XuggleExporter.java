package meijie.study.xuggle;

import java.awt.image.BufferedImage;
import java.time.LocalTime;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IMetaData;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
/*
 * 把数据写入磁盘中
 */
public class XuggleExporter {
	private static final int MIN_BITRATE = 2000000;
	private static final double BPP = 1;
	
	private IMediaWriter mediaWriter;
	private IConverter converter;
	private long frameNo;
	private double deltat;
	
	public void open(String path, int w, int h, int fps) throws Exception {
		int bitRate = (int)Math.max(w * h * fps * BPP,  MIN_BITRATE);
		IRational frameRate = IRational.make(fps, 1);
		deltat = 1e6 / frameRate.getDouble();
		
		this.mediaWriter = ToolFactory.makeWriter(path);
		this.mediaWriter.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, w, h);
		
		IPixelFormat.Type pixFmt = IPixelFormat.Type.YUV420P;
		this.converter = ConverterFactory.createConverter(ConverterFactory.XUGGLER_BGR_24, pixFmt, w, h);
		
		IContainer container = this.mediaWriter.getContainer();
		IStreamCoder coder = container.getStream(0).getStreamCoder();
		coder.setBitRate(bitRate);
		coder.setFrameRate(frameRate);
		coder.setTimeBase(IRational.make(frameRate.getDenominator(), frameRate.getNumerator()));
		coder.setFlag(IStreamCoder.Flags.FLAG_QSCALE, true);
		coder.setGlobalQuality(0);
		coder.setPixelType(pixFmt);
		
		IMetaData meta = container.getMetaData();
		// primary tags
		meta.setValue("title", "meijie-video");		
		meta.setValue("director", "1");
		meta.setValue("copyright", "2");
		meta.setValue("product", "3");
		meta.setValue("genre", "4");
		meta.setValue("second genre", "5");
		meta.setValue("subjects", "6");
		meta.setValue("keywords", "7");
		meta.setValue("comments", "8");
		meta.setValue("creation date", LocalTime.now().toString());
		// movie credits		
		meta.setValue("written by", "1-1");
		meta.setValue("produced by", "1-2");
		meta.setValue("edited by", "1-3");
		meta.setValue("cinematographer", "1-4");
		meta.setValue("music by", "1-5");
		meta.setValue("production", "1-6");
		meta.setValue("starring", "1-7");
		//meta.setValue("rating", "1-8");
		meta.setValue("country", "1-9");
		// additional tags
		meta.setValue("digitized by", "2-1");
		meta.setValue("encoded by", "2-2");
		
		this.frameNo = 0;
	}
	
	public void encode(BufferedImage image) throws Exception {
		IVideoPicture frame = this.converter.toPicture(image, (long)(this.frameNo * this.deltat));
		frame.setQuality(0);
		
		this.mediaWriter.encodeVideo(0, frame);
		this.frameNo++;
	}
	
	public void close() throws Exception {
		this.mediaWriter.close();
		this.converter.delete();
	}
}
