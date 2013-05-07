package com.sop.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.sp3.mvc.models.Payment;
import com.sp3.mvc.models.Product;

public class PaymentDao extends BaseDao {
	
	private static Logger logger = Logger.getLogger(PaymentDao.class);
	
	
	
	public String getMaxPaymentId() throws SQLException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MAX(CAST(SUBSTR(PAYMENTID, 9) AS INT)) FROM ")
        .append(myProps.getProperty("schemaName"))
        .append("PAYMENT");

		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		Integer maxId = jdbcTemp.queryForInt(sql);
		
		
		if(maxId == 0) {
			return null;
		} else {
			maxId = maxId+1;
			return "PAYMENT-"+maxId;
		}
	}
	
	public List<Payment> getPaymentsByStatus(String status) throws SQLException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("PAYMENT WHERE STATUS = ?");
		
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		@SuppressWarnings("unchecked")
		List<Payment> payments = (List<Payment>)jdbcTemp.queryForObject(sql,
			    new Object[]{status},
			    new RowMapper() {

			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	List<Payment> payments = new ArrayList<Payment>();
			        	do {
			        		Payment payment = new Payment();
							payment.setPaymentId(rs.getString("PAYMENTID"));
							payment.setOrderId(rs.getString("ORDERID"));
							payment.setPaymentAmount(rs.getDouble("TOTALAMOUNT"));
							payment.setPaymentDate(rs.getDate("PAYMENTDATE"));
							payment.setPaymentStatus(rs.getString("STATUS"));
							payments.add(payment);
			        	
			        	}while(rs.next());
			        	 return payments;
			        }
		});
		
		return payments;
	}
	

}
