package com.gardenary.domain.flower.entity;

import com.gardenary.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "question_answer")
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED")
    private int id;

    @ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(targetEntity = MyFlower.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "my_flower_id", nullable = false)
    private MyFlower myFlower;


    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "content", columnDefinition = "VARCHAR(1000)")
    private String content;

    @Column(name = "question_num", columnDefinition = "INT")
    private int questionNum;
}
