//$Id$
package service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dao.DaoClass;
import model.Department;
import model.Report;
import model.Staff;
import model.Student;
import model.Subject;
import utility.EnumClass.ReportStatus;
import utility.EnumClass.SubjectName;

public class staffHandler {
	private static staffHandler staff;
	
    public static staffHandler getInstance() throws Exception {
        if (staff== null) 
        {
        	staff= new staffHandler();
        }
        return staff;
    }

    public Map<Integer, String> getStudentIdAndName(Map<Integer,Student> studentDetail)
    {      
    	Map<Integer,String> studList = new HashMap<Integer,String>(); 
        for(Map.Entry<Integer,Student> student : studentDetail.entrySet())
        {
        	studList.put(student.getKey(), student.getValue().getName());            
        }
        return studList;
    }

    public Map<Integer, List<String>> getStaffIdAndName(Map<Integer,Staff> staffDetail)
    {        
    	Map<Integer, List<String>>  staffList = new HashMap<Integer, List<String>> ();
        for(Map.Entry<Integer,Staff> staff : staffDetail.entrySet())
        {
        	List<String> subjects = new ArrayList<String>();
        	subjects.add(staff.getValue().getName());
        	subjects.add(printStaffSubjects(staff.getValue().getSubjectList()));
        	staffList.put(staff.getKey(),subjects);            
        }
		return staffList;
        
    }   
    
    public Report getUpdatedReport(int subjectId, int mark, HashMap<Integer,SubjectName> subjectsList, Department department,Report report)
	{	
		Subject subject = report.getAllSubject().get(subjectsList.get(subjectId));		
		double tempmark = report.getTotalMark() - subject.getMark();
		tempmark+=mark;		
		report.setTotalMark(tempmark);
		subject.setMark(mark);
    	return department.assignReport(report);
	}
    
    public Map<Integer,Student> getRank(Map<Integer,Student> studentDetail)
    {
    	double previousMark = 0;
        int rank = 0;        
        for(int id : getRankList(studentDetail))
        {
            Student student = studentDetail.get(id);
            if(student.getReport().getStatus().equals(ReportStatus.PASS.toString()) && student.getReport().getTotalMark() ==  previousMark)
            {
                student.getReport().setRank(""+--rank);                  
                rank+=2;
            }        
            else if(student.getReport().getStatus().equals(ReportStatus.PASS.toString()))
            {
                previousMark = student.getReport().getTotalMark();
                student.getReport().setRank(""+ ++rank);                            
            }
            else
            {
            	student.getReport().setRank("-");            	
            }
         }
		return studentDetail;
    }
        
    public Map<SubjectName,Integer> getSubjectOverallAverage(List<Integer>subjectAverage,HashMap<Integer,SubjectName> subjectsList)
    {    
    	Map<SubjectName,Integer> average = new HashMap<SubjectName,Integer> ();
    	for(Map.Entry<Integer,SubjectName> subjectDetail : subjectsList.entrySet())
        {            
            average.put(subjectDetail.getValue() , roundoffAverage(subjectAverage.remove(0)));
        }
    	return average;
    }
    
    public Map<SubjectName,Map<String,Integer>> getStudentsMarksAboveAverage(Map<Integer,Student> studentDetail,List<Integer> subjectAverage, HashMap<Integer,SubjectName> subjectsList)
    {                        
    	Map<SubjectName,Map<String,Integer>>  markAboveAverage =new HashMap<SubjectName,Map<String,Integer>>  (); 
        for(Map.Entry<Integer,SubjectName> subject : subjectsList.entrySet())
        {                        
            int average = subjectAverage.remove(0);
            Map<String,Integer> mark = new HashMap<String,Integer>();
            for(Map.Entry<Integer,Student> student : studentDetail.entrySet())
            {            	
                if(student.getValue().getReport().getAllSubject().containsKey(subject.getValue()))
                {
                    Subject tempSubject = student.getValue().getReport().getAllSubject().get(subject.getValue());
                    if(average <= tempSubject.getMark())
                    {
                    	mark.put(student.getValue().getName() , tempSubject.getMark());                    	                    
                    }                    
                }                    
            }
            markAboveAverage.put(subject.getValue(), mark);            
        }
		return markAboveAverage;
    }
    
