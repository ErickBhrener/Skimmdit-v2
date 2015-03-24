<template:basic htmlTitle="Active Sessions" bodyTitle="Active Sessions">
    There are a total of ${numberOfSessions} active sessions in this
    application.<br /><br />
    <c:forEach items="${sessionList}" var="s">
        <c:out value="${s.id} - ${s.getAttribute('username')}" />
        <c:if test="${s.id == pageContext.session.id}">&nbsp;(you)</c:if>
        &nbsp;- last active
    </c:forEach>
</template:basic>
