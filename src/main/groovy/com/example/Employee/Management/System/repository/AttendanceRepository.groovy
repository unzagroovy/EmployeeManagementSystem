package com.example.Employee.Management.System.repository


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.time.LocalDate

@Repository
interface AttendanceRepository extends JpaRepository<com.example.Employee.Management.System.model.Attendance, Long> {

    com.example.Employee.Management.System.model.Attendance findByDateAndEmployee(LocalDate date, com.example.Employee.Management.System.model.Employee employee)

}