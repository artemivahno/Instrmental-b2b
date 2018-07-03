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

<c:set var="baseUrl" value="${pageContext.request.contextPath}/product" />
<h4 class="header">Product</h4>

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="searchFormModel">
		<div class="input-field col s4">
			<form:input path="name" type="text" />
			<label for="name">Name</label>
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
			<th><mytaglib:i18n key="all.description" /></th>
			<th>external_link</th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="visible">visible</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="position">position</mytaglib:sort-link></th>
			<th>price</th>
			<th>quantity_stock</th>
			<th><mytaglib:i18n key="all.created" /></th>
			<th><mytaglib:i18n key="all.updated" /></th>
			<th>Action</th>
		</tr>
		<c:forEach var="product" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${product.id}" /></td>
				<td><c:out value="${product.name}" /></td>
				<td><c:out value="${product.description}" /></td>
				<td><c:out value="${product.externalLink}" /></td>
				<td><c:out value="${product.visible}" /></td>
				<td><c:out value="${product.position}" /></td>
				<td><c:out value="${product.price}" /></td>
				<td><c:out value="${product.quantityStock}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${product.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${product.updated}" /></td>
			<sec:authorize access="hasRole('admin')">
				<td class="right"><a class="btn-floating"
					href="${baseUrl}/${product.id}"><i class="material-icons">info</i></a>
					<a class="btn-floating" href="${baseUrl}/${product.id}/edit"><i
						class="material-icons">edit</i></a> <a class="btn-floating red"
					href="${baseUrl}/${product.id}/delete"><i
						class="material-icons">delete</i></a></td>
			</sec:authorize>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jspFragments:paging />
<a class="waves-effect waves-light btn right" href="${baseUrl}/add"><i
	class="material-icons">add</i></a>
