<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー登録</title>
</head>
<body>

	<h2>ユーザー登録</h2>

	<!-- RegisterServlet からのエラーメッセージを表示 -->
	<div class="error">${errorMsg}</div>

	<!-- 登録フォーム -->
	<form action="register" method="post">
		<!-- ユーザー名入力欄 -->
		<label>ユーザー名</label><br> <input type="text"
			name="userName" value="${param.userName}" required><br>
		
		<!-- パスワード入力欄 -->
		<label>パスワード</label><br> <input type="password" name="password"
			required>
			<br>

		<!-- 登録ボタン -->
		<input type="submit" value="登録">
	</form>
	</div>

	<!-- ログイン画面へのリンク -->
	<p>
		<a href="login.jsp">ログイン画面へ戻る</a>
	</p>

</body>
</html>