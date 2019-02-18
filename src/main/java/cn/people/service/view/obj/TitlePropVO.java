package cn.people.service.view.obj;

import java.sql.Timestamp;

public class TitlePropVO implements java.io.Serializable{
	private String titlePropId;
	private Integer sort;
	private Timestamp publishTime;
	private Integer itemType;
	private String textBox;
	private String textUrl;
	private String mediaType;
	private String title;
	private String contentId;
    private Integer contentLogicId;
	private Integer cnodeId;
	private String linkUrl;
	private Timestamp inputDate;
	private Integer mediaCount;
	private String channelName;
	private Integer nodeId;
	private Integer ttId;
	private Short br;
	private String summary;
	private Timestamp createDate;	
	private String ext1;//Pretitle
	private String ext2;//Subtitle
	private String ext3;//Content_Prefix
	private String ext4;//Content_Postfix
	private Integer imgFloatType;
	
	
	public Integer getImgFloatType() {
		return imgFloatType;
	}
	public void setImgFloatType(Integer imgFloatType) {
		this.imgFloatType = imgFloatType;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public String getExt4() {
		return ext4;
	}
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	public String getTitlePropId() {
		return titlePropId;
	}
	public void setTitlePropId(String titlePropId) {
		this.titlePropId = titlePropId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Timestamp getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}
	public Integer getItemType() {
		return itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	public String getTextBox() {
		return textBox;
	}
	public void setTextBox(String textBox) {
		this.textBox = textBox;
	}
	public String getTextUrl() {
		return textUrl;
	}
	public void setTextUrl(String textUrl) {
		this.textUrl = textUrl;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public Integer getCnodeId() {
		return cnodeId;
	}
	public void setCnodeId(Integer cnodeId) {
		this.cnodeId = cnodeId;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public Integer getMediaCount() {
		return mediaCount;
	}
	public void setMediaCount(Integer mediaCount) {
		this.mediaCount = mediaCount;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public Integer getTtId() {
		return ttId;
	}
	public void setTtId(Integer ttId) {
		this.ttId = ttId;
	}
	public Short getBr() {
		return br;
	}
	public void setBr(Short br) {
		this.br = br;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
    public Integer getContentLogicId()
    {
        return contentLogicId;
    }
    public void setContentLogicId(Integer contentLogicId)
    {
        this.contentLogicId = contentLogicId;
    }
}
