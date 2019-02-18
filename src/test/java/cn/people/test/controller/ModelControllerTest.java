/**   
* @Title: ModelControllerTest.java 
* @Package cn.people.test.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author shidandan
* @date 2018年12月26日 上午10:03:27 
* @version V1.0   
*/
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

import cn.people.controller.vo.ModelListRequestVO;
import cn.people.controller.vo.ModelVO;
import cn.people.entity.Model;



/** 
* @ClassName: ModelControllerTest 
* @Description: 模板测试类 
* @author shidandan
* @date 2018年12月26日 上午10:03:27 
*  
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ModelControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    
    @Test
 //   @Transactional
    public void testgetModelPaged() throws Exception {
        
    	ModelListRequestVO param=new ModelListRequestVO();
        //param.setNameOrid("张三");
        param.setTypeId("1");
        MvcResult mvcResult = mockMvc.perform( 
            post("/model/list") .contentType(MediaType.APPLICATION_JSON) 
            .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    //@Transactional
    public void testCreateModel() throws Exception {
        
        ModelVO param=new ModelVO();
        param.setCreateid("11122");
        param.setModelcontent("<div>${TAG_123_TAG}aaa${TAG_123_TAG}</div>");
        param.setModelname("联调测试模板");
        param.setSiteid("0c5f4d06090937f0a50e9bedad24420c");
        param.setTypeid("1");
        MvcResult mvcResult = mockMvc.perform( 
            post("/model/add") .contentType(MediaType.APPLICATION_JSON) 
            .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    //@Transactional
    public void testUpdateModel() throws Exception {
        

    	Model param=new Model();
    	param.setCreateId("111000");
    	param.setModelName("oooooooo");
    	param.setTypeId("12");
        MvcResult mvcResult = mockMvc.perform( 
            post("/model/9/update") .contentType(MediaType.APPLICATION_JSON) 
            .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void testGetModel() throws Exception {
        MvcResult mvcResult = mockMvc.perform( 
            get("/model/47a441b5e948ec08c6c5f1776f1de149/detail"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void testEnabledModel() throws Exception {
        MvcResult mvcResult = mockMvc.perform( 
            get("/model/b349c7f35c14d27695e0e80e72d5b5f6/enabled"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void testDisableModel() throws Exception {
        MvcResult mvcResult = mockMvc.perform( 
            get("/model/b349c7f35c14d27695e0e80e72d5b5f6/disable"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    
    @Test
    public void testGetModelPaged() throws Exception {
        
        ModelListRequestVO param=new ModelListRequestVO();
        param.setNameOrid("1");
        MvcResult mvcResult = mockMvc.perform( 
            post("/model/list") .contentType(MediaType.APPLICATION_JSON) 
            .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            //.andExpect(jsonPath("$.data.success").value(true))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
