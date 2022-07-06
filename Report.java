package model;

import java.util.*;

import utility.EnumClass.ReportStatus;
import utility.EnumClass.OverallPerformance;
import utility.EnumClass.SubjectName;

public class Report 
{   
    private HashMap<SubjectName,Subject> allSubject;    
    private String rank;
    private double totalMark;
    private double average;
    private String status;
    private String performance;    

    public Report() 
    {
        this.allSubject = new HashMap<SubjectName,Subject>();
    }    

    public String getRank()
    {
        return rank;
    }
    
    public void setRank(String value)
    {
        this.rank= value;
    }
    
    public double getTotalMark()
    {
        return totalMark;
    }
    
    public void setTotalMark(double value)
    {
        this.totalMark = value;
    }

    public void addSubjectMarkToTotalMark(int value)
    {
        this.totalMark += value;
    }
    
    public double getAverage()
    {
        return average;
    }
    
    public void setAverage(double value)
    {
        this.average = value;
    }

    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String value)
    {
        this.status = value;
    }

    public HashMap<SubjectName,Subject> getAllSubject()
    {
        return allSubject;
    }
    
    public void setAllSubject(HashMap<SubjectName,Subject> value)
    {
        this.allSubject = value;
    }

    public String getPerformance()
    {
        return performance;
    }

    public void setPerformance(String value)
    {
        this.performance = value;
    }    
    
    public void assignoverallPerformance()
    {
        if(this.status == ReportStatus.FAIL.toString())
        {
            this.performance = OverallPerformance.POOR.toString();
        }
        else if(this.totalMark >= 450)
        {
            this.performance = OverallPerformance.EXCELLENT.toString();
        }
        else if(this.totalMark >= 400)
        {
            this.performance = OverallPerformance.VERYGOOD.toString();
        }
        else if(this.totalMark >= 350)
        {
            this.performance = OverallPerformance.GOOD.toString();
        }
        else if(this.totalMark > 300)
        {
            this.performance = OverallPerformance.AVERAGE.toString();
        }
        else if(this.totalMark > 250)
        {
            this.performance = OverallPerformance.NEEDIMPROVEMENT.toString();
        }                
    }
}