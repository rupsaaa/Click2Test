public class Question {

    String questionText;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String correctOption;

    public Question(String questionText, String optionA, String optionB,
                    String optionC, String optionD, String correctOption) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
    }
}
