package org.example.builders.querybuilder;

import java.lang.reflect.Field;

public class QueryManager {
    Object queryObject;
    QueryBuilder queryBuilder;

    public QueryManager(Object queryObject, QueryBuilder queryBuilder){
        this.queryObject = queryObject;
        this.queryBuilder = queryBuilder;
    }

    public String getMatches(){
        Field[] fields = queryObject.getClass().getDeclaredFields();
        for(Field f: fields){
            f.getName();
        }
        return "";
    }
}
