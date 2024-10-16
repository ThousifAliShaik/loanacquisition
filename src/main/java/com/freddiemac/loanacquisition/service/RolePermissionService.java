package com.freddiemac.loanacquisition.service;


import com.freddiemac.loanacquisition.DTO.RolePermissionDTO;
import com.freddiemac.loanacquisition.entity.PermissionName;
import com.freddiemac.loanacquisition.entity.RolePermission;
import com.freddiemac.loanacquisition.repository.RolePermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionService(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    // Convert RolePermission entity to RolePermissionDTO
    private RolePermissionDTO convertToDTO(RolePermission rolePermission) {
        return new RolePermissionDTO(
            rolePermission.getRolePermissionId(),
            rolePermission.getPermissionName().name() // Permission as String
        );
    }

    // Convert RolePermissionDTO to RolePermission entity
    private RolePermission convertToEntity(RolePermissionDTO rolePermissionDTO) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRolePermissionId(rolePermissionDTO.getRolePermissionId());
        rolePermission.setPermissionName(PermissionName.valueOf(rolePermissionDTO.getPermissionName()));
        return rolePermission;
    }

    @Transactional(readOnly = true)
    public List<RolePermissionDTO> getAllRolePermissions() {
        return rolePermissionRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public RolePermissionDTO createRolePermission(RolePermissionDTO rolePermissionDTO) {
        RolePermission rolePermission = convertToEntity(rolePermissionDTO);
        RolePermission savedRolePermission = rolePermissionRepository.save(rolePermission);
        return convertToDTO(savedRolePermission);
    }

    @Transactional
    public void deleteRolePermission(UUID rolePermissionId) {
        rolePermissionRepository.deleteById(rolePermissionId);
    }
}
