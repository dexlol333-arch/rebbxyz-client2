package com.rebbxyz.render;

import com.rebbxyz.module.ModuleManager;
import com.rebbxyz.module.modules.SpawnerFinder;
import com.rebbxyz.module.modules.StorageFinder;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.entity.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class RenderEngine {

    public static void init() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world == null || client.player == null) return;

            MatrixStack matrices = context.matrixStack();
            Vec3d camPos = context.camera().getPos();

            StorageFinder storageFinder = ModuleManager.getModule(StorageFinder.class);
            SpawnerFinder spawnerFinder = ModuleManager.getModule(SpawnerFinder.class);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();

            for (BlockEntity be : client.world.blockEntities) {
                BlockPos pos = be.getPos();
                double x = pos.getX() - camPos.x;
                double y = pos.getY() - camPos.y;
                double z = pos.getZ() - camPos.z;

                if (storageFinder.isEnabled()) {
                    float[] col = null;
                    if (be instanceof ChestBlockEntity || be instanceof EnderChestBlockEntity) {
                        col = StorageFinder.CHEST_COLOR.getValue();
                    } else if (be instanceof ShulkerBoxBlockEntity) {
                        col = StorageFinder.SHULKER_COLOR.getValue();
                    } else if (be instanceof DropperBlockEntity || be instanceof DispenserBlockEntity) {
                        col = StorageFinder.DROPPER_COLOR.getValue();
                    }

                    if (col != null) {
                        renderEspAndTracer(matrices, x, y, z, col, 
                            StorageFinder.OUTLINE.getValue(), 
                            StorageFinder.OPACITY.getValue(), 
                            StorageFinder.TRACERS.getValue(),
                            StorageFinder.LINE_WIDTH.getValue());
                    }
                }

                if (spawnerFinder.isEnabled() && be instanceof MobSpawnerBlockEntity) {
                    float[] col = SpawnerFinder.SPAWNER_COLOR.getValue();
                    renderEspAndTracer(matrices, x, y, z, col, 
                        SpawnerFinder.OUTLINE.getValue(), 
                        SpawnerFinder.OPACITY.getValue(), 
                        SpawnerFinder.TRACERS.getValue(),
                        SpawnerFinder.LINE_WIDTH.getValue());
                }
            }

            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
        });
    }

    private static void renderEspAndTracer(MatrixStack matrices, double x, double y, double z, float[] color, boolean outline, float fillOpacity, boolean tracer, float lineWidth) {
        matrices.push();
        matrices.translate(x, y, z);
        Matrix4f matrix = matrices.peek().getPositionMatrix();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        if (fillOpacity > 0.0f) {
            drawBoxQuads(buffer, matrix, 0, 0, 0, 1, 1, 1, color[0], color[1], color[2], fillOpacity);
            BufferRenderer.drawWithGlobalProgram(buffer.end());
        }

        if (tracer) {
            Vec3d lookVec = MinecraftClient.getInstance().player.getRotationVec(1.0F);
            BufferBuilder lineBuffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
            RenderSystem.lineWidth(lineWidth);

            lineBuffer.vertex(matrix, (float)-x + (float)lookVec.x, (float)-y + (float)lookVec.y + 1.62f, (float)-z + (float)lookVec.z)
                      .color(color[0], color[1], color[2], color[3]);
            lineBuffer.vertex(matrix, 0.5f, 0.5f, 0.5f)
                      .color(color[0], color[1], color[2], color[3]);

            BufferRenderer.drawWithGlobalProgram(lineBuffer.end());
        }

        matrices.pop();
    }

    private static void drawBoxQuads(BufferBuilder b, Matrix4f m, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float bCol, float a) {
        b.vertex(m, x1, y1, z2).color(r, g, bCol, a); b.vertex(m, x2, y1, z2).color(r, g, bCol, a); b.vertex(m, x2, y2, z2).color(r, g, bCol, a); b.vertex(m, x1, y2, z2).color(r, g, bCol, a);
        b.vertex(m, x2, y1, z1).color(r, g, bCol, a); b.vertex(m, x1, y1, z1).color(r, g, bCol, a); b.vertex(m, x1, y2, z1).color(r, g, bCol, a); b.vertex(m, x2, y2, z1).color(r, g, bCol, a);
        b.vertex(m, x1, y2, z1).color(r, g, bCol, a); b.vertex(m, x1, y2, z2).color(r, g, bCol, a); b.vertex(m, x2, y2, z2).color(r, g, bCol, a); b.vertex(m, x2, y2, z1).color(r, g, bCol, a);
        b.vertex(m, x1, y1, z1).color(r, g, bCol, a); b.vertex(m, x2, y1, z1).color(r, g, bCol, a); b.vertex(m, x2, y1, z2).color(r, g, bCol, a); b.vertex(m, x1, y1, z2).color(r, g, bCol, a);
    }
}
