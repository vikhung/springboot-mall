package tw.com.vik.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tw.com.vik.dao.ProductDao;
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

}
