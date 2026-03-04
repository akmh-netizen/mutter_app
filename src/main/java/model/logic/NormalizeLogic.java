package model.logic;

import java.util.List;

import model.dao.MutterDAO;
import model.data.Mutter;

/**
 * ユーザー名およびつぶやき本文の検索を全角・半角の両方で可能にするロジッククラス
 */
public class NormalizeLogic {

	/**
	 * 全角 → 半角変換（英数字・記号・スペース） 
	 */
	public String toHalfWidth(String s) {
		if (s == null)
			return null;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			// 全角スペース 
			if (c == '　') {
				sb.append(' ');
				continue;
			}

			// 全角ASCII（！〜～）を半角へ 
			if (c >= '！' && c <= '～') {
				sb.append((char) (c - 0xFEE0));
			} else {
				sb.append(c);
			}
			
		}

		return sb.toString();

	}

	/**
	 * 検索用の正規化を行い DAO に渡す 
	 */
	public List<Mutter> search(String user, String keyword) {

		// 検索キーワードを半角に統一 
		String normUser = toHalfWidth(user);
		String normKeyword = toHalfWidth(keyword);
		MutterDAO dao = new MutterDAO();
		
		return dao.findByUserOrKeyword(normUser, normKeyword);
	
	}
	
}
