package com.triadsoft.api.model;
/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 18/04/19 09:06
 */
public class TaskUpdate {
    private String description;
    private String image;
    private Boolean resolved;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }
}
