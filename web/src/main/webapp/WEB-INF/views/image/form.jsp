<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="https://journaldev.com/jsp/tlds/mytags"%>
<h4 class="header">Edit Image data</h4>
<div class="row">
	<form:form class="col s12" method="POST"
		action="${pageContext.request.contextPath}/image/add"
		modelAttribute="formModel">
		<form:input path="id" type="hidden" />
		<div class="row">
			<div class="input-field col s12">
				<form:input path="name" type="text" disabled="${readonly}" />
				<form:errors path="name" cssClass="red-text" />
				<label for="name">название</label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="url" type="text" disabled="${readonly}" />
				<form:errors path="url" cssClass="red-text" />
				<label for="url">url</label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="position" type="text" disabled="${readonly}" />
				<form:errors path="position" cssClass="red-text" />
				<label for="position">позиция</label>
			</div>
		</div>
		<div class="row">
			<div class="col s6"></div>
			<div class="col s3">
				<c:if test="${!readonly}">
					<button class="btn waves-effect waves-light right" type="submit"><mytaglib:i18n key="allbtn.save"/></button>
				</c:if>
			</div>
			<div class="col s3">
				<a class="btn waves-effect waves-light right"
					href="${pageContext.request.contextPath}/image"><mytaglib:i18n key="allbtn.back"/><i
					class="material-icons right"></i>
				</a>
			</div>
		</div>
	</form:form>
</div>
