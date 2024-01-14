package com.example.graydesignmethod.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class QueryUtil {

	private static final String SPACE = " ";

	/**
	 * JPQLを発行し条件に合致するデータの件数を取得する
	 */
	public static int countJpql(String cntQuery, Map<String, Object> params, EntityManager em) {
		TypedQuery<Long> query = createTypedQuery(em, cntQuery, params, Long.class);
		return query.getSingleResult().intValue();
	}

	/**
	 * JPQLを発行し条件に合致するデータを複数件取得する（ページング処理あり）
	 */
	public static <T> List<T> listJpql(String selQuery, Map<String, Object> params, int pageSize, int pageNumber,
			Class<T> cls, EntityManager em) {
		// 1ベースのページ番号を0ベースに変換
		int pageIndex = Math.max(0, pageNumber - 1);
		TypedQuery<T> query = createTypedQuery(em, selQuery, params, cls);
		if (pageSize > 0) {
			query.setMaxResults(pageSize);
			query.setFirstResult(pageSize * pageIndex);
		}
		return query.getResultList();
	}

	/**
	 * JPQLを発行し条件に合致するデータを複数件取得する（ページング処理なし）
	 */
	public static <T> List<T> listJpql(String selQuery, Map<String, Object> params, Class<T> cls, EntityManager em) {
		TypedQuery<T> query = createTypedQuery(em, selQuery, params, cls);
		return query.getResultList();
	}

	/**
	 * 共通のTypedQuery作成ロジック
	 */
	private static <T> TypedQuery<T> createTypedQuery(EntityManager em, String queryStr, Map<String, Object> params,
			Class<T> cls) {
		TypedQuery<T> query = em.createQuery(queryStr, cls);
		params.forEach(query::setParameter);
		return query;
	}

	/**
	 * 文字列を結合してJPQLを作成する
	 */
	public static String createJpql(CharSequence... sqls) {
		return Stream.of(sqls)
				.map(CharSequence::toString)
				.map(sql -> sql.endsWith(SPACE) ? sql : sql + SPACE)
				.collect(Collectors.joining());
	}

	/**
	 * 文字列を結合してorder by句を作成する
	 */
	public static String createOrderq(CharSequence baseOrderq, CharSequence sortKey, CharSequence sortDir) {
		return Stream.of(baseOrderq, sortKey, sortDir)
				.map(CharSequence::toString)
				.map(str -> str.endsWith(SPACE) ? str : str + SPACE)
				.collect(Collectors.joining());
	}
}
