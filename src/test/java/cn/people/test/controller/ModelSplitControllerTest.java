package cn.people.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import cn.people.controller.vo.SplitCreateVO;
import cn.people.controller.vo.SplitPageVO;
import cn.people.controller.vo.SplitUpdateVO;

/**
 * 
* @ClassName: ModelSplitControllerTest 
* @Description: 模板碎片测试类
* @author shidandan
* @date 2019年1月16日 下午4:39:22 
*  
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ModelSplitControllerTest {
	 @Autowired
	 private MockMvc mockMvc;
	
	   @Test
//	   @Transactional
	    public void testCreateModelSplit() throws Exception {
	        
	       SplitCreateVO param=new SplitCreateVO();
	        MvcResult mvcResult = mockMvc.perform( 
	            post("/model/split/add") .contentType(MediaType.APPLICATION_JSON) 
	            .content(JSON.toJSONString(param)))
	            .andExpect(status().isOk())
	            //.andExpect(jsonPath("$.data.success").value(true))
	            .andReturn(); 
	        
	        System.out.println(mvcResult.getResponse().getContentAsString());
	    }
	   @Test
	   public void testGetModelSplitDetail() throws Exception {
	        
		        MvcResult mvcResult = mockMvc.perform( 
		            get("/model/split/0a51f869feba613c4db130884d3cf72b/detail") )
		            .andExpect(status().isOk())
		            //.andExpect(jsonPath("$.data.success").value(true))
		            .andReturn(); 
		        
		        System.out.println(mvcResult.getResponse().getContentAsString());
		    }
	   @Test
	   public void testDelModelSplit() throws Exception {
	        
		        MvcResult mvcResult = mockMvc.perform( 
		            get("/model/split/0a51f869feba613c4db130884d3cf72b/del") )
		            .andExpect(status().isOk())
		            //.andExpect(jsonPath("$.data.success").value(true))
		            .andReturn(); 
		        
		        System.out.println(mvcResult.getResponse().getContentAsString());
	   }
	   @Test
//	   @Transactional
	    public void testUpdateModelSplit() throws Exception {
	        
		 SplitUpdateVO param=new SplitUpdateVO();
		 	param.setSplitName("新建碎片测试2");
		 	param.setType(1);
		 	param.setChannelId("222");
	        MvcResult mvcResult = mockMvc.perform( 
	            post("/model/split/9630d36030b9aa53ad67026bac38bf91/update") .contentType(MediaType.APPLICATION_JSON) 
	            .content(JSON.toJSONString(param)))
	            .andExpect(status().isOk())
	            //.andExpect(jsonPath("$.data.success").value(true))
	            .andReturn(); 
	        
	        System.out.println(mvcResult.getResponse().getContentAsString());
	    }
	   @Test
//	   @Transactional
	    public void getModelSplitPaged() throws Exception {
	        
		   SplitPageVO param=new SplitPageVO();
		 	param.setCreaterId("tl11");
	        MvcResult mvcResult = mockMvc.perform( 
	            post("/model/split/88/list") .contentType(MediaType.APPLICATION_JSON) 
	            .content(JSON.toJSONString(param)))
	            .andExpect(status().isOk())
	            //.andExpect(jsonPath("$.data.success").value(true))
	            .andReturn(); 
	        
	        System.out.println(mvcResult.getResponse().getContentAsString());
	    }  
}
