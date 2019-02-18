package cn.people.service.view.obj;

/**
 * <p>Title: NetaBeans </p>
 * <p>Description: Java Application Framework</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p> </p>
 * @author Fan Li
 * @version 1.0
 */


//TODO zhangcc 替换gwt-dev-2.4.0.jar 使用其他包的RE
import com.sun.org.apache.regexp.internal.RE;
import java.io.*;
import java.util.*;


public class Regular {

    public Regular() {
    }

    public static String TagRemove(String htmlContent) {
    try{
	 RE r = new RE("<.*\r*\n*.*?>");
	 return r.subst(htmlContent,"");
    } catch(Exception ex) {
	 return null;
    }
    }

    public static String AddTag(String htmlContent) {
	try{
	    RE r = new RE("&lt;TAG&gt;");
	    htmlContent = r.subst(htmlContent,"<TAG>");
	    r = new RE("&lt;/TAG&gt;");
	    htmlContent = r.subst(htmlContent,"</TAG>");
	    return htmlContent;
	} catch(Exception ex) {
	    return null;
	}
    }

    public static Vector MatchTagList(String templateContent) {
	Vector v = new Vector();
    try{
	 RE r = new RE("\\{TAG_([0-9]+)_TAG\\}");
	 int startIndex = 0;
	 while(r.match(templateContent,startIndex)) {
	     v.add(r.getParen(1));
	     startIndex = r.getParenEnd(0);
	 }
       } catch(Exception ex) {
	 return null;
       }
       return v;
    }

    public static Vector MatchNewsTagList(String templateContent) {
	Vector v = new Vector();
    try{
	 RE r = new RE("\\$\\{(.*?)\\}");
	 int startIndex = 0;
	 while(r.match(templateContent,startIndex)) {
	     v.add(r.getParen(1));
	     startIndex = r.getParenEnd(0);
	 }
       } catch(Exception ex) {
	 return null;
       }
       return v;
    }

    public static String ReplaceTag(String tagID, String replaceStr, String templateContent) {
	try{
	    RE r = new RE("\\{TAG_" + tagID + "_TAG\\}");
	    return r.subst(templateContent,replaceStr);
	} catch(Exception ex) {
	    return null;
	}
    }

    public static String ReplaceNewsTag(String s, String replaceStr, String templateContent) {
	try{
	    RE r = new RE("\\$\\{" + s + "\\}");
	    return r.subst(templateContent,replaceStr);
	} catch(Exception ex) {
	    return null;
	}
    }

    public static String ReplaceContent(String content) {
	try{
	    RE r = new RE("\\http://(([0-9]+.[0-9]+.[0-9]+.[0-9]+)|([0-9a-zA-Z]+.[0-9a-zA-Z]+))/mediafile/");
	    return r.subst(content,"/mediafile/");
	} catch(Exception ex) {
	    return null;
	}
    }

    public static String ReplaceTemplate(String content, String codeSet) {
	try{
	    RE r = new RE("/cms/template/ChannelView\\.jsp\\?id=([0-9]+)");
	    int startIndex = 0;
	    while(r.match(content,startIndex)) {
		int startP = r.getParenStart(0);
		int endP = r.getParenEnd(0);
		String nodeID = r.getParen(1);
		//String channelURL = (nodeID == null || nodeID.length()==0 ) ? "#" : "/" + codeSet + "/" + JspHelper.getNaviPath(nodeID,"NODE") + "index.htm";
		//content = content.substring(0,startP) + channelURL + content.substring(endP);
		startIndex = startP;
	    }
	    return content;
	} catch(Exception ex) {
	    return null;
	}
    }
}