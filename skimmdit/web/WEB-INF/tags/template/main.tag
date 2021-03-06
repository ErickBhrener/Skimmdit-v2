<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="headContent" fragment="true" required="false" %>
<%@ attribute name="navigationContent" fragment="true" required="true" %>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Skimmdit</title>
        <link rel="stylesheet" href="<c:url value="/resource/stylesheet/main.css" />" />
        <jsp:invoke fragment="headContent" />
    </head>
    <body>
        <h1>Skimmdit</h1>
        <jsp:invoke fragment="navigationContent" />
        <table border="0" id="bodyTable">
            <tbody>
                <tr>
                    <td class="contentCell">
                        <h2><c:out value="${fn:trim(bodyTitle)}" /></h2>
                        <jsp:doBody />
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
