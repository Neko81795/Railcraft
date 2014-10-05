/* 
 * Copyright (c) CovertJaguar, 2014 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 */
package mods.railcraft.common.gui.containers;

import buildcraft.api.power.PowerHandler.PowerReceiver;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import mods.railcraft.common.blocks.machine.alpha.TileRockCrusher;
import mods.railcraft.common.gui.widgets.IndicatorWidget;
import mods.railcraft.common.gui.slots.SlotOutput;
import mods.railcraft.common.gui.slots.SlotRailcraft;
import mods.railcraft.common.gui.widgets.MJEnergyIndicator;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerRockCrusher extends RailcraftContainer {

    private TileRockCrusher tile;
    private int lastProcessTime;
    private final MJEnergyIndicator energyIndicator;

    public ContainerRockCrusher(InventoryPlayer inventoryplayer, TileRockCrusher crusher) {
        super(crusher);
        this.tile = crusher;

        energyIndicator = new MJEnergyIndicator(TileRockCrusher.MAX_ENERGY);
        addWidget(new IndicatorWidget(energyIndicator, 157, 23, 176, 53, 6, 48));

        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                addSlot(new SlotRockCrusher(crusher, i * 3 + k, 8 + k * 18, 21 + i * 18));
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                addSlot(new SlotOutput(crusher, 9 + i * 3 + k, 98 + k * 18, 21 + i * 18));
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }

        }

        for (int j = 0; j < 9; j++) {
            addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }

    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        PowerReceiver provider = tile.getPowerReceiver(null);
        for (int i = 0; i < crafters.size(); i++) {
            ICrafting icrafting = (ICrafting) crafters.get(i);

            if (lastProcessTime != tile.getProcessTime())
                icrafting.sendProgressBarUpdate(this, 0, tile.getProcessTime());

            if (provider != null)
                icrafting.sendProgressBarUpdate(this, 1, (int) provider.getEnergyStored());
        }

        lastProcessTime = tile.getProcessTime();
    }

    @Override
    public void addCraftingToCrafters(ICrafting icrafting) {
        super.addCraftingToCrafters(icrafting);
        icrafting.sendProgressBarUpdate(this, 0, tile.getProcessTime());
        PowerReceiver provider = tile.getPowerReceiver(null);
        if (provider != null)
            icrafting.sendProgressBarUpdate(this, 2, (int) provider.getEnergyStored());
    }

    @Override
    public void updateProgressBar(int id, int data) {
        switch (id) {
            case 0:
                tile.setProcessTime(data);
                break;
            case 1:
                energyIndicator.setEnergy(data);
                break;
            case 2:
                energyIndicator.updateEnergy(data);
                break;
        }
    }

    public class SlotRockCrusher extends SlotRailcraft {

        public SlotRockCrusher(IInventory iinventory, int slotIndex, int posX, int posY) {
            super(iinventory, slotIndex, posX, posY);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            if (stack != null && RailcraftCraftingManager.rockCrusher.getRecipe(stack) != null)
                return true;
            return false;
        }

    }
}