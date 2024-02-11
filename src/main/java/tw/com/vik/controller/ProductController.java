package tw.com.vik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tw.com.vik.constant.ProductCategory;
import tw.com.vik.dto.ProductRequest;
import tw.com.vik.model.Product;
import tw.com.vik.service.ProductService;

@RestController
public class ProductController
{

	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer productId)
	{
		Product product = productService.getProductById(productId);
		
		if(product != null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(product);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search)
    {
        List<Product> productList = productService.getProducts(category, search);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productList);
    }
	
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest)
	{
	    Integer productId = productService.createProduct(productRequest);
	    
	    Product product = productService.getProductById(productId);
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}
	
	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
	                                             @RequestBody @Valid ProductRequest productRequest)
    {
	    //檢查Product是否存在
	    Product product = productService.getProductById(productId);
	    
	    if(product == null)
	    {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	    
	    //修改商品數據
        productService.updateProduct(productId, productRequest);
        
        Product updateProduct = productService.getProductById(productId);
        
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }
	
	@DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId)
    {
        //刪除商品
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
	
	
}
