package com.mbc.leteatgo.repository;

import java.util.List;
import java.util.Map;

import com.mbc.leteatgo.domain.PageVO;

public interface PagingDAO {
	List<PageVO> getPageList(Map<String, Integer> map);
	int getCount();
}
