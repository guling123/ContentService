/**   
* @Title: ContentControllerTest.java 
* @Package cn.people.test.controller 
* @Description: 内容测试类
* @author shidandan
* @date 2018年12月26日 下午3:49:29 
* @version V1.0   
*/
package cn.people.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.alibaba.fastjson.JSON;
import cn.people.controller.vo.ContentCreateVO;
import cn.people.controller.vo.ContentQueryVO;
import cn.people.controller.vo.ContentUpdateVO;

/** 
* @ClassName: ContentControllerTest 
* @Description: 内容测试类
* @author shidandan
* @date 2018年12月26日 下午3:49:29 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContentControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testCreateContent() throws Exception {
        
        ContentCreateVO param=new ContentCreateVO();
        param.setCreaterid("1");
        param.setAuthor("author");
        List<String> channelids=new ArrayList<String>();
        channelids.add("32");
        param.setChannelids(channelids);
        param.setContent("测试内容");
        param.setDescription("description");
        param.setDtype(1);
        param.setDstatus("1");
        param.setImageid("imageid");
        param.setImageurl("imageurl");
        param.setShouldertitle("111");
        param.setSiteid("1");
        param.setSourceid("sourceid");
        param.setSubtitle("11");
        param.setTitle("111");
        
        MvcResult mvcResult = mockMvc.perform( 
            post("/content/add") .contentType(MediaType.APPLICATION_JSON) 
            .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testGetContentPaged() throws Exception {
        ContentQueryVO param=new ContentQueryVO();
        HashSet<String> strings = new HashSet<>();
        /*strings.add("12");
        strings.add("1");
        param.setCreateIds(strings);*/
        param.setKey("1");
        /*param.setDtype(1);
        param.setSiteid("0c5f4d06090937f0a50e9bedad24420c");
        param.setSourceid("sourceid");*/
        
        MvcResult mvcResult = mockMvc.perform( 
            post("/content/list") .contentType(MediaType.APPLICATION_JSON) 
            .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString()); 
    }

    @Test
    public void testUpdateContent() throws Exception {
        ContentUpdateVO param=new ContentUpdateVO();
        param.setAuthor("author");
        List<String> channelids=new ArrayList<String>();
        channelids.add("111111");
        param.setDtype(1);
        param.setImageurl("imageurl");
        param.setShouldertitle("shouldertitle");
        param.setSiteid("0c5f4d06090937f0a50e9bedad24420c");
        param.setSourceid("sourceid");
        param.setSubtitle("subtitle");
        param.setTitle("title");
        param.setDescription("vvvvvvvvvvvvvvvvvvvvvvvv");
        MvcResult mvcResult = mockMvc.perform( 
            put("/content/97f8554eb2ad1f0bf480df62902e3c60/update").contentType(MediaType.APPLICATION_JSON)
            .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString()); 
    }
    
    @Test
    public void testGetContentById() throws Exception {
        MvcResult mvcResult = mockMvc.perform( 
            get("/content/97f8554eb2ad1f0bf480df62902e3c60/detail"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testAuditContent() throws Exception {
        MvcResult mvcResult = mockMvc.perform( 
            get("/content/47a441b5e948ec08c6c5f1776f1de149/{dstatus}/audit"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testGsetContentVersionPaged() throws Exception {
        MvcResult mvcResult = mockMvc.perform( 
            get("/content/97f8554eb2ad1f0bf480df62902e3c60/version?pageNo=0&pageSize=10"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testGetContentVersionInfo() throws Exception {
        MvcResult mvcResult = mockMvc.perform( 
            get("/content/97f8554eb2ad1f0bf480df62902e3c60/version/57efc28288740398b51402d91bed5af8/detail"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testgetprecontent() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            get("/content/97f8554eb2ad1f0bf480df62902e3c60/version/57efc28288740398b51402d91bed5af8/detail"))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
            .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
}
