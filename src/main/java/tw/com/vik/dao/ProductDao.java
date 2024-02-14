package tw.com.vik.dao;

import java.util.List;

import tw.com.vik.dto.ProductQueryParams;
import tw.com.vik.dto.ProductRequest;
import tw.com.vik.model.Product;

public interface ProductDao
{
    List<Product> getProducts(ProductQueryParams productQueryParams);
    
    Product getProductById(Integer productId);

    Integer countProduct(ProductQueryParams productQueryParams);

	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void updateStock(Integer productId, Integer stock);
	
	void deleteProduct(Integer productId);
}