package moe.ymc.safelog.mixin;

import com.mojang.authlib.GameProfile;
import moe.ymc.safelog.Safelog;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.security.MessageDigest;

@Mixin(value = ServerLoginNetworkHandler.class)
public class ServerLoginNetworkHandlerMixin {
    @Shadow
    GameProfile profile;
    @Shadow
    @Final
    public ClientConnection connection;

    @Inject(
            method = "getConnectionInfo",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getConnectionInfo(CallbackInfoReturnable<String> cir) {
        if(profile != null) {
            cir.setReturnValue(profile + " (" + Safelog.sanitize(this.connection.getAddress()) + ")");
        } else {
            cir.setReturnValue(Safelog.sanitize(this.connection.getAddress()));
        }
    }
}
