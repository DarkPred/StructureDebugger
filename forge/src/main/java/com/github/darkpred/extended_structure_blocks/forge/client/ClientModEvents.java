package com.github.darkpred.extended_structure_blocks.forge.client;

import com.github.darkpred.extended_structure_blocks.ExtendedStructureBlockEntity;
import com.github.darkpred.extended_structure_blocks.ExtendedStructureBlocks;
import com.github.darkpred.extended_structure_blocks.mixin.StructureBlockEditScreenAccessor;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ExtendedStructureBlocks.MOD_ID, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void addButton(ScreenEvent.InitScreenEvent.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof StructureBlockEditScreen editScreen) {
            var builder = CycleButton.booleanBuilder(new TextComponent("On"), new TextComponent("Off")).withValues(
                    List.of(Boolean.TRUE, Boolean.FALSE));
            ExtendedStructureBlockEntity structure = (ExtendedStructureBlockEntity) ((StructureBlockEditScreenAccessor) editScreen).getStructure();
            builder.withInitialValue(structure.extendedStructureBlocks$shouldSaveStructureVoid());
            event.addListener(builder.create(screen.width / 2 + 4 + 160, 80, 100, 20, new TextComponent("Save Void"),
                    (cycleButton, object) -> structure.extendedStructureBlocks$setSaveStructureVoid((Boolean) cycleButton.getValue())));

            builder.withInitialValue(structure.extendedStructureBlocks$shouldShowCaveAir());
            event.addListener(builder.create(screen.width / 2 + 4 + 160, 110, 100, 20, new TextComponent("Show Cave Air"),
                    (cycleButton, object) -> structure.extendedStructureBlocks$setShowCaveAir((Boolean) cycleButton.getValue())));
        }
    }
}
