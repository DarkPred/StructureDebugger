package com.github.darkpred.extended_structure_blocks.mixin;

import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureBlockEditScreen.class)
public interface StructureBlockEditScreenAccessor {
    @Accessor
    StructureBlockEntity getStructure();
}
