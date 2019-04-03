package top.shic.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.shic.domain.Admin;
import top.shic.domain.Newsinfo;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsinfoJpaSpecificationExecutorRepositoryTest {

    @Autowired
    private NewsinfoJpaSpecificationExecutorRepository newsinfoJpaSpecificationExecutorRepository;

    @Test
    public void findAllTest(){
        List<Newsinfo> newsinfoList = newsinfoJpaSpecificationExecutorRepository.findAll();
        for(Newsinfo newsinfo:newsinfoList){
            System.out.println(newsinfo);
        }
    }

    @Test
    public void findNewsinfoByTopicTest(){
        List<Newsinfo> newsinfoList = newsinfoJpaSpecificationExecutorRepository.findNewsinfoByTopic("hjdsaf");
        for (Newsinfo newsinfo:newsinfoList){
            System.out.println(newsinfo);
        }

    }

    @Test
    public void findNewsinfoByTitleTest(){
        Newsinfo newsinfo0 = newsinfoJpaSpecificationExecutorRepository.findNewsinfoByTitle("usdbhasbdjh");
        Newsinfo newsinfo1 = newsinfoJpaSpecificationExecutorRepository.findNewsinfoByTitle("");
        System.out.println(newsinfo0);
        System.out.println(newsinfo1);
    }

    @Test
    public void findNewsinfoByCreateDateTest(){
        //让输入一个日期然后传进来,得到一个当天创建得通知列表
        Date date = new Date();
        List<Newsinfo> newsinfoList = newsinfoJpaSpecificationExecutorRepository.findNewsinfoByCreateDate(date);
        for (Newsinfo newsinfo:newsinfoList){
            System.out.println(newsinfo);
        }
    }

    //5.测试添加通知的方法，创建一个Newsinfo对象再调用相应repository的save方法
    @Test
    public void saveNewsinfo(){
//        Newsinfo newsinfo = new Newsinfo("TopicNew0","titleNew0","authorNew0",new Date(),"contentNew0","summaryNew0");
//        newsinfoJpaSpecificationExecutorRepository.save(newsinfo);

        Newsinfo newsinfo1 = null;
        for(Integer i=0;i<100;i++){
            //直接把Integer赋值给String会报错的，需要用到Integer的toString方法（这就是用包装类的好处）
            newsinfo1 = new Newsinfo(i.toString(),i.toString(),i.toString(),new Date(),i.toString(),i.toString());
            newsinfoJpaSpecificationExecutorRepository.save(newsinfo1);
        }
    }

}
