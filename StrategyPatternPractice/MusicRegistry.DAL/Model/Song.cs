using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MusicRegistry.DAL.Model
{
    /// <summary>
    /// A nyilvántartó rendszer nyilvántartott objektumainak (zenék) osztálya.
    /// </summary>
    public class Song
    {

        public Song(string title, List<string> authors )
        {
            Title = title;
            Authors = authors;

        }
        public string Title { get; set; }

        public List<string> Authors { get; set; }

        public override string ToString()
        {
            StringBuilder allInfo = new StringBuilder();
            allInfo.Append($"Title: {Title} Author(s): ");

            for (int i=0; i<Authors.Count; i++)
            {
                allInfo.Append(Authors[i]);
                if (i < Authors.Count - 1)
                {
                    allInfo.Append(", ");
                }
            }

            return allInfo.ToString();
        }

    }
}
