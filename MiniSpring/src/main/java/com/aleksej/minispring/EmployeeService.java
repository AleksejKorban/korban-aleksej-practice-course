package com.aleksej.minispring;

import com.aleksej.minispring.Autowired;
import com.aleksej.minispring.Component;


@Component
public class EmployeeService {

    @Autowired
    private InnoviceIdGenerator idGenerator;

    public String onboardNewEmployee(String name) {
        String newId = idGenerator.generateNewId();
        System.out.println("---");
        System.out.println("Employee Onboarding: " + name);
        System.out.println("Generated Innovice ID: " + newId);
        System.out.println("---");
        return newId;
    }
}
