package org.seeking.jredis;

import java.util.List;

public class CommandSpec {
    int arity;

    List<String> flags;

    int fkial;

    int lkial;

    int stepCount;

    public CommandSpec(int arity, List<String> flags, int fkial, int lkial, int stepCount) {
        this.arity = arity;
        this.flags = flags;
        this.fkial = fkial;
        this.lkial = lkial;
        this.stepCount = stepCount;
    }

    public int getArity() {
        return arity;
    }

    public List<String> getFlags() {
        return flags;
    }

    public int getFkial() {
        return fkial;
    }

    public int getLkial() {
        return lkial;
    }

    public int getStepCount() {
        return stepCount;
    }
}
