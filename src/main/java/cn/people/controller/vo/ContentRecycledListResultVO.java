/**   
* @Title: ContentRecycledListResultVO.java 
* @Package cn.people.controller.vo 
* @Description: 回收站列表查询 
* @author shidandan
* @date 2018年12月5日 下午2:05:26 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.Date;


import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ContentRecycledListResultVO 
* @Description: 回收站列表查询 
* @author shidandan
* @date 2018年12月5日 下午2:05:26 
*  
*/
public class ContentRecycledListResultVO implements Serializable
{

    private static final long serialVersionUID = 3316654580119880601L;
    @ApiModelProperty(value = "ID")
    private String id;
    
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "来源id")
    private String sourceid;

    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @ApiModelProperty(value = "删除时间")
    private Date deletetime;

    @ApiModelProperty(value = "删除人")
    private String deleter;

    @ApiModelProperty(value = "内容类型。1图文、2图集、3点播、4直播")
    private Integer dtype;


    public String getTitle()
    {
        return title;
    }

    public Date getDeletetime()
    {
        return deletetime;
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setDeletetime(Date deletetime)
    {
        this.deletetime = deletetime;
    }


    public String getDeleter()
    {
        return deleter;
    }


    public void setDeleter(String deleter)
    {
        this.deleter = deleter;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }


    public String getAuthor()
    {
        return author;
    }


    public void setAuthor(String author)
    {
        this.author = author;
    }


    public String getSourceid()
    {
        return sourceid;
    }


    public void setSourceid(String sourceid)
    {
        this.sourceid = sourceid;
    }


    public Date getCreatetime()
    {
        return createtime;
    }


    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }

    public Integer getDtype()
    {
        return dtype;
    }


    public void setDtype(Integer dtype)
    {
        this.dtype = dtype;
    }
    
    

}
