package com.wumin.boot152.common.redis.datesource.imp;


import com.wumin.boot152.common.redis.datesource.RedisDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Collection;
import java.util.Iterator;

/**
 * redis资源库实现类,获取redis连接
 * @author Administrator
 *
 */
@Repository
public class RedisDataSourceImp implements RedisDataSource {

	private final Logger log = LoggerFactory.getLogger(RedisDataSourceImp.class);

	@Autowired
	private ShardedJedisPool shardedJedisPool;

	@Override
	public ShardedJedis getRedisClient() {
		try {
			ShardedJedis shardJedis = shardedJedisPool.getResource();
			return shardJedis;
		} catch (Exception e) {
			log.error("getRedisClent error", e);
		}
		return null;
	}

	/**
	 * 指定DB
	 * 
	 * @param db
	 * @return
	 */
	public ShardedJedis getRedisClient(int db) {
		try {
			ShardedJedis shardJedis = shardedJedisPool.getResource();
			Collection<Jedis> collection = shardJedis.getAllShards();
			Iterator<Jedis> jedis = collection.iterator();
			while (jedis.hasNext()) {
				jedis.next().select(db);
			}
			return shardJedis;
		} catch (Exception e) {
			log.error("getRedisClent error", e);
		}
		return null;
	}

	@Override
	public void returnResource(ShardedJedis shardedJedis) {
		shardedJedisPool.returnResource(shardedJedis);
	}

	/**
	 * false:利用连接池;true:销毁连接池
	 */
	@Override
	public void returnResource(ShardedJedis shardedJedis, boolean broken) {
		if (broken) {
			// 销毁对象
			shardedJedisPool.returnBrokenResource(shardedJedis);
		} else {
			// 还会到连接池
			shardedJedisPool.returnResource(shardedJedis);
		}
	}
}
