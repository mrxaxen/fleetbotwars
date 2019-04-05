/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fleetbot_wars.model;

import javafx.scene.control.TextFormatter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 *
 * @author EG
 */
public class Options extends Menu {

    boolean music = true;
    boolean gameSounds = true;
    boolean globalSound = true;

    int musicValue = 50;
    int gameSoundsValue = 50;
    int globalSoundValue = 50;

    JTabbedPane upperPane;
    JPanel lowerPane;
    JPanel sound;
    JPanel misc;

    Options(GUI gui) {
        super(gui);
        upperPane = new JTabbedPane();
        lowerPane = new JPanel();
        sound = new JPanel();
        misc = new JPanel();
    }

    private void initUpperPane() {
        initSoundTab();
        upperPane.addTab("Sound", sound);
        upperPane.addTab("Misc", misc);
    }

    private void initSoundTab() {

        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);
        layout.setHgap(20);
        layout.setVgap(20);
        sound.setLayout(layout);

        //Subcomponenst of sound tab
        JPanel globalSound = new JPanel();
        JPanel gameSounds = new JPanel();
        JPanel music = new JPanel();
        globalSound.setLayout(layout);
        gameSounds.setLayout(layout);
        music.setLayout(layout);

        //globalSound components
        JCheckBox globalSoundCheckBox = new JCheckBox();
        JSlider globalSoundSlider = new JSlider();
        globalSoundSlider.setMinimum(0);
        globalSoundSlider.setMaximum(100);

        globalSoundCheckBox.addChangeListener((e) -> {
            this.globalSound = !this.globalSound;
            globalSoundCheckBox.setEnabled(this.globalSound);
        });
        globalSoundSlider.addChangeListener((e) -> {
            globalSoundValue = globalSoundSlider.getValue();
        });
        globalSound.add(globalSoundCheckBox);
        globalSound.add(globalSoundSlider);

        //gameSound components
        JCheckBox gameSoundCheckBox = new JCheckBox();
        JSlider gameSoundSlider = new JSlider();
        gameSoundSlider.setMinimum(0);
        gameSoundSlider.setMaximum(100);

        gameSoundCheckBox.addChangeListener((e) -> {
            this.gameSounds = !this.gameSounds;
            gameSoundCheckBox.setEnabled(this.gameSounds);
        });
        gameSoundSlider.addChangeListener((e) -> {
            gameSoundsValue = gameSoundSlider.getValue();
        });
        gameSounds.add(gameSoundCheckBox);
        gameSounds.add(gameSoundSlider);

        //music components
        JCheckBox musicCheckBox = new JCheckBox();
        JSlider musicSlider = new JSlider();
        musicSlider.setMinimum(0);
        musicSlider.setMaximum(100);

        musicCheckBox.addChangeListener((e) -> {
            this.music = !this.music;
            musicCheckBox.setEnabled(this.music);
        });
        musicSlider.addChangeListener((e) -> {
            musicValue = musicSlider.getValue();
        });
        music.add(musicCheckBox);
        music.add(musicSlider);

        sound.add(globalSound);
        sound.add(gameSounds);
        sound.add(music);
        //End of sound tab

    }

    @Override
    protected void initButtons() {

    }
}
