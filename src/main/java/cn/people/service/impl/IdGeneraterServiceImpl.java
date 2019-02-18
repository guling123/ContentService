package cn.people.service.impl;

import cn.people.entity.IdConfig;
import cn.people.entity.IdGenerater;
import cn.people.mapper.IdConfigMapper;
import cn.people.mapper.IdGeneraterMapper;
import cn.people.service.IIdGeneraterService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数值 ID 生成器 简易 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2019-01-10
 */
@Service
public class IdGeneraterServiceImpl extends ServiceImpl<IdGeneraterMapper, IdGenerater> implements IIdGeneraterService {

    @Autowired
    private IdGeneraterMapper idGeneraterMapper;
    
    @Autowired
    private IdConfigMapper idConfigMapper;
    /*
    * Title: getNextValue
    * @author shidandan
    * @date 2019年1月2日 上午10:12:11 
    *Description: 
    * @param entityName
    * @return 
    * @see cn.people.service.IIdGeneraterService#getNextValue(java.lang.String) 
    */
    @Override
    public Integer getNextValue(String entityName)
    {
        IdGenerater idGenerater=new IdGenerater();
        idGenerater.setEntityName(entityName);
        idGenerater=idGeneraterMapper.selectOne(new QueryWrapper<IdGenerater>(idGenerater));
        
        if(null==idGenerater) {
            IdConfig idConfig=idConfigMapper.selectById(entityName);
            
            idGenerater=new IdGenerater();
            idGenerater.setCurrentValue(idConfig.getStarter()+1);
            idGenerater.setEntityName(entityName);
            idGenerater.setScopeId(idConfig.getScopeFlg());
            
            idGeneraterMapper.insert(idGenerater);
            
            return idConfig.getStarter();
        }
        idGenerater.setCurrentValue(idGenerater.getCurrentValue()+1);
        idGeneraterMapper.updateById(idGenerater);
        return idGenerater.getCurrentValue();
    }
}
