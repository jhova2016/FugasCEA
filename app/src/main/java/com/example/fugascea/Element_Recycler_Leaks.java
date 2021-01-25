package com.example.fugascea;

public class Element_Recycler_Leaks {

    String Id;
    String Locate;
    String References;
    String ReportDate;
    String WhoReported;
    String Origin;
    String Importance;
    String Status;
    String responsible;

    public Element_Recycler_Leaks(String id, String locate, String references, String reportDate, String whoReported, String origin, String importance, String status, String responsible) {
        Id = id;
        Locate = locate;
        References = references;
        ReportDate = reportDate;
        WhoReported = whoReported;
        Origin = origin;
        Importance = importance;
        Status = status;
        this.responsible = responsible;
    }

    public String getReferences() {
        return References;
    }

    public void setReferences(String references) {
        References = references;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getImportance() {
        return Importance;
    }

    public void setImportance(String importance) {
        Importance = importance;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLocate() {
        return Locate;
    }

    public void setLocate(String locate) {
        Locate = locate;
    }

    public String getReportDate() {
        return ReportDate;
    }

    public void setReportDate(String reportDate) {
        ReportDate = reportDate;
    }

    public String getWhoReported() {
        return WhoReported;
    }

    public void setWhoReported(String whoReported) {
        WhoReported = whoReported;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
