import random
        
class Kerdes:
    def __init__(self, nehezseg, kerdes, a, b, c, d, valasz, kategoria):
        self.nehezseg = nehezseg
        self.kerdes = kerdes
        self.a = a
        self.b = b
        self.c = c
        self.d = d
        self.valasz = valasz
        self.kategoria = kategoria

def pontlista():
    return [5000, 10000, 25000, 50000, 100000,
            200000, 300000, 500000, 800000, 1500000,
            3000000, 5000000, 10000000, 20000000, 40000000]


def beolvas(fajl):
    '''fájlból beolvassa, és egy listába rendezi a sorokat'''
    k = []
    with open(fajl, "rt", encoding="utf-8") as f:
        for sor in f:
            sor = sor.rstrip("\n")
            sor = sor.split("\t")
            k.append(atalakit(sor))
        k.pop(0)
    return k
    
   
def atalakit(adat):
    '''Kerdes objektummá alakítás'''
    return Kerdes(adat[0], adat[1], adat[2], adat[3], adat[4], adat[5], adat[6], adat[7])


def lehetseges_kerdesek(szint):
    '''nehézség szerint adja vissza a lehetséges kérdéseket'''

    osszes_kerdes = beolvas("loimszoveg.txt")
    
    kerdesek = []
    for i in range(len(osszes_kerdes)):
        if int(osszes_kerdes[i].nehezseg) in szint:
            kerdesek.append(osszes_kerdes[i])

    return kerdesek    

def veletlenszeru_kerdesek(lista):
    '''a lehetséges kérdések listájából állít 15 véletlenszerű kérdést a játékhoz'''
    jatek_kerdesei = []
    for i in range(15):
        k = random.randrange(len(lista))
        while lista[k] in jatek_kerdesei:
            k = random.randrange(len(lista))
        jatek_kerdesei.append(lista[k])

    return jatek_kerdesei
    

def kezdo():
    '''véletlenszerűen ad 15 db kérdést a játék kérdései közül'''
    return veletlenszeru_kerdesek(lehetseges_kerdesek([1,2,3,4,5]))


def normal():
    '''normal szintű kérdések nehézségi szintje: 3-8-ig'''
    return veletlenszeru_kerdesek(lehetseges_kerdesek([3,4,5,6,7,8]))

def extrem():
    '''extrem szintű kérdések nehézségi szintje: 9-13-ig'''
    return veletlenszeru_kerdesek(lehetseges_kerdesek([9,10,11,12,13]))
    

