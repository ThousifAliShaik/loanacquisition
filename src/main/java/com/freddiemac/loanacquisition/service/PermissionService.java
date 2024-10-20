package com.freddiemac.loanacquisition.service;


import com.freddiemac.loanacquisition.dto.PermissionDTO;
import com.freddiemac.loanacquisition.entity.Permission;
import com.freddiemac.loanacquisition.entity.PermissionName;
import com.freddiemac.loanacquisition.repository.PermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    // Convert Permission entity to PermissionDTO
    private PermissionDTO convertToDTO(Permission permission) {
        return new PermissionDTO(
            permission.getPermissionId(),
            permission.getPermissionName().name() // Enum as String
        );
    }

    // Convert PermissionDTO to Permission entity
    private Permission convertToEntity(PermissionDTO permissionDTO) {
        Permission permission = new Permission();
        permission.setPermissionId(permissionDTO.getPermissionId());
        permission.setPermissionName(PermissionName.valueOf(permissionDTO.getPermissionName()));
        return permission;
    }

    @Transactional(readOnly = true)
    public List<PermissionDTO> getAllPermissions() {
        return permissionRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PermissionDTO getPermissionById(UUID permissionId) {
        return permissionRepository.findById(permissionId)
            .map(this::convertToDTO)
            .orElse(null);
    }

    @Transactional
    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        Permission permission = convertToEntity(permissionDTO);
        Permission savedPermission = permissionRepository.save(permission);
        return convertToDTO(savedPermission);
    }

    @Transactional
    public void deletePermission(UUID permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}
