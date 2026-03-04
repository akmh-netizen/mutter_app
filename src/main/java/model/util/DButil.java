package model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * データベース接続を管理するユーティリティクラス。
 * 
 * アプリケーション全体で共通して使用する DB 接続処理を一元化する。
 * DAO クラスから呼び出され、PostgreSQL への接続を提供する。
 */
public class DButil {

	/**
	 * PostgreSQL に接続するための JDBC URL
	 * jdbc:postgresql://ホスト名:ポート番号/データベース名 
	 */
	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/mutterdb";

	//データベース接続に使用するユーザー名。
	private static final String DB_USER = "postgres";

	//データベース接続に使用するパスワード。
	private static final String DB_PASS = "postgres";

	/**
	 * DB接続を生成して返す共通メソッド。
	 */
	public static Connection getConnection() throws SQLException {

		try {
			// PostgreSQL JDBC ドライバのロード
			Class.forName("org.postgresql.Driver");
			
		} catch (ClassNotFoundException e) {
			throw new SQLException("PostgreSQL JDBC Driver が見つかりません。", e);
		}

		return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	}
	
}
