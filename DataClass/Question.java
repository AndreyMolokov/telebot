package ru.telebot.DataClass;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "\"Question\"")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question  {


    @Column(name = "question_id",nullable = false)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long questionId;
    @Column(name = "question_text")
    String questionText;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    Quiz quiz;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Answer> answerList;
    @Column(name = "editing_id")
    Long editingId;

    public Question() {

    }
    public Question (Question question) {
        this.questionId = question.getQuestionId();
        this.editingId = question.getEditingId();
        this.questionText = question.getQuestionText();
        question.getAnswerList().stream().forEach(e->e =new Answer(e));
        List<Answer> answerList1 = new ArrayList<>();
        answerList1.addAll(question.getAnswerList());
        this.answerList = answerList1;
    }

    @Override
    public String toString() {
        String allAnswerString = "";
        if(!answerList.isEmpty()){
            allAnswerString = answerList.toString();}
        return questionText+allAnswerString;
    }


}


