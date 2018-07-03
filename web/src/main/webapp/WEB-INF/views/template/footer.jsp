<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>


<footer class="page-footer">
	<div class="container">
		<div class="row">
			<div class="col l6 s12">
				<h5 class="white-text"><mytaglib:i18n key="footer.content" /></h5>
				<p class="grey-text text-lighten-4">
					<iframe src="https://yandex.by/map-widget/v1/-/CBqhJ8EolB"
						width="360" height="150" frameborder="1" allowfullscreen="true"></iframe>
				</p>

			</div>
			<div class="col l6 s12">
				<h5 class="white-text">
					<mytaglib:i18n key="footer.image" />
				</h5>
				<ul>
					<sec:authorize access="!isAnonymous()">
					</sec:authorize>
					<li><a target="_blank"
						href="https://www.dropbox.com/s/k7kb16e5qso1iig/db-schema-b2b.png?dl=0">Database
							schema</a></li>
					<li><a target="_blank"
						href="https://www.dropbox.com/s/jl14q270l0quach/MindMup.jpg?dl=0">MindMup</a></li>
					<li><a target="_blank" href="http://prntscr.com/jnsd5k">JUnitTest</a></li>
					<li><a target="_blank"
						href="https://docs.google.com/drawings/d/1sKUkfSdQ4Wecjv7HxrSuFD2P4t10n_4UiU2Wltsda1E/edit?usp=sharing">Checkstyle</a></li>

					<sec:authorize access="hasRole('admin')">
						<sec:authorize access="hasRole('manager')">
							<li><a href="${baseUrl}/category">Category</a></li>
						</sec:authorize>
						<li><a href="${baseUrl}/user">Users</a></li>
						<li><a href="${baseUrl}/product">Product</a></li>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<li><a class="grey-text text-lighten-3"
							href="${baseUrl}/login"><mytaglib:i18n key="head.login" /></a></li>
					</sec:authorize>
				</ul>

			</div>
			<div class="col l4 offset-l2 s12">
				<h5 class="white-text"></h5>
			</div>
		</div>
	</div>
	<div class="footer-copyright">
		<div class="container">
			2018 A.Ivahno <a class="grey-text text-lighten-4 right"
				href="${baseUrl}/"><mytaglib:i18n key="all.home" /></a>
		</div>
	</div>
</footer>