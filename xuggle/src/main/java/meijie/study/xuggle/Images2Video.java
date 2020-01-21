package meijie.study.xuggle;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avcodec.AVCodec;
import org.bytedeco.javacpp.avcodec.AVCodecContext;
import org.bytedeco.javacpp.avformat.AVFormatContext;
import org.bytedeco.javacpp.avutil.AVRational;

import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avutil.*;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;

import static org.bytedeco.javacpp.avformat.*;

public class Images2Video {

	public void test() {
				
		avcodec.avcodec_register_all();
		
		AVCodec pCodec = avcodec.avcodec_find_encoder(AV_CODEC_ID_MPEG4 );
		AVCodecContext pCodecCtx = avcodec.avcodec_alloc_context3(null);
		pCodecCtx.bit_rate(400000);
		pCodecCtx.width(1400);
		pCodecCtx.height(944);
		AVRational rational = new AVRational();
		rational.den(25);
		rational.num(1);
		pCodecCtx.time_base(rational);
		// emit one intra frame every ten freames
		pCodecCtx.gop_size(10);
//		AVFormatContext pixFmt = avformat_alloc_context();
		pCodecCtx.max_b_frames(60);
		pCodecCtx.pix_fmt(AV_PIX_FMT_YUV420P);
		
		AVDictionary avDic = null;
		
		int ret = avcodec.avcodec_open2(pCodecCtx, pCodec, avDic);
		if (ret < 0) {
			System.out.println("打开失败!");
			return;
		}
		
		// 读取文件
		AVFormatContext pFmtCtx = null;
		ret = avformat_open_input(pFmtCtx, (BytePointer)null, null, null);
		if (ret < 0) {
			System.out.println("读取文件失败！");
			return;
		}
		
		av_dump_format(pFmtCtx, 0, (BytePointer)null, 0);
		
		// output codec context
		AVCodecContext pICodecCtx;
		pICodecCtx = pFmtCtx.streams(0).codec();
		pICodecCtx.width(1400);
		pICodecCtx.height(944);
		pICodecCtx.pix_fmt(AV_PIX_FMT_YUV420P);
		
		AVCodec pICodec = avcodec_find_decoder(pICodecCtx.codec_id());
		ret = avcodec_open2(pICodecCtx, pICodec, (PointerPointer)null);
		if (ret < 0) {
			System.out.println("不能找到decoder");
			return;
		}
		
		AVDictionary options;
		
		// 成对出现
		AVFrame pIFrame = av_frame_alloc();	
		if (pIFrame != null) {
			System.out.println("不能申请input frame");
			return;
		}
		
		int bufSize = av_image_get_buffer_size(AV_PIX_FMT_RGB24, pICodecCtx.width(), pICodecCtx.height(), 0);
		Pointer buffer = av_malloc(bufSize);
		ret = av_image_fill_arrays(pIFrame.data(), pIFrame.linesize(), (BytePointer)buffer, AV_PIX_FMT_BGR24, 
				pIFrame.width(), pIFrame.height(), 1);
		if (ret < 0) {
			System.out.println("填充图片错误！");
			return;
		}
		
		av_frame_free(pIFrame);
		
		AVPacket packet = av_packet_alloc();
		av_read_frame(pFmtCtx, packet);
		packet.data();
		
		av_packet_free(packet);
		
	}

	public void toJPEG(AVCodecContext pCodecCtx, AVFrame pFrame, int frameNo) {
		AVCodec pOCodec = null;
		int pixFmt = AV_PIX_FMT_YUVJ420P;
		int linesize = av_image_get_linesize(pixFmt, pCodecCtx.width(), pCodecCtx.height());
		int bufSize = av_image_get_buffer_size(pixFmt, pCodecCtx.width(), pCodecCtx.height(), linesize);
		Pointer buffer = av_malloc(bufSize);
		AVCodecContext pOCodecCtx = avcodec_alloc_context3(null);
		if (pOCodecCtx == null) {
			av_free(buffer);
			return;
		}
		pOCodecCtx.bit_rate(pCodecCtx.bit_rate());
		pOCodecCtx.width(pCodecCtx.width());
		pOCodecCtx.height(pCodecCtx.height());
		pOCodecCtx.pix_fmt(pCodecCtx.pix_fmt());
		pOCodecCtx.codec_id(AV_CODEC_ID_MJPEG);
		//pOCodecCtx.codec_type(AV_CODEC_TYPE_VIDEO);
		pOCodecCtx.time_base().num(pCodecCtx.time_base().num());
		pOCodecCtx.time_base().den(pCodecCtx.time_base().den());
		
		pOCodec = avcodec_find_decoder(pOCodecCtx.codec_id());
		if (pOCodec == null) {
			av_free(buffer);
			return;
		}
		AVDictionary options = new AVDictionary();
		if (avcodec_open2(pOCodecCtx, pOCodec, options) < 0) {
			av_free(buffer);
			return;
		}
		
		pOCodecCtx.mb_lmin(pOCodecCtx.qmin() * FF_QP2LAMBDA);
		pOCodecCtx.mb_lmax(pOCodecCtx.qmax() * FF_QP2LAMBDA);
		pOCodecCtx.flags(AV_CODEC_FLAG_QSCALE);
		pOCodecCtx.global_quality(pOCodecCtx.qmin() * FF_QP2LAMBDA);
		
		pFrame.pts(1);
		pFrame.quality(pOCodecCtx.global_quality());
		
		// 解码视频， 不支持啦！
//		int bufSizeActual = avcodec_encode_video(pOCodecCtx, buffer, bufSize, pFrame);
		AVPacket packet = null;
		avcodec_encode_video2(pOCodecCtx, packet, pFrame, (IntPointer)null);
		
		// 字幕
//		AVSubtitle subtitle = av_sub
//		avcodec_encode_subtitle(pOCodecCtx, (BytePointer)buffer, bufSize, subtitle);
		
		avcodec_close(pOCodecCtx);
		av_free(buffer);
	}
}
