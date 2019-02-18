package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 模板类型表
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@TableName("tb_model_type")
@ApiModel(value="ModelType对象", description="模板类型表")
public class ModelType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "站点id")
    @TableField("siteid")
    private String siteid;

    @ApiModelProperty(value = "类型名称")
    @TableField("typename")
    private String typename;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private LocalDateTime createtime;

    @ApiModelProperty(value = "状态， 1正常，2删除")
    @TableField("dstatus")
    private Integer dstatus;

    public String getSiteid() {
        return siteid;
    }

    public ModelType setSiteid(String siteid) {
        this.siteid = siteid;
        return this;
    }
    public String getTypename() {
        return typename;
    }

    public ModelType setTypename(String typename) {
        this.typename = typename;
        return this;
    }
    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public ModelType setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
        return this;
    }
    public Integer getDstatus() {
        return dstatus;
    }

    public ModelType setDstatus(Integer dstatus) {
        this.dstatus = dstatus;
        return this;
    }

    @Override
    public String toString() {
        return "ModelType{" +
        "siteid=" + siteid +
        ", typename=" + typename +
        ", createtime=" + createtime +
        ", dstatus=" + dstatus +
        "}";
    }
}
