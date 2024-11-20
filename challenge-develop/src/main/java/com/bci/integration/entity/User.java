package com.bci.integration.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {

  @Id
  private UUID id;

  private String name;
  private String username;
  private String email;
  private String password;
  @CreationTimestamp
  @Column(updatable = false, name = "created_at")
  private String created;
  @CreationTimestamp
  @Column(name = "updated_at")
  private String updatedAt;
  @CreationTimestamp
  @Column(name = "last_loggin")
  private String lastLogin;
  @Column(length=10485760)
  private String token;
  private Boolean status;
  private String role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Phone> phones = new ArrayList<>();

}
