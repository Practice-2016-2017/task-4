package com.roi.entity;


import javax.persistence.*;
import java.util.Set;

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
    @JoinColumn(name = "idTeacher", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "idYears", nullable = false)
    private Year year;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "Subject")
    private Set<Mark> marks;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
