<html lang="en">
<body>
<h1>User Registration</h1>
<form method="post" action="/register">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password" required><br><br>

    <label for="email">Email:</label><br>
    <input type="email" id="email" name="email" required><br><br>

    <label for="firstName">First Name:</label><br>
    <input type="text" id="firstName" name="firstName" required><br><br>

    <label for="middleName">Middle Name:</label><br>
    <input type="text" id="middleName" name="middleName"><br><br>

    <label for="lastName">Last Name:</label><br>
    <input type="text" id="lastName" name="lastName" required><br><br>

    <input type="submit" value="Submit">
</form>
<button> <a href="/login">Login</a> </button>
</body>
</html>