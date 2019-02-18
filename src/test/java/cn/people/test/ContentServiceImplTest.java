package cn.people.test;

import cn.people.ContentApplication;
import cn.people.controller.vo.*;
import cn.people.service.impl.ContentServiceImpl;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= ContentApplication.class)
@WebAppConfiguration
public class ContentServiceImplTest
{
    @Autowired
    ContentServiceImpl contentService;

    @Test
    public void getPreContent()
    {
        ContentDetailVO preContent = contentService.getPreContent(50, "2019-01-17 20:04:52",
            "123");
        System.out.println(preContent.toString());
    }
    @Test
    public void getnNxtContent()
    {
        ContentDetailVO preContent = contentService.getNextContent(50, "2019-01-17 20:04:52",
            "ab003911fa144ff274deac4c00094b4f");
        System.out.println(preContent.toString());
    }

    @Test
    public void listAutoNews()
    {
        ContentViewRequestVO searchParam = new ContentViewRequestVO();
        ContentDetailVO preContent = contentService.getNextContent(50, "2019-01-17 20:04:52",
            "ab003911fa144ff274deac4c00094b4f");
        System.out.println(preContent.toString());
    }

    @Test
    public void addContent()
        throws Exception
    {
        ContentCreateVO param = new ContentCreateVO();
        param.setCreaterid("111");
        param.setAuthor("2222");
        List<String> lsit= new ArrayList<>();
        lsit.add("333");
        lsit.add("444");
        lsit.add("555");
        param.setChannelids(lsit);
        param.setContent("666666666666666666666666666");
        param.setDescription("7777777777777777777777777");
        param.setDstatus("1");
        param.setDtype(1);
        param.setImageurl("aaaaaaaaaaaaaaaaaaaaaaaaa");
        param.setShouldertitle("45555555555555555555556");
        param.setSiteid("02d3ceb0582869f9008c07a6ce79298e");
        param.setSourceid("11111111111111");
        param.setSubtitle("8888888888888");
        param.setTitle("999999999999999");
        param.setImageid("qqqqqqqqqqqqqqqqq");
        param.setImageUrls("D:\\javainstead\\nginx\\nginx-1.15.8\\html\\2019\\01\\14\\2c8cb55079c141f2967481b0328e8b038ecbb00b2add668a981fae4c8abed49.png,D:\\javainstead\\nginx\\nginx-1.15.8\\html\\2019\\01\\14\\2c8aaaaaaaaaaa0b2add668a981fae4c8abed49.png,D:\\javainstead\\nginx\\nginx-1.15.8\\html\\2019\\01\\14\\2c8cb55079c14ssssssssssssssssss2add668a981fae4c8abed49.png,D:\\javainstead\\nginx\\nginx-1.15.8\\html\\2019\\01\\14\\2c8cb55ddddddddddddddddddddddddddd981fae4c8abed49.png");
        param.setRedirecturl("eeeeeeeeeeeeeeeee");
        param.setSendtime(15865);
        param.setSourcename("eeeeeeeeeeeeeeee");
        param.setCreatename("vvvvvvvvvvvvvvvvv");
        param.setSourceurl("78999999999999999445465653212132465");
        ContentCreateResultVO ccv =  contentService.createContent(param);
    }

    @Test
    public void getnName()
    {
        File tempFile =new File( "D:\\javainstead\\nginx\\nginx-1.15.8\\html\\2019\\01\\14\\2c8cb55079c141f2967481b0328e8b038ecbb00b2add668a981fae4c8abed49.png".trim());
        System.out.println(tempFile.getName());
    }


}