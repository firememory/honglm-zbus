package net.zbus.pubsub;

import net.zbus.BusClient;
import net.zbus.BusClientFactory;
import net.zbus.ConnectionConfig;
import net.zbus.Pool;
import net.zbus.PoolConfig;
import net.zbus.ZMsg;

public class Publisher {
 
	public static void main(String[] args) { 
		//1) 创建连接
		ConnectionConfig connCfg = new ConnectionConfig();
		connCfg.setHost("127.0.0.1");
		connCfg.setPort(15555); 
		BusClientFactory factory = new BusClientFactory(connCfg);
		
		
		PoolConfig config = new PoolConfig(); 
		config.setMaxActive(2); 
		Pool<BusClient> pool = new Pool<BusClient>(factory, config);
		
		BusClient client = pool.borrowResource();  
		
		//2) 组装消息（消息帧数组）
		ZMsg message = new ZMsg();
		message.pushBack("PubSub Message@"+System.currentTimeMillis()); //消息帧1 -- 字符类型
		message.pushBack(new byte[10]);     //消息帧2 -- 二进制串 
		message.pushBack("from JAVA");     //消息帧3 -- 字符串 
		
		boolean status = client.publish("MyPubSub"/*服务标识*/, ""/*安全码*/, "topic1"/*消息主题*/, message, 2500);
		
		System.out.println("Publish Status: " + status); 
		
		//4) 销毁客户端
		pool.returnResource(client);
		pool.destroy();
	}

}
