package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 碎片版本表
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@TableName("tb_split_version")
@ApiModel(value="SplitVersion对象", description="碎片版本表")
public class SplitVersion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "碎片ID")
    @TableField("splitid")
    private String splitid;
    
    @ApiModelProperty(value = "模板内容")
    @TableField("modelcontent")
    private String modelcontent;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private Date createtime;
    
    @ApiModelProperty(value = "发布人")
    @TableField("publisher")
    private String publisher;
    
    @ApiModelProperty(value = "更新人")
    @TableField("updater")
    private String updater;
    
    @ApiModelProperty(value = "更新时间")
    @TableField("updatetime")
    private Date updatetime;

    public String getModelcontent() {
        return modelcontent;
    }
    public String getPublisher()
    {
        return publisher;
    }
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }
    public String getUpdater()
    {
        return updater;
    }

    public String getSplitid()
    {
        return splitid;
    }
    public void setSplitid(String splitid)
    {
        this.splitid = splitid;
    }
    public void setUpdater(String updater)
    {
        this.updater = updater;
    }


    public Date getUpdatetime()
    {
        return updatetime;
    }


    public void setUpdatetime(Date updatetime)
    {
        this.updatetime = updatetime;
    }


    public SplitVersion setModelcontent(String modelcontent) {
        this.modelcontent = modelcontent;
        return this;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public SplitVersion setCreatetime(Date createtime) {
        this.createtime = createtime;
        return this;
    }

    @Override
    public String toString() {
        return "SplitVersion{" +
        "modelcontent=" + modelcontent +
        ", createtime=" + createtime +
        "}";
    }
}
