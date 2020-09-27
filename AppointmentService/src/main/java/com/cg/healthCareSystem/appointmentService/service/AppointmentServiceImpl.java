package com.cg.healthCareSystem.appointmentService.service;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.healthCareSystem.appointmentService.dao.AppointmentRepository;
import com.cg.healthCareSystem.appointmentService.dto.AppointmentDto;
import com.cg.healthCareSystem.appointmentService.entity.Appointment;
import com.cg.healthCareSystem.appointmentService.exception.NoValueFoundException;
import com.cg.healthCareSystem.appointmentService.exception.NotPossibleException;

import javassist.NotFoundException;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String checkAppointmentStatus(BigInteger appointmentId) {
		Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
		if (appointment.isEmpty()) {
			throw new NoValueFoundException("No appointment present with this appointment Id");
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
		List<Appointment> appointmentList = new ArrayList<Appointment>();
		String url = "http://localhost:8080/check?Id=" + userId;// create this
		Boolean userExists = restTemplate.getForObject(url, boolean.class); // use restTemplate from user
		if (!userExists) {
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
		List<Appointment> appointmentList = new ArrayList<Appointment>();
		// ask amaan about the url
		String url = "http://diagnostic-center-service/diagnosticcenters/?Id=" + diagnosticCenterId;// change
		Boolean diagnosticCenterExists = restTemplate.getForObject(url, boolean.class);
		if (!diagnosticCenterExists) {
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
			throw new NoValueFoundException("No appointment present with this appointment Id");
		}
		int statusValue = appointmentRepository.fetchStatusByAppointmentId(appointmentId);
		LocalDateTime dateTime = appointmentRepository.fetchDateTimeByAppointmentId(appointmentId);
		if (statusValue != -1 && statusValue != 1) {
			if (dateTime.toLocalDate().equals(LocalDate.now())
					&& Duration.between(dateTime.toLocalTime(), LocalDateTime.now().toLocalTime()).toMinutes() >= 30
					&& dateTime.toLocalTime().isAfter(LocalTime.now())) {
				appointment.get().setStatus(1);
				appointmentRepository.save(appointment.get());
			} else if (dateTime.isAfter(LocalDateTime.now()) && !dateTime.toLocalDate().equals(LocalDate.now())) {
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
		if (!diagnosticCenterExists) {
			throw new NoValueFoundException("No Diagnostic Center present with this Center Id");
		}

		String userUrl = "http://User-Service/userExist?Id=" + appointment.getUserId();// create this
		Boolean userExists = restTemplate.getForObject(userUrl, boolean.class); // use restTemplate from user
		if (!userExists) {
			throw new NoValueFoundException("No user present with this user Id");
		}

		String testUrl = "http://Test-Service/TestCenter?Id=" + appointment.getDiagnosticCenterId();
		Boolean testCenterExists = restTemplate.getForObject(testUrl, boolean.class);
		if (!testCenterExists) {
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
		List<LocalTime> allSlots = new ArrayList<LocalTime>();
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

		List<Appointment> listOfAppointments = (List<Appointment>) appointmentRepository.findAll();
		List<Appointment> toRemoveAppointments = new ArrayList<Appointment>();
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
			throw new NoValueFoundException("No appointment present with this appointment Id");
		}
		appointment.get().setStatus(-1);
		Appointment appointmentObject = appointmentRepository.save(appointment.get());

		if (appointmentObject != null) {
			return "Appointment Cancelled!!";
		} else {
			return "Problem Occured";
		}
	}

	@Override
	public boolean checkAppointmentByAppointmentId(BigInteger appointmentId) {

		return appointmentRepository.existsById(appointmentId);
	}

	@Override
	public AppointmentDto searchAppointmentByAppointmentId(BigInteger appointmentId) {

		return new AppointmentDto(appointmentRepository.findById(appointmentId).get());
	}

	@Override
	public boolean getPendingAppointmentsForDiagnosticCenter(String diagnosticCenterId) {

		String diagnosticUrl = "http://Diagnostic-Service/diagnosticCenter?Id=" + diagnosticCenterId;
		Boolean diagnosticCenterExists = restTemplate.getForObject(diagnosticUrl, boolean.class);
		if (!diagnosticCenterExists) {
			throw new NoValueFoundException("No Diagnostic Center present with this Center Id");
		}
		
		List<Appointment> appointments=appointmentRepository.checkPendingAppointmentForDiagnosticCenter(diagnosticCenterId);
		if(appointments.isEmpty())
		{
			return false;
		}
		else
			return true;
			

	}

}
