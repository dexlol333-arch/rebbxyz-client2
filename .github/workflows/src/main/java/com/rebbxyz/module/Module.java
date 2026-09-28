package com.rebbxyz.module;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private final String name;
    private final Category category;
    private boolean enabled;
    private final List<Setting<?>> settings = new ArrayList<>();

    public enum Category {
        RENDER, UTILITY, MOVEMENT, COMBAT
    }

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (enabled) onEnable(); else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}

    public String getName() { return name; }
    public Category getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public List<Setting<?>> getSettings() { return settings; }
    public void addSetting(Setting<?> setting) { settings.add(setting); }

    public static class Setting<T> {
        private final String name;
        private T value;

        public Setting(String name, T defaultValue) {
            this.name = name;
            this.value = defaultValue;
        }

        public String getName() { return name; }
        public T getValue() { return value; }
        public void setValue(T value) { this.value = value; }
    }

    public static class ColorSetting extends Setting<float[]> {
        public ColorSetting(String name, float r, float g, float b, float a) {
            super(name, new float[]{r, g, b, a});
        }
    }

    public static class NumberSetting extends Setting<Float> {
        private final float min, max;
        public NumberSetting(String name, float defaultValue, float min, float max) {
            super(name, defaultValue);
            this.min = min;
            this.max = max;
        }
        public float getMin() { return min; }
        public float getMax() { return max; }
    }

    public static class BooleanSetting extends Setting<Boolean> {
        public BooleanSetting(String name, boolean defaultValue) {
            super(name, defaultValue);
        }
    }
}
