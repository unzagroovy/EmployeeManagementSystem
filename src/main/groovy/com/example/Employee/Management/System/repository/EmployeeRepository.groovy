package com.example.Employee.Management.System.repository

import com.example.Employee.Management.System.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee getUserByUsername(String username)

}