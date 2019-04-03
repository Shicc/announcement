package top.shic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.shic.domain.User;
import top.shic.repository.UserRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by Administrator on 2017/10/28.
 */
@Controller
public class UserController extends WebMvcConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
    }

    @GetMapping("/userLogin")
    public String userLogin(User user){
        return "userLogin";
    }

    @PostMapping("/userLogin")
    public String toUserLogin(@Valid User user, HttpSession session, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "userLogin";
        }else {
            User o = userRepository.findUserByLoginName(user.getLoginName());
            if(o==null){
                return "userLogin";
            }
            if(user.getLoginPwd().equals(o.getLoginPwd())){
                session.setAttribute("user",o);
                session.setAttribute("currentPageNum",1);
                return "forward:/userIndex";
            }else {
                return "userLogin";
            }
        }
    }

}
