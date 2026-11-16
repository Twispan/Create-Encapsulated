package com.twispan.create_encapsulated.plugins.ponder;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModScenes {
    public static void slugma_burner(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("slugma_burner", "Slugma Burners");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.idle(10);

        BlockPos burner = util.grid().at(2, 1, 2);
        scene.world().showSection(util.select().position(burner), Direction.DOWN);
        scene.idle(10);

        scene.overlay().showText(70)
                .attachKeyFrame()
                .text("Slugma Burners are in all ways similar to Blaze Burners in terms of functionality.")
                .pointAt(util.vector().blockSurface(burner, Direction.WEST))
                .placeNearTarget();
        scene.idle(95);

        scene.overlay().showText(70)
                .attachKeyFrame()
                .text("To get a Slugma Burner, you must first capture a wild Slugma and right click on it with an empty Blaze Burner.")
                .pointAt(util.vector().blockSurface(burner, Direction.WEST))
                .placeNearTarget();
        scene.idle(95);

        scene.overlay().showText(80)
                .attachKeyFrame()
                .text("Alternatively, use the Throw Selected Pokémon hotkey while pointing at a placed empty burner while selecting a valid Slugma from your party.")
                .pointAt(util.vector().blockSurface(burner, Direction.WEST))
                .placeNearTarget();
        scene.idle(105);

        scene.overlay().showText(65)
                .attachKeyFrame()
                .text("To retrieve a Slugma from a burner, simply point at the burner and use the previous hotkey again.")
                .pointAt(util.vector().blockSurface(burner, Direction.WEST))
                .placeNearTarget();
        scene.idle(85);

        scene.overlay().showText(70)
                .attachKeyFrame()
                .text("An Alpha Slugma unfortunately wont fit inside one, same for its evolution, Magcargo.")
                .pointAt(util.vector().blockSurface(burner, Direction.WEST))
                .placeNearTarget();
        scene.idle(90);

        scene.overlay().showControls(util.vector().topOf(burner), Pointing.DOWN, 15).rightClick()
                .withItem(new ItemStack(Items.OAK_PLANKS));
        scene.idle(7);
        scene.world().modifyBlock(burner, s -> s.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.KINDLED), false);
        scene.idle(20);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .text("Fueling a Slugma functions similarly to a Blaze Burner.")
                .pointAt(util.vector().blockSurface(burner, Direction.WEST))
                .placeNearTarget();
        scene.idle(80);

        scene.world().modifyBlock(burner, s -> s.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.FADING), false);
        scene.idle(20);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .text("However, lower level Slugmas will have shorter burn time duration.")
                .pointAt(util.vector().blockSurface(burner, Direction.WEST))
                .placeNearTarget();
        scene.idle(80);

        scene.overlay().showControls(util.vector().topOf(burner), Pointing.DOWN, 15).rightClick()
                .withItem(new ItemStack(Items.LAVA_BUCKET));
        scene.idle(7);
        scene.world().modifyBlock(burner, s -> s.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.KINDLED), false);
        scene.idle(20);

        scene.overlay().showText(80)
                .attachKeyFrame()
                .text("Higher level Slugmas will have their burn time significantly increased, with a Level 100 Slugma, a Lava Bucket makes it burn for more than half an hour.")
                .pointAt(util.vector().blockSurface(burner, Direction.WEST))
                .placeNearTarget();
        scene.idle(100);

        scene.overlay().showControls(util.vector().topOf(burner), Pointing.DOWN, 30).rightClick()
                .withItem(AllItems.BLAZE_CAKE.asStack());
        scene.idle(7);
        scene.world().modifyBlock(burner, s -> s.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SEETHING), false);
        scene.idle(20);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .text("Blaze Cakes can also have their duration prolonged.")
                .pointAt(util.vector().blockSurface(burner, Direction.WEST))
                .placeNearTarget();
        scene.idle(80);

        Class<DeployerBlockEntity> teType = DeployerBlockEntity.class;
        scene.world().modifyBlockEntityNBT(util.select().position(4, 1, 2), teType,
                nbt -> nbt.put("HeldItem", AllItems.BLAZE_CAKE.asStack().saveOptional(scene.world().getHolderLookupProvider())));

        scene.world().showSection(util.select().fromTo(3, 0, 5, 2, 0, 5), Direction.UP);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(4, 1, 2, 4, 1, 5), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(2, 1, 4, 2, 1, 5), Direction.DOWN);
        scene.idle(10);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .text("Slugma Burners can also be fed automatically with Deployers or Mechanical Arms.")
                .pointAt(util.vector().blockSurface(burner.east(2), Direction.UP));
        scene.idle(80);
    }
}
