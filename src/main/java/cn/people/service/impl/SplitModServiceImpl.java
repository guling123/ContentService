package cn.people.service.impl;

import cn.people.commons.constants.CodeConstants;
import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.SplitModCreateVO;
import cn.people.entity.ModelSplitProp;
import cn.people.entity.Split;
import cn.people.entity.SplitMod;
import cn.people.entity.SplitModProp;
import cn.people.mapper.ModelSplitPropMapper;
import cn.people.mapper.SplitMapper;
import cn.people.mapper.SplitModMapper;
import cn.people.service.ISplitModPropService;
import cn.people.service.ISplitModService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 碎片方案表对应原表TAG_MOD 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
@Service
public class SplitModServiceImpl extends ServiceImpl<SplitModMapper, SplitMod> implements ISplitModService {

    @Autowired
    private SplitMapper splitMapper;
    
    @Autowired
    private ISplitModPropService iSplitModPropService;
    
    @Autowired
    private ModelSplitPropMapper modelSplitPropMapper;

    /**
     * 
    * Title: 新增碎片方案
    * @author shidandan
    * @date 2019年1月18日 下午7:58:22 
    * @Description: 新增碎片方案
    * @param splitId 碎片方案id
    * @param createVO 模板碎片
    * @return 成功返回true
    * @throws Exception 
    * @see cn.people.service.ISplitModService#createSplitMod(cn.people.controller.vo.SplitModCreateVO, java.lang.String)
     */
    @Override
    @Transactional
    public Boolean createSplitMod(SplitModCreateVO createVO,String splitId) throws Exception
    {
        Split split=splitMapper.selectById(splitId);
        
        if(null==split) {
            throw new ContentBussinessException(CodeConstants.SITE_NOT_EXIST, "碎片不村在");
        }
        SplitMod splitMod=new SplitMod();
        splitMod.setModName(createVO.getModName());
        List<SplitMod> modList=this.list(new QueryWrapper<SplitMod>(splitMod));
        if(CollectionUtils.isNotEmpty(modList)) {
            throw new ContentBussinessException(CodeConstants.SPILT_MOD_EXIST, "同名称的碎片方案已存在");
        }
        
        SplitMod entity=new SplitMod();
        entity.setCreateId(createVO.getCreateId());
        entity.setCreateTime(LocalDateTime.now());
        entity.setModName(createVO.getModName());
        entity.setSplitId(splitId);
        this.save(entity);
        
        ModelSplitProp modelSplitProp=new ModelSplitProp();
        modelSplitProp.setModelSplitId(createVO.getModelSplitId());
        List<ModelSplitProp> modelSplitPropList=modelSplitPropMapper.selectList(new QueryWrapper<ModelSplitProp>(modelSplitProp));
        if(CollectionUtils.isNotEmpty(modelSplitPropList)) {
            
            List<SplitModProp> entityList=new ArrayList<SplitModProp>();
            modelSplitPropList.forEach(sp ->{
                SplitModProp prop=new SplitModProp();
                prop.setModId(entity.getId());
                prop.setPropId(sp.getSplitPropId());
                prop.setPropValue(sp.getPropValue());
                entityList.add(prop);
            });
            
            return iSplitModPropService.saveBatch(entityList);
        }
        return null;
    }

}
