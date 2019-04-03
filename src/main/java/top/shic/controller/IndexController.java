package top.shic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.shic.domain.Admin;
import top.shic.domain.Newsinfo;
import top.shic.domain.User;
import top.shic.service.NewsinfoService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 学生也需要登录，登录的学生用户才能使用和查看相关的通知和实时数据
 * Created by Administrator on 2017/10/28.
 */
@Controller
public class IndexController extends WebMvcConfigurerAdapter {

    @Autowired
    private NewsinfoService newsinfoService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
    }

    //任意用户访问主页
    @GetMapping("/")
    public String welcome(){
        System.out.println("没执行？？");
        return "welcome";
    }

    @PostMapping("/userIndex")
    public String index(HttpSession session, Model model) {
        long countOfNewsinfo =  newsinfoService.getAndSetCountOfNewsinfo();
        long minIdOfThisPage = countOfNewsinfo-20;
        User user = (User)session.getAttribute("user");
        model.addAttribute("user",user);
        session.setAttribute("currentPageNum",1);
        model.addAttribute("currentPageNum",1);
        newsinfoService.setCurrentPageNum(1);
        List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
        model.addAttribute("newsinfoList", newsinfoList);
        return "userIndex";
    }

    @GetMapping("/nextIndex")
    public String nextPage(HttpSession session, Model model) {
        User user = (User)session.getAttribute("user");
        Integer currentPageNum = (Integer) session.getAttribute("currentPageNum");
        long countOfNewsinfo =  newsinfoService.getAndSetCountOfNewsinfo();
        long minIdOfThisPage = countOfNewsinfo-20*(currentPageNum.intValue()+1);//搞忘+1了，是觉得一直都只能查两条
        if(minIdOfThisPage<1){
            if(minIdOfThisPage<-20){
                minIdOfThisPage = 0;
                newsinfoService.setCurrentPageNum(currentPageNum);//表明当前页就是最后一页
                List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
                session.setAttribute("currentPageNum", currentPageNum);
                model.addAttribute("currentPageNum", currentPageNum);
                model.addAttribute("newsinfoList", newsinfoList);
                model.addAttribute("user",user);
                return "userIndex";
            }else {
                minIdOfThisPage = 0;
                newsinfoService.setCurrentPageNum(currentPageNum+1);
                List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
                session.setAttribute("currentPageNum", currentPageNum+1);
                model.addAttribute("currentPageNum", currentPageNum+1);
                model.addAttribute("newsinfoList", newsinfoList);
                model.addAttribute("user",user);
                System.out.println("下一页返回前，当前页数："+(currentPageNum+1)+", newsinfoList:"+newsinfoList.toString());
                return "userIndex";
            }
        }else {
            newsinfoService.setCurrentPageNum(currentPageNum+1);
            List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
            session.setAttribute("currentPageNum", currentPageNum+1);
            model.addAttribute("currentPageNum", currentPageNum+1);
            model.addAttribute("newsinfoList", newsinfoList);
            model.addAttribute("user",user);
            System.out.println("下一页返回前，当前页数："+(currentPageNum+1)+", newsinfoList:"+newsinfoList.toString());
            return "userIndex";
        }
    }

    /*
    在前端判断currentPageNum是否小于1，如果是就不执行前一页方法。
    此处做的后台控制器，用了一个if，所以会显得相对麻烦
     */
    @GetMapping("/previousIndex")
    public String previousPage(HttpSession session, Model model) {
        User user = (User)session.getAttribute("user");
        Integer currentPageNum = (Integer) session.getAttribute("currentPageNum");
        if(currentPageNum.intValue()==1){
            long countOfNewsinfo =  newsinfoService.getAndSetCountOfNewsinfo();
            long minIdOfThisPage = countOfNewsinfo-20;
            model.addAttribute("user",user);
            session.setAttribute("currentPageNum", 1);
            model.addAttribute("currentPageNum",1);
            newsinfoService.setCurrentPageNum(1);
            List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
            model.addAttribute("newsinfoList", newsinfoList);
            System.out.println("上一页返回前，当前页数："+(currentPageNum-1)+", newsinfoList:"+newsinfoList.toString());
            return "userIndex";
        }else {
            long countOfNewsinfo =  newsinfoService.getAndSetCountOfNewsinfo();
            long minIdOfThisPage = countOfNewsinfo-20*currentPageNum;
            newsinfoService.setCurrentPageNum(currentPageNum-1);
            List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
            session.setAttribute("currentPageNum", currentPageNum-1);
            model.addAttribute("currentPageNum", currentPageNum-1);
            model.addAttribute("newsinfoList", newsinfoList);
            model.addAttribute("user",user);
            System.out.println("上一页返回前，当前页数："+(currentPageNum-1)+", newsinfoList:"+newsinfoList.toString());
            return "userIndex";
        }
    }

    //查看，点击通知标题即可
    @GetMapping("/userViewOneNews/{id}")
    public String viewOneNews(@PathVariable("id") Integer id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        Newsinfo thisNewsinfo = newsinfoService.findOneById(id);
        model.addAttribute("thisNewsinfo", thisNewsinfo);
        return "userViewOneNews";
    }

    //登出，管理员登出后把存入session里面的admin消除掉，或者让他等于null
    @GetMapping("/userExit")
    public String exit(HttpSession session){
        session.setAttribute("user",new User());
        return "welcome";
    }

}
