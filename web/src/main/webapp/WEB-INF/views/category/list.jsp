<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/category" />
<h4 class="header">Category</h4>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:i18n key="all.id" /></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="name"><mytaglib:i18n key="all.name" /></mytaglib:sort-link></th>
			<th><mytaglib:i18n key="all.description" /></th>
			<th>position</th>
			<th><mytaglib:i18n key="all.image" /></th>
			<th><mytaglib:i18n key="all.created" /></th>
			<th><mytaglib:i18n key="all.updated" /></th>
			<th></th>
		</tr>
		<c:forEach var="category" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${category.id}" /></td>
				<td><c:out value="${category.name}" /></td>
				<td><c:out value="${category.description}" /></td>
				<td><c:out value="${category.position}" /></td>
				<td><c:out value="${category.imageName}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${category.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${category.updated}" /></td>
				<td class="right"><a class="btn-floating"
					href="${baseUrl}/${category.id}"><i class="material-icons">info</i></a>
					<a class="btn-floating" href="${baseUrl}/${category.id}/edit"><i
						class="material-icons">edit</i></a> <a class="btn-floating red"
					href="${baseUrl}/${category.id}/delete"><i
						class="material-icons">delete</i></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jspFragments:paging />
<a class="waves-effect waves-light btn right" href="${baseUrl}/add"><i
	class="material-icons">add</i></a>
