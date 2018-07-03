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

<h2>
	<mytaglib:i18n key="login.head" />
</h2>
<div class="row">
	<div class="col s3"></div>
	<div class="col s6">
		<form name='loginForm' action="<c:url value='login' />" method='POST'>
			<div class="row">
				<div class="input-field col s12 center">
					<input placeholder="enter e-mail" type='text' name='username'
						value=''> <label for="username"><mytaglib:i18n
							key="login.email" /></label>
				</div>
			</div>
			<div class="row">
				<div class="input-field col s12 center">
					<input placeholder="enter pass" type='password' name='password' /><label
						for="password"><mytaglib:i18n key="login.password" /></label>
				</div>
			</div>
			<c:if test="${not empty error}">
				<div class="row">
					<div class="col s12 center">
						<div class="error">${error}</div>
					</div>
				</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="row">
					<div class="col s12 center">
						<div class="msg">${msg}</div>
					</div>
				</div>
			</c:if>
			<div class="row">
				<div class="col s12 center">
					<button class="btn waves-effect waves-light " type="submit">
						<mytaglib:i18n key="login.btn" />
					</button>
				</div>
			</div>
		</form>
			
		
	</div>
	<div class="col s3">
			<p>Admin name:</p> 
		<p>said.kirill@gmail.com</p>
			<p>Pass:</p>
		<p>55555</p>
			<p>. </p>
			<p>Customer:</p>
		<p>peter@gmail.com</p>
			<p>Pass:</p>
		<p>11111</p>
	</div>
</div>