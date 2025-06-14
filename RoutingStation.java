/*
 Name: Eduardo Canelas
 Course: CNT 4714 Summer 2025
 Assignment title: Project 1 – Multi-threaded programming in Java
 Date: June 15, 2025

 Class: RoutingStation
*/

import java.util.Random;

public class RoutingStation implements Runnable {
    private final int stationId;
    private final Conveyor input;
    private final Conveyor output;
    private int workload;
    private final Random rand = new Random();

    public RoutingStation(int stationId, Conveyor input, Conveyor output, int workload) {
        this.stationId = stationId;
        this.input = input;
        this.output = output;
        this.workload = workload;
        System.out.println("Routing Station S" + stationId + " Has Total Workload of " + workload + " Package Groups.");
    }

    @Override
    public void run() {
        System.out.println(
                "Routing Station S" + stationId + ": Input conveyor assigned to conveyor number C" + input.getId());
        System.out.println(
                "Routing Station S" + stationId + ": Output conveyor assigned to conveyor number C" + output.getId());

        while (workload > 0) {
            boolean acquired = false;

            Conveyor first = input.getId() < output.getId() ? input : output;
            Conveyor second = input.getId() < output.getId() ? output : input;

            if (first.getLock().tryLock()) {
                System.out.println(
                        "Routing Station S" + stationId + ": Currently holds lock on input conveyor C" + first.getId());
                if (second.getLock().tryLock()) {
                    System.out.println("Routing Station S" + stationId + ": Currently holds lock on output conveyor C"
                            + second.getId());

                    // Work simulation
                    System.out.println(
                            "* * Routing Station S" + stationId + ": * * CURRENTLY HARD AT WORK MOVING PACKAGES. * *");
                    try {
                        Thread.sleep(rand.nextInt(1000) + 500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    workload--;
                    System.out.println("Routing Station S" + stationId + ": Package group completed - " + workload
                            + " package groups remaining to move.");

                    second.getLock().unlock();
                    System.out.println(
                            "Routing Station S" + stationId + ": Unlocks/releases output conveyor C" + second.getId());

                    first.getLock().unlock();
                    System.out.println(
                            "Routing Station S" + stationId + ": Unlocks/releases input conveyor C" + first.getId());

                    try {
                        Thread.sleep(rand.nextInt(1000) + 500); // idle simulation
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    acquired = true;
                } else {
                    System.out.println(
                            "Routing Station S" + stationId + ": UNABLE TO LOCK OUTPUT CONVEYOR C" + second.getId() +
                                    ". SYNCHRONIZATION ISSUE: Station holding lock on C" + second.getId() +
                                    " – Station S" + stationId + " releasing lock on input conveyor C" + first.getId());
                    first.getLock().unlock();
                }
            }

            if (!acquired) {
                try {
                    Thread.sleep(rand.nextInt(300) + 200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        System.out.println("## Routing Station S" + stationId + ": going offline – work completed!   BYE! ##");
    }
}
