package com.bellone.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Impostazioni {

    public static final String imgTurnBack = "turnBack.png";
    public static final String imgMosseVisibili = "mosseVisibili.png";
    public static final String imgPezziOpposti = "pezziOpposti.png";
    public static final String imgPezziUguali = "pezziUguali.png";
    public static final String imgAttivo = "attivo.png";
    public static final String imgDisattivo = "disattivo.png";
    public static final String imgScrPezzi = "dispPezzi.png";
    public static final String imgScrMosse = "visibili.png";

    private final Texture t_turnBack;
    private final Texture t_mosseVisibili;
    private final Texture t_pezziOpposti;
    private final Texture t_pezziUguali;
    private final Texture t_attivo;
    private final Texture t_disattivo;
    private final Texture t_scrPezzi;
    private final Texture t_scrMosse;


    private final SpriteBatch batch;

    private final int WIDTH_TURN_BACK;
    private final int HEIGHT_TURN_BACK;
    private final int X_BACK;
    private final int Y_BACK;

    private final int WIDTH_SCRITTE;
    private final int HEIGHT_SCRITTE;
    private final int X_SCRITTE;
    private final int Y_SCR_PEZZI;
    private final int Y_SCR_MOSSE;

    private final int WIDTH_PEZZI;
    private final int HEIGHT_PEZZI;
    private final int X_PEZZI;
    private final int X_CHECK;
    private final int X_PEZZI2;
    private final int X_CHECK2;
    private final int Y_PEZZI;

    private final int WIDTH_DIS_ATTIVO;
    private final int HEIGHT_DIS_ATTIVO;

    private final int WIDTH_MOSSE;
    private final int HEIGHT_MOSSE;
    private final int X_MOSSE;
    private final int Y_CHECK3;
    private final int Y_MOSSE;
    private final int WIDTH_DIS_ATTIVO2;
    private final int HEIGHT_DIS_ATTIVO2;


    public Impostazioni(SpriteBatch batch) {
        this.batch = batch;

        t_turnBack = new Texture(imgTurnBack);
        t_mosseVisibili = new Texture(imgMosseVisibili);
        t_pezziOpposti = new Texture(imgPezziOpposti);
        t_pezziUguali = new Texture(imgPezziUguali);
        t_attivo = new Texture(imgAttivo);
        t_disattivo = new Texture(imgDisattivo);
        t_scrPezzi = new Texture(imgScrPezzi);
        t_scrMosse = new Texture(imgScrMosse);


        //Sul mio tel (width = 1080): 200
        WIDTH_TURN_BACK = (int) (Gdx.graphics.getWidth()/5.4);
        //Sul mio tel (width = 1080): 150
        HEIGHT_TURN_BACK = (int) (WIDTH_TURN_BACK/1.33);

        //Sul mio tel (width = 1080): 300
        WIDTH_PEZZI = (int) (Gdx.graphics.getWidth()/3.6);
        //Sul mio tel (width = 1080): 330
        HEIGHT_PEZZI = (int) (WIDTH_PEZZI*1.1);

        //Sul mio tel (width = 1080): 120
        HEIGHT_DIS_ATTIVO = Gdx.graphics.getWidth()/9;
        //Sul mio tel (width = 1080): 110
        WIDTH_DIS_ATTIVO = (int) (HEIGHT_DIS_ATTIVO/1.091);

        //Sul mio tel (width = 1080): 360
        HEIGHT_MOSSE = Gdx.graphics.getWidth()/3;
        //Sul mio tel (width = 1080): 460
        WIDTH_MOSSE = (int) (HEIGHT_MOSSE*1.27);

        //Sul mio tel (width = 1080): 140
        WIDTH_DIS_ATTIVO2 = (int) (Gdx.graphics.getWidth()/7.7);
        //Sul mio tel (width = 1080): 145
        HEIGHT_DIS_ATTIVO2 = (int) (WIDTH_DIS_ATTIVO2*1.035);

        //Sul mio tel (width = 1080): 20
        X_BACK = Gdx.graphics.getWidth()/54;
        //Sul mio tel (width = 1080): 20
        Y_BACK = Gdx.graphics.getHeight() - Gdx.graphics.getWidth()/54 - HEIGHT_TURN_BACK;

        //Sul mio tel (width = 1080): 80
        X_PEZZI = (int) (Gdx.graphics.getWidth()/2 - WIDTH_PEZZI - Gdx.graphics.getWidth()/13.5);
        //Sul mio tel (width = 1080): 40
        X_CHECK = X_PEZZI + WIDTH_PEZZI/2 + Gdx.graphics.getWidth()/27;
        //Sul mio tel (width = 1080): 80
        X_PEZZI2 = (int) (Gdx.graphics.getWidth()/2 + Gdx.graphics.getWidth()/13.5);
        //Sul mio tel (width = 1080): 80
        X_CHECK2 = X_PEZZI2 + WIDTH_PEZZI/2 + Gdx.graphics.getWidth()/27;
        //Sul mio tel (width = 1080): 200
        Y_PEZZI = (int) (Gdx.graphics.getHeight()/2 + Gdx.graphics.getWidth()/5.4);
        //Sul mio tel (width = 1080): 200

        WIDTH_SCRITTE = (int) ((X_PEZZI2+WIDTH_PEZZI) - X_PEZZI - Gdx.graphics.getWidth()/5.4);
        //Sul mio tel (width = 1080): 10
        HEIGHT_SCRITTE = WIDTH_SCRITTE/4 - Gdx.graphics.getWidth()/108;

        X_SCRITTE = Gdx.graphics.getWidth()/2 - WIDTH_SCRITTE /2;
        //Sul mio tel (width = 1080): 50
        Y_SCR_PEZZI = (int) (Y_PEZZI + HEIGHT_PEZZI + Gdx.graphics.getWidth()/21.6);
        X_MOSSE = Gdx.graphics.getWidth()/2 - WIDTH_MOSSE/2;
        //Sul mio tel (width = 1080): 200
        Y_MOSSE = (int) (Gdx.graphics.getHeight()/2 - HEIGHT_MOSSE - Gdx.graphics.getWidth()/5.4);
        //Sul mio tel (width = 1080): 215
        Y_CHECK3 = Y_MOSSE + Gdx.graphics.getWidth()/5;
        //Sul mio tel (width = 1080): 50
        Y_SCR_MOSSE = (int) (Y_MOSSE + HEIGHT_MOSSE + Gdx.graphics.getWidth()/21.6);
    }

    public void disegna(){
        batch.begin();
        batch.draw(t_turnBack, X_BACK, Y_BACK, WIDTH_TURN_BACK, HEIGHT_TURN_BACK);
        batch.end();

        batch.begin();
        batch.draw(t_scrPezzi, X_SCRITTE, Y_SCR_PEZZI, WIDTH_SCRITTE, HEIGHT_SCRITTE);
        batch.end();
        batch.begin();
        batch.draw(t_pezziUguali, X_PEZZI, Y_PEZZI, WIDTH_PEZZI, HEIGHT_PEZZI);
        batch.end();

        batch.begin();
        batch.draw(t_pezziOpposti, X_PEZZI2, Y_PEZZI, WIDTH_PEZZI, HEIGHT_PEZZI);
        batch.end();

        batch.begin();
        batch.draw(t_scrMosse, X_SCRITTE, Y_SCR_MOSSE, WIDTH_SCRITTE, HEIGHT_SCRITTE);
        batch.end();
        batch.begin();
        batch.draw(t_mosseVisibili, X_MOSSE, Y_MOSSE, WIDTH_MOSSE, HEIGHT_MOSSE);
        batch.end();


        batch.begin();
        batch.draw(t_attivo, (MyGdxGame.pezziOpposti ? X_CHECK2 : X_CHECK), Y_PEZZI, WIDTH_DIS_ATTIVO, HEIGHT_DIS_ATTIVO);
        batch.end();
        batch.begin();
        batch.draw(t_disattivo, (!MyGdxGame.pezziOpposti ? X_CHECK2 : X_CHECK), Y_PEZZI, WIDTH_DIS_ATTIVO, HEIGHT_DIS_ATTIVO);
        batch.end();


        batch.begin();
        batch.draw((MyGdxGame.mosseVisibili ? t_attivo : t_disattivo),
                X_MOSSE, Y_CHECK3, WIDTH_DIS_ATTIVO2, HEIGHT_DIS_ATTIVO2);
        batch.end();
    }

    public boolean clickBack(float mx, float my){
        Rectangle rect = new Rectangle(X_BACK, Y_BACK, WIDTH_TURN_BACK, HEIGHT_TURN_BACK);
        return rect.contains(mx, my);
    }
    public boolean clickPezziUguali(float mx, float my){
        Rectangle rect = new Rectangle(X_PEZZI, Y_PEZZI, WIDTH_PEZZI, HEIGHT_PEZZI);
        return rect.contains(mx, my);
    }
    public boolean clickPezziOpposti(float mx, float my){
        Rectangle rect = new Rectangle(X_PEZZI2, Y_PEZZI, WIDTH_PEZZI, HEIGHT_PEZZI);
        return rect.contains(mx, my);
    }
    public boolean clickMosseVisibili(float mx, float my){
        Rectangle rect = new Rectangle(X_MOSSE, Y_MOSSE, WIDTH_MOSSE, HEIGHT_MOSSE);
        return rect.contains(mx, my);
    }
}
