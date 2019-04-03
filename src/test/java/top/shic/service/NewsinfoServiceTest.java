package top.shic.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2017/10/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsinfoServiceTest {

    @Autowired
    private NewsinfoService newsinfoService;

    @Test
    public void updateNewsinfoByTest(){
        newsinfoService.updateNewsinfoById("updateTest",103);
    }

    @Test
    public void deleteOneById(){
        newsinfoService.deleteNewsinfoById(10);
    }

}
