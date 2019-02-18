package cn.people.service.view.obj;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.people.controller.vo.ContentDetailVO;
import cn.people.entity.Content;
import cn.people.entity.ContentChannel;



public class AutoNewsList {
	//private RedisServer redisServer;
	
	public AutoNewsList(){
		//redisServer = (RedisServer) SpringContextUtil.getBean("redisServer");
	}
	
	public String getPropValueMd5(String strTT_ID,String strNodeID){
		//获得对应标记所有属性组成的MD5串
		//return redisServer.hGet("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID, "MD5");
	    return null;
	}
	
	public void setPropValueMd5(String strTT_ID,String strNodeID,String strValue){
		//redisServer.hSet("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID, "MD5", strValue);
	}
	
	public String getPropValueTime(String strTT_ID,String strNodeID){
		//获得对应标记所对应的新闻列表最近的Time值
		//return redisServer.hGet("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID, "TIME");
		return null;
	}
	
	public String getPropValueTime(String strTTID_NODEID){
		//获得对应标记所对应的新闻列表最近的Time值
		//return redisServer.hGet(strTTID_NODEID, "TIME");
		return null;
	}
	
	public void setPropValueTime(String strTT_ID,String strNodeID,String strValue){
		//redisServer.hSet("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID, "TIME", strValue);
	}
	
	public String getPropValueLimit(String strTT_ID,String strNodeID){
		//获得对应标记所有属性组成的MD5串
		//return redisServer.hGet("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID, "LIMIT");
		return null;
	}
	
	public String getPropValueLimit(String strTTID_NODEID){
		//获得对应标记所有属性组成的MD5串
		//return redisServer.hGet(strTTID_NODEID, "LIMIT");
	    return null;
	}
	
	public void setPropValueLimit(String strTT_ID,String strNodeID,String strValue){
		//redisServer.hSet("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID, "LIMIT", strValue);
	}
	
	public void delPropValue(String strTT_ID,String strNodeID){
		//清空对应TT_ID的MD5值及日期值
		//redisServer.Del("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID);
	}
	
	public String getNodeID(String strTT_ID,String strNodeID){
		//获得自动新闻列表'新闻_自动新闻_所属栏目ID'字符串
		//return redisServer.get("NODE_ID:TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID);
	    return null;
	}
	
	public String getNodeID(String strTTID_NODEID){
		//获得自动新闻列表'新闻_自动新闻_所属栏目ID'字符串
		//return redisServer.get("NODE_ID:"+strTTID_NODEID);
		return null;
	}
	
	public void setNodeID(String strTT_ID,String strNodeID,String strValue){
		//redisServer.set("NODE_ID:TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID, strValue);
	}
	
	public void delNodeID(String strTT_ID,String strNodeID){
		//清空对应TT_ID保存的node_id串
		//redisServer.Del("NODE_ID:TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID);
	}
	
	public void setTTID(String strNodeID,String strStrTT_ID){
		//设置NODE_ID---TT_ID Key-Value的值
		//redisServer.set("NODE_ID:"+strNodeID, strStrTT_ID);
	}
	
	public String getTTID(String strNodeID){
		//return redisServer.get("NODE_ID:"+strNodeID);
	    return null;
	}
	
	public void setRedisAutoNewsList(String strNodeId,String strTT_ID,List<Object> value){
		/*SerializeUtil s = new SerializeUtil();
		String strKey = "DateType:AutoNews:NODE_ID:"+strNodeId+":TT_ID:"+strTT_ID;
		byte [] byteKey = s.serialize(strKey);	
		byte [] byteValue = s.serializeList(value);
		redisServer.Del(byteKey);
		redisServer.set(byteKey, byteValue);*/
	}
	public void setRedisAutoNewsList(String strNodeId,String strTT_ID,String iAutoNewsTimeLimit,List<ContentDetailVO> value){
		/*SerializeUtil s = new SerializeUtil();
		String strKey = "DateType:AutoNews:NODE_ID:"+strNodeId+":TT_ID:"+strTT_ID+":AutoNewsTimeLimit:"+iAutoNewsTimeLimit;
		byte [] byteKey = s.serialize(strKey);	
		byte [] byteValue = s.serializeList(value);
		redisServer.Del(byteKey);
		redisServer.set(byteKey, byteValue);*/
	}
	
	public List getRedisAutoNewsList(String strNodeId,String strTT_ID){
		/*SerializeUtil s = new SerializeUtil();
		String strKey = "DateType:AutoNews:NODE_ID:"+strNodeId+":TT_ID:"+strTT_ID;
		byte [] byteKey = s.serialize(strKey);
		byte [] byteValue = redisServer.get(byteKey);
		List list = s.deserializeList(byteValue);
		return list;*/
	    return new ArrayList();
	}
	
