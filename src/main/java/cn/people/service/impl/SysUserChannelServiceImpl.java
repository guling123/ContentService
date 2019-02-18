package cn.people.service.impl;

import cn.people.controller.vo.SysUserChannelVO;
import cn.people.entity.SysUserChannel;
import cn.people.mapper.SysUserChannelMapper;
import cn.people.service.ISysUserChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户常用栏目 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2019-01-28
 */
@Service
public class SysUserChannelServiceImpl extends ServiceImpl<SysUserChannelMapper, SysUserChannel> implements ISysUserChannelService {

    @Autowired
    private SysUserChannelMapper sysUserChannelMapper;
    
    /**
    * Title: 查询用户常用栏目
    * @author shidandan
    * @date 2019年1月28日 下午2:00:14 
    * @Description: 查询用户常用栏目
    * @param userId 用户ID
    * @return 
    * @see cn.people.service.ISysUserChannelService#getSysUserChannel(java.lang.String) 
    */
    @Override
    public List<SysUserChannelVO> getSysUserChannel(String userId)
    {
        return sysUserChannelMapper.getSysUserChannel(userId);
    }

}
