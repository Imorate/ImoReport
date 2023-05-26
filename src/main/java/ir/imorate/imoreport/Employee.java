package ir.imorate.imoreport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Employee {
    private String name;
    private String country;
    private Date birthDate;
    private BigDecimal payment;
    private BigDecimal bonus;
}
