package cn.people.service.view.obj;

import java.util.List;

import cn.people.entity.ContentMedia;

public class ContentMediaRedis {
	//private RedisServer redisServer;	
	public ContentMediaRedis(){
		//redisServer = (RedisServer) SpringContextUtil.getBean("redisServer");
	}
	public void setRedisContentMediaList(String strContentId,List<ContentMedia> value){
		/*SerializeUtil s = new SerializeUtil();
		String strKey = "DateType:ContentMediaList:CONTENT_ID:"+strContentId;
		byte [] byteKey = s.serialize(strKey);	
		byte [] byteValue = s.serializeList(value);
		redisServer.Del(byteKey);
		redisServer.set(byteKey, byteValue);*/
	}
	public List<ContentMedia> getRedisContentMediaList(String strContentId){
		/*SerializeUtil s = new SerializeUtil();
		String strKey = "DateType:ContentMediaList:CONTENT_ID:"+strContentId;
		byte [] byteKey = s.serialize(strKey);
		byte [] byteValue = redisServer.get(byteKey);
		List list = s.deserializeList(byteValue);
		return list;*/
	    return null;
	}
}
