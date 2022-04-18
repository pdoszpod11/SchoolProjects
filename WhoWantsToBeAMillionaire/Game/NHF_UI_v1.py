import sys
import pygame
import pygame.gfxdraw

class Rubrika:
    def __init__(self, x, y, a, b, szin, szoveg):
        self.x = x
        self.y = y
        self.a = a
        self.b = b
        self.szin = szin
        self.szoveg = szoveg
        
    def negyzet(self, kepernyo):
        return pygame.Rect(self.x, self.y, self.a, self.b)

def logo(kepernyo):
    logo = pygame.image.load("mil_logo.gif")
    kepernyo.blit(logo, (435, 60))

def inditas_negyzet():
    return pygame.Rect(412, 310, 200, 60)

def dicsoseg_negyzet():
    return pygame.Rect(412, 380, 200, 60)

def menu_kilepes_negyzet():
    return pygame.Rect(412, 450, 200, 60)

def fomenu(kepernyo):
    kepernyo.fill((0, 0, 60))
    logo(kepernyo)

    fomenu_elemek = [Rubrika(412, 310, 200, 60, pygame.Color(255, 255, 255), "Játék indítása"),
                     Rubrika(412, 380, 200, 60, pygame.Color(255, 255, 255), "Dicsőséglista"),
                     Rubrika(412, 450, 200, 60, pygame.Color(255, 255, 255), "Kilépés")]

    for f in fomenu_elemek:
        negyzet_rajzol(kepernyo, f, 0)
        szoveg_negyzetbe(kepernyo, f)

    pygame.display.update()

def segitseg_rubrika():
    return [Rubrika(12, 306, 75, 60, pygame.Color(255, 255, 255), ""),
            Rubrika(122, 306, 75, 60, pygame.Color(255, 255, 255), "")]

def segitseg_negyzet():
    '''visszaadja a segítségek dobozainak koordinátáit a kattintáshoz'''
    return [pygame.Rect(12, 306, 75, 60), pygame.Rect(122, 306, 75, 60)]

def segitseg_logo(kepernyo, hely, fajl):
    fajlbol = pygame.image.load(fajl)
    kepernyo.blit(fajlbol, (hely.x + 5, hely.y + 5))

def negyzet_rajzol(kepernyo, rubrika, teli):
    if teli == 1:
        pygame.gfxdraw.box(kepernyo, rubrika.negyzet(kepernyo), rubrika.szin)
        pygame.gfxdraw.rectangle(kepernyo, pygame.Rect(rubrika.x, rubrika.y, rubrika.a+2, rubrika.b+2), pygame.Color(255, 255, 255))
        
    elif teli == 0:
        pygame.gfxdraw.rectangle(kepernyo, rubrika.negyzet(kepernyo), rubrika.szin)


def szoveg_negyzetbe(kepernyo, forras):
    betustilus = pygame.font.SysFont("Bahnschrift SemiLight", 24)
    szoveg = betustilus.render(forras.szoveg, 1, (255, 255, 255))
    kepernyo.blit(szoveg, (forras.x + 10, forras.y + (forras.b)/2))


def valasz_negyzet():
    return [
            pygame.Rect(12, 500, 500, 100),
            pygame.Rect(12+500, 500, 500, 100),
            pygame.Rect(12, 500+100, 500, 100),
            pygame.Rect(12+500, 500+100, 500, 100)
            ]

def kerdes_valasz_betoltes(kepernyo, forras, statusz):
    '''betölti a következő kérdést és válaszlehetőségeket, a statusz 0,1,2 értéket vehet fel: 0=alaphelyzet, 1=jó válasz, 2=rossz válasz - ettől függően változik a jó válasz négyzetének színe'''
    kerdes = Rubrika(12, 384, 1000, 100, pygame.Color(255, 255, 255), forras.kerdes)
    kategoria = Rubrika(12, 236, 200, 50, pygame.Color(255, 255, 255), forras.kategoria)

    valaszok = [
            Rubrika(12, 500, 500, 100, pygame.Color(0, 0, 60), "A: {}".format(forras.a)),
            Rubrika(12+500, 500, 500, 100, pygame.Color(0, 0, 60), "B: {}".format(forras.b)),
            Rubrika(12, 500+100, 500, 100, pygame.Color(0, 0, 60), "C: {}".format(forras.c)),
            Rubrika(12+500, 500+100, 500, 100, pygame.Color(0, 0, 60), "D: {}".format(forras.d))
            ]
    
    if statusz == 0:
        pass
    elif statusz == 1:
        for jo in valaszok:
            if jo.szoveg[0] == forras.valasz:
                jo.szin = pygame.Color(50, 100, 0)
                
    elif statusz == 2:
        for jo in valaszok:
            if jo.szoveg[0] == forras.valasz:
                jo.szin = pygame.Color(150, 0, 0)

    negyzet_rajzol(kepernyo, kerdes, 0)
    szoveg_negyzetbe(kepernyo, kerdes)

    negyzet_rajzol(kepernyo, kategoria, 0)
    szoveg_negyzetbe(kepernyo, kategoria)
    
    
    for v in valaszok:
        negyzet_rajzol(kepernyo, v, 1)
        szoveg_negyzetbe(kepernyo, v)
        
    pygame.display.update()



