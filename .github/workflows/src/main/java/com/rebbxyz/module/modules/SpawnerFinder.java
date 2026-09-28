package com.rebbxyz.module.modules;

import com.rebbxyz.module.Module;

public class SpawnerFinder extends Module {
    public static final BooleanSetting OUTLINE = new BooleanSetting("Outline", true);
    public static final NumberSetting OPACITY = new NumberSetting("Fill Opacity", 0.40f, 0.0f, 1.0f);
    public static final NumberSetting LINE_WIDTH = new NumberSetting("Line Width", 2.5f, 0.5f, 5.0f);
    public static final BooleanSetting TRACERS = new BooleanSetting("Tracers", true);
    public static final ColorSetting SPAWNER_COLOR = new ColorSetting("Spawner Color", 0.90f, 0.15f, 0.20f, 0.9f);

    public SpawnerFinder() {
        super("SpawnerFinder", Category.RENDER);
        addSetting(OUTLINE);
        addSetting(OPACITY);
        addSetting(LINE_WIDTH);
        addSetting(TRACERS);
        addSetting(SPAWNER_COLOR);
    }
}
