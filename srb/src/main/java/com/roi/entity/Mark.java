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
    private Integer value;

    @Column(name = "Date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "Student_idStudent", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "Subject_idSubject", nullable = false)
    private Subject subject;

    public Mark (){}
    public Mark (Integer value, Date date, Student student, Subject subject){
        this.value = value;
        this.date=date;
        this.student=student;
        this.subject=subject;
    }
    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
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

    public void setStudent(Student student){
        this.student=student;
    }
    public Subject getSubject(){
     return this.subject;
    }

    public Student getStudent(){
        return this.student;
    }

    public String subjectName() {
        return subject.getName();
    }

    public String studentName() {
        return student.getName();
    }

}
