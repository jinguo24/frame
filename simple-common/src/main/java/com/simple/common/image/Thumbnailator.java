package com.simple.common.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

public class Thumbnailator {

	public static void main(String[] args) {
		try {
			File f = new File("D:\\1.JPG");
			cutSquareImage(f,220,220,0.1f,0,"D:\\1_1_1_220.jpg");
			//scale("D:\\2_1_1.jpg",220,220,0.1f,0,"D:\\2_1_1_220.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void scale(InputStream stream,int width,int height,double rate,double rotate,String desFile) throws IOException {
		Builder<InputStream> bf = null;
		if (rate > 0 ) {
			bf = (Builder<InputStream>) Thumbnails.of(stream).size(width, height).outputQuality(rate).outputFormat("jpg");
		}else {
			bf = (Builder<InputStream>) Thumbnails.of(stream).size(width, height);
		}
		if (rotate != 0 ) {
			bf.rotate(rotate).toFile(desFile);;
		}else {
			bf.toFile(desFile);
		}
	}
	
	/**
	 * 指定大小进行缩放
	 * rate 压缩比例
	 * rotate 旋转角度
	 * @throws IOException
	 */
	public static void scale(String file,int width,int height,double rate,double rotate, String desFile) throws IOException {
		/*
		 * size(width,height) 若图片横比200小，高比300小，不变
		 * 若图片横比200小，高比300大，高缩小到300，图片比例不变 若图片横比200大，高比300小，横缩小到200，图片比例不变
		 * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
		 */
		Builder<File> bf = null;
		if (rate > 0 ) {
			bf = Thumbnails.of(file).size(width, height).outputQuality(rate).outputFormat("jpg");
		}else {
			bf = Thumbnails.of(file).size(width, height);
		}
		
		if (rotate != 0 ) {
			bf.rotate(rotate).toFile(desFile);;
		}else {
			bf.toFile(desFile);
		}
		
	}
	
	public static void scaleAbsulate(InputStream stream,int width,int height,double rate,double rotate,String desFile) throws IOException {
		Builder<InputStream> bf = null;
		if (rate > 0 ) {
			bf = (Builder<InputStream>) Thumbnails.of(stream).size(width, height).keepAspectRatio(false).outputQuality(rate).outputFormat("jpg");
		}else {
			bf = (Builder<InputStream>) Thumbnails.of(stream).size(width, height).keepAspectRatio(false);
		}
		if (rotate != 0 ) {
			bf.rotate(rotate).toFile(desFile);;
		}else {
			bf.toFile(desFile);
		}
	}
	/**
	 * 不按照比例，指定大小进行缩放
	 * 
	 * @throws IOException
	 */
	public static void scaleAbsulate(String file,int width,int height,double rate,double rotate, String desFile) throws IOException {
		/**
		 * keepAspectRatio(false) 默认是按照比例缩放的
		 */
		Builder<File> bf = null;
		if (rate > 0 ) {
			bf = Thumbnails.of(file).size(width, height).keepAspectRatio(false).outputQuality(rate).outputFormat("jpg");
		}else {
			bf = Thumbnails.of(file).size(width, height).keepAspectRatio(false);
		}
		if (rotate != 0 ) {
			bf.rotate(rotate).toFile(desFile);;
		}else {
			bf.toFile(desFile);
		}
	}
	
//	/**
//	 * 旋转
//	 * 
//	 * @throws IOException
//	 */
//	private void rotate() throws IOException {
//		/**
//		 * rotate(角度),正数：顺时针 负数：逆时针
//		 */
//		Thumbnails.of("images/test.jpg").size(1280, 1024).rotate(90).toFile(
//				"C:/image+90.jpg");
//		Thumbnails.of("images/test.jpg").size(1280, 1024).rotate(-90).toFile(
//				"C:/iamge-90.jpg");
//	}
	
	/**
	 * 水印
	 * 
	 * @throws IOException
	 */
	private void test5() throws IOException {
		/**
		 * watermark(位置，水印图，透明度)
		 */
		Thumbnails.of("images/test.jpg").size(1280, 1024).watermark(
				Positions.BOTTOM_RIGHT,
				ImageIO.read(new File("images/watermark.png")), 0.5f)
				.outputQuality(0.8f).toFile(
						"C:/image_watermark_bottom_right.jpg");
		Thumbnails.of("images/test.jpg").size(1280, 1024).watermark(
				Positions.CENTER,
				ImageIO.read(new File("images/watermark.png")), 0.5f)
				.outputQuality(0.8f).toFile("C:/image_watermark_center.jpg");
	}

	
	public static void cutSquareImage(File file,int width,int height,double rate,double rotate, String desFile) throws IOException {
		BufferedImage image = ImageIO.read(file);  
		Builder<BufferedImage> builder = null;  
		  
		int imageWidth = image.getWidth();  
		int imageHeitht = image.getHeight();  
		
		
		//压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉(这个是引的网友的代码)  		
		if ((float)width / height != (float)imageWidth / imageHeitht) {  
		    if (imageWidth > imageHeitht) {  
		        image = Thumbnails.of(file).height(height).asBufferedImage();  
		    } else {  
		        image = Thumbnails.of(file).width(width).asBufferedImage();  
		    }  
		    builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, width, height).size(width, height);  
		} else {  
		    builder = Thumbnails.of(image).size(width, height);  
		}  
		if (rate > 0 ) {
			builder = builder.outputQuality(rate);
		}
		if (rotate != 0 ) {
			builder = builder.rotate(rotate);
		}
		builder.outputFormat("jpg").toFile(desFile); 
	}
	
