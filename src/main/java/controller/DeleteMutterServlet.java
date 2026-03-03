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

@WebServlet("/delete")
public class DeleteMutterServlet extends HttpServlet {
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

		// ログイン中のユーザー名 
		String loginName = loginUser.getUserName();

		// 投稿IDを取得 
		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);

		// 削除処理 
		MutterDAO dao = new MutterDAO();
		boolean result = dao.delete(id, loginName);

		// 削除できなかった場合（他人の投稿 or 存在しない投稿） 
		if (!result) {
			request.setAttribute("errorMsg", "他人の投稿は削除できません。");
			request.getRequestDispatcher("/WEB-INF/jsp/mutter.jsp").forward(request, response);
			return;
		}
		
		// 削除後は mutter に戻る 
		response.sendRedirect("mutter");

	}

}
