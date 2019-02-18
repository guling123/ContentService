package cn.people.service.view.tag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.people.commons.utils.SpringContextUtil;
import cn.people.entity.Channel;
import cn.people.entity.ModelSplit;
import cn.people.entity.ModelSplitProp;
import cn.people.entity.SplitProp;
import cn.people.entity.SplitPropValue;
import cn.people.service.IChannelService;
import cn.people.service.IContentChannelService;
import cn.people.service.IContentMediaService;
import cn.people.service.IContentService;
import cn.people.service.IModelSplitPropService;
import cn.people.service.IModelSplitService;
import cn.people.service.ISplitContentService;
import cn.people.service.ISplitPropService;
import cn.people.service.ISplitPropValueService;
import cn.people.service.ISplitService;
import cn.people.service.view.obj.CodeTool;
import cn.people.service.view.obj.CommonTool;
import cn.people.service.view.obj.ContentObj;
import cn.people.service.view.obj.DateUtil;
import cn.people.service.view.obj.Encode;
import cn.people.service.view.obj.SharedConstant;
import cn.people.service.view.obj.TagCachePojo;

public abstract class AbstractBaseTag {
	public int ttId = 0;
	public Integer nodeId ;
	public String contentId ;
	public int page = 0;
	public String s = "";
	public ContentObj contentObj = null;
	public String courl = "";
	public String verurl = "";
	
	IChannelService iChannelService = SpringContextUtil.getBean(IChannelService.class);
	IModelSplitPropService iModelSplitPropService = SpringContextUtil.getBean(IModelSplitPropService.class);
	ISplitPropService iSplitPropService = SpringContextUtil.getBean(ISplitPropService.class);
	ISplitPropValueService iSplitPropValueService  = SpringContextUtil.getBean(ISplitPropValueService.class);
	IContentService iContentService = SpringContextUtil.getBean(IContentService.class);
	IContentChannelService iContentChannelService = SpringContextUtil.getBean(IContentChannelService.class);
	IModelSplitService iModelSplitService =  SpringContextUtil.getBean(IModelSplitService.class);
	ISplitService iSplitService =  SpringContextUtil.getBean(ISplitService.class);
	ISplitContentService iSplitContentService = SpringContextUtil.getBean(ISplitContentService.class);
	IContentMediaService iContentMediaService = SpringContextUtil.getBean(IContentMediaService.class);
    SharedConstant sharedConstant = SpringContextUtil.getBean(SharedConstant.class);
	
	//标记版本 1适用于由java代码直接解释的标记,2适用于VTL语言的标记'
	public int version=1;
	//如果标记只是生成简单的字符串，那么就复写下边的2个方法isStringTag()，viewString()
	// isStringTag()返回true时标记解析类会调用viewString()方法来返回结果
	public boolean isStringTag(){
		return false;
	}
	//生成标记数据供模板引擎使用
	public String viewString(){
		return "";
	}
	//是否可缓存标记结果，true:可缓存 false:不可缓存
	public boolean canCacheView(){
		return false;
	}
	/**
	 * 如果标记结果可以缓存，此方法用来判断缓存的结果是否已过期
	 * @param TagCachePojo 当前已缓存的对象
	 * @return true为已过期需要重新生成view结果，false为没过期
	 */
	public boolean isExpired(TagCachePojo cache){
		return true;
	}
	//更新过期判断条件map，标记可将判断是否过期需要的条件存入conditionMap中
	public void updateConditionMap(TagCachePojo cache){
		
	}
	//所有标记属性值md5，用来验证属性值是否有改动，作为判断缓存是否过期的依据
	private String propValueMd5="";
	
	//是否缓存分页数据，true:缓存 false:不缓存 默认不缓存
	public boolean isCachePage(){
		return false;
	}
	
	//根据模板标记属性id(ttpId)查标记属性值
	public HashMap<String, Object> tagPropIdValueMap = new HashMap<String, Object>();
	//根据标记属性名查标记属性id
	public HashMap<String, String> tagPropNameValueMap = new HashMap<String, String>();

	//生成标记数据供模板引擎使用
	public abstract HashMap<String, Object> view();

