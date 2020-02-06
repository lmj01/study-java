package meijie.study.xuggle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        File tarFile = new File(App.class.getClassLoader().getResource("test.tar").getPath());
        List<BufferedImage> images = new ArrayList<>();
        try {
//			if (VideoGenerator.tar2Video(tarFile, "./test.mp4")) {
//				System.out.println("Write to video mp4 success!");
//			} else {
//				System.out.println("Write to video mp4 with something wrong!!!");
//			}
        	
//        	TestJavaCpp.test();
        	
//        	FFmpegRead ffmpegRead=new FFmpegRead();
        	
        	//FFmpeg2.test(tarFile, ".\\test3.mp4");
        	
//        	FFmpeg3 ff3 = new FFmpeg3();
        	//ff3.probeMp4();
//        	ff3.createVideo();
//        	ff3.createVideo1();
        	
//        	FFmpeg4 ff4 = new FFmpeg4();
//        	ff4.readImageFromVideo("./test3.mp4");
        	
        	FFmpeg5 ff5 = new FFmpeg5("./test5.mp4");
        	ff5.imageFile2Video(tarFile);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
