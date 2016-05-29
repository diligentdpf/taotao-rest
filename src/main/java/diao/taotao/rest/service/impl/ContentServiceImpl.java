package diao.taotao.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diao.taotao.mapper.TbContentMapper;
import diao.taotao.pojo.TbContent;
import diao.taotao.pojo.TbContentExample;
import diao.taotao.pojo.TbContentExample.Criteria;
import diao.taotao.rest.service.ContenService;

@Service
public class ContentServiceImpl implements ContenService {
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public List<TbContent> getContentList(long contentCid) {
		// 根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		// 执行查询
		List<TbContent> list = contentMapper.selectByExample(example);

		return list;
	}
}
