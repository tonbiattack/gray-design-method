package com.example.graydesignmethod.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * APIレスポンスとして返すクラス
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectResponse<T> extends BasicResponse {
	private T obj;
}
