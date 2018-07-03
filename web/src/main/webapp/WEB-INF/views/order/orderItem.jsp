<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>


<div class="row">
	<c:out value="${product.quantityStock}" />
</div>

<form:form class="col s12" method="POST"
	action="${baseUrl}/b2b/orderItemMain/item/add"
	modelAttribute="formModel">
	<form:input path="productId" type="hidden" />


	<div class="row">
		<div class="input-field col s12">
			<form:input path="quantity" type="text" disabled="${readonly}" />
			<form:errors path="quantity" cssClass="red-text" />
			<label for="quantity"><mytaglib:i18n key="all.category" /></label>
		</div>
	</div>


	<div class="row">
		<div class="col s6"></div>
		<div class="col s3">
			<c:if test="${!readonly}">
				<button class="btn waves-effect waves-light right" type="submit">
					<mytaglib:i18n key="allbtn.save" />
				</button>
			</c:if>
		</div>

	</div>
</form:form>


