package net.sourov.storyapp.model;

public class Categories {

    String catid,catimage,name;


    public Categories() {
    }

    public Categories(String catid, String catimage, String name) {
        this.catid = catid;
        this.catimage = catimage;
        this.name = name;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCatimage() {
        return catimage;
    }

    public void setCatimage(String catimage) {
        this.catimage = catimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
