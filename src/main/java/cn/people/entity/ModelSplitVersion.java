package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 模板碎片版本关系表
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@TableName("tb_model_split_version")
@ApiModel(value="ModelSplitVersion对象", description="模板碎片版本关系表")
public class ModelSplitVersion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板版本id")
    @TableField("modelversionid")
    private String modelversionid;

    @ApiModelProperty(value = "模板id")
    @TableField("modelid")
    private String modelid;

    @ApiModelProperty(value = "碎片id")
    @TableField("splitid")
    private String splitid;

    @ApiModelProperty(value = "碎片版本id")
    @TableField("splitversionid")
    private String splitversionid;

    public String getModelversionid() {
        return modelversionid;
    }

    public ModelSplitVersion setModelversionid(String modelversionid) {
        this.modelversionid = modelversionid;
        return this;
    }
    public String getModelid() {
        return modelid;
    }

    public ModelSplitVersion setModelid(String modelid) {
        this.modelid = modelid;
        return this;
    }
    public String getSplitid() {
        return splitid;
    }

    public ModelSplitVersion setSplitid(String splitid) {
        this.splitid = splitid;
        return this;
    }
    public String getSplitversionid() {
        return splitversionid;
    }

    public ModelSplitVersion setSplitversionid(String splitversionid) {
        this.splitversionid = splitversionid;
        return this;
    }

    @Override
    public String toString() {
        return "ModelSplitVersion{" +
        "modelversionid=" + modelversionid +
        ", modelid=" + modelid +
        ", splitid=" + splitid +
        ", splitversionid=" + splitversionid +
        "}";
    }
}
