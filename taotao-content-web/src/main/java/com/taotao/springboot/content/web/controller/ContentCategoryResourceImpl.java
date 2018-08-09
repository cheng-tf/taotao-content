package com.taotao.springboot.content.web.controller;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.springboot.content.common.utils.JacksonUtils;
import com.taotao.springboot.content.domain.result.EasyUITreeNode;
import com.taotao.springboot.content.domain.result.TaotaoResult;
import com.taotao.springboot.content.export.ContentCategoryResource;
import com.taotao.springboot.content.service.ContentCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>Title: ContentCategoryResourceImpl</p>
 * <p>Description: 内容类目管理Controller</p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-05 20:10</p>
 * @author ChengTengfei
 * @version 1.0
 */
@Service(interfaceClass = ContentCategoryResource.class)
@Controller
public class ContentCategoryResourceImpl implements ContentCategoryResource{

    private static final Logger log = LoggerFactory.getLogger(ContentCategoryResourceImpl.class);

    @Autowired
    private ContentCategoryService contentCategoryService;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        List<EasyUITreeNode>  res = null;
        try {
            log.info("内容类目列表 getContentCategoryList parentId = " + parentId);
            res = contentCategoryService.getContentCategoryList(parentId);
            log.info("内容类目列表 getContentCategoryList res = {}", JacksonUtils.objectToJson(res));
        } catch (Exception e){
            log.error("### Call ItemResourceImpl.getContentCategoryList error = {}", e);
        }
        return res;
    }

    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        TaotaoResult  res = null;
        try {
            log.info("添加内容类目 addContentCategory parentId = {}, name = {}", String.valueOf(parentId), name);
            res = contentCategoryService.addContentCategory(parentId, name);
            log.info("添加内容类目 addContentCategory res = {}", JacksonUtils.objectToJson(res));
        } catch (Exception e){
            log.error("### Call ItemResourceImpl.addContentCategory error = {}", e);
        }
        return res;
    }

}
