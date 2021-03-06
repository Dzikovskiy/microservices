package by.dzikovskiy.usermicroservice.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BalanceSnapshot {

    private Long id;

    private LocalDateTime snapshotTime;

    //-------------------- Inner Data --------------------

    private String currencyCode;

    private String innerDepositAmount; //BigDecimal

    private String innerWithdrawAmount; //BigDecimal

    //remove -> calculate after request from db (innerDepositAmount - innerWithdrawAmount)
    private String innerBalance; //BigDecimal

    private String innerTradingFees; //BigDecimal

    private String innerUsersBalance; //BigDecimal

    //-------------------- Quantum Data --------------------

    //remove -> calculate after request from db (outerWarmBalance + outerHotBalance + outerColdBalance)
    private String outerBalance; //BigDecimal

    private String outerWarmBalance; //BigDecimal

    private String outerHotBalance; //BigDecimal

    private String outerColdBalance; //BigDecimal

    private String txFee; //BigDecimal

}
