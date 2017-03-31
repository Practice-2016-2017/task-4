package com.roi.entity;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "Admin")
public class Admin {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idAdmin", nullable = false)
    private Integer id;

    @Column(name = "Login", nullable = false)
    private Integer login;

    @Column(name = "Password", nullable = false)
    private String password;

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
    public void setLogin(Integer name) {
        this.login = name;
    }

}
