package pl.jellytech.machiavelli.users.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private UUID userId;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "nickName", unique = true, nullable = false)
    private String nickname;
    @Column(name = "avatar", columnDefinition = "TEXT")
    @Lob
    private byte[] avatar;
    @Column(name = "userRole", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole;
}
