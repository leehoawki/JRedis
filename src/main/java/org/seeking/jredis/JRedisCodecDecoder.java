package org.seeking.jredis;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

public class JRedisCodecDecoder extends CumulativeProtocolDecoder {

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        int start = in.position();
        try {
            readChar(in, '*');
            int count = readNumber(in);
            readChar(in, '\r', '\n');
            List<String> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                readChar(in, '$');
                int length = readDigit(in);
                readChar(in, '\r', '\n');
                list.add(readString(in, length));
                readChar(in, '\r', '\n');
            }
            out.write(list);
            return true;
        } catch (BufferUnderflowException ex) {
            in.position(start);
            return false;
        }
    }

    static char readChar(IoBuffer in) {
        return (char) in.get();
    }

    static void readChar(IoBuffer in, char... chs) {
        for (char expect : chs) {
            char get = readChar(in);
            if (get != expect) throw new IllegalStateException(expect + " expected, get a " + get);
        }
        return;
    }

    static String readString(IoBuffer in, int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = in.get();
        }
        return new String(bytes);
    }

    static int readDigit(IoBuffer in) {
        byte b = in.get();
        if (b <= '9' && b >= '0') {
            return b - '0';
        }
        throw new IllegalStateException("Digit expected, get a " + (char) b);
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