	/**
	 * 裁剪
	 * 
	 * @throws IOException
	 */
	public static void cutImage(String file,int width,int height,String desFile) throws IOException {
		/**
		 * 图片中心400*400的区域
		 */
		Thumbnails.of(file).sourceRegion(Positions.CENTER, width,height).size(width, height).keepAspectRatio(false).toFile(desFile);
//		/**
//		 * 图片右下400*400的区域
//		 */
//		Thumbnails.of("images/test.jpg").sourceRegion(Positions.BOTTOM_RIGHT,
//				400, 400).size(200, 200).keepAspectRatio(false).toFile(
//				"C:/image_region_bootom_right.jpg");
//		/**
//		 * 指定坐标
//		 */
//		Thumbnails.of("images/test.jpg").sourceRegion(600, 500, 400, 400).size(
//				200, 200).keepAspectRatio(false).toFile(
//				"C:/image_region_coord.jpg");
	}
	
	/**
	 * 转化图像格式
	 * 
	 * @throws IOException
	 */
	private void test7() throws IOException {
		/**
		 * outputFormat(图像格式)
		 */
		Thumbnails.of("images/test.jpg").size(1280, 1024).outputFormat("png")
				.toFile("C:/image_1280x1024.png");
		Thumbnails.of("images/test.jpg").size(1280, 1024).outputFormat("gif")
				.toFile("C:/image_1280x1024.gif");
	}

	
	/**
	 * 输出到OutputStream
	 * 
	 * @throws IOException
	 */
	private void test8() throws IOException {
		/**
		 * toOutputStream(流对象)
		 */
		OutputStream os = new FileOutputStream(
				"C:/image_1280x1024_OutputStream.png");
		Thumbnails.of("images/test.jpg").size(1280, 1024).toOutputStream(os);
	}
	
	/**
	 * 输出到BufferedImage
	 * 
	 * @throws IOException
	 */
	private void test9() throws IOException {
		/**
		 * asBufferedImage() 返回BufferedImage
		 */
		BufferedImage thumbnail = Thumbnails.of("images/test.jpg").size(1280,
				1024).asBufferedImage();
		ImageIO.write(thumbnail, "jpg", new File(
				"C:/image_1280x1024_BufferedImage.jpg"));
	}
	
}
