package com.hll_sc_app.base;

public class UseCaseException extends RuntimeException {
    private final Level mLevel;
    private Object mTag;
    private String mCode;
    private String mMsg;


    public UseCaseException(String code, String msg) {
        this(Level.FAIL, code, msg);
    }

    public UseCaseException(Level level, String code, String msg) {
        this(level, code, msg, null);
    }

    public UseCaseException(Level level, String code, String msg, Throwable e) {
        super(msg, e);
        this.mLevel = level;
        this.mCode = code;
        this.mMsg = msg;
    }

    public UseCaseException(Level level, String msg) {
        this(level, null, msg, null);
    }

    public UseCaseException(Throwable e) {
        this(Level.ERROR, "001", "执行出错", e);
    }

    public UseCaseException(String code, String msg, Throwable e) {
        this(Level.ERROR, code, msg, e);
    }

    public Object getTag() {
        return mTag;
    }

    public UseCaseException setTag(Object mTag) {
        this.mTag = mTag;
        return this;
    }

    public Level getLevel() {
        return mLevel;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        this.mMsg = msg;
    }

    @Override
    public String toString() {
        return "UseCaseException{" +
            "level=" + mLevel +
            ", code='" + mCode + '\'' +
            ", msg='" + mMsg + '\'' +
            '}';
    }

    public enum Level {
        FAIL, ERROR, NET
    }
}
