package meijie.study.xuggle;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.avformat.AVFormatContext;
import org.bytedeco.javacpp.avformat.AVInputFormat;
import org.bytedeco.javacpp.avformat.Read_packet_Pointer_BytePointer_int;
import org.bytedeco.javacpp.avutil.AVFrame;

import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avformat.*;
import static org.bytedeco.javacpp.avutil.*;
import static org.bytedeco.javacpp.swscale.*;

public class FFmpeg3 {

	private InputStream inStream;
	
	Read_packet_Pointer_BytePointer_int read_buffer= new Read_packet_Pointer_BytePointer_int() {		 
		@Override
		public int call(Pointer opaque, BytePointer buf, int buf_size) {
			System.out.println(String.format("%d", buf_size));			
			return buf_size;
		}
	};
	
	public void createVideo1() {
		int ret = 0;
		String outFilename = "./test5.mp4";
		
		// media container
		AVFormatContext pOutFmtCtx = avformat_alloc_context();
		pOutFmtCtx.video_codec_id(AV_PIX_FMT_YUV420P);
		
		// 文件名可能为空
		avformat_alloc_output_context2(pOutFmtCtx, null, null, outFilename);				
		
		// 这里可以不设置文件，直接缓存
		av_dump_format(pOutFmtCtx, 0, outFilename, 1);
		if ((pOutFmtCtx.oformat().flags() & AVFMT_NOFILE)==0) {
			ret = avio_open(pOutFmtCtx.pb(), outFilename, AVIO_FLAG_WRITE);
		}
		
		AVDictionary opts = new AVDictionary();
		av_dict_set(opts, "movflags", "frag_keyframe+empty_moov+default_base_moof", 0);
		// mux的第一步 写文件头信息
		avformat_write_header(pOutFmtCtx, opts);
		
		for (int i=0; i< 30; i++) {
			
			// represents audio, video, subtitle, metadata and so on
			AVStream stream = avformat_new_stream(pOutFmtCtx, null);
			
			// AVCodec , like h264, vp9 and so on
			
			
			AVCodecContext codecCtx = avcodec_alloc_context3(null);
			
			AVCodecParameters codecParam = avcodec_parameters_alloc();
						
			avcodec_parameters_to_context(codecCtx, codecParam);
			
			stream.codec(codecCtx);
			
			//ret = avcodec_parameters_copy(stream.codecpar(), codecParam);
						
			// slice of compressed data
			AVPacket packet = av_packet_alloc();
			
			AVFrame frame = av_frame_alloc();
			
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
		
		// mux第三步，写文件尾部
		av_write_trailer(pOutFmtCtx);
		
		avformat_free_context(pOutFmtCtx);		
	}
	
	public void createVideo() {
		
		int width = 1420, height = 944;
		int pixFmt = AV_PIX_FMT_YUV420P, ret = 0;
		
		AVFormatContext pFmtCtx = avformat_alloc_context();
		pFmtCtx.bit_rate(400000);
		pFmtCtx.video_codec_id(pixFmt);
		
		ByteBuffer buffer = pFmtCtx.asByteBuffer();		
		AVIOContext pIOCtx = avio_alloc_context(buffer, 0, 1, null, null, null, null);		
		
		
		// av io context to ByteArrayOutputStream		
		//ByteBuffer byteData = pIOCtx.position(0).asByteBuffer();
		ByteBuffer byteData = ByteBuffer.allocate(100);
		byte[] bytes = new byte[32];
		for (int i=0;i<bytes.length;i++) {
			bytes[i] = 'a';
		}
		bytes[31]='b';
		byteData.wrap(bytes, 0, bytes.length);
		System.out.println(String.format("byte-buffer --- %d, %d", 
				byteData.limit(), 
				byteData.remaining() 
				));
		System.out.println(byteData.array());
		System.out.println(byteData.getChar(0));
		
		try(FileOutputStream stream = new FileOutputStream("./test5.txt")) {
			stream.write(byteData.array());
			//stream.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void images2Video() {
		File pngFile = new File("test.png");
		
		BufferedImage image;
		try {
			
			image = ImageIO.read(pngFile);
			
			AVFrame pFrame = getYuv420pFrame(image, image.getWidth(), image.getHeight());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void probeMp4() {
		File mp4File = new File("test3.mp4");
		
		try {
			this.inStream = new FileInputStream(mp4File);
			int fileSize = this.inStream.available();
			System.out.println(fileSize);
			byte[] byteData = new byte[fileSize];
			this.inStream.read(byteData);
			BytePointer buffer = new BytePointer(av_malloc(fileSize));
			buffer.put(byteData, 0, fileSize);
			AVIOContext  pIoCtx = avio_alloc_context(buffer, fileSize, 0, null, 
					//read_buffer,
					null, 
					null, null);
			
			AVInputFormat  pInputFmt = new AVInputFormat();
			int ret = av_probe_input_buffer2(pIoCtx, pInputFmt, buffer, (Pointer)null, 0, fileSize);
			if (ret < 0) {
				System.out.println("未知格式");
				return;
			}
			System.out.println(String.format("视频格式：%s %g %d", 
					pInputFmt.name(), 
					pInputFmt.long_name(),
					ret
					));
			AVFormatContext  pFmtCtx = avformat_alloc_context();
			pFmtCtx.pb(pIoCtx);
			
			ret = avformat_open_input(pFmtCtx, "0", null, null);
			if (ret != 0)
			{
				System.out.println("avformat opne input return error");
				return  ;
			}			
			int videoStream = -1;
			for (int i = 0; i < pFmtCtx.nb_streams(); i++){
				if (pFmtCtx.streams(i).codecpar().codec_type() == AVMEDIA_TYPE_VIDEO){
					videoStream = i;
					break;
				}
			}
			
			if (videoStream == -1){
				System.out.println("find stream video fail");
				return ;
			}
			System.out.println("find stream video succ.");
			
			AVCodecContext pCodecCtx = avcodec_alloc_context3(null);
			ret = avcodec_parameters_to_context(pCodecCtx, pFmtCtx.streams(videoStream).codecpar());
			if (ret < 0)
				return ;
			
			// step5: 得到解码器
			AVCodec pCodec = avcodec_find_decoder(pCodecCtx.codec_id());
			if (pCodec == null){
				System.out.println("find decoder fail" );
				return ;
			}
			System.out.println("find decoder succ");
			
			avformat_close_input(pFmtCtx);
			
			av_free(buffer);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public AVFrame allocFrameByFormat(int pixFmt, int width, int height) {
		AVFrame frame = av_frame_alloc();
		int linesize = av_image_get_linesize(pixFmt, width, height);
		int bufSize = av_image_get_buffer_size(pixFmt, width, height, linesize);
		Pointer buffer = av_malloc(bufSize);				
		int ret = av_image_fill_arrays(frame.data(), frame.linesize(), (BytePointer)buffer, AV_PIX_FMT_BGR24, 
				width, height, linesize);
		if (ret < 0) {
			av_frame_free(frame);
			return null;
		}		
		return frame;
	}
	
	public AVFrame getYuv420pFrame(BufferedImage image, int width, int height) {
		Raster ra = image.getRaster();
		DataBufferByte buf = (DataBufferByte)ra.getDataBuffer();		
		AVFrame pFrame = allocFrameByFormat(AV_PIX_FMT_YUV420P, width, height);
//		ByteBuffer ss = (ByteBuffer) buffer.asBuffer();
//		ss.put(buf.getData(), 0, buf.getSize());
		return pFrame;
	}

}
