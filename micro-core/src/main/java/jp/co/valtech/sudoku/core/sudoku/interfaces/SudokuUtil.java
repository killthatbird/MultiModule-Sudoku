package jp.co.valtech.sudoku.core.sudoku.interfaces;

import jp.co.valtech.sudoku.core.config.CommonConstant;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface SudokuUtil {

	/**
	 * @param numberPlace
	 * @return
	 */
	@NonNull
	default String createAnswerKey(int[][] numberPlace) {
		StringBuilder answerKey = new StringBuilder();
		for (int[] arrays : numberPlace) {
			for (int array : arrays) {
				answerKey.append(array);
			}
		}
		return answerKey.toString();
	}

	/**
	 * SHA256変換した文字列を返却します。
	 *
	 * @param str
	 * 				文字列
	 * @return　SHA256変換した文字列かnull
	 */
	@Nullable
	default String convertToSha256(@NonNull String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes(CommonConstant.UTF8));
			byte[] cipherByte = md.digest();
			StringBuilder hash = new StringBuilder(2 * cipherByte.length);
			for (byte b : cipherByte) {
				hash.append(String.format("%02x", b & 0xff));
			}
			return hash.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
