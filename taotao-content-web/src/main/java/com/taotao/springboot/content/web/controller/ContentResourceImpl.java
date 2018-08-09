package com.taotao.springboot.content.web.controller;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.springboot.content.common.utils.JacksonUtils;
import com.taotao.springboot.content.domain.pojo.TbContent;
import com.taotao.springboot.content.domain.result.EasyUIDataGridResult;
import com.taotao.springboot.content.domain.result.TaotaoResult;
import com.taotao.springboot.content.export.ContentResource;
import com.taotao.springboot.content.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>Title: ContentResourceImpl</p>
 * <p>Description: 内容管理Controller</p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-05 20:07</p>
 * @author ChengTengfei
 * @version 1.0
 */
@Service(interfaceClass = ContentResource.class)
@Controller
public class ContentResourceImpl implements ContentResource{

    private static final Logger log = LoggerFactory.getLogger(ContentResourceImpl.class);

    @Autowired
    private ContentService contentService;

    @Override
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        EasyUIDataGridResult res = null;
        try {
            log.info("根据内容类目ID查询内容列表 分页显示 getContentList categoryId = " + categoryId);
            res = contentService.getContentList(categoryId, page, rows);
            log.info("根据内容类目ID查询内容列表 分页显示 getContentList res = {}", JacksonUtils.objectToJson(res));
        } catch (Exception e){
            log.error("### Call ContentResourceImpl.getContentList error = {}", e);
        }
        return res;
    }

    @Override
    public TaotaoResult addContent(TbContent content) {
        TaotaoResult res = null;
        try {
            log.info("添加内容信息 addContent categoryId = {}" + JacksonUtils.objectToJson(content));
            res = contentService.addContent(content);
            log.info("添加内容信息 addContent res = {}", JacksonUtils.objectToJson(res));
        } catch (Exception e){
            log.error("### Call ContentResourceImpl.addContent error = {}", e);
        }
        return res;
    }

    @Override
    public List<TbContent> getContentByCid(long categoryId) {
        List<TbContent>  res = null;
        try {
            log.info("根据内容类目ID查询内容列表 getContentByCid categoryId = " + categoryId);
            res = contentService.getContentByCid(categoryId);
            log.info("根据内容类目ID查询内容列表 getContentByCid res = {}", JacksonUtils.objectToJson(res));
        } catch (Exception e){
            log.error("### Call ItemResourceImpl.getContentByCid error = {}", e);
        }
        return res;
    }

}
