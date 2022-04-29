package queues;

import java.util.concurrent.ArrayBlockingQueue;

public class Queue implements Runnable{

    private ArrayBlockingQueue<Client> clients;
    private int waitingTime = 0;
    private int simulationTime;
    private boolean OK;

    public Queue(ArrayBlockingQueue<Client> clients, int waitingTime) {
        this.clients = clients;
        this.waitingTime = waitingTime;
        Thread t = new Thread(this);
        t.start();
    }

    public ArrayBlockingQueue<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayBlockingQueue<Client> clients) {
        this.clients = clients;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(int simulationTime) {
        this.simulationTime = simulationTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean getOK() {return OK;}

    public void setOK(boolean OK) {this.OK = OK;}

    @Override
    public void run() {
        while (OK) {
            System.out.println(waitingTime);
            if (waitingTime > 0) {
                waitingTime--;
            }
            Client client = this.clients.peek();
            if (client != null) {
                System.out.println(client.getReadyTime() + " " + simulationTime);
                if (client.getReadyTime() == simulationTime) {
                    this.clients.remove(client);
                }
            }
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                System.out.println("Thread got interrupted!");
            }
        }
    }
}
