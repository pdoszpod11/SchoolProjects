import pygame
import pygame.gfxdraw
import sys
import NHF_UI_v1 as ui


pygame.init()
win = pygame.display.set_mode((1024,720))
pygame.display.set_caption("Legyen Ön is Milliomos!")

class Menu:
    def __init__(self):
        self.inditas = None
        self.dicsoseg = None
        self.kilepes = None

def kattintas(funkcio):
    if funkcio.collidepoint(pygame.mouse.get_pos()):
        return True
    else:
        return False
    

def main_menu():
    futas = True
    funkciok = Menu()
    ui.fomenu(win)

    #funkciók helye
    indit = ui.inditas_negyzet()
    dics = ui.dicsoseg_negyzet()
    kilep = ui.menu_kilepes_negyzet()

    while futas:

        for event in pygame.event.get():
            if event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1:
                    if kattintas(indit):
                        funkciok.inditas = True
                        futas = False

                    elif kattintas(dics):
                        funkciok.dicsoseg = True
                        futas = False

                    elif kattintas(kilep):
                        funkciok.kilepes = True
                        futas = False

            if event.type == pygame.QUIT:
                futas = False

    
    if funkciok.inditas:
        return "indítás"

    elif funkciok.dicsoseg:
        return "dicsőséglista"

    elif funkciok.kilepes:
        return "kilépés"

    
    
    
    
    
