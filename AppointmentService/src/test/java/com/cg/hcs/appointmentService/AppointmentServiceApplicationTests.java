package com.cg.hcs.appointmentService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.healthCareSystem.appointmentService.service.AppointmentService;

@SpringBootTest
class AppointmentServiceApplicationTests {

	@Autowired
	private AppointmentService appointmentService;
	
	@Test
	public void checkAppointmentStatus() {
		//String status=appointmentService.checkAppointmentStatus(1);
	}

}
