package cn.people.test.controller;

import cn.people.controller.vo.ChannelCreateVO;
import cn.people.controller.vo.ModelSplitPropCreateVO;
import cn.people.controller.vo.ModelSplitPropUpdateVO;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author guling
 * @ClassName: ModelSpiltPropControllerTest
 * @Description: 模板属性测试(这里用一句话描述这个类的作用)
 * @date 2019/1/10 16:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ModelSpiltPropControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testgetModelSplitPropListByChannel() throws Exception {

        ChannelCreateVO param=new ChannelCreateVO();
        MvcResult mvcResult = mockMvc.perform(
            post("/model/spilt/prop/1/1/list").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void testgetModelSplitPropList() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
            get("/model/spilt/prop/1/list") )
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    @Transactional
    public void testDelModelSplitProp() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
            get("/model/spilt/prop/1/del") )
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void testcreateModelSplitProp() throws Exception {

        ModelSplitPropCreateVO param=new ModelSplitPropCreateVO();
        param.setCreateId("tl");
        param.setSplitPropId("111");
        MvcResult mvcResult = mockMvc.perform(
            post("/model/spilt/prop/1/add") .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testUpdateModelSplitProp() throws Exception {

        ModelSplitPropUpdateVO param=new ModelSplitPropUpdateVO();
        param.setPropValue("111111");
        MvcResult mvcResult = mockMvc.perform(
            post("/model/spilt/prop/1/update") .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void testUpdateModelSplitPropByChannel() throws Exception {

        ModelSplitPropUpdateVO param=new ModelSplitPropUpdateVO();
        param.setPropValue("111111");
        MvcResult mvcResult = mockMvc.perform(
            post("/model/spilt/prop/1/2/update") .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
