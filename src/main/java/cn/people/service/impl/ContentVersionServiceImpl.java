package cn.people.service.impl;

import cn.people.entity.ContentVersion;
import cn.people.mapper.ContentVersionMapper;
import cn.people.service.IContentVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 内容版本表 服务实现类
 * </p>
 *
 * @author gaoyongjiu
 * @since 2018-12-04
 */
@Service
public class ContentVersionServiceImpl extends ServiceImpl<ContentVersionMapper, ContentVersion> implements IContentVersionService {

}
