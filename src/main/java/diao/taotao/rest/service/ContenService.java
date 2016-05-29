package diao.taotao.rest.service;


import java.util.List;

import diao.taotao.pojo.TbContent;

public interface ContenService {
	public List<TbContent> getContentList(long contentCid);
}
