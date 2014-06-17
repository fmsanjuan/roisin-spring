<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<h1>
	<spring:message code="preprocessing.header" />
</h1>

<p>
	Número de reglas Roisin obtenidas: 
	${rules.size()}.
</p>
