<template:loggedOut htmlTitle="Create User" bodyTitle="Create User">
    <c:if test="${createFailed}">
        <b>The username alrady exists. Please try
            again.</b><br /><br />
    </c:if>
    <p>Create a new user</p>
    <form:form method="post" modelAttribute="userForm">
        <form:label path="username">Username</form:label><br />
        <form:input path="username" /><br /><br />
        <form:label path="password">Password</form:label><br />
        <form:password path="password" /><br /><br />
        <input type="submit" value="Create" />
    </form:form>
</template:loggedOut>