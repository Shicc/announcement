package top.shic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/13.
 */
@Entity
public class Newsinfo {

    @Id
    @GeneratedValue
    private Integer id;//id

    @Column(length = 20,nullable = false)
    private String topic;//主题

    @Column(length = 20,nullable = false)
    private String title;//标题

    @Column(length = 18,nullable = false)
    private String author;//作者

    @Column(nullable = false)
    private Date createDate;//创建日期

    @Column(length = 2000,nullable = false)
    private String content;//内容

    @Column(length = 200,nullable = false)
    private String summary;//概要

    @Override
    public String toString() {
        return "Newsinfo{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", createDate=" + createDate +
                ", content='" + content + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    public Newsinfo() {
    }

    public Newsinfo(String title, String topic, String summary, String content) {
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.summary = summary;
    }

    public Newsinfo(String topic, String title, String author, Date createDate, String content, String summary) {
        this.topic = topic;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.content = content;
        this.summary = summary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
