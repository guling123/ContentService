package cn.people.service.impl;

import cn.people.commons.constants.CodeConstants;
import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.*;
import cn.people.entity.ModelSplit;
import cn.people.entity.ModelSplitProp;
import cn.people.entity.SplitModProp;
import cn.people.entity.SplitProp;
import cn.people.entity.SplitPropValue;
import cn.people.mapper.ModelSplitMapper;
import cn.people.mapper.ModelSplitPropMapper;
import cn.people.mapper.SplitModPropMapper;
import cn.people.mapper.SplitPropMapper;
import cn.people.mapper.SplitPropValueMapper;
import cn.people.service.IModelSplitPropService;
import cn.people.service.ISplitPropValueService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 
* @ClassName: ModelSplitPropServiceImpl 
* @Description: 模板碎片属性接口实现类 
* @author shidandan
* @date 2019年1月17日 下午8:40:37 
*  
 */
@Service
public class ModelSplitPropServiceImpl extends ServiceImpl<ModelSplitPropMapper, ModelSplitProp> implements IModelSplitPropService
{

    @Autowired
    private ModelSplitPropMapper modelSplitPropMapper;

    @Autowired
    private SplitPropValueMapper splitPropValueMapper;
    
    @Autowired
    private ISplitPropValueService iSplitPropValueService;
    
    @Autowired
    private ModelSplitMapper modelSplitMapper;
    
    @Autowired
    private SplitPropMapper splitPropMapper;
    
    @Autowired
    private SplitModPropMapper splitModPropMapper;

