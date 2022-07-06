package service;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.Student;
import model.Subject;
import utility.EnumClass.SubjectName;

public class studentHandler {
private static studentHandler stud;
	
    public static studentHandler getInstance() throws Exception {
        if (stud== null) 
        {
        	stud= new studentHandler();
        }
        return stud;
    }
	 public Map<SubjectName,List<String>> compareTwoStudents(Map<Integer,Student> studentDetail, HashMap<Integer,SubjectName> subjectsList,int studentId1,int studentId2)
	 {        
		 Map<SubjectName,List<String>> studentList = new HashMap<SubjectName,List<String>> (); 
	            Student student1 = studentDetail.get(studentId1);
	            Student student2 = studentDetail.get(studentId2);	            
	            for(Map.Entry<Integer,SubjectName> subjectDetail : subjectsList.entrySet())
	            {
	                Subject subject1 = student1.getReport().getAllSubject().get(subjectDetail.getValue());
	                Subject subject2 = student2.getReport().getAllSubject().get(subjectDetail.getValue());
	                List<String> compareList = new ArrayList<String>();  
	                if(subject1.getMark() > subject2.getMark())
	                {
	                	compareList.add(subject1.getMark()+"");
	                	compareList.add(subject2.getMark()+"");
	                	compareList.add(student1.getName() +" has better performance than "+student2.getName());	                		                      	                   
	                }
	                else if(subject1.getMark() < subject2.getMark())
	                {	                    
	                    compareList.add(subject1.getMark()+"");
	                	compareList.add(subject2.getMark()+"");
	                	compareList.add(student2.getName() +" has better performance than "+student1.getName());
	                }
	                else if(subject1.getMark() == subject2.getMark())
	                {
	                	compareList.add(subject1.getMark()+"");
	                	compareList.add(subject2.getMark()+"");
	                	compareList.add(student1.getName() +" and "+student2.getName()+" both has equal performance");
	                }
	                studentList.put(subjectDetail.getValue(), compareList);
	            }
	            return studentList;
	 }	 
}