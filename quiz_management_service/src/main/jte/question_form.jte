@param boolean createdBool
@param String  created

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Question Form</title>
</head>
<body>
    @if(createdBool)
        <h4>${created}</h4>
    @endif
    <form action="/admin/question" method="post">
        <h2>Create a New Question</h2>
        <div>
            <label for="question">Question:</label>
            <textarea id="question" name="question" rows="3" cols="50"></textarea>
        </div>
        <div>
            <label for="answer">Answer:</label>
            <textarea id="answer" name="answer" rows="2" cols="30"></textarea>
        </div>

        <h3>Section Options</h3>
        <div>
            <label for="options">Options:</label>
            <h4>May be left blank if open-ended question</h4>
            <textarea id="options" name="options" rows="2" cols="40" placeholder="Example: A=option;B=option;C=option"></textarea>
        </div>

        <h3>Question Details</h3>
        <div>
            <label for="subject">Subject:</label>
            <input type="text" id="subject" name="subject">
        </div>
        <div>
            <label for="points">Points:</label>
            <input type="number" id="points" name="points">
        </div>
        <div>
            <label for="level">Level:</label>
            <textarea id="level" name="level" rows="2" cols="30" placeholder="Example: easy, medium, hard"></textarea>
        </div>

        <button type="submit">Create Question</button>
    </form>
    <button type="button"> <a href="/admin"> Back </a> </button>
</body>
</html>