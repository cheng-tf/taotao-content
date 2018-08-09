package com.taotao.springboot.content.domain.result;

import java.io.Serializable;

/**
 * <p>Title: EasyUITreeNode</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-05 16:40</p>
 * @author ChengTengfei
 * @version 1.0
 */
public class EasyUITreeNode implements Serializable {

    private static final long serialVersionUID = 1837602574909975350L;

    // ID
    private long id;
    // text
    private String text;
    // state
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

