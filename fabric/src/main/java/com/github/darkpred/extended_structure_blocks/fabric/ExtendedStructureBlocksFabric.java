package com.github.darkpred.extended_structure_blocks.fabric;

import com.github.darkpred.extended_structure_blocks.ExtendedStructureBlocks;
import net.fabricmc.api.ModInitializer;

public final class ExtendedStructureBlocksFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        ExtendedStructureBlocks.init();
    }
}
