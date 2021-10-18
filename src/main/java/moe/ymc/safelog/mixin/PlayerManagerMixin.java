package moe.ymc.safelog.mixin;

import moe.ymc.safelog.SanitizedInetSocketAddress;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Redirect(
            method = "onPlayerConnect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/ClientConnection;getAddress()Ljava/net/SocketAddress;")
    )
    public SocketAddress modifyGetAddress(ClientConnection clientConnection) {
        SocketAddress address = clientConnection.getAddress();
        if (!(address instanceof InetSocketAddress inetAddress)) {
            return address;
        }
        return new SanitizedInetSocketAddress(inetAddress.getAddress(), inetAddress.getPort());
    }
}
