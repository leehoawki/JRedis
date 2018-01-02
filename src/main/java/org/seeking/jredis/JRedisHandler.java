package org.seeking.jredis;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.command.*;
import org.seeking.jredis.reply.ErrorReply;
import org.seeking.jredis.reply.StatusReply;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JRedisHandler extends IoHandlerAdapter {
    Map<String, Object> memory = new ConcurrentHashMap<>();

    Map<String, Command> commands = new CaseInsensitiveMap();

    String password;

    boolean requirepass;

    public JRedisHandler(String password) {
        this.password = password;
        if (this.password != null) {
            this.requirepass = true;
        }

        commands.put("auth", new AuthCommand(password));
        commands.put("exists", new ExistsCommand(memory));
        commands.put("del", new DelCommand(memory));
        commands.put("incr", new IncrCommand(memory));
        commands.put("ping", new PingCommand());
        commands.put("get", new GetCommand(memory));
        commands.put("set", new SetCommand(memory));
        commands.put("lpush", new LPushCommand(memory));
        commands.put("rpush", new RPushCommand(memory));
        commands.put("lpop", new LPopCommand(memory));
        commands.put("rpop", new RPopCommand(memory));
        commands.put("keys", new KeysCommand(memory));
        commands.put("command", new CmdCommand(commands));
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        List<String> list = (List<String>) message;
        if ("quit".equalsIgnoreCase(list.get(0))) {
            session.closeNow();
            session.write(new StatusReply("OK"));
            return;
        }
        Command command = commands.get(list.get(0));
        if (command == null) {
            session.write(new ErrorReply("ERR unknown command '" + list.get(0) + "'"));
            return;
        }
        List<String> parameters = list.subList(1, list.size());
        if ((command.getCommandSpec().getArity() > 0 && list.size() != command.getCommandSpec().getArity()) || list.size() < -command.getCommandSpec().getArity()) {
            System.out.println(list.size());
            System.out.println( -command.getCommandSpec().getArity());
            session.write(new ErrorReply("ERR wrong number of arguments for '" + list.get(0) + "' command"));
            return;
        }
        if (requirepass && session.getAttribute("authenticated") == null && command.getClass() != AuthCommand.class) {
            session.write(new ErrorReply("NOAUTH Authentication required."));
            return;
        }
        session.write(command.eval(parameters, session));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println(cause);
    }
}