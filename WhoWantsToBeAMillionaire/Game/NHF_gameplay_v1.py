import sys
import pygame
import pygame.gfxdraw
import NHF_UI_v1 as ui
import NHF_adatszerk_v1 as adat
import NHF_dicsoseglista_v1 as dicso
import random

pygame.init()
kepernyo = pygame.display.set_mode((1024, 720))

class Jatek:
    def __init__(self, futas, jovalasz, megallas):
        self.futas = futas
        self.jovalasz = jovalasz
        self.megallas = megallas

class Segitoeszkoz:
    def __init__(self, kozonseg, felezes, koz_felhasznal, fel_felhasznal):
        self.kozonseg = kozonseg
        self.felezes = felezes
        self.koz_felhasznal = koz_felhasznal
        self.fel_felhasznal = fel_felhasznal

class Valasz:
    def __init__(self, valasz, betujel):
        self.valasz = valasz
        self.betujel = betujel


def kilepes_jelzo():
    kilep = ui.kilepes_negyzet()
    if kilep.collidepoint(pygame.mouse.get_pos()):
        return True
    else:
        return False
    
def megallas_jelzo():
    megall = ui.megallas_negyzet()
    if megall.collidepoint(pygame.mouse.get_pos()):
        return True
    else:
        return False
    
def megjelenites(kv_forras, p_forras, statusz):
    ui.hatter(kepernyo)    
    ui.kerdes_valasz_betoltes(kepernyo, kv_forras, statusz)
    ui.pontok_betoltes(kepernyo, p_forras)
    ui.megallas(kepernyo)
    

def valasz_hely(v):
    '''a válaszlehetőségek közül visszaadja a helyes választ tartalmazó rubrika helyét'''
    valaszok = ui.valasz_negyzet()
    if v == "A":
        return valaszok[0]
    elif v == "B":
        return valaszok[1]
    elif v == "C":
        return valaszok[2]
    elif v == "D":
        return valaszok[3]

def tippeles(lehetosegek, valaszlehetosegek,  helyesvalasz):
    '''az egér pozícióját hasonlítja a jó válasz pozíciójával - ha egybeesnek akkor egy sztring a visszatérési érték, ellenkező esetben False'''
    
    honnan = [Valasz(lehetosegek.a, "A"), Valasz(lehetosegek.b, "B"), Valasz(lehetosegek.c, "C"), Valasz(lehetosegek.d, "D")]
    kivetel =[]
    for kivesz in range(len(honnan)):
        if honnan[kivesz].valasz == "":
            kivetel.append(kivesz)
            
    
    for l in range(len(valaszlehetosegek)):
        if valaszlehetosegek[l].collidepoint(pygame.mouse.get_pos()) and valaszlehetosegek[l] == helyesvalasz:
            return "jóválasz"
        elif valaszlehetosegek[l].collidepoint(pygame.mouse.get_pos()) and valaszlehetosegek[l] != helyesvalasz:
            if l in kivetel:
                return False
            return "rosszválasz"
        
    return False

def segitseg_hasznalat(negyzet):
    '''egér pozícióját hasonlítja a segítség pozíciójával: ha belekattintok akkor True a visszatérési érték'''
    if negyzet.collidepoint(pygame.mouse.get_pos()):
        return True
    else:
        False


def felezes(lehetosegek):
    '''inputként egy Kerdes objektumokat tartalmazó tömböt kap, a kivágottak szövegét beállítja üresre'''
    honnan = [Valasz(lehetosegek.a, "A"), Valasz(lehetosegek.b, "B"), Valasz(lehetosegek.c, "C"), Valasz(lehetosegek.d, "D")]
    for h in honnan:
        if h.betujel == lehetosegek.valasz:
            helyes = h.valasz
            break

    kivagott = 0
    while kivagott < 2:
        kivag = random.randrange(0, 4)
        if kivag == 0:
            if lehetosegek.a == helyes or lehetosegek.a == "":
                continue
            
            lehetosegek.a = ""
            kivagott += 1
            continue
        
        elif kivag == 1:
            if lehetosegek.b == helyes or lehetosegek.b == "":
                continue
            
            lehetosegek.b = ""
            kivagott += 1
            continue

        elif kivag == 2:
            if lehetosegek.c == helyes or lehetosegek.c == "":
                continue
            
            lehetosegek.c = ""
            kivagott += 1
            continue

        elif kivag == 3:
            if lehetosegek.d == helyes or lehetosegek.d == "":
                continue
            
            lehetosegek.d = ""
            kivagott += 1
            continue

    return lehetosegek

def kozonseg_szavazas(lehetosegek):
    '''random modul random.randrange funkciója generál egy százalékot minden elérhető válaszhoz - a jó válasz mindig a legnagyobb százalék'''
    szoveg = []
    honnan = [Valasz(lehetosegek.a, "A"), Valasz(lehetosegek.b, "B"), Valasz(lehetosegek.c, "C"), Valasz(lehetosegek.d, "D")]
    for h in honnan:
        if h.betujel == lehetosegek.valasz:
            joszazalek = random.randrange(33, 70)
            szoveg.append("{}: {}% ".format(h.betujel, joszazalek))
            break

    rszazalek = 0
    for h in honnan:
        if h.betujel != lehetosegek.valasz:
            ujszazalek = random.randrange(0, 100-(joszazalek + rszazalek))
            szoveg.append("{}: {}% ".format(h.betujel, ujszazalek))
            rszazalek += ujszazalek

    szoveg.sort()
    sztring_szoveg = ""
    for s in szoveg:
        sztring_szoveg += "{} ".format(s)
        
    ui.kozonsegszavazat(kepernyo, 1, sztring_szoveg)   


