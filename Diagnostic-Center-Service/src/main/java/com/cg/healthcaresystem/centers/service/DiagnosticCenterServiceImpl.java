package com.cg.healthcaresystem.centers.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.healthcaresystem.centers.DTO.DiagnosticCenterDTO;
import com.cg.healthcaresystem.centers.dao.DiagnosticCenterDao;
import com.cg.healthcaresystem.centers.entity.DiagnosticCenter;
import com.cg.healthcaresystem.centers.exception.InvalidArgumentException;
import com.cg.healthcaresystem.centers.exception.InvalidCenterIdException;

@Service
public class DiagnosticCenterServiceImpl implements DiagnosticCenterService {

	@Autowired
	private DiagnosticCenterDao diagnosticCenterDao;

	@Override
	public DiagnosticCenter addCenter(DiagnosticCenter diagnosticCenter) {
		if (diagnosticCenter == null) {
			throw new InvalidArgumentException("Please provide some data");
		} else {
			UUID uuid = UUID.randomUUID();
			diagnosticCenter.setCenterId(uuid.toString().substring(0, 13).replace("-", ""));
			return diagnosticCenterDao.save(diagnosticCenter);

		}
	}

	@Override
	public boolean deleteCenter(DiagnosticCenterDTO diagnosticCenterDto) {
		String centerId = diagnosticCenterDto.getCenterId();
		if (centerId == null) {
			throw new InvalidArgumentException("Center Id cannot be null");
		}
		Optional<DiagnosticCenter> center = diagnosticCenterDao.findById(centerId);
		if (center.isPresent()) {
			diagnosticCenterDao.deleteById(centerId);
			return true;
		} else {
			throw new InvalidCenterIdException("Sorry, Center not found with the provided Id");
		}
	}

	@Override
	public DiagnosticCenter findCenterById(String diagnosticCenterId) {
		Optional<DiagnosticCenter> diagnosticCenter = diagnosticCenterDao.findById(diagnosticCenterId);
		if (diagnosticCenter.isPresent()) {
			return diagnosticCenter.get();

		} else {
			throw new InvalidCenterIdException("Sorry, No center found with the given Id");
		}
	}

	@Override
	public List<DiagnosticCenter> findAllCenters() {
		return diagnosticCenterDao.findAll();
	}

	@Override
	public DiagnosticCenter findCenterByName(String diagnosticCenterName) {
		return diagnosticCenterDao.findByName(diagnosticCenterName);
	}

	@Override
	public DiagnosticCenter updateCenter(DiagnosticCenter diagnosticCenter) {
		if (diagnosticCenter == null) {
			throw new InvalidArgumentException("Please provide Center Details");
		}
		Optional<DiagnosticCenter> center = diagnosticCenterDao.findById(diagnosticCenter.getCenterId());
		if (!center.isPresent()) {
			throw new InvalidArgumentException("Warning! center with the given Id not present. Will add a new center");
		} else {
			diagnosticCenterDao.save(diagnosticCenter);
			return diagnosticCenter;
		}

	}
}