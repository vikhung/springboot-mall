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

import tw.com.vik.dao.OrderDao;
import tw.com.vik.model.Order;
import tw.com.vik.model.OrderItem;
import tw.com.vik.rowmapper.OrderItemRowMapper;
import tw.com.vik.rowmapper.OrderRowMapper;

@Component
public class OrderDaoImpl implements OrderDao
{
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, int totalAmount)
    {
        String sql = "insert into `order`(user_id, total_amount, created_date, last_modified_date) "
                + "values(:userId, :totalAmount, :createdDate, :lastModifiedDate)";
  
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);
        
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        
        int orderId = keyHolder.getKey().intValue();
        
        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList)
    {
        //方法一：逐項新增效率較低
//        for(OrderItem orderItem : orderItemList)
//        {
//            String sql = "insert into orderItem(order_id, product_id, quantity, amount) "
//                    + "values(:orderId, :productId, :quantity, :amount)";
//      
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("orderId", orderId);
//            map.put("productId", orderItem.getProductId());
//            map.put("quantity", orderItem.getQuantity());
//            map.put("amount", orderItem.getAmount());
//            
//            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
//        }
        

        //方法二：Batch處理，效率較高
        String sql = "insert into order_item(order_id, product_id, quantity, amount) "
                + "values(:orderId, :productId, :quantity, :amount)";
        MapSqlParameterSource[] mapSqlParameterSource = new MapSqlParameterSource[orderItemList.size()];
        for(int i=0;i<orderItemList.size();i++)
        {
            OrderItem orderItem = orderItemList.get(i);
            mapSqlParameterSource[i] = new MapSqlParameterSource();
            mapSqlParameterSource[i].addValue("orderId", orderId);
            mapSqlParameterSource[i].addValue("productId", orderItem.getProductId());
            mapSqlParameterSource[i].addValue("quantity", orderItem.getQuantity());
            mapSqlParameterSource[i].addValue("amount", orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, mapSqlParameterSource);
    }

    @Override
    public Order getOrderById(Integer orderId)
    {
        String sql = "select order_id, user_id, total_amount, created_date, last_modified_date"
                + " from `order` where order_id = :orderId";
     
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
     
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
     
        if(!orderList.isEmpty())
            return orderList.get(0);
        else
            return null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId)
    {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url "
                + "FROM mall.order_item as oi "
                + "left join product as p "
                + "on oi.product_id = p.product_id "
                + "where oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        
        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
        
        return orderItemList;
    }
}