	public List<ContentDetailVO> getRedisAutoNewsList(String strNodeId,String strTT_ID,String iAutoNewsTimeLimit){
		/*SerializeUtil s = new SerializeUtil();
		String strKey = "DateType:AutoNews:NODE_ID:"+strNodeId+":TT_ID:"+strTT_ID+":AutoNewsTimeLimit:"+iAutoNewsTimeLimit;
		byte [] byteKey = s.serialize(strKey);
		byte [] byteValue = redisServer.get(byteKey);
		List list = s.deserializeList(byteValue);
		return list;*/
	    return new ArrayList();
	}
	public void setRedisAutoKeywordNewsList(String strNodeId,String strTT_ID,List<Object> value){
		/*SerializeUtil s = new SerializeUtil();
		String strKey = "DateType:KeywordNews:NODE_ID:"+strNodeId+":TT_ID:"+strTT_ID;
		byte [] byteKey = s.serialize(strKey);	
		byte [] byteValue = s.serializeList(value);
		redisServer.Del(byteKey);
		redisServer.set(byteKey, byteValue);*/
	}
	public List getRedisAutoKeywordNewsList(String strNodeId,String strTT_ID){
		/*SerializeUtil s = new SerializeUtil();
		String strKey = "DateType:KeywordNews:NODE_ID:"+strNodeId+":TT_ID:"+strTT_ID;
		byte [] byteKey = s.serialize(strKey);
		byte [] byteValue = redisServer.get(byteKey);
		List list = s.deserializeList(byteValue);
		return list;*/
	    return new ArrayList();
	}
	
	
	public void setStrNodeID_TTID(String strStrNodeID,String strTT_ID){
		//根据传进来的Node_ID字符串,一一设置每个Node_ID所对应的TT_ID字符串
/*		if(strStrNodeID==null || strTT_ID==null)
			return;
		
		String[] arrayNodeID=strStrNodeID.split(",");
			
		for(int i=0;i<arrayNodeID.length;i++){
			if(arrayNodeID[i]!=null && arrayNodeID[i].equals("")==false){
				String strTemp=getTTID(arrayNodeID[i]);
				if(strTemp==null || strTemp.equals("")==true){
					setTTID(arrayNodeID[i],","+strTT_ID+",");
				}else{
					if(strTemp.indexOf(","+strTT_ID+",")==-1)
						setTTID(arrayNodeID[i],strTemp+strTT_ID+",");
				}
			}
		}*/
	}
	
	public void delNodeID_TTID(String strStrNodeID,String strTT_ID){
		//根据传进来的Node_ID字符串,一一设置每个Node_ID所对应的TT_ID字符串
/*		if(strStrNodeID==null || strTT_ID==null)
			return;
		
		String[] arrayNodeID=strStrNodeID.split(",");
			
		for(int i=0;i<arrayNodeID.length;i++){
			if(arrayNodeID[i]!=null && arrayNodeID[i].equals("")==false){
				String strTemp=getTTID(arrayNodeID[i]);
				if(strTemp==null || strTemp.equals("")==true){
					continue;
				}else{
					strTemp=strTemp.replace(","+strTT_ID+",",",");
					if(strTemp.equals(","))
						strTemp="";
					setTTID(arrayNodeID[i],strTemp);
				}
			}
		}*/
	}
	
	public void emptyStrNodeID_TTID(String strTT_ID,String strNodeID){
		//删除每个NODE_ID中所对应的strTT_ID值
		//delNodeID_TTID(strNodeID,strTT_ID);
	}
	
	public void emptyStrNodeID_TTID(String strTT_ID){
		//删除每个NODE_ID中所对应的strTT_ID值
		/*if(strTT_ID==null)
			return;
		
		List<String> listNodeID=getTTID_NODEID(strTT_ID);
		if(listNodeID==null || listNodeID.size()==0)
			return;
		
		for(int i=0;i<listNodeID.size();i++){
			delNodeID_TTID(listNodeID.get(i),strTT_ID);
		}*/
	}
	
	public List<String> getContentID(String strTT_ID,String strNodeID){
		//获得一个自动新闻列表标记所缓存的所有Content_ID信息
		//return redisServer.LRange("CONTENT_ID:TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID);
	    return new ArrayList();
	}
	
	public List<String> getContentID(String strTT_ID,String strNodeID,int nNumber){
		//获得一个自动新闻列表标记所缓存的指定数量的Content_ID信息
		//return redisServer.LRange("CONTENT_ID:TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID,nNumber);
	    return new ArrayList();
	}
	
