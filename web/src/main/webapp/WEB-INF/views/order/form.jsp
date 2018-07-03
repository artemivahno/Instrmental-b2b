<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<h4 class="header">Order Info</h4>


<H3>ITEMS</H3>

<sec:authorize access="hasRole('customer')">
<form:form class="col s12" method="POST"
action="${baseUrl}/b2b/orderItemMain/item/close"
		modelAttribute="formModel">
<div>
	<button class="btn waves-effect waves-light right" type="submit"> Confirm
	</button>
</div>
</form:form>
</sec:authorize>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th>id</th>
			<th>Name</th>
			<th>Price</th>
			<th>order quantity</th>
			<th>Total price</th>
			<th>action</th>
		</tr>
		<c:set var="totalValue" value="${0}" />
		<c:forEach var="product" items="${orderItems}" varStatus="loopCounter">
			<c:set var="totalValue"
				value="${totalValue + product.productPrice * product.quantity}" />
			<tr>
				<td><c:out value="${product.id}" /></td>
				<td><c:out value="${product.productName}" /></td>
				<td><c:out value="${product.productPrice}" /></td>
				<td><c:out value="${product.quantity}" /></td>
				<td><c:out value="${product.productPrice * product.quantity}" /></td>
				
				<td class="right"><a class="btn-floating red"
					href="/b2b/order/${product.id}/delete"><i
						class="material-icons">delete</i></a></td>

			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="totalValue">
	<strong>Total:<fmt:formatNumber type = "number" 
         minFractionDigits = "2" value = "${totalValue}" /></strong>

</div>
<sec:authorize access="hasRole('customer')">
<a href="<spring:url value="/product/list4buy" />"
	class="btn btn-default"> <span
	class="glyphicon-hand-left glyphicon"></span> Continue shopping
</a>
</sec:authorize>
