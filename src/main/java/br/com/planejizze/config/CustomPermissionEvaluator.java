package br.com.planejizze.config;

import br.com.planejizze.model.Role;
import br.com.planejizze.model.Usuario;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.Map;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        String targetType = ((String) targetDomainObject).replace("Resource", "").toUpperCase();
        return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetType.toUpperCase(),
                permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        for (Role role : ((Usuario) auth.getPrincipal()).getRoles()) {
            Map<String, Map<String, Boolean>> perm = (Map<String, Map<String, Boolean>>) role.getPermissions();
            for (Map.Entry<String, Map<String, Boolean>> CRUD : perm.entrySet()) {
                if (CRUD.getKey().toUpperCase().equals(targetType)) {
                    for (Map.Entry<String, Boolean> resultado : CRUD.getValue().entrySet()) {
                        if (resultado.getKey().toUpperCase().equals(permission)) {
                            if (resultado.getValue().equals(true)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