	public void setContentID(String strTT_ID,String strNodeID,List<String> list){
		//redisServer.RPush("CONTENT_ID:TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID, list);
	}
	
	public void LPushContentID(String strTT_ID,String strNodeID,List<String> list){
		//redisServer.LPush("CONTENT_ID:TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID, list);
	}
	
	public void insertContentID(String strTTID_NODEID,String strContent_ID,String strInputDate){
		/*List<String> listContentID=null;
		String strContentID;
		int nLimit=0;
		String strLimit=null;
		String strGreatContentID=null;//用于记住要插入在哪个CONTENT_ID之前
		String strPropValueDate=null;
		Timestamp tPropValueDate=null,tInputDate=null;
		
		strPropValueDate=this.getPropValueTime(strTTID_NODEID);
		if(strPropValueDate==null || strPropValueDate.equals(""))
			return;
		//只有新闻的input_date早于getPropValueTime中的值才向相关自动新闻列表中插入
		tPropValueDate=Timestamp.valueOf(strPropValueDate);
		tInputDate=Timestamp.valueOf(strInputDate);
		if(tInputDate.after(tPropValueDate))
			return;
		
		strLimit=this.getPropValueLimit(strTTID_NODEID);//获得一个自动新闻列表的限制条数
		if(strLimit==null || strLimit.equals(""))
			return;
		
		nLimit=Integer.parseInt(strLimit)+30;
		
		listContentID=redisServer.LRange("CONTENT_ID:"+strTTID_NODEID,nLimit);//为了提高性能只取有效的记录数+30
		if(listContentID==null)
			return;
		
		for(int i=0;i<listContentID.size();i++){
			//这里新增对应关系的插入采用Content_ID从大到小方式进行(由于自动新闻列表一般按input_date或display_date排序显示,这样处理能保持
			//大部分情况下顺序正确)
			strContentID=listContentID.get(i);
			if(Integer.parseInt(strContent_ID)==Integer.parseInt(strContentID)){//如果有相等CONTENT_ID则直接返回
				return;
			}	
			if(Integer.parseInt(strContent_ID)>Integer.parseInt(strContentID) && strGreatContentID==null){
				strGreatContentID=strContentID;
			}
		}
		
		if(strGreatContentID!=null)
			redisServer.LInsert("CONTENT_ID:"+strTTID_NODEID, strGreatContentID, strContent_ID);
		//最悲观情况,比所有缓存中的ContentID值均小,则增加到末尾
		//redisServer.RPush("CONTENT_ID:"+strTTID_NODEID, strContent_ID);
		//*********注:超过有效条数+30以后的,即使新增的关系,也不加到content_id列表中
*/	}
	
	public void emptyContentID(String strTT_ID,String strNodeID){
		//清空自动新闻列表标记所缓存的所有Content_ID信息
		//redisServer.Del("CONTENT_ID:TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID);
	}
	
	public AutoNewsList_Content getContent(String strContent_ID){
		//根据content_id获得缓存中缓存的自动新闻列表一条数据的详细信息
		AutoNewsList_Content content=null;
		/*Map<String,String> hashMap=null;
	
		hashMap=redisServer.hGetAll("CONTENT_ID:"+strContent_ID);
		if(hashMap==null)
			return null;
		if(hashMap.size()<=0)
			return null;
		
		content=new AutoNewsList_Content();
		content.nContentID=Integer.parseInt(strContent_ID);
		content.strNodeID=hashMap.get("NODE_ID");
		content.strTitle=hashMap.get("TITLE");
		content.nMediaCount=Integer.parseInt(hashMap.get("MEDIA_COUNT"));
		content.inputDate=Timestamp.valueOf(hashMap.get("INPUT_DATE"));
		content.displayDate=Timestamp.valueOf(hashMap.get("DISPLAY_DATE"));
		//content.strTTID_NODEID=hashMap.get("TT_ID:NODE_ID");
*/		
		return content;
	}
	
	public void setContent(String strContent_ID,AutoNewsList_Content content){
		/*Map<String,String> hashMap=new HashMap<String,String>();
		
		if(content.strNodeID==null ||content.strNodeID.equals(""))
			return;
		if(content.strTitle==null ||content.strTitle.equals(""))
			return;
		if(content.inputDate==null ||content.inputDate.equals(""))
			return;
		if(content.displayDate==null||content.displayDate.equals(""))
			content.displayDate=content.inputDate;
		
		hashMap.put("NODE_ID",content.strNodeID);
		hashMap.put("TITLE",content.strTitle);
		hashMap.put("MEDIA_COUNT",content.nMediaCount.toString());
		
		DateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		hashMap.put("INPUT_DATE",dF.format(content.inputDate));
		hashMap.put("DISPLAY_DATE", dF.format(content.displayDate));
		
		//hashMap.put("TT_ID:NODE_ID", content.strTTID_NODEID);
		
		redisServer.hMSet("CONTENT_ID:"+strContent_ID, hashMap);*/
	}
	
