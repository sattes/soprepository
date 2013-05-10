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
public class CustomerRolesFieldSetMapper implements FieldSetMapper<CustomerRoles> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.mapping.FieldSetMapper#mapFieldSet
	 * (org.springframework.batch.item.file.transform.FieldSet)
	 */
	@Override
	public CustomerRoles mapFieldSet(FieldSet fieldSet) throws BindException {
		return new CustomerRoles(
				fieldSet.readInt("roleId"),
				fieldSet.readString("roleDesc"), 
				fieldSet.readString("roleName")
		);
	}
}
