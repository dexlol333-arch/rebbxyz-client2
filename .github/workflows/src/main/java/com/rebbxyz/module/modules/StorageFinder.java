package com.rebbxyz.module.modules;

import com.rebbxyz.module.Module;

public class StorageFinder extends Module {
    public static final BooleanSetting OUTLINE = new BooleanSetting("Outline", true);
    public static final NumberSetting OPACITY = new NumberSetting("Fill Opacity", 0.35f, 0.0f, 1.0f);
    public static final NumberSetting LINE_WIDTH = new NumberSetting("Line Width", 2.0f, 0.5f, 5.0f);
    public static final BooleanSetting TRACERS = new BooleanSetting("Tracers", true);

    public static final ColorSetting CHEST_COLOR = new ColorSetting("Chest Color", 0.95f, 0.65f, 0.15f, 0.8f);
    public static final ColorSetting SHULKER_COLOR = new ColorSetting("Shulker Color", 0.80f, 0.30f, 0.85f, 0.8f);
    public static final ColorSetting DROPPER_COLOR = new ColorSetting("Dropper Color", 0.50f, 0.50f, 0.55f, 0.8f);

    public StorageFinder() {
        super("StorageFinder", Category.RENDER);
        addSetting(OUTLINE);
        addSetting(OPACITY);
        addSetting(LINE_WIDTH);
        addSetting(TRACERS);
        addSetting(CHEST_COLOR);
        addSetting(SHULKER_COLOR);
        addSetting(DROPPER_COLOR);
    }
}
