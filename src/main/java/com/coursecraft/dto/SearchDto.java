package com.coursecraft.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
public class SearchDto {

	@NotNull
	@NotEmpty
	private String searchQuery;

}
