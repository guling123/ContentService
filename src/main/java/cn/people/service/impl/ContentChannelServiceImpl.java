package cn.people.service.impl;

import cn.people.entity.ContentChannel;
import cn.people.mapper.ContentChannelMapper;
import cn.people.service.IContentChannelService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 内容栏目表 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@Service
public class ContentChannelServiceImpl extends ServiceImpl<ContentChannelMapper, ContentChannel> implements IContentChannelService {

    @Override
    public List<ContentChannel> getByContentId(String contentId)
    {
        QueryWrapper<ContentChannel> queryWrapper = new QueryWrapper<ContentChannel>();
        queryWrapper.eq("contentid", contentId);
        return list(queryWrapper);
    }

}
