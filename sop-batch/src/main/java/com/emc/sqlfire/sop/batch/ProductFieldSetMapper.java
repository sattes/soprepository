/**
 * 
 */
package com.emc.sqlfire.sop.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * @author manchk1
 * 
 */
public class ProductFieldSetMapper implements FieldSetMapper<Product> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.mapping.FieldSetMapper#mapFieldSet
	 * (org.springframework.batch.item.file.transform.FieldSet)
	 */
	@Override
	public Product mapFieldSet(FieldSet fieldSet) throws BindException {
		return new Product(
				fieldSet.readString("productId"),
				fieldSet.readString("catId"), 
				fieldSet.readDouble("unitCost"),
				fieldSet.readString("name"), 
				fieldSet.readString("description")
		);
	}
}
