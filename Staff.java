package model;	
import java.util.*;

import utility.EnumClass.SubjectName;


public class Staff 
{    
    private int staffId;
    private String name;
    private HashMap<Integer, SubjectName> subjectList;

    public Staff(String name) 
    {        
        this.name = name;        
        this.subjectList = new HashMap<Integer, SubjectName>();
    }

    public int getStaffId()
    {
        return staffId;
    }

    public void setStaffId(int value)
    {
        this.staffId = value;
    }

    public String getName()
    {
        return name;
    }        

    public HashMap<Integer, SubjectName> getSubjectList()
    {
        return subjectList;
    }    

    public void setSubjectList(HashMap<Integer, SubjectName> value)
    {
        this.subjectList = value;
    }    
}