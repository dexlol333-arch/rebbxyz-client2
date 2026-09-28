package com.rebbxyz.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {

    public ClickGuiScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, "Rebbxyz Client GUI", this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
