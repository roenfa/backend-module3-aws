package org.example.constants;

public interface AthenaQueries {


    String ATHENA_SELECT_QUERY = "SELECT * FROM transactions;";
    String ATHENA_FIND_BY_ID_QUERY = "SELECT * FROM transactions WHERE id = ";

    String ATHENA_CREATE_TRANSACTION = "INSERT INTO transactions (id,  date,type, amount ) VALUES (";

    String ATHENA_DELETE_TRANSACTION = "DELETE FROM transactions WHERE id = ";


}
