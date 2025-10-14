package com.aleksej.minispring;
import com.aleksej.minispring.Component;

@Component
public class InnoviceIdGenerator implements InitializingBean {
    private int counter = 1000;
    private final String prefix = "INV-";

    public String generateNewId() {
        return prefix + (++counter);
    }


    @Override
    public void afterPropertiesSet() {
        System.out.println("  InnoviceIdGenerator готов к работе. Инициализация счетчика ID завершена.");
    }
}
