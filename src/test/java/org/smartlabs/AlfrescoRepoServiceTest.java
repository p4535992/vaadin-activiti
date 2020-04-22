package org.smartlabs;

import org.apache.chemistry.opencmis.client.api.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.vaadin.activiti.simpletravel.smartlabs.core.services.AlfrescoRepoService;



@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class AlfrescoRepoServiceTest {

	@Autowired
	private AlfrescoRepoService repoService;
	
	
	@Test
	public void testService(){
		//assertTrue("Error Login Repo", repoService.login());
		Session login = repoService.login("admin","admin");
		
		repoService.getObjectTreeStructure(login, -1);
	}
	
}
