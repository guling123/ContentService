package cn.people.service.view.obj;

import java.sql.Timestamp;

public class AutoNewsList_Content {
	//自动新闻列表对应的Content信息数据结构
	public Integer nContentID;
	public String strNodeID;
	public String strTitle;
	public Integer nMediaCount;
	public Timestamp inputDate;
	public Timestamp displayDate;
	//public String strTTID_NODEID;
	
	public AutoNewsList_Content(){
		nContentID=-1;
		strNodeID="";
		strTitle="";
		nMediaCount=-1;
		inputDate=null;
		displayDate=null;
		//strTTID_NODEID=null;
	}
}
