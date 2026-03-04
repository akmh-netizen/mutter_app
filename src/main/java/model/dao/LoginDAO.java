package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.data.Login;
import model.util.DButil;

/**
 * ログイン処理、ログインユーザー登録メソッドを担当するDAOクラス
 */
public class LoginDAO {

	/**
	 * 指定したユーザー名およびパスワードのユーザーを取得するメソッド
	 */
	public Login findByUserName(String userName, String password) {
		String sql = "SELECT loginid, username, password "
				+ "FROM login "
				+ "WHERE username = ? "
				+ "AND password = ?";

		try (Connection conn = DButil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, userName);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			//ログイン成功
			if (rs.next()) {
				Login login = new Login();
				login.setLoginId(rs.getInt("loginid"));
				login.setUserName(rs.getString("username"));
				login.setPassword(rs.getString("password"));

				return login;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		//ログイン失敗
		return null;

	}

	/**
	 * 新規ユーザーを登録するメソッド
	 */
	public boolean insertUser(String userName, String password) {

		String sql = "INSERT INTO login (username, password) VALUES (?, ?)";

		try (Connection conn = DButil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, userName);
			ps.setString(2, password);

			int result = ps.executeUpdate();
			return result == 1;

		} catch (SQLException e) {
			e.printStackTrace();

			return false;

		}

	}

}
