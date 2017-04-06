package com.roi.entity;

import javax.persistence.*;


@Entity
@Table(name = "Teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idTeacher", nullable = false)
    private Integer id;

    @Column(name = "Login", nullable = false, unique = true)
    private Integer login;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "TeacherName", nullable = false)
    private String name;

    public Teacher(){}

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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
