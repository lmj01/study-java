package meijie.study.xuggle;
//
//import static org.bytedeco.javacpp.avcodec.av_packet_alloc;
//import static org.bytedeco.javacpp.avcodec.av_packet_unref;
//import static org.bytedeco.javacpp.avcodec.avcodec_alloc_context3;
//import static org.bytedeco.javacpp.avcodec.avcodec_parameters_alloc;
//import static org.bytedeco.javacpp.avcodec.avcodec_parameters_to_context;
//import static org.bytedeco.javacpp.avformat.AVFMT_NOFILE;
//import static org.bytedeco.javacpp.avformat.AVIO_FLAG_WRITE;
//import static org.bytedeco.javacpp.avformat.av_dump_format;
//import static org.bytedeco.javacpp.avformat.av_interleaved_write_frame;
//import static org.bytedeco.javacpp.avformat.av_write_trailer;
//import static org.bytedeco.javacpp.avformat.avformat_alloc_context;
//import static org.bytedeco.javacpp.avformat.avformat_alloc_output_context2;
//import static org.bytedeco.javacpp.avformat.avformat_free_context;
//import static org.bytedeco.javacpp.avformat.avformat_new_stream;
//import static org.bytedeco.javacpp.avformat.avformat_write_header;
//import static org.bytedeco.javacpp.avformat.avio_open;
//import static org.bytedeco.javacpp.avutil.AV_PIX_FMT_YUV420P;
//import static org.bytedeco.javacpp.avutil.av_dict_set;
//import static org.bytedeco.javacpp.avutil.av_frame_alloc;
//import static org.bytedeco.javacpp.avutil.av_frame_free;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.avformat.AVFormatContext;
//import org.bytedeco.javacpp.avformat.AVInputFormat;
//import org.bytedeco.javacpp.avformat.Read_packet_Pointer_BytePointer_int;
import org.bytedeco.javacpp.avutil.AVFrame;

import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avformat.*;
import static org.bytedeco.javacpp.avutil.*;
import static org.bytedeco.javacpp.swscale.*;

public class FFmpeg5 {
	
	private AVFormatContext pOutFmtCtx = null;
	private AVOutputFormat pOutFmt = null;
	private AVIOContext pIOCtx = null;
	private int pixFmt;
	private int width;
	private int height;
	private boolean isInitial = false;
	private String filename;
	
	// https://blog.csdn.net/leonpengweicn/article/details/8447044
	// https://blog.csdn.net/leixiaohua1020/article/details/44116215
	
	public FFmpeg5(String fname) {
		filename = fname;
	}
	
	public void begin() {
		
//		av_register_all();
		av_register_output_format(pOutFmt);
		
		pixFmt = AV_PIX_FMT_YUV420P;
		
//		pOutFmt = new AVOutputFormat();
		pOutFmt = av_guess_format("mp4", null, null);
		if (pOutFmt == null) {
			System.out.println("guess format mp4 return null");
		} else {
			System.out.println("output mp4 format");
		}
		pOutFmt.init();
		pOutFmt.flags(AVFMT_NOFILE);
		pOutFmt.video_codec(AV_PIX_FMT_YUV420P);
		pOutFmt.write_header();
		
		// media container initial
		pOutFmtCtx = avformat_alloc_context();
		pOutFmtCtx.video_codec_id(pixFmt);
		
		// 
		pOutFmtCtx.oformat(pOutFmt);		
		// AVFMT_NOFILE after setted, the pb must be null
		pOutFmtCtx.pb(null);
		
		// init muxer
		
	}
	
	public void bind2File(String filename) {
		int ret = 0;
		
		// 文件名可能为空
		avformat_alloc_output_context2(pOutFmtCtx, null, null, filename);				
		
		// 这里可以不设置文件，直接缓存
		av_dump_format(pOutFmtCtx, 0, filename, 1);
		if ((pOutFmtCtx.oformat().flags() & AVFMT_NOFILE)==0) {
			ret = avio_open(pOutFmtCtx.pb(), filename, AVIO_FLAG_WRITE);
		}
		
	}
	
	public void writeHeader() {
		
		AVDictionary opts = new AVDictionary();
//		av_dict_set(opts, "movflags", "frag_keyframe+empty_moov+default_base_moof", 0);
		av_dict_set(opts, "preset", "slow", 0);
		av_dict_set(opts, "tune", "zerolatency", 0);
		
		// mux的第一步 写文件头信息
		int ret = avformat_write_header(pOutFmtCtx, opts);
		if (ret == AVSTREAM_INIT_IN_WRITE_HEADER) {
			System.out.println("if the codec had not already been fully initialized in avformat_init");
		} else if (ret == AVSTREAM_INIT_IN_INIT_OUTPUT) {
			System.out.println("if the codec had already been fully initialized in avformat_init");
		} else if (ret < 0) {
			System.out.println("Some Error occure");
		}
	}
	
