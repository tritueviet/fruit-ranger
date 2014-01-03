// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 11/15/2012 3:20:52 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FruitCanvas.java

import java.io.*;
import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.media.Player;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class FruitCanvas extends Canvas
        implements Runnable {

    public FruitCanvas(FruitMania midlet) {
        musicFlag = false;
        bPlay = false;
        vecFruits = new Vector();
        vecPieces = new Vector();
        gamestate = -1;
        tempstate = -1;
        gN = 100D;
        failFruitCount = 0;
        liveCount = 3;
        collision_flag = false;
        bombCollision_flag = false;
        slashCount = 5;
        step = 0;
        time = 0;
        gametime = 0;
        starttime = 0;
        speedLevel = 0;
        fruitSet = new Fruit[fruitNum];
        slash = new Slash();
        sleepTime = 0;
        rand = new Random();
        fprevTime = 0;
        fnextTime = 0;
        bselected = false;
        win = false;
        gameplay = false;
        bStand = true;
        bX = 0;
        bY = 0;
        bCount = 0;
        snd = new Sound[8];
        tmptime = 0;
        bonuscount = 0;
        bonus = 0;
        digit = new int[20];
        soundeffect = true;
        cflag = false;
        j = 0;
        k = 0;
        s = 0;
        autoSound = 0;
        flag = false;
        keyFlag = false;
        upflag = false;
        downflag = false;
        leftflag = false;
        rightflag = false;
        playTime = 0;
        timeUpFlag = false;
        pointX = 0;
        pointY = 0;
        gbflag = false;
        dx = 0;
        dy = 0;
        sx = 0;
        sy = 0;
        vtime = 0;
        startdX = 0;
        startdY = 0;
        slashSoundFlag = false;
        bloading = false;
        helpUP = false;
        helpDOWN = false;
        pointerY = -1;
        dprevTime = 0;
        dnextTime = 0;
        prevX = 0;
        prevY = 0;
        currentX = 0;
        currentY = 0;
        dflag = false;
        comDflag = false;
        soundCount = 0;
        ltime = 0;
        viewstart = 0;
        viewheight = 167;
        pos1 = new int[4][2];
        pos2 = new int[4][2];
        pos = new int[4][2];
        sel_menu = 0;
        pauseFlag = false;
        confirm_res = 0;
        tTime = 0;
        bhide = false;
        startX = 0;
        startY = 282;
        u = 0;
        angry = 0;
        bSound = false;
        ccount = 0;
        bonusFlag = false;
        bonusX = 0;
        bonusY = 0;
        sameCount = 0;
        setFullScreenMode(true);
        mlet = midlet;
        width = getWidth();
        height = getHeight();
        bStand = true;
        bX = 0;
        bY = 0;
        bPlay = true;
        sleepTime = 50;
        Goto(1);
        //   File_Load();
        try {
            im_common = new Image[6];
            loadDatOnece("common", im_common, 0, 0, 6);
            for (int i = 0; i < 8; i++) {
                snd[i] = new Sound(1000, i);
            }

        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    void Goto(int state) {
        try {
            if (gamestate != 11 && tempstate != 11 && gamestate != 4 && gamestate != 13 && tempstate == 4) {
                FreeMem();
            }
            switch (state) {
                case 1: // '\001'
                    im_splash = new Image[3];
                    loadDatOnece("splash", im_splash, 0, 0, 3);
                    break;

                case 2: // '\002'
                    im_enableSound = new Image[1];
                    loadDatOnece("enable_sound", im_enableSound, 0, 0, 1);
                    break;

                case 3: // '\003'
                    im_menu = new Image[6];
                    loadDatOnece("menu", im_menu, 0, 0, 6);
                    break;

                case 9: // '\t'
                    im_option = new Image[7];
                    loadDatOnece("options", im_option, 0, 0, 7);
                    break;

                case 12: // '\f'
                    im_confirm = new Image[2];
                    loadDatOnece("confirmation", im_confirm, 0, 0, 2);
                    break;

                case 10: // '\n'
                    im_help = new Image[4];
                    loadDatOnece("help", im_help, 0, 0, 4);
                    break;

                case 4: // '\004'
                    im_play = new Image[58];
                    loadDatOnece("play", im_play, 0, 0, 58);
                    break;

                case 11: // '\013'
                    im_pause = new Image[4];
                    loadDatOnece("pause", im_pause, 0, 0, 4);
                    break;

                case 13: // '\r'
                    im_result = new Image[6];
                    loadDatOnece("result", im_result, 0, 0, 6);
                    break;

                case 8: // '\b'
                    im_about = new Image[2];
                    loadDatOnece("about", im_about, 0, 0, 2);
                    break;

                case 14: // '\016'
                    im_bombfail = new Image[1];
                    loadDatOnece("bombfail", im_bombfail, 0, 0, 1);
                    break;
            }
            gamestate = state;
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void FreeMem() {
        im_splash = null;
        im_menu = null;
        im_option = null;
        im_help = null;
        im_confirm = null;
        im_pause = null;
        im_play = null;
        im_result = null;
        im_enableSound = null;
        im_about = null;
        System.gc();
    }

    public void FreeSound() {
        try {
            for (int i = 0; i < 8; i++) {
                if (snd[i] != null) {
                    snd[i].stopSound();
                    try {
                        snd[i].mPlayer.deallocate();
                        snd[i].mPlayer.close();
                    } catch (Exception e) {
                    }
                    snd[i].mPlayer = null;
                    snd[i] = null;
                }
            }

        } catch (Exception e) {
        }
    }

    void startBG(int id) {
        if (option[1] == 0) {
            snd[id].startSound(id, 1);
            musicFlag = true;
        }
    }

    void stopBG(int id) {
        if (snd[id] != null) {
            snd[id].stopSound();
        }
    }

    public void playSound(int soundId) {
        if (soundId == 0) {
            if (!musicFlag && option[1] == 0) {
                snd[soundId].startSound(soundId, 0);
            }
        } else if (option[1] == 0) {
            snd[soundId].startSound(soundId, 0);
        }
    }
 
    void loadDatOnece(String fileName, Image img[], int destIdx, int imgIdx, int len) {
      for (int i = 0; i < len; i++) {
            try {
                img[i]=Image.createImage("/data/"+fileName+ i+".PNG");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
      }
  }
  
    void loadDatOnece2(String fileName, Image img[], int destIdx, int imgIdx, int len) {
        StringBuffer strBuf = new StringBuffer("/image/");
        strBuf.append(fileName);
        strBuf.append(".dat");
        InputStream inp = getClass().getResourceAsStream(strBuf.toString());
        int totlen = readInt(inp);
        int skipSize = 0;
        for (int i = 0; i < imgIdx; i++) {
            skipSize += readInt(inp);
        }

        int fileSize[] = new int[len];
        for (int i = 0; i < len; i++) {
            fileSize[i] = readInt(inp);
        }

        skipSize += (totlen - imgIdx - len) * 4;
        try {
            inp.skip(skipSize);
        } catch (Exception e) {
        }
        for (int i = 0; i < len; i++) {
            int readCnt = 0;
            imgBuf = new byte[fileSize[i]];
            try {
                readCnt = inp.read(imgBuf);
            } catch (Exception e) {
            }
            try {
                img[destIdx + i] = Image.createImage(imgBuf, 0, readCnt);
            } catch (Exception e) {
            }
            
            saveImage.saveImage2File(imgBuf, fileName+i);
        }

        try {
            inp.close();
        } catch (Exception e) {
        }
        inp = null;
        imgBuf = null;
    }

    int readInt(InputStream inp) {
        byte buf[] = new byte[4];
        try {
            inp.read(buf, 0, 4);
        } catch (Exception e) {
        }
        return buf[0] << 24 & 0xff000000 | buf[1] << 16 & 0xff0000 | buf[2] << 8 & 0xff00 | buf[3] & 0xff;
    }

    protected void paint(Graphics g) {
        switch (gamestate) {
            case 1: // '\001'
                draw_splash(g);
                break;

            case 3: // '\003'
                draw_menu(g);
                break;

            case 2: // '\002'
                draw_enableSound(g);
                break;

            case 9: // '\t'
                draw_option(g);
                break;

            case 10: // '\n'
                draw_help(g);
                break;

            case 4: // '\004'
                draw_play(g);
                break;

            case 11: // '\013'
                draw_pause(g);
                break;

            case 12: // '\f'
                draw_confirm(g);
                break;

            case 13: // '\r'
                draw_result(g);
                break;

            case 8: // '\b'
                draw_about(g);
                break;

            case 14: // '\016'
                draw_bombfail(g);
                break;
        }
    }

    public int getRandomInt(int a, int b) {
        int result = (rand.nextInt() >>> 1) % (b - a) + a;
        return result;
    }

    void draw_result(Graphics g) {
        if (!bPlay) {
            return;
        }
        g.drawImage(im_common[0], 0, 0, 20);
        int x = (width - im_common[4].getWidth()) / 2;
        int y = (height - im_common[4].getHeight()) / 2;
        g.drawImage(im_common[4], x, y, 20);
        int y3 = y + 40;
        if (gamekind == bomb) {
            int x3 = (width - im_result[0].getWidth()) / 2;
            g.drawImage(im_result[0], x3, y3, 20);
        } else if (gamekind == arcade) {
            int x3 = (width - im_result[1].getWidth()) / 2;
            g.drawImage(im_result[1], x3, y3, 20);
        }
        int x4 = (width - im_result[2].getWidth()) / 2;
        int y4 = y3 + im_result[1].getHeight() + 10;
        g.drawImage(im_result[2], x4, y4, 20);
        if (gamekind == bomb) {
            draw_num(g, im_result[3], cScore, x4 + im_result[2].getWidth() / 2 + 5, y4 + (im_result[2].getHeight() - im_result[3].getHeight()) / 2, im_result[3].getWidth() / 10, im_result[3].getHeight(), 2);
        } else if (gamekind == arcade) {
            draw_num(g, im_result[3], aScore, x4 + im_result[2].getWidth() / 2 + 5, y4 + (im_result[2].getHeight() - im_result[3].getHeight()) / 2, im_result[3].getWidth() / 10, im_result[3].getHeight(), 2);
        }
        int x1 = (width - im_result[4].getWidth()) / 2;
        int y1 = y4 + im_result[2].getHeight() + 10;
        int x2 = (width - im_result[5].getWidth()) / 2;
        int y2 = y1 + im_result[4].getHeight() + 5;
        g.drawImage(im_result[4], x1, y1, 20);
        g.drawImage(im_result[5], x2, y2, 20);
    }

    void draw_about(Graphics g) {
        g.drawImage(im_common[0], 0, 0, 20);
        int x = (width - im_common[1].getWidth()) / 2;
        int y = (height - im_common[1].getHeight()) / 2;
        g.drawImage(im_common[1], x, y, 20);
        int x1 = (width - im_about[0].getWidth()) / 2;
        int y1 = y + 40;
        int x2 = (width - im_about[1].getWidth()) / 2;
        int y2 = y1 + im_about[0].getHeight() + 30;
        g.drawImage(im_about[0], x1, y1, 20);
        g.drawImage(im_about[1], x2, y2, 20);
    }

    void draw_num(Graphics g, Image img, int num, int x, int y, int w, int h,
            int pos) {
        int temp_num = num;
        int index = 0;
        int xx = 0;
        while (temp_num / 10 != 0) {
            digit[index] = temp_num % 10;
            temp_num /= 10;
            index++;
        }
        digit[index] = temp_num;
        if (pos == 0) {
            xx = x - (index + 1) * w;
            for (int i = index; i >= 0; i--) {
                g.drawRegion(img, digit[i] * w, 0, w, h, 0, xx + (index - i) * w, y, 20);
            }

        } else if (pos == 1) {
            xx = x - ((index + 1) * w) / 2;
            for (int i = index; i >= 0; i--) {
                g.drawRegion(img, digit[i] * w, 0, w, h, 0, xx + (index - i) * w, y, 20);
            }

        } else if (pos == 2) {
            xx = x;
            for (int i = index; i >= 0; i--) {
                g.drawRegion(img, digit[i] * w, 0, w, h, 0, xx + (index - i) * w, y, 20);
            }

        }
    }

    void draw_time(Graphics g, Image img, int time, int y) {
        int hour = time / 3600;
        int minute = (time % 3600) / 60;
        int second = time % 60;
        int w = img.getWidth() / 11;
        int x = 161;
        if (minute < 10) {
            draw_num(g, img, 0, x, y, w, img.getHeight(), 2);
            x += w;
        }
        draw_num(g, img, minute, x, y, w, img.getHeight(), 2);
        if (minute < 10) {
            x += w;
        } else {
            x += w * 2;
        }
        g.drawRegion(img, w * 10, 0, w, img.getHeight(), 0, x, y, 20);
        x += w;
        if (second < 10) {
            draw_num(g, img, 0, x, y, w, img.getHeight(), 2);
            x += w;
        }
        draw_num(g, img, second, x, y, w, img.getHeight(), 2);
    }

    void draw_play(Graphics g) {
        int markX = 0;
        int markY = 0;
        int bestX = 0;
        int bestY = 0;
        int pauseX = 0;
        int pauseY = 0;
        int livesX1 = 0;
        int livesY1 = 0;
        int livesX2 = 0;
        int livesY2 = 0;
        int livesX3 = 0;
        int livesY3 = 0;
        int timeX = 0;
        int timeY = 0;
        int scoreX = 0;
        int scoreY = 0;
        g.drawImage(im_common[0], 0, 0, 20);
        markX = 5;
        markY = 8;
        g.drawImage(im_play[50], markX, markY, 20);
        bestX = markX;
        bestY = markY + im_play[50].getHeight() + 4;
        g.drawImage(im_play[38], bestX, bestY, 20);
        scoreX = markX + im_play[50].getWidth() + 3;
        scoreY = markY;
        if (gamekind == bomb) {
            draw_num(g, im_play[37], cMaxScore, bestX + im_play[38].getWidth() + 1, bestY, im_play[37].getWidth() / 10, im_play[37].getHeight(), 2);
            draw_num(g, im_play[36], cScore, scoreX, scoreY, im_play[36].getWidth() / 10, im_play[36].getHeight(), 2);
        } else if (gamekind == arcade) {
            draw_num(g, im_play[37], aMaxScore, bestX + im_play[38].getWidth() + 1, bestY, im_play[37].getWidth() / 10, im_play[37].getHeight(), 2);
            draw_num(g, im_play[36], aScore, scoreX, scoreY, im_play[36].getWidth() / 10, im_play[36].getHeight(), 2);
        }
        pauseX = width - im_play[28].getWidth() - 2;
        pauseY = height - im_play[28].getHeight() - 2;
        g.drawImage(im_play[28], pauseX, pauseY, 20);
        if (gamekind == bomb) {
            livesX1 = 161;
            livesY1 = 10;
            livesX2 = livesX1 + im_play[29].getWidth() + 2;
            livesY2 = livesY1 - 1;
            livesX3 = livesX2 + im_play[30].getWidth() + 2;
            livesY3 = livesY1 - 2;
            if (liveCount == 2) {
                if (failFruitCount == 1) {
                    g.drawImage(im_play[29], livesX1, livesY1, 20);
                    g.drawImage(im_play[54], livesX2, livesY2, 20);
                    g.drawImage(im_play[34], livesX3, livesY3, 20);
                } else if (failFruitCount == 2) {
                    g.drawImage(im_play[29], livesX1, livesY1, 20);
                    g.drawImage(im_play[55], livesX2, livesY2, 20);
                    g.drawImage(im_play[34], livesX3, livesY3, 20);
                } else {
                    g.drawImage(im_play[29], livesX1, livesY1, 20);
                    g.drawImage(im_play[33], livesX2, livesY2, 20);
                    g.drawImage(im_play[34], livesX3, livesY3, 20);
                }
            } else if (liveCount == 1) {
                if (failFruitCount == 1) {
                    g.drawImage(im_play[29], livesX1, livesY1, 20);
                    g.drawImage(im_play[30], livesX2, livesY2, 20);
                    g.drawImage(im_play[56], livesX3, livesY3, 20);
                } else if (failFruitCount == 2) {
                    g.drawImage(im_play[29], livesX1, livesY1, 20);
                    g.drawImage(im_play[30], livesX2, livesY2, 20);
                    g.drawImage(im_play[57], livesX3, livesY3, 20);
                } else {
                    g.drawImage(im_play[29], livesX1, livesY1, 20);
                    g.drawImage(im_play[30], livesX2, livesY2, 20);
                    g.drawImage(im_play[34], livesX3, livesY3, 20);
                }
            } else if (failFruitCount == 1) {
                g.drawImage(im_play[52], livesX1, livesY1, 20);
                g.drawImage(im_play[33], livesX2, livesY2, 20);
                g.drawImage(im_play[34], livesX3, livesY3, 20);
            } else if (failFruitCount == 2) {
                g.drawImage(im_play[53], livesX1, livesY1, 20);
                g.drawImage(im_play[33], livesX2, livesY2, 20);
                g.drawImage(im_play[34], livesX3, livesY3, 20);
            } else {
                g.drawImage(im_play[32], livesX1, livesY1, 20);
                g.drawImage(im_play[33], livesX2, livesY2, 20);
                g.drawImage(im_play[34], livesX3, livesY3, 20);
            }
        } else if (gamekind == arcade) {
            timeX = 161;
            timeY = 10;
            draw_time(g, im_play[35], playTime, timeY);
        }
        draw_fruits(g);
        draw_pieces(g);
        if (dflag) {
            draw_slash(g);
            dflag = false;
        }
    }

    public void run() {
        do {
            if (!bPlay) {
                break;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            width = getWidth();
            height = getHeight();
            if (!bhide) {
                time++;
                if (time > 10000) {
                    time = 0;
                }
                switch (gamestate) {
                    case 5: // '\005'
                    case 6: // '\006'
                    case 7: // '\007'
                    case 8: // '\b'
                    case 13: // '\r'
                    default:
                        break;

                    case 1: // '\001'
                        if (time > 20) {
                            Goto(2);
                        }
                        break;

                    case 2: // '\002'
                        if (tTime < 3) {
                            tTime++;
                        }
                        break;

                    case 3: // '\003'
                        if (tTime < 3) {
                            tTime++;
                        }
                        break;

                    case 12: // '\f'
                        if (tTime < 3) {
                            tTime++;
                        }
                        break;

                    case 11: // '\013'
                        if (tTime < 3) {
                            tTime++;
                        }
                        break;

                    case 4: // '\004'
                        if (gameplay && gamekind == arcade) {
                            gametime = (int) System.currentTimeMillis() / 1000;
                            fnextTime = gametime;
                            if (speedLevel > 60) {
                                if (fnextTime - fprevTime >= 2) {
                                    shootFruits();
                                    fprevTime = fnextTime;
                                }
                            } else if (fnextTime - fprevTime >= 3) {
                                shootFruits();
                                fprevTime = fnextTime;
                            }
                            if (gametime - starttime >= 1) {
                                speedLevel++;
                                playTime--;
                                starttime = gametime;
                            }
                            if (playTime >= 0) {
                                break;
                            }
                            gameplay = false;
                            if (aScore > aMaxScore) {
                                aMaxScore = aScore;
                            }
                            vecFruits.removeAllElements();
                            vecPieces.removeAllElements();
                            Goto(13);
                            playSound(1);
                            break;
                        }
                        if (!gameplay || gamekind != bomb) {
                            break;
                        }
                        gametime = (int) System.currentTimeMillis() / 1000;
                        fnextTime = gametime;
                        if (fnextTime - fprevTime >= 3) {
                            shootFruits();
                            fprevTime = fnextTime;
                        }
                        if (gametime - starttime >= 1) {
                            speedLevel++;
                            starttime = gametime;
                        }
                        if (liveCount <= 0) {
                            if (cScore > cMaxScore) {
                                cMaxScore = cScore;
                            }
                            tempstate = 4;
                            Goto(13);
                            playSound(1);
                        }
                        if (failFruitCount >= 3) {
                            liveCount--;
                            failFruitCount = 0;
                        }
                        if (!bombCollision_flag) {
                            break;
                        }
                        gameplay = false;
                        vecFruits.removeAllElements();
                        vecPieces.removeAllElements();
                        if (liveCount <= 0) {
                            if (cScore > cMaxScore) {
                                cMaxScore = cScore;
                            }
                            tempstate = 4;
                            Goto(13);
                            playSound(1);
                        } else {
                            tempstate = 4;
                            bCount = 0;
                            Goto(14);
                            playSound(6);
                        }
                        bombCollision_flag = false;
                        break;

                    case 9: // '\t'
                        if (tTime < 3) {
                            tTime++;
                        }
                        break;

                    case 14: // '\016'
                        bCount++;
                        if (bCount > 37) {
                            tempstate = -1;
                            gameplay = true;
                            Goto(4);
                        }
                        break;

                    case 10: // '\n'
                        if (pointerY == -1) {
                            break;
                        }
                        if (pointerY <= (btnY + im_help[2].getHeight() / 2) - 10 && pointerY >= barY) {
                            helpUP = true;
                            helpDOWN = false;
                            break;
                        }
                        if (pointerY >= btnY + im_help[2].getHeight() / 2 + 10 && pointerY <= barY + im_help[1].getHeight()) {
                            helpDOWN = true;
                            helpUP = false;
                        } else {
                            helpDOWN = false;
                            helpUP = false;
                        }
                        break;
                }
                repaint();
            }
        } while (true);
    }

    protected void pointerPressed(int x, int y) {
        switch (gamestate) {
            case 8: // '\b'
                pointerPressed_about(x, y);
                break;

            case 4: // '\004'
                pointerPressed_play(x, y);
                break;

            case 3: // '\003'
                pointerPressed_menu(x, y);
                break;

            case 2: // '\002'
                pointerPressed_enableSound(x, y);
                break;

            case 9: // '\t'
                pointerPressed_option(x, y);
                break;

            case 10: // '\n'
                pointerPressed_help(x, y);
                break;

            case 11: // '\013'
                pointerPressed_pause(x, y);
                break;

            case 12: // '\f'
                pointerPressed_confirm(x, y);
                break;

            case 13: // '\r'
                pointerPressed_result(x, y);
                break;
        }
    }

    void pointerPressed_enableSound(int x, int y) {
        int titleY = 0;
        int yesX1 = 0;
        int yesX2 = 0;
        int yesY1 = 0;
        int yesY2 = 0;
        int noX1 = 0;
        int noX2 = 0;
        int noY1 = 0;
        int noY2 = 0;
        bX = (width - im_common[4].getWidth()) / 2;
        bY = (height - im_common[4].getHeight()) / 2;
        titleY = bY + 40;
        yesX1 = (width - im_common[2].getWidth()) / 2;
        yesX2 = yesX1 + im_common[2].getWidth();
        yesY1 = titleY + 45;
        yesY2 = yesY1 + im_common[2].getHeight();
        noX1 = (width - im_common[3].getWidth()) / 2;
        noX2 = noX1 + im_common[3].getWidth();
        noY1 = yesY2 + 25;
        noY2 = noY1 + im_common[3].getHeight();
        if (x >= yesX1 && x <= yesX2 && y >= yesY1 && y <= yesY2) {
            option[0] = 0;
            option[1] = 0;
            Goto(3);
            startBG(2);
            playSound(0);
        } else if (x >= noX1 && x <= noX2 && y >= noY1 && y <= noY2) {
            option[0] = 1;
            option[1] = 1;
            Goto(3);
            startBG(2);
            playSound(0);
        }
    }

    void pointerPressed_about(int x, int y) {
        int backX1 = (width - im_common[1].getWidth()) / 2 + 15;
        int backX2 = backX1 + im_common[1].getWidth();
        int backY1 = ((height - im_common[1].getHeight()) / 2 + im_common[1].getHeight()) - 27;
        int backY2 = backY1 + 25;
        if (x >= backX1 && x <= backX2 && y >= backY1 && y <= backY2) {
            Goto(9);
            playSound(0);
        }
    }

    void pointerPressed_play(int x, int y) {
        int pauseX1 = 0;
        int pauseX2 = 0;
        int pauseY1 = 0;
        int pauseY2 = 0;
        pauseX1 = width - im_play[28].getWidth() - 2;
        pauseX2 = pauseX1 + im_play[28].getWidth();
        pauseY1 = height - im_play[28].getHeight() - 2;
        pauseY2 = pauseY1 + im_play[28].getHeight();
        if (x >= pauseX1 && x <= pauseX2 && y >= pauseY1 && y <= pauseY2) {
            tempstate = 4;
            pauseFlag = true;
            gameplay = false;
            Goto(11);
            playSound(0);
        } else {
            prevX = x;
            prevY = y;
            dprevTime = (int) System.currentTimeMillis();
            slashSoundFlag = true;
        }
    }

    void pointerPressed_result(int x, int y) {
        int bX = 0;
        int bY = 0;
        int titleY = 0;
        int scoreY = 0;
        int yesX1 = 0;
        int yesX2 = 0;
        int yesY1 = 0;
        int yesY2 = 0;
        int noX1 = 0;
        int noX2 = 0;
        int noY1 = 0;
        int noY2 = 0;
        bX = (width - im_common[4].getWidth()) / 2;
        bY = (height - im_common[4].getHeight()) / 2;
        titleY = bY + 40;
        scoreY = titleY + im_result[1].getHeight() + 10;
        yesX1 = (width - im_result[4].getWidth()) / 2;
        yesX2 = yesX1 + im_result[4].getWidth();
        yesY1 = scoreY + im_result[2].getHeight() + 10;
        yesY2 = yesY1 + im_result[4].getHeight();
        noX1 = (width - im_result[5].getWidth()) / 2;
        noX2 = noX1 + im_result[5].getWidth();
        noY1 = yesY2 + 5;
        noY2 = noY1 + im_result[5].getHeight();
        if (x >= yesX1 && x <= yesX2 && y >= yesY1 && y <= yesY2) {
            tempstate = -1;
            Goto(3);
            if (option[0] == 0) {
                stopBG(2);
                startBG(2);
            }
            playSound(0);
        } else if (x >= noX1 && x <= noX2 && y >= noY1 && y <= noY2) {
            aScore = 0;
            cScore = 0;
            failFruitCount = 0;
            speedLevel = 0;
            liveCount = 3;
            playTime = 120;
            gameplay = true;
            vecFruits.removeAllElements();
            vecPieces.removeAllElements();
            Goto(4);
            playSound(0);
            starttime = (int) System.currentTimeMillis() / 1000;
        }
    }

    void pointerPressed_option(int x, int y) {
        int bX = 0;
        int bY = 0;
        int titleX = 0;
        int titleY = 0;
        int musicX1 = 0;
        int musicX2 = 0;
        int musicY1 = 0;
        int musicY2 = 0;
        int soundX1 = 0;
        int soundX2 = 0;
        int soundY1 = 0;
        int soundY2 = 0;
        int vibX1 = 0;
        int vibX2 = 0;
        int vibY1 = 0;
        int vibY2 = 0;
        int aboutX1 = 0;
        int aboutX2 = 0;
        int aboutY1 = 0;
        int aboutY2 = 0;
        int exitX1 = 0;
        int exitX2 = 0;
        int exitY1 = 0;
        int exitY2 = 0;
        int interval = 40;
        bX = (width - im_common[4].getWidth()) / 2;
        bY = (height - im_common[4].getHeight()) / 2;
        titleX = (width - im_option[0].getWidth()) / 2;
        titleY = bY + 40;
        musicX1 = (width - im_option[1].getWidth()) / 2 - im_option[4].getWidth() - 10;
        musicX2 = musicX1 + im_option[4].getWidth();
        musicY1 = titleY + interval;
        musicY2 = musicY1 + im_option[3].getHeight();
        soundX1 = musicX1;
        soundX2 = musicX2;
        soundY1 = musicY1 + interval;
        soundY2 = soundY1 + im_option[2].getHeight();
        vibX1 = musicX1;
        vibX2 = musicX2;
        vibY1 = soundY1 + interval;
        vibY2 = vibY1 + im_option[3].getHeight();
        exitX1 = bX + 15;
        exitX2 = exitX1 + 21;
        exitY1 = (bY + im_common[4].getHeight()) - im_option[3].getHeight() - 1;
        exitY2 = exitY1 + im_option[4].getHeight();
        aboutX1 = (bX + im_common[4].getWidth()) - im_option[3].getWidth() - 3;
        aboutX2 = aboutX1 + im_option[3].getWidth();
        aboutY1 = exitY1;
        aboutY2 = exitY2;
        if (x >= musicX1 && x <= musicX2 && y >= musicY1 && y <= musicY2) {
            if (option[0] != 0) {
                option[0] = 0;
                playSound(0);
                if (option[0] == 0) {
                    startBG(2);
                }
            } else if (option[0] != 1) {
                option[0] = 1;
                playSound(0);
                stopBG(2);
                musicFlag = false;
            }
        } else if (x >= soundX1 && x <= soundX2 && y >= soundY1 && y <= soundY2) {
            if (option[1] != 0) {
                option[1] = 0;
                playSound(0);
                if (option[0] == 0) {
                    startBG(2);
                }
            } else if (option[1] != 1) {
                option[1] = 1;
                playSound(0);
                stopBG(2);
                musicFlag = false;
            }
        } else if (x >= vibX1 && x <= vibX2 && y >= vibY1 && y <= vibY2) {
            if (option[2] != 0) {
                option[2] = 0;
                playSound(0);
                vib(150);
            } else if (option[2] != 1) {
                option[2] = 1;
                playSound(0);
            }
        } else if (x >= aboutX1 && x <= aboutX2 && y >= aboutY1 && y <= aboutY2) {
            Goto(8);
            playSound(0);
        } else if (x >= exitX1 && x <= exitX2 && y >= exitY1 && y <= exitY2) {
            Goto(tempstate);
            tempstate = -1;
            playSound(0);
        }
    }

    void pointerPressed_help(int x, int y) {
        int helpX1 = (width - im_common[1].getWidth()) / 2;
        int helpX2 = helpX1 + im_common[1].getWidth();
        int backX1 = (width - im_common[1].getWidth()) / 2 + 15;
        int backX2 = backX1 + im_common[1].getWidth();
        int backY1 = ((height - im_common[1].getHeight()) / 2 + im_common[1].getHeight()) - 27;
        int backY2 = backY1 + 25;
        if (x >= backX1 && x <= backX2 && y >= backY1 && y <= backY2) {
            Goto(tempstate);
            tempstate = -1;
            playSound(0);
        }
        if (x >= helpX1 && x <= helpX2) {
            if (y <= btnY + im_help[2].getHeight() / 2 && y >= barY) {
                helpUP = true;
                pointerY = y;
                playSound(0);
            } else if (y >= btnY + im_help[2].getHeight() / 2 && y <= barY + viewheight) {
                helpDOWN = true;
                pointerY = y;
                playSound(0);
            }
        }
    }

    void pointerPressed_confirm(int x, int y) {
        int bX = 0;
        int bY = 0;
        int yesX1 = 0;
        int yesX2 = 0;
        int yesY1 = 0;
        int yesY2 = 0;
        int noX1 = 0;
        int noX2 = 0;
        int noY1 = 0;
        int noY2 = 0;
        int titleY = 0;
        int messageY = 0;
        bX = (width - im_common[4].getWidth()) / 2;
        bY = (height - im_common[4].getHeight()) / 2;
        titleY = bY + 40;
        messageY = titleY + im_confirm[0].getHeight() + 15;
        yesX1 = (width - im_common[2].getWidth()) / 2;
        yesX2 = yesX1 + im_common[2].getWidth();
        yesY1 = messageY + im_confirm[0].getHeight() + 10;
        yesY2 = yesY1 + im_common[2].getHeight();
        noX1 = yesX1;
        noX2 = yesX2;
        noY1 = yesY2 + 10;
        noY2 = noY1 + im_common[2].getHeight();
        if (x >= yesX1 && x <= yesX2 && y >= yesY1 && y <= yesY2) {
            if (tempstate == 11) {
                tempstate = -1;
                Goto(3);
                if (option[0] == 0) {
                    stopBG(2);
                    startBG(2);
                }
                playSound(0);
            } else {
                stopBG(2);
                mlet.destroyApp(false);
            }
        } else if (x >= noX1 && x <= noX2 && y >= noY1 && y <= noY2) {
            Goto(tempstate);
            tempstate = -1;
            playSound(0);
        }
    }

    void pointerPressed_pause(int x, int y) {
        int bY = 0;
        int titleY = 0;
        int continueX1 = 0;
        int continueX2 = 0;
        int continueY1 = 0;
        int continueY2 = 0;
        int optionX1 = 0;
        int optionX2 = 0;
        int optionY1 = 0;
        int optionY2 = 0;
        int exitX1 = 0;
        int exitX2 = 0;
        int exitY1 = 0;
        int exitY2 = 0;
        int backX1 = 0;
        int backX2 = 0;
        int backY1 = 0;
        int backY2 = 0;
        int interval = 40;
        bX = (width - im_common[4].getWidth()) / 2;
        bY = (height - im_common[4].getHeight()) / 2;
        titleY = bY + 40;
        continueX1 = (width - im_pause[1].getWidth()) / 2;
        continueX2 = continueX1 + im_pause[1].getWidth();
        continueY1 = titleY + interval;
        continueY2 = continueY1 + im_pause[1].getHeight();
        optionX1 = continueX1;
        optionX2 = continueX2;
        optionY1 = continueY1 + interval;
        optionY2 = optionY1 + im_pause[1].getHeight();
        exitX1 = continueX1;
        exitX2 = continueX2;
        exitY1 = optionY1 + interval;
        exitY2 = exitY1 + im_pause[2].getHeight();
        backX1 = bX + 15;
        backX2 = backX1 + 21;
        backY1 = (bY + im_common[2].getHeight()) - 27;
        backY2 = backY1 + 25;
        if (x >= continueX1 && x <= continueX2 && y >= continueY1 && y <= continueY2) {
            gamestate = 4;
            tempstate = -1;
            bPlay = true;
            gameplay = true;
            playSound(0);
        } else if (x >= optionX1 && x <= optionX2 && y >= optionY1 && y <= optionY2) {
            tempstate = 11;
            Goto(9);
            playSound(0);
        } else if (x >= exitX1 && x <= exitX2 && y >= exitY1 && y <= exitY2) {
            tempstate = 11;
            Goto(12);
            playSound(0);
        } else if (x >= backX1 && x <= backX2 && y >= backY1 && y <= backY2) {
            gamestate = 4;
            tempstate = -1;
            bPlay = true;
            gameplay = true;
            playSound(0);
        }
    }

    void pointerPressed_menu(int x, int y) {
        int bX = 0;
        int bY = 0;
        int cX1 = 0;
        int cX2 = 0;
        int cY1 = 0;
        int cY2 = 0;
        int aX1 = 0;
        int aX2 = 0;
        int aY1 = 0;
        int aY2 = 0;
        int oX1 = 0;
        int oX2 = 0;
        int oY1 = 0;
        int oY2 = 0;
        int exitX1 = 0;
        int exitX2 = 0;
        int exitY1 = 0;
        int exitY2 = 0;
        int helpX1 = 0;
        int helpX2 = 0;
        int helpY1 = 0;
        int helpY2 = 0;
        cX1 = 20;
        cX2 = cX1 + im_menu[0].getWidth();
        cY1 = 100;
        cY2 = cY1 + im_menu[0].getHeight();
        aX1 = width - im_menu[1].getWidth() - cX1;
        aX2 = aX1 + im_menu[1].getWidth();
        aY1 = cY1;
        aY2 = cY2;
        oX1 = (width - im_menu[1].getWidth()) / 2;
        oX2 = oX1 + im_menu[0].getWidth();
        oY1 = cY2 + 2;
        oY2 = oY1 + im_menu[1].getHeight();
        exitX1 = 5;
        exitX2 = exitX1 + im_menu[3].getWidth();
        exitY1 = height - im_menu[3].getHeight() - 5;
        exitY2 = exitY1 + im_menu[3].getHeight();
        helpX1 = width - im_menu[4].getWidth() - exitX1;
        helpX2 = helpX1 + im_menu[4].getWidth();
        helpY1 = exitY1;
        helpY2 = exitY2;
        if (x >= cX1 && x <= cX2 && y >= cY1 && y <= cY2) {
            tempstate = gamestate;
            cScore = 0;
            failFruitCount = 0;
            speedLevel = 0;
            liveCount = 3;
            gamekind = bomb;
            vecFruits.removeAllElements();
            vecPieces.removeAllElements();
            Goto(4);
            playSound(0);
            starttime = (int) System.currentTimeMillis() / 1000;
            fprevTime = starttime;
            playTime = 120;
            gameplay = true;
        } else if (x >= aX1 && x <= aX2 && y >= aY1 && y <= aY2) {
            tempstate = gamestate;
            aScore = 0;
            failFruitCount = 0;
            speedLevel = 0;
            liveCount = 3;
            gamekind = arcade;
            vecFruits.removeAllElements();
            vecPieces.removeAllElements();
            Goto(4);
            playSound(0);
            starttime = (int) System.currentTimeMillis() / 1000;
            fprevTime = starttime;
            playTime = 120;
            gameplay = true;
        } else if (x >= oX1 && x <= oX2 && y >= oY1 && y <= oY2) {
            tempstate = gamestate;
            Goto(9);
            playSound(0);
        } else if (x >= exitX1 && x <= exitX2 && y >= exitY1 && y <= exitY2) {
            System.out.println("back...");
            tempstate = 3;
            Goto(12);
            playSound(0);
        } else if (x >= helpX1 && x <= helpX2 && y >= helpY1 && y <= helpY2) {
            tempstate = 3;
            Goto(10);
            viewstart = 0;
            playSound(0);
        }
    }

    protected void pointerReleased(int x, int y) {
        switch (gamestate) {
            case 4: // '\004'
                pointerReleased_play(x, y);
                break;

            case 10: // '\n'
                pointerReleased_help(x, y);
                break;
        }
    }

    void pointerReleased_play(int x, int y) {
        prevX = 0;
        prevY = 0;
        dflag = false;
        slashSoundFlag = false;
        soundCount = 0;
    }

    void pointerReleased_help(int x, int y) {
        helpUP = false;
        helpDOWN = false;
        pointerY = -1;
    }

    protected void pointerDragged(int x, int y) {
        switch (gamestate) {
            case 4: // '\004'
                pointerDraged_play(x, y);
                break;

            case 10: // '\n'
                pointerDraged_help(x, y);
                break;
        }
    }

    void pointerDraged_play(int x, int y) {
        dnextTime = (int) System.currentTimeMillis();
        if (!dflag) {
            if (dnextTime - dprevTime > 10) {
                currentX = x;
                currentY = y;
                soundCount++;
                dprevTime = dnextTime;
                if (Math.sqrt((currentX - prevX) * (currentX - prevX) + (currentY - prevY) * (currentY - prevY)) > 30D) {
                    dflag = true;
                }
            }
            if (slashSoundFlag || soundCount > 10) {
                playSound(4);
                slashSoundFlag = false;
                soundCount = 0;
            }
        }
    }

    void pointerDraged_help(int x, int y) {
        int helpX1 = (width - im_common[1].getWidth()) / 2;
        int helpX2 = helpX1 + im_common[1].getWidth();
        if (x > helpX1 && x < helpX2) {
            pointerY = y;
        } else {
            helpUP = false;
            helpDOWN = false;
            pointerY = -1;
        }
    }

    void draw_slash(Graphics g) {
        draw_slashLine(g, prevX, prevY, currentX, currentY, 5);
        process_collision(prevX, prevY, currentX, currentY);
        prevX = currentX;
        prevY = currentY;
    }

    void draw_slashLine(Graphics g, int x1, int y1, int x2, int y2, int rate) {
        int x = 0;
        int y = 0;
        int dx = 0;
        int dy = 0;
        float sinVal = 0.0F;
        float cosVal = 0.0F;
        dx = x1 - x2;
        dy = y1 - y2;
        cosVal = (float) dx / (float) Math.sqrt(dx * dx + dy * dy);
        sinVal = -(float) dy / (float) Math.sqrt(dx * dx + dy * dy);
        Image im_slash = Resizer.resizeImage(im_play[39], (float) Math.sqrt(dx * dx + dy * dy) / (float) im_play[39].getWidth());
        rotateImage1(im_slash, sinVal, cosVal, (x2 + dx / 2) - im_slash.getWidth() / 2, (y2 + dy / 2) - im_slash.getHeight() / 2, g);
    }

    void draw_menu(Graphics g) {
        int titleX = 0;
        int titleY = 0;
        int fruitX = 0;
        int fruitY = 0;
        g.drawImage(im_common[0], 0, 0, 20);
        titleX = (width - im_common[5].getWidth()) / 2;
        titleY = 15;
        g.drawImage(im_common[5], titleX, titleY, 20);
        fruitX = (width - im_menu[5].getWidth()) / 2;
        fruitY = 250;
        g.drawImage(im_menu[5], fruitX, fruitY, 20);
        int x = 20;
        int y = 100;
        int x1 = width - im_menu[0].getWidth() - x;
        int y1 = y;
        int x2 = (width - im_menu[0].getWidth()) / 2;
        int y2 = y + im_menu[1].getHeight() + 2;
        g.drawImage(im_menu[0], x, y, 20);
        g.drawImage(im_menu[1], x1, y1, 20);
        g.drawImage(im_menu[2], x2, y2, 20);
        int x3 = 5;
        int y3 = height - im_menu[3].getHeight() - 5;
        g.drawImage(im_menu[3], x3, y3, 20);
        g.drawImage(im_menu[4], width - im_menu[4].getWidth() - x3, y3, 20);
    }

    void draw_splash(Graphics g) {
        int x = 0;
        int y = 0;
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        int x3 = 0;
        int y3 = 0;
        g.drawImage(im_common[0], bX, bY, 20);
        x = (width - im_splash[0].getWidth()) / 2;
        y = 70;
        x1 = (width - im_common[5].getWidth()) / 2;
        y1 = 65;
        x2 = (width - im_splash[2].getWidth()) / 2;
        y2 = 250;
        x3 = (width - im_splash[1].getWidth()) / 2;
        y3 = height - im_splash[1].getHeight() - 10;
        g.drawImage(im_common[5], x1, y1, 20);
        g.drawImage(im_splash[0], x, y, 20);
        g.drawImage(im_splash[2], x2, y2, 20);
        g.drawImage(im_splash[1], x3, y3, 20);
    }

    void draw_enableSound(Graphics g) {
        int x = (width - im_common[4].getWidth()) / 2;
        int y = (height - im_common[4].getHeight()) / 2;
        int x3 = (width - im_enableSound[0].getWidth()) / 2;
        int y3 = y + 40;
        g.drawImage(im_common[0], 0, 0, 20);
        g.drawImage(im_common[4], x, y, 20);
        g.drawImage(im_enableSound[0], x3, y3, 20);
        int x1 = (width - im_common[2].getWidth()) / 2;
        int y1 = y3 + 45;
        int x2 = (width - im_common[3].getWidth()) / 2;
        int y2 = y1 + im_common[2].getHeight() + 25;
        g.drawImage(im_common[2], x1, y1, 20);
        g.drawImage(im_common[3], x2, y2, 20);
    }

    void draw_option(Graphics g) {
        int x = 0;
        int y = 0;
        int x1 = 0;
        int y1 = 0;
        int opY = 0;
        int opX = 0;
        int checkX = 0;
        int checkY = 0;
        int interval = 0;
        int aboutX = 0;
        int aboutY = 0;
        g.drawImage(im_common[0], 0, 0, 20);
        x = (width - im_common[4].getWidth()) / 2;
        y = (height - im_common[4].getHeight()) / 2;
        g.drawImage(im_common[4], x, y, 20);
        x1 = (width - im_option[0].getWidth()) / 2;
        y1 = y + 40;
        g.drawImage(im_option[0], x1, y1, 20);
        interval = 40;
        opX = (width - im_option[1].getWidth()) / 2;
        opY = y1 + 40;
        checkX = opX - im_option[4].getWidth() - 10;
        checkY = opY;
        g.drawImage(im_option[2], opX, opY, 20);
        g.drawImage(im_option[1], opX, opY + interval, 20);
        g.drawImage(im_option[3], opX, opY + interval * 2, 20);
        if (option[0] == 0) {
            g.drawImage(im_option[5], checkX, checkY, 20);
        } else if (option[0] == 1) {
            g.drawImage(im_option[4], checkX, checkY, 20);
        }
        if (option[1] == 0) {
            g.drawImage(im_option[5], checkX, checkY + interval, 20);
        } else if (option[1] == 1) {
            g.drawImage(im_option[4], checkX, checkY + interval, 20);
        }
        if (option[2] == 0) {
            g.drawImage(im_option[5], checkX, checkY + interval * 2, 20);
        } else if (option[2] == 1) {
            g.drawImage(im_option[4], checkX, checkY + interval * 2, 20);
        }
        aboutX = (x + im_common[4].getWidth()) - im_option[6].getWidth() - 3;
        aboutY = (y + im_common[4].getHeight()) - im_option[6].getHeight() - 1;
        g.drawImage(im_option[6], aboutX, aboutY, 20);
    }

    void draw_help(Graphics g) {
        int textX = 27;
        int textY = 70;
        int barLength = viewheight;
        g.drawImage(im_common[0], 0, 0, 20);
        int x = (width - im_common[1].getWidth()) / 2;
        int y = (height - im_common[1].getHeight()) / 2;
        int x1 = (width - im_help[0].getWidth()) / 2;
        int y1 = y + 35;
        g.drawImage(im_common[1], x, y, 20);
        g.drawImage(im_help[0], x1, y1, 20);
        g.drawRegion(im_help[3], 0, viewstart, im_help[3].getWidth(), viewheight, 0, x + textX, y + textY + 12, 20);
        int barX = 195;
        barY = (y + textY) - 5;
        g.drawImage(im_help[1], barX, barY, 20);
        btnX = barX + 8;
        int startbtnY = barY + 20;
        int endbtnY = ((barY + viewheight) - im_help[2].getHeight()) + 5;
        int movelength = endbtnY - startbtnY;
        int viewtotal = im_help[3].getHeight();
        btnY = startbtnY + (movelength * viewstart) / (viewtotal - viewheight);
        g.drawImage(im_help[2], btnX, btnY, 20);
        if (helpUP) {
            viewstart -= 10;
            if (viewstart < 0) {
                viewstart = 0;
            }
        }
        if (helpDOWN) {
            viewstart += 10;
            if (viewstart > viewtotal - viewheight) {
                viewstart = viewtotal - viewheight;
            }
        }
    }

    void draw_pause(Graphics g) {
        int interval = 0;
        int itemX = 0;
        int itemY = 0;
        int itemX1 = 0;
        int itemX2 = 0;
        if (pauseFlag) {
            initGraphics();
            pauseFlag = false;
        }
        g.drawImage(im_buffer, 0, 0, 20);
        int x = (width - im_common[4].getWidth()) / 2;
        int y = (height - im_common[4].getHeight()) / 2;
        g.drawImage(im_common[4], x, y, 20);
        int x1 = (width - im_pause[0].getWidth()) / 2;
        int y1 = y + 40;
        g.drawImage(im_pause[0], x1, y1, 20);
        interval = 40;
        itemX = (width - im_pause[1].getWidth()) / 2;
        itemY = y1 + 40;
        itemX1 = (width - im_pause[2].getWidth()) / 2;
        itemX2 = (width - im_pause[3].getWidth()) / 2;
        g.drawImage(im_pause[1], itemX, itemY, 20);
        g.drawImage(im_pause[2], itemX1, itemY + interval, 20);
        g.drawImage(im_pause[3], itemX2, itemY + interval * 2, 20);
    }

    void draw_bombfail(Graphics g) {
        int x = 0;
        int y = 0;
        x = width;
        y = (height - im_bombfail[0].getHeight()) / 2;
        draw_play(g);
        g.drawImage(im_bombfail[0], width - 6 * bCount, y, 20);
    }

    void draw_confirm(Graphics g) {
        if (!bPlay) {
            return;
        } else {
            g.drawImage(im_common[0], 0, 0, 20);
            int x = (width - im_common[4].getWidth()) / 2;
            int y = (height - im_common[4].getHeight()) / 2;
            g.drawImage(im_common[4], x, y, 20);
            int x3 = (width - im_confirm[0].getWidth()) / 2;
            int y3 = y + 40;
            g.drawImage(im_confirm[0], x3, y3, 20);
            int x4 = (width - im_confirm[1].getWidth()) / 2;
            int y4 = y3 + im_confirm[0].getHeight() + 15;
            g.drawImage(im_confirm[1], x4, y4, 20);
            int x1 = (width - im_common[2].getWidth()) / 2;
            int y1 = y4 + im_confirm[0].getHeight() + 10;
            int x2 = (width - im_common[3].getWidth()) / 2;
            int y2 = y1 + im_common[2].getHeight() + 10;
            g.drawImage(im_common[2], x1, y1, 20);
            g.drawImage(im_common[3], x2, y2, 20);
            return;
        }
    }

    void clearProcess() {
        aScore = 0;
        cScore = 0;
    }

    void File_Save() throws RecordStoreException, Exception {
        String SNAME = "FruitMania";
        db = RecordStore.openRecordStore(SNAME, true);
        byte wBuf[] = new byte[100];
        int POS = 0;
        int RE = 0;
        POS = write_Int(option[0], wBuf, POS);
        POS = write_Int(option[1], wBuf, POS);
        POS = write_Int(option[2], wBuf, POS);
        POS = write_Int(cMaxScore, wBuf, POS);
        POS = write_Int(aMaxScore, wBuf, POS);
        if (db.getNumRecords() == 0) {
            RE = db.addRecord(wBuf, 0, POS);
        } else {
            db.setRecord(1, wBuf, 0, POS);
        }
        Exception e;

        db.closeRecordStore();
        db = null;
        System.gc();

        // Misplaced declaration of an exception variable

        // break MISSING_BLOCK_LABEL_206;

        //e.printStackTrace();
        db.closeRecordStore();
        db = null;
        System.gc();

        // Misplaced declaration of an exception variable


        db.closeRecordStore();
        db = null;
        System.gc();

    }

    void File_Load() throws RecordStoreException, IOException, Exception {
        db = RecordStore.openRecordStore("FruitMania", false);
        ByteArrayInputStream bai = new ByteArrayInputStream(db.getRecord(1));
        DataInputStream dis = new DataInputStream(bai);
        option = new int[3];
        option[0] = dis.readInt();
        option[1] = dis.readInt();
        option[2] = dis.readInt();
        cMaxScore = dis.readInt();
        aMaxScore = dis.readInt();
        Exception e;


        db.closeRecordStore();
        db = null;
        System.gc();

        // Misplaced declaration of an exception variable

        db.closeRecordStore();
        db = null;
        System.gc();

        // Misplaced declaration of an exception variable
        db.closeRecordStore();
        db = null;
        System.gc();

    }

    int write_Int(int val, byte Dec[], int Pointer) {
        Dec[Pointer++] = (byte) ((val & 0xff000000) >> 24);
        Dec[Pointer++] = (byte) ((val & 0xff0000) >> 16);
        Dec[Pointer++] = (byte) ((val & 0xff00) >> 8);
        Dec[Pointer++] = (byte) (val & 0xff);
        return Pointer;
    }

    int write_Byte(byte val, byte Dec[], int Pointer) {
        Dec[Pointer++] = val;
        return Pointer;
    }

    void initGraphics() {
        int w = 0;
        int h = 0;
        w = 240;
        h = 320;
        im_buffer = Image.createImage(w, h);
        g2 = im_buffer.getGraphics();
        int rgbImage[] = new int[w * h];
        g2.setClip(0, 0, w, h);
        draw_play(g2);
        im_buffer.getRGB(rgbImage, 0, w, 0, 0, w, h);
        for (int i = 0; i < rgbImage.length; i++) {
            int color = rgbImage[i] & 0xffffff;
            int oldAlpha = rgbImage[i] & 0xff000000;
            int newAlpha = 0;
            int newred = 0;
            int newgreen = 0;
            int newblue = 0;
            int oldred = rgbImage[i] & 0xff0000;
            int oldgreen = rgbImage[i] & 0xff00;
            int oldblue = rgbImage[i] & 0xff;
            oldred >>>= 16;
            oldgreen >>>= 8;
            oldblue = oldblue;
            oldAlpha >>>= 24;
            newAlpha = (oldAlpha * 5) / 5;
            newred = (oldred * 5) / 15;
            newgreen = (oldgreen * 5) / 15;
            newblue = (oldblue * 5) / 15;
            newred <<= 16;
            newgreen <<= 8;
            newAlpha <<= 24;
            color = newAlpha + newgreen + newred + newblue;
            rgbImage[i] = color;
        }

        g2.drawImage(Image.createRGBImage(rgbImage, w, h, true), 0, 0, 20);
    }

    void vib(int time) {
        if (option[2] == 0 && mlet != null) {
            FruitMania _tmp = mlet;
            FruitMania.display.vibrate(time);
        }
    }

    protected void showNotify() {
        if (bhide) {
            bhide = false;
            if (gamestate == 4 && gameplay) {
                Goto(11);
                pauseFlag = true;
                flag = false;
                tTime = 0;
                sel_menu = 0;
            }
            if (gamestate == 3 || gamestate == 10 || gamestate == 9 && tempstate == 3 || gamestate == 12 && tempstate == 3) {
                startBG(2);
            }
        }
    }

    protected void hideNotify() {
        if (!bhide) {
            bhide = true;
            stopBG(2);
            musicFlag = false;
        }
    }

    void shootFruits() {
        int nFruit = 0;
        int nKind = 0;
        int id = 0;
        int x0 = startX;
        int y0 = startY;
        int n = 0;
        if (gamekind == bomb) {
            if (speedLevel <= 60) {
                nFruit = getRandomInt(1, 3);
            } else if (speedLevel > 60 && speedLevel <= 120) {
                nFruit = getRandomInt(2, 4);
            } else if (speedLevel > 120) {
                nFruit = getRandomInt(3, 7);
            }
        } else if (gamekind == arcade) {
            if (playTime >= 105) {
                nFruit = getRandomInt(1, 3);
            } else if (playTime < 105 && playTime >= 90) {
                nFruit = getRandomInt(2, 5);
            } else if (playTime < 90) {
                nFruit = getRandomInt(3, 5);
            }
        }
        if (nFruit == 0) {
            nFruit = 1;
        }
        for (int i = 0; i < nFruit; i++) {
            Fruit fruits = new Fruit();
            x0 = getRandomInt(15, 200);
            if (gamekind == bomb) {
                id = rand.nextInt(10);
                if (id == 9) {
                    fruits.id = 27;
                } else {
                    fruits.id = id;
                }
            } else if (gamekind == arcade) {
                id = rand.nextInt(9);
                fruits.id = id;
            }
            fruits.width = im_play[id].getWidth() - 2;
            fruits.height = im_play[id].getHeight() - 2;
            if (x0 < 75) {
                fruits.angle = getRandomInt(80, 90);
            } else if (x0 >= 135) {
                fruits.angle = getRandomInt(90, 100);
            } else {
                fruits.angle = 90;
            }
            if (gamekind == bomb) {
                if (speedLevel <= 60) {
                    fruits.v = getRandomInt(160, 180);
                    fruits.gN = 60;
                } else if (speedLevel > 60 && speedLevel <= 120) {
                    fruits.v = getRandomInt(260, 285);
                    fruits.gN = 147;
                } else if (speedLevel > 120) {
                    fruits.v = getRandomInt(310, 330);
                    fruits.gN = 176;
                }
            } else if (gamekind == arcade) {
                if (playTime > 105) {
                    fruits.v = getRandomInt(160, 180);
                    fruits.gN = 60;
                } else if (playTime <= 105 && playTime >= 90) {
                    fruits.v = getRandomInt(260, 285);
                    fruits.gN = 150;
                } else if (playTime < 90 && playTime >= 60) {
                    fruits.v = getRandomInt(300, 320);
                    fruits.gN = 184;
                } else if (playTime < 60) {
                    fruits.v = getRandomInt(330, 340);
                    fruits.gN = 205;
                }
            }
            fruits.x0 = x0;
            fruits.y0 = y0;
            fruits.liveFlag = true;
            fruits.rotateAngle = 0.0D;
            fruits.ltime = System.currentTimeMillis();
            vecFruits.addElement(fruits);
            fruits = null;
        }

    }

    void draw_fruits(Graphics g1) {
        int num = 0;
        int j = 0;
        double t = 0.0D;
        Object obj;
        for (Enumeration e = vecFruits.elements(); e.hasMoreElements(); obj = null) {
            Fruit f = new Fruit();
            f = (Fruit) e.nextElement();
            if (!f.liveFlag) {
                continue;
            }
            t = ((double) System.currentTimeMillis() - (double) f.ltime) / 1000D;
            f.x = (int) ((double) f.x0 + (double) f.v * Math.cos(Math.toRadians(f.angle)) * t);
            f.y = (int) (((double) f.y0 - (double) f.v * Math.sin(Math.toRadians(f.angle)) * t) + ((double) f.gN * t * t) / 2D);
            f.rotateAngle = f.rotateAngle + (double) getRandomInt(5, 15);
            rotateImage(im_play[f.id], (float) Math.sin(Math.toRadians(f.rotateAngle)), (float) Math.cos(Math.toRadians(f.rotateAngle)), f.x, f.y, g1);
            if (f.y > startY && f.id != 27) {
                failFruitCount++;
                f.liveFlag = false;
            }
        }

        for (Enumeration e = vecFruits.elements(); e.hasMoreElements();) {
            Fruit f = new Fruit();
            f = (Fruit) e.nextElement();
            if (!f.liveFlag) {
                vecFruits.removeElement(f);
            }
            f = null;
        }

    }

    void draw_pieces(Graphics g1) {
        int num = 0;
        int x = 0;
        int y = 0;
        double t = 0.0D;
        Object obj;
        for (Enumeration e = vecPieces.elements(); e.hasMoreElements(); obj = null) {
            Fruit p = new Fruit();
            p = (Fruit) e.nextElement();
            if (!p.piece_flag) {
                continue;
            }
            t = ((double) System.currentTimeMillis() - (double) p.ltime) / 1000D;
            p.x = p.x0;
            p.y = (int) ((double) p.y0 + ((double) p.gN * t * t) / 2D);
            if (p.scatter != 0) {
                g1.drawImage(im_play[p.scatter_id], p.x0, p.y0, 20);
                p.scatter--;
            }
            if (bonusFlag && p.scatter != 0) {
                g1.drawImage(im_play[51], bonusX, bonusY, 20);
                draw_num(g1, im_play[36], bonus, bonusX + 30, bonusY, im_play[36].getWidth() / 10, im_play[36].getHeight(), 2);
                if (!bSound) {
                    playSound(5);
                    bSound = true;
                }
            }
            if (p.scatter == 0) {
                bonusFlag = false;
                bonusX = 0;
                bonusY = 0;
            }
            g1.drawImage(im_play[p.id], p.x - 20, p.y - 10, 20);
            g1.drawImage(im_play[p.id + 9], p.x + 20, p.y + 5, 20);
            if (p.y > startY) {
                p.piece_flag = false;
            }
        }

        for (Enumeration e = vecFruits.elements(); e.hasMoreElements();) {
            Fruit p = new Fruit();
            p = (Fruit) e.nextElement();
            if (!p.liveFlag) {
                vecFruits.removeElement(p);
            }
            p = null;
        }

    }

    void process_collision(int x1, int y1, int x2, int y2) {
        int n = 0;
        int k = 0;
        int bnX = 0;
        int bnY = 0;
        n = vecFruits.size();
        for (int i = 0; i < n; i++) {
            Fruit f = new Fruit();
            f = (Fruit) vecFruits.elementAt(i);
            if (f.liveFlag && !f.piece_flag && RectangleLineIntersect.intersectsLine(x1, y1, x2, y2, f.x, f.y, f.width, f.height)) {
                if (gamekind == bomb && f.id == 27) {
                    f.liveFlag = false;
                    bombCollision_flag = true;
                    liveCount--;
                    break;
                }
                sameCount++;
                if (gamekind == bomb) {
                    cScore = cScore + 4;
                } else if (gamekind == arcade) {
                    aScore = aScore + 4;
                }
                f.liveFlag = false;
                Fruit p = new Fruit();
                p.piece_flag = true;
                p.id = f.id + 9;
                p.scatter_id = f.id + 41;
                p.ltime = System.currentTimeMillis();
                p.x0 = f.x;
                p.y0 = f.y;
                p.scatter = 20;
                p.gN = f.gN;
                bnX = p.x0 - 30;
                bnY = p.y0 - 30;
                vecPieces.addElement(p);
                p = null;
                collision_flag = true;
                playSound(3);
            }
            f = null;
        }

        if (sameCount >= 2) {
            bonus = 10 * (sameCount - 1);
            if (gamekind == bomb) {
                cScore = cScore + bonus;
            } else if (gamekind == arcade) {
                aScore = aScore + bonus;
            }
            bonusFlag = true;
            bonusX = bnX;
            bonusY = bnY;
            bSound = false;
        }
        sameCount = 0;
        for (Enumeration e = vecFruits.elements(); e.hasMoreElements();) {
            Fruit f = new Fruit();
            f = (Fruit) e.nextElement();
            if (!f.liveFlag) {
                vecFruits.removeElement(f);
            }
            f = null;
        }

    }

    public void rotateImage(Image src, float sinVal, float cosVal, int cx, int cy, Graphics g) {
        int w1 = src.getWidth();
        int h1 = src.getHeight();
        int x = w1 / 2;
        int y = h1 / 2;
        int srcMap[] = new int[w1 * h1];
        src.getRGB(srcMap, 0, w1, 0, 0, w1, h1);
        int dx = x <= w1 / 2 ? w1 - x : x;
        int dy = y <= h1 / 2 ? h1 - y : y;
        double dr = Math.sqrt(dx * dx + dy * dy);
        int wh2 = (int) (2D * dr);
        int destMap[] = new int[wh2 * wh2];
        for (int i = 0; i < w1; i++) {
            for (int j = 0; j < h1; j++) {
                if (srcMap[j * w1 + i] >> 24 != 0) {
                    double destX = dr + (double) ((float) (i - x) * cosVal) + (double) ((float) (j - y) * sinVal);
                    double destY = (dr + (double) ((float) (j - y) * cosVal)) - (double) ((float) (i - x) * sinVal);
                    destMap[(int) destY * wh2 + (int) destX] = srcMap[j * w1 + i];
                    destMap[wh2 * (int) destY + (int) destX + 1] = srcMap[j * w1 + i];
                }
            }

        }

        int dd[] = new int[2];
        dd[0] = (int) ((double) x - dr);
        dd[1] = (int) ((double) y - dr);
        g.drawRGB(destMap, 0, wh2, dd[0] + cx, dd[1] + cy, wh2, wh2, true);
    }

    public void rotateImage1(Image src, float sinVal, float cosVal, int cx, int cy, Graphics g) {
        int w1 = src.getWidth();
        int h1 = src.getHeight();
        int x = w1 / 2;
        int y = h1 / 2;
        int srcMap[] = new int[w1 * h1];
        src.getRGB(srcMap, 0, w1, 0, 0, w1, h1);
        int dx = x <= w1 / 2 ? w1 - x : x;
        int dy = y <= h1 / 2 ? h1 - y : y;
        double dr = Math.sqrt(dx * dx + dy * dy);
        int wh2 = (int) (2D * dr);
        int destMap[] = new int[wh2 * wh2];
        int idx = 0;
        for (int i = 0; i < w1; i++) {
            for (int j = 0; j < h1; j++) {
                if (srcMap[j * w1 + i] >> 24 == 0) {
                    continue;
                }
                double destX = dr + (double) ((float) (i - x) * cosVal) + (double) ((float) (j - y) * sinVal);
                double destY = (dr + (double) ((float) (j - y) * cosVal)) - (double) ((float) (i - x) * sinVal);
                idx = (int) destY * wh2 + (int) destX;
                if (idx <= wh2 * wh2) {
                    destMap[idx] = srcMap[j * w1 + i];
                    destMap[wh2 * (int) destY + (int) destX + 1] = srcMap[j * w1 + i];
                }
            }

        }

        int dd[] = new int[2];
        dd[0] = (int) ((double) x - dr);
        dd[1] = (int) ((double) y - dr);
        g.drawRGB(destMap, 0, wh2, dd[0] + cx, dd[1] + cy, wh2, wh2, true);
    }
    public static final int KEY_DOWN = -2;
    public static final int KEY_LEFT = -3;
    public static final int KEY_RIGHT = -4;
    public static final int KEY_UP = -1;
    public static final int KEY_SELECT = -5;
    public static final int KEY_R = -7;
    public static final int KEY_L = -6;
    public static FruitMania mlet;
    public boolean musicFlag;
    public static int width_240 = 240;
    public static int height_320 = 320;
    public boolean bPlay;
    Vector vecFruits;
    Vector vecPieces;
    public static final int gm_splash = 1;
    public static final int gm_enableSound = 2;
    public static final int gm_menu = 3;
    public static final int gm_play = 4;
    public static final int gm_continue = 5;
    public static final int gm_new_game = 6;
    public static final int gm_about = 8;
    public static final int gm_option = 9;
    public static final int gm_help = 10;
    public static final int gm_pause = 11;
    public static final int gm_confirm = 12;
    public static final int gm_result = 13;
    public static final int gm_bombfail = 14;
    public int gamestate;
    public int tempstate;
    public static int bomb = 1;
    public static int arcade = 2;
    double gN;
    int gamekind;
    int failFruitCount;
    int liveCount;
    boolean collision_flag;
    boolean bombCollision_flag;
    int slashCount;
    Image im_splash[];
    Image im_common[];
    Image im_menu[];
    Image im_option[];
    Image im_confirm[];
    Image im_help[];
    Image im_about[];
    Image im_play[];
    Image im_pause[];
    Image im_result[];
    Image im_score[];
    Image im_enableSound[];
    Image im_bombfail[];
    int width;
    int height;
    int step;
    int time;
    int gametime;
    int starttime;
    int speedLevel;
    public static int fruitNum = 7;
    Fruit fruitSet[];
    Slash slash;
    int sleepTime;
    Random rand;
    int fprevTime;
    int fnextTime;
    boolean bselected;
    boolean win;
    boolean gameplay;
    int cScore;
    int aScore;
    int aMaxScore;
    int cMaxScore;
    int option[] = {
        1, 1, 0
    };
    boolean bStand;
    int bX;
    int bY;
    int bCount;
    Sound snd[];
    byte imgBuf[];
    int tmptime;
    int bonuscount;
    int bonus;
    int digit[];
    boolean soundeffect;
    boolean cflag;
    int j;
    int k;
    int s;
    int autoSound;
    boolean flag;
    boolean keyFlag;
    boolean upflag;
    boolean downflag;
    boolean leftflag;
    boolean rightflag;
    int playTime;
    boolean timeUpFlag;
    int pointX;
    int pointY;
    boolean gbflag;
    int dx;
    int dy;
    int sx;
    int sy;
    boolean up;
    boolean down;
    boolean left;
    boolean right;
    int vtime;
    int startdX;
    int startdY;
    boolean slashSoundFlag;
    boolean bloading;
    boolean helpUP;
    boolean helpDOWN;
    int pointerY;
    int dprevTime;
    int dnextTime;
    int prevX;
    int prevY;
    int currentX;
    int currentY;
    boolean dflag;
    boolean comDflag;
    int soundCount;
    int ltime;
    int delta;
    int viewstart;
    int viewheight;
    int btnX;
    int btnY;
    int barY;
    int pos1[][];
    int pos2[][];
    int pos[][];
    int sel_menu;
    boolean pauseFlag;
    int confirm_res;
    int tTime;
    RecordStore db;
    Graphics g2;
    Graphics g3;
    Image im_buffer;
    Image im_buffer_car;
    public boolean bhide;
    int startX;
    int startY;
    int u;
    int angry;
    boolean bSound;
    int ccount;
    boolean bonusFlag;
    int bonusX;
    int bonusY;
    int sameCount;
}
