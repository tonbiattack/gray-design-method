package com.example.graydesignmethod.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.graydesignmethod.util.PagingList;
import com.example.graydesignmethod.util.QueryUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class BookService {
	@PersistenceContext
	private EntityManager entityManager;

	public PagingList<Book> searchBooksByPage(String id, String title, String author, String genre,
			int publicationYear, int pageNumber, int pageSize,
			String sortKey, String sortDirection) {
		StringBuilder selq = new StringBuilder("SELECT b ");
		StringBuilder cntq = new StringBuilder("SELECT COUNT(b) ");
		StringBuilder fromq = new StringBuilder("FROM Book b ");
		StringBuilder whereq = new StringBuilder("WHERE 1 = 1 ");
		String orderq = getOrderBy(sortKey, sortDirection);

		Map<String, Object> params = new HashMap<>();
		if (!Objects.requireNonNullElse(id, "").isBlank()) {
			whereq.append("AND b.id = :id ");
			params.put("id", id);
		}

		if (!Objects.requireNonNullElse(title, "").isBlank()) {
			whereq.append("AND b.title LIKE :title ");
			params.put("title", "%" + title + "%");
		}
		if (!Objects.requireNonNullElse(author, "").isBlank()) {
			whereq.append("AND b.author LIKE :author ");
			params.put("author", "%" + author + "%");
		}
		if (!Objects.requireNonNullElse(genre, "").isBlank()) {
			whereq.append("AND b.genre LIKE :genre ");
			params.put("genre", "%" + genre + "%");
		}

		if (publicationYear > 0) {
			whereq.append("AND b.publicationYear = :publicationYear ");
			params.put("publicationYear", publicationYear);
		}

		String countJpql = QueryUtil.createJpql(cntq, fromq, whereq);
		String selectJpql = QueryUtil.createJpql(selq, fromq, whereq, orderq);

		int totalCount = QueryUtil.countJpql(countJpql, params, entityManager);
		List<Book> list = QueryUtil.listJpql(selectJpql, params, pageSize, pageNumber, Book.class,
				entityManager);
		return new PagingList<>(list, totalCount, pageNumber, pageSize);
	}

	/**
	 * orderBy句を作成する
	 *
	 * @param sortKey データをどの要素でソートするか この引数はオプション
	 * @param sortDir 昇順にソートするか降順にソートするか。 ASC DESCの2種類に対応
	 */
	private String getOrderBy(String sortKey, String sortDir) {
		switch (sortKey) {
			case "id":
			case "title":
			case "author":
			case "genre":
			case "publicationYear":
				return QueryUtil.createOrderq("ORDER BY b.", sortKey, sortDir);
			// 想定外の値がソートキーに入ってきたときの保険用
			default:
				return QueryUtil.createOrderq("ORDER BY b.", "title", "ASC");
		}
	}
}
