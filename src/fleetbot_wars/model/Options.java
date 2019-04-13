/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class Options extends Menu {

    private final int SLIDER_EXTENT = 15;
    private final int SLIDER_MIN = 0;
    private final int SLIDER_MAX = 100;

    private boolean music, gameSounds, globalSound;
    private int musicValue, gameSoundsValue, globalSoundsValue;

    Options(GUI gui) {
        super(gui);
        initPane();
    }

    private void backToDefault() {
        music = true;
        gameSounds = true;
        globalSound = true;

        musicValue = 50;
        gameSoundsValue = 50;
        globalSoundsValue = 50;
    }

    private void initPane() {
        JTabbedPane upperPane = initUpperPane();
        JPanel lowerPane = initLowerPane();
        GridLayout layout = new GridLayout(2, 1);
        this.setLayout(layout);

        this.add(upperPane);
        this.add(lowerPane);
    }

    //TODO: add buttons to lowerPane and implement listeners
    private JPanel initLowerPane() {
        JPanel lowerPane = new JPanel();
        GridLayout layout = new GridLayout();
        lowerPane.setLayout(layout);

        JButton okButton = new JButton("Accept");
        JButton defaultButton = new JButton("Default");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener((e) -> {

        });
        defaultButton.addActionListener((e) -> {
            backToDefault();
        });
        cancelButton.addActionListener((e) -> {
            gui.putComponentToFront(this, GUI.ComponentType.MAIN);
        });

        return lowerPane;
    }

    private JTabbedPane initUpperPane() {
        JTabbedPane upperPane = new JTabbedPane();
        JPanel sound = initSoundTab();
        JPanel misc = initMiscTab();
        upperPane.addTab("Sound", sound);
        upperPane.addTab("Misc", misc);
        return upperPane;
    }

    private JPanel initMiscTab() {
        JPanel misc = new JPanel();
        GridLayout layout = new GridLayout();
        layout.setColumns(2);
        layout.setRows(3);
        misc.setLayout(layout);

        JCheckBox placeHolder = new JCheckBox();

        misc.add(placeHolder);

        return misc;
    }

    private JPanel initSoundTab() {
        JPanel sound = new JPanel();

        GridLayout layout = new GridLayout(0, 3);
        layout.setHgap(20);
        layout.setVgap(20);
        sound.setLayout(layout);

        //Subcomponenst of sound tab
        JPanel globalSound = new JPanel();
        JPanel gameSounds = new JPanel();
        JPanel music = new JPanel();
        layout = new GridLayout(3, 1);
        globalSound.setLayout(layout);
        gameSounds.setLayout(layout);
        music.setLayout(layout);

        //globalSound components
        JLabel globalSoundLabel = new JLabel("Global sound");
        JCheckBox globalSoundCheckBox = new JCheckBox();
        globalSoundCheckBox.setText("On/Off");
        JSlider globalSoundSlider = new JSlider();
        globalSoundSlider.setLabelTable(globalSoundSlider.createStandardLabels(25, 0));
        globalSoundSlider.setPaintLabels(true);
        globalSoundSlider.setExtent(SLIDER_EXTENT);
        globalSoundSlider.setMinimum(SLIDER_MIN);
        globalSoundSlider.setMaximum(SLIDER_MAX);

        globalSoundCheckBox.addActionListener((e) -> {
            this.globalSound = !this.globalSound;
            globalSoundCheckBox.setEnabled(this.globalSound);
        });
        globalSoundSlider.addChangeListener((e) -> {
            if (globalSoundSlider.getValueIsAdjusting())
                globalSoundsValue = globalSoundSlider.getValue();
        });
        globalSound.setBorder(new EtchedBorder());
        globalSound.add(globalSoundLabel);
        globalSound.add(globalSoundCheckBox);
        globalSound.add(globalSoundSlider);

        //gameSound components
        JLabel gameSoundsLabel = new JLabel("Game Sounds");
        JCheckBox gameSoundCheckBox = new JCheckBox();
        JSlider gameSoundSlider = new JSlider();
        gameSoundSlider.setLabelTable(gameSoundSlider.createStandardLabels(25, 0));
        gameSoundSlider.setPaintLabels(true);
        gameSoundSlider.setExtent(SLIDER_EXTENT);
        gameSoundSlider.setMinimum(SLIDER_MIN);
        gameSoundSlider.setMaximum(SLIDER_MAX);

        gameSoundCheckBox.addActionListener((e) -> {
            this.gameSounds = !this.gameSounds;
            gameSoundCheckBox.setEnabled(this.gameSounds);
        });
        gameSoundSlider.addChangeListener((e) -> {
            if (gameSoundSlider.getValueIsAdjusting())
                gameSoundsValue = gameSoundSlider.getValue();
        });
        gameSounds.setBorder(new EtchedBorder());
        gameSounds.add(gameSoundsLabel);
        gameSounds.add(gameSoundCheckBox);
        gameSounds.add(gameSoundSlider);

        //music components
        JLabel musicLabel = new JLabel("Music");
        JCheckBox musicCheckBox = new JCheckBox();
        JSlider musicSlider = new JSlider();
        musicSlider.setLabelTable(musicSlider.createStandardLabels(25, 0));
        musicSlider.setPaintLabels(true);
        musicSlider.setExtent(SLIDER_EXTENT);
        musicSlider.setMinimum(SLIDER_MIN);
        musicSlider.setMaximum(SLIDER_MAX);

        musicCheckBox.addActionListener((e) -> {
            this.music = !this.music;
            musicCheckBox.setEnabled(this.music);
        });
        musicSlider.addChangeListener((e) -> {
            if (!musicSlider.getValueIsAdjusting())
                musicValue = musicSlider.getValue();
        });
        music.setBorder(new EtchedBorder());
        music.add(musicLabel);
        music.add(musicCheckBox);
        music.add(musicSlider);

        sound.add(globalSound);
        sound.add(gameSounds);
        sound.add(music);
        //End of sound tab

        return sound;
    }

    @Override
    protected void initButtons() {

    }
}
