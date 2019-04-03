package top.shic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.shic.domain.Admin;
import top.shic.repository.AdminRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 仅提供登录功能，暂不提供注册
 * 管理员账户目前由底层预留
 * Created by Administrator on 2017/10/13.
 */
@Controller
public class AdminController extends WebMvcConfigurerAdapter {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/adminLogin")
    public String login(Admin admin){
        return "adminLogin";
    }

    @PostMapping("/adminLogin")
    public String toLogin(@Valid Admin admin, HttpSession session, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "adminLogin";
        }else {
            Admin o = adminRepository.findAdminByLoginName(admin.getLoginName());
            if(o==null){ //没有这个判断的话，乱输入就要报错，空输出登录也要报错
                return "adminLogin";
            }
            if(admin.getLoginPwd().equals(o.getLoginPwd())){//判断的顺序要写对
                session.setAttribute("admin",o);
                session.setAttribute("currentPageNum",1);
                //登录成功后返回首页，页码是1，从后台得到当前页的参数值，传入前台，方便进行下一页和上一页的点击
                return "forward:/adminIndex";//登录成功后返回到管理员首页，也就是amdinIndex
            }else {
                return "adminLogin";
            }
        }
    }

    //登出，管理员登出后把存入session里面的admin消除掉，或者让他等于null
    @GetMapping("/adminExit")
    public String exit(HttpSession session){
        session.setAttribute("admin",new Admin());
        return "welcome";
    }

}
