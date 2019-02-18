package cn.people.service.view.obj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentTool {
	private int longContentLen=1140;
	private int longContentSection=11;
	/*re 字符串
	 *versionWith 标准无线图片版本宽度 
	 *systemRoot 系统根目录
	 *picServer 无线图片服务器
	 *return 替换后的图片
	 * */	
	public  String wirelessImgSrc(String re,int versionWith,String systemRoot,String picServer){
		String regex = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
		//regex = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
	    final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher ma = pa.matcher(re); 
		ImageTool imageTool = new ImageTool();
		while (ma.find()){
			String src = ma.group(1);
			String picPath = src;
			if(src.contains("http://paper.people.com.cn/")){
				return re;
			}
			if(src.contains("http://www.people.com.cn")){
				picPath = src.replace("http://www.people.com.cn", "");
			}
			String path = systemRoot+"/wirelesspic"+picPath;
			//String path = systemRoot+picPath;
			//System.out.println(path);
			String imgWith_WX = imageTool.getimgWith_Wireless(versionWith, path); 
			String newSrc = picServer+"/"+imgWith_WX+"/data/cms"+picPath;   
		    re = re.replace(src, newSrc);
		}
		return re;
	}
	public  String replaceEmbed(String re){
		byte[] byteArray;
		String res = re;
		try {
			String regex1 = "<embed\\s+([^<>]*?)tvplayer.people.com.cn(.*?)></embed>";
			Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
			java.util.regex.Matcher m_html = pa1.matcher(re); 		
			 while(m_html.find()){
				 String c = m_html.group(0);
				 int s = c.indexOf("xml");
				 int e = c.indexOf("playerByOsmf.swf");
				 String b = c.substring(s,e);			 		
				 String [] arr = b.split("/");
				 String g = arr[1];
				 String a = "";
				 if(arr.length>=3){
					 String xml = arr[2];
					 int ss = b.lastIndexOf(xml);
					 xml = b.substring(ss);		 
					 String [] arr1 = xml.split("/");
					 for(int i=0;i<arr1.length;i++){
						 String d = arr1[i];
						 if(i%2==0){
							 a += d+":";
						 }else{
							 a +=d+","; 			
						 }
					 }
				}
				String noad = "";
				String skip = "";			 
				String regex2 = "(width|height)=(.*?)[0-9]{1,4}(.*?)[\"]{0,1}";
				Pattern pa2 = Pattern.compile(regex2, Pattern.DOTALL);
				Matcher ma2 = pa2.matcher(c); 
				while(ma2.find()){
					a += ma2.group()+",";
				}
				int end = a.lastIndexOf(",");
				a = a.substring(0, end);
				a = a.replace("=", ":");
				 
				byteArray = Base64.decryptBASE64(g);
				String f = new String(byteArray);
				String h = "<script src=\"http://tv.people.com.cn/img/player/v.js\"></script><script>showPlayer({id:\""+f+"\","+a+"});</script>"; 
				res = res.replace(c, h);
			 }
		} catch (Exception e) {
			res = "";
			e.printStackTrace();
		}
		return res;
	}
	
	public String getPageStrWIRELESS(Short pageType,int pageCount,int page,int contentId){
		String pageStr = "<div class=\"wb_page center\">";
		if(pageType!=null){     		 			
//			if(page>1){
//	    		int prePage = page-1;
//	    		pageStr = pageStr+"<a href=\"NewsView.shtml?id="+contentId+"&page="+prePage+"\">上一页</a>"; 
//	    	}        		
	    	if(page<pageCount){
	    		int nextPage = page+1;
	    		pageStr = pageStr+"<a href=\"javascript:showAll();\">余下全文</a>"; 
	    		//pageStr = pageStr+"<a href=\"NewsView.shtml?id="+contentId+"&page="+nextPage+"\">下一页</a>";
	    		
	    	}
		}
		pageStr = pageStr +"</div>";
		pageStr = pageStr +"<!--NOPAGE-->";
    	return pageStr;
	}
	
	public String getImgStyle(String rt){
		String regex = "<\\s*img\\s+([^>]*)\\s*>";
	    final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
	    final Matcher ma = pa.matcher(rt);    
	    String re = "";
	    String x = "";
	    while (ma.find()){
	    	x = ma.group();   
			String regex1 = "style=\"([^>]*)(width|height):\\s*[0-9]{1,4}px;\\s*(width|height):\\s*[0-9]{1,4}px;\\s*\"";				
			Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
			Matcher ma1 = pa1.matcher(x); 
			String xx = "";
			String newStr = "";
			while(ma1.find()){
				xx = ma1.group();
				String regex2 = "(width|height):\\s*[0-9]{1,4}";
				Pattern pa2 = Pattern.compile(regex2, Pattern.DOTALL);
				Matcher ma2 = pa2.matcher(xx); 
				while(ma2.find()){
					String xxx = ma2.group().replace(":", "=")+" ";
					newStr = newStr + xxx;
				}				
			}
			re = x.replace(xx, newStr);
			rt = rt.replace(x, re);
		}	    	
	    return rt;
	}
	public String getPicNoIndent(String rt){
		String regex = "<p style=\"text-indent: 2em;.*?>([\\s\\S]*?)</p>";
	    final Pattern p = Pattern.compile(regex, Pattern.DOTALL);
	    final Matcher m = p.matcher(rt);  
	    while(m.find()){
	        String oldStr = m.group(0);
	        if(oldStr.contains("img")||oldStr.contains("tvplayer.people.com.cn")){
	        	String newStr = oldStr.replace("text-indent: 2em; ", "");	
	        	  rt = rt.replace(oldStr, newStr);
	        }	      
	    }
	    return rt;
	}
	public String getAdKey(String second,String third){
		String local = "";
		if(second.equals("1016")){
			local = "pic";
		}
		if(second.equals("122366")){
			local = "comic";
		}
		if(second.equals("40130")){
			local = "game";
		}
		if(second.equals("1013")){
			local = "culture";
		}
		if(second.equals("151132")){
			local = "gongyi";
		}
		if(second.equals("54816")){
			local = "mnc";
		}
		if(second.equals("41390")){
			local = "homea";
		}
		if(second.equals("183008")){
			local = "tc";
		}
		if(second.equals("1009")){
			local = "IT";
		}
		if(second.equals("1005")){
			local = "auto";
		}
		if(second.equals("42272")){
			local = "hm";
		}
		if(second.equals("14657")){
			local = "tw";
		}
		if(second.equals("1011")){
			local = "military";
		}
		
		if(second.equals("1002")){
			local = "world";
		}
		if(second.equals("143124")){
			local = "fangtan";
		}
		if(second.equals("14677")){
			local = "media";
		}
		if(second.equals("1003")){
			local = "opinion";
		}
		if(second.equals("41426")){
			local = "art";
		}
		if(second.equals("1006")){
			local = "edu";
		}
		if(second.equals("14739")){
			local = "health";
		}
		if(second.equals("85914")){
			local = "food";
		}
		if(second.equals("164220")){
			local = "house";
		}
		if(second.equals("31889")){
			local = "lottery";
		}
		if(second.equals("71661")){
			local = "energy";
		}
		if(second.equals("198221")){
			local = "history";
		}
		if(second.equals("41570")){
			local = "travel";
		}
		if(second.equals("57922")){
			local = "expo";
		}
		if(second.equals("58278")){
			local = "leader";
		}
		if(second.equals("14717")){
			local = "unn";
		}
		if(second.equals("42510")){
			local = "legal";
		}
		if(second.equals("1008")){
			local = "society";
		}
		if(second.equals("1001")){
			local = "politics";
		}
		if(second.equals("1000")){
			local = "news";
		}
		if(second.equals("1")){
			local = "homepage";
		}
		if(second.equals("1007")){
			local = "scitech";
		}
		if(second.equals("14820")){
			local = "sports";
		}
		if(second.equals("1010")){
			local = "env";
		}
		if(second.equals("1014")){
			local = "lady";
		}
		if(second.equals("1012")){
			local = "ent";
		}
		if(second.equals("1004")){
			local = "finance";
			if(third.equals("67815")){
				local = "stock";
			}
		}
		if(second.equals("141677")){
			local = "ccnews";
		}
		if(second.equals("151264")){
			local = "jiaju";
		}
		
		if(second.equals("32306")){
			if(third.equals("143124")){
				local = "fangtan";
			}
		}
								
		if(second.equals("14540")){
			local = "bj";
		}
		if(second.equals("192235")){
			local = "he";
		}
		if(second.equals("186328")){
			local = "shanxi";
		}
		if(second.equals("192247")){
			local = "nm";
		}
		if(second.equals("220005")){
			local = "hlj";
		}
		if(second.equals("134768")){
			local = "sh";
		}
		if(second.equals("166188")){
			local = "sd";
		}
		if(second.equals("186327")){
			local = "zj";
		}
			
		if(second.equals("181466")){
			local = "fujian";
		}
		if(second.equals("186330")){
			local = "jx";
		}
		if(second.equals("123932")){
			local = "gd";
		}
		if(second.equals("179409")){
			local = "gx";
		}
		if(second.equals("192237")){
			local = "hb";
		}
		if(second.equals("192238")){
			local = "hn";
		}
		if(second.equals("210626")){
			local = "yn";
		}
		if(second.equals("192245")){
			local = "gz";
		}	
		if(second.equals("186331")){
			local = "sx";
		}
		if(second.equals("181468")){
			local = "gs";
		}
		if(second.equals("186332")){
			local = "xj";
		}
		if(second.equals("181467")){
			local = "qh";
		}
		if(second.equals("186329")){
			local = "nx";
		}
		if(second.equals("70078")){
			local = "wf";
		}
		if(second.equals("138996")){
			local = "wz";
		}
		if(second.equals("154630")){
			local = "su";
		}
		
		if(second.equals("202846")){
			local = "sz";
		}
		if(second.equals("138901")){
			local = "xz";
		}
		if(second.equals("192244")){
			local = "anhui";
		}
			
		if(second.equals("123701")){
			local = "weihai";
		}
		if(second.equals("158820")){
			local = "ksz";
		}
		if(second.equals("175649")){
			local = "ordos";
		}	
		if(second.equals("339782")){
			local = "ln";
		}
		if(second.equals("345167")){
			local = "sichuan";
		}
		if(second.equals("366126")){
			local = "golf";
		}
		if(second.equals("351638")){
			local = "henan";
		}
		if(second.equals("228872")){
			local = "hainan";
		}
		if(SharedConstant.SYSTEM_NAME.equals("LOCAL")){
			if(second.equals("98407")){
				local = "zibo";
			}
		}
		if(second.equals("68880")){
			local = "book";
		}
		if(second.equals("136655")){
			local = "ip";
		}
		if(second.equals("358232")){
			local = "js";
		}
		if(second.equals("40531")){
			local = "theory";
		}
		if(second.equals("42877")){
			local = "money";
		}
		if(second.equals("375366")){
			local = "tj";
		}
		
		if(second.equals("406725")){
			local = "fupin";
		}
		
		if(second.equals("411837")){
			local = "ydyl";
		}
		if(second.equals("413883")){
			local = "industry";
		}
		return local;
	}
}
