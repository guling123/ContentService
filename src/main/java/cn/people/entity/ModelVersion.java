package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 模板版本表
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@TableName("tb_model_version")
@ApiModel(value="ModelVersion对象", description="模板版本表")
public class ModelVersion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板内容")
    @TableField("modelcontent")
    private String modelcontent;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private LocalDateTime createtime;

    public String getModelcontent() {
        return modelcontent;
    }

    public ModelVersion setModelcontent(String modelcontent) {
        this.modelcontent = modelcontent;
        return this;
    }
    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public ModelVersion setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
        return this;
    }

    @Override
    public String toString() {
        return "ModelVersion{" +
        "modelcontent=" + modelcontent +
        ", createtime=" + createtime +
        "}";
    }
}
