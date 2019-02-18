/**   
* @Title: SiteControllerTest.java 
* @Package cn.people.test.controller 
* @Description: 站点初始化测试
* @author shidandan
* @date 2018年12月5日 上午10:08:08 
* @version V1.0   
*/
package cn.people.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
 * 
* @ClassName: ViewControllerTest 
* @Description: 模板预览测试类 
* @author zhangchengchun
* @date 2019年1月7日 下午7:16:23 
*  
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ViewControllerTest
{
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetChannelModelContent() throws Exception {
         
        MvcResult mvcResult = mockMvc.perform( 
            get("/view/channel/4/mod") .contentType(MediaType.APPLICATION_JSON))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
 
    @Test
    public void testGetChannelModelContentsplits() throws Exception {
         
        MvcResult mvcResult = mockMvc.perform( 
            get("/view/channel/4/splits") .contentType(MediaType.APPLICATION_JSON))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testViewChannel() throws Exception {
         
        MvcResult mvcResult = mockMvc.perform( 
            get("/view/channel/4") .contentType(MediaType.APPLICATION_JSON))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testViewContent() throws Exception {
         
        MvcResult mvcResult = mockMvc.perform( 
            get("/view/content/100004?page=2") .contentType(MediaType.APPLICATION_JSON))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testContentPage() throws Exception {
         
        MvcResult mvcResult = mockMvc.perform( 
            get("/view/content/100004/pages").contentType(MediaType.APPLICATION_JSON))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testChannelPage() throws Exception {
         
        MvcResult mvcResult = mockMvc.perform( 
            get("/view/channel/100005/pages").contentType(MediaType.APPLICATION_JSON))
            .andReturn(); 
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
