package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s join s.pets p where p.id = :petId")
    List<Schedule> findByPet(Long petId);

    @Query("select s from Schedule s join s.employees e where e.id = :employeeId")
    List<Schedule> findByEmployee(Long employeeId);

    @Query("select s from Schedule s join s.pets p join p.owner c where c.id = :customerId")
    List<Schedule> findByCustomer(Long customerId);
}
