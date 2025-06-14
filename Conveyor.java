/*
 Name: Eduardo Canelas
 Course: CNT 4714 Summer 2025
 Assignment title: Project 1 – Multi-threaded programming in Java
 Date: June 15, 2025

 Class: Conveyor
*/

import java.util.concurrent.locks.ReentrantLock;

public class Conveyor {
    private final int id;
    private final ReentrantLock lock;

    public Conveyor(int id) {
        this.id = id;
        this.lock = new ReentrantLock();
    }

    public int getId() {
        return id;
    }

    public ReentrantLock getLock() {
        return lock;
    }
}