def gameplay(szint):
    '''a játékmenet futása az alapadatok és a futáshoz szükséges változók értékadása után'''
    #alapadatok
    kerdes = szint
    nyeremeny = adat.pontlista()
    segitseg = Segitoeszkoz(True, True, False, False)
    segitseg_ui = ui.segitseg_rubrika()

    segitseg_negyzet = ui.segitseg_negyzet()
    
    #futás
    ora = pygame.time.Clock()
    jatek = Jatek(True, True, False)
    sorszam = 0
    felez = False
    
    while sorszam < 15:

        if segitseg.fel_felhasznal:
            felezes(kerdes[sorszam])
            megjelenites(kerdes[sorszam], nyeremeny[sorszam], 0)
            ui.kozonsegszavazat(kepernyo, 0)
            segitseg.fel_felhasznal = False

        elif segitseg.koz_felhasznal:
            kozonseg_szavazas(kerdes[sorszam])
            segitseg.koz_felhasznal = False
            
        else:
            megjelenites(kerdes[sorszam], nyeremeny[sorszam], 0)
            ui.kozonsegszavazat(kepernyo, 0)
            
        if segitseg.kozonseg:
            ui.segitseg_megjelenes(segitseg_ui[0], kepernyo,  0)
        elif not segitseg.kozonseg:
            ui.segitseg_megjelenes(segitseg_ui[0], kepernyo,  1)
        if segitseg.felezes:
            ui.segitseg_megjelenes(segitseg_ui[1], kepernyo, 0)
        elif not segitseg.felezes:
            ui.segitseg_megjelenes(segitseg_ui[1], kepernyo, 1)

        ido = ui.idozito_keret(kepernyo, "60")
        mp = 6000

        while jatek.futas:
           
            v_lehetoseg = ui.valasz_negyzet()

            helyes_v = valasz_hely(kerdes[sorszam].valasz)
            
            for event in pygame.event.get():
                if event.type == pygame.MOUSEBUTTONDOWN:
                    if event.button == 1:
                        if tippeles(kerdes[sorszam], v_lehetoseg, helyes_v) == "jóválasz":
                            jatek.jovalasz = True
                            jatek.futas = False
                            
                        elif tippeles(kerdes[sorszam], v_lehetoseg, helyes_v) == "rosszválasz":
                            jatek.jovalasz = False
                            jatek.futas = False

                    if event.button == 1:
                        if segitseg.felezes and segitseg_hasznalat(segitseg_negyzet[1]):
                            segitseg.felezes = False
                            segitseg.fel_felhasznal = True
                            jatek.futas = False

                    if event.button == 1:
                        if segitseg.kozonseg and segitseg_hasznalat(segitseg_negyzet[0]):
                            segitseg.kozonseg = False
                            segitseg.koz_felhasznal = True
                            jatek.futas = False

                    if event.button == 1:
                        if megallas_jelzo():
                            jatek.megallas = True
                            jatek.jovalasz = False
                            jatek.futas = False

                    if event.button == 1:
                         if kilepes_jelzo():
                             jatek.futas = False
                             jatek.jovalasz = False
                            
                            
                if event.type == pygame.QUIT:
                    pygame.quit()
                    sys.exit()

            if mp % 100 == 0:
                ui.idozito_keret(kepernyo, "{}".format(mp//100))

            if mp == 0:
                jatek.jovalasz = False
                jatek.futas = False

            mp -= 1
            ora.tick(100)
            pygame.display.update()

        if segitseg.fel_felhasznal:
            jatek.futas = True
            continue
        
        if segitseg.koz_felhasznal:
            jatek.futas = True
            continue
            
        elif jatek.jovalasz:
            megjelenites(kerdes[sorszam], nyeremeny[sorszam], 1)
            ui.segitseg_megjelenes(segitseg_ui[0], kepernyo,  0)
            ui.segitseg_megjelenes(segitseg_ui[1], kepernyo, 0)
            ui.idozito_keret(kepernyo, "60")
            pygame.time.delay(500)
            jatek.futas = True
            sorszam += 1
            
        elif jatek.jovalasz == False:
            megjelenites(kerdes[sorszam], nyeremeny[sorszam], 2)
            ui.segitseg_megjelenes(segitseg_ui[0], kepernyo,  0)
            ui.segitseg_megjelenes(segitseg_ui[1], kepernyo, 0)
            ui.idozito_keret(kepernyo, "60")           
            pygame.time.delay(500)
            break


    ido_halad = pygame.time.get_ticks()
    vegpontszam = vege(sorszam, jatek.megallas)
    
    return dicsolista_adatok(ido_halad, segitseg.kozonseg, segitseg.felezes, vegpontszam)



def dicsolista_adatok(ido, kozonseg, felezes, pont):
    if not kozonseg or not felezes:
        segitseg = True
    else:
        segitseg = False
        
    return [pont, ido, segitseg]
    

def vege(kerdes_szama, megallt_e):
    
    nyert_e = False
    
    if megallt_e:
        statusz = 1
    else:
        statusz = 0

    if kerdes_szama > 14:
        nyert_e = True
    
    nyeremeny = dicso.nyeremeny_szamitas(kerdes_szama, megallt_e)
    ui.jatek_vege(kepernyo, nyeremeny[0], nyert_e)
    while True:
        
        
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                return nyeremeny[1]

            if event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1:
                    if kilepes_jelzo():
                        return nyeremeny[1]
        
        pygame.display.update()








        
