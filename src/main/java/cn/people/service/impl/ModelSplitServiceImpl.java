package cn.people.service.impl;

import cn.people.commons.constants.CodeConstants;
import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.ModelSplitDetailVO;
import cn.people.controller.vo.ModelSplitPageVO;
import cn.people.controller.vo.ModelSplitVO;
import cn.people.controller.vo.SplitCreateVO;
import cn.people.controller.vo.SplitPageVO;
import cn.people.controller.vo.SplitUpdateVO;
import cn.people.entity.Model;
import cn.people.entity.ModelSplit;
import cn.people.entity.Split;
import cn.people.mapper.ModelMapper;
import cn.people.mapper.ModelSplitMapper;
import cn.people.mapper.SplitMapper;
import cn.people.service.IIdGeneraterService;
import cn.people.service.IModelSplitService;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
* @ClassName: ModelSplitServiceImpl 
* @Description: 模板碎片接口实现类 
* @author shidandan
* @date 2019年1月17日 下午8:26:32 
*  
 */
@Service
public class ModelSplitServiceImpl extends ServiceImpl<ModelSplitMapper, ModelSplit> implements IModelSplitService {
    @Autowired
    private SplitMapper splitMapper;
    @Autowired
    private IIdGeneraterService iIdGeneraterService;
    
    @Autowired
    private ModelMapper modelMapper;
   
    /**
     * 
    * Title: 通过模板碎片逻辑ID查询模板碎片详情
    * @author shidandan
    * @date 2019年1月17日 下午8:22:25 
    * @Description: 通过模板碎片逻辑ID查询模板碎片详情
    * @param ttId 模板碎片ID
    * @return 模板碎片详情
    * @see cn.people.service.IModelSplitService#getByLogicId(int)
     */
	@Override
    public ModelSplit getByLogicId(int ttId)
    {
        QueryWrapper<ModelSplit> queryWrapper = new QueryWrapper<ModelSplit>();
        queryWrapper.eq("logic_id", ttId);
        ModelSplit result = getOne(queryWrapper);
        return result;
    }

    /**
     * 
    * Title: 更新模板碎片信息
    * @author shidandan
    * @date 2019年1月17日 下午8:23:04 
    * @Description: 更新模板碎片信息
    * @param id 模板碎片ID
    * @param split 模板碎片信息
    * @return 成功返回true
    * @throws Exception 
    * @see cn.people.service.IModelSplitService#updateSplit(java.lang.String, cn.people.controller.vo.SplitUpdateVO)
     */
    @Override
    public Boolean updateSplit(String id, SplitUpdateVO split) throws Exception
    {
        ModelSplit modelSplit=this.getById(id);
        if(null==modelSplit) {
            throw new ContentBussinessException(CodeConstants.MODEL_SPLIT_NOT_EXIST, "模板碎片不存在");
        }
        BeanUtils.copyProperties(split, modelSplit);
        modelSplit.setId(id);
        return this.updateById(modelSplit);
    
    }

    /**
     * 
    * Title: 分页查询模板碎片信息
    * @author shidandan
    * @date 2019年1月17日 下午8:23:53 
    * @Description: 分页查询模板碎片信息
    * @param split 模板碎片信息
    * @param modelid 模板ID
    * @return 
     * @throws Exception 
    * @see cn.people.service.IModelSplitService#getModelSplitPaged(cn.people.controller.vo.SplitPageVO, java.lang.String)
     */
    @Override
    public ModelSplitPageVO getModelSplitPaged(SplitPageVO split, String modelid) throws Exception
    {
        Model model=modelMapper.selectById(modelid);
        if(null==model) {
            throw new ContentBussinessException(CodeConstants.MODEL_NOT_EXIST, "模板不存在");
        }
        QueryWrapper<ModelSplit> wrapper = new QueryWrapper<ModelSplit>();
        
        wrapper.eq("model_id",  modelid);
        if (!StringUtils.isEmpty(split.getSplitnameOrId())) {
            wrapper.like("split_name", split.getSplitnameOrId()).or().like("id", split.getSplitnameOrId());
        }
        if (null != split.getType()) {
           wrapper.eq("type",  split.getType());
        }
        if (null != split.getCreaterIds()) {
            wrapper.in("create_id",  split.getCreaterIds());
        }
        
        IPage<ModelSplit> list=this.page(new Page<>(split.getPageNo(), split.getPageSize()), wrapper);
        
        ModelSplitPageVO result=new ModelSplitPageVO();
        // copy属性
        result.setCurrent(list.getCurrent());
        result.setPages(list.getPages());
        result.setTotal(list.getTotal());
        result.setModelName(model.getModelName());
        if(!CollectionUtils.isEmpty(list.getRecords())) {
            List<ModelSplitVO> modePlitList=new ArrayList<ModelSplitVO>();
            
            list.getRecords().forEach(record->{
                ModelSplitVO pageVO=new ModelSplitVO();
                BeanUtils.copyProperties(record, pageVO);
                if(record.getType()==1) {
                    pageVO.setTypeName("通用碎片");
                }
                if(record.getType()==2) {
                    pageVO.setTypeName("导航碎片");
                }
                modePlitList.add(pageVO);
            });
            result.setRecords(modePlitList); 
        }
        
        return result;

    }

