package cn.people.service.view.obj;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageTool {
	

	private float quality;//图片质量

	private int MAX_SIZE;//图片最大值
	private int x;//水印图片在图片x轴方向位置
	private int y;//水印图片在图片y轴方向位置
	
	public ImageTool(){
		//默认图片压缩质量是0.8；
		quality=0.8f;
		x=10;
		y=10;
	}
	
	public int GetImageSize(String path){
		/*
		 * 功能：返回path指向图片大小
		 * path图片路径
		 * */
		
		if(!isImagepath(path)){
			return 0;
		}
		long size=0;
	
    	File file=new File(path);
    	size=file.length();
    	
    	return (int)size/1024;
	
		
	}
	/**
	 * 左上角添加水印
	 * @param ImagePath
	 * @param WaterImagePath
	 * 1
	 */
	public Boolean AddWaterImageLeftUp(String ImagePath,String WaterImagePath){
		/*
		 * 功能：在ImagePath图片左上角添加水印图片WatherImagePath
		 * */
		Boolean b = false;
		if( !isImagepath(WaterImagePath) || !isImagepath(ImagePath)){
			return b;
		}
		try{			
			int IWidth=GetImageWidth(ImagePath);
			int IHeight=GetImageHeight(ImagePath);
			int  WWidth=GetImageWidth(WaterImagePath);
			int WHeight=GetImageHeight(WaterImagePath);
			
			if(IWidth <WWidth+x||IHeight<WHeight+y){
				return b;
			}
			AddWaterImage(ImagePath,WaterImagePath,x,y);
			b = true;

		} catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
		return b;
        
	}
	/**
	 * 左下角添加水印
	 * @param ImagePath
	 * @param WaterImagePath
	 * 2
	 */
	public Boolean AddWaterImageLeftDown(String ImagePath,String WaterImagePath){
		/*
		 * 功能：在ImagePath图片左下角添加水印图片WatherImagePath
		 * */
		Boolean b = false;
		if(!isImagepath(ImagePath)||!isImagepath(WaterImagePath)){
			return b;
		}
		try{
			
			int IWidth=GetImageWidth(ImagePath);
			int IHeight=GetImageHeight(ImagePath);
			int  WWidth=GetImageWidth(WaterImagePath);
			int WHeight=GetImageHeight(WaterImagePath);
			
			if(IWidth <WWidth+20||IHeight<WHeight+20){
				return b;
			}
			AddWaterImage(ImagePath,WaterImagePath,x,IHeight-WHeight-y);
			b = true;			

		} catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
		return b;
        
	}
	/**
	 * 右上角添加水印
	 * @param ImagePath
	 * @param WaterImagePath
	 * 3
	 */
	public Boolean AddWaterImageRightUp(String ImagePath,String WaterImagePath){
		/*
		 * 功能：在ImagePath图片右上角添加水印图片WatherImagePath
		 * */
		Boolean b = false;
		if(!isImagepath(ImagePath)||!isImagepath(WaterImagePath)){
			return b;
		}
		try{
			
			int IWidth=GetImageWidth(ImagePath);
			int IHeight=GetImageHeight(ImagePath);
			int  WWidth=GetImageWidth(WaterImagePath);
			int WHeight=GetImageHeight(WaterImagePath);
			
			if(IWidth <WWidth+20||IHeight<WHeight+20){
				return b;
			}
			AddWaterImage(ImagePath,WaterImagePath,IWidth-WWidth-x,y);
			b = true;	

		} catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
		return b;
        
	}
	/**
	 * 右下角添加水印
	 * @param ImagePath
	 * @param WaterImagePath
	 * 4
	 */
	public Boolean AddWaterImageRightDown(String ImagePath,String WaterImagePath){
		/*
		 * 功能：在ImagePath图片右上角添加水印图片WatherImagePath
		 * */
		Boolean b = false;
		if(!isImagepath(ImagePath) || !isImagepath(WaterImagePath)){
			return b;
		}
		try{
			
			int IWidth=GetImageWidth(ImagePath);
			int IHeight=GetImageHeight(ImagePath);
			int  WWidth=GetImageWidth(WaterImagePath);
			int WHeight=GetImageHeight(WaterImagePath);
			
			if(IWidth <WWidth+x||IHeight<WHeight+y){
				return b;
			}
			AddWaterImage(ImagePath,WaterImagePath,IWidth-WWidth-x,IHeight-WHeight-y);
			b = true;

		} catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
		return b;
        
	}
	
	
	/*
	 * 手动添加一张图片水印
	 * 
	 * 功能：在ImagePath图片x,y位置添加水印图片WatherImagePath
	 * ImagePath:原图
	 * WaterImagePath：水印图片
	 * x:水印图片在原图上横坐标
	 * y:水印图片在原图上纵坐标
	 * */
	public Boolean AddWaterImage(String ImagePath, String WaterImagePath, int x, int y){
		
		Boolean b  ;
		if(!isImagepath(ImagePath) && !isImagepath(WaterImagePath)){
			b = false;
		}
		if(x<1||y<1){
			b = false;
		}
		
		 try {
	            //目标文件
	            File _file = new File(ImagePath);
	 
	            Image src = ImageIO.read(_file);
	            int wideth = src.getWidth(null);
	            int height = src.getHeight(null);
	            BufferedImage image = new BufferedImage(wideth, height,
	                    BufferedImage.TYPE_INT_RGB);
	            Graphics g = image.createGraphics();
	            
	            g.drawImage(src, 0, 0, wideth, height, null);

	            //水印文件
	            File _filebiao = new File(WaterImagePath);
	            Image src_biao = ImageIO.read(_filebiao);
	            int wideth_biao = src_biao.getWidth(null);
	            int height_biao = src_biao.getHeight(null);
	            g.drawImage(src_biao, x, y, wideth_biao, height_biao, null);
	            //水印文件结束
	            g.dispose();
	            FileOutputStream out = new FileOutputStream(ImagePath);
	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	            encoder.encode(image);
	            out.close();
	            b = true;
	        } catch (Exception e) {
	        	b = false;
	            e.printStackTrace();
	        }
		return b;
	}
	
	public void ZipImageFile(String path,float Iquality ){
		/*
		 * 功能：按照压缩质量参数quality压缩path指向图片
		 * path:压缩图片路径
		 * Iquality:图片压缩质量
		 * */
		if(!isImagepath(path)){
			return;
		}
		
		while(GetImageSize(path)>MAX_SIZE){
			zip(path,Iquality);
		}
		
	}
	
	private void zip(String path,float Iquality){
		/*
		 * 功能：按照压缩质量参数quality压缩path指向图片
		 * path:压缩图片路径
		 * Iquality:图片压缩质量
		 * */
		String newImage = null;   
        try {   
            /** 对服务器上的临时文件进行处理 */  
            Image srcFile = ImageIO.read(new File(path));
            int w = srcFile.getWidth(null);
            int h = srcFile.getHeight(null);
 
            int width =(int)(w*0.9);
            int height = (int)(h*0.9);
            
            ModeImageByWH(path,width,height,Iquality);
  
        } catch (FileNotFoundException e) {   
            e.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
	}
	
	public void ModeImageByWH(String path,int width,int height,float Iquality){
		/*
		 * 功能：设置path指向图片宽是width，高是height,生成图片质量Iquality
		 * width:新生成图片宽、
		 * height:新身材图片高
		 * Iquality:图片生成质量
		 * */
		if(!isImagepath(path)||width<1||height<1){
			return;
		}
		String newImage = null;   
        try {   
            /** 对服务器上的临时文件进行处理 */  
            Image srcFile = ImageIO.read(new File(path));
            
            /** 宽,高设定 */  
            BufferedImage tag = new BufferedImage(width, height,   
                    BufferedImage.TYPE_INT_RGB);   
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);   
            String filePrex = path.substring(5, path.indexOf('.'));   
            /** 压缩后的文件名 */  
                         
            FileOutputStream out = new FileOutputStream(path);
  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);   
            /** 压缩质量 */  
            jep.setQuality(Iquality, true);   
            encoder.encode(tag, jep);   
            out.close();   
  
        } catch (FileNotFoundException e) {   
            e.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }  
		
	}
	public int GetImageWidth(String path){
		/*
		 * 功能：获得图片宽
		 * path:图片路径
		 * */
		
		if(!isImagepath(path)){
			return 0;
		}
	  try{
		 int Width=0;
         Image srcFile = ImageIO.read(new File(path));
         Width = srcFile.getWidth(null);
         return Width;
	  } catch (IOException e) {   
          e.printStackTrace();  
          return 0;
      } 
		
		
	}
	
	public int GetImageHeight(String path){
		/*
		 *功能:获得图片
		 *path:图片路径
		 * */
		if(!isImagepath(path)){
			return 0;
		}
		int Height=0;
		try{
			 
	         Image srcFile = ImageIO.read(new File(path));
	         Height = srcFile.getHeight(null);
	         
	         
		  } catch (IOException e) {   
	          e.printStackTrace();  
	          return 0;
	      } 
		return Height;
		
	}
	
	public String cutImage(String path,int x,int y,int width,int height){
		/*
		 * 功能：剪切图片,返回剪切图片地址
		 * path:要剪切图片地址
		 * x:剪切点在原图上横坐标位置
		 * y:剪切点在原图上纵坐标位置
		 * width:在横坐标x剪切点开始剪切长度
		 * heigth：在纵坐标y开始位置剪切长度
		 * */
		
		if(!isImagepath(path)){
			return null;
		}
		
		if(x<1||y<1||width<1||height<1){
			return null;
		}
		
		 FileInputStream is = null ;
         ImageInputStream iis =null ;
      
         try{   
        	 Image srcFile = ImageIO.read(new File(path));
             int w = srcFile.getWidth(null);
             int h = srcFile.getHeight(null);
             if(w<x+width||h<y+height){
            	 return null;
             }
        	 String fileType=path.substring(path.indexOf(".")+1,path.length()-path.indexOf("0")-1);
             //读取图片文件
             is = new FileInputStream(path); 
             
            
             Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(fileType);  
             ImageReader reader = it.next(); 
           
             iis = ImageIO.createImageInputStream(is);
             reader.setInput(iis,true) ;
        
             ImageReadParam param = reader.getDefaultReadParam();                        
             Rectangle rect = new Rectangle(x, y, width, height); 
                                     
             param.setSourceRegion(rect);            
             BufferedImage bi = reader.read(0,param);                
       
            
             String cutPath=path.replaceFirst("."+fileType, "_c."+fileType);
             File file=new File(cutPath);
             ImageIO.write(bi, fileType, file);     
             file.setReadable(true,false);
       
             if(is!=null)
                is.close() ;       
             if(iis!=null)
                iis.close(); 
             
             return cutPath;
         }
         catch(Exception e){
        	 
        	 e.printStackTrace(); 
        	 return null;
        	 
         }
          
		
	}
	
	private boolean isImagepath(String path){
		/*
		 * 功能：判断imagePath是否有效值，有效返回true,无效返回false
		 * 
		 * */
		
		if(path.isEmpty()){
			return false;
		}
		
		File file=new File(path);
		if(!file.exists()){
			return false;
		}
		
		return true;
	
		
	}
	private float getQuality() {
		return quality;
	}
	public void setQuality(float quality) {
		this.quality = quality;
	}

	public int getMAX_SIZE() {
		return MAX_SIZE/1024;
	}

	public void setMAX_SIZE(int mAXSIZE) {
		MAX_SIZE = mAXSIZE*1024;
	}

	public void SetWHByPercent(String path,float percent){
		/*
		 * 功能：按照percent对应百分比重新设置path指向路径图片的长宽
		 * path:图片路径
		 * percent:百分数，新图片长宽对应原图片的百分数，
		 * */
		if(!isImagepath(path)||percent<0){
			return ;
		}
		
		int width=GetImageWidth(path);
		int height=GetImageHeight(path);
		ModeImageByWH(path,(int)(width*percent),(int)(height*percent),quality);
		
		
	}
	
	public void setXY(int x,int y){
		/*
		 * 设置水印图片距离左上角位置
		 * x:水印图片横坐标位置
		 * y:水印图片纵坐标位置
		 * */
		this.x=x*2;
		this.y=y;
	}
	
	public String getimgWith_Wireless(int with,String path){
		/*
		 * with 指定宽
		 * path 图片路径
		 * return 按指定宽同比压缩
		 * */
		String imgWith_WX = "";
		ImageInputStream iis =null;
		ImageReader reader =null;
		try{
			if(!isImagepath(path)){
 				imgWith_WX = "321/100";
 				return imgWith_WX;
 			}
			String imageType = path.substring(path.lastIndexOf(".")+1);
			Iterator readers = ImageIO.getImageReadersByFormatName(imageType);
		    reader = (ImageReader)readers.next();
		    iis = ImageIO.createImageInputStream(new File(path));
		    reader.setInput(iis, true);
		    double picHeight = reader.getHeight(0);   
	        double picWith = reader.getWidth(0);  
//          reader.dispose();
//	        iis.close();
	        double ratio = picWith/with;
	 		if(ratio==0){	 			
	 			ratio = 1;
	 			with = (int)picWith;
	 		}
	 		int height = (int)(picHeight/ratio);
	 		imgWith_WX = String.valueOf(with)+"/"+height;
		  } catch (IOException e) {              
	          e.printStackTrace();  
	      } finally{
	          if (reader != null) {
	              reader.dispose();
              }
	          if (iis != null) {
	                try {
	                    iis.close();
	                } catch (IOException e2) {
	                }
	            }
	      }
		return imgWith_WX;
	}
	
	/**
     * 等比例压缩图片文件<br> 先保存原文件，再压缩、上传
     * @param oldFile  要进行压缩的文件
     * @param newFile  新文件
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放）
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放）
     * @param quality 质量
     * @return 返回压缩后的文件的全路径
     */ 
    public static void zipImageFile(String oldFilePath,String newFilePath, int width, int height, 
            float quality) {
    	File oldFile = new File(oldFilePath);
        try { 
            /** 对服务器上的临时文件进行处理 */ 
            Image srcFile = ImageIO.read(oldFile); 
            int w = srcFile.getWidth(null); 
            int h = srcFile.getHeight(null); 
            double bili; 
            if(width>0){ 
                bili=width/(double)w; 
                height = (int) (h*bili); 
            }else{ 
                if(height>0){ 
                    bili=height/(double)h; 
                    width = (int) (w*bili); 
                } 
            } 
            /** 宽,高设定 */ 
            BufferedImage tag = new BufferedImage(width, height, 
                    BufferedImage.TYPE_INT_RGB); 
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null); 
            //String filePrex = oldFile.getName().substring(0, oldFile.getName().indexOf('.')); 
            /** 压缩后的文件名 */ 
            //newImage = filePrex + smallIcon+  oldFile.getName().substring(filePrex.length()); 
   
            /** 压缩之后临时存放位置 */ 
            File newFile = new File(newFilePath);
            if (!newFile.exists()) {
				FileTool.write(newFilePath, "", false);
			}
            FileOutputStream out = new FileOutputStream(newFile); 
   
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); 
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag); 
            /** 压缩质量 */ 
            jep.setQuality(quality, true); 
            encoder.encode(tag, jep); 
            out.close(); 
   
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    }
}
