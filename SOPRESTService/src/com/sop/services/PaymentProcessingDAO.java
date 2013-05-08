package com.sop.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sop.message.transform.Order;
import com.sop.message.transform.Payment;

public class PaymentProcessingDAO {
	
	protected static Logger logger = Logger.getLogger(PaymentProcessingDAO.class);

	JdbcTemplate jdbcTemp;
	
	public Payment getPaymentDetailFor(String paymentid){
		@SuppressWarnings("unchecked")
		Payment actor = (Payment) jdbcTemp.queryForObject(
			    "select paymentid, orderid, totalamount,paymentdate,status from sopv2.payment where paymentid = ?",
			    new Object[]{paymentid},
			    new RowMapper() {

			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	Payment actor = new Payment();
			            actor.setOrderId(rs.getString("ORDERID"));
			            actor.setPaymentId(rs.getString("PAYMENTID"));
			            actor.setPaymentAmount(rs.getDouble("TOTALAMOUNT"));
			            actor.setPaymentStatus(rs.getString("STATUS"));
			            actor.setPaymentDate(rs.getDate("PAYMENTDATE"));
			            return actor;
			        }
			    });
		logger.debug("Payment retrieved = "+actor);
		return actor;

	}
	
	public boolean updatePaymentStatus(String paymentid, String status){
		boolean updFlg = false;
		
		int rowsUpdated = jdbcTemp.update("update sopv2.payment set status=? where paymentid=?",status,paymentid);
		if(rowsUpdated>0)
			updFlg=true;
		logger.debug("Payment updated = "+updFlg);
		return updFlg;
	}
	public JdbcTemplate getJdbcTemp() {
		return jdbcTemp;
	}
	public void setJdbcTemp(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
}
