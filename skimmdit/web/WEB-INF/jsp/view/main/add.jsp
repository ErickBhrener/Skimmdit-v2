<template:basic htmlTitle="Create a Post" bodyTitle="Create a Post">
    <form:form method="post" enctype="multipart/form-data"
               modelAttribute="postForm">
        <form:label path="title">Title</form:label><br />
        <form:input path="title"/><br /><form:errors path="title"/><br />
        <form:label path="description">Description</form:label><br />
        <form:textarea path="description" rows="5" cols="30" /><br /><br />
        <form:label path="link">Link</form:label><br />
        <form:input path="link"/><br /><form:errors path="link"/><br />
        <input type="submit" value="Submit"/>
    </form:form>
</template:basic>
