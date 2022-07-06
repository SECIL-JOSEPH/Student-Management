package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Student;
import model.Subject; 
import model.Report;
import model.Staff;
import model.Department;
import model.Management;
import utility.EnumClass.SubjectName;
import utility.Config;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class DaoClass {

    private static DaoClass dao;
    private Connection connection =null;
    
    
    private DaoClass() throws Exception{
        try 
        {                                    
            Properties properties = Config.loadProperty();
            String url = properties.getProperty("mysql.CONNECTION_URL");
            String userName = properties.getProperty("mysql.username");
            String password = properties.getProperty("mysql.password");
            String driverName = properties.getProperty("mysql.CONNECTION_DRIVER");
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, userName , password);           
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }    
    }
    public static DaoClass getInstance() throws Exception {
        if (dao == null) 
        {
            dao = new DaoClass();
        }
        return dao;
    }

    public boolean isUser(String username, String password, boolean student) throws Exception{
    	
    	Properties properties = Config.loadProperty();
    	String query ="";
    	if(student)
    	{
    		query = properties.getProperty("mysql.query.is_student");
    	}
    	else
    	{
    		query = properties.getProperty("mysql.query.is_staff");
    	}
    	PreparedStatement pstmt = connection.prepareStatement(query);
    	pstmt.setString(1,  username);
		pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
       	{    			
			return true;
       	}    
    	return false;
    }

    public Map<Integer,String> getDepartment() throws Exception
    {
        Properties properties = Config.loadProperty();        
        HashMap<Integer,String> department = new HashMap<>();
        String query = properties.getProperty("mysql.query.all_department");        
        PreparedStatement pstmt = connection.prepareStatement(query);        
        ResultSet resultSet  = pstmt.executeQuery();
        while(resultSet.next())
        {   
            department.put(resultSet.getInt(1), resultSet.getString(2));
        }
        return department;
    }

    public Map<Integer, SubjectName> getSubjects(Department department) throws Exception
    {
        Properties properties = Config.loadProperty();
        HashMap<Integer,SubjectName> subjects = new HashMap<>();
        String query = properties.getProperty("mysql.query.subject_detail");        
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,department.getID());
        ResultSet resultSet  = pstmt.executeQuery();
        while(resultSet.next())
        {   
            subjects.put(resultSet.getInt(1), getSubjectEnum(resultSet.getString(2)));            
        }
        return subjects;
    }

    public Map<Integer,Student> getStudentIdAndName(Department department) throws Exception
    {
        Properties properties = Config.loadProperty();
        HashMap<Integer,Student> studentDetail = new HashMap<Integer,Student>();
        String query = properties.getProperty("mysql.query.all_students");                
        PreparedStatement pstmt = connection.prepareStatement(query);        
        pstmt.setInt(1,department.getID());
        ResultSet resultSet  = pstmt.executeQuery();
        while(resultSet.next())
        {
            Student student = new Student(resultSet.getString(2));
            student.setStudId(resultSet.getInt(1));
            studentDetail.put(student.getStudId(), student);
        }
        return studentDetail;
    }

    public Map<Integer,Student> getAllStudentIdAndName() throws Exception
    {
        Properties properties = Config.loadProperty();
        HashMap<Integer,Student> studentDetail = new HashMap<Integer,Student>();
        String query = properties.getProperty("mysql.query.alldepartment_students");                
        PreparedStatement pstmt = connection.prepareStatement(query);                
        ResultSet resultSet  = pstmt.executeQuery();
        while(resultSet.next())
        {
            Student student = new Student(resultSet.getString(2));
            student.setStudId(resultSet.getInt(1));
            studentDetail.put(student.getStudId(), student);
        }
        return studentDetail;
    }

    public Map<Integer,Staff> getStaffIdAndName(Department department) throws Exception
    {
        Properties properties = Config.loadProperty();        
        HashMap<Integer,Staff> staffDetail = new HashMap<Integer,Staff>();
        String query = properties.getProperty("mysql.query.all_staffs");                
        String query1 = properties.getProperty("mysql.query.staffs_detail");                
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);        
        pstmt.setInt(1,department.getID());
        ResultSet resultSet  = pstmt.executeQuery();        
        while(resultSet.next())
        {
            Staff staff =new Staff(resultSet.getString(2));
            staff.setStaffId(resultSet.getInt(1));
            pstmt1.setInt(1,staff.getStaffId());        
            ResultSet resultSet1  = pstmt1.executeQuery();            
            while(resultSet1.next())
            {
                staff.getSubjectList().put(resultSet1.getInt(1), getSubjectEnum(resultSet1.getString(2)));
            }
            staffDetail.put(staff.getStaffId(), staff);
        }
        return staffDetail;
    }

    public Map<Integer,Staff> getAllStaffIdAndName() throws Exception
    {
        Properties properties = Config.loadProperty();        
        HashMap<Integer,Staff> staffDetail = new HashMap<Integer,Staff>();
        String query = properties.getProperty("mysql.query.alldepartment_staffs");                
        String query1 = properties.getProperty("mysql.query.staffs_detail");                
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);                
        ResultSet resultSet  = pstmt.executeQuery();        
        while(resultSet.next())
        {
            Staff staff =new Staff(resultSet.getString(2));
            staff.setStaffId(resultSet.getInt(1));            
            pstmt1.setInt(1,staff.getStaffId());        
            ResultSet resultSet1  = pstmt1.executeQuery();            
            while(resultSet1.next())
            {
                staff.getSubjectList().put(resultSet1.getInt(1), getSubjectEnum(resultSet1.getString(2)));
            }
            staffDetail.put(staff.getStaffId(), staff);
        }
        return staffDetail;
    }
    
    public Map<Integer,Student> getReportCard(Department department, int studentId) throws Exception
    {
        Properties properties = Config.loadProperty();
        HashMap<Integer,Student> studentDetail = new HashMap<Integer,Student>();
        String query = properties.getProperty("mysql.query.student");                
        String query1 = properties.getProperty("mysql.query.student_mark");                
        String query2 = properties.getProperty("mysql.query.student_report");                
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);        
        PreparedStatement pstmt2 = connection.prepareStatement(query2);                
        pstmt.setInt(1, studentId);
        ResultSet resultSet  = pstmt.executeQuery();                
        Student student = new Student("");        
        while(resultSet.next())
        {
            student.setStudId(resultSet.getInt(1));        
            student.setName(resultSet.getString(2));
        }            
        pstmt1.setInt(1, studentId);
        ResultSet resultSet1  = pstmt1.executeQuery();                        
        Report report = new Report();
        while(resultSet1.next())
        {        
            Subject subject = new Subject();
            subject.setName(getSubjectEnum(resultSet1.getString(1)));
            subject.setMark(resultSet1.getInt(2));
            subject.setStatus(resultSet1.getString(3));            
            report.getAllSubject().put(subject.getName(), subject);
        }
        pstmt2.setInt(1, studentId);
        ResultSet resultSet2 = pstmt2.executeQuery();                
        while(resultSet2.next())
        {        
            report.setTotalMark(resultSet2.getDouble(2));
            report.setStatus(resultSet2.getString(3));
            report.setPerformance(resultSet2.getString(4));
            report.setAverage(resultSet2.getDouble(5));                                
            student.setReport(report);
        }
        studentDetail.put(student.getStudId(), student);
        return studentDetail;
    }

    public Map<Integer,Student> getCompareStudent(Department department, int student_Id1,int student_Id2) throws Exception
    {
        Map<Integer,Student> studentDetail = getReportCard(department, student_Id1);
        for(Map.Entry<Integer,Student> student : getReportCard(department, student_Id2).entrySet())
        {
            studentDetail.put(student.getKey(), student.getValue());
        }
        return studentDetail;
    }

    public Map<Integer,Student> getStudentRank(Department department) throws Exception
    {
        Properties properties = Config.loadProperty();
        HashMap<Integer,Student> studentDetail = new HashMap<Integer,Student>();
        String query = properties.getProperty("mysql.query.student_rank");                
        PreparedStatement pstmt = connection.prepareStatement(query);        
        pstmt.setInt(1, department.getID());
        ResultSet resultSet  = pstmt.executeQuery();                
        while(resultSet.next())
        {
            Student student =new Student(resultSet.getString(2));
            Report report = new Report();
            student.setStudId(resultSet.getInt(1));
            report.setTotalMark(resultSet.getInt(3));
            report.setStatus(resultSet.getString(4));
            student.setReport(report);
            studentDetail.put(student.getStudId(), student);
        }               
        return studentDetail;
    }
    
    public List<Integer> getSubjectAverage(Department department) throws Exception
    {
        Properties properties = Config.loadProperty();
        List<Integer> subjectAverage = new ArrayList<Integer>();
        String query = properties.getProperty("mysql.query.subject_detail");                
        String query1 = properties.getProperty("mysql.query.subject_sum");                
        String query2 = properties.getProperty("mysql.query.subject_count");                
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);        
        PreparedStatement pstmt2 = connection.prepareStatement(query2);        
        pstmt.setInt(1, department.getID());
        ResultSet resultSet  = pstmt.executeQuery();                
        while(resultSet.next())
        {
            pstmt1.setInt(1, resultSet.getInt(1));
            ResultSet resultSet1  = pstmt1.executeQuery();                
            pstmt2.setInt(1, resultSet.getInt(1));
            ResultSet resultSet2  = pstmt2.executeQuery();                
            if(resultSet1.next() && resultSet2.next())
            {
                subjectAverage.add(resultSet1.getInt(1)/resultSet2.getInt(1));
            }
        }               
        return subjectAverage;
    }

    public Map<Integer,Student> getStudentsMarksAboveAverage(Department department) throws Exception
    {        
        HashMap<Integer,Student> studentDetail =new HashMap<Integer,Student>();
        for(Map.Entry<Integer,Student> Id : getStudentIdAndName(department).entrySet())
        {
        
            for(Map.Entry<Integer,Student> student : getReportCard(department, Id.getKey()).entrySet())
            {
                studentDetail.put(student.getKey(), student.getValue());
            }
        }
        return studentDetail;
    }
        
    public Map<String,Integer> gettopScorer(Department department) throws Exception
    {
        Properties properties = Config.loadProperty();
        HashMap<String,Integer> topscorer = new HashMap<String,Integer>();
        String query = properties.getProperty("mysql.query.subject_detail");                
        String query1 = properties.getProperty("mysql.query.top_scorer");                        
        String query2 = properties.getProperty("mysql.query.top_scorer_Id");                        
        String query3 = properties.getProperty("mysql.query.top_scorer_name");                        
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);                
        PreparedStatement pstmt2 = connection.prepareStatement(query2);        
        PreparedStatement pstmt3 = connection.prepareStatement(query3);                
        pstmt.setInt(1, department.getID());
        ResultSet resultSet  = pstmt.executeQuery();                
        while(resultSet.next())
        {
            pstmt1.setInt(1, resultSet.getInt(1));
            ResultSet resultSet1  = pstmt1.executeQuery();                            
            if(resultSet1.next())
            {                
                pstmt2.setInt(1, resultSet1.getInt(1));
                ResultSet resultSet2  = pstmt2.executeQuery();                            
                if(resultSet2.next())
                {
                    pstmt3.setInt(1, resultSet2.getInt(1));
                    ResultSet resultSet3  = pstmt3.executeQuery();                            
                    if(resultSet3.next())                    
                    {
                        topscorer.put(resultSet3.getString(1), resultSet1.getInt(1));
                    }
                }                
            }
        }               
        return topscorer;
    }

    public void insertStudent(Department department, Student student) throws Exception
    {
        Properties properties = Config.loadProperty();
        String query = properties.getProperty("mysql.query.add_student");                
        String query1 = properties.getProperty("mysql.query.max_student");
        String query2 = properties.getProperty("mysql.query.add_student_mark");                                
        String query3 = properties.getProperty("mysql.query.add_student_report");                                        
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);        
        PreparedStatement pstmt2 = connection.prepareStatement(query2);                
        PreparedStatement pstmt3 = connection.prepareStatement(query3);                
        pstmt.setString(1, student.getName());
        pstmt.setInt(2,department.getID());
        pstmt.executeUpdate();                                                        
        ResultSet resultSet = pstmt1.executeQuery();
        if(resultSet.next())
        {                        
            for(Map.Entry<SubjectName,Subject> subject : student.getReport().getAllSubject().entrySet())
            {                
                pstmt2.setInt(1, resultSet.getInt(1));
                pstmt2.setInt(2, subject.getValue().getId());
                pstmt2.setInt(3, subject.getValue().getMark());                
                pstmt2.setString(4, subject.getValue().getStatus());                
                pstmt2.executeUpdate();                
            }                            
        }
        pstmt3.setInt(1, resultSet.getInt(1));        
        pstmt3.setInt(2, (int)student.getReport().getTotalMark());
        pstmt3.setString(3, student.getReport().getStatus());
        pstmt3.setString(4, student.getReport().getPerformance());
        pstmt3.setInt(5, (int)student.getReport().getAverage());
        pstmt3.executeUpdate();        
    }                

    public void insertStaff(Department department, Staff staff) throws Exception
    {
        Properties properties = Config.loadProperty();
        String query = properties.getProperty("mysql.query.add_staff");                
        String query1 = properties.getProperty("mysql.query.max_staff");
        String query2 = properties.getProperty("mysql.query.add_staff_subject_allocation");                                        
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);        
        PreparedStatement pstmt2 = connection.prepareStatement(query2);                        
        pstmt.setString(1, staff.getName());
        pstmt.setInt(2,department.getID());
        pstmt.executeUpdate();                                                        
        ResultSet resultSet = pstmt1.executeQuery();
        if(resultSet.next())
        {                        
            for(Map.Entry<Integer,SubjectName> subject : staff.getSubjectList().entrySet())
            {                
                pstmt2.setInt(1, resultSet.getInt(1));
                pstmt2.setInt(2, subject.getKey());
                pstmt2.executeUpdate();                
            }                            
        }        
    }
    
    public void updateStudent(int studentId, int subjectId, int mark, Report report) throws Exception
    {
        Properties properties = Config.loadProperty();
        String query = properties.getProperty("mysql.query.update_student_mark");                
        String query1 = properties.getProperty("mysql.query.update_reports");
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);        
        pstmt.setInt(1, mark);
        pstmt.setInt(2, studentId);
        pstmt.setInt(3, subjectId);
        pstmt.executeUpdate();                                                                      
        pstmt1.setInt(1, (int)report.getTotalMark());
        pstmt1.setString(2, report.getStatus());
        pstmt1.setString(3, report.getPerformance());
        pstmt1.setInt(4, (int)report.getAverage());
        pstmt1.setInt(5, studentId);
        pstmt1.executeUpdate();        
    }
    
    public void deleteStudent(int studentId) throws Exception
    {
        Properties properties = Config.loadProperty();                      
        String query = properties.getProperty("mysql.query.delete_students");
        String query1 = properties.getProperty("mysql.query.delete_student_mark");
        String query2 = properties.getProperty("mysql.query.delete_report");        
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);
        PreparedStatement pstmt2 = connection.prepareStatement(query2);
        pstmt.setInt(1, studentId);                                                               
        pstmt1.setInt(1, studentId);                                                                      
        pstmt2.setInt(1, studentId);        
        pstmt2.executeUpdate();    
        pstmt1.executeUpdate();
        pstmt.executeUpdate();
    }
        
    public Map<String,Integer> getSubjectPassPercentage(Department department) throws Exception
    {
        Properties properties = Config.loadProperty();
        HashMap<String,Integer> subjectPassPercentage= new HashMap<String,Integer>();
        String query = properties.getProperty("mysql.query.count_with_pass");                
        String query1 = properties.getProperty("mysql.query.count_without_pass");                        
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);                
        for(Map.Entry<Integer, SubjectName> subject : department.getSubjectsList().entrySet())
        {
            pstmt.setInt(1, subject.getKey());
            pstmt1.setInt(1, subject.getKey());
            ResultSet resultSet  = pstmt.executeQuery();
            ResultSet resultSet1  = pstmt1.executeQuery();
            int percentage =0;
            if(resultSet.next() && resultSet1.next())
            {
            	int i = resultSet.getInt(1);
            	int j = resultSet1.getInt(1);
            	percentage = i* 100/j;
            }
            subjectPassPercentage.put(subject.getValue().toString(), percentage);
        }
           return subjectPassPercentage;
    }
    
    public Map<String,Integer> getDepartmentPassPercentage(Management maagement) throws Exception
    {
        Properties properties = Config.loadProperty();
        HashMap<String,Integer> departmentPassPercentage= new HashMap<String,Integer>();
        String query = properties.getProperty("mysql.query.department_count_with_pass");                
        String query1 = properties.getProperty("mysql.query.department_count_without_pass");                        
        PreparedStatement pstmt = connection.prepareStatement(query);        
        PreparedStatement pstmt1 = connection.prepareStatement(query1);                
        for(Map.Entry<Integer, String> dept : maagement.getDepartmentCode().entrySet())
        {
            pstmt.setInt(1, dept.getKey());
            pstmt1.setInt(1, dept.getKey());
            ResultSet resultSet  = pstmt.executeQuery();
            ResultSet resultSet1  = pstmt1.executeQuery();
            int percentage =0;
            if(resultSet.next() && resultSet1.next())
            {
            	int i = resultSet.getInt(1);
            	int j = resultSet1.getInt(1);
            	percentage = i* 100/j;
            }
            departmentPassPercentage.put(dept.getValue().toString(), percentage);
        }
        return departmentPassPercentage;
    }
    
    private SubjectName getSubjectEnum(String subject)
    {
        SubjectName subjectEnum = SubjectName.WEBDEVOLPMENT;
        if(subject.equals(SubjectName.WEBDEVOLPMENT.toString()))
        {
            subjectEnum = SubjectName.WEBDEVOLPMENT;
        }
        else if(subject.equals(SubjectName.RDBMS.toString()))
        {
            subjectEnum = SubjectName.RDBMS;
        }
        else if(subject.equals(SubjectName.DATASTRUCTURE.toString()))
        {
            subjectEnum = SubjectName.DATASTRUCTURE;
        }
        else if(subject.equals(SubjectName.OOPS.toString()))
        {
            subjectEnum = SubjectName.OOPS;
        }
        else if(subject.equals(SubjectName.JAVA.toString()))
        {
            subjectEnum = SubjectName.JAVA;
        }
        else if(subject.equals(SubjectName.ACCOUNTSMANAGEMENT.toString()))
        {
            subjectEnum = SubjectName.ACCOUNTSMANAGEMENT;
        }
        else if(subject.equals(SubjectName.ENTREPRENEURSHIP.toString()))
        {
            subjectEnum = SubjectName.ENTREPRENEURSHIP;
        }
        else if(subject.equals(SubjectName.FINANCIALMANAGEMENT.toString()))
        {
            subjectEnum = SubjectName.FINANCIALMANAGEMENT;
        }
        else if(subject.equals(SubjectName.MARKETINGMANAGEMENT.toString()))
        {
            subjectEnum = SubjectName.MARKETINGMANAGEMENT;
        }
        else if(subject.equals(SubjectName.OPERATIONMANAGEMENT.toString()))
        {
            subjectEnum = SubjectName.OPERATIONMANAGEMENT;
        }   
        return subjectEnum;
    }
}