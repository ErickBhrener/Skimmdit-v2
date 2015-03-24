<template:basic htmlTitle="${post.title}"
                bodyTitle="${post.title}">
    <i>User Name - <c:out value="${post.name}" /><br /><br />
    Description: <c:out value="${post.description}" /><br /><br />
    URL: <a href="${post.link}" target="_blank">
      	<c:out value="${post.link}"/>
    </a>
    <br /><br />
    <b>Votes: </b><c:out value="${post.votes}"/><br />
</template:basic>
