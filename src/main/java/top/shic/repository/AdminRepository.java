package top.shic.repository;

import org.springframework.data.repository.Repository;
import top.shic.domain.Admin;

/**
 * Created by Administrator on 2017/10/13.
 */
public interface AdminRepository extends Repository<Admin,Integer> {

    public Admin findAdminByLoginName(String loginName);

}
