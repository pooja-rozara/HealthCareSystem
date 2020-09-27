package com.cg.healthcaresystem.centers.service;

import java.util.List;

import com.cg.healthcaresystem.centers.DTO.DiagnosticCenterDTO;
import com.cg.healthcaresystem.centers.entity.DiagnosticCenter;

public interface DiagnosticCenterService {

	DiagnosticCenter addCenter(DiagnosticCenter diagnosticCenter);

	boolean deleteCenter(DiagnosticCenterDTO diagnosticCenterDto);

	DiagnosticCenter findCenterById(String diagnosticCenterId);

	List<DiagnosticCenter> findAllCenters();

	DiagnosticCenter findCenterByName(String diagnosticCenterName);

	DiagnosticCenter updateCenter(DiagnosticCenter diagnosticCenter);

}
