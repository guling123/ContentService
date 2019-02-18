package cn.people.service.view.obj;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTool {
	private static final Logger log = LoggerFactory.getLogger(FileTool.class);

	public static boolean isSymbolicLink(String link_name) {
		if (link_name == null) {
			return false;
		} else {
			return isSymbolicLink(new File(link_name));
		}
	}

	public static boolean isSymbolicLink(File link_file) {
		try {
			if (link_file != null && link_file.exists()) {
				if (!link_file.getAbsolutePath().equals(link_file.getCanonicalPath())) {
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public static String ReadFile(String filename, String charset) {
		StringBuilder strBuffer = new StringBuilder();
		try {
			File f = new File(filename);
			if (f.exists() && f.isFile()) {
				Reader reader = new InputStreamReader(new FileInputStream(f), charset);
				char[] charArray = new char[1024];
				int length = 0;
				while ((length = reader.read(charArray, 0, 1024)) != -1) {
					strBuffer.append(charArray, 0, length);
				}
				reader.close();
			}

		} catch (Exception ex) {
			log.error("Read File Error " + filename + ex.getMessage());
			// throw new Exception(ex.getMessage());
		}
		return strBuffer.toString();
	}

	public static void writeFile(String filepath, String content, String encode) throws IOException {
		File file = new File(filepath);
		OutputStreamWriter os = null;
		try {
			if (!file.exists()) {
				File parentDir = file.getParentFile();
				if (!parentDir.exists()) {
					makeDirs(parentDir);
					// parentDir.mkdirs();
					// parentDir.setWritable(true, false);
				}
			}
			os = new OutputStreamWriter(new FileOutputStream(file), encode);
			os.write(content);
			os.close();
			file.setWritable(true, false);
			file.setReadable(true,false);
		} catch (IOException e) {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e2) {
				// TODO: handle exception
			}
			throw e;
		}
	}

	public static void copyFile(String sourcePath, String targetPath) throws IOException {
		File targetFile = new File(targetPath);
		if (!targetFile.exists()) {
			File parentDir = targetFile.getParentFile();
			if (!parentDir.exists()) {
				makeDirs(parentDir);
				// parentDir.mkdirs();
				// parentDir.setWritable(true, false);
			}
		}
		// 新建文件输入流并对它进行缓冲
		BufferedInputStream inBuff = new BufferedInputStream(new FileInputStream(sourcePath));

		// 新建文件输出流并对它进行缓冲
		BufferedOutputStream outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();
		// 关闭流
		inBuff.close();
		outBuff.close();
		targetFile.setReadable(true,false);
	}

	/**
	 * 复制源目录下的多个文件到目标目录下
	 * 
	 * @param sourceDir
	 *            源目录
	 * @param targetDir
	 *            目标目录
	 * @param fileRegx
	 *            需要复制的文件名正则表达式
	 * @throws IOException
	 */
	public static void copyAllFile(File sourceDir, File targetDir, final String fileRegx) throws IOException {
		if (sourceDir != null && sourceDir.exists()) {
			if (!targetDir.exists()) {
				makeDirs(targetDir);
			}
			File allSubFile[] = null;
			if (fileRegx != null && fileRegx.length() > 0) {
				allSubFile = sourceDir.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						if (name.matches(fileRegx)) {
							return true;
						}
						return false;
					}
				});
			} else {
				allSubFile = sourceDir.listFiles();
			}
			if (allSubFile != null && allSubFile.length > 0) {
				for (File file : allSubFile) {
					if (file.isFile()) {
						// 新建文件输入流并对它进行缓冲
						BufferedInputStream inBuff = new BufferedInputStream(new FileInputStream(file));

						// 新建文件输出流并对它进行缓冲
						File outFile=new File(targetDir.getAbsolutePath()+"/"+file.getName());
						BufferedOutputStream outBuff = new BufferedOutputStream(new FileOutputStream(outFile));

						// 缓冲数组
						byte[] b = new byte[1024 * 5];
						int len;
						while ((len = inBuff.read(b)) != -1) {
							outBuff.write(b, 0, len);
						}
						// 刷新此缓冲的输出流
						outBuff.flush();
						// 关闭流
						inBuff.close();
						outBuff.close();
						outFile.setReadable(true,false);
					}
				}
			}
		}
	}

	// 创建父目录，并设置写权限
	public static void makeDirs(File path) {
		if (path != null && path.exists() == false) {
			ArrayList<File> array = new ArrayList<File>();
			array.add(0, path);
			File parent = path.getParentFile();
			while (parent != null && parent.exists() == false) {
				array.add(0, parent);
				parent = parent.getParentFile();
			}
			if (array.size() > 0) {
				for (File file : array) {
					file.mkdir();
					file.setWritable(true, false);
					file.setReadable(true,false);
					file.setExecutable(true,false);
				}
			}
		}
	}
	
	public static void write(String filePath, String content) {   
        FileWriter writer = null;  
        File file = new File(filePath);
        try {                
        	if (!file.exists()) {
				File parentDir = file.getParentFile();
				if (!parentDir.exists()) {
					makeDirs(parentDir);
				}
			}
        	// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(filePath, true);     
            writer.write(content); 
            file.setReadable(true,false);
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    }  
	
	public static void write(String filePath, String content,boolean coverFlag) {   
        FileWriter writer = null;  
        File file = new File(filePath);
        try {                
        	if (!file.exists()) {
				File parentDir = file.getParentFile();
				if (!parentDir.exists()) {
					makeDirs(parentDir);
				}
			}
        	// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(filePath, coverFlag);     
            writer.write(content);
            file.setReadable(true,false);
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    } 
	
	public static void write(String filePath, Document doc) {   
		XMLWriter writer = null;  
        File file = new File(filePath);
        try {                
        	if (!file.exists()) {
				File parentDir = file.getParentFile();
				if (!parentDir.exists()) {
					makeDirs(parentDir);
				}
			}          
            OutputFormat format = OutputFormat.createPrettyPrint();
		    format.setEncoding("UTF-8");  // 指定XML编码    	      
		    writer = new XMLWriter(new FileOutputStream(filePath),format);		            
		    writer.write(doc);
		    file.setReadable(true,false);
		    
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    } 
	
	// 读取对方的xml内容
		public static InputStream getXmlFile(String xmlUrl) {
			InputStream inputStream = null;
			try {
				URL url = new URL(xmlUrl);
				inputStream = url.openStream();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return inputStream;
		}
		public static String loadJson (String url) {  
	        StringBuilder json = new StringBuilder();  
	        try {  
	            URL urlObject = new URL(url);  
	            URLConnection conn = urlObject.openConnection();  
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
	            String inputLine = null;  
	            while ( (inputLine = in.readLine()) != null) {  
	                json.append(inputLine);  
	            }  
	            in.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	        return json.toString();  
	    }
	public static void main(String[] args) {
		try {
			FileTool.copyAllFile(new File("e:/test/del"),new File("e:/test/del1"),"index[0-9]*\\.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
