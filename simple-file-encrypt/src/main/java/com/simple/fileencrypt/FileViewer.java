package com.simple.fileencrypt;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileViewer {
	
	public void view() {
		JFileChooser chooseFile = new JFileChooser();  
		FileFilter filter = new FileFilter() {  
		  
		    //要过滤的文件  
		    public boolean accept(File f) {  
		        //显示的文件类型  
		        if (f.isDirectory()) {  
		            return true;  
		        }  
		        //显示满足条件的文件     
		        return f.getName().endsWith(".txt") || f.getName().endsWith(".java");  
		    }  
		  
		    /**   
		     * 这就是显示在打开框中   
		     */  
		    public String getDescription() {  
		  
		        return "*.txt,*.java";  
		    }  
		};  
		  
		FileFilter filter1 = new FileFilter() {  
		  
		    public boolean accept(File f) {  
		        if (f.isFile()) {  
		            return true;  
		        }  
		        //显示满足条件的文件     
		        return f.getName().endsWith(".xls");  
		    }  
		  
		    /**   
		     * 这就是显示在打开框中   
		     */  
		    public String getDescription() {  
		        return "*.xls";  
		    }  
		};  
		  
		chooseFile.addChoosableFileFilter(filter);  
		chooseFile.addChoosableFileFilter(filter1);  
		int open = chooseFile.showOpenDialog(null);  
		if (open == JFileChooser.APPROVE_OPTION) {  
		    File f = chooseFile.getSelectedFile();  
		    Runtime runtime = Runtime.getRuntime();  
		    try{  
		        //打开文件  
		        runtime.exec("rundll32 url.dll FileProtocolHandler "+f.getAbsolutePath());  
		    }catch(Exception ex){  
		        ex.printStackTrace();  
		    }  
		}  
	}
	
	public static void main(String[] args) {
		FileViewer f = new FileViewer();
		f.view();
	}
}
