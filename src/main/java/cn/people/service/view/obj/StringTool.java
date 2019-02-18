package cn.people.service.view.obj;

import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class StringTool {
	public static String REGEX_ID_LIST = "[0-9]+(,[0-9]+)*";
	public static String REGEX_TAGID_LIST = "(PRE)?[0-9]+(,(PRE)?[0-9]+)*";
	public static String REGEX_NUMBER = "[0-9]{1,20}";
	public static String REGEX_IP = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
	public static Pattern REGEX_TAG = Pattern.compile("\\$\\{TAG_([0-9]+)_TAG\\}");
	public static String REGEX_EMPTY_STRING = "[ 　\t]*";// 判断字符串是否由全角或半角空格以及tab字符组成
	public static String REGEX_ENGLISH_STRING = "[a-zA-Z0-9]+";// 判断字符串是否由字母和数字组成
	public static Pattern REGEX_LETTER = Pattern.compile("[a-zA-Z0-9]+");
	public static String REGEX_ENGLISH_FILE_STRING = "[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)?";// 判断字符串是否由字母和数字组成的文件名

	// 将字符串source中的所有出现的oldString替换为newString
	public static String replaceAll(String source, String oldString, String newString) {
		if (source == null)
			return null;
		StringBuffer res = new StringBuffer(source);
		if (res != null && oldString != null && newString != null) {
			int offset = 0;
			while ((offset = res.indexOf(oldString, offset)) >= 0) {
				res.replace(offset, offset + oldString.length(), newString);
				offset = offset + newString.length();
			}
		}
		return res.toString();
	}
	public static boolean isTagIdList(String string) {
        return string == null ? false : string.matches(REGEX_TAGID_LIST);
    }
	public static boolean isIdList(String string) {
		return string == null ? false : string.matches(REGEX_ID_LIST);
	}

	public static boolean isIP(String string) {
		return string == null ? false : string.matches(REGEX_IP);
	}

	// 将字符串source中的所有出现的oldString替换为newString
	public static String replaceAll_old(String source, String oldString, String newString) {
		String res = source;
		if (res != null && oldString != null && newString != null) {
			int offset = 0;
			while ((offset = res.indexOf(oldString, offset)) >= 0) {
				res = res.substring(0, offset) + newString + res.substring(offset + oldString.length());
				offset = offset + newString.length();
			}
		}
		return res;
	}

	public static String getNotNullString(Object obj) {
		if (obj == null)
			return "";
		else if (obj instanceof String) {
			return (String) obj;
		} else
			return obj.toString();
	}

	public static String getString(Object obj) {
		if (obj == null)
			return null;
		else if (obj instanceof String) {
			return (String) obj;
		} else
			return obj.toString();
	}

	public static Vector<String> MatchTagList(String templateContent) {
		Vector<String> v = new Vector<String>();
		Matcher matcher = REGEX_TAG.matcher(templateContent);
		while (matcher.find()) {
			String tagid = matcher.group(1);
			v.add(tagid == null ? "" : tagid);
		}
		return v;
	}

	public static String[] splitString(final String string, final String onToken) {
		if (string == null)
			return null;
		final StringTokenizer tokenizer = new StringTokenizer(string, onToken);
		final String[] result = new String[tokenizer.countTokens()];

		for (int i = 0; i < result.length; i++) {
			result[i] = tokenizer.nextToken();
		}

		return result;
	}

	// 判断字符串是否为空
	public static boolean isEmptyString(Object obj) {
		if (obj == null)
			return true;
		else if (obj instanceof String) {
			return ((String) obj).matches(REGEX_EMPTY_STRING);
		} else
			return true;
	}

	// 判断字符串是否匹配字符和数字组成的文件名
	public static boolean isEnglishFileString(Object obj) {
		if (obj == null)
			return false;
		else if (obj instanceof String) {
			return ((String) obj).matches(REGEX_ENGLISH_FILE_STRING);
		} else
			return false;
	}

	public static String clob2Str(Clob cl) {
		if (cl == null) {
			return null;
		} else {
			String rtn = null;
			try {
				rtn = cl.getSubString((long) 1, (int) cl.length());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rtn;
		}
	}

	public static int getStringByteLength(String str) {
		int len = 0;
		if (str != null && str.length() > 0) {
			try {
				return str.getBytes("UTF-8").length;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return len;
	}

	// 创建length位随机数, %010d补0
	public static String buildRandom(final int length) {
		Long max = Long.parseLong(StringUtils.rightPad("1", length + 1, '0'));
		return String.format("%0" + length + "d", Math.abs(RandomUtils.nextLong()) % max);
	}

	//将字符传转换为long型，如果出错返回0
	public static long parseLong(String string) {
		try {
			return Long.parseLong(string);
		} catch (Exception e) {
			return 0;
		}

	}
	//将字符传转换为int型，如果出错返回0
	public static int parseInt(String string) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			return 0;
		}

	}
	public static Integer parseInteger(String string) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			return null;
		}
	}
	public static Short parseShort(String string) {
		try {
			return Short.valueOf(string);
		} catch (Exception e) {
			return null;
		}
	}
	public static String toGBK(String strvalue) {
		try {
			if (strvalue == null) {
				return "";
			} else {
				strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK").trim();
				return strvalue;
			}
		} catch (Exception e) {
			return "";
		}
	}
	public static String tolatin(String strvalue) {
		try {
			if (strvalue == null) {
				return "";
			} else {
				strvalue = new String(strvalue.getBytes("GBK"), "ISO8859_1").trim();
				return strvalue;

			}
		} catch (Exception e) {
			return "";
		}
	}
	public static String replaceEmbed(String re){
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
	public static boolean isChinese(char c) {	
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);	
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS	
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS	
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A	
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;	
        }	
        return false;	
    }
    // 完整的判断中文汉字和符号
    public static double getStrLength(String strName) {	
        char[] ch = strName.toCharArray();
        double valueLength = 0;  
        for (int i = 0; i < ch.length; i++) {	
            char c = ch[i];	
            if (isChinese(c)) {	
            	valueLength+=1;	
            }else{
            	valueLength+=0.5;	
            }	
        }	
        return valueLength;	
    }    
    public static String deleteBlank(String re){
    	String dest = "";  
        if (re!=null) {
        	Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
    		Matcher m = p.matcher(re);
    		dest = m.replaceAll("");
        }  	      	
        return dest;
    }
    
    //过滤非法字符,文件名只允许字母和数字,如果有问题返回def默认字符串
	public static String getFileExtName(String name, String def) {
		if (name != null) {
			name = name.trim();
			if (name.length() > 0) {
				if (name.matches(REGEX_ENGLISH_STRING) == false) {
					return def;
				}
			} else {
				return "";
			}
		} else {
			return "";
		}
		return name;
	}
	//过滤非法字符,文件名只允许字母和数字,如果有问题返回def默认字符串
	public static String getFileFullName(String name) {
		if (name != null && name.length() > 0) {
			name = name.trim();
			name=name.replaceAll("./", "");
			name=name.replaceAll("..", "");
		}
		return "";
	}
	//判断文件路径中是否有./ 和..
	public static Boolean isFileFullName(String name) {
		if (name != null && name.length() > 0) {
			if (name.indexOf("./") >= 0) {
				return false;
			} else if (name.indexOf("..") >= 0) {
				return false;
			}else return true;
		}
		return false;
	}
    
	public static void main(String[] args) {
				System.out.println(isFileFullName("/cms/cms/cms/cms/aa.jpg"));
	}
}
