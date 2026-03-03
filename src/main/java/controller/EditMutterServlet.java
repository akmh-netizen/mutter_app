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

@WebServlet("/edit")
public class EditMutterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		// 投稿内容を取得（本人チェックも含む） 
		MutterDAO dao = new MutterDAO();
		Mutter mutter = dao.findByIdAndUser(id, loginUser.getUserName());

		if (mutter == null) {
			// 他人の投稿 or 存在しない投稿 
			session.setAttribute("errorMsg", "他人の投稿は編集できません。");
			response.sendRedirect("mutter");
			return;
		}

		// 編集画面へ 
		request.setAttribute("mutter", mutter);
		request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
	}

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
		// 本人の投稿だけ更新 
		MutterDAO dao = new MutterDAO();
		boolean result = dao.update(id, loginUser.getUserName(), newText);
		if (!result) {
			session.setAttribute("errorMsg", "他人の投稿は編集できません。");
		} else {
			// 編集完了メッセージ
			session.setAttribute("editMsg", "投稿を編集しました");
		}
		// 更新後は一覧へ 
		response.sendRedirect("mutter");
	}
}
