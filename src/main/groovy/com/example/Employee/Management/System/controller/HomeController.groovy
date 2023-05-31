package com.example.Employee.Management.System.controller


import com.example.Employee.Management.System.model.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
class HomeController {
    @Autowired
    com.example.Employee.Management.System.repository.EmployeeRepository repo

    @GetMapping("/login")
    def login() {
        new ModelAndView("views/login")
    }

    @GetMapping("/employee/dashboard")
    ModelAndView studentDashboard(@RequestParam("id") String employeeId) {
        ModelAndView view = new ModelAndView("views/viewEmployee")
        Employee employee = repo.getReferenceById(Long.valueOf(employeeId))
        view.addObject("employee", employee)
        view
    }

    @GetMapping("/admin/dashboard")
     def showAdminDashboard() {
        new ModelAndView("views/index")
    }

    // Other controller methods
}

