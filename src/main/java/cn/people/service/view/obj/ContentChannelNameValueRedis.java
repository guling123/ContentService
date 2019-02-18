package cn.people.service.view.obj;

public class ContentChannelNameValueRedis
{
    //TODO zhangcc 处理redis逻辑
    
    //private RedisServer redisServer;

    public ContentChannelNameValueRedis()
    {
        //redisServer = (RedisServer)SpringContextUtil.getBean("redisServer");
    }

    public String get(String strContentId, String strTT_ID)
    {
      /*  String strKey = "DateType:ContentChannelNameValue:CONTENT_ID:" + strContentId + ":TT_ID:"
                        + strTT_ID;
        System.out.println(strKey);
        return redisServer.get(strKey);*/
        return null;
    }

    public void set(String strContentId, String strTT_ID, String strTagValue)
    {
/*        String strKey = "DateType:ContentChannelNameValue:CONTENT_ID:" + strContentId + ":TT_ID:"
                        + strTT_ID;
        redisServer.Del(strKey);
        redisServer.set(strKey, strTagValue);*/
    }

    public void del(String strContentId, String strTT_ID)
    {
        /*String strKey = "DateType:ContentChannelNameValue:CONTENT_ID:" + strContentId + ":TT_ID:"
                        + strTT_ID;
        redisServer.Del(strKey);*/
    }
}
