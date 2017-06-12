package estfeller;

import network.Network;
import network_gui.NetworkGUI;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Maximilian Estfelller
 * @since 09.06.2017
 */
public class Trainer {

    //
    static final int topCount = 50;
    static final int trainCount = 2000;
    static final int dataSetCount = 200;

    private static final Environment environment = new Environment();

    private static final ArrayList<Learner> agents = new ArrayList<>();

    private static Network bestNetwork;

    public static void main(String[] args) {
        NetworkGUI gui = new NetworkGUI();
        environment.createData();
        createFirstWave();

        new Thread(() -> {
            while (true) {
                gui.addNetwork(bestNetwork);
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true) {
            processAgents();
            filterAgents();
            copyAgents();
        }
    }

    private static void createFirstWave() {
        for (int i = 0; i < topCount; i++) {
            agents.add(new Learner(new Network(
                    environment.environmentLength, // inputNeuronCount
                    environment.environmentLength, // outputNeuronCount
                    1, // hiddenNeuronCount
                    new int[]{environment.environmentLength})
            ));
        }
    }

    private static void processAgents() {
        for (Learner agent : agents)
            agent.setFitness(0);
        for (int i = 0; i < dataSetCount; i++) {
            environment.createData();
            double[] in = environment.getData();
            for (Learner agent : agents) {
                double[] out = agent.processData(in);
                agent.increaseFitness(environment.evaluateActions(in, out));
            }
        }
    }

    private static void filterAgents() {
        agents.sort((o1, o2) -> (int)(o2.getFitness()-o1.getFitness()));
        while (agents.size() > topCount) {
            agents.remove(topCount);
        }
        bestNetwork = new Network(agents.get(0).getNet().getDescriptor());
        System.out.println(agents.get(0).getFitness());
    }

    private static void copyAgents() {
        for (int i = 0; i < topCount; i++) {
            Agent agent = agents.get(i);
            agent.getNet().generateDescriptor();
            for (int j = 0; j < trainCount/topCount; j++) {
                double[] desc = agent.getNet().getDescriptor().clone();
                Utils.mutateDescriptor(desc);
                agents.add(new Learner(new Network(desc)));
            }
        }
    }
}