	public void delContentNodeID(String strContent_ID,String strNodeID){
		/*//当删除一条新闻的content_channel关系时,从新闻的NODE_ID中删除相关信息
		//同时从各个缓存的自动新闻列表中删除
		//可能存在较高的时间复杂度,当预极端情况时有性能问题
		String strTemp=null;
		
		strTemp=redisServer.hGet("CONTENT_ID:"+strContent_ID, "INPUT_DATE");
		if(strTemp==null || strTemp.equals("")){
			//新闻仍未被缓存(已签发的新闻均会被缓存)
			return;
		}
		
		strTemp=redisServer.hGet("CONTENT_ID:"+strContent_ID, "NODE_ID");
		if(strTemp==null)
			return;
		
		strTemp=strTemp.replace(","+strNodeID+",",",");
		
		redisServer.hSet("CONTENT_ID:"+strContent_ID, "NODE_ID", strTemp);
		//从各个已缓存的Content_ID List缓存中删除已不存在的关系
		String strStrTTID=this.getTTID(strNodeID);
		if(strStrTTID==null)
			return;
		
		String[] arrayTTID=strStrTTID.split(",");//获得此栏目被哪些自动新闻标记使用的标记串信息
		for(int i=0;i<arrayTTID.length;i++){
			if(arrayTTID[i]!=null && arrayTTID[i].equals("")!=true)
				delContentIDFromList("TT_ID:"+arrayTTID[i]+":NODE_ID:"+strNodeID,strContent_ID,strNodeID);
		}*/
	}
	
	public void delContentIDFromList(String strTTID_NODEID,String strContent_ID,String strNodeID){
		/*//从缓存的List ContentID中删除已不存在的ContentID
		String strStringNodeID;
		
		strStringNodeID=getNodeID(strTTID_NODEID);
		if(strStringNodeID==null)
			return;
		//如果被删除的新闻栏目关系不属于此自动新闻列表则直接返回
		if(strStringNodeID.indexOf(","+strNodeID+",")==-1)
			return;
		
		delContentID(strTTID_NODEID,strContent_ID);	*/
	}
	
	public void delContentID(String strTTID_NODEID,String strContent_ID){
		//从List型的Content_ID列表中删除指定的content_ID
		//redisServer.LRem("CONTENT_ID:"+strTTID_NODEID, strContent_ID);
	}
	
	public void delContent(String strContent_ID){
		//redisServer.Del("CONTENT_ID:"+strContent_ID);
	}
	
	public void updateContentNodeID(String strContent_ID,String strNodeID){
		/*String strTemp=null,strInputDate=null;

		strInputDate=redisServer.hGet("CONTENT_ID:"+strContent_ID, "INPUT_DATE");
		if(strInputDate==null || strInputDate.equals("")){
			//新闻仍未被缓存(已签发的新闻均会被缓存)
			return;
		}
		
		strTemp=redisServer.hGet("CONTENT_ID:"+strContent_ID, "NODE_ID");
		if(strTemp==null)//增加第一个对应关系
			strTemp="";
		
		if(strTemp.equals("")==true || findNodeID(strTemp,strNodeID)==null){
			if(strTemp.equals("")==true)
				redisServer.hSet("CONTENT_ID:"+strContent_ID, "NODE_ID", ","+strNodeID+",");
			else
				redisServer.hSet("CONTENT_ID:"+strContent_ID, "NODE_ID", strTemp+strNodeID+",");
			//如果是新增的栏目关系,通过NODE_ID---TT_ID里面的值去一一去更新已有的缓存列表
			//此段代码可能会存在较大的性能隐患
			String strStrTTID=this.getTTID(strNodeID);
			if(strStrTTID==null)
				return;
			
			String[] arrayTTID=strStrTTID.split(",");//获得此栏目被哪些自动新闻标记使用的标记串信息
			for(int i=0;i<arrayTTID.length;i++){
				if(arrayTTID[i]!=null && arrayTTID[i].equals("")!=true)
					insertContentID("TT_ID:"+arrayTTID[i]+":NODE_ID:"+strNodeID,strContent_ID,strInputDate);
			}
			
		}*/
	}
	
