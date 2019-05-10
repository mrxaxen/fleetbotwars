/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import fleetbot_wars.model.enums.ResourceType;
import fleetbot_wars.model.enums.VisualType;
import visual.unit.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author WB
 */
class ActionBar extends JPanel {


    class BuildButton extends JButton{
        HashMap<ResourceType, Integer> price;
        BuildButton(String name, HashMap<ResourceType, Integer> price){
            super(name);
            this.price = price;
        }
        BuildButton(String name, HashMap<ResourceType, Integer> price, Icon icon){
            super(name, icon);
            this.price = price;
            this.setVerticalTextPosition(SwingConstants.BOTTOM);
            this.setHorizontalTextPosition(SwingConstants.CENTER);
            this.setFont(new Font("Arial", Font.PLAIN, 12));
            System.out.println(ResourceType.gold.getURL());
            ActionBar.class.getResource(ResourceType.gold.getURL());
            this.setToolTipText(price.toString());
            this.setToolTipText("" +
                    "<html><body>"+
                        "<img src='" + ActionBar.class.getResource(ResourceType.wood.getURL()) +
                        "' width=36 height=36 />" +
                        "<span>" + price.get(ResourceType.wood) + "</span>" +
                        "<img src='" + ActionBar.class.getResource(ResourceType.gold.getURL()) +
                        "' width=36 height=36 />" +
                        "<span>" + price.get(ResourceType.gold) + "</span>" +
                        "<img src='" + ActionBar.class.getResource(ResourceType.stone.getURL()) +
                        "' width=36 height=36 />" +
                        "<span>" + price.get(ResourceType.stone) + "</span>" +
                        "<img src='" + ActionBar.class.getResource(ResourceType.food.getURL()) +
                        "' width=36 height=36 />" +
                        "<span>" + price.get(ResourceType.food) + "</span>" +
                        "<img src='" + ActionBar.class.getResource(ResourceType.upgrade.getURL()) +
                        "' width=36 height=36 />" +
                        "<span>" + price.get(ResourceType.upgrade) + "</span>" +
                    "</body></html>"
            );
        }
        BuildButton(String name){
            super(name);
        }
    }
    //    Builder     Soldier     Lumberjack    Miner
    private static final HashSet<UnitType> attackers = new HashSet<>();
    private static ActionBar instance = new ActionBar();
    private static HashSet<BuildButton> active;
    private HashMap<String,HashSet<BuildButton>> oneToRuleThemAll = new HashMap<>();
    private SelectionController selectionController = SelectionController.getInstance();

    static ActionBar getInstance() {
        return instance;
    }
    //Reimplements with button lists so the action bar component does not resize itself
    private ActionBar() {
        initAttackers();
        HashSet<BuildButton> builder = initBuilder();
        HashSet<BuildButton> buildMenu = initBuildMenu();
        HashSet<BuildButton> attack = initAttack();
        addBar(builder);
        addBar(buildMenu);
        addBar(attack);
        this.setBackground(GameWindow.backgroundColor);
        this.setBorder(GameWindow.uiBorder);
        oneToRuleThemAll.put("BUILDER",builder);
        oneToRuleThemAll.put("buildmenu",buildMenu);
        oneToRuleThemAll.put("attack",attack);
    }

    private void initAttackers() {
        attackers.add(UnitType.CAVALRY);
        attackers.add(UnitType.DESTROYER);
        attackers.add(UnitType.INFANTRY);
        attackers.add(UnitType.RANGER);
        attackers.add(UnitType.TURRET);
        attackers.add(UnitType.LUMBERJACK);
    }

    private void addBar(HashSet<BuildButton> bar) {
        bar.forEach(button -> {
            this.add(button);
        });
    }

    void changeToDefault() {
        changeActionBar("default");
        this.revalidate();
    }

    void changeActionBar(UnitType unitType) {
        System.out.println(unitType);
        if(unitType != null) {
            changeActionBar(unitType.name());
        }
    }

