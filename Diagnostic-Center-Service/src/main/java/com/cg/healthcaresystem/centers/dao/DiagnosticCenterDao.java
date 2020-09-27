package com.cg.healthcaresystem.centers.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.healthcaresystem.centers.entity.DiagnosticCenter;


public interface DiagnosticCenterDao extends JpaRepository<DiagnosticCenter, String> {

	@Query(value = "Select * from diagnostic_centers where center_name =?1 ", nativeQuery = true)
	DiagnosticCenter findByName(String diagnosticCenterName);


}
