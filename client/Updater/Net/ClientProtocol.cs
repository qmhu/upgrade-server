using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
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
            HttpWebResponse response = HttpClient.download(downloadUrl);

            byte[] buff = new byte[4096];
            Stream fs = new FileStream(dest, FileMode.Create);
            int r = 0;

            while ((r = response.GetResponseStream().Read(buff, 0, buff.Length)) > 0)
            {
                fs.Write(buff, 0, r);
            }

            fs.Flush();
            fs.Close();

            String md5File = Util.Util.getMd5(dest);
            String md5Header = response.GetResponseHeader("Content-MD5");

            response.Close();
            if (!md5File.Equals(md5Header))
            {
                throw new Exception("MD5 not match:" + md5Header + " " + md5File);
            }

            return;

        }

    }
}
