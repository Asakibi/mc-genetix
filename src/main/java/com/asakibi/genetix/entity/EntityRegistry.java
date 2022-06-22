package com.asakibi.genetix.entity;

import com.asakibi.genetix.Mod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistry {
    public static final EntityType<GenetixSheepEntity> GENETIX_SHEEP
        = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(Mod.NAME, "sheep"),
            FabricEntityTypeBuilder
                .create(SpawnGroup.CREATURE, GenetixSheepEntity::new)
                .dimensions(EntityDimensions.fixed(0.9f, 1.3f))
                .build()
    );

    public static void registerAll() {
        FabricDefaultAttributeRegistry.register(GENETIX_SHEEP, GenetixSheepEntity.createAttributes());
    }
}
