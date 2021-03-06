package diao.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import diao.taotao.common.util.JsonUtils;
import diao.taotao.common.util.TaotaoResult;
import diao.taotao.mapper.TbItemDescMapper;
import diao.taotao.mapper.TbItemMapper;
import diao.taotao.mapper.TbItemParamItemMapper;
import diao.taotao.pojo.TbItem;
import diao.taotao.pojo.TbItemDesc;
import diao.taotao.pojo.TbItemParamItem;
import diao.taotao.pojo.TbItemParamItemExample;
import diao.taotao.pojo.TbItemParamItemExample.Criteria;
import diao.taotao.rest.dao.JedisClient;
import diao.taotao.rest.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

    @Autowired
    private JedisClient jedisClient;

    /**
     * 查询商品的基本信息
     */
    @Override
    public TaotaoResult getItemBaseInfo(long itemId) {
        try {
            // 添加缓存逻辑
            // 从缓存中取商品信息，商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
            // 判断是否有值
            if (!StringUtils.isBlank(json)) {
                // 把json转换成java对象
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return TaotaoResult.ok(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据商品id查询商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        // 使用TaotaoResult包装一下
        try {
            // 把商品信息写入缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
            // 设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(item);
    }

    /**
     * 查询商品的描述信息
     */
    @Override
    public TaotaoResult getItemDesc(long itemId) {
        try {
            // 从缓存中取商品信息，商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
            // 判断是否有值
            if (!StringUtils.isBlank(json)) {
                // 把json转换成java对象
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return TaotaoResult.ok(itemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建查询
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        try {
            // 写入缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc",
                    JsonUtils.objectToJson(itemDesc));
            // 设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(itemDesc);
    }

    /**
     * 取商品规格参数信息
     */
    @Override
    public TaotaoResult getItemParam(long itemId) {
        // 添加缓存
        try {
            // 添加缓存逻辑
            // 从缓存中取商品信息，商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
            // 判断是否有值
            if (!StringUtils.isBlank(json)) {
                // 把json转换成java对象
                TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return TaotaoResult.ok(paramItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据商品id查询规格参数
        // 设置查询条件
        TbItemParamItemExample example = new TbItemParamItemExample();
        Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        // 执行查询
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            TbItemParamItem paramItem = list.get(0);
            try {
                // 把商品信息写入缓存
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param",
                        JsonUtils.objectToJson(paramItem));
                // 设置key的有效期
                jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return TaotaoResult.ok(paramItem);
        }
        return TaotaoResult.build(400, "无此商品规格");
    }
}
