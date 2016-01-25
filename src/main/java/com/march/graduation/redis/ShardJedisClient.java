package com.march.graduation.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShardJedisClient implements JedisCommands {

    private static final Logger logger = LoggerFactory.getLogger(ShardJedisClient.class);

    private ShardedJedisPool jedisPool;

    /**
     * @param jedisPool
     */
    public ShardJedisClient(ShardedJedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public ShardJedisClient(JedisPoolConfig config, List<JedisShardInfo> shards) {
        this.jedisPool = new ShardedJedisPool(config, shards);
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#set(java.lang.String, java.lang.String)
     */
    public String set(String key, String value) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#get(java.lang.String)
     */
    public String get(String key) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#exists(java.lang.String)
     */
    public Boolean exists(String key) {
        Boolean result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#type(java.lang.String)
     */
    public String type(String key) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.type(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#expire(java.lang.String, int)
     */
    public Long expire(String key, int seconds) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#expireAt(java.lang.String, long)
     */
    public Long expireAt(String key, long unixTime) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expireAt(key, unixTime);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#ttl(java.lang.String)
     */
    public Long ttl(String key) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.ttl(key);
        } catch (JedisConnectionException connEx) {
            logger.error(connEx.getMessage(), connEx);
            logger.error(connEx.getMessage(), connEx);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#setbit(java.lang.String, long, boolean)
     */
    public Boolean setbit(String key, long offset, boolean value) {
        Boolean result = null;

        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setbit(key, offset, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#getbit(java.lang.String, long)
     */
    public Boolean getbit(String key, long offset) {
        Boolean result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.getbit(key, offset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#setrange(java.lang.String, long, java.lang.String)
     */
    public Long setrange(String key, long offset, String value) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setrange(key, offset, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#getrange(java.lang.String, long, long)
     */
    public String getrange(String key, long startOffset, long endOffset) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#getSet(java.lang.String, java.lang.String)
     */
    public String getSet(String key, String value) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.getSet(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#setnx(java.lang.String, java.lang.String)
     */
    public Long setnx(String key, String value) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#setex(java.lang.String, int, java.lang.String)
     */
    public String setex(String key, int seconds, String value) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#decrBy(java.lang.String, long)
     */
    public Long decrBy(String key, long integer) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.decrBy(key, integer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#decr(java.lang.String)
     */
    public Long decr(String key) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.decr(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#incrBy(java.lang.String, long)
     */
    public Long incrBy(String key, long integer) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.incrBy(key, integer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#incr(java.lang.String)
     */
    public Long incr(String key) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.incr(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#append(java.lang.String, java.lang.String)
     */
    public Long append(String key, String value) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.append(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#substr(java.lang.String, int, int)
     */
    public String substr(String key, int start, int end) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.substr(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hset(java.lang.String, java.lang.String, java.lang.String)
     */
    public Long hset(String key, String field, String value) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hget(java.lang.String, java.lang.String)
     */
    public String hget(String key, String field) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hget(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hsetnx(java.lang.String, java.lang.String, java.lang.String)
     */
    public Long hsetnx(String key, String field, String value) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hmset(java.lang.String, java.util.Map)
     */
    public String hmset(String key, Map<String, String> hash) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hmset(key, hash);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hmget(java.lang.String, java.lang.String[])
     */
    public List<String> hmget(String key, String... fields) {
        List<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hmget(key, fields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hincrBy(java.lang.String, java.lang.String, long)
     */
    public Long hincrBy(String key, String field, long value) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hexists(java.lang.String, java.lang.String)
     */
    public Boolean hexists(String key, String field) {
        Boolean result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hexists(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hdel(java.lang.String, java.lang.String[])
     */
    public Long hdel(String key, String... field) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hdel(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hlen(java.lang.String)
     */
    public Long hlen(String key) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hlen(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hkeys(java.lang.String)
     */
    public Set<String> hkeys(String key) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hkeys(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hvals(java.lang.String)
     */
    public List<String> hvals(String key) {
        List<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hvals(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#hgetAll(java.lang.String)
     */
    public Map<String, String> hgetAll(String key) {
        Map<String, String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#rpush(java.lang.String, java.lang.String[])
     */
    public Long rpush(String key, String... string) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.rpush(key, string);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#lpush(java.lang.String, java.lang.String[])
     */
    public Long lpush(String key, String... string) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.lpush(key, string);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#llen(java.lang.String)
     */
    public Long llen(String key) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.llen(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#lrange(java.lang.String, long, long)
     */
    public List<String> lrange(String key, long start, long end) {
        List<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#ltrim(java.lang.String, long, long)
     */
    public String ltrim(String key, long start, long end) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.ltrim(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#lindex(java.lang.String, long)
     */
    public String lindex(String key, long index) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.lindex(key, index);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#lset(java.lang.String, long, java.lang.String)
     */
    public String lset(String key, long index, String value) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.lset(key, index, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#lrem(java.lang.String, long, java.lang.String)
     */
    public Long lrem(String key, long count, String value) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.lrem(key, count, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#lpop(java.lang.String)
     */
    public String lpop(String key) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.lpop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#rpop(java.lang.String)
     */
    public String rpop(String key) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.rpop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#sadd(java.lang.String, java.lang.String[])
     */
    public Long sadd(String key, String... member) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.sadd(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#smembers(java.lang.String)
     */
    public Set<String> smembers(String key) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.smembers(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#srem(java.lang.String, java.lang.String[])
     */
    public Long srem(String key, String... member) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.srem(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#spop(java.lang.String)
     */
    public String spop(String key) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.spop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#scard(java.lang.String)
     */
    public Long scard(String key) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.scard(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#sismember(java.lang.String, java.lang.String)
     */
    public Boolean sismember(String key, String member) {
        Boolean result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.sismember(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#srandmember(java.lang.String)
     */
    public String srandmember(String key) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.srandmember(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zadd(java.lang.String, double, java.lang.String)
     */
    public Long zadd(String key, double score, String member) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zadd(key, score, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zadd(java.lang.String, java.util.Map)
     */
    public Long zadd(String key, Map<Double, String> scoreMembers) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zadd(key, scoreMembers);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrange(java.lang.String, long, long)
     */
    public Set<String> zrange(String key, long start, long end) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrange(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrem(java.lang.String, java.lang.String[])
     */
    public Long zrem(String key, String... member) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrem(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zincrby(java.lang.String, double, java.lang.String)
     */
    public Double zincrby(String key, double score, String member) {
        Double result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zincrby(key, score, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrank(java.lang.String, java.lang.String)
     */
    public Long zrank(String key, String member) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrank(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrank(java.lang.String, java.lang.String)
     */
    public Long zrevrank(String key, String member) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrevrank(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrange(java.lang.String, long, long)
     */
    public Set<String> zrevrange(String key, long start, long end) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrangeWithScores(java.lang.String, long, long)
     */
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        Set<Tuple> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeWithScores(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrangeWithScores(java.lang.String, long, long)
     */
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        Set<Tuple> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrevrangeWithScores(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zcard(java.lang.String)
     */
    public Long zcard(String key) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zcard(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zscore(java.lang.String, java.lang.String)
     */
    public Double zscore(String key, String member) {
        Double result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zscore(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#sort(java.lang.String)
     */
    public List<String> sort(String key) {
        List<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.sort(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#sort(java.lang.String, redis.clients.jedis.SortingParams)
     */
    public List<String> sort(String key, SortingParams sortingParameters) {
        List<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.sort(key, sortingParameters);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zcount(java.lang.String, double, double)
     */
    public Long zcount(String key, double min, double max) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zcount(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zcount(java.lang.String, java.lang.String, java.lang.String)
     */
    public Long zcount(String key, String min, String max) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zcount(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, double, double)
     */
    public Set<String> zrangeByScore(String key, double min, double max) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, java.lang.String, java.lang.String)
     */
    public Set<String> zrangeByScore(String key, String min, String max) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, double, double)
     */
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, double, double, int, int)
     */
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, java.lang.String, java.lang.String)
     */
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, java.lang.String, java.lang.String, int, int)
     */
    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, double, double, int, int)
     */
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrevrangeByScore(key, max, min, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, double, double)
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        Set<Tuple> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, double, double)
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        Set<Tuple> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, double, double, int, int)
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        Set<Tuple> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, java.lang.String, java.lang.String, int, int)
     */
    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        Set<String> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrevrangeByScore(key, max, min, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String)
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        Set<Tuple> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScoreWithScores(key, max, min);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String)
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        Set<Tuple> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScoreWithScores(key, max, min);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String, int, int)
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        Set<Tuple> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScoreWithScores(key, max, min, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, double, double, int, int)
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        Set<Tuple> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScoreWithScores(key, max, min, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String, int, int)
     */
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        Set<Tuple> result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zrangeByScoreWithScores(key, max, min, offset, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zremrangeByRank(java.lang.String, long, long)
     */
    public Long zremrangeByRank(String key, long start, long end) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zremrangeByScore(java.lang.String, double, double)
     */
    public Long zremrangeByScore(String key, double start, double end) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#zremrangeByScore(java.lang.String, java.lang.String, java.lang.String)
     */
    public Long zremrangeByScore(String key, String start, String end) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#linsert(java.lang.String, redis.clients.jedis.BinaryClient.LIST_POSITION, java.lang.String, java.lang.String)
     */
    public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.linsert(key, where, pivot, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#lpushx(java.lang.String, java.lang.String)
     */
    public Long lpushx(String key, String string) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.lpushx(key, string);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see redis.clients.jedis.JedisCommands#rpushx(java.lang.String, java.lang.String)
     */
    public Long rpushx(String key, String string) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.rpushx(key, string);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /**
     * 删除记录
     * 
     * @param key
     * @return
     */
    public Long del(String key) {
        Long result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.del(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    public String watch(String key) {
        String result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.getShard(key).watch(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    public Transaction multi(String key) {
        Transaction result = null;
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.getShard(key).multi();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                try {
                    jedisPool.returnResource(jedis);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return result;
    }

}
