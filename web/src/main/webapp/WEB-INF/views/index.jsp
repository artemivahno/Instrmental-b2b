<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>

<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div>

	<script
		src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"/>"></script>
	<script src="<c:url value="/resources/js/controllers.js"/>"></script>

	<h4>
		<mytaglib:i18n key="home.one" />
	</h4>

	<p>
		<mytaglib:i18n key="home.two" /><li><a href="${baseUrl}?language=en"><mytaglib:i18n key="lang.en" /></a></li>
		<li><a href="${baseUrl}?language=ru"><mytaglib:i18n key="lang.ru" /></a></li>
	</p>

	<p>
		<mytaglib:i18n key="home.three" />
		<a href="/b2b/user/registration"
			title="Link Instrumental"> <mytaglib:i18n key="registrate.btn" /></a>
	</p>
	<p>
		<mytaglib:i18n key="home.four" />
		<a target="_blank" href="${pageContext.request.contextPath}/login"
			title="Link Instrumental"> <mytaglib:i18n key="head.login" /></a>
	</p>
	
	<h2><a target="_blank" href="/b2b/product/list4buy"
			title="List ALL Products"> <mytaglib:i18n key="all.products" /> </a></h2>

</div>
