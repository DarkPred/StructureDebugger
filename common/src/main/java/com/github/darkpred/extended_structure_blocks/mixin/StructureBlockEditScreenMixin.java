package com.github.darkpred.extended_structure_blocks.mixin;

import com.github.darkpred.extended_structure_blocks.ExtendedStructureBlockEntity;
import com.github.darkpred.extended_structure_blocks.ExtendedStructurePacket;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(StructureBlockEditScreen.class)
public abstract class StructureBlockEditScreenMixin {

    @Shadow
    @Final
    private StructureBlockEntity structure;

    @ModifyArg(method = "sendToServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V"))
    private Packet<?> injected(Packet<?> pPacket) {
        ((ExtendedStructurePacket) pPacket).extendedStructureBlocks$setSaveStructureVoid(((ExtendedStructureBlockEntity)structure).extendedStructureBlocks$shouldSaveStructureVoid());
        ((ExtendedStructurePacket) pPacket).extendedStructureBlocks$setShowCaveAir(((ExtendedStructureBlockEntity)structure).extendedStructureBlocks$shouldShowCaveAir());
        return pPacket;
    }
}
