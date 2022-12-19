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
        this.from = " FROM " + "(\"" + table + "\")";
    }

    public void where(String conditions){
        this.where = " WHERE " + conditions;
        // String result = " WHERE ";
        // for(String[] c: conditions){
        //     int size = conditions.size() - conditions.indexOf(c);
        //     result += String.join(" ", c) 
        //     + (size == 1 ? "" : " AND ");
        // }
        // this.where = result;
    }

    public String getQuery(){
        return select + from + where + ";";
    }

}
