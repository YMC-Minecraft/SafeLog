package moe.ymc.safelog;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class SanitizedInetSocketAddress extends InetSocketAddress {
    public SanitizedInetSocketAddress(int port) {
        super(port);
    }

    public SanitizedInetSocketAddress(InetAddress addr, int port) {
        super(addr, port);
    }

    public SanitizedInetSocketAddress(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public String toString() {
        return Safelog.sanitize(this);
    }
}
