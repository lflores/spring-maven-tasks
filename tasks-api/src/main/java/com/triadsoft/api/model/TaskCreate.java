package com.triadsoft.api.model;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 21/04/19 10:09
 */
public class TaskCreate {
    private String description;
    private String image;
    private String status = "todo";

    public TaskCreate() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
