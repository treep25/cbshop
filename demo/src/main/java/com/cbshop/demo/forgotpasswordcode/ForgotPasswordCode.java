package com.cbshop.demo.forgotpasswordcode;

import com.cbshop.demo.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
public class ForgotPasswordCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String forgotPasswordCode;
    @OneToOne(cascade = CascadeType.MERGE)
    private User user;
    @CreatedDate
    private Date createDate;

    public boolean isValid() {
        long thirtyMin = 1000 * 60 * 30;
        return new Date().before(new Date(createDate.getTime() + thirtyMin));
    }
}