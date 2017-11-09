package org.seeking.jredis;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.util.ArrayList;
import java.util.List;

public class JRedisCodecDecoder implements ProtocolDecoder {

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        readChar(in, '*');
        int count = readNumber(in);
        readChar(in, '\r');
        readChar(in, '\n');
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            readChar(in, '$');
            int length = readDigit(in);
            readChar(in, '\r');
            readChar(in, '\n');
            list.add(readString(in, length));
            readChar(in, '\r');
            readChar(in, '\n');
        }
        out.write(list);
    }

    static char readChar(IoBuffer in) {
        if (in.hasRemaining()) return (char) in.get();
        throw new IllegalStateException("");
    }

    static void readChar(IoBuffer in, char ch) {
        if (in.hasRemaining()) {
            byte b = in.get();
            if ((char) b == ch) return;
            throw new IllegalStateException("");
        }
    }

    static String readString(IoBuffer in, int length) {
        byte[] bytes = new byte[length];
        in.get(bytes);
        return new String(bytes);
    }

    static int readDigit(IoBuffer in) {
        if (in.hasRemaining()) {
            byte b = in.get();
            if (b <= '9' && b >= '0') {
                return b - '0';
            }
        }
        throw new IllegalStateException("");
    }

    static int readNumber(IoBuffer in) {
        int re = 0;
        while (in.hasRemaining()) {
            byte b = in.get();
            if (b <= '9' && b >= '0') {
                re = (10 * re) + (b - '0');
            } else {
                int pos = in.position();
                in.position(pos - 1);
                return re;
            }
        }
        throw new IllegalStateException("");
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {

    }

    @Override
    public void dispose(IoSession session) throws Exception {

    }
}