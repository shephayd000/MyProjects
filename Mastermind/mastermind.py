import math
import random
import pygame
import array as arr
import tkinter as tk
from tkinter import messagebox

flag = True

def drawHint(pos, val, cr, win):
    color = (255,255,255)
    if val == 0:
        color = (0,0,0)
    if pos == 0:
        pygame.draw.circle(win, color, (422,(60*cr+122)), 12)
    elif pos == 1:
        pygame.draw.circle(win, color, (447,(60*cr+122)), 12)
    elif pos == 2:
        pygame.draw.circle(win, color, (422,(60*cr+147)), 12)   
    elif pos == 3:
        pygame.draw.circle(win, color, (447,(60*cr+147)), 12)
    else:
        print("Error in drawHint")
    pass

def showAnswer(win, ansVals):
    for i in range(4):
        pygame.draw.circle(win, whatColor(ansVals[i]), (4*50+25+(i*50), 75), 25)
    pass

def whatColor(value):
    #Default white
    color = (255,255,255)
    if value == 1:   
        color = (0,0,0) #Black
    elif value == 2:
        color = (255,0,0) #Red
    elif value == 3:
        color = (0,255,0) #Green
    elif value == 4:
        color = (0,0,255) #Blue
    elif value == 5:
        color = (255,255,0) #Yellow
    
    return color

def calcHints(win, array, cr, answers):
    global flag
    bc = 0 #Black token count
    wc = 0 #White token count

    #Find how many black tokens are earned
    for i in range(4):
        if array[i] == answers[i]:
            bc += 1

    if bc == 4:
        #You win
        print("You Won!")
        message_box("You Won!", "Nice Job!")
        showAnswer(win, answers)
        flag = False

    elif cr == 0:
        #You lose
        print("You Lost")
        message_box("You Lost", "Try again")
        showAnswer(win, answers)
        flag = False

    #Find how many white tokens are earned
    for i in range(6):
        countA = 0
        countB = 0
        for x in array:
            if x == i:
                countA += 1
        
        for x in answers:
            if x == i:
                countB += 1
        
        if countA >= countB:
            wc += countB
        else:
            wc += countA
    
    wc = abs(bc - wc)

    for i in range(4):
        if bc > 0:
            drawHint(i, 0, cr, win)
            bc -= 1
        elif wc > 0:
            drawHint(i, 1, cr, win)
            wc -= 1
        else:
            break
    pass    

def message_box(subject, content):
    root = tk.Tk()
    root.attributes("-topmost", True)
    root.withdraw()
    messagebox.showinfo(subject, content)
    try:
        root.destroy()
    except:
        pass

def main():
    global flag
    height = 1000
    width = 700
    bs = 50 #Block Size
    gameRows = 11

    curRow = 10

    darkGrey = (100,100,100)
    lightGrey = (211,211,211)
    green = (0,255,0)
    backgroundColor = (0,0,0)

    cvs = arr.array('i', [0, 0, 0, 0]) #Color Values
    ansVals = arr.array('i', [0, 0, 0, 0]) #Random Values

    ansVals[0] = random.randint(0,5)
    ansVals[1] = random.randint(0,5)
    ansVals[2] = random.randint(0,5)
    ansVals[3] = random.randint(0,5)
    
    
    #create screen
    win = pygame.display.set_mode((width,height))
    win.fill(backgroundColor)
    

    submitB = pygame.draw.rect(win, green,(3*bs-10,(60*curRow+110),bs,bs))
    pygame.draw.rect(win, darkGrey, (200,50,4*bs,50))

    #for i in range(4):
        #pygame.draw.circle(win, whatColor(ansVals[i]), (4*bs+25+(i*50), 75), 25)

    for row in range(gameRows):
        space = 60*row + 110
        pygame.draw.rect(win, lightGrey, (4*bs,space,4*bs,bs))
        pygame.draw.rect(win, lightGrey, ((8*bs+10), space, bs, bs))
                    
    while flag:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                flag = False
            elif event.type == pygame.MOUSEBUTTONUP:
                pos_x, pos_y = pygame.mouse.get_pos()
                
                #If current row is clicked
                if pos_y > 60*curRow+110 and pos_y < 60*curRow+160:
                    #If button is clicked
                    if pos_x >= 3*bs-10 and pos_x <= 4*bs-10:
                        pygame.draw.rect(win, backgroundColor,submitB)
                        calcHints(win, cvs, curRow, ansVals)
                        curRow = curRow -1
                        submitB.move_ip(0,-60)
                        cvs = [0,0,0,0]

                        #print("moved "+str(submitB.top))
                    
                    #If first circle is clicked
                    elif pos_x >= 4*bs and pos_x < 5*bs:
                        if cvs[0] == 5:
                            cvs[0] = 0
                        else:
                            cvs[0]+= 1
                    
                    #If second circle is clicked
                    elif pos_x >= 5*bs and pos_x < 6*bs:
                        if cvs[1] == 5:
                            cvs[1] = 0    
                        else:
                            cvs[1]+= 1

                    #If third circle is clicked
                    elif pos_x >= 6*bs and pos_x < 7*bs:
                        if cvs[2] == 5:
                            cvs[2] = 0    
                        else:
                            cvs[2]+= 1
                    
                    #If fourth circle is clicked
                    elif pos_x >= 7*bs and pos_x < 8*bs:
                        if cvs[3] == 5:
                            cvs[3] = 0    
                        else:
                            cvs[3]+= 1
            else: 
                pygame.draw.rect(win, green,submitB)
                pygame.draw.circle(win, whatColor(cvs[0]), (4*bs+25, 60*curRow+135), 25)
                pygame.draw.circle(win, whatColor(cvs[1]), (4*bs+75, 60*curRow+135), 25)
                pygame.draw.circle(win, whatColor(cvs[2]), (4*bs+125, 60*curRow+135), 25)
                pygame.draw.circle(win, whatColor(cvs[3]), (4*bs+175, 60*curRow+135), 25)
            pygame.display.update()
    pass

main()