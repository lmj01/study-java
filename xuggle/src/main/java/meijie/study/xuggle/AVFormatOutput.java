package meijie.study.xuggle;

import org.bytedeco.javacpp.avformat.AVFormatContext;
import org.bytedeco.javacpp.avformat.AVStream;
import org.bytedeco.javacpp.avutil.AVFrame;
import org.bytedeco.javacpp.swscale.SwsContext;

import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avformat.*;
import static org.bytedeco.javacpp.avutil.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.avcodec.AVCodecContext;

public class AVFormatOutput {

	public void test() {
		
		AVFormatContext fmtCtx = avformat_alloc_context();
		AVStream video_str = add_video_stream(fmtCtx);
	}
	
	public AVStream add_video_stream(AVFormatContext fmtCtx) {
		AVCodecContext ctx = null;
		AVStream st = null;
		AVCodec codec = null;
		st = avformat_new_stream(fmtCtx, codec);
		
		ctx = st.codec();
		ctx.codec_id(AV_CODEC_ID_MPEG4);
		ctx.bit_rate(400000);
		ctx.width(800);
		ctx.height(600);
		ctx.time_base(av_stream_get_r_frame_rate(st));
		ctx.gop_size(12);
//		ctx.pix_fmt(STREAM_PIX_FMT)
		
		
		return st;
	}
	/*
	 * pixFmt , e.g.  AV_PIX_FMT_RGB24
	 */
	public AVFrame createAVFrameByFormat(int pixFmt, int width, int height, DataBufferByte buf) {
		AVFrame frame = av_frame_alloc();
		int linesize = av_image_get_linesize(pixFmt, width, height);
		int bufSize = av_image_get_buffer_size(pixFmt, width, height, linesize);
		Pointer buffer = av_malloc(bufSize);		
		ByteBuffer ss = (ByteBuffer) buffer.asBuffer();
		ss.put(buf.getData(), 0, buf.getSize());
		int ret = av_image_fill_arrays(frame.data(), frame.linesize(), (BytePointer)buffer, AV_PIX_FMT_BGR24, 
				width, height, linesize);
		if (ret < 0) {
			av_frame_free(frame);
			return null;
		}		
		return frame;
	}
	
	public AVFrame bufferedImage2Frame(BufferedImage image, int width, int height) {
		Raster ra = image.getRaster();
		DataBufferByte buf = (DataBufferByte)ra.getDataBuffer();		
		return createAVFrameByFormat(AV_PIX_FMT_YUV420P, width, height, buf);
	}
		
	public void write_video_frame(AVStream st) {
		SwsContext swsCtx;
		
		AVPacket packet = null;
		st.attached_pic(packet);
	}
	
	public ByteBuffer saveFrame(AVFrame pFrame, int width, int height) {
		BytePointer data = pFrame.data(0);
		int size = width * height * 3;
		ByteBuffer buf = data.position(0).limit(size).asBuffer();
		return buf;
	}
	
	
	public BufferedImage bgr2BufferedImage(ByteBuffer src, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Raster ra = image.getRaster();
		DataBufferByte out = (DataBufferByte)ra.getDataBuffer();
		ByteBuffer.wrap(out.getData()).put(src);
		return image;
	}
		
	public BufferedImage bgr2BufferedImage(IntBuffer src, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Raster ra = image.getRaster();
		DataBufferInt out = (DataBufferInt)ra.getDataBuffer();
		IntBuffer.wrap(out.getData()).put(src);
		return image;
	}
	
 	public void fill_yuv_image(AVFrame frame, int frame_index, int width, int height) {
		int x, y, i;
		i = frame_index;
		/* Y */
		for (y=0;y<height;y++) {
			for (x=0;x<width;x++) {
				BytePointer buf = new BytePointer();
				byte[] bytebuf=new byte[10];				
				buf.position(0);
				buf.put(bytebuf, 0, 10);
				frame.data(0, buf);
			}
		}
		/* Cb and Cr */
	}
}
