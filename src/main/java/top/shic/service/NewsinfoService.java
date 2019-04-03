package top.shic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import top.shic.domain.Newsinfo;
import top.shic.repository.NewsinfoJpaSpecificationExecutorRepository;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
@Service
public class NewsinfoService {
    @Autowired
    private NewsinfoJpaSpecificationExecutorRepository newsinfoJpaSpecificationExecutorRepository;

    //记录当前页
    private Integer currentPageNum;

    //总记录数
    private long countOfNewsinfo;

    //获取当前页数,点击下一页和上一页的第一步处理
    public void setCurrentPageNum(Integer currentPageNum){
        this.currentPageNum = currentPageNum;
    }

    /*
    要调用了getListOfCurrentPage()方法后，调用本方法才有效果，
    因为总记录数是在getListOfCurrentPage()方法执行后赋值的。
    这样就可以在控制器里面判断当前页是否<0或者*20>总页数了
     */
    public long getAndSetCountOfNewsinfo() {
        //获取总记录数，用于计算当前页面应该显示的最后一条通知的id，得到id方便查询,该方法返回一个long类型
        countOfNewsinfo =  newsinfoJpaSpecificationExecutorRepository.count();
        return countOfNewsinfo;
    }

    public Newsinfo findOneById(Integer id){
        Newsinfo newsinfo =  newsinfoJpaSpecificationExecutorRepository.findOne(id);
        return newsinfo;
    }

    @Transactional
    public void updateNewsinfoById(String content,Integer id){
        newsinfoJpaSpecificationExecutorRepository.updateNewsinfoById(content,id);
    }

    public void deleteNewsinfoById(Integer id){
//        //调用repository内置的findOne(id);
//        Newsinfo deleteOne =  newsinfoJpaSpecificationExecutorRepository.findOne(id);
//        newsinfoJpaSpecificationExecutorRepository.delete(deleteOne);
//        然而内置的delete有更加牛批的方法直接传一个id进去把它删了，从视图层传当前id
        newsinfoJpaSpecificationExecutorRepository.delete(id);
        //删除方法不屑事务管理也通过
    }

    //获取pageable对象
    public Pageable getPageable(){
        //按照ID的降序来排序，因为最新创建的通知的ID最大
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"id");
        Sort sort = new Sort(order);

        //此方法的第一个参数是传递一个页数，表示显示第几页，从第0页开始，显示20条
        Pageable pageable = new PageRequest(currentPageNum-1,20,sort);
        return pageable;
    }

    //获取Specification对象
    public Specification<Newsinfo> getSpecificationList(long minIdOfThisPage){
        System.out.println("通知总条数："+countOfNewsinfo+",当前页："+currentPageNum);
        Specification<Newsinfo> specification = new Specification<Newsinfo>() {
            @Override
            public Predicate toPredicate(Root<Newsinfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path path = root.get("id");
                //最后一条的id是1，那么要查询到最后一条，minIdOfThisPage表示比这个数大的，当着最小的时候它应该为0
                return cb.gt(path,minIdOfThisPage);
            }
        };
        return specification;
    }

    //返回当前分页的通知集合
    public List<Newsinfo> getListOfCurrentPage(long minIdOfThisPage){
        Page<Newsinfo> page =  newsinfoJpaSpecificationExecutorRepository.findAll(getSpecificationList(minIdOfThisPage),getPageable());
        List<Newsinfo> newsinfoList = page.getContent();//这样才获得了想要查询的当前页所要显示的通知的集合
        return newsinfoList;
    }

    public void saveOneNewsinfo(Newsinfo newsinfo){
        newsinfoJpaSpecificationExecutorRepository.save(newsinfo);
    }

}
