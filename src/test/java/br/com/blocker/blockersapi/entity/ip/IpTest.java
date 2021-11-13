package br.com.blocker.blockersapi.entity.ip;

import com.google.common.net.InetAddresses;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class IpTest {

    @Test
    void ipv4Test(){
        Ipv4 i = Ipv4.builder()
                .address(-1062797052)
                .origin("IpTestV4")
                .build();
        String addr = InetAddresses.fromInteger(i.getAddress()).getHostAddress();
        Assert.assertEquals("192.167.1.4", addr);
        Assert.assertEquals("IpTestV4", i.getOrigin());
    }

    @Test
    void ipv6Test(){
        byte[] addr = Base64.decodeBase64("fe80::3c8b:1dff:fee0:96ec");
        Ipv6 i = Ipv6.builder()
                .address(addr)
                .origin("IpTestV6")
                .build();
        final String addrV6 = Base64.encodeBase64String(i.getAddress());
        Assert.assertEquals("fe803c8b1dfffee096ec", addrV6);
        Assert.assertEquals("IpTestV6", i.getOrigin());
    }
}
