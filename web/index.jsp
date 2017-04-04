<%-- 
    Document   : index
    Author     : w_martin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><font face ="avenir_next">The Smart Registration System</font></h1>
        <form method="POST" action="ListModules.do">
        <p />
            View a table <br />
            <input type="radio" name="tbl" value="List">List Modules<br />
            <input type="radio" name="tbl" value="NewUser">New User<br />
            <input type="radio" name="tbl" value="Update">Password Change<br />
            <input type="radio" name="tbl" value="Delete">Delete a User<br />
            <input type=submit value="Action"> <br />
        </form> 
        <h2> Please enter your login details</h2>

        <div class="container">
            <form action="doLogin">
            <p>
                <label><b>Username</b></label>
                <input type="text" placeholder="Enter Username" name="username" required>
            </p>
            <p>
                <label><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="pswd" required>
            </p>
            <p>
                <button type="submit">Login</button>
            </p>
            <p>
            </form>
                
            <p>Don't have an account? Register Below.</p>
            <form action="register.html">
                <input type="submit" value="Register" />
            </form>
       
        </div>
    </body>
</html>
