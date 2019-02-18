package cn.people.service.view.obj;

/**
 * <p>Title: NetaBeans </p>
 * <p>Description: Java Application Framework</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p> </p>
 * @author  Taylor Wang (wzp@herosys.com)
 * @version 1.0
 */


/**
 * <p>Title: HeroSYS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: HeroSYS.COM</p>
 * @author Taylor Wang
 * @version 1.0
 */

import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.text.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    public DateUtil() {
    }

    public static java.sql.Date getNowTime() {
	return new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }

    public static java.sql.Date getNowDate() {
	java.sql.Date now = getNowTime();
	String today = Date2Str(now, "yyyy-MM-dd");
	now = Str2Date(today + " 00:00:00");
	return now;
    }

    public static Date Str2Date(String date) {
	try {
	    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	    if (date.length()>10) {
		ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    }
	    java.util.Date d = ft.parse(date);
	    return new java.sql.Date(d.getTime());
	} catch (Exception ex) {
		log.debug("Parse Date Error!" + ex.getMessage());
	    return new Date(Calendar.getInstance().getTime().getTime());
	}
    }

    public static Date Str2Date1(String date) {
	try {
	    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	    if (date.length()>10) {
		ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    }
	    java.util.Date d = ft.parse(date);
	    return new java.sql.Date(d.getTime());
	} catch (Exception ex) {
		log.debug("Parse Date Error!" + ex.getMessage());
	    return null;
	}
    }

    public static Date Str2DateX(String date, String pattern) {
	try {
	    SimpleDateFormat ft = new SimpleDateFormat(pattern);
	    java.util.Date d = ft.parse(date);
      	    return new java.sql.Date(d.getTime());
	} catch (Exception ex) {
		log.debug("Parse Date Error!" + ex.getMessage());
	    return null;
	}
    }

    public static String Date2Str(java.sql.Date date,String pattern) {
	if( date == null ) return "";
	SimpleDateFormat ft = new SimpleDateFormat(pattern);
	return ft.format(date);
    }

    public static String Date2Str(java.util.Date date,String pattern) {
	if( date == null ) return "";
	SimpleDateFormat ft = new SimpleDateFormat(pattern);
	return ft.format(date);
    }

    public static String getYear(java.sql.Date date) {
	SimpleDateFormat ft = new SimpleDateFormat("yyyy");
	return ft.format(date);
    }

    public static String getMonth(java.sql.Date date) {
	SimpleDateFormat ft = new SimpleDateFormat("MM");
	return ft.format(date);
    }

    public static String getDay(java.sql.Date date) {
	SimpleDateFormat ft = new SimpleDateFormat("dd");
	return ft.format(date);
    }

    public static String getHour(java.sql.Date date) {
	SimpleDateFormat ft = new SimpleDateFormat("HH");
	return ft.format(date);
    }

    public static String getMinute(java.sql.Date date) {
	SimpleDateFormat ft = new SimpleDateFormat("mm");
	return ft.format(date);
    }

    public static void main(String[] arg) {
	//System.out.println( "" + Date2Str(getNowDate() , "yyyy-MM-dd HH:mm:ss") );
	String ss = "" + Date2Str(getNowDate() , "yyyy-MM-dd") + " 23:59:59";
	//System.out.println( ss );
        //LongToDate("dd","dd");
	//System.out.println( Date2Str( Str2DateX(ss, "yyyy-MM-dd HH:mm:ss") , "yyyy-MM-dd HH:mm:ss") );
    }
    
    public static Date asDate(Timestamp ts) {
    	if (ts!=null){
    		return new Date(ts.getTime());
    	}
    	return null;
    }
    


    public static  String LongToDate(String Longdate,String pattern){

                 if (Longdate == null || Longdate.length() == 0) {
                     Longdate = "0";
                 }

                 long millis = Long.parseLong(Longdate);
                 Date d = new Date(millis);
                 //d.setTime(millis);
                 SimpleDateFormat ft = new SimpleDateFormat(pattern);
                 return ft.format(d);

        }
    
    public static Timestamp StrToTimestamp(String date){
    	Timestamp ts = new Timestamp(System.currentTimeMillis());   
        try {   
            ts = Timestamp.valueOf(date);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
        return ts;
    }

    public static Timestamp dateToTimestamp(java.util.Date date)
    {
        String strDate = Date2Str(date, "yyyy-MM-dd HH:mm:ss");
        return StrToTimestamp(strDate);
    }

}