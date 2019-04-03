package top.shic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.shic.domain.Newsinfo;

import java.util.Date;
import java.util.List;

/**
 * Repository层应该是放对数据库的操作，具体调用放在service层，控制层再去调用service层方法
 * Created by Administrator on 2017/10/13.
 */
public interface NewsinfoJpaSpecificationExecutorRepository
        extends JpaRepository<Newsinfo,Integer> , JpaSpecificationExecutor<Newsinfo>{
    //1.查询所有通知，findAll(),直接提供不用写了
    //2.根据主题查询通知，同一个主题应该有很多个通知Id
    public List<Newsinfo> findNewsinfoByTopic(String topic);

    //3.通过标题查询通知
    public Newsinfo findNewsinfoByTitle(String title);

    //4.通过创建日期查询
    public List<Newsinfo> findNewsinfoByCreateDate(Date date);

    //5.管理员添加通知,有内置的save方法

    /*
     6.根据标题找到通知,管理员修改该通知，点击某一条通知旁边的修改按钮时，传递该通知的id或者标题
     修改后方法返回一个数值
     */
    @Modifying
    @Query("update Newsinfo o set o.content = :content where o.id = :id")
    public void updateNewsinfoById(@Param("content") String content, @Param("id") Integer id);

    /*
     7.管理员删除通知,有内置的delete(Newsinfo newsinfo)方法,
     在视图层上点击删除，传进一个id，delete(id),删除它
     */

}
