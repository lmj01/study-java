package com.meijie.study.image;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.StringValue;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.meijie.study.image.ImageMeta;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        String strs[] = {
        		"type1.jpg",
        		"type2.jpg",
        		"type3.png",
        		"type4.png"
        };
        
//        File imgFile1 = new File(App.class.getClassLoader().getResource("type1.jpg").getPath());
//        File imgFile2 = new File(App.class.getClassLoader().getResource("type2.jpg").getPath());
		
        App app = new App();

//        ImageMeta im = new ImageMeta();
//        app.printMetadata(imgFile, im);
        
        //app.printJpg(imgFile, im);
//        app.printFile(imgFile1);
//        System.out.println("\n\n\n");
//        System.out.println(String.format("image %s, width:%d, height:%d, orient: %s", imgFile1.getName(), im.getWidth(), im.getHeight(), im.getOrientation()));
        
        for (String str : strs) {
        	ImageMeta im = new ImageMeta();
        	File imgfile = new File(App.class.getClassLoader().getResource(str).getPath());
        	app.printFile(imgfile, im);
        	System.out.format("image %s, width:%d, height:%d, orient: %d\n", str, im.getWidth(), im.getHeight(), im.getOrientation());
        }
    }
    
    public void printFile(File file, ImageMeta im) {
    	try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			for (Directory directory : metadata.getDirectories()) {
				for (Tag tag : directory.getTags()) {
					if (tag.getTagName().equals("Image Height")) {
						System.out.format("%s, %s\n", tag.getDescription(), directory.getName());						
						String strs[] = tag.getDescription().split(" ");
						im.setHeight(Integer.parseInt(strs[0]));
					}
					if (tag.getTagName().equals("Image Width")) {
						//System.out.format("%s, %s\n", tag.getDescription(), directory.getName());						
						String strs[] = tag.getDescription().split(" ");
						im.setWidth(Integer.parseInt(strs[0]));
					}
					if (tag.getTagName().contains("Orientation")) {
						im.setOrientation(directory.getInt(ExifDirectoryBase.TAG_ORIENTATION));
					}
				}
			}
		} catch (ImageProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void printJpg(File imgFile, ImageMeta im) {    	
		try {
			Metadata metadata = JpegMetadataReader.readMetadata(imgFile);
			for (JpegDirectory directory: metadata.getDirectoriesOfType(JpegDirectory.class)) {
				System.out.format("%s -- \n", directory.getName());
				im.setWidth(directory.getInt(JpegDirectory.TAG_IMAGE_WIDTH));
				im.setHeight(directory.getInt(JpegDirectory.TAG_IMAGE_HEIGHT));
			}
			
//			ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
//			im.setWidth(exifDirectory.getInt(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH));
//			im.setHeight(exifDirectory.getInt(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT));
//			im.setOrientation(exifDirectory.getString(ExifSubIFDDirectory.TAG_ORIENTATION));
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
        
    public void printMetadata(File imgfile, ImageMeta im) {
    	Metadata metadata;
    	
		try {
			metadata = JpegMetadataReader.readMetadata(imgfile);
			for (Directory directory : metadata.getDirectories()) {
				System.out.format("directory -- %s\n", directory.getName());
				for (Tag tag : directory.getTags()) {
					if (ExifDirectoryBase.TAG_IMAGE_WIDTH == tag.getTagType()) {
						im.setWidth(directory.getInt(ExifDirectoryBase.TAG_IMAGE_WIDTH));
						im.setHeight(directory.getInt(ExifDirectoryBase.TAG_IMAGE_HEIGHT));
						System.out.format("width=%d, height=%d\n", im.getWidth(), im.getHeight());
					}
					System.out.format("%s = %s\n", tag.getTagName(), tag.getDescription());
				}
			}
			
//			Iterable<Directory> directoryIterable = metadata.getDirectories();
//	        if (directoryIterable != null) {
//	        	Iterator<Directory> directoryIterator = directoryIterable.iterator();
//	        	while (directoryIterator.hasNext()) {
//	        		Directory directory = directoryIterator.next();	        		
//	        		Collection<Tag> tags = directory.getTags();
//	        		for (Tag tag : tags) {
//	        			System.out.println(tag);
//	        		}
//	        	}	        	
//        		        	
//	        	Iterable<ExifSubIFDDirectory> exifSubIFDDirectory = metadata.getDirectoriesOfType(ExifSubIFDDirectory.class);
//	        	if (exifSubIFDDirectory != null) {
//	        		Iterator<ExifSubIFDDirectory> exifSubIterator = exifSubIFDDirectory.iterator();
//	        		if (exifSubIterator != null && exifSubIterator.hasNext()) {
//	        			ExifSubIFDDirectory dr = exifSubIterator.next();
//	        			if (dr.containsTag(ExifSubIFDDirectory.TAG_USER_COMMENT)) {
//	        				System.out.print(dr.getDescription(ExifSubIFDDirectory.TAG_USER_COMMENT));
//	        			}
//	        			if (dr.containsTag(ExifSubIFDDirectory.TAG_IMAGE_WIDTH)) {
//	        				im.setWidth(dr.getInt(ExifSubIFDDirectory.TAG_IMAGE_WIDTH));
//	        				im.setHeight(dr.getInt(ExifSubIFDDirectory.TAG_IMAGE_HEIGHT));
//	        			}
//	        		}
//	        	}
//	        }
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
    }
}
	