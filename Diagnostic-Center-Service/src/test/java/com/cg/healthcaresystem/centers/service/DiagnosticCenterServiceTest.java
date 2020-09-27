package com.cg.healthcaresystem.centers.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.healthcaresystem.centers.dao.DiagnosticCenterDao;
import com.cg.healthcaresystem.centers.entity.DiagnosticCenter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiagnosticCenterServiceTest {
	@InjectMocks
	private DiagnosticCenterServiceImpl diagnosticCenterService;

	@MockBean
	private DiagnosticCenterDao diagnosticCenterDao;

	private DiagnosticCenter diagnosticCenter;

	@BeforeEach
	void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		diagnosticCenter = new DiagnosticCenter();
		diagnosticCenter.setCenterName("AIIMS");
		diagnosticCenter.setCenterAddress("Cantt Road");
		diagnosticCenter.setContactNumber("9767678547");
		diagnosticCenter.setCenterId("1");

	}

	@Test
	public void testAddCenter() {

		Mockito.when(diagnosticCenterDao.save(diagnosticCenter)).thenReturn(diagnosticCenter);
		assertThat(diagnosticCenterService.addCenter(diagnosticCenter)).isEqualTo(diagnosticCenter);

	}

	@Test
	public void testfindCenterByName() {

		Mockito.when(diagnosticCenterDao.findByName("AIIMS")).thenReturn(diagnosticCenter);
		assertThat(diagnosticCenterService.findCenterByName("AIIMS")).isEqualTo(diagnosticCenter);

	}

	@Test
	public void testfindCenterById() {

		Mockito.when(diagnosticCenterDao.findById("1")).thenReturn(Optional.of(diagnosticCenter));
		assertThat(diagnosticCenterService.findCenterById("1")).isEqualTo(diagnosticCenter);

	}

	@Test
	public void testfindAllCenters() {
		DiagnosticCenter diagnosticCenter1 = new DiagnosticCenter();
		diagnosticCenter1.setCenterName("AIIMS");
		diagnosticCenter1.setCenterAddress("Cantt Road");
		diagnosticCenter1.setContactNumber("9767678547");

		DiagnosticCenter diagnosticCenter2 = new DiagnosticCenter();
		diagnosticCenter2.setCenterName("APOLLO");
		diagnosticCenter2.setCenterAddress("Sarojni Marg");
		diagnosticCenter2.setContactNumber("9089237432");

		List<DiagnosticCenter> centerList = new ArrayList<>();
		centerList.add(diagnosticCenter1);
		centerList.add(diagnosticCenter2);
		Mockito.when(diagnosticCenterDao.findAll()).thenReturn(centerList);
		assertThat(diagnosticCenterService.findAllCenters()).isEqualTo(centerList);

	}


	@Test
	public void testdeleteCenter() {
		Mockito.when(diagnosticCenterDao.findById("1")).thenReturn(Optional.of(diagnosticCenter));
		Mockito.when(diagnosticCenterDao.existsById("1")).thenReturn(false);
		assertFalse(diagnosticCenterDao.existsById(diagnosticCenter.getCenterId()));

	}
	
//	@Test
//	public void testupdateCenter() {
//
//		Mockito.when(diagnosticCenterDao.findById("1")).thenReturn(Optional.of(diagnosticCenter));
//		diagnosticCenter.setCenterAddress("5 BN road");
//		Mockito.when(diagnosticCenterDao.save(diagnosticCenter)).thenReturn(diagnosticCenter);
//
//		DiagnosticCenter center = new DiagnosticCenter();
//		center.setCenterId("1");
//		center.setCenterAddress("5 BN road");
//		assertThat(diagnosticCenterService.updateCenter(center)).isEqualTo(diagnosticCenter);
//
//	}

}