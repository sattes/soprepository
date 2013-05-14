package com.sop.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class TransactionDao  extends BaseDao  {
	
	private static Logger logger = Logger.getLogger(TransactionDao.class);
	
	
	
	public String getMaxTxnId() throws SQLException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MAX(CAST(SUBSTR(TRANSACTIONID, 5) AS INT)) FROM ")
        .append(myProps.getProperty("schemaName"))
        .append("SOPTRANSACTION");

		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		Integer maxId = jdbcTemp.queryForInt(sql);
		
		
		if(maxId == 0) {
			return null;
		} else {
			maxId = maxId+1;
			return "TXN-"+maxId;
		}
	}

}
