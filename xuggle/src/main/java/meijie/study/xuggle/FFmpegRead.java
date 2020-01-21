package meijie.study.xuggle;

import java.io.*;
 
import org.bytedeco.javacpp.*;
 
//ffmpeg
import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avformat.*;
import static org.bytedeco.javacpp.avutil.*;
import static org.bytedeco.javacpp.swscale.*;
 
public class FFmpegRead {
	static InputStream in=null;
	final int BUF_SIZE = 1400;
 
	
	Read_packet_Pointer_BytePointer_int read_buffer= new Read_packet_Pointer_BytePointer_int() {
 
		@Override
		public int call(Pointer opaque, BytePointer buf, int buf_size) {
			byte[] bytebuf=new byte[buf_size];
			int size=-1;
			try {
				size = in.read(bytebuf, 0, buf_size);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// arg1=new BytePointer(ByteBuffer.wrap(buf));
			buf.position(0);
			buf.put(bytebuf, 0, size);
			return size;
		}
	};
	
	public FFmpegRead() throws FileNotFoundException {
		AVFrame  pFrame = null;
		AVFrame  pFrameRGB = null;
		AVIOContext  pIoCtx=null;
		AVInputFormat  pInputFmt = new AVInputFormat();
		AVFormatContext  pFormatCtx = null;
		AVCodecContext  pCodecCtx = null;
		AVCodec  pCodec = null;
		SwsContext   pSwxCtx  =null;
		in = new FileInputStream("test2.mp4");
		
		int result = 0;
		 BytePointer inputBuffer = new BytePointer(av_malloc(BUF_SIZE));
		pIoCtx = avio_alloc_context(inputBuffer, BUF_SIZE, 0, null, read_buffer, null,null);
 
		int ret = av_probe_input_buffer2(pIoCtx, pInputFmt, (BytePointer)null, (Pointer)null, 0, 0);
		if (ret < 0) {
			System.out.println("探测失败");
			return ;
		}
		System.out.println(String.format("视频格式：%g %g", pInputFmt.name(), pInputFmt.long_name()));
		pFormatCtx = avformat_alloc_context();
		pFormatCtx.pb(pIoCtx);
		
		// step1: 打开媒体文件,最后2个参数是用来指定文件格式，buffer大小和格式参数，设置成null的话，libavformat库会自动去探测它们
		result = avformat_open_input(pFormatCtx, "0", null, null);
		if (result != 0)
		{
			System.out.println("open file fail");
			return  ;
		}
		// step2:查找信息流的信息
		result = avformat_find_stream_info(pFormatCtx, (PointerPointer<Pointer>)null);
		if (result != 0)
		{
			System.out.println("find stream fail");
			return  ;
		}
		// step3: 打印信息
		String filename = "test2.mp4";
		av_dump_format(pFormatCtx, 0, filename, 0);
 
 
		// step4：找到video流数据
		int i = 0;
		int videoStream = -1;
 
 
		for (i = 0; i < pFormatCtx.nb_streams(); i++){
			if (pFormatCtx.streams(i).codecpar().codec_type() == AVMEDIA_TYPE_VIDEO){
				videoStream = i;
				break;
			}
		}
 
 
		if (videoStream == -1){
			System.out.println("find stream video fail");
			return ;
		}
		System.out.println("find stream video succ.");
 
 
		// 得到video编码格式
		/* 新版推荐替换方法 */
		pCodecCtx = avcodec_alloc_context3(null);
		result = avcodec_parameters_to_context(pCodecCtx, pFormatCtx.streams(videoStream).codecpar());
		if (result < 0)
			return ;
		
		// step5: 得到解码器
		pCodec = avcodec_find_decoder(pCodecCtx.codec_id());
		if (pCodec == null){
			System.out.println("find decoder fail" );
			return ;
		}
		System.out.println("find decoder succ");
		result = avcodec_open2(pCodecCtx, pCodec, (PointerPointer<Pointer>)null);
		if (result != 0){
			System.out.println("open codec fail");
			return ;
		}
  
		// step6: 申请原始数据帧 和 RGB帧内存
		pFrame = av_frame_alloc();
		pFrameRGB = av_frame_alloc();
		if (pFrame == null || pFrameRGB == null)
		{
			return ;
		}
		int numBytes = av_image_get_buffer_size(AV_PIX_FMT_BGR24, pCodecCtx.width(), pCodecCtx.height(), 1);
		BytePointer rgbData=new BytePointer(av_malloc(numBytes));
		av_image_fill_arrays(pFrameRGB.data(), pFrameRGB.linesize(), rgbData, AV_PIX_FMT_BGR24, pCodecCtx.width(), pCodecCtx.height(), 1);
 
		AVPacket  packet = av_packet_alloc();
		i = 0;
 
 
		// step7: 创建格式转化文本
		pSwxCtx = sws_getContext(
				pCodecCtx.width(), pCodecCtx.height(), pCodecCtx.pix_fmt(),
				pCodecCtx.width(), pCodecCtx.height(), AV_PIX_FMT_RGB24,
				SWS_BILINEAR, null, null, (DoublePointer)null);

		while (true){
			// 得到数据包
			result = av_read_frame(pFormatCtx, packet);
			if (result != 0){
				break;
			}
 
			if (packet.stream_index() == videoStream){
				// 解码
				result = avcodec_send_packet(pCodecCtx, packet);
				if (result < 0) {
					System.out.println("Decode Error");
					return ;
				}
				result = avcodec_receive_frame(pCodecCtx, pFrame);
				if (result < 0 && result != -11) return  ;

				// 转换
				sws_scale(pSwxCtx, pFrame.data(), pFrame.linesize(), 0, pCodecCtx.height(),pFrameRGB.data(), pFrameRGB.linesize());
 			}
  
			av_packet_unref(packet);
		}
		
		avformat_close_input(pFormatCtx);
		av_packet_free(packet);
	}
	
}