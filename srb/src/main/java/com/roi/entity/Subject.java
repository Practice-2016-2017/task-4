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

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "Teacher_idTeacher", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "Years_idYears", nullable = false)
    private Year year;

    public Subject(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
