package com.taotao.springboot.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.springboot.content.common.utils.JacksonUtils;
import com.taotao.springboot.content.mapper.TbContentMapper;
import com.taotao.springboot.content.domain.pojo.TbContent;
import com.taotao.springboot.content.domain.pojo.TbContentExample;
import com.taotao.springboot.content.domain.result.EasyUIDataGridResult;
import com.taotao.springboot.content.domain.result.TaotaoResult;
import com.taotao.springboot.content.service.ContentService;
import com.taotao.springboot.content.service.jedis.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: ContentServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-05 19:51</p>
 * @author ChengTengfei
 * @version 1.0
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT)
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;

    @Override
    public TaotaoResult addContent(TbContent content) {
        // 补全POJO属性
        content.setCreated( new Date());
        content.setUpdated(new Date());
        // 插入到内容表
        contentMapper.insert(content);
        // 同步缓存，即删除对应的缓存信息
        jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        // 设置分页条件
        PageHelper.startPage(page, rows);
        // 执行查询
        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> contentList = contentMapper.selectByExample(contentExample);
        // 获取查询结果
        PageInfo<TbContent> pageInfo = new PageInfo<>(contentList);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(contentList);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<TbContent> getContentByCid(long categoryId) {
        // 首先，查询缓存，而添加缓存不能影响正常业务逻辑
        try {
            // 查询缓存
            String json = jedisClient.hget(INDEX_CONTENT, categoryId + "");
            // 查询到结果，把JSON转换成List返回
            if (StringUtils.isNotBlank(json)) {
                List<TbContent> list = JacksonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        // 若缓存没有命中，则查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andCategoryIdEqualTo(categoryId);
        // 执行查询
        List<TbContent> list = contentMapper.selectByExample(example);
        // 把结果添加到缓存
        try {
            jedisClient.hset(INDEX_CONTENT, categoryId + "", JacksonUtils.objectToJson(list));
        } catch(Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return list;
    }

}

