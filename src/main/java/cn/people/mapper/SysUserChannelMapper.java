package cn.people.mapper;

import cn.people.controller.vo.SysUserChannelVO;
import cn.people.entity.SysUserChannel;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户常用栏目 Mapper 接口
 * </p>
 *
 * @author shidandan
 * @since 2019-01-28
 */
public interface SysUserChannelMapper extends BaseMapper<SysUserChannel> {

    @Select("SELECT us.*,cs.channel_name FROM `tb_sys_user_channel` us LEFT JOIN `tb_channel` cs ON us.channel_id=cs.id WHERE us.user_id=#{userId} AND cs.dstatus='1';")
    List<SysUserChannelVO> getSysUserChannel(String userId);
}
