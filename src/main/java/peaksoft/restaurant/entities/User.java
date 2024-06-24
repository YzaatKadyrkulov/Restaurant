package peaksoft.restaurant.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.restaurant.enums.Role;
import peaksoft.restaurant.validator.ValidPassword;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "base_id_gen", sequenceName = "user_seq", allocationSize = 1)
public class User extends BaseEntity implements UserDetails {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank
    @Size(min = 4)
    private String password;
    @NotBlank
    @ValidPassword
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    @NotBlank
    private Role role;
    @NotNull
    @Min(value = 0)
    private int experience;

    @ManyToOne(cascade = {MERGE,REFRESH,DETACH})
    private Restaurant restaurant;

    @ManyToOne(cascade = {MERGE,REFRESH,REMOVE})
    private Cheque cheque;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
