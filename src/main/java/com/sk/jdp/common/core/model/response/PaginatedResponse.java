package com.sk.jdp.common.core.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginatedResponse<T> {

	/* 조회 페이지 */
	private int page;
	/* 전체 페이지 수 */
	private int pages;
	/* 전체 데이터 건수 */
	private long total;
	/* 조회 데이터 */
	private List<T> results;
}
