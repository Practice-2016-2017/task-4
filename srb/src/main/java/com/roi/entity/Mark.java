package com.roi.entity;
import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name = "Mark")
public class Mark {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idMark", nullable = false)
    private Integer id;

    @Column(name = "Mark", nullable = false)
    private Integer mark;

    @Column(name = "Date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "Student_idStudent", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "Subject_idSubject", nullable = false)
    private Subject subject;

    public Mark (){}

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getMark() {
        return mark;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }
}
