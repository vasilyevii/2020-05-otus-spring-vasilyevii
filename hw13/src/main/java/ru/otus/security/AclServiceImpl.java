package ru.otus.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AclServiceImpl implements AclService {

    private final MutableAclService mutableAclService;

    @Override
    public void addAclPermissions(AclObject aclObject, List<Permission> permissions) {
        ObjectIdentity oid = new ObjectIdentityImpl(aclObject.getClass(), aclObject.getId());
        MutableAcl acl = mutableAclService.createAcl(oid);
        final Sid user = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        permissions.forEach(permission -> acl.insertAce(acl.getEntries().size(), permission, user, true));
        mutableAclService.updateAcl(acl);
    }
}
