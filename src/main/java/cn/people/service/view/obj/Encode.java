package cn.people.service.view.obj;
/**
 * <p>Title: PUBLISH_II PROJECT</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.sun.org.apache.regexp.internal.RE;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Encode
{
    public static String replace(String in,String s,String d) {
        try {
            RE re = new RE(s);
            String result = re.subst(in,d);
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String input(String in) {
        if ( in==null || in.length() == 0 ) {
            return "";
        }
        char[] charArray = in.toCharArray();
        StringBuffer strBuff = new StringBuffer();

        for( int i = 0; i < charArray.length; i++ )
        {
            switch( charArray[i] )
        {
            case '\r':
                break;
            case '&':
                strBuff.append("&amp;");
                break;
            case '<':
                strBuff.append("&lt;");
                break;
            case '>':
                strBuff.append("&gt;");
                break;
            case '\"':
                strBuff.append("&quot;");
                break;
            default:
                strBuff.append(charArray[i]);
        }

        }
        return strBuff.toString();
    }

    public static String htmlsp(String in)
    {
        if (in == null || in.length() == 0 ||in.equals("&$&$")) {
            return "";
        }
        char[] charArray = in.toCharArray();

        StringBuffer strBuff = new StringBuffer();

        boolean convertFlag = true;
        int len = charArray.length;
        for (int i = 0; i < len; i++) {
            if ( charArray[i] == '&' && i+1 < len &&charArray[i+1] == '$' )
            {
            convertFlag = !convertFlag;
            i += 2;
        }
        if ( i >= len )
            break;
        if ( convertFlag )
        {
            switch (charArray[i]) {
                case '\r':
                    break;
                case '&':
                    if ( i+1 < len &&charArray[i+1] == '$' )
                    {
                        i += 1;
                        convertFlag = !convertFlag;
                        continue;
                    }
                    strBuff.append("&amp;");
                    break;
                case '<':
                    strBuff.append("&lt;");
                    break;
                case ' ':
                    strBuff.append("&nbsp;");
                    break;
                case '>':
                    strBuff.append("&gt;");
                    break;
                case '\"':
                    strBuff.append("&quot;");
                    break;
                case '\n':
                    strBuff.append("<br>");
                    break;
                default:
                    strBuff.append(charArray[i]);
            }
        }
        else
            strBuff.append(charArray[i]);
        }
        return strBuff.toString();

    }

   /* public static String newhtmlsp(String in)
   {
       if (in == null || in.length() == 0) {
           return "";
       }
       char[] charArray = in.toCharArray();

       StringBuffer strBuff = new StringBuffer();

       boolean convertFlag = true;
       int len = charArray.length;

       for (int i = 0; i < len; i++) {
           if ( charArray[i] == '<' )
            {
            convertFlag = false;
            }
            if ( charArray[i] == '>' )
            {
            convertFlag = true;
            }
        Pattern charArray[i]=Pattern.compile(" ");
        Matcher m=charArray[i].matcher("> <");
        boolean b=m.matches();


       if ( i >= len )
           break;
       if ( convertFlag )
       {
           switch (charArray[i]) {
               case '\r':
                   break;
               case ' ':
                       if ( charArray[i-1] == '>' )
                                 {
                                   break;
                                 }
                             else{
                                 strBuff.append("&nbsp;");
                                 break;
                             }

               case '\n':
                   if (charArray[i - 2] == '>' || charArray[i - 2] == ' ' ||
                       charArray[i - 1] == ' ') {
                       break;
                   }
                   else {
                       strBuff.append("<br>");
                       break;
                   }


               case '&':
                   if (i + 1 < len && charArray[i + 1] == '$') {
                       i += 1;
                       continue;
                   }
                   case '>':
                         if ( i+1 < len &&charArray[i+2] == '\n' )
                              {
                                  i=i+2;
                                  strBuff.append(">");
                                  break;
                             }


               case '>':
                   if (i + 1 < len && charArray[i + 1] == ' ') {
                       i += 1;
                       strBuff.append(">");
                       break;
                   }

               default:
                   strBuff.append(charArray[i]);
           }
       }
       else
           strBuff.append(charArray[i]);
       }
       return strBuff.toString();

   }
*/

    public static String htmlsp_nospace(String in)
    {
        if (in == null || in.length() == 0 || in.equals("&$&$")) {
            return "";
        }
        char[] charArray = in.toCharArray();

        StringBuffer strBuff = new StringBuffer();

        boolean convertFlag = true;
        int len = charArray.length;
        for (int i = 0; i < len; i++) {
            if ( charArray[i] == '&' && i+1 < len &&charArray[i+1] == '$' )
            {
            convertFlag = !convertFlag;
            i += 2;
        }
        if ( i >= len )
            break;
        if ( convertFlag )
        {
            switch (charArray[i]) {
                case '\r':
                    break;
                case '&':
                    if ( i+1 < len &&charArray[i+1] == '$' )
                    {
                        i += 1;
                        convertFlag = !convertFlag;
                        continue;
                    }
                    strBuff.append("&amp;");
                    break;
               case '<':
                    strBuff.append("&lt;");
                    break;
                case '>':
                    strBuff.append("&gt;");
                    break;
                case '\"':
                    strBuff.append("&quot;");
                    break;
                case '\n':
                    strBuff.append("<br>");
                    break;
                default:
                    strBuff.append(charArray[i]);
            }
        }
        else
            strBuff.append(charArray[i]);
        }
        return strBuff.toString();

    }
    public static String htmlsp_hawen(String in)
    {
        if (in == null || in.length() == 0) {
            return "";
        }
        char[] charArray = in.toCharArray();

        StringBuffer strBuff = new StringBuffer();

        boolean convertFlag = true;
        int len = charArray.length;
        for (int i = 0; i < len; i++) {
            if ( charArray[i] == '&' && i+1 < len &&charArray[i+1] == '$' )
            {
            convertFlag = !convertFlag;
            i += 2;
        }
        if ( i >= len )
            break;
        if ( convertFlag )
        {
            switch (charArray[i]) {
                case '\r':
                    break;
                case '&':
                    if ( i+1 < len &&charArray[i+1] == '$' )
                    {
                        i += 1;
                        convertFlag = !convertFlag;
                        continue;
                    }
                    strBuff.append("&amp;");
                    break;
               case '<':
                    strBuff.append("&lt;");
                    break;
                case '>':
                    strBuff.append("&gt;");
                    break;
                case '\"':
                    strBuff.append("&quot;");
                    break;
                case '\n':
                    strBuff.append("<br>");
                    break;
                default:
                    strBuff.append(charArray[i]);
            }
        }
        else
            strBuff.append(charArray[i]);
        }
        String result=strBuff.toString().replaceAll("&nbsp;", " ");
        result=result.replaceAll("&nbsp1;", "&nbsp;");
        return result;

    }
    /**
     *
     by yangsong 2006-06-26  英文文本
    */
    public static String htmlsp_nospacekuohao(String in)
        {
            if (in == null || in.length() == 0 || in.equals("&$&$")) {
                return "";
            }
            char[] charArray = in.toCharArray();

            StringBuffer strBuff = new StringBuffer();

            boolean convertFlag = true;
            int len = charArray.length;
            for (int i = 0; i < len; i++) {
                if ( charArray[i] == '&' && i+1 < len &&charArray[i+1] == '$' )
                {
                convertFlag = !convertFlag;
                i += 2;
            }
            if ( i >= len )
                break;
            if ( convertFlag )
            {
                switch (charArray[i]) {
                    case '\r':
                        break;
                    case '&':
                        if ( i+1 < len &&charArray[i+1] == '$' )
                        {
                            i += 1;
                            convertFlag = !convertFlag;
                            continue;
                        }
                        strBuff.append("&amp;");
                        break;
                    /*case '<':
                        strBuff.append("&lt;");
                        break;
                    case '>':
                        strBuff.append("&gt;");
                        break;*/
                    case '\"':
                        strBuff.append("&quot;");
                        break;
                    case '\n':
                        strBuff.append("<br>");
                        break;
                    default:
                        strBuff.append(charArray[i]);
                }
            }
            else
                strBuff.append(charArray[i]);
            }
            return strBuff.toString();

        }



    public static String html(String in) {
        if ( in==null || in.length() == 0 ) {
            return "";
        }
        char[] charArray = in.toCharArray();
        StringBuffer strBuff = new StringBuffer();

        for( int i = 0; i < charArray.length; i++ )
        {
            switch( charArray[i] )
        {
            case '\r':
                break;
            case '&':
                strBuff.append("&amp;");
                break;
            case '<':
                strBuff.append("&lt;");
                break;
            case '>':
                strBuff.append("&gt;");
                break;
            case '\"':
                strBuff.append("&quot;");
                break;
            case '\n':
                strBuff.append("<br>");
                break;
            default:
                strBuff.append(charArray[i]);
        }

        }
        return strBuff.toString();
    }

    public static String javascript(String in) {
      if (in == null || in.length() == 0) {
        return "";
      }
      char[] charArray = in.toCharArray();
      StringBuffer strBuff = new StringBuffer();

      for (int i = 0; i < charArray.length; i++) {
        switch (charArray[i]) {
          case '\'':
            strBuff.append("\\\'");
            break;
          case '\"':
              strBuff.append("\\\"");
            break;
          case '\n':
            strBuff.append("\\n");
            break;
          case '\r':
            strBuff.append("\\r");
            break;
          case '\t':
            strBuff.append("\\t");
            break;
          default:
            strBuff.append(charArray[i]);
        }
      }
      return strBuff.toString();


  /*
 if (in==null||"".equals(in)) {
     return "";
 }
 String result = "";
        //result = replace(in,"\\","\\\\");
        //result = replace(result,"\n","\\n");
        //result = replace(result,"\t","\\t");
        //result = replace(result,"\"","\\\"");
        //result = replace(result,"/","\\/");
 result = replace(in,"'"," ");
 result = replace(result,"\""," ");
 return result;
  */
    }
    public static String javascripttemp(String in) {
      if (in == null || in.length() == 0) {
        return "";
      }
      char[] charArray = in.toCharArray();
      StringBuffer strBuff = new StringBuffer();

      for (int i = 0; i < charArray.length; i++) {
        switch (charArray[i]) {
          case '\'':
            strBuff.append("\''");
            break;
           default:
            strBuff.append(charArray[i]);
        }
      }
      return strBuff.toString();
    }

    public static String sql(String in) {
        if ( in==null || in.length() == 0 ) {
            return "";
        }
        char[] charArray = in.toCharArray();
        StringBuffer strBuff = new StringBuffer();

        for( int i = 0; i < charArray.length; i++ )
        {
            switch( charArray[i] )
        {
            case '\\':
                strBuff.append( "\\\\" );
                break;
            case '\000':
                strBuff.append( "\\0");
                break;
            case '\n':
                strBuff.append( "\\n");
                break;
            case '\r':
                strBuff.append( "\\r");
                break;
            case '\032':
                strBuff.append( "\\Z");
                break;
            case '\'':
                strBuff.append( "\\'");
                break;
            case '\"':
                strBuff.append( "\\\"");
                break;
            default:
                strBuff.append(charArray[i]);
        }

        }
        return strBuff.toString();
  /*
 if (in==null||"".equals(in)) {
     return "";
 }
 String result = "";
 result = replace(in,"\\","\\\\");
 result = replace(result,"\000","\\0");
 result = replace(result,"\n","\\n");
 result = replace(result,"\r","\\r");
 result = replace(result,"\032","\\Z");
 result = replace(result,"'","\\'");
 result = replace(result,"\"","\\\"");
 return result;
  */
    }

    public static String http_url(String in) {
        if ( in==null || in.length() == 0 ) {
            return "";
        }
        char[] charArray = in.toCharArray();
        StringBuffer strBuff = new StringBuffer();

        for( int i = 0; i < charArray.length; i++ )
        {
            switch( charArray[i] )
        {
            case '\000':
                strBuff.append( "%00" );
                break;
            case ' ':
                strBuff.append( "%20" );
                break;
            case '\t':
                strBuff.append( "%09" );
                break;
            case '\n':
                strBuff.append( "%0a" );
                break;
            case '\r':
                strBuff.append( "%0d" );
                break;
            case '%':
                strBuff.append( "%25" );
                break;
            case '\'':
                strBuff.append( "%27" );
                break;
            case '\"':
                strBuff.append( "%22" );
                break;
            case '#':
                strBuff.append( "%23" );
                break;
            case '&':
                strBuff.append( "%26" );
                break;
            case '?':
                strBuff.append( "%3f" );
                break;
            case '=':
                strBuff.append( "%3d" );
                break;
            case '/':
                strBuff.append( "%2f" );
                break;
            case ':':
                strBuff.append( "%3a" );
                break;
            default:
                strBuff.append(charArray[i]);
        }

        }
        return strBuff.toString();
  /*
 if (in==null||"".equals(in)) {
     return "";
 }
 String result = "";
 result = replace(in,"\000","%00");
 result = replace(result," ","%20");
 result = replace(result,"\t","%09");
 result = replace(result,"\n","%0a");
 result = replace(result,"\r","%0d");
 result = replace(result,"%","%25");
 result = replace(result,"'","%27");
 result = replace(result,"\"","%22");
 result = replace(result,"#","%23");
 result = replace(result,"&","%26");
 result = replace(result,"?","%3f");
 result = replace(result,"=","%3d");
 result = replace(result,"/","%2f");
 result = replace(result,":","%3a");
 return result;
  */
    }

    public static String http_cookie(String in) {
        if ( in==null || in.length() == 0 ) {
            return "";
        }
        char[] charArray = in.toCharArray();
        StringBuffer strBuff = new StringBuffer();

        for( int i = 0; i < charArray.length; i++ ){
            switch( charArray[i])
        {
            case '=':
                strBuff.append( "%3d" );
                break;
            case ',':
                strBuff.append( "%2c" );
                break;
            case ';':
                strBuff.append( "%3b" );
                break;
            case '%':
                strBuff.append( "%25" );
                break;
            default:
                strBuff.append(charArray[i]);
        }

        }
        return strBuff.toString();
  /*
 String result = "";
 result = replace(in,"=","%3d");
 result = replace(result,",","%2c");
 result = replace(result,";","%3b");
 result = replace(result,"%","%25");
 return result;
  */
    }

    public static void main(String[] args) {
        System.out.println(html("2003年·中国春运  "));
    }
    //为蒙文生成title属性
    public static String htmlMongolTitle(String in)
    {
        String result=htmlsp_nospace(in);
        result=result.replaceAll("\n", "<br />");
        result=result.replaceAll("\"", "'");
        return result;

    }
    //过滤html代码并截断字符串
    public static String htmlsp(String in,int len,String ext){
    	String result=Html2Text.convert(htmlsp(in));
    	if(len>0 && result.length()>len){
    		return result.substring(0,len)+(ext==null?"":ext);
    	}
    	return result;
    }
    public static String htmlsp(String in,int len){
    	return htmlsp(in,len,"...");
    }
    
  //过滤html代码并截断字符串
    public static String htmlsp_nospace(String in,int len,String ext){
    	String result=Html2Text.convert(htmlsp_nospace(in));
    	if(len>0 && result.length()>len){
    		return result.substring(0,len)+(ext==null?"":ext);
    	}
    	return result;
    }
    public static String htmlsp_nospace(String in,int len){
    	return htmlsp_nospace(in,len,"...");
    }
    
//过滤掉&$并将实体代码转换为字符
    public static String htmlsp_filter(String in)
    {
        if (in == null || in.length() == 0 ||in.equals("&$&$")) {
            return "";
        }
        char[] charArray = in.toCharArray();

        StringBuffer strBuff = new StringBuffer();

        boolean convertFlag = true;
        int len = charArray.length;
        for (int i = 0; i < len; i++) {
            if ( charArray[i] == '&' && i+1 < len &&charArray[i+1] == '$' )
            {
            convertFlag = !convertFlag;
            i += 2;
        }
        if ( i >= len )
            break;
        if ( convertFlag )
        {
            switch (charArray[i]) {
                case '\r':
                    break;
                case '&':
                    if ( i+1 < len &&charArray[i+1] == '$' )
                    {
                        i += 1;
                        convertFlag = !convertFlag;
                        continue;
                    }
                    strBuff.append("&");
                    break;
                default:
                    strBuff.append(charArray[i]);
            }
        }
        else
            strBuff.append(charArray[i]);
        }
        return strBuff.toString();

    }

}
