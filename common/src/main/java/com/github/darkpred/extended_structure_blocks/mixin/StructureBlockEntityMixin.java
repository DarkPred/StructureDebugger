package com.github.darkpred.extended_structure_blocks.mixin;

import com.github.darkpred.extended_structure_blocks.ExtendedStructureBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(StructureBlockEntity.class)
public abstract class StructureBlockEntityMixin implements ExtendedStructureBlockEntity {
    @Unique
    private boolean extendedStructureBlocks$saveStructureVoid;
    @Unique
    private boolean extendedStructureBlocks$showCaveAir;

    @ModifyArg(method = "saveStructure(Z)Z", index = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;fillFromWorld(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Vec3i;ZLnet/minecraft/world/level/block/Block;)V"))
    private @Nullable Block saveStructureVoid(@Nullable Block toIgnore) {
        return extendedStructureBlocks$shouldSaveStructureVoid() ? null : toIgnore;
    }

    @Override
    public boolean extendedStructureBlocks$shouldSaveStructureVoid() {
        return extendedStructureBlocks$saveStructureVoid;
    }

    @Override
    public void extendedStructureBlocks$setSaveStructureVoid(boolean saveStructureVoid) {
        this.extendedStructureBlocks$saveStructureVoid = saveStructureVoid;
    }

    @Override
    public boolean extendedStructureBlocks$shouldShowCaveAir() {
        return extendedStructureBlocks$showCaveAir;
    }

    @Override
    public void extendedStructureBlocks$setShowCaveAir(boolean showCaveAir) {
        this.extendedStructureBlocks$showCaveAir = showCaveAir;
    }
}