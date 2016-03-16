using Starter.Util;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Threading;

namespace starter
{
    class Program
    {
        static void Main(string[] args)
        {
            String upgradeDoneString = "upgrade_done";

            Logger.getLogger().info("try to delete upgrade_done file before updater upgrade.");
            if (File.Exists(upgradeDoneString))
            {
                Logger.getLogger().info("delete upgrade_done file.");
                File.Delete(upgradeDoneString);
            }

            Logger.getLogger().info("Run: Updater.exe upgrade_client.");
            Process proUpdater = Process.Start("Updater.exe", "upgrade_client");
            proUpdater.WaitForExit();

            if (File.Exists(upgradeDoneString))
            {
                StreamReader objReader = new StreamReader(upgradeDoneString);
                String line;
                while ((line = objReader.ReadLine().Trim()) != null)
                {
                    Logger.getLogger().info("try to delete old updater file:" + line);
                    if (File.Exists(line))
                    {
                        Logger.getLogger().info("delete old updater file:" + line);
                        File.Delete(line);
                    }

                    Logger.getLogger().info("try to replace old updater file:" + line);
                    String updateDownloadFile = "./upgrade_tmp/" + line;
                    if (File.Exists(updateDownloadFile))
                    {
                        Logger.getLogger().info("replace updater file:" + updateDownloadFile + " " + line);
                        File.Move(updateDownloadFile, line);
                    }
                }

                objReader.Close();
            }

            Logger.getLogger().info("try to delete upgrade_done file before client upgrade.");
            if (File.Exists(upgradeDoneString))
            {
                Logger.getLogger().info("delete upgrade_done file.");
                File.Delete(upgradeDoneString);
            }

            Logger.getLogger().info("Run: Updater.exe client.");
            Process proClient = Process.Start("Updater.exe", "client");
            
            while(true)
            {
                if (File.Exists(upgradeDoneString))
                {
                    Logger.getLogger().info("found upgrade_done file, client upgrade finish.");
                    break;
                }

                Logger.getLogger().info("not found upgrade_done file, sleep for a while");
                Thread.Sleep(2000);
            }

            Logger.getLogger().info("Run: client.");
            //Process proSoftware = Process.Start("Updater.exe", "client");





        }
    }
}
