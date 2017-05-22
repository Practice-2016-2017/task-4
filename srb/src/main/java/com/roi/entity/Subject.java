package com.roi.entity;


import javax.persistence.*;

@Entity
@Table(name = "Subject")
public class Subject {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idSubject", nullable = false)
    private Integer id;

    @Column(name = "SubjectName", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade =CascadeType.MERGE)
    @JoinColumn(name = "Teacher_idTeacher", nullable = true)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Years_idYears", nullable = false)
    private Year year;

    public Subject(){}

    public Subject (String name,Teacher teacher,  Year year){
        this.name=name;
        this.teacher=teacher;
        this.year=year;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher(){
        return this.teacher;
    }
    public  void setTeacher(Teacher teacher){
        this.teacher=teacher;
    }

    public Year getYear(){
     return this.year;
    }

    public void setYear(Year year){
        this.year=year;
    }



}
