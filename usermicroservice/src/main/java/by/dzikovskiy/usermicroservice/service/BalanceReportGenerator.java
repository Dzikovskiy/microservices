package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.entity.BalanceSnapshot;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Service
public class BalanceReportGenerator {

    public static ByteArrayInputStream balanceToCSV(List<BalanceSnapshot> snapshots) throws IOException {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);

        for (BalanceSnapshot snapshot : snapshots) {
            List<String> data = Arrays.asList(
                    String.valueOf(snapshot.getId()),
                    snapshot.getSnapshotTime().toString(),
                    snapshot.getCurrencyCode(),
                    snapshot.getInnerDepositAmount(),
                    snapshot.getInnerWithdrawAmount(),
                    snapshot.getInnerBalance(),
                    snapshot.getInnerTradingFees(),
                    snapshot.getInnerUsersBalance(),
                    snapshot.getOuterBalance(),
                    snapshot.getOuterWarmBalance(),
                    snapshot.getOuterHotBalance(),
                    snapshot.getOuterColdBalance(),
                    snapshot.getTxFee()
            );

            csvPrinter.printRecord(data);
        }

        csvPrinter.flush();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
