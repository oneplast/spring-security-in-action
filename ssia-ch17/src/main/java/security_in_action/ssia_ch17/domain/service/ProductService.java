package security_in_action.ssia_ch17.domain.service;

import java.util.List;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;
import security_in_action.ssia_ch17.domain.model.Product;

@Service
public class ProductService {

    @PreFilter("filterObject.owner == authentication.name")
    public List<Product> sellProducts(List<Product> products) {
        return products;
    }
}
