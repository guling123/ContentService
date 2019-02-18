/**   
* @Title: SplitContentVO.java 
* @Package cn.people.controller.vo 
* @Description: 碎片内容
* @author shidandan
* @date 2018年12月21日 上午9:50:52 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: SplitContentVO 
* @Description: 碎片内容
* @author shidandan
* @date 2018年12月21日 上午9:50:52 
*  
*/
@ApiModel(value="碎片内容", description="碎片内容")
public class SplitContentVO implements Serializable
{

    private static final long serialVersionUID = 3968829252444893409L;
    @ApiModelProperty(value = "id")
    private String id;
    
    @ApiModelProperty(value = "文章id")
    private String contentid;
    
    @ApiModelProperty(value = "文章标题")
    private String title;
    
    @ApiModelProperty(value = "说明")
    private String textBox;
    
    @ApiModelProperty(value = "图片地址")
    private String linkUrl;
    
    @ApiModelProperty(value = "内容类型。1文章、2文字、3图片")
    private Integer dtype;
    
    @ApiModelProperty(value = "导入人")
    private String importer;
    
    @ApiModelProperty(value = "导入时间")
    private Date importTime;
    
    @ApiModelProperty(value = "状态。1隐藏，0显示")
    private Integer dstatus;

    public String getTextBox()
    {
        return textBox;
    }

    public Integer getDstatus()
    {
        return dstatus;
    }

    public void setDstatus(Integer dstatus)
    {
        this.dstatus = dstatus;
    }

    public void setTextBox(String textBox)
    {
        this.textBox = textBox;
    }

    public String getLinkUrl()
    {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl)
    {
        this.linkUrl = linkUrl;
    }

    public String getContentid()
    {
        return contentid;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setContentid(String contentid)
    {
        this.contentid = contentid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    public Integer getDtype()
    {
        return dtype;
    }

    public void setDtype(Integer dtype)
    {
        this.dtype = dtype;
    }

    public String getImporter()
    {
        return importer;
    }

    public void setImporter(String importer)
    {
        this.importer = importer;
    }

    public Date getImportTime()
    {
        return importTime;
    }

    public void setImportTime(Date importTime)
    {
        this.importTime = importTime;
    }
    
    //TODO 提供预览
}
