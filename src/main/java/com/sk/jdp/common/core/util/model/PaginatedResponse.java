package com.sk.jdp.common.core.util.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginatedResponse<T> {

	int page;
	int records;
	long total;
	List<T> rows;
}
