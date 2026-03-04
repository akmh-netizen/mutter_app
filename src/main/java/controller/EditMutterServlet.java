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
import model.data.Mutter;

/**
 * つぶやき編集を担当するコントローラ
 *
 * ・GET：編集画面の表示（本人の投稿のみ）
 * ・POST：編集内容の更新
 */
@WebServlet("/edit")
public class EditMutterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** 
	 *  編集画面の表示（GET） 
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

		// 投稿IDを取得 
		int id = Integer.parseInt(request.getParameter("id"));

		// 投稿内容を取得
		MutterDAO dao = new MutterDAO();
		Mutter mutter = dao.findByIdAndUser(id, loginUser.getUserName());

		// 編集画面へ フォワード
		request.setAttribute("mutter", mutter);
		request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);

	}

	/** 
	 *  編集内容の更新（POST） 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログインチェック 
		HttpSession session = request.getSession(false);
		Login loginUser = (session != null) ? (Login) session.getAttribute("loginUser") : null;

		if (loginUser == null) {
			response.sendRedirect("login");
			return;
		}

		request.setCharacterEncoding("UTF-8");

		// 更新内容を取得 
		int id = Integer.parseInt(request.getParameter("id"));
		String newText = request.getParameter("text");

		// 更新処理
		MutterDAO dao = new MutterDAO();
		boolean result = dao.update(id, loginUser.getUserName(), newText);

		// 編集完了メッセージ
		session.setAttribute("editMsg", "投稿を編集しました");
		
		// 更新後はつぶやき一覧へリダイレクト
		response.sendRedirect("mutter");
		
	}
}
