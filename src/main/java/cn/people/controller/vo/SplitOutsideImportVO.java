/**   
* @Title: SplitOutsideImportVO.java 
* @Package cn.people.controller.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author shidandan
* @date 2018年12月19日 下午2:44:58 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: SplitOutsideImportVO 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author shidandan
* @date 2018年12月19日 下午2:44:58 
*  
*/
public class SplitOutsideImportVO implements Serializable
{

    private static final long serialVersionUID = -1086037771860327113L;
    @ApiModelProperty(value = "显示标题")
    private String showtitle;

    @ApiModelProperty(value = "添加人id")
    private String createrid;

    @ApiModelProperty(value = "外联地址")
    private String href;
    
    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    public String getShowtitle()
    {
        return showtitle;
    }

    public void setShowtitle(String showtitle)
    {
        this.showtitle = showtitle;
    }

    public String getCreaterid()
    {
        return createrid;
    }

    public void setCreaterid(String createrid)
    {
        this.createrid = createrid;
    }

    public String getHref()
    {
        return href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }
    
}