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
    Map<String, Object> memory = new ConcurrentHashMap<>();

    Map<String, Command> commands = new CaseInsensitiveMap();

    String password;

    boolean requirepass;

    boolean authenticated;

    public JRedisHandler(String password) {
        this.password = password;
        if (this.password != null) {
            this.requirepass = true;
        }

        commands.put("AUTH", new AuthCommand(password));
        commands.put("EXISTS", new ExistsCommand(memory));
        commands.put("DEL", new DelCommand(memory));
        commands.put("INCR", new IncrCommand(memory));
        commands.put("PING", new PingCommand());
        commands.put("GET", new GetCommand(memory));
        commands.put("SET", new SetCommand(memory));
        commands.put("LPUSH", new LPushCommand(memory));
        commands.put("RPUSH", new RPushCommand(memory));
        commands.put("LPOP", new LPopCommand(memory));
        commands.put("RPOP", new RPopCommand(memory));
        commands.put("KEYS", new KeysCommand(memory));
        commands.put("COMMAND", new CmdCommand(commands));
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("messageReceived:" + message);
        List<String> list = (List<String>) message;
        Command command = commands.get(list.get(0));
        if (command == null) {
            session.write(new ErrorReply("ERR unknown command '" + list.get(0) + "'"));
            return;
        }
        if (requirepass && session.getAttribute("authenticated") == null && command.getClass() != AuthCommand.class) {
            session.write(new ErrorReply("NOAUTH Authentication required."));
            return;
        }
        List<String> parameters = list.subList(1, list.size());
        session.write(command.eval(parameters, session));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println(cause);
    }
}