package com.freddiemac.loanacquisition.entity;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private UUID roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, unique = true)
    private UserRole roleName;

    @OneToMany(mappedBy = "role")
    private Set<UserProfile> userProfiles;

    @OneToMany(mappedBy = "role")
    private Set<RolePermission> rolePermissions;

	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public UserRole getRoleName() {
		return roleName;
	}

	public void setRoleName(UserRole roleName) {
		this.roleName = roleName;
	}
    
}
