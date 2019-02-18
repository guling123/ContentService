package cn.people.service.impl;

import cn.people.entity.ContentOperateLog;
import cn.people.mapper.ContentOperateLogMapper;
import cn.people.service.IContentOperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 内容操作记录表 服务实现类
 * </p>
 *
 * @author gaoyongjiu
 * @since 2018-12-04
 */
@Service
public class ContentOperateLogServiceImpl extends ServiceImpl<ContentOperateLogMapper, ContentOperateLog> implements IContentOperateLogService {

}
