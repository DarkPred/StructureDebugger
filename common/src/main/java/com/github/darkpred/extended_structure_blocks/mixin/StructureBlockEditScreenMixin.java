package com.github.darkpred.extended_structure_blocks.mixin;

import com.github.darkpred.extended_structure_blocks.ExtendedStructureBlockEditScreen;
import com.github.darkpred.extended_structure_blocks.ExtendedStructureBlockEntity;
import com.github.darkpred.extended_structure_blocks.ExtendedStructurePacket;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StructureBlockEditScreen.class)
public abstract class StructureBlockEditScreenMixin extends Screen implements ExtendedStructureBlockEditScreen {

    @Unique
    private CycleButton<Boolean> extended$saveStructureVoidButton;
    @Shadow
    @Final
    private StructureBlockEntity structure;

    private StructureBlockEditScreenMixin(Component title) {
        super(title);
    }

    @ModifyArg(method = "sendToServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V"))
    private Packet<?> injected(Packet<?> pPacket) {
        ((ExtendedStructurePacket) pPacket).extended$setSaveStructureVoid(((ExtendedStructureBlockEntity) structure).extended$shouldSaveStructureVoid());
        return pPacket;
    }

    @Inject(method = "init", at = @At(value = "HEAD"))
    private void createButton(CallbackInfo ci) {
        ExtendedStructureBlockEntity extendedStructure = (ExtendedStructureBlockEntity) structure;
        var builder = CycleButton.onOffBuilder(extendedStructure.extended$shouldSaveStructureVoid());
        extended$saveStructureVoidButton = builder.create(width / 2 + 4 + 160, 80, 100, 20, new TextComponent("Save Void"),
                (cycleButton, object) -> extendedStructure.extended$setSaveStructureVoid((Boolean) cycleButton.getValue()));
        addRenderableWidget(extended$saveStructureVoidButton);
    }

    @Inject(method = "updateMode", at = @At(value = "HEAD"))
    private void updateButton(CallbackInfo ci, @Local(argsOnly = true) StructureMode mode) {
        extended$saveStructureVoidButton.visible = mode == StructureMode.SAVE;
    }

    @Override
    public CycleButton<Boolean> extended$getShouldSaveStructureVoidButton() {
        return extended$saveStructureVoidButton;
    }
}
