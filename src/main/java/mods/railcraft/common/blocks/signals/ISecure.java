/* 
 * Copyright (c) CovertJaguar, 2014 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 */
package mods.railcraft.common.blocks.signals;

import mods.railcraft.api.core.IOwnable;
import mods.railcraft.common.gui.buttons.IMultiButtonState;
import mods.railcraft.common.gui.buttons.MultiButtonController;
import net.minecraft.world.IWorldNameable;

/**
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public interface ISecure<T extends IMultiButtonState> extends IOwnable, IWorldNameable {

    MultiButtonController<T> getLockController();

    /*
     * Indicates if this object is locked
     */
    boolean isSecure();

}
