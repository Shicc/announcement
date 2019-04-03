package top.shic.repository;

import org.springframework.data.repository.Repository;
import top.shic.domain.User;

/**
 * Created by Administrator on 2017/10/28.
 */
public interface UserRepository extends Repository<User,Integer> {

    public User findUserByLoginName(String loginName);

    //根据后期情况，来决定是否要保存用户

}
