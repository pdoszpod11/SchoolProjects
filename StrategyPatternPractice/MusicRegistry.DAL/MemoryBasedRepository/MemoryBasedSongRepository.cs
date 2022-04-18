using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MusicRegistry.DAL.Model;

namespace MusicRegistry.DAL.MemoryBasedRepository
{
    /// <summary>
    /// Memória alapú tárolást valósítja meg, egy Song-okat tároló listában.
    /// </summary>
    public class MemoryBasedSongRepository : ISongRepository
    {

        private List<Song> SongList;

        public MemoryBasedSongRepository()
        {
            SongList = new List<Song>();
        }

        public List<Song> LoadData()
        {
            return SongList;
        }

        public void SaveData(Song song)
        {
            SongList.Add(song);
        }
    }
}
