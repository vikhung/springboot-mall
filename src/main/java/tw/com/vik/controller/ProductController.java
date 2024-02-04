package tw.com.vik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
