package org.seeking.jredis;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.collections.map.HashedMap;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.seeking.jredis.command.*;
import org.seeking.jredis.io.RDB;
import org.seeking.jredis.job.ExpirationJob;
import org.seeking.jredis.job.Job;
import org.seeking.jredis.job.SnapshotJob;
import org.seeking.jredis.pubsub.ChannelManager;
import org.seeking.jredis.reply.ErrorReply;
import org.seeking.jredis.reply.StatusReply;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JRedisHandler extends IoHandlerAdapter {
    Map<String, Object> memory;

    Map<String, Command> commands = new CaseInsensitiveMap();

    String password;

    boolean requirepass;

    String filename = "dump.snapshot";

    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);

    Map<String, Job> jobs = new HashedMap();

    public JRedisHandler(String password) {
        this.password = password;
        if (this.password != null) {
            this.requirepass = true;
        }

        if (RDB.INSTANCE.exists(filename)) memory = RDB.INSTANCE.restore(filename);
        else memory = new ConcurrentHashMap<>();

        commands.put("auth", new AuthCommand(password));
        commands.put("exists", new ExistsCommand(memory));
        commands.put("expire", new ExpireCommand(memory));
        commands.put("del", new DelCommand(memory));
        commands.put("incr", new IncrCommand(memory));
        commands.put("persist", new PersistCommand(memory));
        commands.put("ping", new PingCommand());
        commands.put("get", new GetCommand(memory));
        commands.put("set", new SetCommand(memory));
        commands.put("llen", new LLenCommand(memory));
        commands.put("lpush", new LPushCommand(memory));
        commands.put("rpush", new RPushCommand(memory));
        commands.put("lpop", new LPopCommand(memory));
        commands.put("rpop", new RPopCommand(memory));
        commands.put("keys", new KeysCommand(memory));
        commands.put("command", new CmdCommand(commands));
        commands.put("save", new SaveCommand(memory, filename));
        commands.put("subscribe", new SubscribeCommand());
        commands.put("unsubscribe", new UnSubscribeCommand());
        commands.put("publish", new PublishCommand());
        commands.put("ttl", new TTLCommand(memory));
        commands.put("bgsave", new BgSaveCommand(memory, filename, Executors.newSingleThreadScheduledExecutor()));

        jobs.put("expiration", new ExpirationJob(memory));
        jobs.put("snapshot", new SnapshotJob(memory, filename));

        for (Job job : jobs.values()) {
            scheduledThreadPool.scheduleWithFixedDelay(job.getRunnable(), job.delay(), job.rate(), TimeUnit.MILLISECONDS);
        }
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
            session.write(new ErrorReply("ERR wrong number of arguments for '" + list.get(0) + "' command"));
            return;
        }
        if (requirepass && session.getAttribute("authenticated") == null && command.getClass() != AuthCommand.class) {
            session.write(new ErrorReply("NOAUTH Authentication required."));
            return;
        }
        Reply reply = command.eval(parameters, session);
        if (reply != null) session.write(reply);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println(cause);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        ChannelManager.INSTANCE.remove(session);
    }
}