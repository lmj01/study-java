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
			if (VideoGenerator.tar2Video(tarFile, "./test.mp4")) {
				System.out.println("Write to video mp4 success!");
			} else {
				System.out.println("Write to video mp4 with something wrong!!!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