    public Map<SubjectName,List<String>> getTopScorer(Map<Integer,Student> studentDetail,HashMap<Integer,SubjectName> subjectsList)
    {           	
    	Map<SubjectName,List<String>>  topScorer =new HashMap<SubjectName,List<String>> ();
        for(Map.Entry<Integer,SubjectName> subject : subjectsList.entrySet())
        {   
            String maxScorerName = "";
            int maxScore = 0;
             
            List<String> markList = new ArrayList<String> (); 
            for(Map.Entry<Integer,Student> student : studentDetail.entrySet())
            {
                if(student.getValue().getReport().getAllSubject().containsKey(subject.getValue()))
                {                    
                    Subject tempSubject = student.getValue().getReport().getAllSubject().get(subject.getValue());
                    if(maxScore < tempSubject.getMark())
                    {                    	
                        maxScore = tempSubject.getMark();
                        maxScorerName = student.getValue().getName();                        
                    }                    
                }                                
            }
            markList.add(maxScorerName);
        	markList.add(maxScore+"");        	
        	topScorer.put(subject.getValue(), markList);
        }
		return topScorer;
    }
        
    public Map<SubjectName,Map<String,List<String>>> getAcademicDetails(Map<Integer,Student> studentDetail,HashMap<Integer,SubjectName> subjectsList)
    {
    	
    	Map<SubjectName,Map<String,List<String>>> acadamicDetails =new HashMap<SubjectName,Map<String,List<String>>>();
        for(Map.Entry<Integer,SubjectName> subject : subjectsList.entrySet())
        {   
        	Map<String,List<String>> studentList = new HashMap<String,List<String>>();        	
        	for(Map.Entry<Integer,Student> student : studentDetail.entrySet())
            {
        		List<String> markList = new ArrayList<String>();
                if(student.getValue().getReport().getAllSubject().containsKey(subject.getValue()))
                {                    
                    Subject tempSubject = student.getValue().getReport().getAllSubject().get(subject.getValue());
                                                    
                    markList.add(tempSubject.getMark()+"");
                    markList.add(tempSubject.getStatus());
                    studentList.put(student.getValue().getName(), markList);
                }                
            }               
        	acadamicDetails.put(subject.getValue(), studentList);            
        }
        return acadamicDetails;
    }
    
    public Map<String,List<String>> getSubjectsAndCountAboveAverage(Map<Integer,Student> studentDetail,Department department,HashMap<Integer,SubjectName> subjectsList)
    {               
    	Map<String,List<String>> subjectCountList= 	new HashMap<String,List<String>>();
    	for(Map.Entry<Integer,Student> student : studentDetail.entrySet())        
        {       
    		List<Integer> allSubjectTotalMarks = new ArrayList<Integer>();             
            try
            {
                allSubjectTotalMarks =  DaoClass.getInstance().getSubjectAverage(department);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            int count = 0;            
        	List<String> subjects = new ArrayList<String>();             
            for(Map.Entry<Integer,SubjectName> subject : subjectsList.entrySet())
            {
                int average = allSubjectTotalMarks.remove(0);
                Subject tempSubject = student.getValue().getReport().getAllSubject().get(subject.getValue());
                if(average <= tempSubject.getMark())
                {
                	subjects.add(subject.getValue().toString());                    
                    count++;
                }
            }            
            subjectCountList.put(student.getValue().getName(), subjects);
        }    
    	return subjectCountList;
    }
    
	private static List<Integer> getRankList(Map<Integer,Student> studentDetail)
    {
        List<Integer> rankList= new ArrayList<Integer>();        
        for(Map.Entry<Integer,Student> set : studentDetail.entrySet())
        {
            boolean addRank = true;
            Student student = set.getValue();
           if(rankList.isEmpty())
            {
                rankList.add(student.getStudId());
            }
            else
            {
                for(int i = 0; i < rankList.size();i++)
                {
                    int studId = rankList.get(i);
                    Student tempStudent = studentDetail.get(studId);
                    if(tempStudent.getReport().getTotalMark() <= student.getReport().getTotalMark())
                    {
                        rankList.add(i, student.getStudId());
                        addRank = false;
                        break;
                    }                
                }
                if(addRank)
                {
                    rankList.add(student.getStudId());
                }
            }
        }
        return rankList;
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
	 private static String printStaffSubjects(HashMap<Integer, SubjectName> subjectList)
	    {
	        String str ="| ";
	        for(Map.Entry<Integer,SubjectName> subject : subjectList.entrySet())
	        {
	            str+=subject.getValue() +" | ";
	        }
	        return str;
	    }
}