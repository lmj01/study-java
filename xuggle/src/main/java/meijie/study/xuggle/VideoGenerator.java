package meijie.study.xuggle;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class VideoGenerator {
	
	public static boolean tar2Video(File tarFile, String videoPath) {
		try {
			XuggleExporter exporter = new XuggleExporter();
			exporter.open(videoPath, 1420, 944, 60);
			
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			try (TarArchiveInputStream fin = new TarArchiveInputStream(new FileInputStream(tarFile))) {
				TarArchiveEntry entry;
				while((entry = fin.getNextTarEntry()) != null) {
					if (entry.isDirectory()) continue;
										
					outStream.reset();
					IOUtils.copy(fin, outStream);				
					BufferedImage image = ImageIO.read(new ByteArrayInputStream(outStream.toByteArray()));
					
					exporter.encode(toFormatImage(image, BufferedImage.TYPE_3BYTE_BGR));
				}
				exporter.close();
			}
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static BufferedImage toFormatImage(BufferedImage source, int targetType) {
		BufferedImage dst;
		if (source.getType() == targetType) {
			dst = source;
		} else {
			dst = new BufferedImage(source.getWidth(), source.getHeight(), targetType);
			dst.getGraphics().drawImage(source, 0, 0, null);
		}
		return dst;
	}
}
