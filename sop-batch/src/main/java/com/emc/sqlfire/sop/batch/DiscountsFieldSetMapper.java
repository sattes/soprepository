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
public class DiscountsFieldSetMapper implements FieldSetMapper<Discounts> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.mapping.FieldSetMapper#mapFieldSet
	 * (org.springframework.batch.item.file.transform.FieldSet)
	 */
	@Override
	public Discounts mapFieldSet(FieldSet fieldSet) throws BindException {
		return new Discounts(
				fieldSet.readInt("discId"),
				fieldSet.readString("custType"), 
				fieldSet.readString("discountType"),
				fieldSet.readDouble("discountPercent")
		);
	}
}
