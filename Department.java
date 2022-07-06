package model;
import java.util.*;

import utility.EnumClass.ReportStatus;
import utility.EnumClass.SubjectName;
import utility.EnumClass.SubjectStatus;
import model.Student;
import model.Staff;
import dao.DaoClass;

public class Department 
{    
    private int id;
    private String name;    
    private HashMap<Integer,Student> studentDetail;
    private HashMap<Integer,Staff> staffDetail;  
    private HashMap<Integer, SubjectName> subjectsList;
    

    public Department() 
    {                             
        this.staffDetail = new HashMap<Integer,Staff>();                
        this.studentDetail =  new HashMap<Integer,Student>();        
    }

    public int getID()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {      
        return this.name;
    }

    public  void setName(String value)
    {      
        this.name = value;
    }

    public HashMap<Integer,Student> getStudentDetail()
    {        
        return this.studentDetail;
    }

    public void setStudentDetail(HashMap<Integer,Student> value)
    {        
        this.studentDetail = value;
    }
    
    public HashMap<Integer, SubjectName> getSubjectsList()
    {      
        return this.subjectsList;
    }

    public void setSubjectsList(HashMap<Integer, SubjectName> value)
    {      
        this.subjectsList = value;
    }

    public HashMap<Integer,Staff>  getStaffDetail()
    {                
        return this.staffDetail;
    }
    
    public void getStaffDetail(HashMap<Integer,Staff> value)
    {                
        this.staffDetail = value;
    }
    
    public Report assignReport(Report report)
    {        
        report.setAverage(roundoffAverage(report.getTotalMark()/report.getAllSubject().size()));
        report.setStatus(assignReportStatus(report));
        report.assignoverallPerformance();        
        return report;
    }

    public void addStudent(Department department, Student student)
    {
        try
        {
            DaoClass.getInstance().insertStudent(department, student);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }                        
    }

    public Report addSubject(Report report,Subject subject)
    {        
        report.addSubjectMarkToTotalMark(subject.getMark());                
        report.getAllSubject().put(subject.getName(), subject);        
        return report;
    }
        
    public void addStaff(Department department,Staff staff)
    {
        try
        {
            DaoClass.getInstance().insertStaff(department, staff);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        staffDetail.put(staff.getStaffId(), staff);
    }

    public Staff addStaffSubjects(Staff staff,int subjectCode)
    {
        staff.getSubjectList().put(subjectCode, subjectsList.get(subjectCode));

        return staff;
    }

    public void showSubjectCode()
    {        
        int i = 0;
        for(Map.Entry<Integer,SubjectName> subject : subjectsList.entrySet())
        {
            System.out.println("\t" +subject.getKey()+ " - "+subject.getValue());
            i++;
        }
    }
        

    //#region Private    
    private String assignReportStatus(Report report)
    {
        report.setStatus(ReportStatus.PASS.toString());
        for(Map.Entry<SubjectName,Subject> subject : report.getAllSubject().entrySet())
        {
            if(subject.getValue().getStatus()== SubjectStatus.FAIL.toString())
            {
                report.setStatus(ReportStatus.FAIL.toString());
                break;
            }
        }
        return report.getStatus();
    }    
    private static int roundoffAverage(double average)
    {
        double N = average*10;
        int temp =(int) N % 10;
        if(temp>=5)
        {
            temp = (int)average;
            return ++temp;
        }
        else
        {
            temp = (int)average;            
            return temp;
        }
    }
    //#endregio   
}