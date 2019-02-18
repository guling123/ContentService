package cn.people.service;

import cn.people.entity.Split;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 碎片表 服务类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
public interface ISplitService extends IService<Split> {

    /**
     * 
    * @Title: 查询未被某个模板关联得碎片 
    * @author shidandan
    * @date 2019年1月22日 下午2:07:09 
    * @Description: 查询未被某个模板关联得碎片 
    * @param modelid 模板ID
    * @return  参数说明 
    * @return List<Split>    返回类型 
    * @throws 
     */
    List<Split> getSplit();
}
