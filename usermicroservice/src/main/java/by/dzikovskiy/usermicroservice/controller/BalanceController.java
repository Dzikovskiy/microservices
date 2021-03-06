package by.dzikovskiy.usermicroservice.controller;

import by.dzikovskiy.usermicroservice.entity.BalanceSnapshot;
import by.dzikovskiy.usermicroservice.security.annotation.UserAndAdminAuthorization;
import by.dzikovskiy.usermicroservice.service.BalanceReportGenerator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Controller
public class BalanceController {
    private final String filename = "snapshot.csv";

    @GetMapping(value = "/balance/csv", produces = "text/csv")
    @UserAndAdminAuthorization
    public ResponseEntity generateCsv(@RequestBody List<BalanceSnapshot> snapshots) {
        try {
            InputStreamResource file = new InputStreamResource(BalanceReportGenerator.balanceToCSV(snapshots));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/csv"))
                    .body(file);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to generate report: " + filename, e);
        }
    }
}
