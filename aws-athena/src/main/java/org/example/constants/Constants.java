package org.example.constants;

public interface Constants {
    String ATHENA_OUTPUT_BUCKET = "s3://mf-athena-s3";
    String ATHENA_SAMPLE_QUERY = "SELECT * FROM transactions WHERE amount >= 5";
    int TIMEOUT = 100000;
    long SLEEP_AMOUNT_IN_MS = 1000;
    String ATHENA_DEFAULT_DATABASE = "devopsathenadb";

    // .builder().select(id,name).where(type='$type','and').order() -> "SELECT * FROM transactions WHERE type='REFUND'

}
