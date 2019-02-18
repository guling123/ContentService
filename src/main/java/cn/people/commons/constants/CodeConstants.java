/**
 *   
 *
 * @Title: ModelBussinessExceptionConstants.java 
 * @Package cn.people.commons.constants 
 * @Description: 模板异常错误CODE常量类 
 * @author 高永久
 * @date 2018年12月04日 下午2:58:19 
 * @version V1.0   
 */
package cn.people.commons.constants;

/**
 *  
 *
 * @author gaoyongjiu
 * @ClassName: ModelBussinessExceptionConstants 
 * @Description: 内容异常错误CODE常量类 
 * @date 2018年12月04日 下午2:58:19 
 *   
 */
public class CodeConstants {

    /**
     * success
     */
    public static final String RESULT_OK = "200";

    /**
     * 参数错误
     */
    public static final String RESULT_ERR_PARAM = "001";
    /**
     * 操作错误
     */
    public static final String OPERATE_ERROR = "401";

    /**
     * 站点名称已经存在
     */
    public static final String SITENAME_EXIST = "501";

    /**
     * 站点不存在
     */
    public static final String SITE_NOT_EXIST = "502";

    /**
     * 内容不存在
     */
    public static final String CONTENT_NOT_EXIST = "503";
    
    /**
     * 碎片名称已经存在
     */
    public static final String SPLIT_NAME_EXIST = "601";
    
    /**
     * 模板不存在
     */
    public static final String MODEL_NOT_EXIST = "602";
    /**
     * 碎片不存在
     */
    public static final String SPLIT_NOT_EXIST = "603";
    
    /**
     * 碎片版本不存在
     */
    public static final String SPLIT_VERSION_NOT_EXIST = "604";
    /**
     * 模板存在
     */
    public static final String MODEL_EXIST = "605";
    
    /**
     * 内容不存在
     */
    public static final String CONTENT_STATUS_ERROR = "606";
    /**
     * 碎片内容不存在
     */
    public static final String SPLIT_CONTENT_NOT_EXIST = "607";
    
    /**
     * 模板碎片不存在
     */
    public static final String MODEL_SPLIT_NOT_EXIST = "608";
    
    /**
     * 碎片内容已存在
     */
    public static final String SPLIT_CONTENT_EXIST = "609";
    
    /**
     * 碎片属性不存在
     */
    public static final String SPLIT_PROP_NOT_EXIST = "610";
    
    /**
     * 栏目信息不存在
     */
    public static final String CHANNEL_NOT_EXIST = "611";
    
    /**
     * 栏目下有内容不能删除
     */
    public static final String CHANNEL_DEL_ERROR_EXIST_CONTENT = "612";
    
    /**
     * 碎片方案已存在
     */
    public static final String SPILT_MOD_EXIST = "613";
    
    /**
     * 碎片属性已存在
     */
    public static final String SPLIT_PROP_EXIST = "614";
    
    /**
     * 内容已删除
     */
    public static final String CONTENT_DELETE  = "615";
    
    /**
     * 不可以删除已经提审的内容
     */
    public static final String CONTENT_DELETE_ERROR  = "616";
    
    /**
     * 用户常用栏目已经存在
     */
    public static final String USER_CHANNEL_EXIST  = "617";
    
    /**
     * 栏目名称已被占用
     */
    public static final String CHANNEL_EXIST = "618";
}
