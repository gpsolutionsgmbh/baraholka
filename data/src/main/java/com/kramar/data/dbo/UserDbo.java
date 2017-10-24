package com.kramar.data.dbo;

import com.kramar.data.type.UserRole;
import com.kramar.data.type.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDbo extends AbstractAuditableEntity {

    private static final long serialVersionUID = -1803488657443938487L;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_surname")
    private String userSurname;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "image_id")
    private ImageDbo image;

    @ElementCollection
    @CollectionTable(name = "user2roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<UserRole> userRoles;

    public UserDbo(UUID id) {
        this.setId(id);
        this.version = 0L;
    }
}
