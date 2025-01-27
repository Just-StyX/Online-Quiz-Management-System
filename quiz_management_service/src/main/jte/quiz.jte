@param jsl.group.quiz.models.QuizQuestion question
@param java.util.Map<String, Character> map = new java.util.HashMap<>()
@param boolean first
@param boolean last

<html lang="en">
<body>
    <div>
        <h1>Question: ${question.question()} <span>[${question.points()} point(s)]</span></h1>
        <div>
            @for(java.util.Map.Entry<Character, String> entry : question.options().entrySet())
                <ul style="list-style-type: none;">
                    <li><button value="${entry.getKey()}" onclick="'${map.put(question.questionId(), entry.getKey())}'">${entry.getKey()}</button> <span>${entry.getValue()}</span></li>
                </ul>
            @endfor
        </div>
        @if(!first)
            <button > <a href="/quiz/question/quiz?previous=true">Previous</a> </button>
        @endif
        @if(!last)
            <button> <a href="/quiz/question/quiz?next=true">Next</a> </button>
        @endif
        <button> <a href="/quiz/question/end">End Quiz</a> </button>
    </div>
</body>
</html>