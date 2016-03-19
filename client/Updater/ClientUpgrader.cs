using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using Updater.Model;
using Updater.Net;
using Updater.Util;

namespace Updater
{
    class ClientUpgrader
    {
        private ClientProtocol clientProtocol;

        public ClientUpgrader(ClientProtocol clientProtocol)
        {
            this.clientProtocol = clientProtocol;
        }

        public void execute()
        {
            String client_version = Util.Util.getVersion();
            if (client_version == null)
            {
                Logger.getLogger().info("not found version file, set version to 1.0.0");
                Util.Util.setVersion("1.0.0");
                client_version = "1.0.0";
            }

            Logger.getLogger().info("begin to get upgrade info, client version : " + client_version + " module : client");

            UpgradeInfo upgradeInfo = clientProtocol.getUpgradeInfo(client_version, "client");
            if (upgradeInfo == null || upgradeInfo.releaseFiles == null)
            {
                Logger.getLogger().info("No upgrade happens.");
                return;
            }

            Logger.getLogger().info("create tmp folder: ./upgrade_tmp");

            if (Directory.Exists("./upgrade_tmp"))
            {
                Directory.Delete("./upgrade_tmp", true);
            }
                
            Directory.CreateDirectory("./upgrade_tmp");
            
            
            foreach (ReleaseFile releaseFile in upgradeInfo.releaseFiles)
            {
                String destTmp = "./upgrade_tmp/" + releaseFile.name;

                Logger.getLogger().info("start to download file :" + releaseFile.ToString() + " to " + destTmp);

                clientProtocol.downloadFile(releaseFile.name, destTmp);

                if (File.Exists(releaseFile.dest))
                {
                    Logger.getLogger().info("delete old file :" + releaseFile.dest);
                    File.Delete(releaseFile.dest);
                }
                else
                {
                    Logger.getLogger().info("try create folder for target file :" + releaseFile.dest);
                    Util.Util.createDirForFile(releaseFile.dest);
                }

                Logger.getLogger().info("move file :" + destTmp + " " + releaseFile.dest);
                File.Move(destTmp, releaseFile.dest);
            }

            Logger.getLogger().info("update client version :" + upgradeInfo.version);
            Util.Util.setVersion(upgradeInfo.version);
            FileStream fs = new FileStream("upgrade_done", FileMode.Create);
            fs.Close();

            Logger.getLogger().info("upgrade client done");
        }

        
    }
}
