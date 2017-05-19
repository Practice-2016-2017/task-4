package com.roi.entity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "Student")
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idStudent", nullable = false)
    private Integer id;

    @Column(name = "Login", nullable = false, unique = true )
    private Integer login;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "StudentName", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "Years_idYears", nullable = false)
    private Year year;


    public Student(){}

    public Student(Integer login, String password, String name, Year year){
        this.login=login;
        this.password=password;
        this.name=name;
        this.year=year;
    }


    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLogin() {
        return login;
    }
    public void setLogin(Integer login) {
        this.login = login;
    }

    public void setYear(Year year) {
        this.year=year;
    }
    public Year getYear() {
        return this.year;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
