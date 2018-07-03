<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="https://journaldev.com/jsp/tlds/mytags"%>
<h4 class="header">Edit User</h4>

<c:if test="${showDuplicateError}">

not unique user
</c:if>
<div class="row">
	<form:form class="col s12" method="POST"
		action="${pageContext.request.contextPath}/user/add"
		modelAttribute="formModel">
		<form:input path="id" type="hidden" />
		<div class="row">
			<div class="input-field col s12">
				<form:input path="name" type="text" disabled="${readonly}" />
				<form:errors path="name" cssClass="red-text" />
				<label for="name">Имя</label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="email" type="text" disabled="${readonly}" />
				<form:errors path="email" cssClass="red-text" />
				<label for="email">e-mail</label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="password" type="text" disabled="${readonly}" />
				<form:errors path="password" cssClass="red-text" />
				<label for="password">пароль</label>
			</div>
		</div>
		<sec:authorize access="isAnonymous()">
		<div class="row">
			<div class="input-field col s12">
				<div class="switch">
					<label> Не активирован <form:checkbox path="enabled"
							disabled="${readonly}" /> <span class="lever"></span>
						Активирован!
					</label>
				</div>
				<label class="switch-label">is enabled</label> <br />
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="role" type="text" disabled="${readonly}">
					<form:options items="${typeChoices}" />
				</form:select>
				<form:errors path="role" cssClass="red-text" />
				<label for="role">Роль</label>
			</div>
		</div>
		</sec:authorize>
		<div class="row">
			<div class="col s6"></div>
			<div class="col s3">
				<c:if test="${!readonly}">
					<button class="btn waves-effect waves-light right" type="submit">сохранить</button>
				</c:if>
			</div>
			<div class="col s3">
				<a class="btn waves-effect waves-light right"
					href="${pageContext.request.contextPath}/user">к списку<i
					class="material-icons right"></i>
				</a>
			</div>
		</div>
	</form:form>
</div>
