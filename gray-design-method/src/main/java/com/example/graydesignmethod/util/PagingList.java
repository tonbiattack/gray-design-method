package com.example.graydesignmethod.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PagingList<T> {
	/** 指定のページのデータのリスト */
	private final List<T> pageData;
	/** 条件に合致するレコード総数*/
	private final long totalCount;
	/** 現在ページ */
	private final int pageNumber;
	/** 合計ページ数 */
	private final int totalPages;
}
