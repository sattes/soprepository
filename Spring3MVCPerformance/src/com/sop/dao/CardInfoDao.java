package com.sop.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class CardInfoDao extends BaseDao {
	
	private static Logger logger = Logger.getLogger(CardInfoDao.class);
	
		
	public String getMaxCardId() throws SQLException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
        sb.append("SELECT MAX(CAST(SUBSTR(CARDINFOID, 6) AS INT)) FROM ")
        .append(myProps.getProperty("schemaName"))
        .append("CARDINFO");

		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);

		

		Integer maxId  = jdbcTemp.queryForInt(sql);

		if(maxId == 0) {
			return null;
		} else {
			maxId = maxId+1;
			return "CARD-"+maxId;
		}
	}

}
