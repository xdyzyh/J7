package domain;

import java.io.Serializable;

/**
 * Created by xcl on 16-3-28.
 */
@javax.persistence.Entity
@javax.persistence.Table(name="Student",schema ="sqmple",catalog = "")
public class Student implements Serializable{

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
    @javax.persistence.Id
    @javax.persistence.Column(name="studentNo")
    private String studentNo=null;
    @javax.persistence.Basic
    @javax.persistence.Column(name="studentName")
    private String studentName=null;
    @javax.persistence.Basic
    @javax.persistence.Column(name="major")
    private String major=null;
}
