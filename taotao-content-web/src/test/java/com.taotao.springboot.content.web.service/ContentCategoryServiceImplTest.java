package com.taotao.springboot.content.web.service;

import com.taotao.springboot.content.common.utils.JacksonUtils;
import com.taotao.springboot.content.domain.result.EasyUITreeNode;
import com.taotao.springboot.content.domain.result.TaotaoResult;
import com.taotao.springboot.content.service.ContentCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title: ContentCategoryServiceImplTest</p>
 * <p>Description: ContentCategoryService测试类 </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-09-01 14:08</p>
 * @author ChengTengfei
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ContentCategoryServiceImplTest {

    @Autowired
    private ContentCategoryService contentCategoryService;

    private static Logger log = LoggerFactory.getLogger(ContentCategoryServiceImplTest.class);

    // 获取内容分类列表
    @Test
    public void testGetContentCategoryList() {
        List<EasyUITreeNode> list =  contentCategoryService.getContentCategoryList(0L);
        List<EasyUITreeNode> list2 =  contentCategoryService.getContentCategoryList(86L);
        log.info(JacksonUtils.objectToJson(list));
        log.info(JacksonUtils.objectToJson(list2));
    }

    // 添加内容分类
    @Test
    public void testAddContentCategory() {
        TaotaoResult result = contentCategoryService.addContentCategory(86L, "内容类目测试");
        log.info(JacksonUtils.objectToJson(result));
    }

}