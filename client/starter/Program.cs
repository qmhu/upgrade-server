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
            String programName = System.Configuration.ConfigurationManager.AppSettings["appname"] == null ? "RayVR.exe" : System.Configuration.ConfigurationManager.AppSettings["appname"];
            String upgradeDoneString = "upgrade_done";

            try
            {
                Logger.getLogger().info("try to delete upgrade_done file before updater upgrade.");
                if (File.Exists(upgradeDoneString))
                {
                    Logger.getLogger().info("delete upgrade_done file.");
                    File.Delete(upgradeDoneString);
                }

                Logger.getLogger().info("Run: Updater.exe upgrade_client.");

                Process proUpdater = new Process();
                proUpdater.StartInfo.FileName = "Updater.exe";
                proUpdater.StartInfo.Arguments = "upgrade_client";
                proUpdater.StartInfo.CreateNoWindow = true;
                proUpdater.StartInfo.UseShellExecute = false;
                proUpdater.Start();
                proUpdater.WaitForExit();

                if (File.Exists(upgradeDoneString))
                {
                    StreamReader objReader = new StreamReader(upgradeDoneString);
                    String line;
                    while ((line = objReader.ReadLine()) != null)
                    {
                        line = line.Trim();
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
                Process proClient = new Process();
                proClient.StartInfo.FileName = "Updater.exe";
                proClient.StartInfo.Arguments = "client";
                proClient.StartInfo.CreateNoWindow = true;
                proClient.StartInfo.UseShellExecute = false;
                proClient.Start();

                while (true)
                {
                    if (File.Exists(upgradeDoneString))
                    {
                        Logger.getLogger().info("found upgrade_done file, client upgrade finish.");
                        break;
                    }

                    if (proClient.HasExited)
                    {
                        Logger.getLogger().info("client upgrade exit.");
                        break;
                    }

                    Logger.getLogger().info("not found upgrade_done file, sleep for a while");
                    Thread.Sleep(2000);
                }

            } catch (Exception ex)
            {
                Logger.getLogger().error("something wrong happens during update:" + ex);
                Logger.getLogger().error("error trace:" + ex.StackTrace);
            }

            Logger.getLogger().info("Run: client:" + programName);
            Process proApp = new Process();
            proApp.StartInfo.FileName = programName;
            proApp.Start();

        }
    }
}
