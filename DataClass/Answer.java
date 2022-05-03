package ru.telebot.DataClass;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "\"Answer\"")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {
    @Column(name = "answer_id",nullable = false)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long answerId;
    @Column(name = "ans_text")
    String ansText;
    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;
    @Column(name = "is_correct")
    boolean isCorrect;
    @Column(name = "editing_id")
    Long editingId;

    public Answer() {

    }

    @Override
    public String toString(){
        return  answerId+ansText;
    }
    public Answer (Answer answer) {
        this.answerId=answer.getAnswerId();
        this.ansText=answer.getAnsText();
        this.isCorrect=answer.isCorrect();
        this.answerId=answer.getEditingId();
    }

}
