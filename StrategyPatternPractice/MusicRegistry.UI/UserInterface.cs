using MusicRegistry.DAL.Model;
using MusicRegistry.DAL.User;
using MusicRegistry.Logic.Logic;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MusicRegistry.UI
{
    /// <summary>
    /// Egyszerű konzolra író osztály. A zenei nyilvántartó rendszer felső, felhasználói felület osztálya.
    /// </summary>
    public class UserInterface
    {
        private User user;
        public SongService SongService { get; private set; }
        public User LoggedInUser 
        {
            get => user;
            private set { }
        }

        public UserInterface(User user, SongService songService)
        {
            this.user = user;

            SongService = songService;
        }


        private void WriteAllSongsToConsole(SongService songs)
        {
            if (songs != null)
            {
                foreach (Song s in songs.ListAllSongs())
                {
                    Console.WriteLine(s.ToString());
                }

            }


        }

        public void AddSongToRepo(User user, Song song, SongService songService)
        {

            try
            {
                songService.Add(user, song);
            }
            catch (NullReferenceException e)
            {
                Console.Error.WriteLine(e.Message);
                return;
            }
            WriteAllSongsToConsole(songService);
        }

        public void RemoveAuthorFromRepo(User user, Song song, string author, SongService songService)
        {
            try
            {
                songService.RemoveAuthorFromSong(user, song, author);
            }
            catch (InvalidOperationException e)
            {
                Console.Error.WriteLine(e.Message);
                return;
            }
            
            WriteAllSongsToConsole(songService);
        }


    }
}
