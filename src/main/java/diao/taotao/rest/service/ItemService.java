package diao.taotao.rest.service;

import diao.taotao.common.util.TaotaoResult;

public interface ItemService {
    public TaotaoResult getItemBaseInfo(long itemId);

    public TaotaoResult getItemDesc(long itemId);

    public TaotaoResult getItemParam(long itemId);
}
