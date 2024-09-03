package com.coursecraft.util;

import java.util.List;

import com.coursecraft.dao.AppDao;
import com.coursecraft.dto.SearchDto;
import com.coursecraft.entity.Course;

public class SearchEngine {

	public List<Course> getSearchResults(AppDao appDao, SearchDto searchDto) {
		return appDao.search(Course.class, "title", searchDto.searchQuery, 12);
	}

}
