package com.example.entity;

public class Carousel {

    /**ID */
    private Integer id;
    /**非遗 */
    private Integer heritageId;
    /**图片 */
    private String img;
    private String heritageName;

    public String getHeritageName() {
        return heritageName;
    }

    public void setHeritageName(String heritageName) {
        this.heritageName = heritageName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHeritageId() {
        return heritageId;
    }

    public void setHeritageId(Integer heritageId) {
        this.heritageId = heritageId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

