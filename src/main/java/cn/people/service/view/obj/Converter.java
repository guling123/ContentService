package cn.people.service.view.obj;

import com.sun.org.apache.regexp.internal.RE;


public class Converter {

    public Converter() {
    }

    public static long str2Long(String str) {
	long res = 0;
	try {
	    if (str != null) {
		RE r = new RE("\\D");
		String s = r.subst(str , "");
		if (s.length()>0)
		    res = Long.parseLong(s);
	    }
	} catch (Exception ex) {
	}

	return res;
    }

    public static int str2Int(String str) {
	int res = 0;
	try {
	    if (str != null) {
		RE r = new RE("\\D");
		String s = r.subst(str , "");
		if (s.length()>0)
		    res = Integer.parseInt(s);
	    }
	} catch (Exception ex) {
	}
	return res;
    }


    public static String bool2StrHZ(boolean bool){
	return bool?"是":"否";
    }

    public static String bool2StrYN(boolean bool){
	return bool?"Y":"N";
    }

    public static int bool2Int(boolean bool) {
	return bool?1:0;
    }

    public static String Uni2GB(String original) {
	if(original != null) {
	    try {
		return new String(original.getBytes("ISO8859_1"), "GBK");
	    } catch (Exception e) {
		e.printStackTrace();
		return null;
	    }
	} else return null;
    }

    public static String HTML_Txt(String html) {
	int start=0,end=0;
	while((start=html.indexOf('<'))!=-1){
	    end = html.indexOf('>',start);
	    //Util.Debug("!start=" +start+ " end=" + end);
	    if (end>start) {
		html = removeStr(html,start,end);
		//Util.Debug(html);
	    }
	}
	return html;
    }

    public static String Summary(String content) {
	int start = content.indexOf("\r\n");
	if (start<100) {
	    start = content.indexOf("\r\n",start);
	}
	return content.substring(0,start);
    }


    public static String removeStr(String s,int start, int end) {
	if (start>=0&&end<s.length()&&end>start){
	    String s1 = s.substring(0,start);
	    String s2 = s.substring(end+1);
	    return s1 + s2;
	}
	return s;
    }

    public static String URLGBKEncode(String strSRC)
    {
	if (strSRC==null)
	    strSRC = "";
	try
	{
	    byte[]	ba	= strSRC.getBytes("GBK");
	    strSRC		= new String(ba,0,ba.length);
	    return java.net.URLEncoder.encode(strSRC);
	}
	catch (java.io.UnsupportedEncodingException ex)
	{
	    return java.net.URLEncoder.encode(strSRC);
	}
    }

    public static String removeBR(String str) {
	String res = "";
	char[] c = str.toCharArray();

	int len = c.length;
	for (int i = c.length-1; i>0 ; i--) {
	    if (c[i]=='\n' || c[i]=='\r') {
		len --;
	    } else {
		break;
	    }
	}
	res = new String(c , 0 , len);
	return res;
    }
       
   
    /** 
     * 将字符串编码成 Unicode 。 
     * @param theString 待转换成Unicode编码的字符串。 
     * @param escapeSpace 是否忽略空格。 
     * @return 返回转换后Unicode编码的字符串。 
     */  
    public static String toUnicode(String theString, boolean escapeSpace) {  
        int len = theString.length();  
        int bufLen = len * 2;  
        if (bufLen < 0) {  
            bufLen = Integer.MAX_VALUE;  
        }  
        StringBuffer outBuffer = new StringBuffer(bufLen);  
  
        for(int x=0; x<len; x++) {  
            char aChar = theString.charAt(x);  
            // Handle common case first, selecting largest block that  
            // avoids the specials below  
            if ((aChar > 61) && (aChar < 127)) {  
                if (aChar == '\\') {  
                    outBuffer.append('\\'); outBuffer.append('\\');  
                    continue;  
                }  
                outBuffer.append(aChar);  
                continue;  
            }  
            switch(aChar) {  
                case ' ':  
                    if (x == 0 || escapeSpace)  
                        outBuffer.append('\\');  
                    outBuffer.append(' ');  
                    break;  
                case '\t':outBuffer.append('\\'); outBuffer.append('t');  
                          break;  
                case '\n':
                	//outBuffer.append('\\'); 
                	//outBuffer.append('n');  
                          break;  
                case '\r':outBuffer.append('\\'); outBuffer.append('r');  
                          break;  
                case '\f':outBuffer.append('\\'); outBuffer.append('f');  
                          break;  
                case '=': // Fall through  
                case ':': // Fall through  
                case '#': // Fall through  
                case '!':  
                    //outBuffer.append('\\'); 
                    outBuffer.append(aChar);  
                    break;  
                default:  
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {  
                        outBuffer.append('\\');  
                        outBuffer.append('u');  
                        outBuffer.append(toHex((aChar >> 12) & 0xF));  
                        outBuffer.append(toHex((aChar >>  8) & 0xF));  
                        outBuffer.append(toHex((aChar >>  4) & 0xF));  
                        outBuffer.append(toHex( aChar        & 0xF));  
                    } else {  
                        outBuffer.append(aChar);  
                    }  
            }  
        }  
        return outBuffer.toString();  
    }  
    private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };   
    private static char toHex(int nibble) { return hexDigit[(nibble & 0xF)]; }
}