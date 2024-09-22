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

@Mixin(value = ServerboundSetStructureBlockPacket.class, priority = 995)
public abstract class ServerboundSetStructureBlockPacketMixin implements ExtendedStructurePacket {

    @Unique
    private boolean extended$saveStructureVoid;

    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("TAIL"))
    private void updateConstructor(CallbackInfo ci, @Local(argsOnly = true) FriendlyByteBuf pBuffer) {
        extended$setSaveStructureVoid(pBuffer.readBoolean());
    }

    @Inject(method = "write", at = @At("TAIL"))
    private void updateWrite(CallbackInfo ci, @Local(argsOnly = true) FriendlyByteBuf pBuffer) {
        pBuffer.writeBoolean(extended$saveStructureVoid);
    }

    @Override
    public boolean extended$shouldSaveStructureVoid() {
        return extended$saveStructureVoid;
    }

    @Override
    public void extended$setSaveStructureVoid(boolean saveStructureVoid) {
        this.extended$saveStructureVoid = saveStructureVoid;
    }
}
