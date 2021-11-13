package br.com.blocker.blockersapi.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SQLHelperTest {

    @Test
    void generateBulkInsert(){
        final String sql = SQLHelper.generateBulkInsert();
        Assertions.assertNotNull(sql);
    }
}
