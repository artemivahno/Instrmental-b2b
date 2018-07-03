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

<c:set var="baseUrl" value="${pageContext.request.contextPath}" />


<nav>
    <ul id="menu">
        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
       <sec:authorize access="hasRole('customer')">
       <li><a href="${baseUrl}/order">My Order</a></li>
       </sec:authorize>
       <sec:authorize access="hasRole('admin')">
       <li><a href="${baseUrl}/order">All Orders</a></li>
       </sec:authorize>
       <li><a href="${baseUrl}/feedback">Contact Us</a></li>
    </ul>
</nav>