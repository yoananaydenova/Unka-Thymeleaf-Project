package com.yoanan.unka.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "exercises")
public class ExerciseEntity extends BaseEntity {

    private LessonEntity lesson;
    private String title;
    private String description;
    private String text;

 //   private Set<SolutionEntity> solutions = new HashSet<>();

    public ExerciseEntity() {
    }

    @ManyToOne
    public LessonEntity getLesson() {
        return lesson;
    }

    public ExerciseEntity setLesson(LessonEntity lesson) {
        this.lesson = lesson;
        return this;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public ExerciseEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public ExerciseEntity setDescription(String description) {
        this.description = description;
        return this;
    }
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    public String getText() {
        return text;
    }

    public ExerciseEntity setText(String text) {
        this.text = text;
        return this;
    }





    //    @OneToMany
//    public Set<SolutionEntity> getSolutions() {
//        return solutions;
//    }
//
//    public ExerciseEntity setSolutions(Set<SolutionEntity> solutions) {
//        this.solutions = solutions;
//        return this;
//    }
//
//    public ExerciseEntity addSolution(SolutionEntity solution) {
//        this.solutions.add(solution);
//        return this;
//    }

//    public ExerciseEntity addSolutions(Set<SolutionEntity> solutions) {
//        this.solutions.addAll(solutions);
//        return this;
//    }
}
