package security_in_action.ssia_ch16.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import security_in_action.ssia_ch16.domain.entity.Document;
import security_in_action.ssia_ch16.domain.repository.DocumentRepository;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    //    @PostAuthorize("hasPermission(returnObject, 'ROLE_admin')")
    @PreAuthorize("hasPermission(#code, 'document', 'ROLE_admin')")
    public Document getDocument(String code) {
        return documentRepository.findDocument(code);
    }
}
