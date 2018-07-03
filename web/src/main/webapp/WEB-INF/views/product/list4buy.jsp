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


<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="searchFormModel">
		<div class="input-field col s6">
			<form:input path="name" type="text" />
			<label for="name"><mytaglib:i18n key="all.name" /></label>
		</div>
		<div class="input-field col s6">
			<form:select path="categoryId" disabled="${readonly}">
				<option value=""  ${emptyformProduct.categoryId? "selected":""}>
				<mytaglib:i18n key="list.category" /></option>
				<form:options items="${categoryChoices}" />
			</form:select>
			<label for="categoryId"><mytaglib:i18n key="all.category" /></label>

		</div>
		<div class="col s6">
			<button class="btn waves-effect waves-light right" type="submit">
				<mytaglib:i18n key="all.search" />
			</button>
		</div>
	</form:form>
</div>

<H4>
	<sec:authorize access="isAnonymous()">
		<mytaglib:i18n key="list.one" />
		<a target="_blank" href="${pageContext.request.contextPath}/login"
			title="Link Instrumental"> <mytaglib:i18n key="head.login" /></a>
				  or 
				  <a target="_blank" href="/b2b/user/registration"
			title="Link Instrumental"> <mytaglib:i18n key="registrate.btn" /></a>
	</sec:authorize>
</H4>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="position">position</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="category">Category</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="name">
					<mytaglib:i18n key="all.name" />
				</mytaglib:sort-link></th>
			<th><mytaglib:i18n key="all.link" /></th>
			<sec:authorize access="!isAnonymous()">
				<th><mytaglib:i18n key="all.price" /></th>
				<th><mytaglib:i18n key="all.qtitystock" /></th>
			</sec:authorize>
			<th></th>
			<th>
			<th><mytaglib:i18n key="all.action" /></th>
			</th>
		</tr>
		<c:forEach var="product" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${product.position}" /></td>
				<td><c:out value="${product.categoryName}" /></td>
				<td><c:out value="${product.name}" /></td>
				<td><a target="_blank" href="${product.externalLink}"
					title="Link Instrumental"> Link</a></td>
				<sec:authorize access="!isAnonymous()">
					<td><c:out value="${product.price}" /></td>
					<td><c:out value="${product.quantityStock}" /></td>
					<td></td>
					<sec:authorize access="hasRole('customer')">
						<td class="right"><a class="btn-floating green"
							href="${baseUrl}/b2b/orderItemMain/${product.id}/add"><i
								class="material-icons">shopping_cart</i></a>
					</sec:authorize>
					<sec:authorize access="hasRole('admin')">
						<td><a class="btn-floating yellow"
							href="${baseUrl}/b2b/product/${product.id}/edit"> <i
								class="material-icons">edit</i>
						</a></td>
						<td><a class="btn-floating red"
							href="${baseUrl}/b2b/product/${product.id}/delete"><i
								class="material-icons">delete</i></a>
					</sec:authorize>
				</sec:authorize>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jspFragments:paging />
<sec:authorize access="hasRole('admin')">
	<a class="waves-effect waves-light btn right"
		href="${baseUrl}/b2b/product/add"><i class="material-icons">add</i></a>
</sec:authorize>
