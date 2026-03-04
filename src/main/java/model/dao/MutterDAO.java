package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.data.Mutter;
import model.util.DButil;

/**
 * mutters テーブルの CRUD 操作を担当する DAO クラス
 *
 * ・一覧取得
 * ・新規投稿
 * ・検索（ユーザー名 / 本文）
 * ・ログインユーザーの投稿一覧取得
 * ・ログインユーザーの投稿削除 / 更新
 */
public class MutterDAO {

	/**
	 * つぶやき一覧取得メソッド
	 */
	public List<Mutter> findAll() {
		//DBから取り出した Mutter オブジェクトを順番に詰めていくための ArrayList
		List<Mutter> mutterList = new ArrayList<Mutter>();

		String sql = "SELECT id, username, text FROM mutters ORDER BY id DESC";

		try (
				Connection conn = DButil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			//結果セットを1行ずつ読み込むループ
			while (rs.next()) {
				int id = rs.getInt("id");
				String userName = rs.getString("username");
				String text = rs.getString("text");

				//1行分のデータから Mutter オブジェクトを生成、リストに追加
				Mutter mutter = new Mutter(id, userName, text);
				mutterList.add(mutter);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mutterList;

	}

	/**
	 * 新規つぶやきを登録するメソッド
	 */
	public boolean create(Mutter mutter) {

		String sql = "INSERT INTO mutters(username, text) VALUES(?, ?)";

		try (
				Connection conn = DButil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			//1つ目の ? に値をセット
			ps.setString(1, mutter.getUserName());
			//2つ目の ? に値をセット
			ps.setString(2, mutter.getText());

			// 1件だけ INSERT されれば成功
			return ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * ユーザー名またはつぶやき本文の部分一致で検索するメソッド
	 */
	public List<Mutter> findByUserOrKeyword(String userName, String keyword) {
		List<Mutter> mutterList = new ArrayList<Mutter>();

		StringBuilder sql = new StringBuilder("SELECT id, username, text FROM mutters "
				+ "WHERE 1=1 ");

		// 条件フラグ 
		boolean hasUser = (userName != null && !userName.isEmpty());
		boolean hasKeyword = (keyword != null && !keyword.isEmpty());

		// 全角 → 半角変換用
		String zenkaku = "０１２３４５６７８９"
				+ "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"
				+ "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";
		String hankaku = "0123456789"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "abcdefghijklmnopqrstuvwxyz";

		// ユーザー名検索（部分一致） 
		if (hasUser) {
			sql.append("AND translate(username, '")
					.append(zenkaku)
					.append("', '")
					.append(hankaku)
					.append("') LIKE ? ");
		}

		// 本文検索（部分一致） 
		if (hasKeyword) {
			sql.append("AND translate(text, '")
					.append(zenkaku)
					.append("', '")
					.append(hankaku)
					.append("') LIKE ? ");
		}

		sql.append("ORDER BY id DESC");

		try (
				Connection conn = DButil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());) {

			int index = 1;

			// 正規化済みの値をセット
			if (hasUser) {
				ps.setString(index++, "%" + userName + "%");
			}

			if (hasKeyword) {
				ps.setString(index++, "%" + keyword + "%");
			}

			//SQL文を実行し、その結果セットをrsとして受け取る
			try (ResultSet rs = ps.executeQuery()) {

				//結果セットを1行ずつ読み込むループ
				while (rs.next()) {
					int id = rs.getInt("id");
					String uName = rs.getString("username");
					String text = rs.getString("text");

					//1行分のデータから Mutter オブジェクトを生成、リストに追加
					Mutter mutter = new Mutter(id, uName, text);
					mutterList.add(mutter);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mutterList;

	}

	/**
	 * ログインユーザーの投稿を取得するメソッド
	 */
	public List<Mutter> findByLoginUser(String userName) {
		List<Mutter> list = new ArrayList<>();

		try (Connection conn = DButil.getConnection()) {

			//ログインユーザー本人の投稿を取得
			String sql = "SELECT id, username, text FROM mutters WHERE username = ? ORDER BY id DESC";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);

			ResultSet rs = ps.executeQuery();

			// 1件ずつMutterオブジェクトに詰めてリストへ
			while (rs.next()) {
				Mutter m = new Mutter();
				m.setId(rs.getInt("id"));
				m.setUserName(rs.getString("username"));
				m.setText(rs.getString("text"));
				list.add(m);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}

	/**
	 * ログインユーザーのつぶやきの削除メソッド
	 */
	public boolean delete(int id, String userName) {
		try (Connection conn = DButil.getConnection()) {

			// 削除実行
			String sql = "DELETE FROM mutters WHERE id = ? AND username = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, userName);

			return pstmt.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 編集する投稿を取得する
	 */
	public Mutter findByIdAndUser(int id, String userName) {
		try (Connection conn = DButil.getConnection()) {
			String sql = "SELECT id, username, text FROM mutters WHERE id = ? AND username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, userName);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Mutter m = new Mutter();
				m.setId(rs.getInt("id"));
				m.setUserName(rs.getString("username"));
				m.setText(rs.getString("text"));
				return m;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * つぶやき更新メソッド
	 */
	public boolean update(int id, String userName, String newText) {
		try (Connection conn = DButil.getConnection()) {

			String sql = "UPDATE mutters SET text = ? WHERE id = ? AND username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newText);
			ps.setInt(2, id);
			ps.setString(3, userName);

			return ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}

	}

}
