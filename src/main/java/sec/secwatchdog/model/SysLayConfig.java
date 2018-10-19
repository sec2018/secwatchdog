package sec.secwatchdog.model;

import java.util.Date;

public class SysLayconfig {
    private Integer id;

    private String mid;

    private Date one;

    private Date two;

    private Date three;

    private Date four;

    private Date five;

    private Date six;

    private Date seven;

    private Date eight;

    private Date nine;

    private Date ten;

    private Date eleven;

    private Date twelve;

    private Date updatetime;

    public SysLayconfig(Integer id, String mid, Date one, Date two, Date three, Date four, Date five, Date six, Date seven, Date eight, Date nine, Date ten, Date eleven, Date twelve, Date updatetime) {
        this.id = id;
        this.mid = mid;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.seven = seven;
        this.eight = eight;
        this.nine = nine;
        this.ten = ten;
        this.eleven = eleven;
        this.twelve = twelve;
        this.updatetime = updatetime;
    }

    public SysLayconfig() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    public Date getOne() {
        return one;
    }

    public void setOne(Date one) {
        this.one = one;
    }

    public Date getTwo() {
        return two;
    }

    public void setTwo(Date two) {
        this.two = two;
    }

    public Date getThree() {
        return three;
    }

    public void setThree(Date three) {
        this.three = three;
    }

    public Date getFour() {
        return four;
    }

    public void setFour(Date four) {
        this.four = four;
    }

    public Date getFive() {
        return five;
    }

    public void setFive(Date five) {
        this.five = five;
    }

    public Date getSix() {
        return six;
    }

    public void setSix(Date six) {
        this.six = six;
    }

    public Date getSeven() {
        return seven;
    }

    public void setSeven(Date seven) {
        this.seven = seven;
    }

    public Date getEight() {
        return eight;
    }

    public void setEight(Date eight) {
        this.eight = eight;
    }

    public Date getNine() {
        return nine;
    }

    public void setNine(Date nine) {
        this.nine = nine;
    }

    public Date getTen() {
        return ten;
    }

    public void setTen(Date ten) {
        this.ten = ten;
    }

    public Date getEleven() {
        return eleven;
    }

    public void setEleven(Date eleven) {
        this.eleven = eleven;
    }

    public Date getTwelve() {
        return twelve;
    }

    public void setTwelve(Date twelve) {
        this.twelve = twelve;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}