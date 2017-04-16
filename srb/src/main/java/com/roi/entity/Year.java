package com.roi.entity;

import javax.persistence.*;


@Entity
@Table(name = "Year")
public class Year {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idYears", nullable = false)
    private Integer id;

    @Column(name = "Year", nullable = false)
    private Integer name;

    public Year(){}

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setName(Integer name) {
        this.name = name;
    }
    public Integer getName() {
        return name;
    }
}
