//$Id$
package model;

import java.util.*;

import utility.EnumClass.SubjectName;
import  dao.DaoClass;
public class Management 
{        
	HashMap<Integer,String> departmentCode;
    HashMap<String,Department> allDepartment;
    
    public Management() 
    {                
    	this.departmentCode = new HashMap<Integer,String>();  
        this.allDepartment = new HashMap<String,Department>();
    }    

    public HashMap<Integer,String> getDepartmentCode()
    {
        return this.departmentCode;
    }
    
    public void addDepartment()
    {
        Map<Integer,String> deptMap =new HashMap<Integer,String>();
        try
        {
            deptMap = DaoClass.getInstance().getDepartment();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }        
        for(Map.Entry<Integer,String> dept : deptMap.entrySet())
        {
            Department department = new Department();
            department.setId(dept.getKey());            
            department.setName(dept.getValue());            
            department.setSubjectsList(assignSubject(getSubjectDetails(department)));            
            allDepartment.put(dept.getValue(),department);
            departmentCode.put(dept.getKey(), dept.getValue());
        }
    }

    public Department changeDepartment(String dept)
    {        
        return allDepartment.get(dept);
    }

    //#region Private
    private Map<Integer,SubjectName> getSubjectDetails(Department department)
    {       
        Map<Integer,SubjectName> subjects =new HashMap<Integer,SubjectName>();

        try
        {
            subjects = DaoClass.getInstance().getSubjects(department);    
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return subjects;
    }

    private HashMap<Integer,SubjectName> assignSubject(Map<Integer,SubjectName> subjects)
    {       
        HashMap<Integer,SubjectName> subjectList= new HashMap<Integer,SubjectName>();
        for(Map.Entry<Integer,SubjectName> subject : subjects.entrySet())
        {                        
            subjectList.put(subject.getKey(), subject.getValue());
        }
        return subjectList;
    }   
    //#endregion
}