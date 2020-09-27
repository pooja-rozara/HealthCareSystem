package com.cg.healthcaresystem.centers.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cg.healthcaresystem.centers.DTO.DiagnosticCenterDTO;
import com.cg.healthcaresystem.centers.entity.DiagnosticCenter;
import com.cg.healthcaresystem.centers.exception.InvalidCenterIdException;
import com.cg.healthcaresystem.centers.exception.InvalidArgumentException;
import com.cg.healthcaresystem.centers.service.DiagnosticCenterService;


/* @RestController tells Spring that this is the Handler class and contains handler methods. Basically does combined job of @Controller and @ResponseBody
 * @RequestMapping annotation is used to map the web requests to specified 
 * handler classes or methods 
 */

@RestController
@RequestMapping("/diagnosticcenters")
public class DiagnosticCenterController {

	@Autowired
	private DiagnosticCenterService diagnosticCenterService;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger log = LoggerFactory.getLogger(DiagnosticCenterController.class);

	
	@PostMapping("/add")
	public ResponseEntity<DiagnosticCenterDTO> addDiagnosticCenter(
			@Valid @RequestBody DiagnosticCenterDTO diagnosticCenterDto) {
		DiagnosticCenter diagnosticCenter = convertFromDTO(diagnosticCenterDto);
		diagnosticCenter = diagnosticCenterService.addCenter(diagnosticCenter);
		DiagnosticCenterDTO centerDto = convertToDTO(diagnosticCenter);
		return new ResponseEntity<>(centerDto, HttpStatus.OK);

	}

	@DeleteMapping("/remove")
	public ResponseEntity<Boolean> removeDiagnosticCenter(@RequestBody DiagnosticCenterDTO diagnosticCenterDto) {
		String url = "http://Appointment-service/pendingAppointment?Id=" + diagnosticCenterDto.getCenterId();
		ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
		Boolean appointmentsExists = response.getBody();
		if (appointmentsExists == true) {
			throw new InvalidArgumentException("Cannot Delete Center. Pending Appointments!");
		} else {
			Boolean result = diagnosticCenterService.deleteCenter(diagnosticCenterDto);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}

	}

	@GetMapping("/search_center_by_id/{diagnosticCenterId}")
	public ResponseEntity<DiagnosticCenterDTO> getDiagnosticCenterById(@PathVariable String diagnosticCenterId) {
		DiagnosticCenter diagnosticCenter = diagnosticCenterService.findCenterById(diagnosticCenterId);
		DiagnosticCenterDTO centerDto = convertToDTO(diagnosticCenter);
		return new ResponseEntity<>(centerDto, HttpStatus.OK);

	}

	@GetMapping("/allcenters")
	public ResponseEntity<List<DiagnosticCenterDTO>> getAllDiagnosticCenters() {
		List<DiagnosticCenter> diagnosticCenters = diagnosticCenterService.findAllCenters();
		List<DiagnosticCenterDTO> diagnosticCenterDTOList = new ArrayList<>();
		for (DiagnosticCenter diagnosticCenter : diagnosticCenters) {
			diagnosticCenterDTOList.add(convertToDTO(diagnosticCenter));
		}
		return new ResponseEntity<>(diagnosticCenterDTOList, HttpStatus.OK);

	}

	@GetMapping("/search_center_name/{diagnosticCenterName}")
	public ResponseEntity<DiagnosticCenterDTO> getDiagnosticCenterByName(@PathVariable String diagnosticCenterName) {
		DiagnosticCenter diagnosticCenter = diagnosticCenterService.findCenterByName(diagnosticCenterName);
		DiagnosticCenterDTO centerDto = convertToDTO(diagnosticCenter);
		return new ResponseEntity<>(centerDto, HttpStatus.OK);

	}

	@PutMapping("/update")
	public ResponseEntity<DiagnosticCenterDTO> updateDiagnosticCenter(
			@RequestBody DiagnosticCenterDTO diagnosticCenterDto) {
		DiagnosticCenter diagnosticCenter = convertFromDTO(diagnosticCenterDto);
		diagnosticCenter = diagnosticCenterService.updateCenter(diagnosticCenter);
		DiagnosticCenterDTO centerDto = convertToDTO(diagnosticCenter);
		return new ResponseEntity<>(centerDto, HttpStatus.OK);

	}

	public DiagnosticCenter convertFromDTO(DiagnosticCenterDTO diagnosticCenterDto) {
		DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
		diagnosticCenter.setCenterId(diagnosticCenterDto.getCenterId());
		diagnosticCenter.setCenterName(diagnosticCenterDto.getCenterName());
		diagnosticCenter.setCenterAddress(diagnosticCenterDto.getCenterAddress());
		diagnosticCenter.setContactNumber(diagnosticCenterDto.getContactNumber());
		return diagnosticCenter;

	}

	public DiagnosticCenterDTO convertToDTO(DiagnosticCenter diagnosticCenter) {
		DiagnosticCenterDTO diagnosticCenterDto = new DiagnosticCenterDTO();
		diagnosticCenterDto.setCenterId(diagnosticCenter.getCenterId());
		diagnosticCenterDto.setCenterName(diagnosticCenter.getCenterName());
		diagnosticCenterDto.setCenterAddress(diagnosticCenter.getCenterAddress());
		diagnosticCenterDto.setContactNumber(diagnosticCenter.getContactNumber());
		return diagnosticCenterDto;

	}

	@ExceptionHandler(InvalidCenterIdException.class)
	public String handleCenterIdNotFound(InvalidCenterIdException ex) {
		log.error("handleCenterIdNotFound()", ex);
		String msg = ex.getMessage();
		return msg;
	}

	@ExceptionHandler(InvalidArgumentException.class)
	public String handleCenterNotFound(InvalidArgumentException ex) {
		log.error("handleCenterNotFound()", ex);
		String msg = ex.getMessage();
		return msg;
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handleAll(Throwable ex) {
		log.error("Something went wrong", ex);
		String msg = ex.getMessage();
		return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
