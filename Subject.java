package model;
import utility.EnumClass.SubjectName;

public class Subject 
{
    private int id;
    private SubjectName name;
    private int mark;
    private String status; 

    public int getId()
    {
        return this.id;
    }

    public void setId(int value)
    {
        this.id = value;
    }

    public SubjectName getName()
    {
        return name;
    }
    
    public void setName(SubjectName value)
    {
        this.name = value;
    }

    public int getMark()
    {
        return mark;
    }

    public void setMark(int value)
    {
        this.mark = value;        
    }
    
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String value)
    {
        this.status = value;        
    }    
}