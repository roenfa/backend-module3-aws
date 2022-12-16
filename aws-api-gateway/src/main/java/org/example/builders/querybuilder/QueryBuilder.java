package org.example.builders.querybuilder;

import java.util.List;

public class QueryBuilder {
    private String select;
    private String from;
    private String where;



    public void select(String... columns){
        this.select = "SELECT " + String.join(",", columns);
    }

    public void from(String table){
        this.from = "FROM " + table;
    }

    public void where(List<String[]> conditions){
        String result = "";
        for(String[] c: conditions){
            int size = conditions.size() - conditions.indexOf(c);
            result += "WHERE "
            + String.join(" ", c) 
            + (size == 1 ? "" : " AND ");
        }
        this.where = result;
    }

}
