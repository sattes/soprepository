/**
 * 
 */
package com.emc.sqlfire.sop.batch;

import java.util.Properties;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/sop-btach-job-test.xml")
public class CategoryJobTest {

	@Autowired
	private Job job;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "myProps")

	private Properties myProps;

	private String schemaName ;
	
	@Before public void setUp() {
		
		schemaName = myProps.getProperty("sop.schemaName");
		jdbcTemplate.update("delete from "+schemaName+"category");
	}
	
	@Test public void flatFileReading() throws Exception {
		JobExecution execution = jobLauncher.run(job, new JobParameters());
		assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
		assertEquals(10,jdbcTemplate.queryForInt("select count(1) from "+schemaName+"category"));
		System.out.println("record count: "+jdbcTemplate.queryForInt("select count(1) from "+schemaName+"category"));
	}
	
}
