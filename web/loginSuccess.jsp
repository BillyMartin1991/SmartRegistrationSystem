<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Successful</title>
    </head>
    <body>
        <h1><%

//allow access only if session exists
            String user = (String) session.getAttribute("username");
            String userName = null;
            String sessionID = null;
            String key = (String) session.getAttribute("authKey");

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("user")) {
                        userName = cookie.getValue();
                    }
                    if (cookie.getName().equals("JSESSIONID")) {
                        sessionID = cookie.getValue();
                    }
                }
            }
            response.sendRedirect("dashboard.html");

            %></h1>
    </body>
</html>