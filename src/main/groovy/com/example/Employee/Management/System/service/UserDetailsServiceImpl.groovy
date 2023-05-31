package com.example.Employee.Management.System.service


import com.example.Employee.Management.System.model.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException


class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private com.example.Employee.Management.System.repository.EmployeeRepository userRepository

    @Override
    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Employee employee = userRepository.getUserByUsername(userName)
        if(employee == null) {
            throw new UsernameNotFoundException("Could not find User");
        }
        new MyUserDetails(employee);
    }

}
