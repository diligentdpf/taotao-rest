package diao.taotao.rest.service;

import diao.taotao.common.util.TaotaoResult;

public interface RedisService {
    public TaotaoResult syncContent(long contentCid);
}
