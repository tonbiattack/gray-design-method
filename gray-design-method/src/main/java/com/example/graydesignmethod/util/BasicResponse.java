package com.example.graydesignmethod.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 継承して仕様される前提のクラス
 */
@Setter
@Getter
public class BasicResponse {
	/** Errorがない場合でも明示的にfalseを返す */
	private boolean error = false;
	private String errorCode;
	private String errorMessage;
}
