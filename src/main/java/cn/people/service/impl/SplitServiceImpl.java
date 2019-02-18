package cn.people.service.impl;

import cn.people.entity.Split;
import cn.people.mapper.SplitMapper;
import cn.people.service.ISplitService;
import java.util.List;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * <p>
 * 碎片表 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
@Service
public class SplitServiceImpl extends ServiceImpl<SplitMapper, Split> implements ISplitService {

    /**
    * Title: 查询未被某个模板关联得碎片
    * @author shidandan
    * @date 2019年1月22日 下午2:08:25 
    * @Description: 查询未被某个模板关联得碎片
    * @param modelid 模板ID
    * @return 
    * @see cn.people.service.ISplitService#getSplit(java.lang.String) 
    */
    @Override
    public List<Split> getSplit()
    {
        QueryWrapper<Split> wrapper=new QueryWrapper<Split>();
        return this.list(wrapper);
    }

}
