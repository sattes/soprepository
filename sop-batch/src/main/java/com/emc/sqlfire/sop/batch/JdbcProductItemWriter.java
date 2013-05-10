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
public class JdbcProductItemWriter implements ItemWriter<Product> {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "myProps")

	private Properties myProps;
	
	private String schemaName ;
	
	public JdbcProductItemWriter(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends Product> items) throws Exception {
		schemaName = myProps.getProperty("sop.schemaName");
		for(Product item : items) {
			jdbcTemplate.update(
				"insert into "+schemaName+"Product (productid,catid,unitcost,name,descn) values (?,?,?,?,?)",
				item.getProductId(),item.getCatId(),item.getUnitCost(),item.getName(),item.getDescription()
			);
		}
	}

}
