package security_in_action.ssia_ch16.security.config;

import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import security_in_action.ssia_ch16.domain.entity.Document;

@Component
public class DocumentPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication a, Object subject, Object permission) {
        Document document = (Document) subject;
        String p = (String) permission;

        boolean admin = a.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(p));

        return admin || document.getOwner().equals(a.getName());
    }

    @Override
    public boolean hasPermission(Authentication a, Serializable id, String type, Object permission) {
        return false;
    }
}
