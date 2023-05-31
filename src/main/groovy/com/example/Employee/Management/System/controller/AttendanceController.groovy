package com.example.Employee.Management.System.controller


import com.example.Employee.Management.System.repository.AttendanceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView


@Controller
class AttendanceController {
    @Autowired
    AttendanceRepository attendanceRepo

    @Autowired
    com.example.Employee.Management.System.repository.EmployeeRepository empRepo

    @GetMapping("/create")
    ModelAndView showCreateAttendanceForm() {
        ModelAndView view = new ModelAndView("views/createAttendance")
        List<com.example.Employee.Management.System.model.Employee> employees = empRepo.findAll()
        view.addObject("employees", employees)
        view.addObject("attendance", new com.example.Employee.Management.System.model.Attendance())
        return view
    }

    @PostMapping("/save-attendance")
    String createAttendance(@ModelAttribute("attendance") com.example.Employee.Management.System.model.Attendance attendance) {
        com.example.Employee.Management.System.model.Attendance exists = attendanceRepo.findByDateAndEmployee(attendance.getDate(), attendance.getEmployee())
        if (exists != null) {
            if (exists.getStatus() == attendance.getStatus()) {
            } else {
                exists.setStatus(attendance.getStatus())
                attendanceRepo.save(exists)
            }
            return "redirect:/view-attendance"
        }

        attendanceRepo.save(attendance)
        return "redirect:/view-attendance"
    }

    @GetMapping("/view-attendance")
    ModelAndView viewAttendance() {
        List<com.example.Employee.Management.System.model.Attendance> attendances = attendanceRepo.findAll()
        ModelAndView view = new ModelAndView("views/view-attendance")
        view.addObject("attendance", attendances)
        return view
    }

    @GetMapping("/viewEmployeeAttendance")
    ModelAndView viewEmployeeAttendance(@RequestParam Long employeeId) {
        ModelAndView modelAndView = new ModelAndView("views/view-Attendance")
        com.example.Employee.Management.System.model.Employee employee = empRepo.findById(employeeId).get()
        modelAndView.addObject("attendance", employee.getAttendanceRecords())
        return modelAndView
    }

    @GetMapping("/update-attendance")
    ModelAndView updateAttendance(@RequestParam Long attendanceId) {
        ModelAndView modelAndView = new ModelAndView("views/updateAttendance")
        com.example.Employee.Management.System.model.Attendance attendance = attendanceRepo.findById(attendanceId).get()
        modelAndView.addObject("attendance", attendance)
        return modelAndView
    }

    @GetMapping("/markAttendance")
    ModelAndView markAttendanceForm(@RequestParam Long attendanceId) {
        ModelAndView modelAndView = new ModelAndView("views/markAttendanceForm")
        com.example.Employee.Management.System.model.Attendance attendance = attendanceRepo.findById(attendanceId).get()
        modelAndView.addObject("attendance", attendance)
        return modelAndView
    }

    @GetMapping("/delete-attendance")
    String deleteAttendance(@RequestParam Long attendanceId) {
        attendanceRepo.deleteById(attendanceId)
        return "redirect:/view-attendance"
    }

    @PostMapping("/saveAttendance")
    String saveAttendance(@ModelAttribute com.example.Employee.Management.System.model.Attendance attendanceDto) {
        attendanceRepo.save(attendanceDto)
         "redirect:/view-attendance"
    }
}

