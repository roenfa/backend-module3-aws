package org.example.constants;

public interface Constants {
    String ATHENA_OUTPUT_BUCKET = "s3://backend_djag-s3/results/";
    String ATHENA_SAMPLE_QUERY = "SELECT * FROM transactions where type = 'refund';";
    int TIMEOUT = 100000;
    long SLEEP_AMOUNT_IN_MS = 1000;
    String ATHENA_DEFAULT_DATABASE = "backend_djag_fp";
}
