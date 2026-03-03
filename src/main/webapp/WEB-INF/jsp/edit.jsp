<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<h2>投稿を編集</h2>

<form action="edit" method="post">
	<!-- 投稿IDを hidden で送る -->
	<input type="hidden" name="id" value="${mutter.id}">

	<p>
		つぶやき：<br>
		<textarea name="text" rows="3" cols="40" required>${mutter.text}</textarea>
	</p>

	<p>
		<button type="submit">更新する</button>
	</p>
</form>

<a href="mutter">戻る</a>
