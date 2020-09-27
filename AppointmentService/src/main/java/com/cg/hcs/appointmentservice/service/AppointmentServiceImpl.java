package com.cg.hcs.appointmentservice.service;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.hcs.appointmentservice.dao.AppointmentRepository;
import com.cg.hcs.appointmentservice.dto.AppointmentDto;
import com.cg.hcs.appointmentservice.entity.Appointment;
import com.cg.hcs.appointmentservice.exception.NoValueFoundException;
import com.cg.hcs.appointmentservice.exception.NotPossibleException;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	String appointmentNotPresent = "No appointment present with this appointment Id";

	@Override
	public String checkAppointmentStatus(BigInteger appointmentId) {
		Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
		if (appointment.isEmpty()) {
			logger.error("check appointment status failed, no appointmentId");
			throw new NoValueFoundException(appointmentNotPresent);
		}
		String status = "Pending";
		int statusValue = appointmentRepository.fetchStatusByAppointmentId(appointmentId);
		if (statusValue == 1)
			status = "Approved";
		else if (statusValue == -1)
			status = "Cancelled";
		return status;
	}

	@Override
	public List<AppointmentDto> fetchAppointmentsByUserId(String userId) {
		List<Appointment> appointmentList;
		String url = "http://localhost:8080/check?Id=" + userId;
		Boolean userExists = restTemplate.getForObject(url, boolean.class); 
		if (Boolean.FALSE.equals(userExists)) {
			throw new NoValueFoundException("No user present with this user Id");
		}
		try {
			appointmentList = appointmentRepository.fetchAppointmentsByUserId(userId);
		} catch (NoSuchElementException e) {
			throw new NoValueFoundException("You Haven't made any appointment yet");
		}
		return appointmentList.stream().map(a -> new AppointmentDto(a)).collect(Collectors.toList());
	}

	@Override
	public List<AppointmentDto> fetchAppointmentsByDiagnosticCenterId(String diagnosticCenterId) {
		List<Appointment> appointmentList;
		// ask amaan about the url
		String url = "http://diagnostic-center-service/diagnosticcenters/?Id=" + diagnosticCenterId;// change
		Boolean diagnosticCenterExists = restTemplate.getForObject(url, boolean.class);
		if (Boolean.FALSE.equals(diagnosticCenterExists)) {
			throw new NoValueFoundException("No Diagnostic Center present with this Center Id");
		}
		try {
			appointmentList = appointmentRepository.fetchAppointmentsByDiagnosticCenterId(diagnosticCenterId);
		} catch (NoSuchElementException e) {
			throw new NoValueFoundException("You Haven't made any appointment yet");
		}
		return appointmentList.stream().map(a -> new AppointmentDto(a)).collect(Collectors.toList());
	}

	@Override
	public boolean approveAppointment(BigInteger appointmentId) {
		Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
		if (appointment.isEmpty()) {
			throw new NoValueFoundException(appointmentNotPresent);
		}
		int statusValue = appointmentRepository.fetchStatusByAppointmentId(appointmentId);
		LocalDateTime dateTime = appointmentRepository.fetchDateTimeByAppointmentId(appointmentId);
		if (statusValue != -1 && statusValue != 1) {
			if ((dateTime.toLocalDate().equals(LocalDate.now())
					&& Duration.between(dateTime.toLocalTime(), LocalDateTime.now().toLocalTime()).toMinutes() >= 30
					&& dateTime.toLocalTime().isAfter(LocalTime.now())||dateTime.isAfter(LocalDateTime.now()) && !dateTime.toLocalDate().equals(LocalDate.now()))) {
				appointment.get().setStatus(1);
				appointmentRepository.save(appointment.get());
			} else
				throw new NotPossibleException("Appointment date and time is already missed!!");
		} else if (statusValue == 1) {
			throw new NotPossibleException("Appointment is already approved");
		} else {

			throw new NotPossibleException("Appointment is already cancelled");
		}
		return true;
	}

	@Override
	public AppointmentDto makeAppointment(AppointmentDto appointmentDto) {
		Appointment appointment = new Appointment(appointmentDto);

		appointment.setAppointmentId(null);
		appointment.setStatus(0);

		String diagnosticUrl = "http://Diagnostic-Service/diagnosticCenter?Id=" + appointment.getDiagnosticCenterId();
		Boolean diagnosticCenterExists = restTemplate.getForObject(diagnosticUrl, boolean.class);
		if (Boolean.FALSE.equals(diagnosticCenterExists)) {
			throw new NoValueFoundException("No Diagnostic Center present with this Center Id");
		}

		String userUrl = "http://User-Service/userExist?Id=" + appointment.getUserId();// create this
		Boolean userExists = restTemplate.getForObject(userUrl, boolean.class); // use restTemplate from user
		if (Boolean.FALSE.equals(userExists)) {
			throw new NoValueFoundException("No user present with this user Id");
		}

		String testUrl = "http://Test-Service/TestCenter?Id=" + appointment.getDiagnosticCenterId();
		Boolean testCenterExists = restTemplate.getForObject(testUrl, boolean.class);
		if (Boolean.FALSE.equals(testCenterExists)) {
			throw new NoValueFoundException("No Test Center present with this Center Id");
		}

		if (validateDate(appointment.getDateTime())) {
			List<LocalTime> allSlots = getAvailableSlots(appointment.getTestId(), appointment.getDateTime());
			if (allSlots.contains(appointment.getDateTime().toLocalTime())) {
				appointment = appointmentRepository.save(appointment);
			} else
				throw new NotPossibleException("sorry we can't book you appointment");

		}
		return new AppointmentDto(appointment);
	}

	@Override
	public boolean validateDate(LocalDateTime dateTime) {
		if (dateTime.toLocalDate().isEqual(LocalDate.now())
				|| !dateTime.toLocalDate().isBefore(LocalDate.now().plusDays(30))
				|| dateTime.toLocalDate().isBefore(LocalDate.now())) {
			throw new NotPossibleException("Please select date of today or any day within 30 days of today.");
		} else if (dateTime.toLocalDate().getDayOfWeek().toString().equalsIgnoreCase("SUNDAY")) {
			throw new NotPossibleException("we are closed on Sunday!");
		}

		return true;
	}

	@Override
	public List<LocalTime> getAvailableSlots(String testId, LocalDateTime time) {
		List<LocalTime> allSlots = new ArrayList<>();
		allSlots.add(LocalTime.of(9, 00));
		allSlots.add(LocalTime.of(9, 30));
		allSlots.add(LocalTime.of(10, 00));
		allSlots.add(LocalTime.of(10, 30));
		allSlots.add(LocalTime.of(11, 00));
		allSlots.add(LocalTime.of(11, 30));
		allSlots.add(LocalTime.of(12, 00));
		allSlots.add(LocalTime.of(12, 30));
		allSlots.add(LocalTime.of(13, 00));
		allSlots.add(LocalTime.of(14, 00));
		allSlots.add(LocalTime.of(14, 30));
		allSlots.add(LocalTime.of(15, 00));
		allSlots.add(LocalTime.of(15, 30));
		allSlots.add(LocalTime.of(16, 00));
		allSlots.add(LocalTime.of(16, 30));
		allSlots.add(LocalTime.of(17, 00));
		allSlots.add(LocalTime.of(17, 30));
		allSlots.add(LocalTime.of(18, 00));
		allSlots.add(LocalTime.of(18, 30));
		allSlots.add(LocalTime.of(19, 00));
		allSlots.add(LocalTime.of(19, 30));

		List<Appointment> listOfAppointments =  appointmentRepository.findAll();
		List<Appointment> toRemoveAppointments = new ArrayList<>();
		Iterator<Appointment> itr = listOfAppointments.iterator();
		if (validateDate(time)) {
			while (itr.hasNext()) {
				Appointment appointment = itr.next();
				if (!appointment.getDateTime().toLocalDate().isEqual(time.toLocalDate())) {
					toRemoveAppointments.add(appointment);
				} else if (!appointment.getTestId().equals(testId)) {
					toRemoveAppointments.add(appointment);
				}
			}
		}
		listOfAppointments.removeAll(toRemoveAppointments);
		itr = listOfAppointments.iterator();
		while (itr.hasNext()) {
			Appointment appointment = itr.next();
			if (appointment.getDateTime().toLocalTime().compareTo(time.toLocalTime()) == 0) {

				allSlots.remove(appointment.getDateTime().toLocalTime());

			}
		}

		return allSlots;

	}

	@Override
	public String cancelAppointment(BigInteger appointmentId) {
		Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
		if (appointment.isEmpty()) {
			throw new NoValueFoundException(appointmentNotPresent);
		}
		appointment.get().setStatus(-1);
		Appointment appointmentObject = appointmentRepository.save(appointment.get());

		if (appointmentObject.getStatus() != -1) {
			return "Problem Occured";
		} else {
			return "Appointment Cancelled!!";
		}
	}

	@Override
	public boolean checkAppointmentByAppointmentId(BigInteger appointmentId) {
		Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
		if (appointment.isEmpty()) {
			throw new NoValueFoundException(appointmentNotPresent);
		}

		return true;
	}

	@Override
	public AppointmentDto searchAppointmentByAppointmentId(BigInteger appointmentId) {
		Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
		if (appointment.isEmpty()) {
			throw new NoValueFoundException(appointmentNotPresent);
		}

		return new AppointmentDto(appointment.get());
	}

	@Override
	public boolean getPendingAppointmentsForDiagnosticCenter(String diagnosticCenterId) {

		String diagnosticUrl = "http://Diagnostic-Service/diagnosticCenter?Id=" + diagnosticCenterId;
		Boolean diagnosticCenterExists = restTemplate.getForObject(diagnosticUrl, boolean.class);
		if (Boolean.FALSE.equals(diagnosticCenterExists)) {
			throw new NoValueFoundException("No Diagnostic Center present with this Center Id");
		}

		List<Appointment> appointments = appointmentRepository
				.checkPendingAppointmentForDiagnosticCenter(diagnosticCenterId);

	return !appointments.isEmpty();

	}

	@Override
	public boolean getPendingAppointmentsForTestCenter(String testCenterId) {
		String testUrl = "http://test-center-Service/diagnosticCenter?Id=" + testCenterId;//change url based on test service
		Boolean testCenterExists = restTemplate.getForObject(testUrl, boolean.class);
		if (Boolean.FALSE.equals(testCenterExists)) {
			throw new NoValueFoundException("No Test Center present with this Center Id");
		}

		List<Appointment> appointments = appointmentRepository
				.checkPendingAppointmentForTestCenter(testCenterId);

	return !appointments.isEmpty();
	}

}
