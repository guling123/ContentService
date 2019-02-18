package cn.people.service.view.obj;

public class ChannelTagValueRedis {
	public String get(String strNodeId,String strTT_ID){
	    //TODO zhangcc 处理redis逻辑
	    return "";
	    
		/*String strKey = "DateType:ChannelTagValue:NODE_ID:"+strNodeId+":TT_ID:"+strTT_ID;
		return redisServer.get(strKey);*/
	}
	public void set(String strNodeId,String strTT_ID,String strTagValue){
	    //TODO zhangcc 处理redis逻辑
		/*String strKey = "DateType:ChannelTagValue:NODE_ID:"+strNodeId+":TT_ID:"+strTT_ID;
		redisServer.Del(strKey);
		redisServer.set(strKey,strTagValue);*/
	}
	public void del(String strNodeId,String strTT_ID){
	    //TODO zhangcc 处理redis逻辑
		/*String strKey = "DateType:ChannelTagValue:NODE_ID:"+strNodeId+":TT_ID:"+strTT_ID;
		redisServer.Del(strKey);*/
	}
}
