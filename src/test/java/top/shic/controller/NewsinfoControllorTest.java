package top.shic.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Created by Administrator on 2017/10/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NewsinfoControllorTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void nextPageTest() throws Exception{
//        mvc.perform(MockMvcRequestBuilders.post("/nextPage"));//post怎么测试。。。。
    }

}
