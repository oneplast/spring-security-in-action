package security_in_action.ssia_ch6.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security_in_action.ssia_ch6.domain.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
