package com.example.Employee.Management.System.security

import com.example.Employee.Management.System.repository.EmployeeRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    @Autowired
    EmployeeRepository repo
    final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy()

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("employee")) {
            Long employeeId = getStudentIdFromDatabase(authentication.getName()); // Replace with your logic to get the student ID
            redirectStrategy.sendRedirect(request, response, "/employee/dashboard?id=" + employeeId)
        }
        else if (roles.contains("admin")) {
            redirectStrategy.sendRedirect(request, response, "/admin/dashboard")

        }
        else {
            // Handle other roles or unauthorized access
            redirectStrategy.sendRedirect(request, response, "/login")
        }
    }

     Long getStudentIdFromDatabase(String username) {
        return repo.getUserByUsername(username).getId()
    }

}
