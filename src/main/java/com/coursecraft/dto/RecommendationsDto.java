package com.coursecraft.dto;

import java.util.Map;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
public class RecommendationsDto {

	private String sortKey;
	private Map<String, ?> filters;

}
