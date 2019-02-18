/**
 *   
 * 
 * @Title: ContentBussinessException.java 
 * @Package cn.people.commons.exceptions 
 * @Description: 内容服务异常 
 * @author gaoyongjiu
 * @date 2018年12月04日 上午11:32:50 
 * @version V1.0   
 */
package cn.people.commons.exceptions;

/**
 *  
 * 
 * @ClassName: ContentBussinessException 
 * @Description: 内容服务异常
 * @author gaoyongjiu
 * @date 2018年12月04日 上午11:32:50    
 */
public class ContentBussinessException extends Exception
{
    private static final long serialVersionUID = -4870036406541680645L;

    protected final String code;

    public ContentBussinessException(String code)
    {
        super("Image Business Exception");
        this.code = code;
    }

    public ContentBussinessException(String code, String msg)
    {
        super(msg);
        this.code = code;
    }

    public ContentBussinessException(String code, Throwable t)
    {
        super(t);
        this.code = code;
    }

    public ContentBussinessException(String code, String msg, Throwable t)
    {
        super(msg, t);
        this.code = code;
    }

    public String getCode()
    {
        return this.code;
    }
}
