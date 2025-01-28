@param boolean show
@param String message
@param boolean messageBool
@param jsl.group.quiz.models.entities.Question question

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Question Form</title>
</head>
<body>
    <form action="/admin/question/search" method="post">
        <label for="search-question">
            <textarea id="search-question" name="search-question" rows="3" cols="50" >Enter question fully</textarea>
        </label>
        <button type="submit">Search Question</button>
    </form>
    @if(messageBool)
        <div>
            <h4>${message}</h4>
        </div>
    @endif
    @if(show)
        <div>
            <form action="/admin/question/updates?id=${question.questionId()}" method="post">
                <h2>Create a New Question</h2>
                <div>
                    <label for="question">Question:</label>
                    <textarea id="question" name="question" rows="3" cols="50" >${question.question()}</textarea>
                </div>
                <div>
                    <label for="answer">Answer:</label>
                    <input id="answer" name="answer" placeholder="A;B;C" value="${question.answer()}"/>
<%--                    <textarea id="answer" name="answer" rows="2" cols="30">${question.answer()}</textarea>--%>
                </div>

                <h3>Section Options</h3>
                <div>
                    <label for="options">Options:</label>
                    <h4>May be left blank if open-ended question. Make sure there are no trailing ';'.</h4>
                    <textarea id="options" name="options" rows="2" cols="40" placeholder="Example: A=option;B=option;C=option">
                        ${question.options().entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue() + ";")
                            .collect(java.util.stream.Collectors.joining()).trim()}
                    </textarea>
                </div>

                <h3>Question Details</h3>
                <div>
                    <label for="subject">Subject:</label>
                    <input type="text" id="subject" name="subject" value="${question.subject()}">
                </div>
                <div>
                    <label for="points">Points:</label>
                    <input type="number" id="points" name="points" value="${question.points()}">
                </div>
                <div>
                    <label for="level">Level:</label>
                    <textarea id="level" name="level" rows="2" cols="30" placeholder="Example: easy, medium, hard">
                        ${question.level()}
                    </textarea>
                </div>

                @if(!question.questionId().isEmpty())
                    <button type="submit">Edit Question</button>
                @endif
            </form>
        </div>
        @if(!question.questionId().isEmpty())
            <div>
                <h3>Completely Delete Question</h3>
                    <button type="button"> <a href="/admin/question/remove?id=${question.questionId()}"> Delete Question </a> </button>
            </div>
        @endif
    @endif
    <button type="button"> <a href="/admin"> Back </a> </button>
</body>
</html>