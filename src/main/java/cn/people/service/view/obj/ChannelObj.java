package cn.people.service.view.obj;

import java.io.File;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.springframework.util.StringUtils;

import cn.people.commons.utils.SpringContextUtil;
import cn.people.entity.Channel;
import cn.people.entity.Site;
import cn.people.service.IChannelService;
import cn.people.service.ISiteService;
import cn.people.service.ISplitContentService;

/**
 * 用来保存栏目相关数据
 */

public class ChannelObj implements java.io.Serializable {

	private Integer nodeId;
	private Integer languageId;
	private String parentId;
	private String channelName;
	private Integer channelType;
	private String contentTemplateId;
	private String parentString;
	private String desc;
	private String filePath;
	private String storagePath;
	private String domain;
	private Integer statusRelease;
	private String siteId;
	private String createrId;
	private Channel channel;

	private ChannelUrl channelUrl = null;
	private LinkedHashMap<Integer, ChannelObj> parents;// 所有的父节点,最顶级的父节点在第一个
	ISiteService iSiteService = SpringContextUtil.getBean(ISiteService.class);
	IChannelService iChannelService = SpringContextUtil.getBean(IChannelService.class);
	

	/** full constructor */
	public ChannelObj(Channel channel) {
	    init(channel);
	}
	
	public ChannelObj(Integer logicId) {
	    Channel channel = iChannelService.getByLogicId(logicId);
	    init(channel);
	}

    private void init(Channel channel)
    {
        this.nodeId = channel.getLogicId();
        this.parentId = channel.getParentId();
        this.channelName = channel.getChannelName();
        this.channelType = channel.getDtype();
        this.contentTemplateId = channel.getContentModelId();
        this.parentString = channel.getParentString();
        this.desc = channel.getDescription();
        this.filePath = channel.getUrl();
        this.storagePath = channel.getUrl();
        this.domain = channel.getDomain();
        this.statusRelease = channel.getStatusRelease();
        this.createrId = channel.getCreaterId();
        this.siteId = channel.getSiteid();
        this.channel = channel;
    }

    public ChannelUrl getChannelUrl() {
	    ChannelUrl result = new ChannelUrl();
	    result.setNodeId(this.getNodeId());
	       
	    String domain = getDomain(this.channel);	    
	    String urlPath = getUrlPath(this.channel);
	    
	    result.setDomain(domain);
	    result.setUrlPath(urlPath);	    
	    
	    return result;
	}

	private String getDomain(Channel channel)
    {
        if(!StringUtils.isEmpty(channel.getDomain())){
            return channel.getDomain();
        }
        else if(StringUtils.isEmpty(channel.getParentId())//跟栏目
            ||"0".equals(channel.getParentId())) {
            Site site = iSiteService.getById(this.siteId);
            return site.getDomain();
        }
        else {
            Channel pChannel = iChannelService.getById(channel.getParentId());
            return getDomain(pChannel);
        }
    }

    private String getUrlPath(Channel channel)
    {
	    String urlPath = StringUtils.isEmpty(channel.getUrl())?"":channel.getUrl();
	    if(StringUtils.isEmpty(channel.getParentId())//跟栏目
	        ||"0".equals(channel.getParentId())//跟栏目
	        ||!StringUtils.isEmpty(channel.getDomain())//有域名的栏目
	        ) {
	        return "/"+urlPath;
	    }else {
	        Channel pChannel = iChannelService.getById(channel.getParentId());
	        return getUrlPath(pChannel)+"/"+urlPath;
	    }
    }

    public LinkedHashMap<Integer, ChannelObj> getParents() {
		if (parents == null) {
			parents = new LinkedHashMap<Integer, ChannelObj>();
			if (!StringUtils.isEmpty(parentId)) {
				String parentString = getParentString();
				if (parentString != null && parentString.endsWith(",")) {
					parentString = parentString.substring(0, parentString.length() - 1);
				}
				String [] channelLogicIds = parentString.split(",");
				List<Channel> tempParents = iChannelService.listByLogicIds(channelLogicIds);
				//因为查询出来的栏目顺序不一定是parentString中的顺序，所以需要重新排一下序
				if (tempParents.size() > 0) {
					LinkedHashMap<Integer, Channel> tempMap = new LinkedHashMap<Integer, Channel>();
					String ids[] = parentString.split(",");
					for (int i = 0; i < ids.length; i++) {
						Integer id = CommonTool.getInt(ids[i]);
						if (id > 0) {
							tempMap.put(id, null);
						}
					}
					for (Channel temp : tempParents) {
						tempMap.put(temp.getLogicId(), temp);
					}
					// 删除value为null的
					for (Iterator<Integer> iterator = tempMap.keySet().iterator(); iterator.hasNext();) {
						Integer key = (Integer) iterator.next();
						Channel value = tempMap.get(key);
						if (value != null) {
							parents.put(key,new ChannelObj(value));
						}
					}
				}
			}

		}
		return parents;
	}

	public String getSiteId()
    {
        return siteId;
    }

    public void setSiteId(String siteId)
    {
        this.siteId = siteId;
    }

    public Integer getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getChannelName() {
		return this.channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getChannelType() {
		return this.channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getContentTemplateId() {
		return this.contentTemplateId;
	}

	public void setContentTemplateId(String contentTemplateId) {
		this.contentTemplateId = contentTemplateId;
	}

	public String getParentString() {
		return this.parentString;
	}

	public void setParentString(String parentString) {
		if (parentString != null && parentString.endsWith(",")) {
			this.parentString = parentString.substring(0, parentString.length() - 1);
		} else {
			this.parentString = parentString;
		}
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStoragePath() {
		return this.storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getStatusRelease() {
		return this.statusRelease;
	}

	public void setStatusRelease(Integer statusRelease) {
		this.statusRelease = statusRelease;
	}

	public String getCreaterId() {
		return this.createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

}