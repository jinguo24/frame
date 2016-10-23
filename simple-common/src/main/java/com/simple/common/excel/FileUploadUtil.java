package com.simple.common.excel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.util.PrimaryKeyUtil;

public class FileUploadUtil {

	private static final String UPLOAD_TEMP_DIR = EnvPropertiesConfiger.getValue("uploadDir")+"/upload/temp/";
	public static final String UPLOAD_IMAGE_DIR = EnvPropertiesConfiger.getValue("uploadDir")+"/upload/images/";
	/**
	 * 流传文件
	 * @param request
	 * @return
	 * @throws FileUploadException
	 */
	public static List<FileItem> getFiles(HttpServletRequest request) throws FileUploadException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
        //超过10M往磁盘写
		factory.setSizeThreshold(1024*10);
		String path = request.getSession().getServletContext().getRealPath(UPLOAD_TEMP_DIR);
        factory.setRepository(new File(path));
        ServletFileUpload upload = new ServletFileUpload(factory);
        //上传的最大限制
        upload.setSizeMax(1024*10);
		List<FileItem> fileItems = upload.parseRequest(request);
		return fileItems;
	}
	
	private static void saveFile(InputStream ins, File file) throws IOException {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (null != os) {
				os.close();
			}
			if (null != ins) {
				ins.close();
			}
		}
	}
	
	public static File getFileByInputStream(InputStream is,String subfix,String folder) {
			try {
				String filefloder = folder+System.currentTimeMillis();
				File f = new File(filefloder);
				if (!f.exists()) {
					f.mkdirs();
				}
				String filepath = filefloder+"/"+PrimaryKeyUtil.getUUID()+"."+subfix;
				File file = new File(filepath);
				if (!file.exists()) {
					file.createNewFile();
				}
				saveFile(is,file);
				return file;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public static File getFileByHex(String imageData,String subfix) {
		try {
			InputStream is = new ByteArrayInputStream(imageData.getBytes("ISO-8859-1"));
			return getFileByInputStream(is,subfix,UPLOAD_TEMP_DIR);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static File getFileByHex(String imageData,String subfix,String folder) {
		try {
			InputStream is = new ByteArrayInputStream(imageData.getBytes("ISO-8859-1"));
			return getFileByInputStream(is,subfix,folder);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
