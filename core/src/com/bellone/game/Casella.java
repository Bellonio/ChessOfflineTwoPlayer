package com.bellone.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.bellone.game.Scacchiera.b_tTorre;
import static com.bellone.game.Scacchiera.n_tTorre;
import static com.bellone.game.Scacchiera.b_tCavallo;
import static com.bellone.game.Scacchiera.n_tCavallo;
import static com.bellone.game.Scacchiera.b_tAlfiere;
import static com.bellone.game.Scacchiera.n_tAlfiere;
import static com.bellone.game.Scacchiera.b_tRegina;
import static com.bellone.game.Scacchiera.n_tRegina;
import static com.bellone.game.Scacchiera.b_tRe;
import static com.bellone.game.Scacchiera.n_tRe;
import static com.bellone.game.Scacchiera.b_tPedone;
import static com.bellone.game.Scacchiera.n_tPedone;

public class Casella {

    public static final int[] casellaNeraColor = new int[]{100, 60, 20};
    public static final int[] casellaBiancaColor = new int[]{218, 165, 32};

    public static int WIDTH_PED;
    public static final int ORIGINAL_W_H_PEDINA = 150;

    public static final int DIVISORE = 2;
    //Gli oggetti Casella li creo in Scacchiera, quando è già stato lanciato
    // il metodo create() di MyGdxGame. Quindi posso usare senza problemi
    // Gdx. ...
    //Sul mio tel (width = 1080): 20
    public static final int WIDTH_CASELLA = Gdx.graphics.getWidth()/8 - Gdx.graphics.getWidth()/54;

    public int numCasella;
    public int x;
    public int y;
    private final int[] color;
    public int codPezzoSopra;
    public boolean pezzo_bianco;
    public boolean mosse;
    public boolean mossaMangia;
    public boolean evidenzia;
    public boolean arroccoDx;
    public boolean arroccoSx;

    public boolean scaccoAlRe;
    public boolean scacco;

    public Casella(int num, int posX, int posY, int[] c) {
        //Sul mio tel (width = 1080): 20
        WIDTH_PED = (int) (WIDTH_CASELLA - (Gdx.graphics.getWidth()/30.85));

        numCasella = num;
        x = posX;
        y = posY;
        color = c;

        mosse = false;
        mossaMangia = false;
        evidenzia = false;
        scaccoAlRe = false;
        scacco = false;
        arroccoDx = false;
        arroccoSx = false;
        codPezzoSopra = -1;

        //Imposta la giusta pedina per la casella
        if(numCasella <= 8){
            pezzo_bianco = true;

            switch (numCasella){
                case 1:
                    codPezzoSopra = 1;
                    break;
                case 2:
                    codPezzoSopra = 2;
                    break;
                case 3:
                    codPezzoSopra = 3;
                    break;
                case 4:
                    codPezzoSopra = 4;
                    break;
                case 5:
                    codPezzoSopra = 5;
                    break;
                case 6:
                    codPezzoSopra = 3;
                    break;
                case 7:
                    codPezzoSopra = 2;
                    break;
                case 8:
                    codPezzoSopra = 1;
                    break;
                default:
                    break;
            }
        }else if(numCasella <= 16){
            pezzo_bianco = true;

            codPezzoSopra = 6;
        }else if(numCasella >= 57){
            pezzo_bianco = false;

            switch (numCasella){
                case 57:
                    codPezzoSopra = 1;
                    break;
                case 58:
                    codPezzoSopra = 2;
                    break;
                case 59:
                    codPezzoSopra = 3;
                    break;
                case 60:
                    codPezzoSopra = 4;
                    break;
                case 61:
                    codPezzoSopra = 5;
                    break;
                case 62:
                    codPezzoSopra = 3;
                    break;
                case 63:
                    codPezzoSopra = 2;
                    break;
                case 64:
                    codPezzoSopra = 1;
                    break;
                default:
                    break;
            }
        }else if(numCasella >= 49){
            pezzo_bianco = false;

            codPezzoSopra = 6;
        }
    }

