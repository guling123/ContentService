package cn.people.service.impl;

import cn.people.entity.ModelVersion;
import cn.people.mapper.ModelVersionMapper;
import cn.people.service.IModelVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 模板版本表 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@Service
public class ModelVersionServiceImpl extends ServiceImpl<ModelVersionMapper, ModelVersion> implements IModelVersionService {

}