    /**
     * 
    * Title: 查询某个模板碎片下的模板碎片属性
    * @author shidandan
    * @date 2019年1月17日 下午8:36:24 
    * @Description: 查询某个模板碎片下的模板碎片属性
    * @param modelSpiltId 模板碎片ID
    * @return 模板碎片属性集合
    * @throws Exception 
    * @see cn.people.service.IModelSplitPropService#getModelSplitPropList(java.lang.String)
     */
    @Override
    public ModelSplitPropListParentVO getModelSplitPropList(String modelSpiltId) throws Exception
    {
        ModelSplitPropListParentVO modelSplitPropListParentVO = new ModelSplitPropListParentVO();
        ModelSplit modelSplit = modelSplitMapper.selectById(modelSpiltId);
        String splitId = modelSplit.getSplitId();
        if (splitId ==null)
        {
            throw new ContentBussinessException(CodeConstants.MODEL_SPLIT_NOT_EXIST,"模板碎片不存在");
        }
        QueryWrapper<ModelSplitProp> wrapper=new QueryWrapper<ModelSplitProp>();
        wrapper.eq("model_split_id", modelSpiltId);
        wrapper.eq("state", 1);
        List<ModelSplitProp> list=this.list(wrapper);

        if(!CollectionUtils.isEmpty(list)) {
            Set<String> splitPropIds=list.stream().map(m->{return m.getSplitPropId();}).collect(Collectors.toSet());

            List<SplitProp> splitPropList=splitPropMapper.selectBatchIds(splitPropIds);

            Map<String,SplitProp> splitMap=new HashMap<String,SplitProp>();
            splitPropList.forEach(p ->{
                splitMap.put(p.getId(), p);
            });
            ArrayList<ModelSplitPropListVO>  result=new ArrayList<ModelSplitPropListVO>();
            list.forEach(modelSplitProp ->{
                ModelSplitPropListVO vo=new ModelSplitPropListVO();
                BeanUtils.copyProperties(modelSplitProp, vo);

                if(null!=splitMap.get(modelSplitProp.getSplitPropId())) {
                    SplitProp splitProp=splitMap.get(modelSplitProp.getSplitPropId());
                    vo.setPropDes(splitProp.getPropDes());
                    vo.setPropName(splitProp.getPropName());
                    vo.setPropLevel(splitProp.getPropLevel());
                    vo.setSplitId(splitProp.getSplitId());
                }
                result.add(vo);
            });
            modelSplitPropListParentVO.setRecords(result);
            modelSplitPropListParentVO.setSplitName(modelSplit.getSplitName());
            return modelSplitPropListParentVO;
        }
        modelSplitPropListParentVO.setSplitName(modelSplit.getSplitName());
        return modelSplitPropListParentVO;

    }
    /**
     * 
    * Title: 创建模板碎片属性
    * @author shidandan
    * @date 2019年1月17日 下午8:37:32 
    * @Description: 创建模板碎片属性
    * @param modelSplitId 模板碎片ID
    * @param createVO 模板碎片属性值
    * @return 成功返回true
    * @throws Exception 
    * @see cn.people.service.IModelSplitPropService#createModelSplitProp(java.lang.String, cn.people.controller.vo.ModelSplitPropCreateVO)
     */
    @Override
    public Boolean createModelSplitProp(String modelSplitId,ModelSplitPropCreateVO createVO) throws Exception
    {   
       ModelSplit modelSplit = modelSplitMapper.selectById(modelSplitId);
       
       if(null==modelSplit) {
           throw new ContentBussinessException(CodeConstants.MODEL_SPLIT_NOT_EXIST, "模板碎片不存在");
       }
       //单独添加模板碎片属性
       if(!StringUtils.isEmpty(createVO.getSplitPropId())) {
           SplitProp splitProp = splitPropMapper.selectById(createVO.getSplitPropId());
           
           if(null==splitProp) {
               throw new ContentBussinessException(CodeConstants.SPLIT_PROP_NOT_EXIST, "碎片属性不存在");
           }
           //效验是否已经添加相同的碎片属性
           ModelSplitProp entity=new ModelSplitProp();
           entity.setSplitPropId(createVO.getSplitPropId());
           entity.setModelSplitId(modelSplitId);
           entity.setState(1);
           List<ModelSplitProp> entityList=this.list(new QueryWrapper<ModelSplitProp>(entity));
           if(!CollectionUtils.isEmpty(entityList)) {
               throw new ContentBussinessException(CodeConstants.SPLIT_PROP_EXIST, "该模板碎片下已有此碎片属性");
           }
           
           ModelSplitProp modelSplitProp=new ModelSplitProp();
           modelSplitProp.setCreateTime(new Date());
           modelSplitProp.setModelSplitId(modelSplitId);
           modelSplitProp.setState(1);
           modelSplitProp.setModelId(modelSplit.getModelId());
           modelSplitProp.setPropValue(splitProp.getPropValue());
           modelSplitProp.setCreateId(createVO.getCreateId());
           modelSplitProp.setModelSplitLogicId(modelSplit.getLogicId());
           modelSplitProp.setSplitPropId(createVO.getSplitPropId());
           return  this.save(modelSplitProp);
       }
       //按照碎片方案添加模板碎片属性
       if(!StringUtils.isEmpty(createVO.getModId())) {
           ModelSplitProp modelSplitPropEntity=new ModelSplitProp();
           modelSplitPropEntity.setState(2);
           ModelSplitProp updateWrapperEntity=new ModelSplitProp();
           updateWrapperEntity.setModelSplitId(modelSplitId);
           Boolean isSuccess=this.update(modelSplitPropEntity, new QueryWrapper<ModelSplitProp>(updateWrapperEntity));
           SplitModProp splitModProp=new SplitModProp();
           splitModProp.setModId(createVO.getModId());
           List<SplitModProp> propList=splitModPropMapper.selectList(new QueryWrapper<SplitModProp>(splitModProp));
           if(isSuccess&& !CollectionUtils.isEmpty(propList)) {
               List<ModelSplitProp> entityList=new ArrayList<ModelSplitProp>();
               propList.forEach(prop ->{
                   ModelSplitProp entity=new ModelSplitProp();
                   entity.setCreateTime(new Date());
                   entity.setModelSplitId(modelSplitId);
                   entity.setState(1);
                   entity.setModelId(modelSplit.getModelId());
                   entity.setPropValue(prop.getPropValue());
                   entity.setCreateId(createVO.getCreateId());
                   entity.setModelSplitLogicId(modelSplit.getLogicId());
                   entity.setSplitPropId(prop.getPropId());
                   entityList.add(entity);
               });
               return this.saveBatch(entityList);
           }
       }
       return true;
    }
    /**
     * 
    * Title: 查询含有分页信息的模板碎片属性 
    * @author shidandan
    * @date 2019年1月17日 下午8:38:14 
    * @Description: 查询含有分页信息的模板碎片属性 
    * @param tagIdList 模板碎片ID集合
    * @return  模板碎片属性集合
    * @see cn.people.service.IModelSplitPropService#selectHasPage(java.util.List)
     */
    @Override
    public List<ModelSplitProp> selectHasPage(List<String> tagIdList)
    {
        return baseMapper.selectHasPage(tagIdList);
    }

