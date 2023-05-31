package com.example.Employee.Management.System.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

import java.time.LocalDate

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendance")
 class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
    Employee employee

    String status
    LocalDate date;
}

