package com.aleksej.minispring;



public class MiniSpringApplication {

    public static void main(String[] args) throws Exception {

        Class<?>[] components = {
                InnoviceIdGenerator.class,
                EmployeeService.class
        };

        MiniApplicationContext context = new MiniApplicationContext(components);
        EmployeeService employeeService = context.getBean(EmployeeService.class);
        employeeService.onboardNewEmployee("Alexey Korban");
        employeeService.onboardNewEmployee("Ivan IDEA");

        InnoviceIdGenerator generator = context.getBean(InnoviceIdGenerator.class);
        System.out.println("ID Check (Singleton): Next raw ID is: " + generator.generateNewId());
    }

}
