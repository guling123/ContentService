/**   
* @Title: ModelVO.java 
* @Package cn.people.controller.vo 
* @Description: 创建模板 
* @author shidandan
* @date 2018年12月19日 下午3:24:53 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ModelVO 
* @Description: 创建模板
* @author shidandan
* @date 2018年12月19日 下午3:24:53 
*  
*/
public class ModelVO implements Serializable
{

    private static final long serialVersionUID = 5273907488424378772L;
    
    @NotBlank(message="站点id不能为空")
    @ApiModelProperty(value = "站点id")
    private String siteid;

    @NotBlank(message="模板名称不能为空")
    @ApiModelProperty(value = "模板名称")
    private String modelname;

    @NotBlank(message="模板内容不能为空")
    @ApiModelProperty(value = "模板内容")
    private String modelcontent;

    @ApiModelProperty(value = "类型id")
    private String typeid;

    @ApiModelProperty(value = "创建人id ")
    private String createid;

    public String getSiteid()
    {
        return siteid;
    }

    public void setSiteid(String siteid)
    {
        this.siteid = siteid;
    }

    public String getModelname()
    {
        return modelname;
    }

    public void setModelname(String modelname)
    {
        this.modelname = modelname;
    }

    public String getModelcontent()
    {
        return modelcontent;
    }

    public void setModelcontent(String modelcontent)
    {
        this.modelcontent = modelcontent;
    }

    public String getTypeid()
    {
        return typeid;
    }

    public void setTypeid(String typeid)
    {
        this.typeid = typeid;
    }

    public String getCreateid()
    {
        return createid;
    }

    public void setCreateid(String createid)
    {
        this.createid = createid;
    }
}
