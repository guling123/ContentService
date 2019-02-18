/**   
* @Title: SplitContentControllerTest.java 
* @Package cn.people.test.controller 
* @Description: 碎片内容测试类
* @author shidandan
* @date 2018年12月26日 下午3:13:41 
* @version V1.0   
*/
package cn.people.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import cn.people.controller.vo.SplitContentAddVO;
import cn.people.controller.vo.SplitContentVO;

/** 
* @ClassName: SplitContentControllerTest 
* @Description: 碎片内容测试类
* @author shidandan
* @date 2018年12月26日 下午3:13:41 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SplitContentControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    //@Transactional
    public void testUpdateSplitContent() throws Exception {
        
        SplitContentVO param=new SplitContentVO();
        
        param.setTextBox("111111");
        MvcResult mvcResult = mockMvc.perform( 
            post("/split/content/13255a526a07a64a5fa9bde6220fceab/update") .contentType(MediaType.APPLICATION_JSON) 
            .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void testAaddSplitContent() throws Exception {
        SplitContentAddVO param=new SplitContentAddVO();
        List<SplitContentVO> spiltContentList=new ArrayList<SplitContentVO>();
        SplitContentVO vo=new SplitContentVO();
        vo.setContentid("ab003911fa144ff274deac4c00094b4f");
        vo.setDtype(1);
        vo.setImporter("1");
        vo.setLinkUrl("http://baidu.com");
        vo.setTextBox("ccccc");
        vo.setTitle("title");
        spiltContentList.add(vo);
        param.setSpiltContentList(spiltContentList);
        
        
        MvcResult mvcResult = mockMvc.perform( 
            post("/split/content/120/30/add") .contentType(MediaType.APPLICATION_JSON) 
            .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testQuerySplitContentList() throws Exception {
        
        MvcResult mvcResult = mockMvc.perform( 
            get("/split/content/120/30/list"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testGetSplitContent() throws Exception {
        
        MvcResult mvcResult = mockMvc.perform( 
            get("/split/content/13255a526a07a64a5fa9bde6220fceab/detail"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testHideSplitContent() throws Exception {
        
        MvcResult mvcResult = mockMvc.perform( 
            get("/split/content/13255a526a07a64a5fa9bde6220fceab/hide"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testShowSplitContent() throws Exception {
        
        MvcResult mvcResult = mockMvc.perform( 
            get("/split/content/13255a526a07a64a5fa9bde6220fceab/show"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testDelSplitContent() throws Exception {
        
        MvcResult mvcResult = mockMvc.perform( 
            get("/split/content/13255a526a07a64a5fa9bde6220fceab/del"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
