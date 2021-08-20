package com.task;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Trade {

    private String trade_id;
    private int version;
    private String counterParty_id;
    private String book_id;
    private LocalDate maturity_date;
    private LocalDate created_date;
    private String expired_flag;
}
