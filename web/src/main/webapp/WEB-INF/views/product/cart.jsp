<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/product/cart" />
<h4 class="header">Cart Page</h4>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="name"><mytaglib:i18n key="all.name" /></mytaglib:sort-link></th>
			<th>Instrumental</th>
			<th>price</th>
			<th>quantity_stock</th>
			<th>quantity_order</th>
			<th>
			</th>
		</tr>
		<c:forEach var="product" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${product.name}" /></td>
				<td><img src="${product.externalLink}" /></td>
				<td><c:out value="${product.price}" /></td>
				<td><c:out value="${product.quantityStock}" /></td>
				<td>
			<div class="input-field col s12" method="POST">
				<form:input path="price" type="text" disabled="${readonly}" />
				<form:errors path="price" cssClass="red-text" />
				<label for="price">price</label> 
			</div>
				</td>
				<td class="right"><a class="btn-floating"
					href="${baseUrl}/${product.id}"><i class="material-icons">info</i></a>
					<a class="btn-floating" href="${baseUrl}/${product.id}/edit"><i
						class="material-icons">edit</i></a> <a class="btn-floating red"
					href="${baseUrl}/${product.id}/delete"><i
						class="material-icons">delete</i></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jspFragments:paging />
<a class="waves-effect waves-light btn right" href="${baseUrl}/add"><i
	class="material-icons">add</i></a>
 --%>