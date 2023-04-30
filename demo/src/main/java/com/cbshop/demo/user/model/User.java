package com.cbshop.demo.user.model;

import com.cbshop.demo.user.role.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private boolean isVerificated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User user)) return false;

        return new EqualsBuilder().append(getId(), user.getId()).append(isVerificated(), user.isVerificated()).append(getFirstName(), user.getFirstName()).append(getLastName(), user.getLastName()).append(getEmail(), user.getEmail()).append(getPassword(), user.getPassword()).append(getRole(), user.getRole()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getFirstName()).append(getLastName()).append(getEmail()).append(getPassword()).append(getRole()).append(isVerificated()).toHashCode();
    }
}
