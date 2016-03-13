using System;
using System.Collections.Generic;
using System.Text;

namespace Updater.Model
{
    class ReleaseFile
    {
        public String name { get; set; }
        public String src { get; set; }
        public String dest { get; set; }
        public String type { get; set; }

        public String toString()
        {
            return "name:" + name + " src:" + src + " dest:" + dest + " type:" + type;
        }
    }
}