	public void addFrame(BufferedImage image) {
		int ret = 0;
					
		// represents audio, video, subtitle, metadata and so on
		AVStream stream = avformat_new_stream(pOutFmtCtx, null);
		
		// AVCodec , like h264, vp9 and so on
		
		// codec init
		// codec encode
		// codec close
		
		AVCodecContext codecCtx = avcodec_alloc_context3(null);

		int codec_id = av_guess_codec(pOutFmt, null, filename, null, AVMEDIA_TYPE_VIDEO);
		AVCodec codec = avcodec_find_decoder(codec_id);
		if (codec == null) {
			System.out.println("not find codec by avcodec_find_decoder");
			codec = codecCtx.codec();
		} 
		
		AVCodecParameters codecParam = avcodec_parameters_alloc();
		codecParam.codec_id(codec_id);
		codecParam.width(width);
		codecParam.height(height);
		codecParam.codec_type(AVMEDIA_TYPE_VIDEO);
							
		ret = avcodec_parameters_to_context(codecCtx, codecParam);
		if (ret < 0) {
			System.out.println("set codec parameter fail");
		}
		
		codecCtx.pix_fmt(pixFmt);
		codecCtx.time_base().den(1);
		codecCtx.time_base().num(60);
		
		if ((pOutFmt.flags() & AVFMT_GLOBALHEADER) != 0) {
			codecCtx.flags(codecCtx.flags() | AV_CODEC_FLAG_GLOBAL_HEADER);
		}
		
		if (avcodec_open2(codecCtx, codec, (PointerPointer)null) < 0) {
			System.out.println("can't open avcodec open2");
		}
		
		if ((pOutFmt.flags() & AVFMT_NOFILE) == 0) {
			System.out.println("output flags is not file type");
		}
		
//		this.writeHeader();
//		av_write_header(pOutFmt);
		
		stream.codec(codecCtx);
		
		//ret = avcodec_parameters_copy(stream.codecpar(), codecParam);
					
		// slice of compressed data
		AVPacket packet = av_packet_alloc();
		
		
		AVFrame frame = image2Frame(image);
		
		//av_packet_rescale_ts(pkt, tb_src, tb_dst);
		
		frame.data(0, packet.data());
		
		BytePointer buffer = new BytePointer(frame.asByteBuffer());
		packet.data(buffer);	
				
		// mux第二步，写packet
		// 二选一
		// av_write_frame直接写进入Mux，没有缓存和重新排序，需要用户自己设置
		// interleaved将对packet进行缓存和pts检查，
		ret = av_interleaved_write_frame(pOutFmtCtx, packet);			
		//ret = av_write_frame(pOutFmtCtx, packet);
		
		av_frame_free(frame);
		
		av_packet_unref(packet);
		
	}
	
	public void writeTrail() {
		// mux第三步，写文件尾部
		av_write_trailer(pOutFmtCtx);
	}
	
	public void end() {
		
		
		
		avformat_free_context(pOutFmtCtx);
	}
	
	public void imageFile2Video(File tarFile) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			try (TarArchiveInputStream fin = new TarArchiveInputStream(new FileInputStream(tarFile))) {
				TarArchiveEntry entry;
				//while((entry = fin.getNextTarEntry()) != null) {
//					if (entry.isDirectory()) continue;
										
				entry = fin.getNextTarEntry();
					outStream.reset();
					IOUtils.copy(fin, outStream);				
					BufferedImage image = ImageIO.read(new ByteArrayInputStream(outStream.toByteArray()));
					if (!isInitial) {
						setWidth(image.getWidth());
						setHeight(image.getHeight());
						begin();
						writeHeader();
						isInitial = true;
					}
					addFrame(image);
//				}
			}
			
			writeTrail();
			end();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setWidth(int _width) {
		if (width < 1) width = _width;
	}
	public void setHeight(int _height) {
		if (height < 1) height = _height;
	}
	
	public AVFrame image2Frame(BufferedImage image) {
		AVFrame frame = av_frame_alloc();
		int linesize = av_image_get_linesize(pixFmt, width, height);
		int bufSize = av_image_get_buffer_size(pixFmt, width, height, linesize);
		BytePointer buffer = new BytePointer(av_malloc(bufSize));
		
		int ret = av_image_fill_arrays(frame.data(), frame.linesize(), (BytePointer)buffer, pixFmt, 
				width, height, linesize);
		if (ret < 0) {
			av_frame_free(frame);
			return null;
		}
		
		Raster ra = image.getRaster();
		DataBufferByte buf = (DataBufferByte)ra.getDataBuffer();
		ByteBuffer byteBuffer = buffer.asByteBuffer();
		byteBuffer.put(buf.getData(), 0, bufSize);
//		frame.data(0, (BytePointer)buf.getData());
		frame.data(0, (BytePointer)buffer);
		return frame;
	}
}
