package org.seeking.jredis;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.command.*;
import org.seeking.jredis.reply.ErrorReply;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JRedisHandler extends IoHandlerAdapter {

    static Map<String, Object> memory = new ConcurrentHashMap<>();

    static Map<String, Command> commands = new CaseInsensitiveMap() {{
        put("AUTH", new AuthCommand());
        put("EXISTS", new ExistsCommand(memory));
        put("DEL", new DelCommand(memory));
        put("INCR", new IncrCommand(memory));
        put("PING", new PingCommand());
        put("GET", new GetCommand(memory));
        put("SET", new SetCommand(memory));
        put("LPUSH", new LPushCommand(memory));
        put("RPOP", new RPopCommand(memory));
        put("COMMAND", new CmdCommand(commands));
    }};

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("messageReceived:" + message);
        List<String> list = (List<String>) message;
        Command command = commands.get(list.get(0));
        if (command == null) {
            session.write(new ErrorReply("ERR unknown command '" + list.get(0) + "'"));
            return;
        }
        List<String> parameters = list.subList(1, list.size());
        session.write(command.eval(parameters));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}