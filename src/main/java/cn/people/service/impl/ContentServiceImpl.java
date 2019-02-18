package cn.people.service.impl;


import cn.people.commons.constants.CodeConstants;
import cn.people.commons.enumeration.ContentQueryStatusEnum;
import cn.people.commons.enumeration.ContentStatusEnum;
import cn.people.commons.enumeration.OperateTypeEnum;
import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.commons.utils.EnumUtils;
import cn.people.controller.vo.*;
import cn.people.entity.*;
import cn.people.mapper.*;
import cn.people.request.CommonParamsSession;
import cn.people.service.IContentChannelService;
import cn.people.service.IContentService;

import cn.people.service.IIdGeneraterService;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p> 内容主体表 服务实现类 </p>
 *
 * @author shidandan
 * @since 2018-12-04
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements IContentService
{

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private ContentVersionMapper contentVersionMapper;

    @Autowired
    private ContentOperateLogMapper contentOperateLogMapper;

    @Autowired
    private IContentChannelService iContentChannelService;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private ContentMediaServiceImpl contentMediaServiceImpl;

    @Autowired
    private IIdGeneraterService iIdGeneraterService;

    /**
     * Title: createContent
     *
     * @param param
     *            内容信息保存入参
     * @return
     * @author shidandan
     * @date 2018年12月4日 下午4:11:28 
     * @Description: 保存内容
     */
    @Override
    @Transactional
    public ContentCreateResultVO createContent(ContentCreateVO param)
        throws Exception
    {
        // 效验站点是否存在
        Site site = siteMapper.selectById(param.getSiteid());

        if (null == site)
        {
            throw new ContentBussinessException(CodeConstants.SITE_NOT_EXIST, "站点不存在");
        }

        // 创建内容
        Content content = new Content();
        BeanUtils.copyProperties(param, content);
        content.setCreatetime(new Date());
        content.setDstatus(Integer.valueOf(param.getDstatus()));
        content.setSendtime(new Date(param.getSendtime()));
        content.setLogicId(iIdGeneraterService.getNextValue(
            Content.class.getAnnotation(TableName.class).value()));
        Boolean isSuccess = this.save(content);

        // 保存文章图片信息
        List<ContentMedia> contentMediaList = new ArrayList<>();
        // TODO 图片路径校验
        String[] split = param.getImageUrls().split(",");
        if (split.length != 0)
        {
            Arrays.asList(split).stream().forEach(s -> {
                ContentMedia contentMedia = new ContentMedia();
                BeanUtils.copyProperties(param, contentMedia);
                contentMedia.setMediaDesc(param.getDescription());
                contentMedia.setPath(s);
                File tempFile = new File(s.trim());
                contentMedia.setMediaName(tempFile.getName());
                contentMedia.setContentId(content.getId());
                contentMedia.setMediaType(String.valueOf(param.getDtype()));
                contentMedia.setUploadtype(1);
                contentMedia.setLevel(1);
                contentMedia.setUserId(param.getCreaterid());
                contentMedia.setContentInputDate(content.getCreatetime());
                contentMedia.setUrl(param.getImageurl());
                contentMedia.setHeadPic(0);
                contentMediaList.add(contentMedia);
            });
            contentMediaServiceImpl.saveBatch(contentMediaList);
        }

        // 保存内容版本信息
        ContentVersion contentVersion = new ContentVersion();
        BeanUtils.copyProperties(param, contentVersion);
        contentVersion.setContentid(content.getId());
        contentVersion.setCreatetime(new Date());
        contentVersion.setOperatorid(param.getCreaterid());
        contentVersion.setImagecount(String.valueOf(split.length));
        contentVersion.setOperatorname(param.getCreatename());
        contentVersionMapper.insert(contentVersion);

        // 更新内容得版本信息
        Content entity = new Content();
        entity.setId(content.getId());
        entity.setVersionid(contentVersion.getId());
        this.updateById(entity);

        // 保存内容栏目信息
        if (!CollectionUtils.isEmpty(param.getChannelids()))
        {
            List<ContentChannel> channelList = param.getChannelids().stream().map(channelid -> {
                ContentChannel contentChannel = new ContentChannel();
                contentChannel.setChannelid(channelid);
                contentChannel.setContentid(content.getId());
                contentChannel.setSiteid(param.getSiteid());
                return contentChannel;
            }).collect(Collectors.toList());
            iContentChannelService.saveBatch(channelList);
        }

        // 创建操作日志
        ContentOperateLog contentOperateLog = new ContentOperateLog();
        contentOperateLog.setContentId(content.getId());
        contentOperateLog.setCreateTime(new Date());
        contentOperateLog.setDtype(OperateTypeEnum.CREATE.getValue());
        contentOperateLog.setOperatorId(param.getCreaterid());
        contentOperateLog.setOperatorName(param.getCreatename());
        contentOperateLog.setVersionId(contentVersion.getId());
        contentOperateLogMapper.insert(contentOperateLog);

        ContentCreateResultVO result = new ContentCreateResultVO();
        result.setSuccess(isSuccess);
        result.setContentId(content.getId());
        result.setLogicId(content.getLogicId());
        return result;
    }

    /*
     * Title: updateContent
     * @author shidandan
     * @date 2018年12月25日 下午7:53:56 Description:
     * @param id
     * @param content
     * @return
     * @see cn.people.service.IContentService#updateContent(java.lang.String,
     * cn.people.controller.vo.ContentUpdateVO)
     */
    @Override
    @Transactional
    public ContentUpdateResultVO updateContent(String id, ContentUpdateVO param)
        throws Exception
    {
        // 效验站点是否存在
        Site site = siteMapper.selectById(param.getSiteid());

        if (null == site)
        {
            throw new ContentBussinessException(CodeConstants.SITE_NOT_EXIST, "站点不存在");
        }

        Content content = this.getById(id);

        if (null == content)
        {
            throw new ContentBussinessException(CodeConstants.CONTENT_NOT_EXIST, "文章不存在");
        }

        BeanUtils.copyProperties(param, content);
        content.setSendtime(new Date(param.getSendtime()));
        content.setDstatus(Integer.valueOf(param.getDstatus()));
        content.setUpdatetime(new Date());
        content.setId(id);
        this.updateById(content);

        // 保存文章图片信息
        List<ContentMedia> contentMediaList = new ArrayList<>();
        // TODO 图片路径校验
        String[] split = param.getImageUrls().split(",");
        Arrays.asList(split).stream().forEach(s -> {
            ContentMedia contentMedia = new ContentMedia();
            BeanUtils.copyProperties(param, contentMedia);
            contentMedia.setMediaDesc(param.getDescription());
            contentMedia.setPath(s);
            File tempFile = new File(s.trim());
            contentMedia.setMediaName(tempFile.getName());
            contentMedia.setContentId(content.getId());
            contentMedia.setMediaType(String.valueOf(param.getDtype()));
            contentMedia.setUploadtype(1);
            contentMedia.setLevel(1);
            contentMedia.setContentInputDate(content.getCreatetime());
            contentMedia.setUrl(param.getImageurl());
            contentMedia.setHeadPic(0);
            contentMediaList.add(contentMedia);
        });
        contentMediaServiceImpl.updateBatchById(contentMediaList);

        // 保存内容版本信息
        ContentVersion contentVersion = new ContentVersion();
        BeanUtils.copyProperties(param, contentVersion);
        contentVersion.setContentid(id);
        contentVersion.setCreatetime(new Date());
        contentVersion.setOperatorid(param.getOperatorid());
        contentVersion.setImagecount(String.valueOf(split.length));
        contentVersionMapper.insert(contentVersion);

        // 更新内容得版本信息
        Content entity = new Content();
        entity.setId(id);
        entity.setVersionid(contentVersion.getId());
        this.updateById(entity);

        // 保存内容栏目信息
        if (!CollectionUtils.isEmpty(param.getChannelids()))
        {
            Map<String, Object> columnMap = new HashMap<String, Object>();
            columnMap.put("contentid", content.getId());
            iContentChannelService.removeByMap(columnMap);
            List<ContentChannel> channelList = param.getChannelids().stream().map(channelid -> {
                ContentChannel contentChannel = new ContentChannel();
                contentChannel.setChannelid(channelid);
                contentChannel.setContentid(content.getId());
                contentChannel.setSiteid(param.getSiteid());
                return contentChannel;
            }).collect(Collectors.toList());
            iContentChannelService.saveBatch(channelList);
        }

        // 创建操作日志
        ContentOperateLog contentOperateLog = new ContentOperateLog();
        contentOperateLog.setContentId(content.getId());
        contentOperateLog.setCreateTime(new Date());
        contentOperateLog.setDtype(OperateTypeEnum.SAVE.getValue());
        contentOperateLog.setOperatorId(param.getOperatorid());
        contentOperateLog.setOperatorName(param.getOperatorname());
        contentOperateLog.setVersionId(contentVersion.getId());
        
        contentOperateLogMapper.insert(contentOperateLog);

        ContentUpdateResultVO result = new ContentUpdateResultVO();
        result.setContentId(id);
        result.setSuccess(true);
        result.setVersionId(contentVersion.getId());
        return result;
    }

    /**
     * @Title: 回收站列表查询 
     * @author shidandan
     * @date 2019年1月18日 下午7:21:58 
     * @Description: 回收站列表查询 
     * @param param
     *            回收站内容列表查询参数
     * @return 回收站列表
     * @see cn.people.service.IContentService#getContentRecycledPaged(cn.people.controller.vo.ContentRecycledListVO)
     */
    @Override
    public Page<ContentRecycledListResultVO> getContentRecycledPaged(ContentRecycledListVO param)
    {
        // 分页查询已删除的内容
        Content content = new Content();
        BeanUtils.copyProperties(param, content);

        QueryWrapper<Content> wrapper = new QueryWrapper<Content>();

        if (!StringUtils.isEmpty(param.getTitle()))
        {
            wrapper.like("title", param.getTitle());
        }
        if (null != param.getDeleteBeginTime())
        {
            wrapper.gt("updatetime", param.getDeleteBeginTime());
        }
        if (null != param.getDeleteEndTime())
        {
            wrapper.le("updatetime", param.getDeleteEndTime());
        }
        wrapper.setEntity(content);
        wrapper.eq("dstatus", 7);

        IPage<Content> contentList = this.page(new Page<>(param.getPageNo(), param.getPageSize()),
            wrapper);

        return getContentRecycledPagedResult(contentList);
    }

    /**
     * Title: 内容列表查询
     * 
     * @author gaoyongjiu
     * @date 2019年1月21日 上午9:47:01 
     * @Description: 内容列表查询
     * @param page
     *            内容
     * @param param
     *            内容查询参数
     * @return  内容列表 
     * @see cn.people.service.IContentService#getContentPaged(com.baomidou.mybatisplus.extension.plugins.pagination.Page,
     *      cn.people.controller.vo.ContentQueryVO)
     */
    @Override
    public Page<ContentListVO> getContentPaged(Page<ContentListVO> page, ContentQueryVO param)
    {

        if (!CollectionUtils.isEmpty(param.getCreateIds()))
        {
            param.setCreateIdsList(new ArrayList(param.getCreateIds()));
        }

        ContentPageQueryVO queryParam = new ContentPageQueryVO();
        BeanUtils.copyProperties(param, queryParam);

        // 封装状态
        if (null != param.getDstatus())
        {
            String[] dstatus = ContentQueryStatusEnum.getIdent(param.getDstatus()).split(",");
            queryParam.setDstatusSet(dstatus);
        }
        List<ContentListVO> list = this.baseMapper.getContentPaged(page, queryParam);

        if (!CollectionUtils.isEmpty(list))
        {
            // 发布人 发布时间
            Set<String> ids = list.stream().map(id -> {
                if (id.getDstatus() == 7)
                {
                    return id.getId();
                }
                return null;
            }).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(ids))
            {
                QueryWrapper<ContentOperateLog> queryWrapper = new QueryWrapper<ContentOperateLog>();
                queryWrapper.in("content_id", ids);
                queryWrapper.eq("dtype", 9);
                queryWrapper.orderByDesc("create_time");
                List<ContentOperateLog> logList = contentOperateLogMapper.selectList(queryWrapper);

                if (!CollectionUtils.isEmpty(logList))
                {
                    Map<String, String> logMap = new HashMap<String, String>();
                    logList.forEach(log -> {
                        if (!logMap.containsKey(log.getContentId()))
                        {
                            logMap.put(log.getContentId(), log.getOperatorName());
                        }
                    });

                    list.forEach(l -> {
                        if (logMap.containsKey(l.getId()))
                        {
                            l.setSendName(logMap.get(l.getId()));
                        }
                        // 封装状态
                        if (l.getDstatus() == 3)
                        {
                            l.setDstatus(2);
                        }
                        if (l.getDstatus() == 6)
                        {
                            l.setDstatus(4);
                        }
                    });
                }
            }
        }
        page.setRecords(list);

        return page;

    }

    /**
     * @Title: 封装查询回收站列表结果集
     * @author shidandan
     * @date 2019年1月21日 上午9:48:59 
     * @Description: 封装查询回收站列表结果集
     * @param contentList
     *            内容
     * @return  封装查询回收站列表结果集
     * @return Page<ContentRecycledListResultVO>    返回类型  @throws 
     */
    private Page<ContentRecycledListResultVO> getContentRecycledPagedResult(IPage<Content> contentList)
    {
        if (!CollectionUtils.isEmpty(contentList.getRecords()))
        {
            // 查询删除人，
            List<String> contentIds = new ArrayList<String>();
            contentList.getRecords().forEach(recycled -> {
                contentIds.add(recycled.getId());
            });

            QueryWrapper<ContentOperateLog> logWrapper = new QueryWrapper<ContentOperateLog>();
            logWrapper.in("contentid", contentIds);
            logWrapper.orderByDesc("createtime");
            List<ContentOperateLog> logs = contentOperateLogMapper.selectList(logWrapper);
            Map<String, String> deleterMap = new HashMap<String, String>();
            if (!CollectionUtils.isEmpty(logs))
            {
                logs.forEach(log -> {
                    if (StringUtils.isEmpty(deleterMap.get(log.getContentId())))
                    {
                        deleterMap.put(log.getContentId(), log.getOperatorId());
                    }
                });

            }
            // 封装结果集，将上面查询出来的删除人添加进去
            Page<ContentRecycledListResultVO> result = new Page<ContentRecycledListResultVO>();

            result.setTotal(contentList.getTotal());
            result.setSize(contentList.getSize());
            result.setCurrent(contentList.getCurrent());

            List<ContentRecycledListResultVO> records = new ArrayList<ContentRecycledListResultVO>();
            contentList.getRecords().forEach(record -> {
                ContentRecycledListResultVO vo = new ContentRecycledListResultVO();
                // vo.setAuthor(record.getAuthor());
                vo.setCreatetime(record.getCreatetime());
                vo.setDeletetime(record.getUpdatetime());
                vo.setDtype(record.getDtype());
                vo.setId(record.getId());
                vo.setSourceid(record.getSourceid());
                // vo.setTitle(record.getTitle());
                if (!StringUtils.isEmpty(deleterMap.get(record.getId())))
                {
                    vo.setDeleter(deleterMap.get(record.getId()));
                }
                records.add(vo);
            });
            result.setRecords(records);
            return result;
        }
        return null;
    }

    /**
     * Title: 删除内容
     * 
     * @author tianweisong
     * @date 2019年1月21日 上午9:44:59 
     * @Description: 删除内容
     * @param contentId
     *            内容id
     * @return 删除内容结果
     * @throws Exception
     * @see cn.people.service.IContentService#delContent(java.lang.String)
     */
    @Override
    public Boolean delContent(String contentId)
        throws Exception
    {
        ContentDelResultVO result = new ContentDelResultVO();
        result.setContentId(contentId);

        Content content = super.getById(contentId);
        if (content == null)
        {
            throw new ContentBussinessException(CodeConstants.CONTENT_NOT_EXIST, "文章不存在");
        }

        if (EnumUtils.equals(ContentStatusEnum.DELETED, content.getDstatus()))
        {

            throw new ContentBussinessException(CodeConstants.CONTENT_DELETE, "文章已删除");
        }
        content.setUpdatetime(new Date());

        if (content.getDstatus() == 1 || content.getDstatus() == 9)
        {
            content.setDstatus(ContentStatusEnum.DELETED.value());

            // 记录日志
            ContentOperateLog log = new ContentOperateLog();
            log.setContentId(contentId);
            log.setCreateTime(new Date());
            log.setDtype(OperateTypeEnum.DELETE.getValue());
            log.setOperatorId(CommonParamsSession.getParams().getOperatorid());
            contentOperateLogMapper.insert(log);

            return super.updateById(content);

        }
        else
        {
            throw new ContentBussinessException(CodeConstants.CONTENT_DELETE_ERROR,
                "不可以删除已经提审的内容");
        }
    }

    /**
     * Title: 从回收站还原内容
     * 
     * @author tianweisong
     * @date 2019年1月21日 上午9:44:22 
     * @Description: 从回收站还原内容
     * @param contentId
     *            内容id
     * @return  还原内容结果
     * @see cn.people.service.IContentService#restoreContent(java.lang.String)
     */
    @Override
    public ContentRestoreResultVO restoreContent(String contentId)
    {
        ContentRestoreResultVO result = new ContentRestoreResultVO();
        result.setContentId(contentId);

        Content content = super.getById(contentId);
        if (content == null)
        {
            result.setSuccess(false);
            result.setMsg("文章不存在");
            return result;
        }

        if (!EnumUtils.equals(ContentStatusEnum.DELETED, content.getDstatus()))
        {
            result.setSuccess(false);
            result.setMsg("文章已还原，或未被删除");
            return result;
        }

        content.setDstatus(ContentStatusEnum.DELETED.value());
        content.setUpdatetime(new Date());
        boolean isSuccess = super.updateById(content);
        if (!isSuccess)
        {
            result.setSuccess(false);
            result.setMsg("还原失败");
            return result;
        }

        // 记录日志
        ContentOperateLog log = new ContentOperateLog();
        log.setContentId(contentId);
        log.setCreateTime(new Date());
        log.setDtype(OperateTypeEnum.RESTORE.getValue());
        log.setOperatorId(CommonParamsSession.getParams().getOperatorid());
        contentOperateLogMapper.insert(log);

        result.setSuccess(true);
        result.setMsg("还原成功");
        return result;
    }

    /**
     * Title: 获取回收站内容详情
     * 
     * @author tianweisong
     * @date 2019年1月21日 上午9:42:49 
     * @Description: 获取回收站内容详情
     * @param contentId
     *            内容id
     * @return  还原内容结果
     * @see cn.people.service.IContentService#getContentForRecycled(java.lang.String)
     */
    @Override
    public ContentDetailVO getContentForRecycled(String contentId)
    {
        Content content = super.getById(contentId);
        if (content == null)
        {
            return null;
        }

        if (!EnumUtils.equals(ContentStatusEnum.DELETED, content.getDstatus()))
        {
            return null;
        }

        ContentDetailVO detail = new ContentDetailVO();
        BeanUtils.copyProperties(content, detail);

        if (StringUtils.isNotBlank(content.getVersionid()))
        {
            ContentVersion contentVersion = contentVersionMapper.selectById(
                content.getVersionid());
            if (contentVersion != null)
            {
                detail.setContent(contentVersion.getContent());
            }
        }

        return detail;
    }
    /**
     * Title: 获取内容详情 
     * 
     * @author shidandan
     * @date 2019年1月21日 上午9:41:40 
     * @Description: 获取内容详情 
     * @param id
     *            内容id
     * @return  内容详情 
     * @see cn.people.service.IContentService#getContentById(java.lang.String)
     */
    @Override
    public ContentDetailVO getContentById(String id)
    {
        Content content = this.getById(id);

        if (null != content)
        {
            ContentDetailVO result = new ContentDetailVO();
            BeanUtils.copyProperties(content, result);
            ContentVersion contentVersion = new ContentVersion();
            contentVersion.setContentid(id);
            contentVersion.setId(content.getVersionid());
            contentVersion = contentVersionMapper.selectOne(
                new QueryWrapper<ContentVersion>(contentVersion));

            if (null != contentVersion)
            {
                BeanUtils.copyProperties(contentVersion, result);
            }
            result.setId(id);
            ContentChannel contentChannel = new ContentChannel();
            contentChannel.setContentid(id);
            List<ContentChannel> channelList = iContentChannelService.list(
                new QueryWrapper<ContentChannel>(contentChannel));

            if (!CollectionUtils.isEmpty(channelList))
            {
                Set<String> channelIds = channelList.stream().map(channel -> {
                    return channel.getChannelid();
                }).collect(Collectors.toSet());
                List<Channel> list = channelMapper.selectBatchIds(channelIds);
                Map<String, String> channelMap = new HashMap<String, String>();
                List<String> channelids = new ArrayList<String>();
                list.forEach(c -> {
                    channelMap.put(c.getId(), c.getChannelName());
                    channelids.add(c.getId());
                });
                result.setChannels(channelMap);
                result.setChannelids(channelids);
            }

            Channel channel = channelMapper.selectById(content.getChannelid());
            result.setChannelLogicId(channel.getLogicId());

            return result;
        }
        return null;
    }

    /**
     * Title: 文章审核 
     * 
     * @author shidandan
     * @date 2019年1月21日 上午9:40:35 
     * @Description: 文章审核 
     * @param id
     *            文章id
     * @param auditVO
     *            文章审核
     * @return 成功返回true
     * @throws Exception
     * @see cn.people.service.IContentService#auditContent(java.lang.String,
     *      cn.people.controller.vo.ContentAuditVO)
     */
    @Override
    @Transactional
    public Boolean auditContent(String id, ContentAuditVO auditVO, Integer dstatus)
        throws Exception
    {
        Content content = super.getById(id);
        if (content == null)
        {
            throw new ContentBussinessException(CodeConstants.CONTENT_NOT_EXIST, "文章不存在");
        }

        if (!(content.getDstatus() == 2 || content.getDstatus() == 3))
        {

            throw new ContentBussinessException(CodeConstants.CONTENT_STATUS_ERROR, "文章状态不正确");
        }

        // 状态。1未提审，2已提审，3一审通过，4一审失败，5二审通过，6二审失败，7已发布,8 已删除,9已下线,10发布中
        if (dstatus == 1)
        {
            if (content.getDstatus() == 4 || content.getDstatus() == 5 || content.getDstatus() == 9
                || content.getDstatus() == 10)
            {

                throw new ContentBussinessException(CodeConstants.CONTENT_STATUS_ERROR, "文章状态不正确");
            }
            if (content.getDstatus() == 2)
            {
                content.setDstatus(3);
            }
            if (content.getDstatus() == 4)
            {
                content.setDstatus(5);
            }
        }
        else
        {
            if (content.getDstatus() == 1 || content.getDstatus() == 8)
            {

                throw new ContentBussinessException(CodeConstants.CONTENT_STATUS_ERROR, "文章状态不正确");
            }
            if (content.getDstatus() == 2)
            {
                content.setDstatus(5);
            }

            if (content.getDstatus() == 3)
            {
                content.setDstatus(6);
            }
        }

        Boolean isSuccess = this.updateById(content);

        // 创建操作日志
        ContentOperateLog contentOperateLog = new ContentOperateLog();
        contentOperateLog.setContentId(content.getId());
        contentOperateLog.setCreateTime(new Date());
        contentOperateLog.setDtype(content.getDstatus());
        if (dstatus == 2)
        {
            contentOperateLog.setReason(auditVO.getRejectReason());
        }
        contentOperateLog.setOperatorId(auditVO.getOperatorid());
        contentOperateLog.setOperatorName(auditVO.getOperatorname());
        isSuccess = contentOperateLogMapper.insert(contentOperateLog) > 0;
        return isSuccess;
    }

    /**
     * Title: 返回当前稿件的前一个已签发的稿件
     * 
     * @author zhangchengchun
     * @date 2019年1月21日 上午9:37:37 
     * @Description: 返回当前稿件的前一个已签发的稿件
     * @param node_id
     *            所属栏目的逻辑id
     * @param inputDate
     *            当前稿件创建日期
     * @param contentId
     *            当前稿件id
     * @return  内容详情
     * @see cn.people.service.IContentService#getPreContent(java.lang.Integer, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public ContentDetailVO getPreContent(Integer node_id, String inputDate, String contentId)
    {
        // TODO Auto-generated method stub
        ArrayList<ContentResultVO> contentList = contentMapper.findByNumberHQL(node_id, inputDate);
        if (contentList.size() != 0)
        {
            ContentResultVO content = contentList.get(0);
            ContentDetailVO contentDetailVO = new ContentDetailVO();
            BeanUtils.copyProperties(content, contentDetailVO);
            ContentChannel contentChannel = new ContentChannel();
            contentChannel.setContentid(contentId);
            List<ContentChannel> channelList = iContentChannelService.list(
                new QueryWrapper<ContentChannel>(contentChannel));

            if (!CollectionUtils.isEmpty(channelList))
            {
                List<String> channelIds = channelList.stream().map(channel -> {
                    return channel.getChannelid();
                }).collect(Collectors.toList());
                contentDetailVO.setChannelids(channelIds);
            }
            return contentDetailVO;
        }
        return null;
    }

    /**
     * Title: 返回当前稿件的后一个已签发的稿件
     * 
     * @author zhangchengchun
     * @date 2019年1月21日 上午9:36:14 
     * @Description: 返回当前稿件的后一个已签发的稿件
     * @param node_id
     *            所属栏目的逻辑id
     * @param inputDate
     *            当前稿件创建日期
     * @param contentId
     *            当前稿件id
     * @return 内容详情
     * @see cn.people.service.IContentService#getNextContent(java.lang.Integer, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public ContentDetailVO getNextContent(Integer node_id, String inputDate, String contentId)
    {
        // TODO Auto-generated method stub

        /*
         * queryString = "from POJO_ContentChannel where contentId !="+contentId+" and nodeId="
         * +node_id+"  and status=40 and inputDate>'"+inputDate+"' order by inputDate asc"; List
         * list2 = dao.findByNumberHQL(queryString, 1);
         */
        ArrayList<ContentResultVO> contentList = contentMapper.findByThreeParam(node_id, inputDate,
            contentId);
        if (contentList.size() != 0)
        {
            ContentResultVO content = contentList.get(0);
            ContentDetailVO contentDetailVO = new ContentDetailVO();
            BeanUtils.copyProperties(content, contentDetailVO);
            ContentChannel contentChannel = new ContentChannel();
            contentChannel.setContentid(contentId);
            List<ContentChannel> channelList = iContentChannelService.list(
                new QueryWrapper<ContentChannel>(contentChannel));

            if (!CollectionUtils.isEmpty(channelList))
            {
                List<String> channelIds = channelList.stream().map(channel -> {
                    return channel.getChannelid();
                }).collect(Collectors.toList());
                contentDetailVO.setChannelids(channelIds);
            }
            return contentDetailVO;
        }
        return null;
    }

    /**
     * Title: 查询自动新闻数据
     * 
     * @author zhangchengchun
     * @date 2019年1月21日 上午9:33:18 
     * @Description: 查询自动新闻数据
     * @param searchParam
     *            查询自动新闻查询条件
     * @return 自动新闻数据
     * @see cn.people.service.IContentService#listAutoNews(cn.people.controller.vo.ContentViewRequestVO)
     */
    @Override
    public ArrayList<ContentDetailVO> listAutoNews(ContentViewRequestVO searchParam)
    {
        // TODO Auto-generated method stub

        /*
         * String queryString =
         * "from POJO_ContentChannel where cacheDeleted=0 and status=40 and nodeId in("
         * +NodeSet+") "; queryString +=
         * " and inputDate >='"+startTime+"' and inputDate<='"+endTime+"'";
         * if(!this._notListContentSet.equals("")){ queryString +=
         * " and contentId not in("+_notListContentSet+")"; } String content_IdSet =
         * GetParam("新闻_自动新闻_过滤新闻ID"); if(!content_IdSet.equals("")){ queryString +=
         * " and contentId not in("+content_IdSet+") "; }
         * if(GetParam("新闻_自动新闻_是否只显示图片新闻").equals("1")){ queryString += " and mediaCount>0 "; }
         * if(GetParam("新闻_自动新闻_是否按显示时间排序").equals("1")){ queryString +=
         * " order by displayDate desc "; }else{ queryString += " order by inputDate desc "; } list
         * = dao.findByNumberHQL(queryString, (int)limit);
         */

        ContentViewSearchRequestVO contentViewSearchRequestVO = new ContentViewSearchRequestVO();
        // 将条件转成list放入对象中
        contentViewSearchRequestVO.setNodeList(
            Arrays.asList(searchParam.getChannelLogicIdInStr().split(",")));
        contentViewSearchRequestVO.setContentIdList(
            Arrays.asList(searchParam.getContentIdNotInStr1().split(",")));
        contentViewSearchRequestVO.setGetContentIdList1(
            Arrays.asList(searchParam.getContentIdNotInStr2().split(",")));
        // 查询
        ArrayList<ContentResultVO> contentList = contentMapper.getlistAutoNews(searchParam,
            contentViewSearchRequestVO);
        if (!CollectionUtils.isEmpty(contentList))
        {
            ArrayList<ContentDetailVO> contentDetailVOArrayList = new ArrayList<ContentDetailVO>();
            contentList.forEach(c -> {
                ContentDetailVO contentDetailVO = new ContentDetailVO();
                BeanUtils.copyProperties(c, contentDetailVO);
                contentDetailVOArrayList.add(contentDetailVO);
            });
            return contentDetailVOArrayList;
        }
        return null;
    }

    /**
     * Title: 根据查询条件组装ContentDetailVO数据
     * 
     * @author zhangchengchun
     * @date 2019年1月21日 上午9:32:13 
     * @Description: 根据查询条件组装ContentDetailVO数据
     * @param searchParam
     *            查询自动新闻查询条件
     * @return 内容详情
     * @see cn.people.service.IContentService#listContentDetails(cn.people.controller.vo.ContentDetailRequestVO)
     */
    @Override
    public List<ContentDetailVO> listContentDetails(ContentDetailRequestVO searchParam)
    {
        // TODO Auto-generated method stub

        /*
         * String queryString =
         * "FROM POJO_ContentChannel where status=40 and nodeId in("+NodeSet+") "; queryString +=
         * " and inputDate >='"+startTime1+"' and inputDate<='"+endTime1+"'"; List itemlist =
         * dao.findByNumberHQL(queryString,(int)limit);
         */
        ContentViewSearchRequestVO contentViewSearchRequestVO = new ContentViewSearchRequestVO();
        // 将条件转成list放入对象中
        contentViewSearchRequestVO.setNodeList(
            Arrays.asList(searchParam.getChannelLogicIdInStr().split(",")));
        // 查询
        ArrayList<ContentResultVO> contentList = contentMapper.listContentDetails(searchParam,
            contentViewSearchRequestVO);
        if (!CollectionUtils.isEmpty(contentList))
        {
            ArrayList<ContentDetailVO> contentDetailVOArrayList = new ArrayList<ContentDetailVO>();
            contentList.forEach(c -> {
                ContentDetailVO contentDetailVO = new ContentDetailVO();
                BeanUtils.copyProperties(c, contentDetailVO);
                contentDetailVOArrayList.add(contentDetailVO);
            });
            return contentDetailVOArrayList;
        }
        return null;
    }

    @Override
    public Boolean updateReleaseStatus(Integer logicId, Integer status)
        throws ContentBussinessException
    {
        QueryWrapper<Content> queryWrapper = new QueryWrapper<Content>();
        queryWrapper.eq("logic_id", logicId);
        Content content = getOne(queryWrapper);
        if (null == content)
        {
            throw new ContentBussinessException(CodeConstants.CONTENT_NOT_EXIST, "内容不存在");
        }
        content.setDstatus(status);
        return updateById(content);
    }

    @Override
    public ContentDetailVO getContentByLogicId(Integer contentLogicId)
    {
        
        QueryWrapper<Content> queryWrapper = new QueryWrapper<Content>();
        queryWrapper.eq("logic_id", contentLogicId);
        Content content = getOne(queryWrapper );
        String id = content.getId();

        if (null != content)
        {
            ContentDetailVO result = new ContentDetailVO();
            BeanUtils.copyProperties(content, result);
            ContentVersion contentVersion = new ContentVersion();
            contentVersion.setContentid(id);
            contentVersion.setId(content.getVersionid());
            contentVersion = contentVersionMapper.selectOne(
                new QueryWrapper<ContentVersion>(contentVersion));

            if (null != contentVersion)
            {
                BeanUtils.copyProperties(contentVersion, result);
            }
            result.setId(id);
            ContentChannel contentChannel = new ContentChannel();
            contentChannel.setContentid(id);
            List<ContentChannel> channelList = iContentChannelService.list(
                new QueryWrapper<ContentChannel>(contentChannel));

            if (!CollectionUtils.isEmpty(channelList))
            {
                Set<String> channelIds = channelList.stream().map(channel -> {
                    return channel.getChannelid();
                }).collect(Collectors.toSet());
                List<Channel> list = channelMapper.selectBatchIds(channelIds);
                Map<String, String> channelMap = new HashMap<String, String>();
                List<String> channelids = new ArrayList<String>();
                list.forEach(c -> {
                    channelMap.put(c.getId(), c.getChannelName());
                    channelids.add(c.getId());
                });
                result.setChannels(channelMap);
                result.setChannelids(channelids);
            }

            Channel channel = channelMapper.selectById(content.getChannelid());
            result.setChannelLogicId(channel.getLogicId());

            return result;
        }
        return null;
    }
}
