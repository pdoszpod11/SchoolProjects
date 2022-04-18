using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MusicRegistry.DAL.Model
{
    /// <summary>
    /// Zenei nyilvántartó rendszer alsó, adathozzáférési rétegének legfőbb osztálya. Adatok mentését és betöltését előíró interfész.
    /// </summary>
    public interface ISongRepository
    {
        public void SaveData(Song song);
        public List<Song> LoadData();

    }
}
