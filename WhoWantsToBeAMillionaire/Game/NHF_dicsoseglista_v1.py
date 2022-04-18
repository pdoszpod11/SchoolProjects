import NHF_UI_v1 as ui
import NHF_adatszerk_v1 as adat
import sys
import pygame
import pygame.gfxdraw


class Jatekos:
    def __init__(self, nev, pontszam, nehezseg, ido, segitseg):
        self.nev = nev
        self.pontszam = pontszam
        self.nehezseg = nehezseg
        self.ido = ido
        self.segitseg = segitseg


def nyeremeny_szamitas(stadium, statusz):
    '''a stadium paramétert az adja, hogy a játékos hányadik kérdésig jutott el'''

    if statusz == 0:
        if stadium < 4:
            return ["0", 0]

        if stadium > 4 and stadium <= 9:
            return ["100 000", 100000]

        if stadium > 9 and stadium <= 14:
            return ["1 500 000", 1500000]

        if stadium > 14:
            return ["40 000 000", 40000000]

    if statusz == 1:

        if stadium == 0:
            return ["0", 0]
            
        pontszam = adat.pontlista()
        return ["{}".format(pontszam[stadium-1]), pontszam[stadium-1]]


def listaba_felvesz(adatok):
    '''dicsőséglista fájlba írja az adott játékost'''
    jatekos_info = ""
    for a in adatok:
        jatekos_info += "{} ".format(a)
    jatekos_info += "\n"
        
    with open("dicsoseglista.txt", "at", encoding="utf-8") as f:
            f.write(jatekos_info)



def atalakit(adat):
    return Jatekos(adat[0], adat[1], adat[2], adat[3], adat[4])


def lista_rendezes():
    osszes = lista_beolvas()

    szintek = [rendezes_szint_szerint(osszes, 3),
               rendezes_szint_szerint(osszes, 2),
               rendezes_szint_szerint(osszes, 1)]

    for szint in range(len(szintek)):
        szintek[szint] = rendezes_pontszam_szerint(szintek[szint])        
        szintek[szint] = rendezes_segitseg_szerint(szintek[szint])

    lista = szintek[0] + szintek[1] + szintek[2]

    if len(lista) > 20:
        lista = lista[:20+1]

    with open("dicsoseglista.txt", "w+", encoding="utf-8") as f:
        for l in lista:
            info = ""
            info += "{} {} {} {} {}\n".format(l.nev, l.pontszam, l.nehezseg, l.ido, l.segitseg)
            f.write(info)
    

def rendezes_szint_szerint(osszes, szint):
    lista = []
    for elem in range(len(osszes)):
        if int(osszes[elem].nehezseg) == szint:
            if int(osszes[elem].pontszam) > 0:
                lista.append(osszes[elem])

    return lista


def rendezes_pontszam_szerint(lista):
    for x in range(len(lista)-1):
        nagyobb_hely = x
        x_pont = int(lista[x].pontszam)
        x_ido = int(lista[x].ido)
        csere = False
        
        for y in range(x+1, len(lista)):
            y_pont = int(lista[y].pontszam)
            y_ido = int(lista[y].ido)
            
            if y_pont > x_pont:
                nagyobb_hely = y
                csere = True
                
            if x_pont == y_pont:
                if x_ido > y_ido:
                    hely = y
                    csere = True
            
                
        if csere:
            temp = lista[x]
            lista[x] = lista[nagyobb_hely]
            lista[nagyobb_hely] = temp

    return lista
            

def rendezes_segitseg_szerint(lista):
    for elem in range(len(lista)):
        if lista[elem].pontszam == 40000000 and not lista[elem].segitseg:
            hozzaad = lista[elem]
            lista.pop(elem)
            lista = lista[elem] + lista

    return lista


def lista_beolvas():
    lista = []
    with open("dicsoseglista.txt", "rt", encoding="utf-8") as f:
        for sor in f:
            sor = sor.rstrip("\n")
            sor = sor.split(" ")
            lista.append(atalakit(sor))

    return lista
    

def dicsoseglista_kiiras(kepernyo):
    lista_rendezes()

    dlista = lista_beolvas()

    futas = True
    ui.dicsoseglista_UI(kepernyo, dlista)

    while futas:

        
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                futas = False


    pygame.quit()
    sys.exit()
        


lista_rendezes()
    
  
    
    
            
                
                
    

    


    


    



    
    
    
    
            


    
    
    
        
        
    
