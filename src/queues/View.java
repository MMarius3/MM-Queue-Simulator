package queues;

import javax.swing.*;
import java.awt.*;

public class View {

    public int numberOfQueues;
    public int numberOfClients;
    public int simulationInterval;
    public int maxArrivalTime;
    public int minArrivalTime;
    public int maxServiceTime;
    public int minServiceTime;

    public JFrame mainFrame;
    public JPanel numberOfQueuesPanel, numberOfClientsPanel, simulationIntervalPanel,
                    maxArrivalTimePanel, minArrivalTimePanel,
                    maxServiceTimePanel, minServiceTimePanel;
    public JTextField numberOfQueuesText, numberOfClientsText, simulationIntervalText,
                    maxArrivalTimeText, minArrivalTimeText,
                    maxServiceTimeText, minServiceTimeText;
    public JLabel averageWaitingTimeLabel, averageServiceTimeLabel, peakHourLabel;

    public View() { GUI(); }

    private void GUI() {
        mainFrame = new JFrame("Queue Simulation");
        mainFrame.setSize(444, 500);
        mainFrame.setLayout(new GridLayout(1, 1));
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainFrame.add(mainPanel);

        numberOfQueuesPanel = new JPanel();
        numberOfQueuesPanel.setLayout(new FlowLayout());
        numberOfQueuesText = new JTextField(10);
        numberOfQueuesPanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.darkGray));
        numberOfQueuesPanel.setBackground(Color.darkGray);
        numberOfQueuesPanel.add(new JLabel("<html><font color='white'>Number of queues : </font></html>", JLabel.LEFT));
        numberOfQueuesPanel.add(numberOfQueuesText);
        mainPanel.add(numberOfQueuesPanel);

        numberOfClientsPanel = new JPanel();
        numberOfClientsPanel.setLayout(new FlowLayout());
        numberOfClientsText = new JTextField(10);
        numberOfClientsPanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.darkGray));
        numberOfClientsPanel.setBackground(Color.darkGray);
        numberOfClientsPanel.add(new JLabel("<html><font color='white'>Number of clients : </font></html>", JLabel.LEFT));
        numberOfClientsPanel.add(numberOfClientsText);
        mainPanel.add(numberOfClientsPanel);

        simulationIntervalPanel = new JPanel();
        simulationIntervalPanel.setLayout(new FlowLayout());
        simulationIntervalText = new JTextField(10);
        simulationIntervalPanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.darkGray));
        simulationIntervalPanel.setBackground(Color.darkGray);
        simulationIntervalPanel.add(new JLabel("<html><font color='white'>Maximum simulation time : </font></html>", JLabel.LEFT));
        simulationIntervalPanel.add(simulationIntervalText);
        mainPanel.add(simulationIntervalPanel);

        maxArrivalTimePanel = new JPanel();
        maxArrivalTimePanel.setLayout(new FlowLayout());
        maxArrivalTimeText = new JTextField(10);
        maxArrivalTimePanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.darkGray));
        maxArrivalTimePanel.setBackground(Color.darkGray);
        maxArrivalTimePanel.add(new JLabel("<html><font color='white'>Maximum arrival time : </font></html>", JLabel.LEFT));
        maxArrivalTimePanel.add(maxArrivalTimeText);
        mainPanel.add(maxArrivalTimePanel);

        minArrivalTimePanel = new JPanel();
        minArrivalTimePanel.setLayout(new FlowLayout());
        minArrivalTimeText = new JTextField(10);
        minArrivalTimePanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.darkGray));
        minArrivalTimePanel.setBackground(Color.darkGray);
        minArrivalTimePanel.add(new JLabel("<html><font color='white'>Minimum arrival time : </font></html>", JLabel.LEFT));
        minArrivalTimePanel.add(minArrivalTimeText);
        mainPanel.add(minArrivalTimePanel);

        maxServiceTimePanel = new JPanel();
        maxServiceTimePanel.setLayout(new FlowLayout());
        maxServiceTimeText = new JTextField(10);
        maxServiceTimePanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.darkGray));
        maxServiceTimePanel.setBackground(Color.darkGray);
        maxServiceTimePanel.add(new JLabel("<html><font color='white'>Maximum service time : </font></html>", JLabel.LEFT));
        maxServiceTimePanel.add(maxServiceTimeText);
        mainPanel.add(maxServiceTimePanel);

        minServiceTimePanel = new JPanel();
        minServiceTimePanel.setLayout(new FlowLayout());
        minServiceTimeText = new JTextField(10);
        minServiceTimePanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.darkGray));
        minServiceTimePanel.setBackground(Color.darkGray);
        minServiceTimePanel.add(new JLabel("<html><font color='white'>Minimum service time : </font></html>", JLabel.LEFT));
        minServiceTimePanel.add(minServiceTimeText);
        mainPanel.add(minServiceTimePanel);

        averageWaitingTimeLabel = new JLabel("",JLabel.LEFT);
        averageWaitingTimeLabel.setVerticalTextPosition(JLabel.TOP);
        averageWaitingTimeLabel.setPreferredSize(new Dimension(333,28));
        JPanel averageWaitingTimePanel = new JPanel();
        averageWaitingTimePanel.setLayout(new GridLayout(2,1));
        averageWaitingTimePanel.setBackground(Color.darkGray);
        averageWaitingTimePanel.add(averageWaitingTimeLabel);
        averageWaitingTimePanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.darkGray));
        mainPanel.add(averageWaitingTimePanel);

        averageServiceTimeLabel = new JLabel("",JLabel.LEFT);
        averageServiceTimeLabel.setVerticalTextPosition(JLabel.TOP);
        averageServiceTimeLabel.setPreferredSize(new Dimension(333,28));
        JPanel averageServiceTimePanel = new JPanel();
        averageServiceTimePanel.setLayout(new GridLayout(2,1));
        averageServiceTimePanel.setBackground(Color.darkGray);
        averageServiceTimePanel.add(averageServiceTimeLabel);
        averageServiceTimePanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.darkGray));
        mainPanel.add(averageServiceTimePanel);

        peakHourLabel = new JLabel("",JLabel.LEFT);
        peakHourLabel.setVerticalTextPosition(JLabel.TOP);
        peakHourLabel.setPreferredSize(new Dimension(333,28));
        JPanel peakHourPanel = new JPanel();
        peakHourPanel.setLayout(new GridLayout(2,1));
        peakHourPanel.setBackground(Color.darkGray);
        peakHourPanel.add(peakHourLabel);
        peakHourPanel.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.darkGray));
        mainPanel.add(peakHourPanel);

        mainPanel.setVisible(true);
    }

    public void queueSimulationPanel() {
        JButton startSimulationButton = new JButton("<html><p style='color:black;font-family:Helvetica;font-weight:300;line-height:100px;'><b>START</b></p></html>");
        startSimulationButton.setActionCommand("START");
        startSimulationButton.addActionListener(new SimulationManager(this));
        numberOfQueuesPanel.add(startSimulationButton);
        mainFrame.setVisible(true);
    }
}
