package com.aleksej;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Robots {
    static Random random = new Random();
    static List<String> storage = Collections.synchronizedList(new ArrayList<>());
    static String[] parts = {"head","torso","hand","foot"};

    static class Factory extends Thread {
        public void run() {
            for (int day = 0; day < 100; day++) {
                int produced = random.nextInt(10) + 1;
                synchronized (storage) {
                    for (int i = 0; i < produced; i++) {
                        storage.add(parts[random.nextInt(parts.length)]);
                    }
                }
                try { Thread.sleep(5); } catch (InterruptedException ignored) {}
            }
        }
    }
    static class Faction extends Thread {
        String name;
        List<String> bag = new ArrayList<>();
        Faction(String n) { name = n; }

        public void run() {
            for (int day = 0; day < 100; day++) {
                synchronized (storage) {
                    for (int i = 0; i < 5 && !storage.isEmpty(); i++) {
                        bag.add(storage.remove(0));
                    }
                }
                try { Thread.sleep(5); } catch (InterruptedException ignored) {}
            }
        }

        int robots() {
            int h = Collections.frequency(bag,"head");
            int t = Collections.frequency(bag,"torso");
            int ha = Collections.frequency(bag,"hand");
            int f = Collections.frequency(bag,"foot");
            return Math.min(Math.min(h,t), Math.min(ha/2,f/2));
        }
    }
    public static void main(String[] args) throws Exception {
        Factory factory = new Factory();
        Faction world = new Faction("World");
        Faction wed = new Faction("Wednesday");

        factory.start();
        world.start();
        wed.start();
        factory.join();
        world.join();
        wed.join();

        int robotsbyworld = world.robots(), robotsbywednesday = wed.robots();
        System.out.println("World: " + robotsbyworld);
        System.out.println("Wednesday: " + robotsbywednesday);
        System.out.println(robotsbyworld > robotsbywednesday ? "Winner---World" :
                robotsbywednesday > robotsbyworld ? "Winner---Wednesday" : "Draw");
    }
}