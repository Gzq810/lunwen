package com.example.entity;

public class News {

    /**ID */
    private Integer id;
    /**资讯标题 */
    private String title;
    /**资讯简介 */
    private String introduce;
    /**资讯内容 */
    private String content;
    /**资讯封面 */
    private String img;
    /**发布时间 */
    private String time;
    /**浏览量 */
    private Integer views;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
