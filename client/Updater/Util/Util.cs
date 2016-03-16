using System;
using System.Collections.Generic;
using System.IO;
using System.Security.Cryptography;
using System.Text;

namespace Updater.Util
{
    class Util
    {
        public static String VERSION_FILE = "version";

        public static String getVersion()
        {
            StreamReader objReader = null;
            try
            {
                objReader = new StreamReader(VERSION_FILE);
                return objReader.ReadLine().Trim();
            }catch (Exception ex)
            {
                Logger.getLogger().error("get version failed :", ex);
                return null;
            }
            finally
            {
                if (objReader != null)
                {
                    objReader.Close();
                }          
            }

        }

        public static void setVersion(String version)
        {
            FileStream fs = new FileStream(VERSION_FILE, FileMode.Create);
            StreamWriter sw = new StreamWriter(fs);

            sw.WriteLine(version);
            sw.Flush();
            sw.Close();
            fs.Close();
        }

        public static void createDirForFile(String path)
        {
            String[] pathArray = path.Split('/');
            String dirPath = "./";

            for (int i= 0;i < pathArray.Length - 1; i++)
            {
                dirPath = dirPath + pathArray[i];
                if (!Directory.Exists(dirPath))
                {
                    Directory.CreateDirectory(dirPath);
                }
                dirPath += "/";
            }

        }


        public static String getMd5(String filename)
        {
            using (var md5 = MD5.Create())
            {
                using (var stream = File.OpenRead(filename))
                {
                    return Encoding.Default.GetString(md5.ComputeHash(stream));
                }
            }
        }

    }
}
