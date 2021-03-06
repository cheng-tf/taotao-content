package com.taotao.springboot.content.service.impl;

import com.taotao.springboot.content.mapper.TbContentCategoryMapper;
import com.taotao.springboot.content.domain.pojo.TbContentCategory;
import com.taotao.springboot.content.domain.pojo.TbContentCategoryExample;
import com.taotao.springboot.content.domain.result.EasyUITreeNode;
import com.taotao.springboot.content.domain.result.TaotaoResult;
import com.taotao.springboot.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: ContentCategoryServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-05 19:53</p>
 * @author ChengTengfei
 * @version 1.0
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT)
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        // #1 根据父类目ID查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        // #2 设置查询条件，完成查询
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        // #3 封装查询结果
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory contentCategory : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent()?"closed":"open");
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        // #1 创建POJO对象，补全属性
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setStatus(1);			// 状态。可选值:1(正常),2(删除)
        contentCategory.setSortOrder(1);		// 排序，默认为1
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        // #2 插入
        contentCategoryMapper.insert(contentCategory);
        // #3 判断父节点的状态，并更新
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            // 若父节点为叶子节点，则应改为父节点
            parent.setIsParent(true);
            // 更新父节点
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        return TaotaoResult.ok(contentCategory);
    }

}
