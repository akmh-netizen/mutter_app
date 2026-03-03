<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>

<% 
String logoutMsg = (String) request.getAttribute("logoutMsg"); 
if (logoutMsg != null) { 
%> 
<script> alert("<%= logoutMsg %>"); 
</script> 
<% 
} 
%>

</head>
<body>

	<h2>ログイン</h2>

	<div class="form-box">

		<!-- LoginServlet からのエラーメッセージを表示 -->
		<div class="error">${errorMsg}</div>

		<!-- LoginServlet からのエラーメッセージを表示 -->
		<p style="color: red;">${errorMsg}</p>
		<!-- ログインフォーム -->

		<!-- action="login" → LoginServlet の doPost に送られる -->
		<form action="login" method="post">

			<!-- ユーザー名入力欄 -->
			<p>
				ユーザー名： <input type="text" name="userName" required>
			</p>

			<!-- パスワード入力欄 -->
			<p>
				パスワード： <input type="password" name="password" required>
			</p>

			<!-- 送信ボタン -->
			<p>
				<input type="submit" value="ログイン">
			</p>
		</form>

	</div>

	<!-- 新規登録へのリンク -->
	<p>
		<a href="register">新規登録はこちら</a>
	</p>

</body>
</html>