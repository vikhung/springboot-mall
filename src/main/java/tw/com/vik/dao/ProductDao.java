package tw.com.vik.dao;

import java.util.List;

import tw.com.vik.constant.ProductCategory;
import tw.com.vik.dto.ProductRequest;
import tw.com.vik.model.Product;

public interface ProductDao
{
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void deleteProduct(Integer productId);
	
	List<Product> getProducts(ProductCategory productCategory, String search);
}