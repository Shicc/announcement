package top.shic.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.shic.domain.Admin;

import javax.validation.constraints.Null;

/**
 * Created by Administrator on 2017/10/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void findAdminByLoginNameTest(){
        Admin adminTest = new Admin("shichi","123456");
        Admin admin =  adminRepository.findAdminByLoginName(adminTest.getLoginName());
        //System.out.println("admin查询结果:"+admin.getLoginName()+" , "+admin.getLoginPwd());
        //找到同名的用户后还需要判断密码是都一样
        /*
        adminTest.getLoginPwd() == admin.getLoginPwd()
        如果这样判断两个字符串的话肯定是不行的，都是测试失败的
        应该用字符串的equals方法
         */
        if(adminTest.getLoginPwd().equals(admin.getLoginPwd())){
            System.out.println("登录成功，欢迎"+admin.getLoginName());
        }else {
            System.out.println("登录失败");
        }
    }



}
