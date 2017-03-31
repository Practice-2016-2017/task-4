package com.roi.entity;
import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name = "Mark")
public class Mark {
    @Column(name = "Mark", nullable = false)
    private Integer mark;

    @Column(name = "Date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "idStudent", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "idTeacher", nullable = false)
    private Teacher teacher;

    public void setMark(Integer mark) {
        this.mark = mark;
    }
    public Integer getId() {
        return mark;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }
}
