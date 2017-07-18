<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Adicionar Conta</title>
</head>
<body>
	<h3>Adiciona Conta</h3>
	<form action="adicionaConta" method="post">
	Descrição: <textarea name="descricao" rows="5" cols="100"></textarea>
	 <form:errors path="conta.descricao" />
	<br>
	Valor: <input type="text" name="valor"/>
	<br>
	Tipo: <select name="tipo">
		<option name="ENTRADA">ENTRADA</option>
		<option name="SAIDA">SAIDA</option>
	</select>
	<br>
	<input type="submit" value="Adicionar"/>
	</form>
	<a href="home">Home Principal</a>
</body>
</html>