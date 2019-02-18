package cn.people.controller.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * @author guling
 * @ClassName: ModelTypeVO
 * @Description: 模型类型返回vo(这里用一句话描述这个类的作用)
 * @date 2019/1/22 9:30
 */
public class ModelTypeVO implements Serializable
{
    @ApiModelProperty(value = "类型id")
    private String id;

    @ApiModelProperty(value = "类型名称")
    private  String typename;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTypename()
    {
        return typename;
    }

    public void setTypename(String typename)
    {
        this.typename = typename;
    }
}
