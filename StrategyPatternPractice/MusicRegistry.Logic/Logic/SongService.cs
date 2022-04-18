using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MusicRegistry.DAL;
using MusicRegistry.DAL.Model;
using MusicRegistry.DAL.User;

namespace MusicRegistry.Logic.Logic
{
    /// <summary>
    /// A zenei nyilvántartó rendszer középső, alapvető logikáját megvalósító rétegének legfőbb osztálya. Az alkalmazás alapvető működése, adatok manipulációja itt valósul meg.
    /// </summary>
    public class SongService
    {

        public ISongRepository SongRepository { get; set; }

        public SongService(ISongRepository songRepository)
        {
            SongRepository = songRepository;
        }

        public void Add(User user, Song song)
        {
            SongTitleValidation(song.Title);
            SongAuthorValidation(song.Authors);
            SongRepository.SaveData(song);
            DataManipulationFeedback(user, song, "Added Song");

        }

        public void Remove(User user, Song song)
        {
            SongRepository.LoadData().Remove(SearchSongInRepository(song.Title));
            DataManipulationFeedback(user, song, "Removed Song");
        }

        public void RemoveAuthorFromSong(User user, Song song, string author)
        {
            if (song.Authors.Count == 1) 
            { 
                throw new InvalidOperationException($"Cannot delete Author! '{song.Title}' has only 1 Author!"); 
                
            }

            for (int i=0; i<song.Authors.Count; i++)
            {
                if (song.Authors.ElementAt(i).ToLower().Equals(author.ToLower()))
                {
                    song.Authors.RemoveAt(i);
                }
            }
            DataManipulationFeedback(user, song, $"Removed Author: {author} from Song");

        }


        public IEnumerable<Song> ListAllSongs()
        {
            foreach (Song s in SongRepository.LoadData())
            {
                yield return s;
            }
        }

        private void SongTitleValidation(string title) 
        {
            if (title == null || title.Length < 2)
            {
                throw new NullReferenceException("Invalid song name! Song cannot be less than 2 characters long!");
            }

        }

        private void SongAuthorValidation(List<string> authors)
        {
            if (authors == null || authors.Count < 1)
            {
                throw new NullReferenceException("Invalid author list! Author list cannot be null!");

            }

            foreach (string author in authors)
            {
                if (author == null)
                {
                    throw new NullReferenceException("Invalid author list! Author cannot be null!");
                }
            }
        }

        public Song SearchSongInRepository(string songTitle)
        {
            foreach (Song s in SongRepository.LoadData())
            {
                if (songTitle.ToLower().Equals(s.Title.ToLower()))
                {
                    return s;
                }
            }
            return null;
        }

        private void DataManipulationFeedback(User user, Song song, string manipulationType)
        {
            
            Console.WriteLine($"Username: {user.GetUsername(LoggedInUserID.GetInstance())} | Manipulation: {manipulationType} | Song title: {song.Title}");
        }


    }
}
