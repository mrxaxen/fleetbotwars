package fleetbot_wars.model;

import fleetbot_wars.model.enums.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

class StatusBar extends JPanel implements Talkative {

    Translation serverComm = Translation.getInstance();
    private static HashMap<ResourceType, ResourceLabel> resourceLabels = new HashMap<>();

    StatusBar() {
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        this.setLayout(layout);
        this.setBackground(new Color(67, 41, 24));
        genResources();
        init();
        serverComm.passStatusBar(this);
        synchronized (Engine.getInstance()) {
            Engine.getInstance().notifyAll();
        }
    }

    private void genResources() {
        resourceLabels.put(ResourceType.wood, new ResourceLabel(ResourceType.wood, serverComm.getResource(ResourceType.wood)));
        resourceLabels.put(ResourceType.stone, new ResourceLabel(ResourceType.stone,serverComm.getResource(ResourceType.stone)));
        resourceLabels.put(ResourceType.gold, new ResourceLabel(ResourceType.gold,serverComm.getResource(ResourceType.gold)));
        resourceLabels.put(ResourceType.food, new ResourceLabel(ResourceType.food,serverComm.getResource(ResourceType.food)));
        resourceLabels.put(ResourceType.upgrade, new ResourceLabel(ResourceType.upgrade,serverComm.getResource(ResourceType.upgrade)));
    }

    void updateResource(ResourceType resourceType, Integer value) {

        ResourceLabel rl = resourceLabels.get(resourceType);
        rl.updateAmount(value);
        rl.reSetText();
        rl.repaint();
        this.revalidate();
    }

    private void init() {
        StatusBar sb = this;
        resourceLabels.forEach((key,value)-> {
            sb.add(value);
        });
    }

    class ResourceLabel extends JLabel {

        ResourceType type;
        int amount;
        ImageIcon icon;

        ResourceLabel(ResourceType type, int amount) {
            borderIcon();
            this.amount = amount;
            this.type = type;
            this.setText(type.getName() + ": " + amount); //May need updating
            this.setFont(new Font("SansSerif", Font.BOLD, 12));
            this.setForeground(new Color(255,255,255));
            this.setBorder(new MatteBorder(icon));

            try {
                this.setIcon(ActionBar.resizeIcon(ImageIO.read(ResourceLabel.class.getResource(type.getURL()))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void borderIcon() {
            try {
                Image image = ImageIO.read(StatusBar.class.getResource("/resources/tileicon.png"));
                icon = new ImageIcon(image.getScaledInstance(5,5,BufferedImage.SCALE_SMOOTH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void reSetText() {
            this.setText(type.getName() + ": " + amount);
        }

        void updateAmount(int newVal) {
            this.amount = newVal;
        }

    }
}
