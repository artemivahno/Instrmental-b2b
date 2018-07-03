<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="jspFragments" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="mytaglib"
	uri="https://journaldev.com/jsp/tlds/mytags"%>

<header>
	<meta name="description" content="B2B System of orders">
	<nav>
		<!-- Мобильное меню Значок-->
		<div class="nav-wrapper container">
			<a href="#" data-activates="nav-mobile" class="button-collapse"><i
				class="material-icons">menu</i> <i class="material-icons">menu</i></a>
			<ul class="right hide-on-med-and-down">


				<sec:authorize access="hasRole('admin')">
					<li class="site-nav--has-submenu"><a style="" href="#"
						class="site-nav__link dropdown-button"
						data-activates="features-dropdown" data-beloworigin="true"
						data-constrainwidth="false" data-hover="true"><mytaglib:i18n
								key="head.list" /><i class="material-icons right">arrow_drop_down</i>
					</a>
						<ul
							style="white-space: nowrap; position: absolute; top: 64px; left: 282.7px; opacity: 1; display: none;"
							id="features-dropdown" class="site-nav__submenu dropdown-content">

							<%-- <li><a href="${baseUrl}/attribute" class="site-nav__link"><mytaglib:i18n
										key="attribute.homeName" /></a></li> --%>

							<li><a href="${baseUrl}/image" class="site-nav__link"><mytaglib:i18n
								key="head.images" /></a></li>
							<li><a href="${baseUrl}/brand" class="site-nav__link"><mytaglib:i18n
								key="head.brands" /></a></li>
							<li><a href="${baseUrl}/order" class="site-nav__link"><mytaglib:i18n
								key="head.order" /></a></li>
							<li><a href="${baseUrl}/category" class="site-nav__link"><mytaglib:i18n
								key="head.category" /></a></li>
							<li><a href="${baseUrl}/user" class="site-nav__link"><mytaglib:i18n
								key="head.users" /></a></li>
							<li><a href="${baseUrl}/product" class="site-nav__link"><mytaglib:i18n
								key="head.products" /></a></li>
						</ul></li>
				</sec:authorize>
				<sec:authorize access="hasRole('customer')">
					<li><a href="${baseUrl}/order"> <i
							class="material-icons left">shopping_cart</i> <mytaglib:i18n
								key="head.myorders" />
					</a></li>
				</sec:authorize>
				<sec:authorize access="hasRole('admin')">
					<li><a href="${baseUrl}/order"><mytaglib:i18n
								key="head.allorders" /></a></li>
				</sec:authorize>

				<sec:authorize access="isAnonymous()">
					<li><a href="${baseUrl}/login" id="customer_login_link"><mytaglib:i18n
								key="head.login" /></a></li>
				</sec:authorize>
				<sec:authorize access="!isAnonymous()">
					<li><a class="right" href="${baseUrl}/execute_logout"
						title="logout"> <i
							style="font-size: 10px; padding-left: 10px; padding-right: 10px;"
							class="modal-trigger btn nav-sign-up 
						waves-effect waves-light">"<sec:authentication
									property="principal" />"
						</i>
					</a></li>
				</sec:authorize>
				<sec:authorize access="isAnonymous()">
					<li><a href="${baseUrl}/user/registration"
						id="customer_register_link"><mytaglib:i18n
								key="registrate.btn" /></a></li>
				</sec:authorize>


			</ul>


			<ul id="nav-mobile" class="right hide-on-med-and-down">

			</ul>
			<li class="right" style="margin-left: 15px;"><a class="right"
				href="${baseUrl}/product/list4buy"><mytaglib:i18n
						key="all.products" /></a></li>

			<li class="right" style="margin-left: 15px;"><a class="right"
				href="${baseUrl}?language=en"><mytaglib:i18n key="lang.en" /></a></li>
			<li class="right"><a href="${baseUrl}?language=ru"><mytaglib:i18n
						key="lang.ru" /></a></li> <a class="nav-header center" href="${baseUrl}/"
				itemprop="url" class="brand-logo site-logo"> <img
				src="http://www.kuldeeppolley.net/images/ico/B2B-icon-kuldeeppolley-website.png"
				height="64" width="157"></a>


			</ul>
		</div>
	</nav>
</header>