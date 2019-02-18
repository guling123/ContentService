package cn.people.test.controller;

import cn.people.controller.vo.ChannelCreateVO;
import cn.people.controller.vo.PublishChanneRequest;
import cn.people.entity.Channel;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author guling
 * @ClassName: ChannelControllerTest
 * @Description: channel的controller测试类(这里用一句话描述这个类的作用)
 * @date 2019/1/9 11:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChannelControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateChannel() throws Exception {

        ChannelCreateVO param=new ChannelCreateVO();

        param.setSiteid("0c5f4d06090937f0a50e9bedad24420c");
        param.setChannelName("fengakdla");
        param.setDtype(1);
        param.setDisplay(1);
        param.setDescription("description");
        param.setParentId("1");
        param.setChannelModelId("2136");
        param.setContentModelId("546845");
        param.setImagesModelId("123");
        param.setDomain("123");
        param.setCreaterId("123");
        param.setIdent("1");
        MvcResult mvcResult = mockMvc.perform(
            post("/channel/add") .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testupdateChannel() throws Exception {

        Channel param=new Channel();

        param.setId("11af8cfd2eaeec7988b63fc24d847b21");
        param.setSiteid("0c5f4d06090937f0a50e9bedad24420c");
        param.setChannelName("fengakdla");
        param.setDtype(1);
        param.setDisplay(1);
        param.setDescription("description");
        param.setParentId("1");
        param.setChannelModelId("2136");
        param.setContentModelId("546845");
        param.setImagesModelId("123");
        param.setDomain("123");
        param.setCreaterId("123");
        param.setIdent("1");
        MvcResult mvcResult = mockMvc.perform(
            post("/channel/11af8cfd2eaeec7988b63fc24d847b21/update") .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testGetChannel() throws Exception {

        String id = "11af8cfd2eaeec7988b63fc24d847b21";

        MvcResult mvcResult = mockMvc.perform(
            post("/channel/11af8cfd2eaeec7988b63fc24d847b21/detail") .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(id)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void testdelChannel() throws Exception {

        ChannelCreateVO param=new ChannelCreateVO();

        MvcResult mvcResult = mockMvc.perform(
            post("/channel/11af8cfd2eaeec7988b63fc24d847b23/del") .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void testgetChannelBySite() throws Exception {

        ChannelCreateVO param=new ChannelCreateVO();

        MvcResult mvcResult = mockMvc.perform(
            post("/channel/0c5f4d06090937f0a50e9bedad24420c/1/list") .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testGetChannelPublist() throws Exception {

        PublishChanneRequest publishChanneRequestVO = new PublishChanneRequest();
        publishChanneRequestVO.setLogicId(100002);

        MvcResult mvcResult = mockMvc.perform(
            post("/channel/publish/tree") .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(publishChanneRequestVO)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
