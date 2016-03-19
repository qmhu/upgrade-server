using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using System.Threading;
using Updater.Model;
using Updater.Net;

namespace Updater
{
    class Program
    {

        static void Main(string[] args)
        {

            if (args.Length < 1)
            {
                Console.WriteLine("Usage:Updater.exe client|upgrade_client");
                System.Environment.Exit(0);
            }

            String module = args[0];

            ClientProtocol clientProtocol = new ClientProtocol(System.Configuration.ConfigurationManager.AppSettings["serverurl"]);
            if (module == "client")
            {
                ClientUpgrader upgrader = new ClientUpgrader(clientProtocol);
                upgrader.execute();
            }
            else
            {
                SelfUpgrader upgrade = new SelfUpgrader(clientProtocol);
                upgrade.execute();
            }
            
        }
    }
}
