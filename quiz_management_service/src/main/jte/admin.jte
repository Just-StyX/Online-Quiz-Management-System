@param jsl.group.quiz.models.entities.FoundUser foundUser

<html lang="en">
    <body>
        <h1>Hello ${foundUser.username()} ${foundUser.firstName()}</h1>
        <div>
            <button type="button"> <a href="/admin/question"> Create Question </a> </button>
            <button type="button"> <a href="/admin/question/updates"> Update Question </a> </button>
            <button type="button"> <a href="/admin/question/quest"> Get Question </a> </button>
        </div>
    </body>
</html>