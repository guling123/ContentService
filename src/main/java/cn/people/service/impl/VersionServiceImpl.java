package cn.people.service.impl;

import cn.people.entity.Version;
import cn.people.mapper.VersionMapper;
import cn.people.service.IVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 版本前后关系表 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@Service
public class VersionServiceImpl extends ServiceImpl<VersionMapper, Version> implements IVersionService {

}
