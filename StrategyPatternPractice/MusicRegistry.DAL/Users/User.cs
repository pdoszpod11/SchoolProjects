using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MusicRegistry.DAL.User
{
    /// <summary>
    /// Felhasználók osztálya.
    /// </summary>
    public class User : IUserAuthentication
    {

        private Dictionary<int, string> AllUserInfo;

        public User(string username, int id)
        {
            AllUserInfo = new Dictionary<int, string>();
            AllUserInfo.Add(id, username);

        }

        public string GetUsername(int id)
        {
            return AllUserInfo.GetValueOrDefault(id);
        }
    }
}
