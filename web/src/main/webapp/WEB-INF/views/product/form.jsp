<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>
<h4 class="header">Edit Product</h4>
<div class="row">
	<form:form class="col s12" method="POST"
		action="${pageContext.request.contextPath}/product/add"
		modelAttribute="formModel">
		<form:input path="id" type="hidden" />
		<form:input path="version" type="hidden" />
		<div class="row">
			<div class="input-field col s12">
				<form:input path="name" type="text" disabled="${readonly}" />
				<form:errors path="name" cssClass="red-text" />
				<label for="name">Имя</label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="description" type="text" disabled="${readonly}" />
				<form:errors path="description" cssClass="red-text" />
				<label for="description">description</label>
			</div>
		</div>

		<div class="row">
			<div class="input-field  col s12">
				<form:select path="imageIds" disabled="${readonly}" multiple="true">
					<option value="" disabled ${emptyformModel.imageIds? "selected":""}>выберите
						изображение</option>
					<form:options items="${imageChoices}" />
				</form:select>
				<form:errors path="imageIds" cssClass="red-text" />
				<label for="imageIds" class="multiselect-label">все
					изображения</label>
			</div>
		</div>

		<div class="row">
			<div class="input-field col s12">
				<form:input path="externalLink" type="text" disabled="${readonly}" />
				<form:errors path="externalLink" cssClass="red-text" />
				<label for="externalLink">externalLink</label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<div class="switch">
					<label> Not visible <form:checkbox path="visible"
							enabled="${readonly}" /> <span class="lever"></span> Visible!
					</label>
				</div>
				<label class="switch-label">visible</label> <br />
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="position" type="text" disabled="${readonly}" />
				<form:errors path="position" cssClass="red-text" />
				<label for="position">position</label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="price" type="text" disabled="${readonly}" />
				<form:errors path="price" cssClass="red-text" />
				<label for="price">price</label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="quantityStock" type="text" disabled="${readonly}" />
				<form:errors path="quantityStock" cssClass="red-text" />
				<label for="quantityStock">quantity in stock</label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="categoryId" type="text" disabled="${readonly}">
					<form:options items="${categoryChoices}" />
				</form:select>
				<form:errors path="categoryId" cssClass="red-text" />
				<label for="categoryId">Category</label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="brandId" type="text" disabled="${readonly}">
					<form:options items="${brandsChoices}" />
				</form:select>
				<form:errors path="brandId" cssClass="red-text" />
				<label for="brandId">Brand</label>
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
			<div class="col s3">
				<a class="btn waves-effect waves-light right"
					href="${pageContext.request.contextPath}/product"><mytaglib:i18n
						key="allbtn.back" /><i class="material-icons right"></i> </a>
			</div>
		</div>
	</form:form>
</div>
