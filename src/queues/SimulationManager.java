package queues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class SimulationManager implements Runnable, ActionListener {

    View panel;

    private List<Client> generatedClients = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();
    private List<Queue> queues = new ArrayList<>();
    private List<Client> clientsToRemove = new ArrayList<>();

    public SimulationManager(View panel) {
        this.panel = panel;
        generateNRandomClients();
        Collections.sort(generatedClients);
        for (int i = 0; i < panel.numberOfQueues; i++) {
            ArrayBlockingQueue<Client> clients = new ArrayBlockingQueue<>(panel.numberOfClients);
            int waitingTime = 0;
            Queue q = new Queue(clients, waitingTime);
            queues.add(q);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        panel.numberOfQueues = (panel.numberOfQueuesText.getText().isEmpty()) ? 0 : Integer.parseInt(panel.numberOfQueuesText.getText());
        panel.numberOfClients = (panel.numberOfClientsText.getText().isEmpty()) ? 0 : Integer.parseInt(panel.numberOfClientsText.getText());
        panel.simulationInterval = (panel.simulationIntervalText.getText().isEmpty()) ? 0 : Integer.parseInt(panel.simulationIntervalText.getText());
        panel.maxArrivalTime = (panel.maxArrivalTimeText.getText().isEmpty()) ? 0 : Integer.parseInt(panel.maxArrivalTimeText.getText());
        panel.minArrivalTime = (panel.minArrivalTimeText.getText().isEmpty()) ? 0 : Integer.parseInt(panel.minArrivalTimeText.getText());
        panel.maxServiceTime = (panel.maxServiceTimeText.getText().isEmpty()) ? 0 : Integer.parseInt(panel.maxServiceTimeText.getText());
        panel.minServiceTime = (panel.minServiceTimeText.getText().isEmpty()) ? 0 : Integer.parseInt(panel.minServiceTimeText.getText());

        if (command.equals("START")) {
            SimulationManager gen = new SimulationManager(panel);
            Thread t = new Thread(gen);
            t.start();
        }
    }

    private void generateNRandomClients() {
        for (int i = 0; i < panel.numberOfClients; i++) {
            Random rand = new Random();
            int newClientID = i + 1;
            int newClientArrivalTime = panel.minArrivalTime + rand.nextInt(panel.maxArrivalTime - panel.minArrivalTime + 1);
            int newClientServiceTime = panel.minServiceTime + rand.nextInt(panel.maxServiceTime - panel.minServiceTime + 1);
            Client c = new Client(newClientID, newClientArrivalTime, newClientServiceTime);
            generatedClients.add(c);
        }
    }

    public Queue minimumQueue(List<Queue> queues) {
        int minimumWaitingTime = 10000;
        int minimumIndex = 0;
        for (Queue queue : queues) {
            if (queue.getWaitingTime() < minimumWaitingTime) {
                minimumWaitingTime = queue.getWaitingTime();
                minimumIndex = queues.indexOf(queue);
            }
        }
        return queues.get(minimumIndex);
    }
    public void addClientToQueue(Client client, Queue queue, int currentSimulationTime) {
        ArrayBlockingQueue<Client> clients = new ArrayBlockingQueue<>(panel.numberOfClients);
        if (queue.getClients() != null) {
            clients = queue.getClients();
        }
        clients.offer(client);
        queue.setClients(clients);
        int queueWaitingTime = queue.getWaitingTime();
        queueWaitingTime += client.getServiceTime();
        client.setReadyTime(currentSimulationTime + queueWaitingTime);
        queue.setWaitingTime(queueWaitingTime);
    }

    public void removeServedClients(List<Client> clientsToRemove, List<Client> generatedClients) {
        for (Client client : clientsToRemove) {
            generatedClients.remove(client);
        }
        clientsToRemove.clear();
    }

    public void display(int time) {
        System.out.println("Time: " + time + "\n");
        System.out.println("Waiting clients: ");
        for (Client client : generatedClients) {
            System.out.println("(" + client.getID() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
        }
        System.out.println("\n");
        for (Queue queue : queues) {
            System.out.println("Queue " + queues.indexOf(queue) + ": ");
            if (queue != null) {
                if (queue.getClients() != null) {
                    if (queue.getClients().size() != 0) {
                        for (Client client : queue.getClients()) {
                            System.out.println("(" + client.getID() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
                        }
                    } else { System.out.println("closed;"); }
                } else { System.out.println("closed;"); }
            } else { System.out.println("closed;"); }
            System.out.println("\n");
        }
        System.out.println("\n\n\n");
    }

    public void displayToFile(int time, FileWriter myWriter) {
        try {
            myWriter.write("Time: " + time + "\n");
            myWriter.write("Waiting clients: ");
            for (Client client : generatedClients) {
                myWriter.write("(" + client.getID() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
            }
            myWriter.write("\n");
            for (Queue queue : queues) {
                //threads.get(queues.indexOf(queue)).run();
                myWriter.write("Queue " + queues.indexOf(queue) + ": ");
                if (queue != null) {
                    if (queue.getClients() != null) {
                        if (queue.getClients().size() != 0) {
                            for (Client client : queue.getClients()) {
                                myWriter.write("(" + client.getID() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
                            }
                        } else { myWriter.write("closed;"); }
                    } else { myWriter.write("closed;"); }
                } else { myWriter.write("closed;");}
                myWriter.write("\n");
            }
            myWriter.write("\n\n\n");
        } catch (IOException e) {
            System.out.println("An error occurred when writing in file.");
            e.printStackTrace();
        }
    }

    public void displayResults(float totalWaitingTime, float totalServiceTime, int peakHour) {
        float averageWaitingTime = totalWaitingTime / panel.numberOfClients;
        float averageServiceTime = totalServiceTime / panel.numberOfClients;
        System.out.println("Average waiting time : " + averageWaitingTime);
        System.out.println("Average service time : " + averageServiceTime);
        System.out.println("Peak hour : " + peakHour);
        panel.averageWaitingTimeLabel.setText("<html><p style='color:yellow;font-family:Helvetica;font-weight:100;line-height:15px;'><b>Average waiting time : " + averageWaitingTime + "</b></p></html>");
        panel.averageServiceTimeLabel.setText("<html><p style='color:yellow;font-family:Helvetica;font-weight:100;line-height:15px;'><b>Average service time : " + averageServiceTime + "</b></p></html>");
        panel.peakHourLabel.setText("<html><p style='color:yellow;font-family:Helvetica;font-weight:100;line-height:15px;'><b>Peak hour : " + peakHour + "</b></p></html>");
    }

    public void setQueuesSimulationTime(int time) {
        for (Queue queue : queues) {
            queue.setSimulationTime(time);
        }
    }

    public int clientsFromAllQueues(List<Queue> queues) {
        int noOfClients = 0;
        for (Queue queue : queues) {
            noOfClients += queue.getClients().size();
        }
        return noOfClients;
    }

    void stopQueues() {
        for (Queue queue : queues) {
            queue.setOK(false);
        }
    }

    @Override
    public void run() {
        int peakHour = 0, currentNumberOfClients = 0, maximumNumberOfClients = 0, currentTime = 0;
        float averageWaitingTime, averageServiceTime, totalWaitingTime = 0, totalServiceTime = 0;
        try { FileWriter myWriter = new FileWriter("C:\\Users\\Marius\\Desktop\\Faculta\\An II\\Sem 2\\PT\\Homework2\\src\\file.txt");
        while (currentTime <= panel.simulationInterval) {
            for (Client client : this.generatedClients) {
                if (client.getArrivalTime() <= currentTime) {
                    boolean clientAdded = false;
                    for (Queue queue : this.queues) {
                        if (queue == minimumQueue(this.queues) && !clientAdded) {
                            totalWaitingTime += queue.getWaitingTime(); totalServiceTime += client.getServiceTime();
                            clientsToRemove.add(client);
                            addClientToQueue(client, queue, currentTime);
                            clientAdded = true;
                        }
                    }
                }
            }
            removeServedClients(clientsToRemove, generatedClients);
            currentNumberOfClients = clientsFromAllQueues(queues); if (maximumNumberOfClients < currentNumberOfClients) { maximumNumberOfClients = currentNumberOfClients; peakHour = currentTime; }
            displayToFile(currentTime, myWriter); display(currentTime);
            currentTime++;
            try { Thread.sleep(1000); } catch(InterruptedException e) { System.out.println("Thread got interrupted!"); }
            setQueuesSimulationTime(currentTime);
        }
            averageWaitingTime = totalWaitingTime / panel.numberOfClients; averageServiceTime = totalServiceTime / panel.numberOfClients;
            myWriter.write("Average waiting time : " + averageWaitingTime + "\n"); myWriter.write("Average service time : " + averageServiceTime + "\n");
            myWriter.write("Peak hour : " + peakHour + "\n"); myWriter.close();
        } catch (IOException e) { System.out.println("An error occurred when writing in file."); e.printStackTrace(); }
        displayResults(totalWaitingTime, totalServiceTime, peakHour);
        stopQueues();
    }
}
