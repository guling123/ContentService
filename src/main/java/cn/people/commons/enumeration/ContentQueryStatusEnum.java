/**   
* @Title: ContentQueryStatusEnum.java 
* @Package cn.people.commons.enumeration 
* @Description: TODO(用一句话描述该文件做什么) 
* @author shidandan
* @date 2019年1月25日 上午11:11:39 
* @version V1.0   
*/
package cn.people.commons.enumeration;

/** 
* @ClassName: ContentQueryStatusEnum 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author shidandan
* @date 2019年1月25日 上午11:11:39 
*  
*/
public enum ContentQueryStatusEnum
{
    //状态。1未提审，2已提审，3一审通过，4一审失败，5二审通过，6二审失败，7已发布,9已下线,   10发布中8 已删除,

    CREATE(1,"未提审","1,") ,        
    AUDIT(2,"待审核","2,3,"),         
    REJECT(4,"已驳回","4,6,"),
    SECOND_PASS(5,"待发布","5,"),        
    PUBLISH(7,"已上线","7,"),            
    OFFLINE(9,"已下线","9,");           
    private int value;
    
    private String desc;
    
    private String ident;

    ContentQueryStatusEnum(int value,String desc,String ident) {
        this.value = value;
        this.desc=desc;
        this.ident=ident;
    }
    public static String getDesc(int value) {
        for (ContentQueryStatusEnum day:ContentQueryStatusEnum.values()) { 
            if(day.getValue()==value) {
                return day.getDesc();
            }
        }
        return null;
    }
    
    public static String getIdent(int value) {
        for (ContentQueryStatusEnum day:ContentQueryStatusEnum.values()) { 
            if(day.getValue()==value) {
                return day.getIdent();
            }
        }
        return null;
    }
    public String getDesc()
    {
        return desc;
    }

    public void setDes(String desc)
    {
        this.desc = desc;
    }
    public int getValue()
    {
        return value;
    }
    public void setValue(int value)
    {
        this.value = value;
    }
    public String getIdent()
    {
        return ident;
    }
    public void setIdent(String ident)
    {
        this.ident = ident;
    }
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}
