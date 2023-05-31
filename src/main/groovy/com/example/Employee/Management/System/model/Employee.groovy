package com.example.Employee.Management.System.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor


@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String username
    String department
    String email
    String role
    String position
    String password
    boolean isEnabled

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Attendance> attendanceRecords = new ArrayList<>()
}

