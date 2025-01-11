@param String email


<html lang="en">
<body>
<h1>User Login</h1>
<form method="post" action="/login">
    <label for="email">Email:</label><br>
    <input type="email" id="email" name="email" value="${email}" required><br><br>

    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password" required><br><br>

    <input type="submit" value="Submit">
</form>
</body>
</html>