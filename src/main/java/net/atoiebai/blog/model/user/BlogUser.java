package net.atoiebai.blog.model.user;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class BlogUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name", nullable = false)
    @Size(min = 2, max = 30, message = "required field | обязательное поле (2-30 letters)")
    String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 2, max = 30, message = "required field | обязательное поле (2-30 letters)")
    String lastName;

    @Email(message = "Invalid email address")
    @NotEmpty(message = "required field | обязательное поле")
    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "username" , nullable = false)
    @NotEmpty(message = "required field | обязательное поле")
    String username;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "sex" , nullable = false)
    Sex sex;

    @Column(name = "bio")
    String bio;

    @CreationTimestamp
    @Column(name = "created" , nullable = false)
    private LocalDateTime created;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "required field | обязательное поле")
    String password;

    @Transient
    String confirmationPassword;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    Status status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return status.equals(Status.ACTIVE);
    }

    public boolean checkPassword() {
        return confirmationPassword.equals(password);
    }
}