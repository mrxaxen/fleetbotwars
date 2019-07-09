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

import static javax.swing.Box.createRigidArea;

/**
 * @author WB
 */
class ActionBar extends JPanel {


    class BuildButton extends JButton {
        HashMap<ResourceType, Integer> price;

        BuildButton(String name, HashMap<ResourceType, Integer> price) {
            super(name);
            this.price = price;
        }

        BuildButton(String name, HashMap<ResourceType, Integer> price, Icon icon) {
            super(name, icon);
            this.price = price;
            this.setVerticalTextPosition(SwingConstants.BOTTOM);
            this.setHorizontalTextPosition(SwingConstants.CENTER);
            this.setFont(new Font("Arial", Font.PLAIN, 12));
            System.out.println(ResourceType.gold.getURL());
            ActionBar.class.getResource(ResourceType.gold.getURL());
            this.setToolTipText(price.toString());
            this.setToolTipText("" +
                    "<html><body>" +
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

        BuildButton(String name) {
            super(name);
        }
    }

    //    Builder     Soldier     Lumberjack    Miner
    private static final HashSet<UnitType> attackers = new HashSet<>();
    private static ActionBar instance = new ActionBar();
    private static HashSet<BuildButton> active;
    private HashMap<String, HashSet<BuildButton>> oneToRuleThemAll = new HashMap<>();
    private SelectionController selectionController = SelectionController.getInstance();

    static ActionBar getInstance() {
        return instance;
    }

    //Reimplements with button lists so the action bar component does not resize itself
    private ActionBar() {
        this.add(createRigidArea(new Dimension(50, 50)));
        initAttackers();
        HashSet<BuildButton> builder = initBuilder();
        HashSet<BuildButton> milSpawner = initMilSpawner();
        HashSet<BuildButton> resSpawner = initResSpawner();
        HashSet<BuildButton> buildMenu = initBuildMenu();
        HashSet<BuildButton> spawnResUnitMenu = initResourceUnitsMenu();
        HashSet<BuildButton> spawnMilUnitMenu = initMilitaryUnitsMenu();
        HashSet<BuildButton> attack = initAttack();
        addBar(builder);
        addBar(milSpawner);
        addBar(resSpawner);
        addBar(buildMenu);
        addBar(spawnResUnitMenu);
        addBar(spawnMilUnitMenu);
        addBar(attack);
        this.setBackground(GameWindow.backgroundColor);
        this.setBorder(GameWindow.uiBorder);
        oneToRuleThemAll.put("BUILDER", builder);
        oneToRuleThemAll.put("MILITARYSPAWN", milSpawner);
        oneToRuleThemAll.put("WORKERSPAWN", resSpawner);
        oneToRuleThemAll.put("buildmenu", buildMenu);
        oneToRuleThemAll.put("spawresunitmenu", spawnResUnitMenu);
        oneToRuleThemAll.put("spawmilunitmenu", spawnMilUnitMenu);
        oneToRuleThemAll.put("attack", attack);
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
        if (unitType != null) {
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
        if (active != null) {
            active.forEach((button) -> {
                button.setVisible(false);
            });
        }
        if (oneToRuleThemAll.containsKey(s)) {
            active = oneToRuleThemAll.get(s);
            active.forEach((button) -> {
                System.out.println(button.getName());
                button.setVisible(true);
                if (s.equals("buildmenu")) {
                    if (Translation.getInstance().enoughResource(button.price)) {
                        button.setVisible(true);
                    } else {
                        button.setVisible(false);
                    }

                } else if (s.equals("spawresunitmenu")) {
                    if (Translation.getInstance().enoughResource(button.price)) {
                        button.setVisible(true);
                    } else {
                        button.setVisible(false);
                    }
                }else if (s.equals("spawmilunitmenu")) {
                    if (Translation.getInstance().enoughResource(button.price)) {
                        button.setVisible(true);
                    } else {
                        button.setVisible(false);
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
        build.setVisible(false);
        return buttons;
    }

    private HashSet<BuildButton> initMilSpawner() {
        HashSet<BuildButton> buttons = new HashSet<>();
        BuildButton spawn = new BuildButton("Spawn");
        spawn.addActionListener(e -> {
            selectionController.buildMode = true;
            changeActionBar("spawmilunitmenu");
            System.out.println("BUTTON PRESSED");
        });
        buttons.add(spawn);
        spawn.setVisible(false);
        return buttons;
    }

    private HashSet<BuildButton> initResSpawner() {
        HashSet<BuildButton> buttons = new HashSet<>();
        BuildButton spawn = new BuildButton("Spawn");
        spawn.addActionListener(e -> {
            selectionController.buildMode = true;
            changeActionBar("spawresunitmenu");
            System.out.println("BUTTON PRESSED");
        });
        buttons.add(spawn);
        spawn.setVisible(false);
        return buttons;
    }

    static Icon resizeIcon(Image img) {
        Image newimg = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    private HashSet<BuildButton> initAttack() {
        HashSet<BuildButton> buttons = new HashSet<>();
        BuildButton button = new BuildButton("Attack");
        button.addActionListener(e -> {
            selectionController.setAttackMode(true);
        });
        buttons.add(button);
        button.setVisible(false);
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

            workerSpawn.setVisible(false);
            militarySpawn.setVisible(false);
            farm.setVisible(false);
            stoneMine.setVisible(false);
            goldMine.setVisible(false);
            turret.setVisible(false);
            barricade.setVisible(false);

            buttons.add(workerSpawn);
            buttons.add(militarySpawn);
            buttons.add(farm);
            buttons.add(stoneMine);
            buttons.add(goldMine);
            buttons.add(turret);
            buttons.add(barricade);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return buttons;
    }

    private HashSet<BuildButton> initResourceUnitsMenu() {
        HashSet<BuildButton> buttons = new HashSet<>();
        Image image = null;
        try {

            image = ImageIO.read(getClass().getResource("/resources/unit/builder.png"));
            BuildButton builder = new BuildButton("Worker", Builder.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/unit/miner.png"));
            BuildButton miner = new BuildButton("Miner", Miner.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/unit/lumberjack.png"));
            BuildButton lumberJack = new BuildButton("Lumberjack", Lumberjack.price, resizeIcon(image));



            builder.addActionListener(e -> {
                selectionController.createUnit(VisualType.BUILDER);
            });
            miner.addActionListener(e -> {
                selectionController.createUnit(VisualType.MINER);
            });
            lumberJack.addActionListener(e -> {
                selectionController.createUnit(VisualType.LUMBERJACK);
            });

            builder.setVisible(false);
            miner.setVisible(false);
            lumberJack.setVisible(false);

            buttons.add(builder);
            buttons.add(miner);
            buttons.add(lumberJack);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return buttons;
    }

    private HashSet<BuildButton> initMilitaryUnitsMenu() {
        HashSet<BuildButton> buttons = new HashSet<>();
        Image image;
        try {

            image = ImageIO.read(getClass().getResource("/resources/unit/cavalry.png"));
            BuildButton cavalry = new BuildButton("Cavalry", Cavalry.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/unit/destroyer.png"));
            BuildButton destroyer = new BuildButton("Destroyer", Destroyer.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/unit/ranger.png"));
            BuildButton ranger = new BuildButton("Ranger", Ranger.price, resizeIcon(image));

            image = ImageIO.read(getClass().getResource("/resources/unit/infantry.png"));
            BuildButton infantry = new BuildButton("Infantry", Infantry.price, resizeIcon(image));



            cavalry.addActionListener(e -> {
                selectionController.setUnitToSpawn(VisualType.CAVALRY);
            });
            destroyer.addActionListener(e -> {
                selectionController.setUnitToSpawn(VisualType.DESTROYER);
            });
            ranger.addActionListener(e -> {
                selectionController.setUnitToSpawn(VisualType.RANGER);
            });
            infantry.addActionListener(e -> {
                selectionController.setUnitToSpawn(VisualType.INFANTRY);

            });

            cavalry.setVisible(false);
            destroyer.setVisible(false);
            ranger.setVisible(false);
            infantry.setVisible(false);

            buttons.add(cavalry);
            buttons.add(destroyer);
            buttons.add(ranger);
            buttons.add(infantry);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return buttons;
    }
}
