<%--@elvariable id="ticketId" type="java.lang.String"--%>
<%--@elvariable id="ticket" type="com.wrox.site.Ticket"--%>
<template:basic htmlTitle="${post.title}"
                bodyTitle="${post.title}">
    <i>User Name - <c:out value="${post.name}" /><br />
<%--     Created <wrox:formatDate value="${post.description}" type="both" --%>
<!--                              timeStyle="long" dateStyle="full" /></i><br /><br /> -->
    <c:out value="${post.description}" /><br /><br />
    <a href="${post.link}" target="_blank">
      	<c:out value="${post.link}"/>
    </a>
</template:basic>
