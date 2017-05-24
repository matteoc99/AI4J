package neural_network_lib.network_gui;

import neural_network_lib.Connection;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Maximilian Estfeller
 * @since 21.05.17
 */
class NetworkPanelConnection extends JComponent implements NetworkGUIComponent{

    static final int DOWNWARDS = 1;
    static final int UPWARDS = 2;
    static final int HORIZONTAL = 3;
    static final int VERTICAL = 4;

    private boolean developerMode = false;

    private int lineDrawOrientation;

    private double weight = 1;

    private NetworkPanelNeuron from;
    private NetworkPanelNeuron to;

    private Object equivalent;

    public NetworkPanelConnection(Connection equivalent, NetworkPanelNeuron from, NetworkPanelNeuron to) {
        this.setEquivalent(equivalent);
        this.from = from;
        this.to = to;

        from.registerAsAxon(this);
        to.registerAsDendrite(this);
    }

    double getWeight() {
        return weight;
    }

    void setWeight(double weight) {
        this.weight = weight;
    }

    NetworkPanelNeuron getFrom() {
        return from;
    }

    void setFrom(NetworkPanelNeuron from) {
        this.from = from;
    }

    NetworkPanelNeuron getTo() {
        return to;
    }

    void setTo(NetworkPanelNeuron to) {
        this.to = to;
    }

    void setLineDrawOrientation(int orientation) {
        this.lineDrawOrientation = orientation;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (equivalent == null || !(equivalent instanceof Connection) ||
                !((Connection) equivalent).isActive() || !evaluateFocus())
            return;
        Graphics2D gAlia = (Graphics2D) g;
        gAlia.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getGradientColor(weight));
        gAlia.setStroke(new BasicStroke(3));
        if (lineDrawOrientation == DOWNWARDS)
            gAlia.drawLine(0,0,getWidth(),getHeight());
        else if (lineDrawOrientation == UPWARDS)
            gAlia.drawLine(0, getHeight(), getWidth(), 0);
        else if (lineDrawOrientation == HORIZONTAL)
            gAlia.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
        else if (lineDrawOrientation == VERTICAL)
            gAlia.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
    }

    private boolean evaluateFocus() {
        NetworkPanelNeuron.FocusState fromState = from.getFocusState();
        NetworkPanelNeuron.FocusState toState = to.getFocusState();
        return fromState == NetworkPanelNeuron.FocusState.ALL || fromState == NetworkPanelNeuron.FocusState.AXONS ||
                toState == NetworkPanelNeuron.FocusState.ALL || toState == NetworkPanelNeuron.FocusState.DENDRITES;
    }

    public Color getGradientColor(double weight)
    {
        weight=(weight+1)/2;
        if (weight<0.4 && weight>0.6)
            return Color.LIGHT_GRAY;

        double H = weight * 0.4; // 0.0 = red, 0.4 = green
        double S = 0.9; // Saturation
        double B = 0.9; // Brightness

        return Color.getHSBColor((float)H, (float)S, (float)B);
    }

    @Override
    public Object getEquivalent() {
        return this.equivalent;
    }

    @Override
    public void setEquivalent(Object equivalent) {
        this.equivalent = equivalent;
    }

    @Override
    public NetworkGUIComponentType getNetworkGUIComponentType() {
        return NetworkGUIComponentType.CONNECTION;
    }

    public void toggleDeveloperMode() {
        developerMode=!developerMode;
        if (developerMode)
            this.setBorder(new LineBorder(Color.BLUE, 2));
        else this.setBorder(null);
    }
}