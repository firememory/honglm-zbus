﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using zbus;
namespace zbus
{
    class Subscriber
    {
        public static void Main(string[] args)
        {
            ConnectionConfig connCfg = new ConnectionConfig();
            connCfg.Host = "127.0.0.1";
            connCfg.Port = 15555;
            Connection conn = new Connection(connCfg);

            WorkerConfig workerCfg = new WorkerConfig();
            workerCfg.Service = "MyPubSub";
            workerCfg.Mode = WorkerConfig.MODE_PUBSUB; 

            Worker worker = new Worker(conn, workerCfg);
            worker.Subscribe("topic1");

            Console.WriteLine("C# Subscriber on {0}", "topic1");
            while (true)
            {
                try
                {
                    ZMsg msg = worker.Recv();
                    msg.Dump(); 
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    break;
                }
            }
            conn.Destroy();
            worker.Destroy();
        } 
    }
}
