package diao.taotao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import diao.taotao.common.util.ExceptionUtil;
import diao.taotao.common.util.TaotaoResult;
import diao.taotao.pojo.TbContent;
import diao.taotao.rest.service.ContenService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContenService contentService;

	/**
	 * 根据广告内容查询
	 */
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public TaotaoResult getContentList(@PathVariable Long contentCategoryId) {
		try {
			List<TbContent> list = contentService.getContentList(contentCategoryId);
			return TaotaoResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/httpclient/post", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult httpClientTest(int id) {
		System.err.println("=============id : " + id);
		return TaotaoResult.ok();
	}
}
