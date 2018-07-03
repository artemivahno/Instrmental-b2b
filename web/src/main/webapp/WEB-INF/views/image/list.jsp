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

<c:set var="baseUrl" value="${pageContext.request.contextPath}/image" />

<h4 class="header">Image</h4>

<div class="row">

	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="searchFormModel">
		<div class="input-field col s4">
			<form:input path="name" type="text" />
			<label for="name">NAME</label>
		</div>

		<div class="col s4">
			<button class="btn waves-effect waves-light right" type="submit">search</button>
		</div>
	</form:form>
</div>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id"><mytaglib:i18n key="all.id" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="name"><mytaglib:i18n key="all.name" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="position">position</mytaglib:sort-link></th>
			<th>Image</th>
			<th><mytaglib:i18n key="all.created" /></th>
			<th><mytaglib:i18n key="all.updated" /></th>
			<th></th>
		</tr>
		<c:forEach var="image" items="${listDTO.list}" varStatus="loopCounter">
			<tr>
				<td><c:out value="${image.id}" /></td>
				<td><c:out value="${image.name}" /></td>
				<td><c:out value="${image.position}" /></td>
				<td>
				<div class="img">
  					<img src="${image.url}">
				</div>
				</td>
				<sec:authorize access="hasRole('admin')">
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${image.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${image.updated}" /></td>
				<td class="right"><a class="btn-floating"
					href="${baseUrl}/${image.id}"><i class="material-icons">info</i></a>
					<a class="btn-floating" href="${baseUrl}/${image.id}/edit"><i
						class="material-icons">edit</i></a> <a class="btn-floating red"
					href="${baseUrl}/${image.id}/delete"><i class="material-icons">delete</i></a></td>
					</sec:authorize>
			</tr>
		</c:forEach>
	</tbody>
</table>
<mytags:paging />
<a class="waves-effect waves-light btn right" href="${baseUrl}/add"><i
	class="material-icons">add</i></a>
