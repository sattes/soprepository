package com.sop.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DirectDebitDao extends BaseDao {
	
	private static Logger logger = Logger.getLogger(DirectDebitDao.class);
	
	
	public String getMaxDebitId() throws SQLException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MAX(CAST(SUBSTR(DEBTID, 7) AS INT)) FROM ")
        .append(myProps.getProperty("schemaName"))
        .append("DIRECTDEBIT");

		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
	
		Integer maxId  = jdbcTemp.queryForInt(sql);
		
		if(maxId == 0) {
			return null;
		} else {
			maxId = maxId+1;
			return "DEBIT-"+maxId;
		}
	}

}
