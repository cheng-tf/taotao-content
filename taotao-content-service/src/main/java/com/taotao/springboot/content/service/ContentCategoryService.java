package com.taotao.springboot.content.service;

import com.taotao.springboot.content.domain.result.EasyUITreeNode;
import com.taotao.springboot.content.domain.result.TaotaoResult;

import java.util.List;

/**
 * <p>Title: ContentCategoryService</p>
 * <p>Description: 内容类目管理Service</p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-05 19:50</p>
 * @author ChengTengfei
 * @version 1.0
 */
public interface ContentCategoryService {

    /**
     * 获取内容分类列表
     */
    List<EasyUITreeNode> getContentCategoryList(long parentId);

    /**
     * 添加内容分类
     */
    TaotaoResult addContentCategory(Long parentId, String name);

}
