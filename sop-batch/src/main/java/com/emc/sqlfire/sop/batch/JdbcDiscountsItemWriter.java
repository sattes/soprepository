/**
 * 
 */
package com.emc.sqlfire.sop.batch;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author manchk1
 *
 */
public class JdbcDiscountsItemWriter implements ItemWriter<Discounts> {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "myProps")

	private Properties myProps;
	
	private String schemaName ;
	
	public JdbcDiscountsItemWriter(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends Discounts> items) throws Exception {
		schemaName = myProps.getProperty("sop.schemaName");
		for(Discounts item : items) {
			jdbcTemplate.update(
				"insert into "+schemaName+"discount (discid,custtype,disctype,discpercent) values (?,?,?,?)",
				item.getDiscId(),item.getCustType(),item.getDiscountType(),item.getDiscountPercent()
			);
		}
	}

}
