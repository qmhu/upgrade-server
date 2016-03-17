using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using Updater.Model;
using Updater.Net;
using Updater.Util;

namespace Updater
{
    class SelfUpgrader
    {
        private ClientProtocol clientProtocol;

        public SelfUpgrader(ClientProtocol clientProtocol)
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

            Logger.getLogger().info("begin to get upgrade info, client version : " + client_version + " module : upgrade_client");

            UpgradeInfo upgradeInfo = clientProtocol.getUpgradeInfo(client_version, "upgrade_client");
            if (upgradeInfo == null || upgradeInfo.releaseFiles == null)
            {
                Logger.getLogger().info("No upgrade happens.");
                return;
            }

            Logger.getLogger().info("create tmp folder: ./upgrade_tmp");

            if (!Directory.Exists("./upgrade_tmp"))
            {
                Directory.CreateDirectory("./upgrade_tmp");
            }

            FileStream fs = new FileStream("upgrade_done", FileMode.Create);
            StreamWriter sw = new StreamWriter(fs);

            foreach (ReleaseFile releaseFile in upgradeInfo.releaseFiles)
            {
                String destTmp = "./upgrade_tmp/" + releaseFile.name;

                Logger.getLogger().info("start to download file :" + releaseFile.ToString() + " to " + destTmp);

                clientProtocol.downloadFile(releaseFile.name, destTmp);

                sw.WriteLine(releaseFile.dest);
            }

            fs.Close();
            Logger.getLogger().info("upgrade self done");

        }
    }
}
