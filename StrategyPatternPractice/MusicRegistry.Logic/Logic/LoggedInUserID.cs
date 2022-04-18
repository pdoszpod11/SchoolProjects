using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MusicRegistry.Logic.Logic
{
    /// <summary>
    /// Singleton osztály, az aktuálisan bejelentkezett felhasználó ID-jával.
    /// </summary>
    public class LoggedInUserID
    {
        private static int instance;
        public static int GetInstance()
        {
            if (instance == 0)
            {
                instance = 123456789;
            }
            return instance;
        }

        protected LoggedInUserID() { }
    }
}
