package cn.people.service.impl;

import cn.people.entity.IdConfig;
import cn.people.mapper.IdConfigMapper;
import cn.people.service.IIdConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ID 生成配置 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2019-01-10
 */
@Service
public class IdConfigServiceImpl extends ServiceImpl<IdConfigMapper, IdConfig> implements IIdConfigService {

}
