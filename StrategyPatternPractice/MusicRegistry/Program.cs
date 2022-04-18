using MusicRegistry.DAL.MemoryBasedRepository;
using MusicRegistry.DAL.Model;
using MusicRegistry.DAL.User;
using MusicRegistry.UI;
using MusicRegistry.Logic.Logic;
using System;
using System.Collections.Generic;
using System.Linq;

namespace MusicRegistry
{
    class Program
    {
        static void Main(string[] args)
        {
            User user = new User("username1", 123456789);

            UserInterface ui = new UserInterface(user, new SongService(new MemoryBasedSongRepository()));

            ui.AddSongToRepo(user, new Song("Für Elise", new List<string>() { "Beethoven" }), ui.SongService);
            ui.AddSongToRepo(user, new Song("River Flows In You", new List<string>() { "Yiruma" }), ui.SongService);
            ui.AddSongToRepo(user, new Song("Ayo Technology", new List<string>() { "Justin Timberlake", "50 Cent" }), ui.SongService);
            ui.AddSongToRepo(user, new Song("First Step", new List<string>() { "Hans Zimmer" }), ui.SongService);
            ui.AddSongToRepo(user, new Song("Io Ti Amo", new List<string>() { }), ui.SongService); //Ennek a felvétele hibaüzenetet fog dobni, mert üres a szerzők listája.

            ui.RemoveAuthorFromRepo(user, ui.SongService.SearchSongInRepository("Ayo Technology"), "50 Cent", ui.SongService);
            


        }
    }
}
