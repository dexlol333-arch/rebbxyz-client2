package com.rebbxyz;

import com.rebbxyz.gui.ClickGuiScreen;
import com.rebbxyz.module.ModuleManager;
import com.rebbxyz.render.RenderEngine;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class RebbxyzClient implements ClientModInitializer {
    public static final String CLIENT_NAME = "rebbxyz client";
    public static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        ModuleManager.init();
        RenderEngine.init();

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.rebbxyz.open_gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.rebbxyz"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openGuiKey.wasPressed() && client.currentScreen == null) {
                client.setScreen(new ClickGuiScreen());
            }
        });
    }
}
