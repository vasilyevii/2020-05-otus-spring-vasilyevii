package ru.otus.security;

import org.springframework.security.acls.model.Permission;

import java.util.List;

public interface AclService {
    void addAclPermissions(AclObject aclObject, List<Permission> permissions);
}