	public void initTag(int ttId, Integer nodeId, String contentId) {
		this.ttId = ttId;
		this.nodeId = nodeId;
		this.contentId = contentId;
		initTagPropValue();
		String extendNodeIdStr = GetParam("继承_栏目ID");
		if(extendNodeIdStr!=null){
			if(!extendNodeIdStr.equals("")){
				if(extendNodeIdStr.equals("-1")){							    
					Channel channel=iChannelService.getById(nodeId);
					Channel parentChannel = iChannelService.getById(channel.getParentId());
					if(parentChannel!=null) {
					    this.nodeId = parentChannel.getLogicId();
					}
				}else{
	                Integer extendNodeId = CommonTool.getInt(extendNodeIdStr);
					this.nodeId = extendNodeId;
				}			
				int extendTTId=CommonTool.getInt(GetParam("继承_标记ID"));
				if(extendTTId>0){
					this.ttId = extendTTId;
				}
				if(!GetParam("继承_只继承内容").equals("1")){
					if(!extendNodeIdStr.equals("-1")||extendTTId>0){
						initTagPropValue();
					}
				}
			}
		}	
	}
	
	public void initTag(int ttId, Integer nodeId, String contentId,int pageNo) {
		initTag(ttId, nodeId, contentId);
		this.page = pageNo;
	}
	
	public void initTag(Integer nodeId, String contentId,String s,ContentObj content) {
		this.nodeId = nodeId;
		this.contentId = contentId;
		this.s = s;
		this.contentObj = content;
	}
	
	// 初始化标记属性值
	public void initTagPropValue() {
		List<String> ttpIdList=new ArrayList<String>();
		StringBuilder allPropValue=new StringBuilder();
		QueryWrapper<ModelSplitProp> queryWrapper = new QueryWrapper<ModelSplitProp>();
		queryWrapper.eq("model_split_logic_id", ttId);
		List<ModelSplitProp> values= iModelSplitPropService.list(queryWrapper);
		if(values!=null&&values.size()>0){
			for (ModelSplitProp templateTagProp:values) {
				tagPropIdValueMap.put("TTP"+templateTagProp.getId(), templateTagProp.getPropValue()==null?"":templateTagProp.getPropValue());
				SplitProp splitProp = iSplitPropService.getById(templateTagProp.getSplitPropId());
				if(splitProp==null)continue;
				tagPropNameValueMap.put(splitProp.getPropName(), "TTP"+templateTagProp.getId());				
				ttpIdList.add(templateTagProp.getId());
				allPropValue.append(templateTagProp.getPropValue()==null?"":templateTagProp.getPropValue());
			}
		}
		//获取在页面控制中用户设定的标记属性
		if(!StringUtils.isEmpty(nodeId)&&ttpIdList.size()>0){
		    QueryWrapper<SplitPropValue> spvQueryWrapper = new QueryWrapper<SplitPropValue>();
		    spvQueryWrapper.in("ttp_id", ttpIdList);
		    spvQueryWrapper.eq("channel_id", nodeId);
		    spvQueryWrapper.orderByAsc("ttp_id");
		    List<SplitPropValue> spvalues = iSplitPropValueService.list(spvQueryWrapper);
			if(spvalues!=null&&spvalues.size()>0){
				for (SplitPropValue tagPropValue:spvalues) {
					if(tagPropValue.getPropValue()!=null){
						tagPropIdValueMap.put("TTP"+tagPropValue.getTtpId(), tagPropValue.getPropValue()==null?"":tagPropValue.getPropValue());
						allPropValue.append(tagPropValue.getPropValue());
					}					
				}
			}
		}
		propValueMd5=CodeTool.getMD5(allPropValue.toString(), 0);
	}
	
	/**
	 * 获取标记属性值
	 * @param name 标记属性名
	 * @return
	 */
	public String GetParam(String name) {
		String ttpId = tagPropNameValueMap.get(name);
		if (ttpId != null) {
			Object tagPropIdValue = tagPropIdValueMap.get(ttpId);
			if (tagPropIdValue != null) {
				if (version == 2) {
					return tagPropIdValue.toString();
				} else {
					return tagPropIdValue.toString().trim();
				}
			}
		}
		return "";
	}
	
