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
import com.taotao.springboot.content.service.cache.CacheService;
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
    private CacheService cacheService;

    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;

    @Value("${TIEM_EXPIRE}")
    private Integer TIEM_EXPIRE;

    @Override
    public TaotaoResult addContent(TbContent content) {
        // #1 补全属性
        content.setCreated( new Date());
        content.setUpdated(new Date());
        // #2 插入，并同步缓存（即删除对应的缓存信息）
        contentMapper.insert(content);
        cacheService.hdel(INDEX_CONTENT, content.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        // #1 设置分页条件
        PageHelper.startPage(page, rows);
        // #2 根据内容分类ID查询内容列表
        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> contentList = contentMapper.selectByExample(contentExample);
        // #3 获取查询结果，并封装
        PageInfo<TbContent> pageInfo = new PageInfo<>(contentList);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(contentList);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<TbContent> getContentByCid(long categoryId) {
        // #1 查询缓存
        try {
            String json = cacheService.hget(INDEX_CONTENT, categoryId + "");
            // 若查询到缓存数据，则将JSON转换成List返回
            if (StringUtils.isNotBlank(json)) {
                return JacksonUtils.jsonToList(json, TbContent.class);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        // #3 若缓存没有命中，则查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExample(example);
        // #4 将查询结果添加到缓存
        try {
            cacheService.hset(INDEX_CONTENT, categoryId + "", JacksonUtils.objectToJson(list));
            //设置过期时间
            cacheService.expire(INDEX_CONTENT, TIEM_EXPIRE);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

