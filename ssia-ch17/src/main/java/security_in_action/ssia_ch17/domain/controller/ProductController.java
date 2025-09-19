package security_in_action.ssia_ch17.domain.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import security_in_action.ssia_ch17.domain.model.Product;
import security_in_action.ssia_ch17.domain.repository.ProductRepository;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
/*
    private final ProductService productService;


    @GetMapping("/sell")
    public List<Product> sellProduct() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("beer", "nikolai"));
        products.add(new Product("candy", "nikolai"));
        products.add(new Product("chocolate", "julien"));

        return productService.sellProducts(products);
    }

    @GetMapping("/find")
    public List<Product> findProducts() {
        return productService.findProducts();
    }
*/

    @GetMapping("/products/{text}")
    public List<Product> findProductsContaining(@PathVariable String text) {
        return productRepository.findProductByNameContains(text);
    }
}
