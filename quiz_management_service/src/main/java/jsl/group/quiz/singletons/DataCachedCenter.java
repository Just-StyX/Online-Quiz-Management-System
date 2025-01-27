package jsl.group.quiz.singletons;

import jsl.group.quiz.cache.DataCenter;

public enum DataCachedCenter {
    INSTANCE;

    private final DataCenter dataCenter;
    DataCachedCenter() { dataCenter = new DataCenter(); }

    public DataCenter getDataCenter() { return dataCenter; }
}
