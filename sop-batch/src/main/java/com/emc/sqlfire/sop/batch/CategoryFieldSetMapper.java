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
public class CategoryFieldSetMapper implements FieldSetMapper<Category> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.mapping.FieldSetMapper#mapFieldSet
	 * (org.springframework.batch.item.file.transform.FieldSet)
	 */
	@Override
	public Category mapFieldSet(FieldSet fieldSet) throws BindException {
		return new Category(
				fieldSet.readString("catId"),
				fieldSet.readString("name"), 
				fieldSet.readString("description")
		);
	}
}
