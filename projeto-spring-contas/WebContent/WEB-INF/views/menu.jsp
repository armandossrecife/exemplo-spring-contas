<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<h2>Página inicial da Lista de Contas</h2>
<p>Bem vindo, ${usuarioLogado.login}</p>
<a href="listaContas">Lista de contas</a>
<br>
<a href="formularioConta">Nova conta</a>
<br>
<a href="logout">Logout</a>
</body>
</html>