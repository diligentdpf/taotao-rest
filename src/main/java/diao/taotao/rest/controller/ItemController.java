package diao.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import diao.taotao.common.util.TaotaoResult;
import diao.taotao.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 根据Id查询商品基本信息
     */
    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public TaotaoResult getItemBaseInfo(@PathVariable Long itemId) {
        TaotaoResult result = itemService.getItemBaseInfo(itemId);
        return result;
    }

    /**
     * 根据Id查询商品描述信息
     */
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDesc(@PathVariable Long itemId) {
        TaotaoResult result = itemService.getItemDesc(itemId);
        return result;
    }

    /**
     * 根据Id查询商品规格信息
     */
    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public TaotaoResult getItemParam(@PathVariable Long itemId) {
        TaotaoResult result = itemService.getItemParam(itemId);
        return result;
    }
}
