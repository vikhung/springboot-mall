package tw.com.vik.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tw.com.vik.dao.ProductDao;
import tw.com.vik.dto.ProductQueryParams;
import tw.com.vik.dto.ProductRequest;
import tw.com.vik.model.Product;
import tw.com.vik.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService
{
	@Autowired
	private ProductDao productDao;

	@Override
	public Product getProductById(Integer productId) {
		return productDao.getProductById(productId);
	}

    @Override
    public Integer createProduct(ProductRequest productRequest)
    {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest)
    {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProduct(Integer productId)
    {
        productDao.deleteProduct(productId);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams)
    {
        return productDao.getProducts(productQueryParams);
    }
    
    @Override
    public Integer countProduct(ProductQueryParams productQueryParams)
    {
        return productDao.countProduct(productQueryParams);
    }
}
