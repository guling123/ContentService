/**   
* @Title: ContentPageQueryVO.java 
* @Package cn.people.controller.vo 
* @Description: 内容分页查询参数
* @author shidandan
* @date 2019年1月25日 上午11:06:11 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.util.Set;

/** 
* @ClassName: ContentPageQueryVO 
* @Description: 内容分页查询参数 
* @author shidandan
* @date 2019年1月25日 上午11:06:11 
*  
*/
public class ContentPageQueryVO extends ContentQueryVO
{

    /** 
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    */ 
    private static final long serialVersionUID = 7046292825022662495L;
    /**
     * 状态集合
     */
    private String[] dstatusSet;
    public String[] getDstatusSet()
    {
        return dstatusSet;
    }
    public void setDstatusSet(String[] dstatusSet)
    {
        this.dstatusSet = dstatusSet;
    }
}