	public void updateContent(Content content) throws RuntimeException{		
/*		String strContentID=null;
		
		if(content.getContentId()==null)//在新闻入库前,其ContentID可能为null值
			return;
		
		strContentID=content.getContentId().toString();
		//无论新闻是否缓存过,只要已签发则保存均进行更新
		if(content.getStatus()==Status.SIGNED.getValue()){
			redisServer.hSet("CONTENT_ID:"+strContentID, "TITLE", content.getTitle());
			redisServer.hSet("CONTENT_ID:"+strContentID, "MEDIA_COUNT", content.getMediaCount().toString());
			
			DateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			redisServer.hSet("CONTENT_ID:"+strContentID, "INPUT_DATE", dF.format(content.getInputDate()));
			redisServer.hSet("CONTENT_ID:"+strContentID, "DISPLAY_DATE", dF.format(content.getDisplayDate() == null ? content.getInputDate() : content.getDisplayDate()));
		}*/
	}
	
	public List<String> getTTID_NODEID(String strTT_ID){
		//return redisServer.LRange("TT_ID:NODE_ID:"+strTT_ID);
	    return new ArrayList<>();
	}
	
	public void setTTID_NODEID(String strTT_ID,String strNodeID){
		//redisServer.RPush("TT_ID:NODE_ID:"+strTT_ID, strNodeID);
	}
	
	public void emptyTTID_NODEID(String strTT_ID){
		//清空一个自动新闻列表标记缓存的List型NODE_ID值
		//redisServer.Del("TT_ID:NODE_ID:"+strTT_ID);
	}
	
	public void InsertTTID_NODEID(String strTT_ID,String strNodeID){
		//向TT_ID:NODE_ID--NODE_ID表中插入索引用数据
		/*List<String> listNodeID=null;
		
		listNodeID=getTTID_NODEID(strTT_ID);
		if(listNodeID==null){
			setTTID_NODEID(strTT_ID,strNodeID);
			return;
		}else{
			for(int i=0;i<listNodeID.size();i++){
				if(strNodeID.equals(listNodeID.get(i)))
					return;
			}
		}
		setTTID_NODEID(strTT_ID,strNodeID);*/
	}
	
	public void setContentChannel(String strTT_ID,String strNodeID,List<ContentChannel> listContentChannel){
		/*List<String> listContentID=new LinkedList<String>();
		String strContentID,strTime=null;
		AutoNewsList_Content content;
		Timestamp time=null;
		
		strTime=getPropValueTime(strTT_ID,strNodeID);
		for(int i=0;i<listContentChannel.size();i++){
			strContentID=listContentChannel.get(i).getContentId().toString();
			
			content=null;
			content=getContent(strContentID);
			if(content==null){
				content=new AutoNewsList_Content();
				
				content.nContentID=listContentChannel.get(i).getContentId();
				content.strNodeID=","+listContentChannel.get(i).getNodeId().toString()+",";
				content.strTitle=listContentChannel.get(i).getTitle();
				content.nMediaCount=listContentChannel.get(i).getMediaCount();
				content.inputDate=listContentChannel.get(i).getInputDate();
				content.displayDate=listContentChannel.get(i).getDisplayDate();
				//content.strTTID_NODEID="TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID;
			}else{
				//由于Redis中AutoNewsList.Content的数据为共享数据，特别是NODE_ID值和TT_ID:NODE_ID（注意：一个标记预览时，Content.Node_ID只会保存一个标记对应的）
				//所以要对content.strNodeID进行特殊处理，以便保存多个Node_ID构成的字符串,1001,2222,3122,
				if(content.strNodeID.indexOf(","+listContentChannel.get(i).getNodeId().toString()+",")==-1){
					content.strNodeID=content.strNodeID+listContentChannel.get(i).getNodeId().toString()+",";
				}
				
				//if(content.strTTID_NODEID.indexOf("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID)==-1){
				//	content.strTTID_NODEID=content.strTTID_NODEID+"-"+"TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID;
				//}
			}
			
			DateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//TT_ID---PROPVALUEMD5表的time字段保存最近的时间值
			
			if(strTime==null){
				setPropValueTime(strTT_ID,strNodeID,dF.format(content.inputDate));
				strTime=dF.format(content.inputDate);
				time=Timestamp.valueOf(strTime);
			}else{
				if(content.inputDate.after(time)){
					setPropValueTime(strTT_ID,strNodeID,dF.format(content.inputDate));
					time=content.inputDate;
				}
			}
				
			setContent(strContentID,content);
			listContentID.add(strContentID);
		}
		
		emptyContentID(strTT_ID,strNodeID);
		setContentID(strTT_ID,strNodeID,listContentID);
		
		emptyStrNodeID_TTID(strTT_ID,strNodeID);
		setStrNodeID_TTID(strNodeID, strTT_ID);
		
		InsertTTID_NODEID(strTT_ID, strNodeID);*/
	}
	
