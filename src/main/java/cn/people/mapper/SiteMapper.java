package cn.people.mapper;

import cn.people.entity.Site;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 站点信息表 Mapper 接口
 * </p>
 *
 * @author shidandan
 * @since 2018-12-04
 */
@Mapper
public interface SiteMapper extends BaseMapper<Site> {

}
