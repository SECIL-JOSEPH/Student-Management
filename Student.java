package model;

public class Student 
{
    private int studId;
    private String name;
    private Report report;

    public Student(String name) 
    {        
        this.name = name;        
    }

    public int getStudId()
    {
        return studId;
    }

    public void setStudId(int value)
    {
        this.studId = value;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String value)
    {
        this.name = value;
    }
    public Report getReport()
    {
        return report;
    }    

    public void setReport(Report value)
    {
        this.report = value;
    }        
}