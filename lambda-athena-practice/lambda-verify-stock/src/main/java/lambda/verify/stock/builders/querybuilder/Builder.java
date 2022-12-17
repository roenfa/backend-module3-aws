package lambda.verify.stock.builders.querybuilder;

import java.util.List;

public interface Builder {
    void select(String... columns);
    void from(String table);
    void where(List<String[]> conditions);
}
