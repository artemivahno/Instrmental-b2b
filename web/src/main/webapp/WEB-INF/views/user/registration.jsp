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


<h4 class="header"><mytaglib:i18n key="registrate.name" /></h4>

<div class="row">
	<form:form class="col s12" method="POST"
		action="${pageContext.request.contextPath}/user/registration"
		modelAttribute="formModel">
		<form:input path="id" type="hidden" />
		<form:input path="role" type="hidden" value="customer" />
		<div class="row">
			<div class="input-field col s12">
				<form:input path="name" type="text" disabled="${readonly}" />
				<form:errors path="name" cssClass="red-text" />
				<label for="name"><mytaglib:i18n key="all.name" /></label>
			</div>
		</div>
		<c:if test="${showDuplicateError}">
			<p cssClass="red-text"><mytaglib:i18n key="registrate.unique" /></p>
		</c:if>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="email" type="text" disabled="${readonly}" />
				<form:errors path="email" cssClass="red-text" />
				<label for="email"><mytaglib:i18n key="login.email" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="password" type="text" disabled="${readonly}" />
				<form:errors path="password" cssClass="red-text" />
				<label for="password"><mytaglib:i18n key="login.password" /></label>
			</div>
		</div>
		<div class="row">
			<div class="col s6"></div>
			<div class="col s3">
				<c:if test="${!readonly}">
					<button class="btn waves-effect waves-light right" type="submit">
						<mytaglib:i18n key="registrate.btn" />
					</button>
				</c:if>
			</div>
		</div>
	</form:form>
</div>
