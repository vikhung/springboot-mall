package tw.com.vik.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import tw.com.vik.constant.ProductCategory;
import tw.com.vik.dao.ProductDao;
import tw.com.vik.dto.ProductRequest;
import tw.com.vik.model.Product;
import tw.com.vik.rowmapper.ProductRowMapper;

@Component
public class ProductDaoImpl implements ProductDao
{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public Product getProductById(Integer productId)
	{
		String sql = "select product_id, product_name, category, image_url, price, stock, "
				   + "description, created_date, last_modified_date from product where product_id = :productId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		
		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
		
		if(!productList.isEmpty())
			return productList.get(0);
		else
			return null;
	}

    @Override
    public Integer createProduct(ProductRequest productRequest)
    {
        String sql = "insert into product(product_name, category, image_url, price, stock, "
                   + "description, created_date, last_modified_date) "
                   + "values(:productName, :category, :imageUrl, :price, :stock, :description, "
                   + ":createdDate, :lastModifiedDate)";
     
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("productName", productRequest.getProductName());
         map.put("category", productRequest.getCategory().toString());
         map.put("imageUrl", productRequest.getImageUrl());
         map.put("price", productRequest.getPrice());
         map.put("stock", productRequest.getStock());
         map.put("description", productRequest.getDescription());
         
         Date now = new Date();
         map.put("createdDate", now);
         map.put("lastModifiedDate", now);
         
         KeyHolder keyHolder = new GeneratedKeyHolder();
         
         namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
         
         int productId = keyHolder.getKey().intValue();
         
         return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest)
    {
        String sql = "update product set product_name = :productName, category = :category, image_url = :imageUrl,"
                + " price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate "
                + " where product_id = :productId";
  
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        
        Date now = new Date();
        map.put("lastModifiedDate", now);
        
        map.put("productId", productId);
        
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }
    
    @Override
    public void deleteProduct(Integer productId)
    {
        String sql = "delete from product where product_id = :productId";
  
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
          
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }

    @Override
    public List<Product> getProducts(ProductCategory productCategory, String search)
    {
        String sql = "select product_id, product_name, category, image_url, price, stock, "
                + "description, created_date, last_modified_date "
                + "from product where 1=1";
     
        Map<String, Object> map = new HashMap<String, Object>();
        
        if(productCategory != null)
        {
            sql = sql + " AND category = :category";
            map.put("category", productCategory.name());
        }
        
        if(search != null)
        {
            sql = sql + " AND product_name like :search";
            map.put("search", "%" + search + "%");
        }
     
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
     
        return productList;
    }
}