	public void addParamValue(String key, String value){
		String ttpId = tagPropNameValueMap.get(key);
		tagPropIdValueMap.put(ttpId, value);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	//根据s来格式化日期date,s的第1个字符值分别表示0:不处理日期返回"" 1:将日期项间插入指定的间隔符
	//s的第1个字符值大于2时表示以特定的语言处理日期，s的第1个字符后面的字符串是要格式化日期的形式，语法为java.text.SimpleDateFormat中定义
	//2:英文 3:法文 4:俄文 5:西班牙文 6:阿拉伯文 7:日文 8:朝鲜语  默认为中文
	public String GetDateStr(String s, java.sql.Date date) {
		if (s == null || s.length() == 0 || s.substring(0, 1).equals("0"))
			return "";
		String rt = "";
		String command = s.substring(0, 1);
		if (command.equals("1")) {
			s = s.substring(1);
			// 11111 : Year|Month|Day|Hour|Min
			// 值为０时不加间隔字符，为１时加入默认的中文日期间隔字符，为其它值时则将此值做为间隔符直接插入
			for (int i = 0; i < s.length(); i++) {
				String symbol = s.substring(i, i + 1);
				symbol = (symbol == null ? "0" : symbol);
				if (symbol.equals("0")) {
				} else {
					if (i == 0) {
						if (symbol.equals("1")) {
							rt += DateUtil.getYear(date) + "年";
						} else {
							rt += DateUtil.getYear(date) + symbol;
						}

					}
					if (i == 1) {
						if (symbol.equals("1")) {
							rt += DateUtil.getMonth(date) + "月";
						} else {
							rt += DateUtil.getMonth(date) + symbol;
						}

					}
					if (i == 2) {
						if (symbol.equals("1")) {
							rt += DateUtil.getDay(date) + "日";
						} else {
							rt += DateUtil.getDay(date) + symbol;
						}

					}
					if (i == 3) {
						if (symbol.equals("1")) {
							rt += DateUtil.getHour(date) + ":";
						} else {
							rt += DateUtil.getHour(date) + symbol;
						}
					}
					if (i == 4) {
						if (symbol.equals("1")) {
							rt += DateUtil.getMinute(date) + "";
						} else {
							rt += DateUtil.getMinute(date) + symbol;
						}

					}
				}
			}
		} else {
			Locale lang = Locale.CHINESE;
			if (command.equals("2")) {// '英文'
				lang = Locale.ENGLISH;
			} else if (command.equals("3")) {// '法文'
				lang = Locale.FRENCH;
			} else if (command.equals("4")) {// '俄文'
				lang = new Locale("ru");
			} else if (command.equals("5")) {// '西班牙文'
				lang = new Locale("es");
			} else if (command.equals("6")) {// '阿拉伯文'
				lang = new Locale("ar");
			} else if (command.equals("7")) {// '日文'
				lang = Locale.JAPANESE;
			} else if (command.equals("8")) {// '朝鲜语'
				lang = Locale.KOREAN;
			}
			SimpleDateFormat dateformat = new SimpleDateFormat(s.substring(1), lang);
			rt += dateformat.format(date);
		}
			// if (rt.length() > 0) rt = "[" + rt + "]";
		if (rt.length() > 0) {
			if (GetParam("时间参数").equals(""))
				rt = "[" + rt + "]";
			else
				rt = rt;
		}
		return rt;
	}
	
	public String GetChannelLink(Integer id, String name, String hrefprop) {
		return "<a href='" + sharedConstant.getChannelViewUrl(id) + "' " + hrefprop + ">" + Encode.htmlsp(name) + "</a>";
	}
	public String GetLink(String href, String name, String css) {
		if (css != null && css.length() > 0)
			return "<a href=\"" + href + "\" class=\"" + css + "\">" + name + "</a>";
		else
			return "<a href=\"" + href + "\">" + name + "</a>";
	}
	public String GetPageLink(Integer contentLogicID, String name, String css) {
		if (css != null && css.length() > 0)
			return "<a href=\"" + sharedConstant.getContentViewUrl(contentLogicID) + "\" class=\"" + css + "\" target=_blank>" + name + "</a>";
		else
			return "<a href=\"" + sharedConstant.getContentViewUrl(contentLogicID) + "\" target=_blank>" + name + "</a>";
	}
	public String getPropValueMd5() {
		return propValueMd5;
	}
	public void setPropValueMd5(String propValueMd5) {
		this.propValueMd5 = propValueMd5;
	}
	
}
