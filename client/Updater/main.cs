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

            HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create("http://www.baidu.com");

            httpWebRequest.Method = "GET";
            httpWebRequest.Timeout = 60000;

            HttpWebResponse result = (HttpWebResponse)httpWebRequest.GetResponse();
            StreamReader streamReader = new StreamReader(result.GetResponseStream());
            string responseContent = streamReader.ReadToEnd();
            streamReader.Close();

            Console.WriteLine("Content from Baidu:" + responseContent);

            String jsonString = "{'name':'somename','src':'somewhere','dest':'target','type':'file'}";
            ReleaseFile releaseFile = JavaScriptConvert.DeserializeObject<ReleaseFile>(jsonString);

            Console.WriteLine("Content JSON Object:" + releaseFile.toString());

            Console.WriteLine("Test Successful!");
            Thread.Sleep(5000);


            return;


            if (args.Length < 1)
            {
                Console.WriteLine("Usage:Updater.exe client|upgrade_client");
                System.Environment.Exit(0);
            }

            String module = args[0];

            ClientProtocol clientProtocol = new ClientProtocol("http://192.168.31.150:8080");
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
