package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    private Schedule scheduleFromDTO(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        return schedule;
    }

    private ScheduleDTO scheduleIntoDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (schedule.getEmployees() != null) {
            List<Long> employeeIds = new ArrayList<>();
            schedule.getEmployees().forEach(employee -> {
                employeeIds.add(employee.getId());
            });
            scheduleDTO.setEmployeeIds(employeeIds);
        }

        if (schedule.getPets() != null) {
            List<Long> petIds = new ArrayList<>();
            schedule.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
            scheduleDTO.setPetIds(petIds);
        }

        return scheduleDTO;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule newSchedule = scheduleFromDTO(scheduleDTO);

        for (int i = 0; i < scheduleDTO.getEmployeeIds().size(); i++) {
            Long employeeId = scheduleDTO.getEmployeeIds().get(i);
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
            newSchedule.addEmployee(employee);
        }

        for (int i = 0; i < scheduleDTO.getPetIds().size(); i++) {
            Long petId = scheduleDTO.getPetIds().get(i);
            Pet pet = petRepository.findById(petId).orElseThrow(EmployeeNotFoundException::new);
            newSchedule.addPet(pet);
        }

        newSchedule = scheduleRepository.save(newSchedule);

        return scheduleIntoDTO(newSchedule);
    }

    private List<ScheduleDTO> scheduleListIntoDTO(List<Schedule> scheduleList) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleList.forEach(schedule -> {
            scheduleDTOList.add(scheduleIntoDTO(schedule));
        });
        return scheduleDTOList;
    }

    public List<ScheduleDTO> findAllSchedule() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        return scheduleListIntoDTO(scheduleList);
    }

    public List<ScheduleDTO> findAllScheduleByPet(Long petId) {
        List<Schedule> scheduleList = scheduleRepository.findByPet(petId);
        return scheduleListIntoDTO(scheduleList);
    }

    public List<ScheduleDTO> findAllScheduleByEmployee(Long employeeId) {
        List<Schedule> scheduleList = scheduleRepository.findByEmployee(employeeId);
        return scheduleListIntoDTO(scheduleList);
    }

    public List<ScheduleDTO> findAllScheduleByOwner(Long customerId) {
        List<Schedule> scheduleList = scheduleRepository.findByCustomer(customerId);
        return scheduleListIntoDTO(scheduleList);
    }
}
