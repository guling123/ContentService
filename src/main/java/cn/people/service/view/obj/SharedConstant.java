package cn.people.service.view.obj;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
* @ClassName: SharedConstant 
* @Description: 保存前后台都可以使用的系统常量 ,人民网cms移植的内容
* @author zhangchengchun
* @date 2019年2月1日 上午11:25:16 
*  
 */
@Component
public class SharedConstant {
	public static String SYSTEM_NAME="PEOPLE";
    public static int AutoPagination;
    //根据日期(yyyyMMdd)判断nas存储对应url规则
    public static boolean REDIS=false;
    public static int TAGLOG = 1000;
    //配置需要缓存的tag
    public static String CONTENT_TAG_NAME = "";
    public static String SYSTEM_ROOT="/cmspeople";
    
    //栏目预览url
    @Value("${model.channelView}")
    private String channelViewUrl="/view/channel/{id}/page";
    
    //栏目预览url
    @Value("${model.contentView}")
    private String contentViewUrl="/view/content/{id}/page";

    /**
     * 
    * @Title: 获取栏目预览url 
    * @author zhangchengchun
    * @date 2019年2月1日 上午11:46:22 
    * @Description:  获取栏目预览url  
    * @param  栏目逻辑id
    * @param @return  参数说明 栏目的url
    * @return String    返回类型 
    * @throws 
     */
    public String getChannelViewUrl(Integer id)
    {
        if (id == null || id == 0)
        {
            return "#";
        }
        return channelViewUrl.replace("{id}", id.toString());
    }

    /**
     * 
    * @Title: 获取稿件预览url 
    * @author zhangchengchun
    * @date 2019年2月1日 上午11:46:22 
    * @Description:  获取稿件预览url  
    * @param 栏目逻辑id
    * @param @return  参数说明 稿件的url
    * @return String    返回类型 
    * @throws 
     */
    public String getContentViewUrl(Integer id)
    {
        if (id == null || id == 0)
        {
            return "#";
        }
        return contentViewUrl.replace("{id}", id.toString());
    }

    
}
