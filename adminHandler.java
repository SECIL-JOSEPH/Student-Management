package service;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import model.Student;
import model.Subject;
import model.Department;
import model.Report;
import model.Staff;
import utility.EnumClass.SubjectName;
import utility.EnumClass.SubjectStatus;

public class adminHandler
{	
private static adminHandler admin;
	
    public static adminHandler getInstance() throws Exception 
    {
        if (admin == null) 
        {
        	admin= new adminHandler();
        }
        return admin;
    }     
	public SubjectName[] getSubjects(HashMap<Integer,SubjectName> subjectsList)
	{        
		 SubjectName[] arr = new SubjectName[5]; 
		 int i=0;   
		 for(Map.Entry<Integer,SubjectName> subjectDetail : subjectsList.entrySet())
	        {
	       	arr[i++]= subjectDetail.getValue();
	        }
	       return arr;
	}
	
	public Student getStudent(String studentname,List<Integer> marks, HashMap<Integer,SubjectName> subjectsList, Department department)
	{
	Student stud = new Student(studentname);
    Report report = new Report();                            
    int i =0; 
    	for(Map.Entry<Integer,SubjectName> subjectDetail : subjectsList.entrySet())
    	{                                                        
    		Subject subject = new Subject();                            
        	subject.setId(subjectDetail.getKey());
        	subject.setName(subjectDetail.getValue());                                       
        	subject.setMark(marks.get(i));
        	subject.setStatus(validateStatus(subject.getMark()));
        	report = department.addSubject(report, subject);
        	i++;
    	}                            
    	stud.setReport(department.assignReport(report));       
    	return stud;
	}
		
	
	public Staff getStaff(String staffname,List<Integer> staffSubject, HashMap<Integer,SubjectName> subjectsList, Department department)
	{
		Staff staff= new Staff(staffname);                               
		HashMap<Integer,SubjectName>  staffSubjectList= new HashMap<Integer,SubjectName>(); 
		for(int Id : staffSubject)
		{
			SubjectName subject=  subjectsList.get(Id);
			staffSubjectList.put(Id, subject);
		}	
			staff.setSubjectList(staffSubjectList);       
    	return staff;
	}
		
	 private static String validateStatus(int mark)
	 {    
	        if(mark >= 40)
	        {
	            return SubjectStatus.PASS.toString();
	        }
	        else
	        {
	            return SubjectStatus.FAIL.toString();
	        }
	 }
}