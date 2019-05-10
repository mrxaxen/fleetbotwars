/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import fleetbot_wars.model.enums.VisualType;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author WB
 */
class ActionBar extends JPanel {

    //    Builder     Soldier     Lumberjack    Miner
    private static final HashSet<UnitType> attackers = new HashSet<>();
    private static ActionBar instance = new ActionBar();
    private static HashSet<JButton> active;
    private HashMap<String,HashSet<JButton>> oneToRuleThemAll = new HashMap<>();
    private SelectionController selectionController = SelectionController.getInstance();

    static ActionBar getInstance() {
        return instance;
    }
    //Reimplements with button lists so the action bar component does not resize itself
    private ActionBar() {
        initAttackers();
        HashSet<JButton> builder = initBuilder();
        HashSet<JButton> buildMenu = initBuildMenu();
        HashSet<JButton> attack = initAttack();
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

    private void addBar(HashSet<JButton> bar) {
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
                button.setEnabled(true);
            });

        }
    }

    private HashSet<JButton> initBuilder() {
        HashSet<JButton> buttons = new HashSet<>();
        JButton build = new JButton("Build");
        build.addActionListener(e -> {
            selectionController.buildMode = true;
            changeActionBar("buildmenu");
            System.out.println("BUTTON PRESSED");
        });
        buttons.add(build);
        build.setEnabled(false);
        return buttons;
    }

    private HashSet<JButton> initAttack() {
        HashSet<JButton> buttons = new HashSet<>();
        JButton button = new JButton("Attack");
        button.addActionListener( e -> {
            selectionController.setAttackMode(true);
        });
        buttons.add(button);
        button.setEnabled(false);
        return buttons;
    }

    private HashSet<JButton> initBuildMenu() {
        HashSet<JButton> buttons = new HashSet<>();
        JButton workerSpawn = new JButton("Worker Training Center");
        JButton militarySpawn = new JButton("Military Training Center");
        JButton farm = new JButton("Farm");
        JButton stoneMine = new JButton("Stone Mine");
        JButton goldMine = new JButton("Gold Mine");
        JButton turret = new JButton("Turret");
        JButton barricade = new JButton("Barricade");
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

        return buttons;
    }
}
