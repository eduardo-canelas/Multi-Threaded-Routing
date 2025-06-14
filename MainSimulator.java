/*
 Name: Eduardo Canelas
 Course: CNT 4714 Summer 2025
 Assignment title: Project 1 – Multi-threaded programming in Java
 Date: June 15, 2025

 Class: MainSimulator
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class MainSimulator {
    private static final int MAX_STATIONS = 10;
    private static final int NUM_CONVEYORS = MAX_STATIONS;

    public static void main(String[] args) {
        String configFile = "config.txt";
        List<RoutingStation> stations = new ArrayList<>();
        Conveyor[] conveyors = new Conveyor[NUM_CONVEYORS];

        for (int i = 0; i < NUM_CONVEYORS; i++) {
            conveyors[i] = new Conveyor(i);
        }

        try (Scanner sc = new Scanner(new File(configFile))) {
            int numStations = sc.nextInt();
            for (int i = 0; i < numStations; i++) {
                int workload = sc.nextInt();
                Conveyor input = conveyors[i];
                Conveyor output = conveyors[(i + 1) % NUM_CONVEYORS];
                stations.add(new RoutingStation(i, input, output, workload));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found!");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(MAX_STATIONS);
        for (RoutingStation station : stations) {
            executor.execute(station);
        }

        executor.shutdown();
    }
}
