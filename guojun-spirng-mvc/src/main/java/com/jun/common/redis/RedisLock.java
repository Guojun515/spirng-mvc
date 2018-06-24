package com.jun.common.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis分布式锁
 *
 * @author cxk
 */
public class RedisLock {

    private final Logger log = LoggerFactory.getLogger(RedisLock.class);

    /**
     * redis template
     */
    private RedisTemplate<String, String> redisTemplate;

    /**
     * lock datas
     */
    private List<RedisLock> redisLockList;

    /**
     * Lock key path.
     */
    private String lockKey;

    /**
     * lock time out default 1 minute (unit:seconds)
     */
    private long lockTimeout = 60;

    /**
     * lock status，default false
     */
    private volatile boolean locked = false;

    /**
     * 锁住一个数据，过期时间为默认
     * @param redisTemplate
     * @param lockKey (unit:seconds)
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
    }

    /**
     * 锁住一个数据
     * @param redisTemplate
     * @param lockKey
     * @param lockTimeout (unit:seconds)
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey, int lockTimeout) {
        this(redisTemplate, lockKey);
        this.lockTimeout=lockTimeout;
    }

    /**
     * 锁一批数据,多个key，过期时间为默认
     *
     * @param redisTemplate
     * @param lockKeyList   key的集合
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, List<String> lockKeyList) {
        //已经加了锁的RedisLock,防止其中某笔数据已经锁住,后面需要释放
        redisLockList = new ArrayList<RedisLock>();
        for (int i = 0; i < lockKeyList.size(); i++) {
            String lockKey = lockKeyList.get(i);
            RedisLock lock = new RedisLock(redisTemplate, lockKey);
            redisLockList.add(lock);
        }
    }

    /**
     * 锁一批数据,多个key
     * @param redisTemplate
     * @param lockKeyList
     * @param lockTimeout (unit:seconds)
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, List<String> lockKeyList, int lockTimeout) {
        //集合去重,否则如果出现重复数据后面的数据获取不到锁的情况
        lockKeyList = lockKeyList.stream().distinct().collect(Collectors.toList());
        //已经加了锁的RedisLock,防止其中某笔数据已经锁住,后面需要释放
        redisLockList = new ArrayList<RedisLock>();
        for (int i = 0; i < lockKeyList.size(); i++) {
            String lockKey = lockKeyList.get(i);
            RedisLock lock = new RedisLock(redisTemplate, lockKey, lockTimeout);
            redisLockList.add(lock);
        }
    }

    /**
     * 从redis获取数据
     * @param key
     * @return
     */
    private String get(final String key) {
        Object obj = null;
        try {
            obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] data = connection.get(serializer.serialize(key));
                connection.close();
                if (data == null) {
                    return null;
                }
                return serializer.deserialize(data);
            });
        } catch (Exception e) {
            log.error("get redis error, key : {}", key);
        }
        return obj != null ? obj.toString() : null;
    }

    /**
     * 从set获取数据
     * @param key
     * @param value
     * @return
     */
    private String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] data = connection.getSet(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return serializer.deserialize(data);
                }
            });
        } catch (Exception e) {
            log.error("getSet redis error, key : {}", key);
        }
        return obj != null ? obj.toString() : null;
    }

    /**
     * 删除
     * @param lockKey
     * @return
     */
    private boolean del(final String lockKey) {
        try {
            redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Long del = connection.del(serializer.serialize(lockKey));
                    connection.close();
                    return del;
                }
            });
            return true;
        } catch (Exception e) {
            log.error("del redis error, key : {}", lockKey);
        }
        return false;
    }

    /**
     * 设置过期时间
     * @param lockKey
     * @param expireTime
     * @return
     */
    private boolean expire(final String lockKey, final Long expireTime) {
        Object obj = null;
        try {
            obj =  redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    boolean bool = connection.expire(serializer.serialize(lockKey), expireTime);
                    connection.close();
                    return bool;
                }
            });
            return (Boolean)obj;
        } catch (Exception e) {
            log.error("expire redis error, key : {}", lockKey);
        }
        return false;
    }

    /**
     * reids没有值则赋值进去
     * @param key
     * @param value
     * @return
     */
    private boolean setNX(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            log.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (Boolean) obj : false;
    }

    /**
     * 加锁
     * 取到锁加锁，取不到锁就返回
     * @return
     */
    public synchronized boolean lock() {
        //锁时间
        Long lock_timeout = currtTimeForRedis() + lockTimeout * 1000  + 1;

        if (this.setNX(lockKey, String.valueOf(lock_timeout))) {
            //设置超时时间，释放内存
            this.expire(lockKey, lockTimeout);
            locked=true;
            return locked;
        } else {
            //获取redis里面的时间
            Object result = get(lockKey);
            Long currt_lock_timeout_str = result == null ? null : Long.parseLong(result.toString());
            //锁已经失效
            if (currt_lock_timeout_str != null && currt_lock_timeout_str < currtTimeForRedis()) {
                //判断是否为空，不为空时，说明已经失效，如果被其他线程设置了值，则第二个条件判断无法执行
                //获取上一个锁到期时间，并设置现在的锁到期时间
                Long old_lock_timeout_Str = Long.valueOf(getSet(lockKey, String.valueOf(lock_timeout)));
                if (old_lock_timeout_Str != null && old_lock_timeout_Str.equals(currt_lock_timeout_str)) {
                    //多线程运行时，多个线程签好都到了这里，但只有一个线程的设置值和当前值相同，它才有权利获取锁
                    //设置超时间，释放内存
                    this.expire(lockKey, lockTimeout);
                    //返回加锁时间
                    locked=true;
                    return locked;
                }
            }
        }
        return false;
    }
    
    /**
     * 解锁
     *
     */
    public synchronized void unLock() {
        //获取redis中设置的时间
        String result = get(lockKey);
        Long currt_lock_timeout_str = result == null ? null : Long.valueOf(result);
        if (currt_lock_timeout_str != null) {
            this.del(lockKey);
            locked=false;
        }
    }

    /**
     * 多服务器集群，使用下面的方法，代替System.currentTimeMillis()，获取redis时间，避免多服务的时间不一致问题！！！
     *
     * @return
     */
    public long currtTimeForRedis() {
        Object obj = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.time();
            }
        });
        return obj == null ? -1 : Long.parseLong(obj.toString());
    }


    /**
     * 锁List
     *
     * @return
     */
    public boolean lockList() {
        if (redisLockList == null || redisLockList.isEmpty()) {
            //change by shencai.li@hand-china.com
            //当列表为空时应返回true
            return true;
        }
        //临时存放lock,方便后面锁有冲突的释放
        List<RedisLock> redisLockListTemp = new ArrayList<RedisLock>();
        for (int i = 0; i < redisLockList.size(); i++) {
            RedisLock redisLock = redisLockList.get(i);
            if (redisLock.lock()) {
                redisLockListTemp.add(redisLock);
            } else {
                for (int j = 0; j < redisLockListTemp.size(); j++) {
                    //获取锁失败,前面锁住的数据需要释放
                    RedisLock redisLockUnlock = redisLockListTemp.get(j);
                    redisLockUnlock.unLock();
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 解锁list
     */
    public void unLockList() {
        if (redisLockList != null) {
            for (int i = 0; i < redisLockList.size(); i++) {
                RedisLock redisLockUnlock = redisLockList.get(i);
                redisLockUnlock.unLock();
            }
        }
    }
}
