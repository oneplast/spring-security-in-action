package security_in_action.ssia_ch17.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import security_in_action.ssia_ch17.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @PostFilter("filterObject.owner == authentication.name")
    public List<Product> findProductByNameContains(String text);
}
