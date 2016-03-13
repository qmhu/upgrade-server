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
           
        }
    }
}
