package cn.people.mapper;

import cn.people.controller.vo.SplitPageVO;
import cn.people.entity.ModelSplit;
import cn.people.entity.Split;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 碎片表,对应表TAG Mapper 接口
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
public interface SplitMapper extends BaseMapper<Split> {

	/*
	    * Title: selectPage
	    * @author tianlu
	    * @date 2019年1月14日上午11:36:23 
	    *Description: 
	    * @param 
	    * @return IPage<SplitPageVO> 
	    */
	IPage<SplitPageVO> selectPage(Page<Split> page, QueryWrapper<ModelSplit> wrapper);

}