    private void changeActionBar(String name) {
        try {
            if (attackers.contains(UnitType.valueOf(name))) {
                name = "attack";
            }
        } catch (RuntimeException e) {
            System.err.println("No such UnitType as " + name);
        }
        final String s = name;
        if(active != null) {
            active.forEach((button) -> {
                button.setEnabled(false);
            });
        }
        if(oneToRuleThemAll.containsKey(s)) {
            active = oneToRuleThemAll.get(s);
            active.forEach((button)-> {
                System.out.println(button.getName());
                button.setEnabled(true);
                if(s == "buildmenu"){
                    if(Translation.getInstance().enoughResource(button.price)){
                        button.setEnabled(true);
                    }else{

                            button.setEnabled(false);

                    }

                }

            });

        }
    }

    private HashSet<BuildButton> initBuilder() {
        HashSet<BuildButton> buttons = new HashSet<>();
        BuildButton build = new BuildButton("Build");
        build.addActionListener(e -> {
            selectionController.buildMode = true;
            changeActionBar("buildmenu");
            System.out.println("BUTTON PRESSED");
        });
        buttons.add(build);
        build.setEnabled(false);
        return buttons;
    }

    private Icon resizeIcon(Image img){
        Image newimg = img.getScaledInstance( 48, 48,  Image.SCALE_SMOOTH ) ;
        return new ImageIcon( newimg );
    }

    private HashSet<BuildButton> initAttack() {
        HashSet<BuildButton> buttons = new HashSet<>();
        BuildButton button = new BuildButton("Attack");
        button.addActionListener( e -> {
            selectionController.setAttackMode(true);
        });
        buttons.add(button);
        button.setEnabled(false);
        return buttons;
    }

    private HashSet<BuildButton> initBuildMenu() {
        HashSet<BuildButton> buttons = new HashSet<>();
        Image image = null;
        try {

            image = ImageIO.read(getClass().getResource("/resources/building/workerspawn.png"));
            BuildButton workerSpawn = new BuildButton("Worker Training Center", WorkerSpawn.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/building/militaryspawn.png"));
            BuildButton militarySpawn = new BuildButton("Military Training Center", MilitarySpawn.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/building/farm.png"));
            BuildButton farm = new BuildButton("Farm", Farm.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/building/stonemine.png"));
            BuildButton stoneMine = new BuildButton("Stone Mine", StoneMine.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/building/goldmine.png"));
            BuildButton goldMine = new BuildButton("Gold Mine", GoldMine.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/building/turret.png"));
            BuildButton turret = new BuildButton("Turret", Turret.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/building/barricade.png"));
            BuildButton barricade = new BuildButton("Barricade", Barricade.price, resizeIcon(image));

            //TODO: implement actionbar menus and functions
            workerSpawn.addActionListener(e -> {
                selectionController.setBuildingToBuild(VisualType.WORKERSPAWN);
                System.out.println("Building to build: " + VisualType.WORKERSPAWN);
            });
            militarySpawn.addActionListener(e -> {
                selectionController.setBuildingToBuild(VisualType.MILITARYSPAWN);
            });
            farm.addActionListener(e -> {
                selectionController.setBuildingToBuild(VisualType.FARM);
            });
            stoneMine.addActionListener(e -> {
                selectionController.setBuildingToBuild(VisualType.STONEMINE);
            });
            goldMine.addActionListener(e -> {
                selectionController.setBuildingToBuild(VisualType.GOLDMINE);
            });
            turret.addActionListener(e -> {
                selectionController.setBuildingToBuild(VisualType.TURRET);
            });
            barricade.addActionListener(e -> {
                selectionController.setBuildingToBuild(VisualType.BARRICADE);
            });

            workerSpawn.setEnabled(false);
            militarySpawn.setEnabled(false);
            farm.setEnabled(false);
            stoneMine.setEnabled(false);
            goldMine.setEnabled(false);
            turret.setEnabled(false);
            barricade.setEnabled(false);

            buttons.add(workerSpawn);
            buttons.add(militarySpawn);
            buttons.add(farm);
            buttons.add(stoneMine);
            buttons.add(goldMine);
            buttons.add(turret);
            buttons.add(barricade);

        }catch (IOException e) {
            e.printStackTrace();
        }


        return buttons;
    }
}
