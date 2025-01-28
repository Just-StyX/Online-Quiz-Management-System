@param jsl.group.quiz.models.entities.FoundUser foundUser
@param java.util.List<String> result = new java.util.ArrayList<>()

<html lang="en">
<body>
    <h1>Hello ${foundUser.username()} ${foundUser.firstName()}</h1>
    <div>
        @for(String string: result)
            <p>${string}</p>
        @endfor
    </div>
    <button> <a href="/quiz/question/quiz?subject=pilot&limit=5"> Start Quiz</a> </button>
</body>
</html>