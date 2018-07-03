
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" trimDirectiveWhitespaces="true"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>
	
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>

<%-- <div class="container">
	<div class="wrapper" id="content">
		<div class="inner">
			<!-- <img src="/images/facepalm.png" /> -->
			<spring:message code="server.internal_error_msg" />
		</div>
	</div>
</div> --%>

<div>
	<h1>
		Not found <span>:(  404</span>
	</h1>
	<div class="container">
		<p>Sorry, but the page you were trying to view does not exist.</p>
		<p>It looks like this was the result of either:</p>
		<ul>
			<li>a mistyped address</li>
			<li>an out-of-date link</li>
		</ul>
		<p>
			<a href="/b2b/">to "HOME" page</a>
		</p>
		<script>
			var GOOG_FIXURL_LANG = (navigator.language || '').slice(0, 2), GOOG_FIXURL_SITE = location.host;
		</script>
		<script src="//linkhelp.clients.google.com/tbproxy/lh/wm/fixurl.js"></script>
	</div>
</div>