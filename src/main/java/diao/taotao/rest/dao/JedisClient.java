package diao.taotao.rest.dao;

public interface JedisClient {
    public String get(String key);

    public String set(String key, String value);

    public String hget(String hkey, String key);

    public long hset(String hkey, String key, String value);

    public long incr(String key);

    public long expire(String key, int second);

    public long ttl(String key);

    public int hdel(String hkey, String string);
    
    public int del(String string);
    
}
