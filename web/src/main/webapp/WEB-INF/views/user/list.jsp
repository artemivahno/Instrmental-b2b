<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/user" />
<h4 class="header">User</h4>

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="searchFormModel">
		<div class="input-field col s4">
			<form:input path="name" type="text" />
			<label for="name"><mytaglib:i18n key="all.name" /></label>
		</div>
		
		<%-- <div class="input-field col s4">
			<form:input path="email" type="text" />
			<label for="email">E-MAIL</label>
		</div> --%>
		<%-- div class="input-field col s6">
			<div class="switch">
				<label> Off <form:checkbox path="enabled" /> <span
					class="lever"></span> On
				</label>
			</div>
			<label class="switch-label">sold</label> <br />
		</div> --%>
		<div class="col s4">
			<button class="btn waves-effect waves-light right" type="submit"><mytaglib:i18n key="all.search" /></button>
		</div>
	</form:form>
</div>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:i18n key="all.id" /></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="name">
					<mytaglib:i18n key="all.name" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="email">
					e-mail
				</mytaglib:sort-link></th>
			<th>enabled</th>
			<th>role</th>
			<th><mytaglib:i18n key="all.created" /></th>
			<th><mytaglib:i18n key="all.updated" /></th>
			<th>Action</th>

		</tr>
		<c:forEach var="user_account" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${user_account.id}" /></td>
				<td><c:out value="${user_account.name}" /></td>
				<td><c:out value="${user_account.email}" /></td>
				<td><c:out value="${user_account.enabled}" /></td>
				<td><c:out value="${user_account.role}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${user_account.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${user_account.updated}" /></td>
				<sec:authorize access="hasRole('admin')">
				<td class="right"><a class="btn-floating"
					href="${baseUrl}/${user_account.id}"><i class="material-icons">info</i></a>
					<a class="btn-floating" href="${baseUrl}/${user_account.id}/edit"><i
						class="material-icons">edit</i></a> <a class="btn-floating red"
					href="${baseUrl}/${user_account.id}/delete"><i
						class="material-icons">delete</i></a></td>
						</sec:authorize>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jspFragments:paging />
<a class="waves-effect waves-light btn right" href="${baseUrl}/add"><i
	class="material-icons">add</i></a>
