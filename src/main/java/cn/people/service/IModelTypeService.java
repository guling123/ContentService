package cn.people.service;

import cn.people.controller.vo.ModelTypeVO;
import cn.people.entity.ModelType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * @author guling
 * @ClassName: IModelTypeService
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2019/1/22 9:35
 */
public interface IModelTypeService extends IService<ModelType>
{
    /**
     *
     * @Title: 获取类型列表信息
     * @author  guling
     * @date 2019年1月22日 09点41分 
     * @Description: 获取类型列表信息
     * @param siteid 站点id
     * @return ResultVO<ContentVersionVO>    站点类型id 
     * @throws Expection
     */
    List<ModelTypeVO> getTypeListBy(String siteid)
        throws Exception;
}
