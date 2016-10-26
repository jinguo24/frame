package com.simple.fileencrypt;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class FileViewerClient extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	GridBagLayout g=new GridBagLayout();
	GridBagConstraints c=new GridBagConstraints();
	JLabel perssionKeyFile,file,result,message;
	JFileChooser keyFile, readFile;
	JTextField keyFilePath,readFilePath;
	JButton keyFileButton, readFileButton;
	//JRadioButton sexMan,sexGirl;
	//JComboBox year,month;
	JButton submit;
	//JTextArea result;
	private List<String> temfilepath = new ArrayList<String>();

	FileViewerClient(String str)
	{
		super(str);
		setSize(1000,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(g);
		//调用方法
		addComponent();
		submit.addActionListener(this);
		setVisible(true);
		setLocationRelativeTo(null);//设居中显示;
		
		addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			//删除临时文件
			if ( null != temfilepath && temfilepath.size() > 0 ) {
				for (int i = 0 ; i < temfilepath.size(); i++) {
					if (null != temfilepath.get(i)) {
						File f = new File(temfilepath.get(i).trim());
						if (f.exists()) {
							f.deleteOnExit();
						}
					}
				}
			}
			System.exit(0);
		}});
	}
	//在这个方法中将会添加所有的组件;
	//使用的网格包布局;希望楼主能看懂;
	public void addComponent()
	{
		
		//perssionKeyFile = new JLabel("mac地址许可密钥：");
		//add(g,c,perssionKeyFile,0,0,1,1);
		
		keyFilePath = new JTextField(30);
		//add(g,c,keyFilePath,1,0,1,1);
		
		//keyFileButton = new JButton("选择密钥");
		//keyFileButton.addActionListener(new ActionListener() {
        //    @Override
        //    public void actionPerformed(ActionEvent e) {
        //    	chooseKeyFile();
        //    }
        //});
		//add(g,c,keyFileButton,2,0,1,1);
		
		file = new JLabel("选择文件：");
		add(g,c,file,0,1,1,1);
		
		readFilePath = new JTextField(30);
		add(g,c,readFilePath,1,1,1,1);
		
		readFileButton = new JButton("选择文件");
		readFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	chooseFile();
            }
        });
		add(g,c,readFileButton,2,1,1,1);
		
		result = new JLabel();
		add(g,c,result,0,2,1,1);
		
		message = new JLabel();
		add(g,c,message,1,2,1,1);
		
		submit=new JButton("确定");
		c.insets=new Insets(4,0,4,0);
		add(g,c,submit,1,3,1,1);
	}
	
	
	private void chooseKeyFile() {
		FileFilter filefilter = new FileFilter() {  
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
		keyFile = new JFileChooser();
		keyFile.addChoosableFileFilter(filefilter);  
		int open = keyFile.showOpenDialog(null);  
		if (open == JFileChooser.APPROVE_OPTION) {  
		    File f = keyFile.getSelectedFile();  
		    keyFilePath.setText(f.getAbsolutePath());
		} 
	}
	
	private void chooseFile() {
		FileFilter keyfilter = new FileFilter() {  
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
		readFile = new JFileChooser();
		readFile.addChoosableFileFilter(keyfilter);  
		int open = readFile.showOpenDialog(null);  
		if (open == JFileChooser.APPROVE_OPTION) {  
		    File f = readFile.getSelectedFile();  
		    readFilePath.setText(f.getAbsolutePath());
		} 
	}
	
	public void add(GridBagLayout g,GridBagConstraints c,JComponent jc,int x ,int y,int gw,int gh)
	{
		c.gridx=x;
		c.gridy=y;
		c.anchor=GridBagConstraints.WEST;
		c.gridwidth=gw;
		c.gridheight=gh;
		g.setConstraints(jc,c);
		add(jc);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		//if (null == keyFilePath.getText() || keyFilePath.getText().trim().isEmpty()) {
		//	result.setText("请选择密钥");
		//}else 
		if (null == readFilePath.getText() || readFilePath.getText().trim().isEmpty()) {
			message.setText("请选择文件");
		}else {
			//TODO 加载工具的配置，判断有效性
			try {
				boolean isvalid = KeyCheck.isValid(keyFilePath.getText());
				if (!isvalid) {
					result.setText("不允许使用");
				}else {
					//反解密文件,第一次打开把本机mac地址绑定文件
					FileEncryptUtil feu = new FileEncryptUtil(Constant.FILE_ENCRYPT_KEY);
					int ri = new Random().nextInt();
					File desfoler = new File("/temp/"+System.currentTimeMillis());
					if (!desfoler.exists()) {
						desfoler.mkdirs();
					}
					String filename = System.currentTimeMillis()+""+ri+"."+feu.getFileSubfix(readFilePath.getText());
					File desfile = new File(desfoler.getAbsolutePath()+"/"+filename);
					if (!desfile.exists()) {
						desfile.createNewFile();
					}
					temfilepath.add(desfile.getAbsolutePath());
					
					
					String mac = KeyCheck.getLocalMac();
					String key = ":MAC:"+mac;
					FileEncryptUtil fepu = new FileEncryptUtil(Constant.FILE_ENCRYPT_KEY);
					//首先判断文件内容是否包含mac信息，如果包含，则是绑定了mac地址，如果没有，则认为是第一次访问
					String str = fepu.getStr(readFilePath.getText(), key.length());
					if (str.startsWith(":MAC:")&&(!str.equals(key))) {
						message.setText("该文件已经被"+key+"绑定,不允许打开");
						return;
					//前面不是:MAC:打头的，认为是第一次访问
					}else if (!str.startsWith(":MAC:")) {
						//解密文件
						fepu.decrypt(readFilePath.getText(), desfoler.getAbsolutePath()+"/"+filename);
						//把文件绑定mac地址
						String macfilename = System.currentTimeMillis()+""+ri+"_mac"+"."+feu.getFileSubfix(readFilePath.getText());
						File macdesfile = new File(desfoler.getAbsolutePath()+"/"+macfilename);
						if (!macdesfile.exists()) {
							macdesfile.createNewFile();
						}
						temfilepath.add(macdesfile.getAbsolutePath());
						fepu.addStr(readFilePath.getText(), macdesfile.getAbsolutePath(), key);
						//将临时文件内容拷贝到本地文件
						fepu.addStr(macdesfile.getAbsolutePath(), readFilePath.getText(), "");
						
						
					//不是第一次，mac校验通过的	
					}else {
						//先把mac地址信息去掉，然后解密
						String tempmacfilename = System.currentTimeMillis()+""+ri+"_mac_remove"+"."+feu.getFileSubfix(readFilePath.getText());
						File tempmacdesfile = new File(desfoler.getAbsolutePath()+"/"+tempmacfilename);
						if (!tempmacdesfile.exists()) {
							tempmacdesfile.createNewFile();
						}
						temfilepath.add(tempmacdesfile.getAbsolutePath());
						fepu.removeStr(readFilePath.getText(), tempmacdesfile.getAbsolutePath(), key);
						fepu.decrypt(tempmacdesfile.getAbsolutePath(), desfoler.getAbsolutePath()+"/"+filename);
					}
					
					Runtime runtime = Runtime.getRuntime();  
					//打开文件  
			        runtime.exec("rundll32 url.dll FileProtocolHandler "+desfoler.getAbsolutePath()+"/"+filename);
				}
			} catch (Exception e) {
				message.setText(e.getLocalizedMessage());
			}
		}
	}
	
	public static void main(String args[])
	{
		new FileViewerClient("解密阅读器");
	}
	
	
}
