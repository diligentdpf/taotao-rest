package diao.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diao.taotao.mapper.TbItemCatMapper;
import diao.taotao.pojo.TbItemCat;
import diao.taotao.pojo.TbItemCatExample;
import diao.taotao.pojo.TbItemCatExample.Criteria;
import diao.taotao.rest.pojo.CatNode;
import diao.taotao.rest.pojo.CatResult;
import diao.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {


    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public CatResult getItemCatList() {
        CatResult catResult = new CatResult();
        // 查询分类列表
        catResult.setData(getCatList(0));
        return catResult;
    }

    /**
     * 查询分类列表
     * <p>
     * Title: getCatList
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param parentId
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private List<?> getCatList(long parentId) {
        // 创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        // 执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        // 返回值list
        List resultList = new ArrayList<>();
        // 向list中添加节点
        int count = 0;
        for (TbItemCat tbItemCat : list) {
            // 判断是否为父节点, 不是父节点，肯定就是叶子节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>"
                            + tbItemCat.getName() + "</a>");
                } else {
                    catNode.setName(tbItemCat.getName());
                }
                catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
                catNode.setItem(getCatList(tbItemCat.getId()));// 递归...
                count++;
                resultList.add(catNode);
                if (parentId == 0 && count == 14) {
                    break;
                }
                // 如果是叶子节点
            } else {
                resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
            }
        }
        return resultList;
    }

}
