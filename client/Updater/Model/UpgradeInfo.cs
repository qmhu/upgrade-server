using System;
using System.Collections.Generic;
using System.Text;

namespace Updater.Model
{
    class UpgradeInfo
    {
        public String version { get; set; }
        public List<ReleaseFile> releaseFiles { get; set; }

        public String ToString()
        {
            return "version:" + version + " " + releaseFiles.ToString();
        }
    }
}
