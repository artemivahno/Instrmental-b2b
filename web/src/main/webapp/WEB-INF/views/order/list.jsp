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

<h4 class="header">Order</h4>
<c:set var="baseUrl" value="${pageContext.request.contextPath}/order" />
<c:out value="${order_object.userName}" />

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:i18n key="all.id" /></th>
			<th>Byer</th>
			<th>Order status</th>
			<th><mytaglib:i18n key="all.created" /></th>
			<th><mytaglib:i18n key="all.updated" /></th>
			<th></th>
		</tr>
		<c:forEach var="order_object" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${order_object.id}" /></td>
				<td><c:out value="${order_object.userName}" /></td>
				<td><c:out value="${order_object.close}" /></td>

				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${order_object.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${order_object.updated}" /></td>

				<td class="right"><a class="btn-floating"
					href="${baseUrl}/${order_object.id}"><i class="material-icons">info</i></a>

				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jspFragments:paging />

