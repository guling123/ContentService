package cn.people.controller.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * @author guling
 * @ClassName: SplitPropListVO
 * @Description: 普通碎片属性列表返回参数(这里用一句话描述这个类的作用)
 * @date 2019/1/10 15:53
 */
public class SplitPropListVO implements Serializable
{

    private static final long serialVersionUID = 3378534316398907195L;

    @ApiModelProperty(value = "id")
    private String id;
    
    @ApiModelProperty(value = "属性名称")
    private String propName;

    @ApiModelProperty(value = "属性值")
    private String propValue;

    @ApiModelProperty(value = "描述")
    private String propDes;

    public String getPropDes()
    {
        return propDes;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setPropDes(String propDes)
    {
        this.propDes = propDes;
    }

    public String getPropName()
    {
        return propName;
    }

    public void setPropName(String propName)
    {
        this.propName = propName;
    }

    public String getPropValue()
    {
        return propValue;
    }

    public void setPropValue(String propValue)
    {
        this.propValue = propValue;
    }

    @Override public String toString()
    {
        return "SplitPropListVO{" + "propName='" + propName + '\'' + ", propValue='" + propValue
               + '\'' + '}';
    }
}
