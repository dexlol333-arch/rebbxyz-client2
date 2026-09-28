package com.rebbxyz;

import net.minecraft.client.gui.GuiGraphics; // Instead of DrawContext
import net.minecraft.client.gui.screens.Screen; // Instead of net.minecraft.client.gui.screen.Screen
import net.minecraft.network.chat.Component; // Instead of net.minecraft.text.Text

public class ClickGuiScreen extends Screen {
    public ClickGuiScreen() {
        super(Component.literal("Click GUI"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
    }
}
