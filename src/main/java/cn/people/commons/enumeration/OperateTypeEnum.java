package cn.people.commons.enumeration;

/**
 * 文章操作
 * <p>
 * Created by wilson on 2018-12-05.
 */
public enum OperateTypeEnum {

    CREATE(1,"创建文章") ,        
    SAVE(2,"保存文章"),         
    SAVE_SUBMIT(3,"保存并提审") ,
    SUBMIT(4,"提审"),       
    FIRST_PASS(5,"一审通过"),
    FIRST_REJECT(6,"一审驳回"),
    SECOND_PASS(7,"二审通过"),        
    SECOND_REJECT(8,"二审驳回"),      
    PUBLISH(9,"发布"),            
    OFFLINE(10,"下线"),           
    STICKY_POST(11,"置顶"),        
    DELETE(12,"删除"),             
    RESTORE(13,"还原");            
    private int value;
    
    private String desc;

    OperateTypeEnum(int value,String desc) {
        this.value = value;
        this.desc=desc;
    }
    public static String getDesc(int value) {
        for (OperateTypeEnum day:OperateTypeEnum.values()) { 
            if(day.getValue()==value) {
                return day.getDesc();
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
}
