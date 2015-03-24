<%--@elvariable id="ticketForm" type="com.wrox.site.TicketController.Form"--%>
<template:basic htmlTitle="Create a Ticket" bodyTitle="Create a Ticket">
    <form:form method="post" enctype="multipart/form-data"
               modelAttribute="postForm">
        <form:label path="title">Title</form:label><br />
        <form:input path="title"/><br /><br />
        <form:label path="description">Description</form:label><br />
        <form:textarea path="description" rows="5" cols="30" /><br /><br />
        <form:label path="link">Link</form:label><br />
        <form:input path="link"/><br /><br />
        <input type="submit" value="Submit"/>
    </form:form>
</template:basic>