package model.logic;

import model.dao.LoginDAO;
import model.data.Login;

/**
 * ログイン実行時のロジッククラス
 */
public class LoginLogic {
	
	public Login login(String userName, String password) {
		// DAO でユーザー名から Login を取得 
		LoginDAO dao = new LoginDAO();
		Login login = dao.findByUserName(userName, password);

		// ユーザー名が存在しない 
		if (login == null) {
			return null;
		}
		
		return login;

	}
	
}
