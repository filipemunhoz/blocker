package br.com.blocker.blockersapi.sql;

import com.google.common.net.InetAddresses;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.InetAddress;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLHelper {

    public static String generateBulkInsert(){

        final int TOTAL_RANGE_3 = 254;
        final int TOTAL_RANGE_4 = 254;
        final int FINISHER = TOTAL_RANGE_3 * TOTAL_RANGE_4 -1;
        int counter = 0;

        final StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO blocker.ip (`timestamp`, address, origin) VALUES ");

        for(int i=0; i < TOTAL_RANGE_3; i++){
            for(int j=0; j < TOTAL_RANGE_4; j++){
                final InetAddress address = InetAddresses.forString(String.format("192.168.%s.%s", i, j));
                Integer addr = Math.abs(InetAddresses.coerceToInteger(address));
                builder.append("(CURRENT_TIMESTAMP, " + addr + ", 'Generated')");

                if(counter != FINISHER) {
                    builder.append(",");
                }
                counter++;
            }
        }
        builder.append(";");
        return builder.toString();
    }
}
