package top.shic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.shic.domain.Admin;
import top.shic.domain.Newsinfo;
import top.shic.service.NewsinfoService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */
@Controller
public class NewsinfoController extends WebMvcConfigurerAdapter {

    @Autowired
    private NewsinfoService newsinfoService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
    }

    @PostMapping("/adminIndex")
    public String toAdminIndex0(HttpSession session, Model model) {
        long countOfNewsinfo =  newsinfoService.getAndSetCountOfNewsinfo();
        long minIdOfThisPage = countOfNewsinfo-20;
        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin",admin);
        model.addAttribute("currentPageNum",1);
        session.setAttribute("currentPageNum",1);
        newsinfoService.setCurrentPageNum(1);
        List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
        model.addAttribute("newsinfoList", newsinfoList);
        return "adminIndex";
    }

    /*
    因为涉及到第一页的前一页和最后一页的后一页问题，
    所以在每一个分页处理方法里，都首先调用一下
     */
    @GetMapping("/nextPage")
    public String nextPage(HttpSession session, Model model) {
        Admin admin = (Admin)session.getAttribute("admin");
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
                model.addAttribute("admin",admin);
                return "adminIndex";
            }else {
                minIdOfThisPage = 0;
                newsinfoService.setCurrentPageNum(currentPageNum+1);
                List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
                session.setAttribute("currentPageNum", currentPageNum+1);
                model.addAttribute("currentPageNum", currentPageNum+1);
                model.addAttribute("newsinfoList", newsinfoList);
                model.addAttribute("admin",admin);
                System.out.println("下一页返回前，当前页数："+(currentPageNum+1)+", newsinfoList:"+newsinfoList.toString());
                return "adminIndex";
            }
        }else {
            newsinfoService.setCurrentPageNum(currentPageNum+1);
            List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
            session.setAttribute("currentPageNum", currentPageNum+1);
            model.addAttribute("currentPageNum", currentPageNum+1);
            model.addAttribute("newsinfoList", newsinfoList);
            model.addAttribute("admin",admin);
            System.out.println("下一页返回前，当前页数："+(currentPageNum+1)+", newsinfoList:"+newsinfoList.toString());
            return "adminIndex";
        }
    }

    //在前端判断currentPageNum是否小于1，如果是就不执行前一页方法。
    @GetMapping("/previousPage")
    public String previousPage(HttpSession session, Model model) {
        Admin admin = (Admin)session.getAttribute("admin");
        Integer currentPageNum = (Integer) session.getAttribute("currentPageNum");
        if(currentPageNum.intValue()==1){
            long countOfNewsinfo =  newsinfoService.getAndSetCountOfNewsinfo();
            long minIdOfThisPage = countOfNewsinfo-20;
            model.addAttribute("admin",admin);
            session.setAttribute("currentPageNum", 1);
            model.addAttribute("currentPageNum",1);
            newsinfoService.setCurrentPageNum(1);
            List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
            model.addAttribute("newsinfoList", newsinfoList);
            System.out.println("上一页返回前，当前页数："+(currentPageNum-1)+", newsinfoList:"+newsinfoList.toString());
            return "adminIndex";
        }else {
            long countOfNewsinfo =  newsinfoService.getAndSetCountOfNewsinfo();
            long minIdOfThisPage = countOfNewsinfo-20*currentPageNum;
            newsinfoService.setCurrentPageNum(currentPageNum-1);
            List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
            session.setAttribute("currentPageNum", currentPageNum-1);
            model.addAttribute("currentPageNum", currentPageNum-1);
            model.addAttribute("newsinfoList", newsinfoList);
            model.addAttribute("admin",admin);
            System.out.println("上一页返回前，当前页数："+(currentPageNum-1)+", newsinfoList:"+newsinfoList.toString());
            return "adminIndex";
        }
    }

    //跳转添加通知
    @GetMapping("/addNewsinfo")
    public String addNewsinfo(HttpSession session, Model model, Newsinfo newsinfo) {
        Admin admin = (Admin) session.getAttribute("admin");
        System.out.println("session.toString()" + session.toString());
        model.addAttribute("admin", admin);
        return "addNewsinfo";
    }

    //添加通知，提交储存请求
    @PostMapping("/toAddNewsinfo")
    public String toAddNewsinfo(HttpSession session, Model model, @Valid Newsinfo newsinfo, BindingResult bindingResult) {
        System.out.println("进入toAddNewsinfo方法了");
        if (bindingResult.hasErrors()) {
            Admin admin = (Admin) session.getAttribute("admin");
            model.addAttribute("admin", admin);
            return "addNewsinfo";
        } else {
            Admin admin = (Admin) session.getAttribute("admin");
            newsinfo.setAuthor(admin.getLoginName());
            newsinfo.setCreateDate(new Date());
            model.addAttribute("admin", admin);
            newsinfoService.saveOneNewsinfo(newsinfo);
            System.out.println("进入else了。。。");
            return "forward:/adminIndex";
        }
    }

    //查看，点击通知标题即可
    @GetMapping("/adminViewOneNews/{id}")
    public String viewOneNews(@PathVariable("id") Integer id, Model model,HttpSession session) {
        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin",admin);
        Newsinfo thisNewsinfo = newsinfoService.findOneById(id);
        model.addAttribute("thisNewsinfo", thisNewsinfo);
        return "adminViewOneNews";
    }

    //删除，不管成没成功返回到管理员主页
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id,HttpSession session,Model model) {
        //执行删除操作
        newsinfoService.deleteNewsinfoById(id);
        //执行首页查询并返回的操作
        long countOfNewsinfo =  newsinfoService.getAndSetCountOfNewsinfo();
        long minIdOfThisPage = countOfNewsinfo-20;
        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin",admin);
        model.addAttribute("currentPageNum",1);
        newsinfoService.setCurrentPageNum(1);
        List<Newsinfo> newsinfoList = newsinfoService.getListOfCurrentPage(minIdOfThisPage);
        model.addAttribute("newsinfoList", newsinfoList);
        return "adminIndex";
    }

    //修改暂时不做，因为直接删除添加就行了。
    //进去修改页面
    @PostMapping("/updateNewsinfo")
    public String updateNewsinfo(@PathVariable("id") Integer id, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "adminIndex";
        } else {
            Newsinfo thisNewsinfo = newsinfoService.findOneById(id);
            model.addAttribute("thisNewsinfo", thisNewsinfo);
            return "updateNewsinfo";
        }
    }

    //进行修改操作
    @PostMapping("/toUpdateNewsinfo")
    public String toUpdateNewsinfo(@PathVariable("id") Integer id,
                                   @RequestParam("content") String content,
                                   Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateNewsinfo";//修改失败应该重新进去修改页面
        } else {
            newsinfoService.updateNewsinfoById(content, id);
            return "adminIndex";//修改成功后前往管理员主页
        }
    }
}
