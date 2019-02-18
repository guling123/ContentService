package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 碎片方案表属性表对应原表TAG_MOD_PROP
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
@TableName("tb_split_mod_prop")
@ApiModel(value="SplitModProp对象", description="碎片方案表属性表对应原表TAG_MOD_PROP")
public class SplitModProp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "碎片id")
    @TableField("mod_id")
    private String modId;

    @ApiModelProperty(value = "属性ID")
    @TableField("prop_id")
    private String propId;

    @ApiModelProperty(value = "属性值")
    @TableField("prop_value")
    private String propValue;

    public String getModId() {
        return modId;
    }

    public SplitModProp setModId(String modId) {
        this.modId = modId;
        return this;
    }
    public String getPropId() {
        return propId;
    }

    public SplitModProp setPropId(String propId) {
        this.propId = propId;
        return this;
    }
    public String getPropValue() {
        return propValue;
    }

    public SplitModProp setPropValue(String propValue) {
        this.propValue = propValue;
        return this;
    }

    @Override
    public String toString() {
        return "SplitModProp{" +
        "modId=" + modId +
        ", propId=" + propId +
        ", propValue=" + propValue +
        "}";
    }
}