	public void setContentChannelDao(String strTT_ID,String strNodeID,List<ContentChannel> listContentChannel){
		/*List<String> listContentID=new LinkedList<String>();
		String strContentID,strTime=null;
		AutoNewsList_Content content;
		Timestamp time=null;
		
		strTime=getPropValueTime(strTT_ID,strNodeID);
		for(int i=0;i<listContentChannel.size();i++){
			strContentID=listContentChannel.get(i).getContentId().toString();
			
			content=null;
			content=getContent(strContentID);
			if(content==null){
				content=new AutoNewsList_Content();
				
				content.nContentID=listContentChannel.get(i).getContentId();
				//content.strNodeID=","+listContentChannel.get(i).getNodeId().toString()+",";
				content.strNodeID=getStrNodeID(strContentID,dbDao);
				content.strTitle=listContentChannel.get(i).getTitle();
				content.nMediaCount=listContentChannel.get(i).getMediaCount();
				content.inputDate=listContentChannel.get(i).getInputDate();
				content.displayDate=listContentChannel.get(i).getDisplayDate();
				//content.strTTID_NODEID="TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID;
			}else{
				//由于Redis中AutoNewsList.Content的数据为共享数据，特别是NODE_ID值和TT_ID:NODE_ID（注意：一个标记预览时，Content.Node_ID只会保存一个标记对应的）
				//所以要对content.strNodeID进行特殊处理，以便保存多个Node_ID构成的字符串,1001,2222,3122,
				if(content.strNodeID.indexOf(","+listContentChannel.get(i).getNodeId().toString()+",")==-1){
					content.strNodeID=content.strNodeID+listContentChannel.get(i).getNodeId().toString()+",";
				}
				
				//if(content.strTTID_NODEID.indexOf("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID)==-1){
				//	content.strTTID_NODEID=content.strTTID_NODEID+"-"+"TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID;
				//}
			}
			
			DateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//TT_ID---PROPVALUEMD5表的time字段保存最近的时间值
			
			if(strTime==null){
				setPropValueTime(strTT_ID,strNodeID,dF.format(content.inputDate));
				strTime=dF.format(content.inputDate);
				time=Timestamp.valueOf(strTime);
			}else{
				if(content.inputDate.after(time)){
					setPropValueTime(strTT_ID,strNodeID,dF.format(content.inputDate));
					time=content.inputDate;
				}
			}
				
			setContent(strContentID,content);
			listContentID.add(strContentID);
		}
		
		emptyContentID(strTT_ID,strNodeID);
		setContentID(strTT_ID,strNodeID,listContentID);
		InsertTTID_NODEID(strTT_ID, strNodeID);*/
	}
	
	private String getStrNodeID(String strContentID){
		//根据content_id获得所有新闻的栏目对应关系
		String strResult="";
		/*String strSql = "FROM POJO_ContentChannel where contentId="+strContentID;
		List<POJO_ContentChannel> list = dbDao.findHQL(strSql,Integer.parseInt(strContentID));
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				strResult+=","+list.get(i).getNodeId().toString();
			}
		}
		if(!strResult.equals(""))
			strResult+=",";*/
		
		return strResult;
	}
	
