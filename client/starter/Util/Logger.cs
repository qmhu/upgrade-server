using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace Starter.Util
{
    class Logger
    {
        private static Logger instance = null;

        private StreamWriter sw = null;

        private Logger()
        {
            FileStream fs = new FileStream("Updater.log", FileMode.Append);
            sw = new StreamWriter(fs);
        }

        public static Logger getLogger()
        {
            if (instance == null)
            {
                instance = new Logger();
            }

            return instance;

        }

        private void writeLog(String level, String msg)
        {
            sw.WriteLine(level + " " + msg);
            sw.Flush();
        }
    
        public void info(String msg)
        {
            writeLog("INFO", msg);
        }

        public void error(String msg)
        {
            writeLog("ERROR", msg);
        }

        public void error(String msg, Exception ex)
        {
            writeLog("ERROR", msg + " TRACE:" + ex.StackTrace);
        }

    }
}
