package com.guosh.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

@RunWith(SpringRunner.class) //运行器
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void stup(){
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    //测试用例
    @Test
    public void whenQuerSuccess() throws Exception {
        String result=mockMvc.perform(MockMvcRequestBuilders.get("/user")
                //传过去的参数
                .param("username","admin")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //判断请求的状态吗是否成功,200
                .andExpect(MockMvcResultMatchers.status().isOk())
                //判断返回的集合的长度是否是3
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                //打印信息
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        //打印返回结果
        System.out.println(result);
    }


    //用户详情用例
    @Test
    public void whenUserInfoSuccess() throws Exception {
        String result=mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //判断请求的状态吗是否成功,200
                .andExpect(MockMvcResultMatchers.status().isOk())
                //判断返回到username是不是tom
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom"))
                //打印信息
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        //打印返回结果
        System.out.println(result);
    }


    //用户创建用例
    @Test
    public void whenCreateSuccess() throws Exception {
        Date date=new Date();
        String content="{\"username\":\"tom\",\"password\":null,\"birthday\":"+date.getTime()+"}";
        String result=mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //判断请求的状态吗是否成功,200
                .andExpect(MockMvcResultMatchers.status().isOk())
                //判断返回到username是不是tom
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        //打印返回结果
        System.out.println(result);
    }
}