package com.simple.common.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;


/**
 *
 * @author kangjie
 *
 */
public class ImageHandleUtil {

	private static final Log logger = LogFactory.getLog(ImageHandleUtil.class);
	private static final int BUFFER_SIZE = 16 * 1024 ;
	private static final String ROOT_PATH = "/image";
	
	private static BufferedImage toImage(File f) {
		try {
			return ImageIO.read(new FileInputStream(f));
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 校验图片尺寸和大小
	 * @param f
	 * @param width
	 * @param height
	 * @param maxSize KB
	 * @return
	 */
	public static boolean isSizeValid(File f ,int width,int height,long maxSize) {
		BufferedImage image = toImage(f);
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		long imageSize = (long) (f.length()/1024.0);
		if (width > 0 && width != imageWidth) {
			return false;
		}
		
		if (height > 0 && height != imageHeight) {
			return false;
		}
		if ((maxSize > 0) && (imageSize > maxSize)) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 穿过来base64位编码格式的图片内容
	 * @param base64
	 * @param path
	 * @param imgName
	 */
	public static String decodeBase64ToImage(String base64, String desFile) {
		File file = new File(ROOT_PATH+desFile);
		if (!file.exists()) {
			file.mkdirs();
		}
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	    try {
	      FileOutputStream write = new FileOutputStream(file);
	      byte[] decoderBytes = decoder.decodeBuffer(base64);
	      write.write(decoderBytes);
	      write.close();
	      return ROOT_PATH+desFile;
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return null ;
	}	
	
	public static String uploadFile(String data,String desFile) {
	        try {  
	        	File file = new File(desFile);
	        	if (!file.exists()) {
	        		file.mkdirs();
	        	}
	        	//注意点：实际的图片数据是从 data:image/jpeg;base64, 后开始的  
	            byte[] k =  Base64.decodeBytes(data.substring("data:image/jpeg;base64,".length()));  
	            InputStream is = new ByteArrayInputStream(k);  
	            String fileName = UUID.randomUUID().toString();  
	            //String imgFilePath = serverPath + "\\static\\usertemp\\" + fileName + ".jpg";  
	            //以下其实可以忽略，将图片压缩处理了一下，可以小一点  
	            double ratio = 1.0;  
	            BufferedImage image = ImageIO.read(is);  
	            int newWidth = (int) (image.getWidth() * ratio);  
	            int newHeight = (int) (image.getHeight() * ratio);  
	            Image newimage = image.getScaledInstance(newWidth, newHeight,  
	            Image.SCALE_SMOOTH);  
	            BufferedImage tag = new BufferedImage(newWidth, newHeight,  
	                    BufferedImage.TYPE_INT_RGB);  
	            Graphics g = tag.getGraphics();  
	            g.drawImage(newimage, 0, 0, null);  
	            g.dispose();  
	            String suffix = file.getName().substring(file.getName().lastIndexOf(".")+1);
	            ImageIO.write(tag, suffix, new File(desFile));  
	            return fileName;  
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
	        return null;
	}
	
	public static void main(String[] args) throws Exception {
		File f = new File("D:\\2.jpg");
		long timestart = System.currentTimeMillis();
		img_change(f,"b",false,false);
		System.out.println(System.currentTimeMillis()-timestart);
		String whiteFile = img_change(f,"w",false,true);
		System.out.println(System.currentTimeMillis()-timestart);
		cutImage(new File(whiteFile), 220);
		System.out.println(System.currentTimeMillis()-timestart);
		//img_change("D:\\","2.jpg");
//		File f = new File("D:\\t1.jpg");
//		//img_change(f,50);
//		try {
//			cutImage(f, 100);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	
	public static String getScaleFilePath(String oriFilePath,int width) {
		if (oriFilePath.contains(".")) {
			return oriFilePath.substring(0,oriFilePath.lastIndexOf("."))+"_"+width+"."+oriFilePath.substring(oriFilePath.lastIndexOf(".")+1);
		}
		return oriFilePath;
	}
	
	private static void img_change(String url,String name) throws Exception {
		tosmallerpic(new File(url+name),"middle",1024, 1024, (float)0.3,false,false);
		//tosmallerpic(new File(url+name), "_small", 45, 45, (float)0.7);
		//tosmallerpic(new File(url+name), "_smaller", 116, 100, (float)0.7);
	}
	
	public static String img_change(File file,String suffix,boolean isAbsolute,boolean isBackWhite) throws Exception {
		return tosmallerpic(file,suffix, 1920, 1080, (float)0.3,isAbsolute,isBackWhite);
	}
	
	/**
	 * 按照宽，高来压缩图片
	 * 小图不压缩
	 * 
	 * @param file
	 * @param ext
	 * @param w
	 * @param h
	 * @param per
	 * @param isAbsolute 是否绝对,严格按照宽高来处理，如果不是绝对，则按照压缩比来处理，
	 * @param needsWhite 是否白色背景
	 * @param angel 旋转90
	 */
	private static String tosmallerpic(File file,String suffix,int w,int h,float per,boolean isAbsolute,boolean needsWhite) {
		Image src; 
		try {
			src  =  javax.imageio.ImageIO.read(file); //构造Image对象 
			int old_w = src.getWidth(null); //得到源图宽 
			int old_h = src.getHeight(null); //得到源图长 
			int new_w = old_w; 
			int new_h = old_h; 
			if (!isAbsolute) {
				//如果宽，高有一边比要求的大，则压缩
				if (old_w > w || old_h > h ) {
					double scale_w = (w*1.00)/(old_w*1.00); 
		            double scale_h = (h*1.00)/(old_h*1.00);
		            //按照小的来压缩
		            double scale = 1.0;
		            if (scale_w<scale_h) {
		            	scale = scale_w;
		            }else {
		            	scale = scale_h;
		            }
		            new_w = (int) (old_w*scale);
		            new_h = (int) (old_h*scale);
				}else {
					//如果宽高都小于要求，则不处理
				}
			}else {
				new_w = w;
				new_h = h;
			}
			
			//图片跟据长宽留白，成一个正方形图。 
			 if (needsWhite) {
				 src = srcBufferedImage(src,old_w,old_h,needsWhite,needsWhite);
			 }
			 BufferedImage image_to_save =  new BufferedImage(new_w,new_h,BufferedImage.TYPE_INT_RGB); 
			 //旋转角度,并且宽度要小于高度
			 int angel = getRotateAngleForPhoto(file);
			 if (angel>0) {
				 Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(old_h,old_w)), angel);
				 BufferedImage source = new BufferedImage(rect_des.width, rect_des.height,BufferedImage.TYPE_INT_RGB);  
				 Graphics2D g2 = source.createGraphics(); 
				 g2.translate((rect_des.width - old_w) / 2, (rect_des.height - old_h) / 2);  
				 g2.rotate(Math.toRadians(angel), old_w / 2, old_h / 2);  
				 g2.drawImage(src, null, null);  
				 image_to_save.getGraphics().drawImage(source.getScaledInstance(new_w, new_h,  Image.SCALE_SMOOTH), 0,0,null); 
			 }else {
	             image_to_save.getGraphics().drawImage(src.getScaledInstance(new_w, new_h,  Image.SCALE_SMOOTH), 0,0,null); 
			 }
	           
             String folder = file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf(File.separator));
             File f = new File(folder);
             if (!f.exists()) {
				f.mkdirs();
             }
             String img_midname  = file.getAbsolutePath();
             if (!StringUtils.isEmpty(suffix)) {
            	 img_midname  =  file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("."))+"_"+suffix+"."+file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")+1);
             }
             FileOutputStream fos = new FileOutputStream(img_midname); 
             saveAsJPEG(100, image_to_save, per, fos);
             fos.close(); 
             return img_midname;
		}catch(Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 图片翻转时，计算图片翻转到正常显示需旋转角度 
	 */
	private static int getRotateAngleForPhoto(File file){
		int angel = 0;
		Metadata metadata;
		try{
			metadata = JpegMetadataReader.readMetadata(file);
			Directory directory = metadata.getDirectory(ExifDirectory.class);
			if(directory.containsTag(ExifDirectory.TAG_ORIENTATION)){ 
				// Exif信息中方向　　
				int orientation = directory.getInt(ExifDirectory.TAG_ORIENTATION); 
				// 原图片的方向信息
				if(6 == orientation ){
					//6旋转90
					angel = 90;
				}else if( 3 == orientation){
				   //3旋转180
					angel = 180;
				}else if( 8 == orientation){
				   //8旋转90
					angel = 270;
				}
			}
		} catch(JpegProcessingException e){
			e.printStackTrace();
		} catch(MetadataException e){
			e.printStackTrace();
		}
		logger.info("图片旋转角度：" + angel);
		return angel;
	}
	/**
	* 计算旋转参数
	*/
	private static Rectangle CalcRotatedSize(Rectangle src,int angel){
		// if angel is greater than 90 degree,we need to do some conversion.
		if(angel > 90){
			if(angel / 9%2 ==1){
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}
		
		double r = Math.sqrt(src.height * src.height + src.width * src.width ) / 2 ;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;  
		double angel_dalta_width = Math.atan((double) src.height / src.width);  
		double angel_dalta_height = Math.atan((double) src.width / src.height);  

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha  
				- angel_dalta_width));  
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha  
				- angel_dalta_height));  
		int des_width = src.width + len_dalta_width * 2;  
		int des_height = src.height + len_dalta_height * 2;  
		return new java.awt.Rectangle(new Dimension(des_width, des_height));  
	}
	
	private static BufferedImage srcBufferedImage(Image src,int old_w,int old_h,boolean isSquare,boolean needsWhite) {
		//如果不是是正方形的，按照原来的尺寸来画
		if (!isSquare) {
			BufferedImage oldpic = new BufferedImage(old_w,old_h,BufferedImage.TYPE_INT_RGB);
			Graphics2D g  =  oldpic.createGraphics(); 
	        Color back = Color.black;
	        if (needsWhite) {
	       	 back = Color.white;
	        }
	        g.setColor(back); 
	        g.fillRect(0, 0, old_w, old_h); 
	        g.drawImage(src.getScaledInstance(old_w, old_h,  Image.SCALE_SMOOTH), 0,0,null); 
	        g.dispose();
	        return oldpic;
		}else {
			BufferedImage oldpic; 
	        if(old_w>old_h) 
	        { 
	            oldpic = new BufferedImage(old_w,old_w,BufferedImage.TYPE_INT_RGB); 
	        }else{if(old_w<old_h){ 
	            oldpic = new BufferedImage(old_h,old_h,BufferedImage.TYPE_INT_RGB); 
	        }else{ 
	             oldpic = new BufferedImage(old_w,old_h,BufferedImage.TYPE_INT_RGB); 
	        } 
	        } 
	         Graphics2D g  =  oldpic.createGraphics(); 
	         Color back = Color.black;
	         if (needsWhite) {
	        	 back = Color.white;
	         }
	         g.setColor(back); 
	         if(old_w>old_h) 
	         { 
	             g.fillRect(0, 0, old_w, old_w); 
	             g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, back, null); 
	         }else{ 
	             if(old_w<old_h){ 
	             g.fillRect(0,0,old_h,old_h); 
	             g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, back, null); 
	             }else{ 
	                 //g.fillRect(0,0,old_h,old_h); 
	                 g.drawImage(src.getScaledInstance(old_w, old_h,  Image.SCALE_SMOOTH), 0,0,null); 
	             } 
	         } 
	         g.dispose();
	         return oldpic;
         }
	}
	
	    /**
	     * 以JPEG编码保存图片
	     * @param dpi  分辨率
	     * @param image_to_save  要处理的图像图片
	     * @param JPEGcompression  压缩比
	     * @param fos 文件输出流
	     * @throws IOException
	     */
	    public static void saveAsJPEG(Integer dpi ,BufferedImage image_to_save, float JPEGcompression, FileOutputStream fos) throws IOException {
	          
	        //useful documentation at http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html
	        //useful example program at http://johnbokma.com/java/obtaining-image-metadata.html to output JPEG data
	      
	        //old jpeg class
	        //com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder  =  com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);
	        //com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam  =  jpegEncoder.getDefaultJPEGEncodeParam(image_to_save);
	      
	        // Image writer
	        JPEGImageWriter imageWriter  =  (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
	        ImageOutputStream ios  =  ImageIO.createImageOutputStream(fos);
	        imageWriter.setOutput(ios);
	        //and metadata
	        IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);
	         
	         
	        if(dpi !=  null && !dpi.equals("")){
	             
	             //old metadata
	            //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
	            //jpegEncodeParam.setXDensity(dpi);
	            //jpegEncodeParam.setYDensity(dpi);
	      
	            //new metadata
	            Element tree  =  (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
	            Element jfif  =  (Element)tree.getElementsByTagName("app0JFIF").item(0);
	            jfif.setAttribute("Xdensity", Integer.toString(dpi) );
	            jfif.setAttribute("Ydensity", Integer.toString(dpi));
	             
	        }
	      
	      
	        if(JPEGcompression >= 0 && JPEGcompression <= 1f){
	      
	            //old compression
	            //jpegEncodeParam.setQuality(JPEGcompression,false);
	      
	            // new Compression
	            JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
	            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
	            jpegParams.setCompressionQuality(JPEGcompression);
	      
	        }
	      
	        //old write and clean
	        //jpegEncoder.encode(image_to_save, jpegEncodeParam);
	      
	        //new Write and clean up
	        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
	        ios.close();
	        imageWriter.dispose();
	      
	    }
	    
	    
	    /**
	     * <p>Title: cutImage</p>
	     * <p>Description:  根据原图与裁切size截取局部图片</p>
	     * @param srcImg    源图片
	     * @param output    图片输出流
	     * @param rect        需要截取部分的坐标和大小
	     */
	    private static void cutImage(File srcImg, OutputStream output, java.awt.Rectangle rect){
	        if(srcImg.exists()){
	            java.io.FileInputStream fis = null;
	            ImageInputStream iis = null;
	            try {
	                fis = new FileInputStream(srcImg);
	                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
	                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
	                String suffix = null;
	                // 获取图片后缀
	                if(srcImg.getName().indexOf(".") > -1) {
	                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
	                }// 类型和图片后缀全部小写，然后判断后缀是否合法
	                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") < 0){
	                	logger.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
	                    return ;
	                }
	                // 将FileInputStream 转换为ImageInputStream
	                iis = ImageIO.createImageInputStream(fis);
	                // 根据图片类型获取该种类型的ImageReader
	                //ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
	                ImageReader reader = ImageIO.getImageReadersBySuffix("jpg").next();
	                reader.setInput(iis,true);
	                ImageReadParam param = reader.getDefaultReadParam();
	                param.setSourceRegion(rect);
	                BufferedImage bi = reader.read(0, param);
	                ImageIO.write(bi, suffix, output);
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    if(fis != null) fis.close();
	                    if(iis != null) iis.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }else {
	        	logger.warn("the src image is not exist.");
	        }
	    }
	    
	    private static void cutImage(File srcImg,int x, int y, int realwidth,int width) throws FileNotFoundException{
	    	String desFile = getScaleFilePath(srcImg.getAbsolutePath(),width);
	    	cutImage(srcImg, new java.io.FileOutputStream(desFile), new java.awt.Rectangle(x, y, realwidth, realwidth));
	    }
	    
	    public static void cutImage(File file,int width) throws IOException {
	    	Image src  =  javax.imageio.ImageIO.read(file);
	    	int old_w = src.getWidth(null); //得到源图宽
            int old_h = src.getHeight(null);
            int x = 0;
            int y = 0 ;
            int realwidth = width;
            //以原图的宽高短的那边做正方形
            if (old_w>old_h) {
            	x = (old_w-old_h)/2;
            	realwidth = old_h;
            }else {
            	realwidth = old_w;
            	y = (old_h-old_w)/2;
            }
            
            /**
            if (old_w > width ) {
            	x = (old_w-width)/2;
            }
	    	int y = 0;
	    	if (old_h > width ) {
	    		y = (old_h-width)/2;
            }*/
	    	cutImage(file,x,y,realwidth,width);
	    }
}
