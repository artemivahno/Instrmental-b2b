<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>

<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


	<c:set var="baseUrl"
		value="${pageContext.request.contextPath}/feedback" />
	<h3 class="header">Feedback</h3>
	
	<form action="${pageContext.request.contextPath}" method="POST">
		<form:input type="text" name="address" value="${param.address}"
			${not empty messages.succes ? 'disabled' : ''}/>
		<p class="error">${messages.address}</p>
		
		<button class="btn waves-effect waves-light right" type="submit">
						<mytaglib:i18n key="allbtn.save" />
					</button>
		<p class="succes">${messages.succes}</p>
	</form>


