package com.example.Employee.Management.System.controller


import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes

import java.security.Principal

@Controller
class EmployeeController {

    @Autowired
    PasswordEncoder passwordEncoder

    @Autowired
    private com.example.Employee.Management.System.repository.EmployeeRepository employeeRepository

    @GetMapping(["/showEmployees", "/elist"])
    ModelAndView getAllEmployees() {
        ModelAndView modal_view = new ModelAndView("views/list_employees2")
        List<com.example.Employee.Management.System.model.Employee> elist = employeeRepository.findAll()
        modal_view.addObject("employees", elist)
        modal_view
    }

    @GetMapping("/addEmployeeForm")
    ModelAndView addEmployeeForm() {
        ModelAndView modal_view = new ModelAndView("views/add_employee_form")
        com.example.Employee.Management.System.model.Employee newEmployee = new com.example.Employee.Management.System.model.Employee()
        modal_view.addObject("employee", newEmployee)
        modal_view
    }

    @PostMapping("/saveEmployee")
    String saveEmployee(@ModelAttribute com.example.Employee.Management.System.model.Employee employee, HttpSession session) {
        employee.setPassword(passwordEncoder.encode(employee.getUsername()))
        employee.isEnabled = true
        employeeRepository.save(employee)
        session.setAttribute("msg", "Emplyee Added Sucessfully..")
        "redirect:/elist"
    }

    @GetMapping("/updateEmployeeForm")
    ModelAndView updateEmployeeForm(@RequestParam Long employeeId) {
        ModelAndView modelAndView = new ModelAndView("views/updateEmployeeForm")
        com.example.Employee.Management.System.model.Employee employe = employeeRepository.findById(employeeId).get()
        modelAndView.addObject("employee", employe)
        modelAndView
    }

    @GetMapping("/disableEmployee")
    String disableEmployee(@RequestParam Long employeeId) {
        com.example.Employee.Management.System.model.Employee employee = employeeRepository.findById(employeeId).get()
        employee.isEnabled = !employee.isEnabled
        employeeRepository.save(employee)
        "redirect:/elist"
    }

    @GetMapping("/deleteEmployee")
    String deleteEmployee(@RequestParam Long employeeId,HttpSession session) {
        employeeRepository.deleteById(employeeId)
        session.setAttribute("msg", "Emp Data Delete Sucessfully..")
        "redirect:/elist"
    }


    @PostMapping("/saveUpdate")
    String saveUpdatedEmployee(@ModelAttribute com.example.Employee.Management.System.model.Employee employee) {
        employeeRepository.save(employee)
        "redirect:/elist"
    }

    @GetMapping("/viewEmployee")
    ModelAndView viewEmployee(@RequestParam Long employeeId) {
        ModelAndView modelAndView = new ModelAndView("views/viewEmployee")
        com.example.Employee.Management.System.model.Employee employee = employeeRepository.findById(employeeId).get()
        modelAndView.addObject("employee", employee)
        modelAndView
    }

    @GetMapping("/viewProfile")
    ModelAndView viewEmployeeProfile(@RequestParam Long employeeId) {
        ModelAndView view = new ModelAndView("views/viewProfile")
        com.example.Employee.Management.System.model.Employee employee = employeeRepository.findById(employeeId).get()
        view.addObject("employee", employee)
        view
    }

    @GetMapping("/changePassword")
    ModelAndView changePassword(@RequestParam Long employeeId) {
        ModelAndView view = new ModelAndView("views/changePassword")
        com.example.Employee.Management.System.model.Employee em = employeeRepository.findById(employeeId).get()
        view.addObject("employee", em)
        view
    }

    @PostMapping("/profile/update-password")
    String updatePassword(@RequestParam("currentPassword") String currentPassword,
                          @RequestParam("newPassword") String newPassword,
                          @RequestParam("confirmPassword") String confirmPassword,
                          Principal principal,
                          RedirectAttributes redirectAttributes) {
        com.example.Employee.Management.System.model.Employee employee = employeeRepository.getUserByUsername(principal.getName())
        Long employeeId = employee.id
        if (!passwordEncoder.matches(currentPassword, employee.password)) {
            redirectAttributes.addFlashAttribute("error", "Incorrect current password")
            "redirect:/changePassword?employeeId=" + employeeId
        }
        if (newPassword != confirmPassword) {
            redirectAttributes.addFlashAttribute("error", "New password and confirm password must match")
            "redirect:/changePassword?employeeId=" + employeeId
        }
        employee.password = passwordEncoder.encode(newPassword)
        employeeRepository.save(employee)
        redirectAttributes.addFlashAttribute("success", "Password changed successfully")
        "redirect:/viewEmployee?employeeId=" + employeeId
    }
}
