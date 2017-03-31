package com.roi.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Year")
public class Year {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idYears", nullable = false)
    private Integer id;

    @Column(name = "Year", nullable = false)
    private Integer year;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "Subject")
    private Set<Subject> subjects;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "Subject")
    private Set<Student> students;

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getYear() {
        return year;
    }
}
