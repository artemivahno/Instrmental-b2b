<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="mytaglib" uri="https://journaldev.com/jsp/tlds/mytags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><tiles:insertAttribute name="title" /></title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.min.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/custom.css">
<script
	src="${pageContext.request.contextPath}/resources/js/init-materialize-forms.js"></script>
</head>
<body>
	<tiles:insertAttribute name="header" />
	<main>
	<div class="container">
	<%-- <tiles:insertAttribute name="menu" /> --%>
		<tiles:insertAttribute name="body" />
	</div>
	</main>
	<tiles:insertAttribute name="footer" />
</body>
</html>