package com.example.studious;

public class SearchParameters {
    private String searchBy; // can be all, course-code or course-name
    private String query;
    private String searchMethod; // either starts-with or contains, but default="contains"

    public String getSearchBy() {
        return this.searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSearchMethod() {
        return this.searchMethod;
    }

    public void setSearchMethod(String searchMethod) {
        this.searchMethod = searchMethod;
    } 

}
