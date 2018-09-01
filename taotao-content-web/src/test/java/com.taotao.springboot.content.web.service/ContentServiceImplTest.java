package com.taotao.springboot.content.web.service;

import com.taotao.springboot.content.common.utils.JacksonUtils;
import com.taotao.springboot.content.domain.pojo.TbContent;
import com.taotao.springboot.content.domain.result.EasyUIDataGridResult;
import com.taotao.springboot.content.domain.result.TaotaoResult;
import com.taotao.springboot.content.service.ContentService;
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
 * <p>Title: ContentServiceImplTest</p>
 * <p>Description: ContentService测试类 </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-09-01 13:52</p>
 * @author ChengTengfei
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ContentServiceImplTest {

    @Autowired
    private ContentService contentService;

    private static Logger log = LoggerFactory.getLogger(ContentServiceImplTest.class);

    // 添加内容
    @Test
    public void testAddContent() {
        TbContent content = new TbContent();
        content.setCategoryId(89L);
        content.setTitle("测试标题");
        content.setSubTitle("测试副标题");
        content.setTitleDesc("标题描述");
        content.setUrl("http://www.jd.com");
        content.setPic("http://localhost:9000/images/2015/07/27/1437979301511057.jpg");
        content.setContent("具体内容");
        TaotaoResult result = contentService.addContent(content);
        log.info(JacksonUtils.objectToJson(result));
    }

    // 根据内容分类ID查询内容列表，并进行分页显示
    @Test
    public void testGetContentList() {
        EasyUIDataGridResult result = contentService.getContentList(89L, 0, 30);
        log.info(String.valueOf(result.getTotal()));
        log.info(JacksonUtils.objectToJson(result.getRows()));
    }

    // 根据内容分类ID查询内容列表
    @Test
    public void testGetContentByCid() {
        List<TbContent> list = contentService.getContentByCid(89L);
        log.info(JacksonUtils.objectToJson(list));
    }

}
