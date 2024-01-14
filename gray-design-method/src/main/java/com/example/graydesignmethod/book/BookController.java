package com.example.graydesignmethod.book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.graydesignmethod.util.PagingList;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController {
	private final BookService bookService;

	/**
	 * 指定された検索条件とページネーション情報に基づいて書籍のリストを検索し、その結果をページングされたリストとして返します。
	 *
	 * @param id            書籍のUUID。指定しない場合は、この条件は無視されます。
	 * @param title         書籍のタイトル。指定しない場合は、この条件は無視されます。
	 * @param author        書籍の著者名。指定しない場合は、この条件は無視されます。
	 * @param genre         書籍のジャンル。指定しない場合は、この条件は無視されます。
	 * @param publishYear   出版年。0が指定された場合は、この条件は無
	 *                      視されます。
	 * @param pageNumber    取得するページ番号。デフォルトは1です。
	 * @param pageSize      1ページあたりのアイテム数。デフォルトは100です。
	 * @param sortKey       ソートに使用するキー。指定しない場合は、この条件は無視されます。
	 * @param sortDirection ソートの方向（'ASC'または'DESC'）。デフォルトは'ASC'です。
	 * @return 検索結果のページングリストを含むレスポンスエンティティ。
	 */
	@GetMapping("/search")
	public ResponseEntity<PagingList<Book>> searchBooksByPage(
			@RequestParam(name = "id", required = false) String id,
			@RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "author", required = false) String author,
			@RequestParam(name = "genre", required = false) String genre,
			@RequestParam(name = "publicationYear", defaultValue = "0") int publicationYear,
			@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "100") int pageSize,
			@RequestParam(name = "sortKey", defaultValue = "title") String sortKey,
			@RequestParam(name = "sortDirection", required = false, defaultValue = "ASC") String sortDirection) {

		PagingList<Book> result = bookService.searchBooksByPage(
				id, title, author, genre, publicationYear, pageNumber, pageSize, sortKey, sortDirection);
		return ResponseEntity.ok(result);
	}
}
