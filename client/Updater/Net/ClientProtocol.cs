using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using Updater.Model;
using Updater.Util;

namespace Updater.Net
{
    class ClientProtocol
    {
        private String serverUrl;

        public ClientProtocol(String serverUrl)
        {
            this.serverUrl = serverUrl;
        }

        public UpgradeInfo getUpgradeInfo(String versoin, String module)
        {
            String infoUrl = String.Format("{0}/upgrade/info?clientVersion={1}&module={2}", serverUrl, versoin, module);
            String responseContent = HttpClient.SendGet(infoUrl);

            UpgradeInfo upgradeInfo = JavaScriptConvert.DeserializeObject<UpgradeInfo>(responseContent);
            return upgradeInfo;
        }

        public void downloadFile(String filename, String dest)
        {
            String downloadUrl = String.Format("{0}/upgrade/download?name={1}", serverUrl, filename);
            Stream responseStream = HttpClient.download(downloadUrl);

            byte[] buff = new byte[4096];
            Stream fs = new FileStream(dest, FileMode.Create);
            int r = 0;

            while ((r = responseStream.Read(buff, 0, buff.Length)) > 0)
            {
                fs.Write(buff, 0, r);
            }

            fs.Flush();
            fs.Close();
        }

    }
}
