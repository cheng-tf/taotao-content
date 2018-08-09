package com.taotao.springboot.content.export;

import com.taotao.springboot.content.domain.pojo.TbContent;
import com.taotao.springboot.content.domain.result.EasyUIDataGridResult;
import com.taotao.springboot.content.domain.result.TaotaoResult;

import java.util.List;

/**
 * <p>Title: ContentResource</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-05 19:57</p>
 * @author ChengTengfei
 * @version 1.0
 */
public interface ContentResource {

    /**
     * 根据内容分类ID查询内容列表，并进行分页显示
     */
    EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

    /**
     * 添加内容
     */
    TaotaoResult addContent(TbContent content);

    /**
     * 根据内容分类ID查询内容列表
     */
    List<TbContent> getContentByCid(long categoryId);

}