    /**
     * 
    * Title: 查询模板碎片详情信息
    * @author shidandan
    * @date 2019年1月17日 下午8:24:35 
    * @Description: 查询模板碎片详情信息
    * @param id 模板碎片ID
    * @return
    * @throws Exception 
    * @see cn.people.service.IModelSplitService#getModelSplitDetail(java.lang.String)
     */
    @Override
    public ModelSplitDetailVO getModelSplitDetail(String id)
        throws Exception
    {
        if(StringUtils.isEmpty(id)) {
            return null;
        }
        
        ModelSplit modelSplit=this.getById(id);
        
        if(null!=modelSplit) {
            ModelSplitDetailVO result=new ModelSplitDetailVO();
            BeanUtils.copyProperties(modelSplit, result);
            Split split=splitMapper.selectById(modelSplit.getSiteId());
            if (split !=null) {
                result.setType(split.getType()); 
            }
            return result;
        }
        return null;
    }

    /**
     * 
    * Title: 删除模板碎片
    * @author shidandan
    * @date 2019年1月17日 下午8:25:09 
    * @Description: 删除模板碎片
    * @param id 模板碎片ID
    * @return 成功返回true
    * @throws Exception 
    * @see cn.people.service.IModelSplitService#delModelSplit(java.lang.String)
     */
    @Override
    public Boolean delModelSplit(String id) throws Exception
    {
        ModelSplit modelSplit=this.getById(id);
        
        if(null==modelSplit) {
            throw new ContentBussinessException(CodeConstants.MODEL_SPLIT_NOT_EXIST, "模板碎片不存在");
        }
        
        return this.removeById(id);
    }

    /**
     * 
    * Title: 创建模板碎片
    * @author shidandan
    * @date 2019年1月17日 下午8:25:39 
    * @Description: 创建模板碎片
    * @param split 模板碎片信息
    * @return 成功返回true
    * @throws Exception 
    * @see cn.people.service.IModelSplitService#createModelSplit(cn.people.controller.vo.SplitCreateVO)
     */
    @Override
    public Boolean createModelSplit(SplitCreateVO split)
        throws Exception
    {
        Split s=splitMapper.selectById(split.getSplitId());
        if(s==null) {
            throw new ContentBussinessException(CodeConstants.SPLIT_NOT_EXIST, "碎片不存在");
        }
        Model model=modelMapper.selectById(split.getModelid());
        
        if(model==null) {
            throw new ContentBussinessException(CodeConstants.MODEL_NOT_EXIST, "模板不存在");
        }
        
        ModelSplit modelSplit=new ModelSplit();
        modelSplit.setCreateTime(new Date());
        modelSplit.setModelId(split.getModelid());
        modelSplit.setSplitId(split.getSplitId());
        modelSplit.setSplitName(split.getSplitName());
        modelSplit.setChannelId(split.getChannelId());
        modelSplit.setCreateId(split.getCreaterId());
        modelSplit.setSiteId(model.getSiteId());
        modelSplit.setType(s.getType());
        modelSplit.setLogicId(iIdGeneraterService.getNextValue(ModelSplit.class.getAnnotation(TableName.class).value()));
        if(split.getCreaterId()!=null) {
            modelSplit.setCreateId(split.getCreaterId());
        }
           
        return this.save(modelSplit);
    }

    /**
    * Title: getModelSplit
    * @author shidandan
    * @date 2019年1月22日 下午1:36:28 
    * @Description: 
    * @param modelid
    * @return 
    * @see cn.people.service.IModelSplitService#getModelSplit(java.lang.String) 
    */
    @Override
    public List<ModelSplit> getModelSplit(String modelid)
    {
        ModelSplit entity=new ModelSplit();
        entity.setModelId(modelid);
        return this.list(new QueryWrapper<ModelSplit>(entity));
    }
}
