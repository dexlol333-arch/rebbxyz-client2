package com.rebbxyz.gui;

import com.rebbxyz.module.Module;
import com.rebbxyz.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class ClickGuiScreen extends Screen {
    private Module selectedModuleForSettings = null;
    private final Map<Module.Category, Integer> categoryX = new HashMap<>();

    public ClickGuiScreen() {
        super(Text.literal("rebbxyz client"));
        int startX = 30;
        for (Module.Category cat : Module.Category.values()) {
            categoryX.put(cat, startX);
            startX += 120;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        context.drawText(this.textRenderer, "rebbxyz client", 15, 12, 0xFF38B6FF, true);

        for (Module.Category category : Module.Category.values()) {
            int x = categoryX.get(category);
            int y = 35;

            context.fill(x, y, x + 110, y + 20, 0xFF18181C);
            context.drawText(this.textRenderer, category.name(), x + 8, y + 6, 0xFFE1E1E1, false);

            int moduleY = y + 22;
            for (Module mod : ModuleManager.getModulesByCategory(category)) {
                boolean hovered = mouseX >= x && mouseX <= x + 110 && mouseY >= moduleY && mouseY <= moduleY + 18;
                int bgColor = mod.isEnabled() ? 0xFF2A2A32 : (hovered ? 0xFF222228 : 0xFF141418);
                int textColor = mod.isEnabled() ? 0xFF38B6FF : 0xFF909090;

                context.fill(x, moduleY, x + 110, moduleY + 18, bgColor);
                context.drawText(this.textRenderer, mod.getName(), x + 8, moduleY + 5, textColor, false);

                if (!mod.getSettings().isEmpty()) {
                    context.drawText(this.textRenderer, ">", x + 98, moduleY + 5, 0xFF606060, false);
                }

                moduleY += 19;
            }
        }

        if (selectedModuleForSettings != null) {
            renderSettingsPanel(context, mouseX, mouseY);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderSettingsPanel(DrawContext context, int mouseX, int mouseY) {
        int panelX = this.width - 180;
        int panelY = 35;
        int panelWidth = 160;

        context.fill(panelX, panelY, panelX + panelWidth, panelY + 20, 0xFF18181C);
        context.drawText(this.textRenderer, selectedModuleForSettings.getName() + " Settings", panelX + 8, panelY + 6, 0xFF38B6FF, false);

        int currentY = panelY + 24;
        for (Module.Setting<?> setting : selectedModuleForSettings.getSettings()) {
            context.fill(panelX, currentY, panelX + panelWidth, currentY + 18, 0xFF141418);

            if (setting instanceof Module.BooleanSetting boolSet) {
                String status = boolSet.getValue() ? "[ON]" : "[OFF]";
                int col = boolSet.getValue() ? 0xFF55FF55 : 0xFFFF5555;
                context.drawText(this.textRenderer, setting.getName(), panelX + 6, currentY + 5, 0xFFCCCCCC, false);
                context.drawText(this.textRenderer, status, panelX + panelWidth - 32, currentY + 5, col, false);
            } else if (setting instanceof Module.NumberSetting numSet) {
                context.drawText(this.textRenderer, setting.getName() + ": " + String.format("%.1f", numSet.getValue()), panelX + 6, currentY + 5, 0xFFCCCCCC, false);
            } else if (setting instanceof Module.ColorSetting) {
                context.drawText(this.textRenderer, setting.getName() + " (RGBA)", panelX + 6, currentY + 5, 0xFFCCCCCC, false);
            }

            currentY += 20;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Module.Category category : Module.Category.values()) {
            int x = categoryX.get(category);
            int moduleY = 57;

            for (Module mod : ModuleManager.getModulesByCategory(category)) {
                if (mouseX >= x && mouseX <= x + 110 && mouseY >= moduleY && mouseY <= moduleY + 18) {
                    if (button == 0) {
                        mod.toggle();
                    } else if (button == 1) {
                        selectedModuleForSettings = (selectedModuleForSettings == mod) ? null : mod;
                    }
                    return true;
                }
                moduleY += 19;
            }
        }

        if (selectedModuleForSettings != null) {
            int panelX = this.width - 180;
            int currentY = 59;
            for (Module.Setting<?> setting : selectedModuleForSettings.getSettings()) {
                if (mouseX >= panelX && mouseX <= panelX + 160 && mouseY >= currentY && mouseY <= currentY + 18) {
                    if (setting instanceof Module.BooleanSetting boolSet) {
                        boolSet.setValue(!boolSet.getValue());
                    }
                    return true;
                }
                currentY += 20;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
