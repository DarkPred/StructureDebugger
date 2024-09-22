package com.github.darkpred.extended_structure_blocks.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.blockentity.StructureBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(StructureBlockRenderer.class)
public abstract class StructureBlockRendererMixin {

    @Inject(method = "renderInvisibleBlocks", at = @At(value = "HEAD"), cancellable = true)
    private void renderCaveAir(StructureBlockEntity blockEntity, VertexConsumer consumer, BlockPos pos, PoseStack poseStack, CallbackInfo ci) {
        ci.cancel();
        BlockGetter blockGetter = blockEntity.getLevel();
        BlockPos blockPos = blockEntity.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(pos);
        Iterator<BlockPos> var8 = BlockPos.betweenClosed(blockPos2, blockPos2.offset(blockEntity.getStructureSize()).offset(-1, -1, -1)).iterator();

        while (true) {
            BlockPos blockPos3;
            boolean isAir;
            boolean isCaveAir;
            boolean isVoid;
            boolean isBarrier;
            boolean isLight;
            boolean bl5;
            do {
                if (!var8.hasNext()) {
                    return;
                }
                blockPos3 = var8.next();
                BlockState blockState = blockGetter.getBlockState(blockPos3);
                isAir = blockState.isAir();
                isCaveAir = blockState.is(Blocks.CAVE_AIR);
                isVoid = blockState.is(Blocks.STRUCTURE_VOID);
                isBarrier = blockState.is(Blocks.BARRIER);
                isLight = blockState.is(Blocks.LIGHT);
                bl5 = isVoid || isBarrier || isLight;
            } while (!isAir && !bl5);

            float f = isAir ? 0.05f : 0;
            double d = (blockPos3.getX() - blockPos.getX()) + 0.45f - f;
            double e = (blockPos3.getY() - blockPos.getY()) + 0.45f - f;
            double g = (blockPos3.getZ() - blockPos.getZ()) + 0.45f - f;
            double h = (blockPos3.getX() - blockPos.getX()) + 0.55f + f;
            double i = (blockPos3.getY() - blockPos.getY()) + 0.55f + f;
            double j = (blockPos3.getZ() - blockPos.getZ()) + 0.55f + f;
            if (isAir) {
                if (isCaveAir) {
                    LevelRenderer.renderLineBox(poseStack, consumer, d, e, g, h, i, j, 0.5f, 1, 0.5f, 1, 0.5f, 1, 0.5f);
                } else {
                    LevelRenderer.renderLineBox(poseStack, consumer, d, e, g, h, i, j, 0.5f, 0.5f, 1, 1, 0.5f, 0.5f, 1);
                }
            } else if (isVoid) {
                LevelRenderer.renderLineBox(poseStack, consumer, d, e, g, h, i, j, 1, 0.75f, 0.75f, 1, 1, 0.75f, 0.75f);
            } else if (isBarrier) {
                LevelRenderer.renderLineBox(poseStack, consumer, d, e, g, h, i, j, 1, 0, 0, 1, 1, 0, 0);
            } else if (isLight) {
                LevelRenderer.renderLineBox(poseStack, consumer, d, e, g, h, i, j, 1, 1, 0, 1, 1, 1, 0);
            }
        }
    }
}