def hatter(kepernyo):
    segitseg_hely = segitseg_rubrika()
    kepernyo.fill((0, 0, 60))
    logo(kepernyo)
    
    segitseg_logo(kepernyo, segitseg_hely[0], "kozonseg.png")
    segitseg_logo(kepernyo, segitseg_hely[1], "felezes.png")
    
    kilepes_rubrika = Rubrika(854, 20, 150, 60, pygame.Color(60, 0, 0), "Kilépés")
    negyzet_rajzol(kepernyo, kilepes_rubrika, 1)
    szoveg_negyzetbe(kepernyo, kilepes_rubrika)


def pontok_betoltes(kepernyo, forras):
    pont_rajz = Rubrika(762, 306, 250, 60, pygame.Color(0, 0, 60), "Nyeremény: {} Ft".format(forras))

    if  "100000 Ft" in pont_rajz.szoveg or "1500000 Ft" in pont_rajz.szoveg or "40000000 Ft" in pont_rajz.szoveg:
        pont_rajz.szin = pygame.Color(220, 180, 0)
        
    negyzet_rajzol(kepernyo, pont_rajz, 1)
    szoveg_negyzetbe(kepernyo, pont_rajz)

def idozito_keret(kepernyo, szam):
    idozito = Rubrika(482, 306, 60, 60, pygame.Color(0, 0, 60), "{}".format(szam))
    negyzet_rajzol(kepernyo, idozito, 1)
    szoveg_negyzetbe(kepernyo, idozito)
    pygame.display.update()


def kezdokepernyo(kepernyo):
    '''kirajzolja az ablakba a kezdőképernyő felületét, a rubrikák koordinátái az 1024*720-as ablak pixelei alapján lettek megadva'''
    kepernyo.fill((0, 0, 60))
    logo(kepernyo)
    kezdo_rubrikak = [Rubrika(362, 232, 300, 60, pygame.Color(0, 0, 60), "Játékos neve"),
                      Rubrika(437, 500, 150, 60, pygame.Color(0, 0, 150), "Játék indítása"),
                      Rubrika(854, 20, 150, 60, pygame.Color(60, 0, 0), "Kilépés"),
                      Rubrika(362, 322, 300, 60, pygame.Color(0, 0, 30), "")]
    
    for rubrika in kezdo_rubrikak:
        negyzet_rajzol(kepernyo, rubrika, 1)
        szoveg_negyzetbe(kepernyo, rubrika)


def szint_negyzetek():
    return [pygame.Rect(257, 412, 150, 60), pygame.Rect(437, 400, 150, 60), pygame.Rect(617, 400, 150, 60)]

def inditogomb_negyzet():
    return pygame.Rect(437, 500, 150, 60)


def kilepes_negyzet():
    return pygame.Rect(854, 20, 150, 60)

def felhasznalo_rubrika():
    return Rubrika(362, 322, 300, 60, pygame.Color(0, 0, 30), "")
    

def szintvalaszto_negyzetek(kepernyo, statusz):
    '''statusz információ: Ha 0, akkor alaphelyzet. Ha 1, Kezdő = világoskék, Ha 2 = Normal = világoskék, Ha 3 = Extrem = világoskék'''
    
    szintek = [Rubrika(257, 412, 150, 60, pygame.Color(0, 0, 60), "Kezdő"),
               Rubrika(437, 412, 150, 60, pygame.Color(0, 0, 60), "Normál"),
               Rubrika(617, 412, 150, 60, pygame.Color(0, 0, 60), "Extrém")]

    if statusz == 0:
        pass
    elif statusz == 1:
        szintek[0].szin = pygame.Color(0, 0, 150)

    elif statusz == 2:
        szintek[1].szin = pygame.Color(0, 0, 150)

    elif statusz == 3:
        szintek[2].szin = pygame.Color(0, 0, 150)

    for rubrika in szintek:
        negyzet_rajzol(kepernyo, rubrika, 1)
        szoveg_negyzetbe(kepernyo, rubrika)


def szint_hibauzenet(kepernyo):
    hibahely = Rubrika(437, 590, 150, 60, pygame.Color(0, 0, 60), "Válassz szintet!")
    szoveg_negyzetbe(kepernyo, hibahely)
    

def segitseg_megjelenes(obj, kepernyo, statusz):
    '''paraméterként megadott Segitoeszkoz objektumot rajzolja körbe egy négyzettel, ha fel lett használva piros színnel'''

    if statusz == 0:
        pass
        
    elif statusz == 1:
        obj.szin = pygame.Color(150, 0, 0)
        
    negyzet_rajzol(kepernyo, obj, 0)
    pygame.display.update()


def kozonsegszavazat(kepernyo, statusz, *forras):
        if statusz == 0:
            kozonseg_rubrika = Rubrika(212, 306, 245, 60, pygame.Color(255, 255, 255), "")
        elif statusz == 1:
            forras = str(forras)
            forras = forras[2:-3]
            kozonseg_rubrika = Rubrika(212, 306, 245, 60, pygame.Color(255, 255, 255), "{}".format(forras))
        
        negyzet_rajzol(kepernyo, kozonseg_rubrika, 0)
        szoveg_negyzetbe(kepernyo, kozonseg_rubrika)
        pygame.display.update()

