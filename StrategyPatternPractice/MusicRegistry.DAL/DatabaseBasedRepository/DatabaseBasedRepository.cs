using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MusicRegistry.DAL.Model;

namespace MusicRegistry.DAL.DatabaseBasedRepository
{
    /// <summary>
    /// Adatbázis alapú tárolást valósít(ana) meg. Nincs implementálva.
    /// </summary>
    public class DatabaseBasedRepository : ISongRepository
    {
        public List<Song> LoadData() => new List<Song>() { new Song("Title from database..", new List<string>() { "Author1 from database..", "Author2 from database.." }) };
        
        public void SaveData(Song song) 
        {
            //saving Song to database..
            throw new NotImplementedException();
        }
    }
}
