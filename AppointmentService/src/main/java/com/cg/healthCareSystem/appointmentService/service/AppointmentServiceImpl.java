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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.healthCareSystem.appointmentService.dao.AppointmentRepository;
import com.cg.healthCareSystem.appointmentService.dao.DiagnosticCenterRepository;
import com.cg.healthCareSystem.appointmentService.dao.TestRepository;
import com.cg.healthCareSystem.appointmentService.dao.UserRepository;
import com.cg.healthCareSystem.appointmentService.entity.Appointment;
import com.cg.healthCareSystem.appointmentService.entity.DiagnosticCenter;
import com.cg.healthCareSystem.appointmentService.entity.User;
import com.cg.healthCareSystem.appointmentService.exception.NoValueFoundException;
import com.cg.healthCareSystem.appointmentService.exception.NotPossibleException;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private UserRepository userRepository;// use another service and remove this

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepository;// use another service and remove this

	@Autowired
	private TestRepository testRepository;
	//
	@Override
	public String checkAppointmentStatus(BigInteger appointmentId) {
		Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
		System.out.println(appointmentId);
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
	public List<Appointment> fetchAppointmentsByUserId(String userId) {
		List<Appointment> appointmentList = null;
		Optional<User> user = userRepository.findById(userId); // use another service here
		if (user.isEmpty()) {
			throw new NoValueFoundException("No user present with this user Id");
		}
		try {
			appointmentList = appointmentRepository.fetchAppointmentsByUserId(user.get());
		} catch (NoSuchElementException e) {
			throw new NoValueFoundException("You Haven't made any appointment yet");
		}
		return appointmentList;
	}

	@Override
	public List<Appointment> fetchAppointmentsByDiagnosticCenterId(String diagnosticCenterId) {
		List<Appointment> appointmentList = null;
		Optional<DiagnosticCenter> diagnosticCenter = diagnosticCenterRepository.findById(diagnosticCenterId);// use another service here
		if (diagnosticCenter.isEmpty()) {
			throw new NoValueFoundException("No Diagnostic Center present with this Center Id");
		}
		try {
			appointmentList = appointmentRepository.fetchAppointmentsByDiagnosticCenterId(diagnosticCenter.get());
		} catch (NoSuchElementException e) {
			throw new NoValueFoundException("You Haven't made any appointment yet");
		}
		return appointmentList;
	}

	@Override
	public boolean approveAppointment(BigInteger appointmentId) {
		Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
		if (appointment.isEmpty()) {
			throw new NoValueFoundException("No appointment present with this appointment Id");
		}
		int statusValue = appointmentRepository.fetchStatusByAppointmentId(appointmentId);
		LocalDateTime dateTime = appointmentRepository.fetchDateTimeByAppointmentId(appointmentId);
		if (statusValue != -1) {
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
	public Appointment makeAppointment(String userId, String diagnosticCenterId, String testCenterId,
			LocalDateTime dateTime) {

		if (validateDate(dateTime)) {
			List<LocalTime> allSlots = getAvailableSlots(testCenterId, dateTime);
			if (allSlots.contains(dateTime.toLocalTime())) {
				Appointment appointment = new Appointment(userRepository.findById(userId).get(), testRepository.findById(testCenterId).get(), dateTime, diagnosticCenterRepository.findById(diagnosticCenterId).get(), 0);
				appointmentRepository.save(appointment);
			}

		} else if (dateTime.toLocalDate().getDayOfWeek().toString().equalsIgnoreCase("SUNDAY")) {
			throw new NotPossibleException("we are closed on Sunday!");
		} else {
			throw new NotPossibleException("Please select date of today or any day within 30 days of today.");
		}

		return null;
	}

	@Override
	public boolean validateDate(LocalDateTime dateTime) {
		if (dateTime.toLocalDate().isEqual(LocalDate.now())
				|| !dateTime.toLocalDate().isBefore(LocalDate.now().plusDays(30))
				|| dateTime.toLocalDate().getDayOfWeek().toString().equalsIgnoreCase("SUNDAY"))
			return false;
		return true;
	}

	@Override
	public List<LocalTime> getAvailableSlots(String testId, LocalDateTime time) {
		List<LocalTime> allSlots = new ArrayList<LocalTime>();
		allSlots.add(LocalTime.of(9, 00, 00));
		allSlots.add(LocalTime.of(9, 30, 00));
		allSlots.add(LocalTime.of(10, 00, 00));
		allSlots.add(LocalTime.of(10, 30, 00));
		allSlots.add(LocalTime.of(11, 00, 00));
		allSlots.add(LocalTime.of(11, 30, 00));
		allSlots.add(LocalTime.of(12, 00, 00));
		allSlots.add(LocalTime.of(12, 30, 00));
		allSlots.add(LocalTime.of(13, 00, 00));
		allSlots.add(LocalTime.of(14, 00, 00));
		allSlots.add(LocalTime.of(14, 30, 00));
		allSlots.add(LocalTime.of(15, 00, 00));
		allSlots.add(LocalTime.of(15, 30, 00));
		allSlots.add(LocalTime.of(16, 00, 00));
		allSlots.add(LocalTime.of(16, 30, 00));
		allSlots.add(LocalTime.of(17, 00, 00));
		allSlots.add(LocalTime.of(17, 30, 00));
		allSlots.add(LocalTime.of(18, 00, 00));
		allSlots.add(LocalTime.of(18, 30, 00));
		allSlots.add(LocalTime.of(19, 00, 00));
		allSlots.add(LocalTime.of(19, 30, 00));

		List<Appointment> listOfAppointments = (List<Appointment>) appointmentRepository.findAll();
		Iterator<Appointment> itr = listOfAppointments.iterator();
		while (itr.hasNext()) {
			Appointment appointment = itr.next();
			if (!appointment.getDateTime().toLocalDate().isEqual(time.toLocalDate())) {
				listOfAppointments.remove(appointment);
			} else if (!appointment.getTest().getTestId().equals(testId)) {
				listOfAppointments.remove(appointment);
			} else if (appointment.getDateTime().toLocalTime().compareTo(time.toLocalTime()) == 0) {

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
		Appointment appointmentObject=appointmentRepository.save(appointment.get());
		
		if (appointmentObject!=null) {
			return "Appointment Cancelled!!";
		} else {
			return "Problem Occured";
		}
	}

}
