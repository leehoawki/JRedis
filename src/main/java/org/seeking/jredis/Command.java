package org.seeking.jredis;

import java.util.List;

public interface Command {
    Reply eval(List<String> params);

    CommandSpec getCommandSpec();
}
