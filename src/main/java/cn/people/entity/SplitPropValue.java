package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 碎片属性值表对应原TAG_PROP_VALUE
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
@TableName("tb_split_prop_value")
@ApiModel(value="SplitPropValue对象", description="碎片属性值表对应原TAG_PROP_VALUE")
public class SplitPropValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板碎片属性表id 可视化编辑时修改,对应tb_model_spilt_prop.id")
    @TableField("ttp_id")
    private String ttpId;

    @ApiModelProperty(value = "栏目id,对应原NODE_ID,对应表 tb_channel.id")
    @TableField("channel_id")
    private String channelId;

    @ApiModelProperty(value = "内容id")
    @TableField("prop_value")
    private String propValue;

    public String getTtpId() {
        return ttpId;
    }

    public SplitPropValue setTtpId(String ttpId) {
        this.ttpId = ttpId;
        return this;
    }
    public String getChannelId() {
        return channelId;
    }

    public SplitPropValue setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }
    public String getPropValue() {
        return propValue;
    }

    public SplitPropValue setPropValue(String propValue) {
        this.propValue = propValue;
        return this;
    }

    @Override
    public String toString() {
        return "SplitPropValue{" +
        "ttpId=" + ttpId +
        ", channelId=" + channelId +
        ", propValue=" + propValue +
        "}";
    }
}
