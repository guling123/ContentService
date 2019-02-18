/**   
* @Title: BaseEntity.java 
* @Package cn.people.entity 
* @Description: 基础DOMAIN
* @author shidandan
* @date 2018年11月28日 下午6:53:06 
* @version V1.0   
*/
package cn.people.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

/** 
* @ClassName: BaseEntity 
* @Description: 基础DOMAIN 
* @author shidandan
* @date 2018年11月28日 下午6:53:06 
*  
*/
@TableName("TEST_SEQUSER")
@KeySequence("SEQ_TEST")//类注解
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = -2096352596557150506L;
    @TableId(value = "ID", type = IdType.UUID)
    private String  id;
//    @Version
//    private Integer version;



    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }


}
