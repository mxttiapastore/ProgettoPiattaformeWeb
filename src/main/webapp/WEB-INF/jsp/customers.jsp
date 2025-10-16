<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html><body>
<h2>Clienti</h2>
<form method="post" action="/customers">
    <input name="firstName" placeholder="Nome" required/>
    <input name="lastName"  placeholder="Cognome" required/>
    <button type="submit">Aggiungi</button>
</form>
<ul>
    <c:forEach var="c" items="${customers}">
        <li>${c.firstName} ${c.lastName}</li>
    </c:forEach>
</ul>
<a href="/">Home</a>
</body></html>
