import NHF_main_menu_v1 as menu
import NHF_UI_v1 as ui
import NHF_adatszerk_v1 as adat
import NHF_gameplay_v1 as gplay
import NHF_dicsoseglista_v1 as dicso
import pygame
import sys

pygame.init()
win = pygame.display.set_mode((1024,720))
pygame.display.set_caption("Legyen Ön is Milliomos!")


class Szint:
    def __init__(self, statusz, valasztott):
        self.statusz = statusz
        self.valasztott = valasztott

def szint_valasztas():
    szintek = ui.szint_negyzetek()
    if szintek[0].collidepoint(pygame.mouse.get_pos()):
        return 1
    
    elif szintek[1].collidepoint(pygame.mouse.get_pos()):
        return 2
    
    elif szintek[2].collidepoint(pygame.mouse.get_pos()):
        return 3

    else:
        return None

def kilepes():
    kilepes_gomb = ui.kilepes_negyzet()
    if kilepes_gomb.collidepoint(pygame.mouse.get_pos()):
        return True
    else:
        return False


def inditas():
    inditogomb = ui.inditogomb_negyzet()
    if inditogomb.collidepoint(pygame.mouse.get_pos()):
        return True
    else:
        return False


def main():

    tovabblep = menu.main_menu()

    fo_futas = True
    jatekinditas = False
    dicsoseglista_megnyitas = False

        
    if tovabblep == "indítás":

        szint_valaszt = Szint(0, False)
        ui.kezdokepernyo(win)
        ui.szintvalaszto_negyzetek(win, szint_valaszt.statusz)
        jatekosnev = ""
        jatekos_betustilus = pygame.font.SysFont("Bahnschrift SemiLight", 24)
    
        
        while fo_futas:
            
            if szint_valaszt.valasztott:
                ui.szintvalaszto_negyzetek(win, szint_valaszt.statusz)
                szint_valaszt.valasztott = False
        
            for event in pygame.event.get():
                if event.type == pygame.MOUSEBUTTONDOWN:
                    if event.button == 1:
                        if szint_valasztas():
                            szint_valaszt.statusz = szint_valasztas()
                            szint_valaszt.valasztott = True

                    if event.button == 1:
                        if inditas() and szint_valaszt.statusz != 0:
                            jatekinditas = True
                            fo_futas = False
                            
                        if inditas() and szint_valaszt.statusz == 0:
                            ui.szint_hibauzenet(win)

                    if event.button == 1:
                        if kilepes():
                            fo_futas = False
                            
                if event.type == pygame.QUIT:
                    fo_futas = False

                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_BACKSPACE:
                        jatekosnev = jatekosnev[:-1]
                    else:
                        if len(jatekosnev) < 15:
                            jatekosnev += event.unicode

            ui.negyzet_rajzol(win, ui.felhasznalo_rubrika(), 1)      
            nev_kijelzon = jatekos_betustilus.render(jatekosnev, True, (255, 255, 255))
            win.blit(nev_kijelzon, (372, 352))
            
            pygame.display.flip()

    

    elif tovabblep == "dicsőséglista":
        dicsoseglista_megnyitas = True

    elif tovabblep == "kilépés":
        pygame.quit()
        sys.exit()

        
    if jatekinditas:
        
        if jatekosnev == "":
            jatekosnev = "Játékos"
        
        if szint_valaszt.statusz == 1:
            teljesitmeny = gplay.gameplay(adat.kezdo())

        elif szint_valaszt.statusz == 2:
            teljesitmeny = gplay.gameplay(adat.normal())

        elif szint_valaszt.statusz == 3:
            teljesitmeny = gplay.gameplay(adat.extrem())
            


        felhasznaloi_adatok = [jatekosnev, teljesitmeny[0], szint_valaszt.statusz, teljesitmeny[1], teljesitmeny[2]]
        dicso.listaba_felvesz(felhasznaloi_adatok)
        dicso.lista_rendezes()

    if dicsoseglista_megnyitas:
        dicso.lista_rendezes()
        dicso.dicsoseglista_kiiras(win)

    pygame.quit()
    sys.exit()
            
main()
