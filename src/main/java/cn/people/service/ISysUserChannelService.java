package cn.people.service;

import cn.people.controller.vo.SysUserChannelVO;
import cn.people.entity.SysUserChannel;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户常用栏目 服务类
 * </p>
 *
 * @author shidandan
 * @since 2019-01-28
 */
public interface ISysUserChannelService extends IService<SysUserChannel> {

    /**
     * 
    * @Title: 查询用户常用栏目 
    * @author shidandan
    * @date 2019年1月28日 下午1:59:50 
    * @Description: 查询用户常用栏目 
    * @param userId 用户ID
    * @return  参数说明 
    * @return List<SysUserChannelVO>    返回类型 
    * @throws 
     */
    List<SysUserChannelVO> getSysUserChannel(String userId);
}
