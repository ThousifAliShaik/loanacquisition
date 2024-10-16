package com.freddiemac.loanacquisition.service;


import com.freddiemac.loanacquisition.DTO.RoleDTO;
import com.freddiemac.loanacquisition.entity.Role;
import com.freddiemac.loanacquisition.entity.UserRole;
import com.freddiemac.loanacquisition.repository.RoleRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Convert Role entity to RoleDTO
    private RoleDTO convertToDTO(Role role) {
        return new RoleDTO(
            role.getRoleId(),
            role.getRoleName().name() // Enum as String
        );
    }

    // Convert RoleDTO to Role entity
    private Role convertToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleId(roleDTO.getRoleId());
        role.setRoleName(UserRole.valueOf(roleDTO.getRoleName()));
        return role;
    }

    
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    
    public RoleDTO getRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
            .map(this::convertToDTO)
            .orElse(null);
    }

   
    public RoleDTO getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
            .map(this::convertToDTO)
            .orElse(null);
    }

   
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = convertToEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }


    public RoleDTO updateRole(UUID roleId, RoleDTO roleDTO) {
        Optional<Role> existingRole = roleRepository.findById(roleId);
        if (existingRole.isPresent()) {
            Role updatedRole = convertToEntity(roleDTO);
            updatedRole.setRoleId(roleId); // Maintain same ID
            Role savedRole = roleRepository.save(updatedRole);
            return convertToDTO(savedRole);
        }
        return null;
    }

 
//    public void deleteRole(UUID roleId) {
//        roleRepository.deleteById(roleId);
//    }
}