def jatek_vege(kepernyo, forras, statusz):
    if statusz:
        vege_rubrika = Rubrika(312, 310, 400, 60, pygame.Color(0, 0, 60), "Megnyerted a játékot! Nyereményed: {} Ft".format(forras))
    else:
        vege_rubrika = Rubrika(312, 310, 400, 60, pygame.Color(0, 0, 60), "Legközelebb jobban megy majd! Nyereményed: {} Ft".format(forras))
        
    jvege = pygame.Surface((1024, 720))
    jvege.fill((0, 0, 60))
    logo(jvege)
    kepernyo.blit(jvege, (0, 0))
    
    szoveg_negyzetbe(kepernyo, vege_rubrika)
    kilepes_rubrika = Rubrika(854, 20, 150, 60, pygame.Color(60, 0, 0), "Kilépés")
    negyzet_rajzol(kepernyo, kilepes_rubrika, 1)
    szoveg_negyzetbe(kepernyo, kilepes_rubrika)
    
    pygame.display.update()



def megallas(kepernyo):
    megallas_rubrika = Rubrika(12, 156, 150, 50, pygame.Color(0, 100, 0), "Megállok")

    negyzet_rajzol(kepernyo, megallas_rubrika, 0)
    szoveg_negyzetbe(kepernyo, megallas_rubrika)


def megallas_negyzet():
    return pygame.Rect(12, 156, 150, 50)

def teljesites_ido_UI(ido):
    ido = int(ido)
    perc = ido//60000
    mp = (ido - (perc*60000)) // 1000
    return "{} perc {} másodperc".format(perc, mp)
    


def dicsoseglista_UI(kepernyo, forras):
    '''grafikusan megjeleníti a toplistát - 20 legjobb játékos eredményét, a forras parameter egy tömb referenciája, amelyben Jatekos objektumok vannak'''
    kepernyo.fill((0, 0, 60))

    adat_rubrikak = [Rubrika(12, 12, 250, 38, pygame.Color(255, 255, 255), "NÉV"),
                     Rubrika(12+250, 12, 250, 38, pygame.Color(255, 255, 255), "PONTSZÁM"),
                     Rubrika(12+500, 12, 250, 38, pygame.Color(255, 255, 255), "NEHÉZSÉGI SZINT"),
                     Rubrika(12+750, 12, 250, 38, pygame.Color(255, 255, 255), "TELJESÍTÉSI IDŐ")]

    for rubrika in adat_rubrikak:
        negyzet_rajzol(kepernyo, rubrika, 0)
        szoveg_negyzetbe(kepernyo, rubrika)

    listaadatok = []

    for f in forras:
        telj_ido = teljesites_ido_UI(f.ido)
        listaadatok.append([f.nev, f.pontszam, f.nehezseg, telj_ido, f.segitseg])

    kezdo_x = 12
    kezdo_y = 50
        
    for i in range(20):
        try:
            f_nev = listaadatok[i][0]
            
        except IndexError:
            f_nev = ""

        try:
            hasznalt_segitseget = listaadatok[i][4]

        except:
            hasznalt_segitseget = ""


        nev = Rubrika(kezdo_x, kezdo_y + i*32, 250, 32, pygame.Color(0, 0, 60), "{}".format(f_nev))
        if hasznalt_segitseget == "False":
            nev.szin = pygame.Color(220, 180, 0)
            
        negyzet_rajzol(kepernyo, nev, 1)
        szoveg_negyzetbe(kepernyo, nev)

    pontszamok = []

    for i in range(20):
        try:
            f_pont = listaadatok[i][1]
            
        except IndexError:
            f_pont = ""
            
        pontszam = Rubrika(kezdo_x + 250, kezdo_y + i*32, 250, 32, pygame.Color(0, 0, 60), "{}".format(f_pont))
        negyzet_rajzol(kepernyo, pontszam, 1)
        szoveg_negyzetbe(kepernyo, pontszam)

    for i in range(20):
        try:
            f_szint = listaadatok[i][2]
            
        except IndexError:
            f_szint = ""
            
        nehezseg = Rubrika(kezdo_x + 500, kezdo_y + i*32, 250, 32, pygame.Color(0, 0, 60), "{}".format(f_szint))
        negyzet_rajzol(kepernyo, nehezseg, 1)
        szoveg_negyzetbe(kepernyo, nehezseg)

    for i in range(20):
        try:
            f_ido = listaadatok[i][3]
            
        except IndexError:
            f_ido = ""
            
        ido = Rubrika(kezdo_x + 750, kezdo_y + i*32, 250, 32, pygame.Color(0, 0, 60), "{}".format(f_ido))
        negyzet_rajzol(kepernyo, ido, 1)
        szoveg_negyzetbe(kepernyo, ido)
        
        

    pygame.display.update()

    
        


    
        
    
    




    

    

    
    
    
        
        
    
    
    
    
    
    
                    






