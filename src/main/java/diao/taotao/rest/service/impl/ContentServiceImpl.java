package diao.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import diao.taotao.common.util.JsonUtils;
import diao.taotao.mapper.TbContentMapper;
import diao.taotao.pojo.TbContent;
import diao.taotao.pojo.TbContentExample;
import diao.taotao.pojo.TbContentExample.Criteria;
import diao.taotao.rest.dao.JedisClient;
import diao.taotao.rest.service.ContenService;

@Service
public class ContentServiceImpl implements ContenService {
    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClientDao;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Override
    public List<TbContent> getContentList(long contentCid) {
        // 从缓存中取内容
        try {
            String result = jedisClientDao.hget(INDEX_CONTENT_REDIS_KEY, contentCid + "");
            if (!StringUtils.isBlank(result)) {
                // 把字符串转换成list
                List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
                return resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 根据内容分类id查询内容列表
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        // 执行查询
        List<TbContent> list = contentMapper.selectByExample(example);

        // 向缓存中添加内容
        try {
            // 把list转换成字符串
            String cacheString = JsonUtils.objectToJson(list);
            jedisClientDao.hset(INDEX_CONTENT_REDIS_KEY, contentCid + "", cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
