package meijie.study.xuggle;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

/*
 * 直接调用process来执行
 */
public class FFmpeg2 {

	public static void deleteFile(File file) {
		if (!file.exists()) return;
		
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				deleteFile(f);
			}
		}
		file.setWritable(true, true);
		Boolean ret = file.delete();
		System.out.println(String.format("delete %s %s", file.getAbsolutePath(), ret.toString()));
	}
	
	public static void test(File tarFile, String videoPath) {
		LocalDateTime ldt = LocalDateTime.now();
		String prefixPath = String.format("tmp/%d", ldt.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
		File targetFolder = new File(prefixPath);
		targetFolder.mkdir();
		int width = 0, height = 0;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		
		try (TarArchiveInputStream fin = new TarArchiveInputStream(new FileInputStream(tarFile))) {
			TarArchiveEntry entry;
			while((entry = fin.getNextTarEntry()) != null) {
				if (entry.isDirectory()) continue;
				File curFile = new File(prefixPath, entry.getName());
				curFile.setWritable(true);
				outStream.reset();
				IOUtils.copy(fin, new FileOutputStream(curFile));
				if (width == 0) {
					BufferedImage image = ImageIO.read(curFile);
					width = image.getWidth();
					height = image.getHeight();
				}				
			}
			
			ProcessBuilder pb = new ProcessBuilder();
			
			List<String> args = new ArrayList<>();
			args.add("D:\\portableApp\\ffmpeg-4.2.1-win64-static\\bin\\ffmpeg.exe");
			args.add("-r");
			args.add("60");
			args.add("-f");
			args.add("image2");
			args.add("-s");
			args.add(String.format("%dx%d", width,height));
			args.add("-i");
			args.add(String.format("%s/%%07d.png", prefixPath));
			args.add("-vcodec");
			args.add("libx264");
			args.add("-crf");
			args.add("25");
			args.add("-pix_fmt");
			args.add("yuv420p");
			args.add("-y");
			args.add(videoPath);
//			for (String str : args) System.out.println(str);
			Process process = pb.command(args).start();
			
			try (BufferedReader read = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
				String line;
				while((line = read.readLine()) != null) {
					System.out.println(line);
				}	
			};
			process.waitFor();
			
			System.out.println("after encode for video "+ videoPath);
			deleteFile(targetFolder);
			targetFolder.deleteOnExit();
			System.gc();
//			ProcessBuilder pb2 = new ProcessBuilder();
//			args.clear();
//			args.add("D:\\portableApp\\ffmpeg-4.2.1-win64-shared\\bin\\ffplay.exe");
//			args.add(videoPath);
//			pb2.command(args).start();					
//			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
