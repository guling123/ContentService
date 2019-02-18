/**   
* @Title: SiteControllerTest.java 
* @Package cn.people.test.controller 
* @Description: 站点初始化测试
* @author shidandan
* @date 2018年12月5日 上午10:08:08 
* @version V1.0   
*/
package cn.people.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import cn.people.entity.Site;

/** 
* @ClassName: SiteControllerTest 
* @Description: 站点初始化测试
* @author shidandan
* @date 2018年12月5日 上午10:08:08 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SiteControllerTest
{
    @Autowired
    private MockMvc mockMvc;


    @Test
    @Transactional
    public void testCreateSite() throws Exception {
        
        Site param=new Site();
        param.setSitename("测试站点11111111111");
        
        MvcResult mvcResult = mockMvc.perform( 
            post("/site/init") .contentType(MediaType.APPLICATION_JSON) 
            .content(JSON.toJSONString(param)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.success").value(true))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
 
}