    /**
     * 
    * Title: 更新栏目下的模板碎片属性
    * @author shidandan
    * @date 2019年1月17日 下午8:38:47 
    * @Description: 更新栏目下的模板碎片属性
    * @param updateVO 模板碎片属性值
    * @param id 模板碎片属性值OD
    * @param channelId 栏目ID
    * @return 成功返回true
    * @see cn.people.service.IModelSplitPropService#updateModelSplitPropByChannel(cn.people.controller.vo.ModelSplitPropUpdateVO, java.lang.String, java.lang.String)
     */
    @Override
    public Boolean updateModelSplitPropByChannel(ModelSplitPropBatchUpdateVO updateVO)
    {
        if(!CollectionUtils.isEmpty(updateVO.getPropList())) {
            Map<String,String> ttpMap=new HashMap<String,String>();
            Set<String> ttpIds=new HashSet<String>();
            updateVO.getPropList().forEach(prop ->{
                ttpIds.add(prop.getId());
                ttpMap.put(prop.getId(), prop.getPropValue());
            });
            QueryWrapper<SplitPropValue> queryWrapper=new QueryWrapper<SplitPropValue>();
            queryWrapper.eq("channel_id", updateVO.getChannelId());
            queryWrapper.in("ttp_id", ttpIds);
            List<SplitPropValue> splitPropValueList=splitPropValueMapper.selectList(queryWrapper);
            
            if(CollectionUtils.isEmpty(splitPropValueList)) {
                List<SplitPropValue> list=new ArrayList<SplitPropValue>();
                updateVO.getPropList().forEach(prop ->{
                    SplitPropValue value=new SplitPropValue();
                    value.setChannelId(updateVO.getChannelId());
                    value.setPropValue(prop.getPropValue());
                    value.setTtpId(prop.getId());
                    list.add(value);
                });
                return iSplitPropValueService.saveBatch(list);
            }
            
            List<SplitPropValue> saveList=new ArrayList<SplitPropValue>();
            Set<String> updateSet=new HashSet<String>();
            splitPropValueList.forEach(value ->{
                updateSet.add(value.getTtpId());
                if(ttpMap.containsKey(value.getTtpId())) {
                    value.setPropValue(ttpMap.get(value.getTtpId()));
                }
            });
            if(updateVO.getPropList().size()>updateSet.size()) {
                updateVO.getPropList().forEach(prop ->{
                    if(!updateSet.contains(prop.getId())) {
                        SplitPropValue value=new SplitPropValue();
                        value.setChannelId(updateVO.getChannelId());
                        value.setPropValue(prop.getPropValue());
                        value.setTtpId(prop.getId());
                        saveList.add(value);
                    }
                }); 
            }
            
            Boolean isSuccess=iSplitPropValueService.updateBatchById(splitPropValueList);
            if(!CollectionUtils.isEmpty(saveList) && saveList.size()>0 && isSuccess) {
                isSuccess=iSplitPropValueService.saveBatch(saveList);
            }
            
            return isSuccess;
        
        }
        return true;
    }
    /**
     * 
    * Title: 得到某个栏目下的模板碎片属性值
    * @author shidandan
    * @date 2019年1月17日 下午8:39:50 
    * @Description: 得到某个栏目下的模板碎片属性值
    * @param channelId 栏目ID
    * @param modelSplitId 模板碎片ID
    * @return 模板碎片属性集合
    * @throws Exception 
    * @see cn.people.service.IModelSplitPropService#getModelSplitPropListByChannel(java.lang.String, java.lang.String)
     */
    @Override
    public ArrayList<SplitPropListVO> getModelSplitPropListByChannel(String channelId,
                                                                     String modelSpiltLogicId)
        throws Exception
    {
        //查询模板碎片对应模板碎片属性集合
        List<Map<String,String>> modelSplitPropList = modelSplitPropMapper.getModelSplitPropListByChannel(modelSpiltLogicId);
        
        
       //查询模板碎片栏目下的模板碎片属性集合
        Set<String> modelSplitProps= modelSplitPropList.stream().map(modelSplitProp ->{return modelSplitProp.get("id");}).collect(Collectors.toSet());
        QueryWrapper<SplitPropValue> queryWrapper=new QueryWrapper<SplitPropValue>();
        queryWrapper.in("ttp_id", modelSplitProps);
        List<SplitPropValue> splitPropValueList=splitPropValueMapper.selectList(queryWrapper);
        
        Map<String,String> splitPropValueMap=new HashMap<String,String>();
        
        if(!CollectionUtils.isEmpty(splitPropValueList)) {
            splitPropValueList.forEach(splitPropValue->{
                splitPropValueMap.put(splitPropValue.getTtpId(), splitPropValue.getPropValue());
            });
        }
        
        
        //整合结果并返回
        ArrayList<SplitPropListVO > SplitPropListVOList = new ArrayList<>();
        modelSplitPropList.forEach(c -> {
            SplitPropListVO splitPropListVO = new SplitPropListVO();
            splitPropListVO.setPropName(c.get("name"));
            splitPropListVO.setId(c.get("id"));
            splitPropListVO.setPropDes(c.get("des"));
            splitPropListVO.setPropValue(c.get("pvalue"));
            if (!StringUtils.isEmpty(splitPropValueMap.get(c.get("id")))){
                splitPropListVO.setPropValue(splitPropValueMap.get(c.get("id"))); 
            }
            SplitPropListVOList.add(splitPropListVO);
        });

        return SplitPropListVOList;
    
    }
}
