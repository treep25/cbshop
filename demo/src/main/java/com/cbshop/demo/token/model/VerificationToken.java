package com.cbshop.demo.token.model;

import com.cbshop.demo.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    @OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private User user;
    @CreatedDate
    private Date createdDate;

    public boolean isValid (){
        long twoHours = 1000 * 60 * 60 * 2;
        return new Date().before(new Date(createdDate.getTime() + twoHours));
    }
}
