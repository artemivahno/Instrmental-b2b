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


<c:set var="baseUrl"
	value="${pageContext.request.contextPath}/attribute" />
<h4 class="header"><mytaglib:i18n key="attribute.homeName"/></h4>

<div class="row">

	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="searchFormModel">
		<div class="input-field col s4">
			<form:input path="name" type="text" />
			<label for="name"><mytaglib:i18n key="all.searchName" /></label>
		</div>

		<div class="col s4">
			<button class="btn waves-effect waves-light right" type="submit">
				<mytaglib:i18n key="all.search" />
			</button>
		</div>
	</form:form>
</div>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id">
					<mytaglib:i18n key="all.id" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="name">
					<mytaglib:i18n key="all.name" />
				</mytaglib:sort-link></th>
			<th><mytaglib:i18n key="attribute.value" /></th>
			<th><mytaglib:i18n key="all.created" /></th>
			<th><mytaglib:i18n key="all.updated" /></th>
			<th></th>
		</tr>
		<c:forEach var="attribute" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${attribute.id}" /></td>
				<td><c:out value="${attribute.name}" /></td>
				<td><c:out value="${attribute.value}" /></td>
				<sec:authorize access="hasRole('admin')">
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${attribute.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${attribute.updated}" /></td>
				<td class="right"><a class="btn-floating"
					href="${baseUrl}/${attribute.id}"><i class="material-icons">info</i></a>
					<a class="btn-floating" href="${baseUrl}/${attribute.id}/edit"><i
						class="material-icons">edit</i></a> <a class="btn-floating red"
					href="${baseUrl}/${attribute.id}/delete"><i
						class="material-icons">delete</i></a></td>
				</sec:authorize>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jspFragments:paging />
<a class="waves-effect waves-light btn right" href="${baseUrl}/add"><i
	class="material-icons">add</i></a>
