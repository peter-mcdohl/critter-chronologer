package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e join e.skills s where :dayOfWeek member of e.daysAvailable and s in (:skills)")
    List<Employee> findByDaysAvailableAndSkills(DayOfWeek dayOfWeek, Set<EmployeeSkill> skills);
}
