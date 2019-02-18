package cn.people.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;


/**
 * @author guling
 * @ClassName: ChannelCreateResultVO
 * @Description: 新增子目录返回结果
 * @date 2019/1/9 9:37
 */
@ApiModel(value = "新增子目录返回结果", description = "新增子目录返回结果")
public class ChannelCreateResultVO implements Serializable {

    private static final long serialVersionUID = -3199387888700614414L;

    @ApiModelProperty(value = "是否创建成功")
    private boolean isSuccess = false;
    
    @ApiModelProperty(value = "栏目ID")
    private String id;
    
    @ApiModelProperty(value = "站点ID")
    private String siteid;
    
    @ApiModelProperty(value = "父栏目id")
    private String parentId;
    
    
    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getSiteid()
    {
        return siteid;
    }

    public void setSiteid(String siteid)
    {
        this.siteid = siteid;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
    
    
}
