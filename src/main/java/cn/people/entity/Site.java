package cn.people.entity;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 站点信息表
 * </p>
 *
 * @author shidandan
 * @since 2018-12-04
 */
@ApiModel(value="Site对象", description="站点信息表")
@TableName(value="tb_site")
public class Site implements Serializable  {


    private static final long serialVersionUID = -8032622377911840130L;
    @TableField(value = "id")
    private String  id;
    
    @ApiModelProperty(value = "站点名称")
    @TableField("sitename")
    private String sitename;
    
    @ApiModelProperty(value = "域名")
    @TableField("domain")
    private String domain;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private Date createtime;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getSitename() {
        return sitename;
    }

    public Site setSitename(String sitename) {
        this.sitename = sitename;
        return this;
    }
    public Date getCreatetime() {
        return createtime;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public Site setCreatetime(Date createtime) {
        this.createtime = createtime;
        return this;
    }

    @Override
    public String toString() {
        return "TbSite{" +
        "sitename=" + sitename +
        ", createtime=" + createtime +
        "}";
    }
}
