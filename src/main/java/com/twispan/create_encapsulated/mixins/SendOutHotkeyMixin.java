package com.twispan.create_encapsulated.mixins;

import com.cobblemon.mod.common.client.keybind.keybinds.PartySendBinding;
import com.twispan.create_encapsulated.events.SendOutHotkeyOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PartySendBinding.class)
public class SendOutHotkeyMixin {
    // We don't want the suppressRelease bool to remain true after the sendOut action is suppressed.
    @Inject(method = "onRelease", at = @At("HEAD"), cancellable = true)
    private void create_encapsulated$skipWhenLookingAtBurner(CallbackInfo callbackInfo) {
        if (SendOutHotkeyOverride.suppressRelease) {
            SendOutHotkeyOverride.suppressRelease = false;
            callbackInfo.cancel();
        }
    }
}
