package security_in_action.ssia_ch16.domain.repository;

import java.util.Map;
import org.springframework.stereotype.Repository;
import security_in_action.ssia_ch16.domain.entity.Document;

@Repository
public class DocumentRepository {

    private final Map<String, Document> documents = Map.of(
            "abc123", new Document("natalie"),
            "qwe123", new Document("natalie"),
            "asd555", new Document("emma")
    );

    public Document findDocument(String code) {
        return documents.get(code);
    }
}
