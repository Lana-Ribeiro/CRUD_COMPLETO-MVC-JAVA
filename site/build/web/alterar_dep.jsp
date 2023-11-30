
<%@page import="model.Departamento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alteração de departamento</title>
    </head>
    <body>
        <%
           Departamento dep = (Departamento) request.getAttribute("dep");
        %>
        <form method="post" action="ControleDepartamento">
            <input type="hidden" name="flag" value="alt_dep">
            <p>
                <label for="id">Id:*</label>
                <input id="id" type="text" name="id" size="10" maxlength="10" value="<%= dep.getIdDep()%>">
            </p>
            <p>
                <label for="nom">Nome:*</label>
                <input id="nom" type="text" name="nome" size="70" maxlength="70" required value="<%= dep.getNomeDep() %>">
            </p>   
            <p>
                <label for="tel">Telefone:</label>
                <input id="tel" type="tel" name="telefone" size="15" maxlength="15" value="<%= dep.getTelefoneDep() %>">
            </p> 
            <p>
                <input type="submit" value="Salvar Alterações">
            </p>            
        </form>
    </body>
</html>
