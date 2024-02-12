package tw.com.vik.dao;

import java.util.List;

import tw.com.vik.dto.ProductQueryParams;
import tw.com.vik.dto.ProductRequest;
import tw.com.vik.model.Product;

public interface ProductDao
{
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void deleteProduct(Integer productId);
	
	List<Product> getProducts(ProductQueryParams productQueryParams);
	
	Integer countProduct(ProductQueryParams productQueryParams);
}