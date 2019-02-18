package cn.people.service;

import cn.people.controller.vo.SplitModCreateVO;
import cn.people.entity.SplitMod;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 碎片方案表对应原表TAG_MOD 服务类
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
public interface ISplitModService extends IService<SplitMod> {

    /**
     * 
    * @Title: 新增碎片方案
    * @author shidandan
    * @date 2019年1月18日 下午7:56:08 
    * @Description: 新增碎片方案
    * @param splitId 碎片方案id
    * @param createVO 模板碎片
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    Boolean createSplitMod(SplitModCreateVO createVO,String splitId) throws Exception;
}
