package cn.people.service.view.obj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

public class DateTool {
	public static SimpleDateFormat sqlShortDateFormat = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat sqlLongDateFormat = new SimpleDateFormat("yyyyMMddHHmmsssss");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static SimpleDateFormat longTDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static SimpleDateFormat longtrsDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	public static SimpleDateFormat longCnDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	public static SimpleDateFormat longCstDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
	public static long ONE_DAY_MILLISECOND = 24 * 60 * 60 * 1000;// 1天的毫秒数
	// the below note is added by lvlin;
	public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	

	public static SimpleDateFormat nasDateFormat = new SimpleDateFormat("yyyyMMdd");
}
