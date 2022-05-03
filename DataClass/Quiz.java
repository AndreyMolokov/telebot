package ru.telebot.DataClass;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.telebot.Methods.BotMethod;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Entity
@Data
@Table(name = "\"Quiz\"")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Quiz {
    @Column(name = "quiz_id")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long quizId;
    @Column(name ="quiz_name")
    String quizName;
    @Column(name = "description")
    String quizDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    List<Question> questionList;
    @Column(name = "editing_id")
    Long editingId;
    @Override
    public String toString(){
        return  "Quiz Name ="+quizName+" User Name = "+"Quiz Id ="+quizId;
    }
    public Quiz (Quiz quiz) {
        this.quizId = quiz.getQuizId();
        this.editingId = quiz.getEditingId();
        this.quizName = quiz.getQuizName();
        this.quizDescription = quiz.getQuizDescription();
        quiz.getQuestionList().stream().forEach(e->e =new Question(e));
        List<Question> questionList1 = new ArrayList<>();
        questionList1.addAll(quiz.getQuestionList());
        this.questionList = questionList1;
    }
    public Quiz ( ) {

    }
}
