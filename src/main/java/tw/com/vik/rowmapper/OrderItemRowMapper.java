package tw.com.vik.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tw.com.vik.model.OrderItem;

public class OrderItemRowMapper implements RowMapper<OrderItem>
{
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        OrderItem order = new OrderItem();
        
        order.setOrderItemId(rs.getInt("order_item_id"));
        order.setOrderId(rs.getInt("order_id"));
        order.setProductId(rs.getInt("product_id"));
        order.setQuantity(rs.getInt("quantity"));
        order.setAmount(rs.getInt("amount"));
        order.setProductName(rs.getString("product_name"));
        order.setImageUrl(rs.getString("image_url"));
        
        return order;
    }
    
}
