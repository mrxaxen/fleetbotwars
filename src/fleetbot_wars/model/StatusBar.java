package fleetbot_wars.model;

import fleetbot_wars.model.enums.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.Map;

class StatusBar extends JPanel implements Talkative {

    Translation serverComm = Translation.getInstance();

    StatusBar(/*HashMap<ResourceType, Integer> resources*/) {
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        this.setLayout(layout);
        this.setBackground(new Color(67, 41, 24));
        init(genResources());
        serverComm.passStatusBar(this);
    }
    //DUMMY
    private HashMap<ResourceType, Integer> genResources() {
        HashMap<ResourceType, Integer> resources = new HashMap<>();
        resources.put(ResourceType.wood, serverComm.getResource(ResourceType.wood));
        resources.put(ResourceType.stone, serverComm.getResource(ResourceType.stone));
        resources.put(ResourceType.gold, serverComm.getResource(ResourceType.gold));
        resources.put(ResourceType.food, serverComm.getResource(ResourceType.food));

        return resources;
    }

    void updateResources(HashMap<ResourceType, Integer> newRes) {
        newRes.forEach((key,value) -> {
            Component[] labels = this.getComponents();
            for (Component component : labels) {
                ResourceLabel label = (ResourceLabel) component;
                if (label.type == key) {
                    label.updateAmount(value);
                }
            }
        });
    }

    //TODO: lay out the resource components (4?)
    private void init(HashMap<ResourceType,Integer> resources) {
        Set<Map.Entry<ResourceType, Integer>> resourceSet = resources.entrySet();
        Iterator it = resourceSet.iterator();
        while (it.hasNext()) {
            Map.Entry<ResourceType, Integer> entry = (Map.Entry<ResourceType, Integer>) it.next();
            this.add(new ResourceLabel(entry.getKey(), entry.getValue()));
        }
    }

    //TODO: Icon?
    private class ResourceLabel extends JLabel {

        ResourceType type;
        int amount;
        ImageIcon icon;

        private ResourceLabel(ResourceType type, int amount) {
            borderIcon();
            this.amount = amount;
            this.type = type;
            this.setText(type.getName() + ": " + amount); //May need updating
            this.setFont(new Font("SansSerif", Font.BOLD, 12));
            this.setForeground(new Color(255,255,255));
            this.setBorder(new MatteBorder(icon));
        }

        private void borderIcon() {
            try {
                Image image = ImageIO.read(StatusBar.class.getResource("/resources/tileicon.png"));
                icon = new ImageIcon(image.getScaledInstance(5,5,BufferedImage.SCALE_SMOOTH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void updateAmount(int newVal) {
            this.amount = newVal;
        }

    }
}
