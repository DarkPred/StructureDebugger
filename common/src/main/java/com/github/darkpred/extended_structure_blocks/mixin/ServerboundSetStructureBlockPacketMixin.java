package com.github.darkpred.extended_structure_blocks.mixin;

import com.github.darkpred.extended_structure_blocks.ExtendedStructurePacket;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerboundSetStructureBlockPacket.class)
public abstract class ServerboundSetStructureBlockPacketMixin implements ExtendedStructurePacket {

    @Unique
    private boolean extendedStructureBlocks$saveStructureVoid;
    @Unique
    private boolean extendedStructureBlocks$showCaveAir;

    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("TAIL"))
    private void updateConstructor(CallbackInfo ci, @Local(argsOnly = true) FriendlyByteBuf pBuffer) {
        extendedStructureBlocks$setSaveStructureVoid(pBuffer.readBoolean());
        extendedStructureBlocks$setShowCaveAir(pBuffer.readBoolean());
    }

    @Inject(method = "write", at = @At("TAIL"))
    private void updateWrite(CallbackInfo ci, @Local(argsOnly = true) FriendlyByteBuf pBuffer) {
        pBuffer.writeBoolean(extendedStructureBlocks$saveStructureVoid);
        pBuffer.writeBoolean(extendedStructureBlocks$showCaveAir);
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