	public void LPushContentChannel(String strTT_ID,String strNodeID,List<ContentChannel> listContentChannel){
		/*List<String> listContentID=new LinkedList<String>();
		String strContentID,strTime=null;
		AutoNewsList_Content content;
		Timestamp time=null;
		
		if(listContentChannel==null)
			return;
		
		strTime=getPropValueTime(strTT_ID,strNodeID);
		if(strTime!=null && strTime.equals("")==false)
			time=Timestamp.valueOf(strTime);
		
		for(int i=listContentChannel.size()-1;i>=0;i--){//LPUSH需倒序压入,才能保证新闻列表按正确的顺序显示
			strContentID=listContentChannel.get(i).getContentId().toString();
			
			content=null;
			content=getContent(strContentID);
			if(content==null){
				content=new AutoNewsList_Content();
				
				content.nContentID=listContentChannel.get(i).getContentId();
				content.strNodeID=","+listContentChannel.get(i).getNodeId().toString()+",";
				content.strTitle=listContentChannel.get(i).getTitle();
				content.nMediaCount=listContentChannel.get(i).getMediaCount();
				content.inputDate=listContentChannel.get(i).getInputDate();
				content.displayDate=listContentChannel.get(i).getDisplayDate();
				//content.strTTID_NODEID="TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID;
			}else{
				//由于Redis中AutoNewsList.Content的数据为共享数据，特别是NODE_ID值和TT_ID:NODE_ID（注意：一个标记预览时，Content.Node_ID只会保存一个标记对应的）
				//所以要对content.strNodeID进行特殊处理，以便保存多个Node_ID构成的字符串,1001,2222,3122,
				if(content.strNodeID.indexOf(","+listContentChannel.get(i).getNodeId().toString()+",")==-1){
					content.strNodeID=content.strNodeID+listContentChannel.get(i).getNodeId().toString()+",";
				}
				
				//if(content.strTTID_NODEID.indexOf("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID)==-1){
				//	content.strTTID_NODEID=content.strTTID_NODEID+"-"+"TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID;
				//}
			}
			
			DateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//TT_ID---PROPVALUEMD5表的time字段保存最近的时间值
			

			if(strTime==null){
				setPropValueTime(strTT_ID,strNodeID,dF.format(content.inputDate));
				strTime=dF.format(content.inputDate);
				time=Timestamp.valueOf(strTime);
			}else{
				if(content.inputDate.after(time)){
					setPropValueTime(strTT_ID,strNodeID,dF.format(content.inputDate));
					time=content.inputDate;
				}
			}
				
			setContent(strContentID,content);
			listContentID.add(strContentID);
		}
		
		LPushContentID(strTT_ID, strNodeID, listContentID);
		//InsertTTID_NODEID(strTT_ID, strNodeID);
*/	}
	
	public void LPushContentChannelDao(String strTT_ID,String strNodeID,List<ContentChannel> listContentChannel){
		/*List<String> listContentID=new LinkedList<String>();
		String strContentID,strTime=null;
		AutoNewsList_Content content;
		Timestamp time=null;
		
		if(listContentChannel==null)
			return;
		
		strTime=getPropValueTime(strTT_ID,strNodeID);
		for(int i=0;i<listContentChannel.size();i++){
			strContentID=listContentChannel.get(i).getContentId().toString();
			
			content=null;
			content=getContent(strContentID);
			if(content==null){
				content=new AutoNewsList_Content();
				
				content.nContentID=listContentChannel.get(i).getContentId();
				//content.strNodeID=","+listContentChannel.get(i).getNodeId().toString()+",";
				content.strNodeID=getStrNodeID(strContentID,dbDao);
				content.strTitle=listContentChannel.get(i).getTitle();
				content.nMediaCount=listContentChannel.get(i).getMediaCount();
				content.inputDate=listContentChannel.get(i).getInputDate();
				content.displayDate=listContentChannel.get(i).getDisplayDate();
				//content.strTTID_NODEID="TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID;
			}else{
				//由于Redis中AutoNewsList.Content的数据为共享数据，特别是NODE_ID值和TT_ID:NODE_ID（注意：一个标记预览时，Content.Node_ID只会保存一个标记对应的）
				//所以要对content.strNodeID进行特殊处理，以便保存多个Node_ID构成的字符串,1001,2222,3122,
				if(content.strNodeID.indexOf(","+listContentChannel.get(i).getNodeId().toString()+",")==-1){
					content.strNodeID=content.strNodeID+listContentChannel.get(i).getNodeId().toString()+",";
				}
				
				//if(content.strTTID_NODEID.indexOf("TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID)==-1){
				//	content.strTTID_NODEID=content.strTTID_NODEID+"-"+"TT_ID:"+strTT_ID+":NODE_ID:"+strNodeID;
				//}
			}
			
			DateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//TT_ID---PROPVALUEMD5表的time字段保存最近的时间值
			

			if(strTime==null){
				setPropValueTime(strTT_ID,strNodeID,dF.format(content.inputDate));
				strTime=dF.format(content.inputDate);
				time=Timestamp.valueOf(strTime);
			}else{
				if(content.inputDate.after(time)){
					setPropValueTime(strTT_ID,strNodeID,dF.format(content.inputDate));
					time=content.inputDate;
				}
			}
				
			setContent(strContentID,content);
			listContentID.add(strContentID);
		}
		
		LPushContentID(strTT_ID, strNodeID, listContentID);
		InsertTTID_NODEID(strTT_ID, strNodeID);*/
	}
	
