package cn.people.service.impl;

import cn.people.entity.ModelSplitProp;
import cn.people.entity.SplitProp;
import cn.people.mapper.ModelSplitPropMapper;
import cn.people.mapper.SplitPropMapper;
import cn.people.service.ISplitPropService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 碎片属性表对应原表TAG_PROP 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
@Service
public class SplitPropServiceImpl extends ServiceImpl<SplitPropMapper, SplitProp> implements ISplitPropService {

    @Autowired
    private ModelSplitPropMapper modelSplitPropMapper;
    
    /**
    * Title: 查询所有模板碎片未添加的碎片属性
    * @author shidandan
    * @date 2019年1月22日 下午7:58:19 
    * @Description: 查询所有模板碎片未添加的碎片属性
    * @param splitId 碎片ID
    * @param modelSpiltId 模板碎片ID
    * @return 
    * @see cn.people.service.ISplitPropService#getSplitProp(java.lang.String, java.lang.String) 
    */
    @Override
    public List<SplitProp> getSplitProp(String splitId, String modelSpiltId)
    {
        QueryWrapper<SplitProp> wrapper=new QueryWrapper<SplitProp>();
        wrapper.eq("split_id", splitId);
        
        ModelSplitProp entity=new ModelSplitProp();
        entity.setModelSplitId(modelSpiltId);
        entity.setState(1);
        List<ModelSplitProp> propList=modelSplitPropMapper.selectList(new QueryWrapper<ModelSplitProp>(entity));
        if(CollectionUtils.isNotEmpty(propList)) {
            Set<String> propIds=propList.stream().map(prop ->{return prop.getSplitPropId();}).collect(Collectors.toSet());
            wrapper.notIn("id", propIds);
        }
        
        return this.list(wrapper);
    }

}
