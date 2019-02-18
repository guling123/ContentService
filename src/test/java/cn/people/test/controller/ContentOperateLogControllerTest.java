/**   
* @Title: ContentOperateLogControllerTest.java 
* @Package cn.people.test.controller 
* @Description: 内容操作日志测试类 
* @author shidandan
* @date 2018年12月26日 下午4:48:12 
* @version V1.0   
*/
package cn.people.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/** 
* @ClassName: ContentOperateLogControllerTest 
* @Description: 内容操作日志测试类
* @author shidandan
* @date 2018年12月26日 下午4:48:12 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContentOperateLogControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testGetModel() throws Exception {
        MvcResult mvcResult = mockMvc.perform( 
            get("/content/operateLog/97f8554eb2ad1f0bf480df62902e3c60/list"))
           .andExpect(status().isOk())
           //.andExpect(jsonPath("$.data.siteid").value("6601476df7196f07c9b4bffb1fd30516"))
           .andReturn();
           
           System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
