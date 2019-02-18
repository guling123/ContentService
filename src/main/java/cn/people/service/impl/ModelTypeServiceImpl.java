package cn.people.service.impl;

import cn.people.commons.constants.CodeConstants;
import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.ModelTypeVO;
import cn.people.entity.ModelType;
import cn.people.entity.Site;
import cn.people.mapper.ModelTypeMapper;
import cn.people.mapper.SiteMapper;
import cn.people.service.IModelTypeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author guling
 * @ClassName: ModelTypeServiceImpl
 * @Description: 模型类型实现类(这里用一句话描述这个类的作用)
 * @date 2019/1/22 9:37
 */
@Service
public class ModelTypeServiceImpl extends ServiceImpl<ModelTypeMapper, ModelType>
    implements IModelTypeService
{
    @Autowired private ModelTypeMapper modelTypeMapper;

    @Autowired private SiteMapper siteMapper;

    @Override public List<ModelTypeVO> getTypeListBy(String siteid)
        throws Exception
    {
        // 效验站点是否存在
        //Site site = siteMapper.selectById(siteid);

       /* if (null == site)
        {
            throw new ContentBussinessException(CodeConstants.SITE_NOT_EXIST, "站点不存在");
        }*/
        //查询列表根据siteid
        ModelType modelType = new  ModelType();
        //modelType.setSiteid(siteid);
        List<ModelType> modelTypeList = modelTypeMapper.selectList(new QueryWrapper<>(modelType));
        //循环赋值
        List<ModelTypeVO> modelTyoeVOList = new ArrayList<>();
        modelTypeList.forEach(modelTyp -> {
            ModelTypeVO modelTypeVO = new ModelTypeVO();
            BeanUtils.copyProperties(modelTyp,modelTypeVO);
            modelTyoeVOList.add(modelTypeVO);
        });
        return modelTyoeVOList;
    }
}
