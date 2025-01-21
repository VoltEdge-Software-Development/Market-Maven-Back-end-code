package com.smc.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(
            generator = "UUID"
    )
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String accountName;
    private String accountNumber;
    private BigDecimal amount;
    private String currency;
    private String accountType;
    @ManyToOne
    @JsonBackReference("smc_user")
    @JoinColumn(name = "smc_user_id", referencedColumnName = "id")
    private User user;

}
