package security_in_action.ssia_ch6.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import security_in_action.ssia_ch6.domain.model.entity.Product;
import security_in_action.ssia_ch6.domain.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
