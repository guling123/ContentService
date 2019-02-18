package cn.people.service.view.obj;

public class ContentTagValueRedis {
  //TODO zhangcc 处理redis逻辑
	//private RedisServer redisServer;	
	public ContentTagValueRedis(){
		//redisServer = (RedisServer) SpringContextUtil.getBean("redisServer");
	}
	public String get(String strContentId,String strTT_ID){
		/*String strKey = "DateType:ContentTagValue:CONTENT_ID:"+strContentId+":TT_ID:"+strTT_ID;
		return redisServer.get(strKey);*/
	    return null;
	}
	public void set(String strContentId,String strTT_ID,String strTagValue){
		/*String strKey = "DateType:ContentTagValue:CONTENT_ID:"+strContentId+":TT_ID:"+strTT_ID;
		redisServer.Del(strKey);
		redisServer.set(strKey,strTagValue);*/
	}
	public void del(String strContentId,String strTT_ID){
		/*String strKey = "DateType:ContentTagValue:CONTENT_ID:"+strContentId+":TT_ID:"+strTT_ID;
		redisServer.Del(strKey);*/
	}
}
