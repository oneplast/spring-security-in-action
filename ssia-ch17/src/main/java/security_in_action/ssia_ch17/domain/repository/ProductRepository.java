package security_in_action.ssia_ch17.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import security_in_action.ssia_ch17.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.name LIKE %:text%
            AND p.owner = ?#{authentication.name}
            """)
    List<Product> findProductByNameContains(String text);
}
