package moe.ymc.safelog;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.file.Path;
import java.util.Properties;

public class Safelog implements ModInitializer {
    private static String salt = "";

    @Override
    public void onInitialize() {
        try {
            final Path config = FabricLoader.getInstance().getConfigDir().resolve("safelog.properties");
            if(!config.toFile().exists()) return;
            Properties properties = new Properties();
            final InputStream in = new FileInputStream(config.toFile());
            properties.load(in);
            in.close();
            salt = properties.getProperty("salt", "");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public static String md5(@NotNull String raw) {
        final boolean removeLeadingSlash = raw.startsWith("/");
        return (removeLeadingSlash ? "/" : "") + DigestUtils.md5Hex(salt + raw.substring(removeLeadingSlash ? 1 : 0));
    }

    @NotNull
    public static String sanitize(@NotNull SocketAddress address) {
        if(!(address instanceof final InetSocketAddress inetSocketAddress)) return address.toString();
        return md5(inetSocketAddress.getAddress().toString()) + ":" + inetSocketAddress.getPort();
    }
}
