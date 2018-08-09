package com.taotao.springboot.content.export;

import com.taotao.springboot.content.domain.result.EasyUITreeNode;
import com.taotao.springboot.content.domain.result.TaotaoResult;

import java.util.List;

/**
 * <p>Title: ContentCategoryResource</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-05 19:58</p>
 * @author ChengTengfei
 * @version 1.0
 */
public interface ContentCategoryResource {

    /**
     * 获取内容分类列表
     */
    List<EasyUITreeNode> getContentCategoryList(long parentId);

    /**
     * 添加内容分类
     */
    TaotaoResult addContentCategory(Long parentId, String name);

}
