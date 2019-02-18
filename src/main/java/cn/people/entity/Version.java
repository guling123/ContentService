package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 版本前后关系表
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@TableName("tb_version")
@ApiModel(value="Version对象", description="版本前后关系表")
public class Version extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型. 1内容，2模板，3碎片，4碎片内容")
    @TableField("dtype")
    private Integer dtype;

    @ApiModelProperty(value = "当前版本id")
    @TableField("currentid")
    private String currentid;

    @ApiModelProperty(value = "上一版本id")
    @TableField("preid")
    private String preid;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private LocalDateTime createtime;

    public Integer getDtype() {
        return dtype;
    }

    public Version setDtype(Integer dtype) {
        this.dtype = dtype;
        return this;
    }
    public String getCurrentid() {
        return currentid;
    }

    public Version setCurrentid(String currentid) {
        this.currentid = currentid;
        return this;
    }
    public String getPreid() {
        return preid;
    }

    public Version setPreid(String preid) {
        this.preid = preid;
        return this;
    }
    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public Version setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
        return this;
    }

    @Override
    public String toString() {
        return "Version{" +
        "dtype=" + dtype +
        ", currentid=" + currentid +
        ", preid=" + preid +
        ", createtime=" + createtime +
        "}";
    }
}
