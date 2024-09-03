package com.coursecraft.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
public class SearchDto {

	@NotNull
	@NotEmpty
	public String searchQuery;

}
