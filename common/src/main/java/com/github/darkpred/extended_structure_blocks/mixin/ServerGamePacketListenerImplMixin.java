package com.github.darkpred.extended_structure_blocks.mixin;

import com.github.darkpred.extended_structure_blocks.ExtendedStructureBlockEntity;
import com.github.darkpred.extended_structure_blocks.ExtendedStructurePacket;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {

    @Inject(method = "handleSetStructureBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/StructureBlockEntity;setSeed(J)V", shift = At.Shift.AFTER))
    private void setStructureVoidSave(CallbackInfo ci, @Local(argsOnly = true) ServerboundSetStructureBlockPacket pPacket, @Local StructureBlockEntity structureBlockEntity) {
        ((ExtendedStructureBlockEntity) structureBlockEntity).extended$setSaveStructureVoid(((ExtendedStructurePacket) pPacket).extended$shouldSaveStructureVoid());
    }
}
