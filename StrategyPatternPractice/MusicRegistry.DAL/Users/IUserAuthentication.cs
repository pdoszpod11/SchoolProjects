
namespace MusicRegistry.DAL.User
{
    /// <summary>
    /// Felhasználói azonosítást előíró interfész.
    /// </summary>
    public interface IUserAuthentication
    {

        public string GetUsername(int id);
    }
}