    public void disegna(ShapeRenderer sr, SpriteBatch batch){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        if(evidenzia) {
            sr.setColor(115/255f, 230/255f, 0, 1);
        }else{
            sr.setColor(color[0] / 255f, color[1] / 255f, color[2] / 255f, 1);
        }
        sr.rect(x, y, WIDTH_CASELLA, WIDTH_CASELLA);
        sr.end();

        if(scaccoAlRe || scacco){
            sr.begin(ShapeRenderer.ShapeType.Filled);
            //--- sara solo il bordo ---
            sr.setColor(Color.RED);
            sr.rect(x, y, WIDTH_CASELLA, WIDTH_CASELLA);
            //--- nasconde una parte per creare il bordo ---
            sr.setColor(color[0]/255f, color[1]/255f, color[2]/255f, 1);
            //Sul mio tel (width = 1080): 12
            sr.rect(x+((float)Gdx.graphics.getWidth()/90),
                    y+((float)Gdx.graphics.getWidth()/90),
                    WIDTH_CASELLA-24, WIDTH_CASELLA-24);
            sr.end();
        }

        if(MyGdxGame.mosseVisibili) {
            if (mosse) {
                sr.begin(ShapeRenderer.ShapeType.Filled);
                sr.setColor(Color.GREEN);
                int w = 30;
                sr.rect(getCenterX() - (float)w/2, getCenterY() - (float)w/2, w, w);
                sr.end();
            } else if (mossaMangia) {
                sr.begin(ShapeRenderer.ShapeType.Filled);
                sr.setColor(Color.GREEN);
                //--- sara solo il BORDO ----
                sr.circle(getCenterX(), getCenterY(), (float)WIDTH_CASELLA/2 - 10);
                //--- nasconde na parte del cerchio per creare il bordo ---
                sr.setColor(color[0] / 255f, color[1] / 255f, color[2] / 255f, 1);
                sr.circle(getCenterX(), getCenterY(), (float)WIDTH_CASELLA/2 - 16);
                sr.end();
            }
        }

        switch (codPezzoSopra){
            case 1:
                if(pezzo_bianco) {
                    disegnaPedina(batch, b_tTorre);
                }else{
                    disegnaPedina(batch, n_tTorre);
                }
                break;
            case 2:
                if(pezzo_bianco) {
                    disegnaPedina(batch, b_tCavallo);
                }else{
                    disegnaPedina(batch, n_tCavallo);
                }
                break;
            case 3:
                if(pezzo_bianco) {
                    disegnaPedina(batch, b_tAlfiere);
                }else{
                    disegnaPedina(batch, n_tAlfiere);
                }
                break;
            case 4:
                if(pezzo_bianco) {
                    disegnaPedina(batch, b_tRegina);
                }else{
                    disegnaPedina(batch, n_tRegina);
                }
                break;
            case 5:
                if(pezzo_bianco) {
                    disegnaPedina(batch, b_tRe);
                }else{
                    disegnaPedina(batch, n_tRe);
                }
                break;
            case 6:
                if(pezzo_bianco) {
                    disegnaPedina(batch, b_tPedone);
                }else{
                    disegnaPedina(batch, n_tPedone);
                }
                break;
            default:
                break;
        }
    }
    private void disegnaPedina(SpriteBatch batch, Texture t){
        batch.begin();
        batch.draw(t, getCenterX() - (float)WIDTH_PED/2, getCenterY() - (float)WIDTH_PED/2,
                WIDTH_PED, WIDTH_PED,0,0, ORIGINAL_W_H_PEDINA, ORIGINAL_W_H_PEDINA,
                false, (!pezzo_bianco && MyGdxGame.pezziOpposti));
        batch.end();
    }

    public boolean mostraMossa(boolean col_pezzo_che_siMuove){
        if(codPezzoSopra != 5){
            if(codPezzoSopra == -1) {
                mosse = true;
                mossaMangia = false;
                arroccoDx = false;
                arroccoSx = false;
            }else{
                if(col_pezzo_che_siMuove != pezzo_bianco) {
                    mossaMangia = true;
                    mosse = false;
                    arroccoDx = false;
                    arroccoSx = false;
                }
            }

        }
        return codPezzoSopra != -1;
    }
    public void rimuoviMossa(){
        mosse = false;
        mossaMangia = false;
        evidenzia = false;
        arroccoDx = false;
        arroccoSx = false;
    }

    private int getCenterX(){
        return x + WIDTH_CASELLA/2;
    }
    private int getCenterY(){
        return y + WIDTH_CASELLA/2;
    }
}
