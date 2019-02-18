package cn.people.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.people.commons.constants.CodeConstants;
import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.ModelDetailVO;
import cn.people.controller.vo.ModelListRequestVO;
import cn.people.controller.vo.ModelPageVO;
import cn.people.controller.vo.ModelVO;
import cn.people.entity.Model;
import cn.people.entity.ModelType;
import cn.people.mapper.ModelMapper;
import cn.people.mapper.ModelTypeMapper;
import cn.people.service.IIdGeneraterService;
import cn.people.service.IModelService;
import cn.people.service.view.obj.FileTool;
import cn.people.service.view.obj.SharedConstant;

/**
 * <p>
 * 模板表 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements IModelService {
    
    @Value("${model.modelDir}")
    private String modelDir;
    
    @Autowired
    private ModelMapper ModelMapper;
    
    @Autowired
    private IIdGeneraterService iIdGeneraterService;
    
    @Autowired
    private ModelTypeMapper modelTypeMapper;
    
    /**
     * 
    * Title: 查询模板列表
    * @author shidandan
    * @date 2019年1月17日 下午3:02:22 
    * @Description: 查询模板列表
    * @param modelListRequestVo
    * @return 成功返回模板列表
    * @see cn.people.service.IModelService#getModelPaged(cn.people.controller.vo.ModelListRequestVO)
     */
    @Override
    public IPage<ModelPageVO> getModelPaged(ModelListRequestVO modelListRequestVo,String siteid)
    {
        QueryWrapper<Model> modelWrapper = new QueryWrapper<Model>();
        modelWrapper.eq("siteid", siteid);
        if (!StringUtils.isEmpty(modelListRequestVo.getNameOrid())) {
            modelWrapper.and(wrapper -> wrapper.like("model_name", modelListRequestVo.getNameOrid()).or().like("id", modelListRequestVo.getNameOrid()));
        }
        if (null != modelListRequestVo.getCreateIds()) {
            modelWrapper.in("create_id",  modelListRequestVo.getCreateIds());
        }
        if (!StringUtils.isEmpty(modelListRequestVo.getTypeId())) {
            modelWrapper.eq("type_id", modelListRequestVo.getTypeId());
        }
        
        modelWrapper.orderByDesc("create_time"); 
        IPage<Model> modelPageResult=this.page(new Page<>(modelListRequestVo.getPageNo(), modelListRequestVo.getPageSize()), modelWrapper);
        IPage<ModelPageVO> result=new Page<ModelPageVO>();
        // copy属性
        result.setCurrent(modelPageResult.getCurrent());
        result.setPages(modelPageResult.getPages());
        result.setTotal(modelPageResult.getTotal());
        if(null!=modelPageResult&&!CollectionUtils.isEmpty(modelPageResult.getRecords())) {
            Set<String> typeIds=modelPageResult.getRecords().stream().map(type ->{return type.getTypeId();}).collect(Collectors.toSet());
            List<ModelType> types=modelTypeMapper.selectBatchIds(typeIds);
            Map<String,String> typeMap=new HashMap<String,String>();
            types.forEach(type->{
                typeMap.put(type.getId(), type.getTypename());
            });
            
            List<ModelPageVO> list=new ArrayList<ModelPageVO>();
            modelPageResult.getRecords().forEach(record->{
                ModelPageVO pageVO=new ModelPageVO();
                BeanUtils.copyProperties(record, pageVO);
                pageVO.setLogicId(record.getLogicId().toString());
                pageVO.setUrl(record.getUrl()+"_"+record.getLogicId()+".html");
                if(null!=typeMap.get(record.getTypeId())) {
                    pageVO.setTypeName(typeMap.get(record.getTypeId()));
                }
                list.add(pageVO);
            });
            result.setRecords(list);
        }
        
        return result;
    
    }

    /**
     * 
    * Title: 创建模板
    * @author shidandan
    * @date 2019年1月17日 下午3:05:01 
    * @Description: 创建模板
    * @param modelVo 模板信息
    * @return 成功返回模板id
    * @throws Exception 
    * @see cn.people.service.IModelService#createModel(cn.people.controller.vo.ModelVO)
     */
    @Override
    public String createModel(ModelVO modelVo) throws Exception
    {
        Model model=new Model();
        model.setModelName(modelVo.getModelname());
        List<Model> modelList=this.list(new QueryWrapper<Model>(model));
        
        if(!CollectionUtils.isEmpty(modelList)) {
            throw new ContentBussinessException(CodeConstants.MODEL_EXIST, "模板已经存在");
        }
        BeanUtils.copyProperties(modelVo, model);
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy/MM");
        String format = sdfDateFormat.format(new Date());
        model.setDstatus(1);
        model.setCreateTime(new Date());
        model.setUrl(format);
        model.setVersionId("1");
        model.setTypeId(modelVo.getTypeid());
        model.setCreateId(modelVo.getCreateid());
        model.setSiteId(modelVo.getSiteid());
        model.setLogicId(iIdGeneraterService.getNextValue(Model.class.getAnnotation(TableName.class).value()));
        System.out.println(modelDir);
        
        
        if(this.save(model)) {
            String id = model.getId();
            //版本内容的存储
            String filePath=modelDir+format+"/"+model.getId()+".html";
            System.out.println(filePath);
            FileTool.write(filePath, modelVo.getModelcontent());
            //创建文件夹
            String folderPath=modelDir.substring(0,1)+"/"+SharedConstant.SYSTEM_NAME+"/"+format+"_"+id+"/";
            File file = new File(folderPath);
            System.out.println(file);
            if(!file.exists()){
                //不存在
                file.mkdirs();
            }
//            //获取模板得存储路径
//            String id = model.getId();
//            SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy/MM");
//            String url = sdfDateFormat.format(model.getCreateTime()) + "/" + id + ".html";
//            model.setUrl(url);
//          
//            this.updateById(model);
//            
//          String filePath=modelDir+url;
//          //将模板的内容存储到文件系统
//          FileTool.write(filePath, modelVo.getModelcontent());
            return model.getId(); 
        }
        return null;
    }

    /**
     * 
    * Title: 修改模板详细信息
    * @author shidandan
    * @date 2019年1月17日 下午3:17:51 
    * @Description: 修改模板详细信息
    * @param modelVo 模板详细信息
    * @param id 模板id
    * @return 成功返回true
    * @throws Exception 
    * @see cn.people.service.IModelService#modelUpdate(cn.people.controller.vo.ModelVO, java.lang.String)
     */
    @Override
    public Boolean modelUpdate(ModelVO modelVo,String id) throws Exception
    {
        Model model2 = new Model();
        model2.setModelName(modelVo.getModelname());
        List<Model> modelList=this.list(new QueryWrapper<Model>(model2));
        if(!CollectionUtils.isEmpty(modelList)) {
            for(Model model:modelList) {
                if(!model.getId().equals(id)) {
                    throw new ContentBussinessException(CodeConstants.MODEL_EXIST, "模板已经存在");
                }
            }
        }
        
        Model model=this.getById(id);
        if(null==model) {
            throw new ContentBussinessException(CodeConstants.MODEL_NOT_EXIST, "模板不存在");
        }
        
        model.setId(id);
        model.setModelName(modelVo.getModelname());
        model.setTypeId(modelVo.getTypeid());
        model.setSiteId(modelVo.getSiteid());
        //model.setUrl(modelVo.getModelcontent());
        
        
        
        Boolean isSuccess=this.updateById(model);
        
        //模板内容的存储
        String filePath=modelDir+model.getUrl()+"/"+id+".html";
        System.out.println(filePath);
        FileTool.write(filePath, modelVo.getModelcontent(),false);
        return isSuccess;
    }

    /**
     * 
    * Title: 查询模板详情
    * @author shidandan
    * @date 2019年1月18日 下午5:29:23 
    * @Description: 查询模板详情
    * @param id 模板id
    * @return 成功返回模板详情
    * @see cn.people.service.IModelService#getModel(java.lang.String)
     */
    @Override
    public ModelDetailVO getModel(String id)
    {
        Model model=this.getById(id);
        if(null!=model) {
           //获取模板得内容主体
           String filepath = modelDir + model.getUrl()+"/"+id+".html";
           System.out.println(filepath);
           String content=FileTool.ReadFile(filepath, "utf-8");
           System.out.println(content);
           ModelDetailVO result=new ModelDetailVO();
           BeanUtils.copyProperties(model, result);
           result.setContent(content);
           return result;
        }
        return null;
    }
    
    /**
     * 
    * Title: 启用模板
    * @author shidandan
    * @date 2019年1月17日 下午3:06:47 
    * @Description: 启用模板
    * @param id 模板id
    * @return 成功返回true
    * @see cn.people.service.IModelService#delModel(java.lang.String)
     */
    @Override
    public Boolean enabledModel(String id) {
         Model model = ModelMapper.selectById(id);
         model.setDstatus(1);
         ModelMapper.updateById(model);
        return true;
    }
    
    /**
     * 
    * Title:禁用模板
    * @author shidandan
    * @date 2019年1月17日 下午3:06:47 
    * @Description: 禁用模板 
    * @param id 模板id
    * @return 成功返回true
    * @see cn.people.service.IModelService#delModel(java.lang.String)
     */
    @Override
    public Boolean disableModel(String id) {
         Model model = ModelMapper.selectById(id);
         model.setDstatus(2);
         ModelMapper.updateById(model);
        return true;
    }

    /**
    * Title: 查询某个站点下某种类型的模板 
    * @author shidandan
    * @date 2019年1月22日 下午7:14:27 
    * @Description: 查询某个站点下某种类型的模板 
    * @param siteid 站点ID
    * @param typeid 类型ID
    * @return 
    * @see cn.people.service.IModelService#getModel(java.lang.String, java.lang.String) 
    */
    @Override
    public List<Model> getModel(String siteid, String typeid)
    {
        Model model=new Model();
        model.setSiteId(siteid);
        model.setTypeId(typeid);
        
        return this.list(new QueryWrapper<Model>(model));
    }

}
