package com.szhengzhu.bean.base;

import java.io.Serializable;

import com.szhengzhu.core.Contacts;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Jehon Zeng
 */
@Data
public class ActionItem implements Serializable {

    private static final long serialVersionUID = -8422987392583600681L;

    private String markId;

    private Integer sort;

    @NotBlank
    private String actionCode;

    private Integer serverType;

    private String content;

    private String templateMark;

    private String imagePath;

    private String title;

    private String imageUrl;

    private String linkUrl;

    private String description;

    public ActionItem() {
//        this.serverType = Contacts.ACTION_TEXT_TYPE;
    }
    
    public ActionItem(Integer serverType) {
        this.serverType = serverType;
    }

    public String getContent() {
        return Contacts.ACTION_TEXT_TYPE.equals(this.serverType) ? this.content : null;
    }

    public String getTitle() {
        return Contacts.ACTION_ARTICLE_TYPE.equals(this.serverType) ? this.title : null;
    }

    public String getImagePath() {
        return Contacts.ACTION_IMAGE_TYPE.equals(this.serverType) ? this.imagePath : null;
    }

    public String getImageUrl() {
        return Contacts.ACTION_ARTICLE_TYPE.equals(this.serverType) ? this.imageUrl : null;
    }

    public String getLinkUrl() {
        return Contacts.ACTION_ARTICLE_TYPE.equals(this.serverType) ? this.linkUrl : null;
    }

    public String getDescription() {
        return Contacts.ACTION_ARTICLE_TYPE.equals(this.serverType) ? this.description : null;
    }
}