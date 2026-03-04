package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.MutterDAO;
import model.data.Login;

/**
 * つぶやき削除を担当するコントローラ
 * 
 * ・ログインユーザー本人の投稿のみ削除可能
 * ・削除後は一覧画面（/mutter）へリダイレクト
 */
@WebServlet("/delete")
public class DeleteMutterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** 
	 * GETリクエストで削除処理を実行 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログインチェック 
		HttpSession session = request.getSession(false);
		Login loginUser = (session != null) ? (Login) session.getAttribute("loginUser") : null;

		if (loginUser == null) {
			response.sendRedirect("login");
			return;
		}

		// ログイン中のユーザー名 
		String loginName = loginUser.getUserName();

		// 削除対象の投稿IDを取得 
		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);

		// 削除処理 
		MutterDAO dao = new MutterDAO();
		boolean result = dao.delete(id, loginName);

		// 削除後はつぶやき一覧へリダイレクト
		response.sendRedirect("mutter");

	}

}