	public List<ContentChannel> getContentChannel(String strTT_ID,String strNodeID){
		/*List<String> listContentID=null;
		List<POJO_ContentChannel> listContentChannel=null;
		POJO_ContentChannel contentChannel=null;
		AutoNewsList_Content content=null;
		String strNodeIDTemp=null,strTemp=null;
		
		strNodeIDTemp=getNodeID(strTT_ID,strNodeID);
		if(strNodeIDTemp==null)
			return null;
		
		listContentID=getContentID(strTT_ID,strNodeID);
		if(listContentID==null)
			return null;
		
		listContentChannel=new LinkedList<POJO_ContentChannel>();
		for(int i=0;i<listContentID.size();i++){
			content=getContent(listContentID.get(i));
			if(content!=null){
				contentChannel=new POJO_ContentChannel();
				
				contentChannel.setContentId(content.nContentID);
				contentChannel.setTitle(content.strTitle);
				contentChannel.setMediaCount(content.nMediaCount);
				contentChannel.setInputDate(content.inputDate);
				contentChannel.setDisplayDate(content.displayDate);
				
				//判断Redis缓存中的新闻是否仍然属于对应的自动新闻标记提取的范围
				//因为新闻更改后(栏目所属关系)或标记提取的栏目ID更改后
				//Redis中缓存的数据可能已不属于此自动新闻列表
				strTemp=findNodeID(strNodeIDTemp,content.strNodeID);
				if(strTemp!=null){
					contentChannel.setNodeId(Integer.parseInt(strTemp));
					listContentChannel.add(contentChannel);
				}		
			}		
		}
		
		return listContentChannel;*/
	    return new ArrayList<>();
	}
	
	public List<ContentChannel> getContentChannel(String strTT_ID,String strNodeID,int nNumber){
		/*List<String> listContentID=null;
		List<POJO_ContentChannel> listContentChannel=null;
		POJO_ContentChannel contentChannel=null;
		AutoNewsList_Content content=null;
		String strNodeIDTemp=null,strTemp=null;
		
		if(nNumber<=0)
			return null;
		
		strNodeIDTemp=getNodeID(strTT_ID,strNodeID);
		if(strNodeIDTemp==null)
			return null;
		
		listContentID=getContentID(strTT_ID,strNodeID,nNumber);
		if(listContentID==null)
			return null;
		
		listContentChannel=new LinkedList<POJO_ContentChannel>();
		if(nNumber>listContentID.size())
			nNumber=listContentID.size();
		for(int i=0;i<nNumber;i++){
			content=getContent(listContentID.get(i));
			if(content!=null){
				contentChannel=new POJO_ContentChannel();
				
				contentChannel.setContentId(content.nContentID);
				contentChannel.setTitle(content.strTitle);
				contentChannel.setMediaCount(content.nMediaCount);
				contentChannel.setInputDate(content.inputDate);
				contentChannel.setDisplayDate(content.displayDate);
				
				//判断Redis缓存中的新闻是否仍然属于对应的自动新闻标记提取的范围
				//因为新闻更改后(栏目所属关系)或标记提取的栏目ID更改后
				//Redis中缓存的数据可能已不属于此自动新闻列表
				strTemp=findNodeID(strNodeIDTemp,content.strNodeID);
				if(strTemp!=null){
					contentChannel.setNodeId(Integer.parseInt(strTemp));
					listContentChannel.add(contentChannel);
				}		
			}		
		}
		
		return listContentChannel;*/
	    return new ArrayList<>();
	}
	
	public void emptyTTID(String strTT_ID){
		//清空自动新闻列表缓存的数据
		/*emptyStrNodeID_TTID(strTT_ID);
		
		List<String> listNodeID=getTTID_NODEID(strTT_ID);
		if(listNodeID==null)
			return;
		
		for(int i=0;i<listNodeID.size();i++){
			delPropValue(strTT_ID,listNodeID.get(i));
			delNodeID(strTT_ID,listNodeID.get(i));
			emptyContentID(strTT_ID,listNodeID.get(i));
			emptyTTID_NODEID(strTT_ID);
		}*/
	}
	
	public String findNodeID(String strSource,String strFind){
		/*String[] arrayFind=strFind.split(",");
		
		for(int i=0;i<arrayFind.length;i++){
			if(strSource.indexOf(","+arrayFind[i]+",")!=-1)
				return arrayFind[i];
		}*/
		
		return null;
	}
	
	public boolean MD5Equals(String strTT_ID,String strNodeID,String strMD5){
		//判断标属性的MD5字符串与缓存的MD5字符串是否相同
		/*String redisPropValueMd5=getPropValueMd5(strTT_ID, strNodeID);
		return strMD5.equals(redisPropValueMd5);*/
	    return true;
	}
}
