package com.bellone.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


import static com.bellone.game.Casella.WIDTH_CASELLA;
import static com.bellone.game.Casella.ORIGINAL_W_H_PEDINA;
import static com.bellone.game.Casella.DIVISORE;
import static com.bellone.game.Casella.WIDTH_PED;

import java.util.ArrayList;

public class Scacchiera {

    public final String sceltaNuovaPedina = "scelta_nuova_pedina.png";
    public final String torre = "/torre.png";                  // cod 1
    public final String cavallo = "/cavallo.png";              // cod 2
    public final String alfiere = "/alfiere.png";              // cod 3
    public final String regina = "/regina.png";                // cod 4
    public final String re = "/re.png";                        // cod 5
    public final String pedone = "/pedone.png";                // cod 6


    public static Texture textureSceltaNuovaPedina;
    public static Texture b_tTorre;
    public static Texture n_tTorre;
    public static Texture b_tCavallo;
    public static Texture n_tCavallo;
    public static Texture b_tAlfiere;
    public static Texture n_tAlfiere;
    public static Texture b_tRegina;
    public static Texture n_tRegina;
    public static Texture b_tRe;
    public static Texture n_tRe;
    public static Texture b_tPedone;
    public static Texture n_tPedone;


    private final ShapeRenderer sr;
    private final SpriteBatch batch;
    private final ArrayList<Casella> caselle;

    private final int x_scacchiera;
    private final int y_scacchiera;
    private final int width_scacchiera;

    private ArrayList<Integer> pezzi_bianchi_mangiati;
    private ArrayList<Integer> pezzi_neri_mangiati;

    private final MostraMosseDisponibili mostraMosseDisponibili;
    public boolean turno_bianchi;
    private Casella ultimoPezzoToccato;
    private int posRe_bianco;
    private boolean bianco_in_scacco;
    private int posRe_nero;
    private boolean nero_in_scacco;
    private boolean reBiancoNoArrocco;
    private boolean reNeroNoArrocco;

    public boolean mostraSceltaNuovaPedina;
    private int pos_nuova_pedina;

    //-------- flag per gli scacchi --------------
    private boolean sAltoSx = false;
    private boolean sAltoSu = false;
    private boolean sAltoDx = false;
    private boolean sDx = false;
    private boolean sBassoDx = false;
    private boolean sBassoGiu = false;
    private boolean sBassoSx = false;
    private boolean sSx = false;

    private boolean scacco_pedone;
    //_________ flag per gli scacchi ______________

    private boolean scacco_matto;
    public boolean stallo;
    public boolean bianchiVincono;
    public boolean neriVincono;


    public Scacchiera(ShapeRenderer shapeRenderer, SpriteBatch bat) {
        sr = shapeRenderer;
        batch = bat;

        mostraSceltaNuovaPedina = false;

        caselle = new ArrayList<>();
        x_scacchiera = (Gdx.graphics.getWidth() - ((WIDTH_CASELLA + DIVISORE) * 8 - DIVISORE)) / 2;
        y_scacchiera = (Gdx.graphics.getHeight() - ((WIDTH_CASELLA+DIVISORE)*8 - DIVISORE)) / 2;
        width_scacchiera = (WIDTH_CASELLA+DIVISORE)*8 - DIVISORE;

        turno_bianchi = true;

        creaCaselle();
        carica_texture();
        resettaFlagScacco();

        mostraMosseDisponibili = new MostraMosseDisponibili(caselle);

        posRe_bianco = 5;
        mostraMosseDisponibili.posRe_bianco = 5;
        bianco_in_scacco = false;
        pezzi_bianchi_mangiati = new ArrayList<>();
        bianchiVincono = false;
        reBiancoNoArrocco = false;

        posRe_nero = 61;
        mostraMosseDisponibili.posRe_nero = 61;
        nero_in_scacco = false;
        pezzi_neri_mangiati = new ArrayList<>();
        neriVincono = false;
        reNeroNoArrocco = false;
    }

    private void creaCaselle(){
        //Cicla per 64 celle mettendo i colori in modo alternato

        int y = (Gdx.graphics.getHeight() - ((WIDTH_CASELLA+DIVISORE)*8 - DIVISORE)) / 2;

        for(int i=0; i<8; i++) {
            int x = (Gdx.graphics.getWidth() - ((WIDTH_CASELLA + DIVISORE) * 8 - DIVISORE)) / 2;
            int[] color1, color2;

            if(i % 2 == 0) {
                color1 = Casella.casellaNeraColor;
                color2 = Casella.casellaBiancaColor;
            }else{
                color1 = Casella.casellaBiancaColor;
                color2 = Casella.casellaNeraColor;
            }
            Casella c;

            c = new Casella(1+(i*8), x, y, color1);
            caselle.add(c);
            x += DIVISORE + WIDTH_CASELLA;

            c = new Casella(2+(i*8), x, y, color2);
            caselle.add(c);
            x += DIVISORE + WIDTH_CASELLA;

            c = new Casella(3+(i*8), x, y, color1);
            caselle.add(c);
            x += DIVISORE + WIDTH_CASELLA;

            c = new Casella(4+(i*8), x, y, color2);
            caselle.add(c);
            x += DIVISORE + WIDTH_CASELLA;

            c = new Casella(5+(i*8), x, y, color1);
            caselle.add(c);
            x += DIVISORE + WIDTH_CASELLA;

            c = new Casella(6+(i*8), x, y, color2);
            caselle.add(c);
            x += DIVISORE + WIDTH_CASELLA;

            c = new Casella(7+(i*8), x, y, color1);
            caselle.add(c);
            x += DIVISORE + WIDTH_CASELLA;

            c = new Casella(8+(i*8), x, y, color2);
            caselle.add(c);

            y += DIVISORE+WIDTH_CASELLA;
        }
    }
    private void carica_texture(){
        textureSceltaNuovaPedina = new Texture(sceltaNuovaPedina);

        String nomeCompleto;

        nomeCompleto = "bianchi"+torre;
        b_tTorre = new Texture(nomeCompleto);
        nomeCompleto = "neri"+torre;
        n_tTorre = new Texture(nomeCompleto);

        nomeCompleto = "bianchi"+cavallo;
        b_tCavallo = new Texture(nomeCompleto);
        nomeCompleto = "neri"+cavallo;
        n_tCavallo = new Texture(nomeCompleto);

        nomeCompleto = "bianchi"+alfiere;
        b_tAlfiere = new Texture(nomeCompleto);
        nomeCompleto = "neri"+alfiere;
        n_tAlfiere = new Texture(nomeCompleto);

        nomeCompleto = "bianchi"+regina;
        b_tRegina = new Texture(nomeCompleto);
        nomeCompleto = "neri"+regina;
        n_tRegina = new Texture(nomeCompleto);

        nomeCompleto = "bianchi"+re;
        b_tRe = new Texture(nomeCompleto);
        nomeCompleto = "neri"+re;
        n_tRe = new Texture(nomeCompleto);

        nomeCompleto = "bianchi"+pedone;
        b_tPedone = new Texture(nomeCompleto);
        nomeCompleto = "neri"+pedone;
        n_tPedone = new Texture(nomeCompleto);
    }
    private void resettaFlagScacco(){
        scacco_matto = false;
        stallo = false;
        scacco_pedone = false;

        sAltoSx = false;
        sAltoSu = false;
        sAltoDx = false;
        sDx = false;
        sBassoDx = false;
        sBassoGiu = false;
        sBassoSx = false;
        sSx = false;
    }

    public void disegna(){
        int width = (WIDTH_CASELLA*8+DIVISORE*7) + 40;
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.BROWN);
        //Sul mio tel (width = 1080): 20
        sr.rect(caselle.get(0).x - ((float)Gdx.graphics.getWidth()/54),
                caselle.get(0).y - ((float)Gdx.graphics.getWidth()/54),
                width, width);
        sr.end();

        for(Casella c: caselle){
            c.disegna(sr, batch);
        }

        //Sul mio tel WIDTH_PED = 79
        int WIDTH_PED_MANGIATA = (int) (WIDTH_PED - (WIDTH_PED/15.8)), DIV = (int) (WIDTH_PED/19.75),
                occupa = ((WIDTH_PED_MANGIATA+DIV)*8)-DIV;
        //Sul mio tel (width = 1080): 40
        int x_ped_mang = x_scacchiera+(width-(Gdx.graphics.getWidth()/27))/2-occupa/2,
                //Sul mio tel (width = 1080): 100
                y_ped_mang = (int) (y_scacchiera - (Gdx.graphics.getWidth()/10.8)), newX;

        //neri mangiati
        for(int i=0; i<pezzi_neri_mangiati.size(); i++){
            if(i >= 8){
                //Sul mio tel (width = 1080): 100
                y_ped_mang = (int) (y_scacchiera - (Gdx.graphics.getWidth()/10.8) - WIDTH_PED_MANGIATA);
                //Sul mio tel (width = 1080): 40
                x_ped_mang = x_scacchiera+(width-(Gdx.graphics.getWidth()/27))/2-occupa/2
                        + WIDTH_PED_MANGIATA/2;


                newX = x_ped_mang+((i%8)*(WIDTH_PED_MANGIATA+DIV));
            }else{
                newX = x_ped_mang+(i*(WIDTH_PED_MANGIATA+DIV));
            }

            batch.begin();
            switch (pezzi_neri_mangiati.get(i)){
                case 1:
                    batch.draw(n_tTorre, newX,y_ped_mang,
                            WIDTH_PED_MANGIATA, WIDTH_PED_MANGIATA);
                    break;
                case 2:
                    batch.draw(n_tCavallo, newX, y_ped_mang,
                            WIDTH_PED_MANGIATA, WIDTH_PED_MANGIATA);
                    break;
                case 3:
                    batch.draw(n_tAlfiere, newX, y_ped_mang,
                            WIDTH_PED_MANGIATA, WIDTH_PED_MANGIATA);
                    break;
                case 4:
                    batch.draw(n_tRegina, newX, y_ped_mang,
                            WIDTH_PED_MANGIATA, WIDTH_PED_MANGIATA);
                    break;
                case 6:
                    batch.draw(n_tPedone, newX, y_ped_mang,
                            WIDTH_PED_MANGIATA, WIDTH_PED_MANGIATA);
                    break;
            }
            batch.end();
        }

        //Sul mio tel (width = 1080): 90
        y_ped_mang = (int) (y_scacchiera + width + (Gdx.graphics.getWidth()/12)
                //Sul mio tel (width = 1080): 100
                - (Gdx.graphics.getWidth()/10.8));
        //Sul mio tel (width = 1080): 40
        x_ped_mang = x_scacchiera+(width-(Gdx.graphics.getWidth()/27))/2+occupa/2 - WIDTH_PED_MANGIATA;
        //bianchi mangiati
        for(int i=0; i<pezzi_bianchi_mangiati.size(); i++){
            if(i >= 8){
                //Sul mio tel (width = 1080): 40
                x_ped_mang = x_scacchiera+(width-(Gdx.graphics.getWidth()/27))/2+occupa/2
                        - WIDTH_PED_MANGIATA - WIDTH_PED_MANGIATA/2;
                y_ped_mang = (int) (y_scacchiera + width + WIDTH_PED_MANGIATA
                        //Sul mio tel (width = 1080): 90
                        + (Gdx.graphics.getWidth()/12)
                        //Sul mio tel (width = 1080): 100
                        - (Gdx.graphics.getWidth()/10.8));


                newX = x_ped_mang-((i%8)*(WIDTH_PED_MANGIATA+DIV));
            }else{
                newX = x_ped_mang-(i*(WIDTH_PED_MANGIATA+DIV));
            }

            batch.begin();
            switch (pezzi_bianchi_mangiati.get(i)){
                case 1:
                    batch.draw(b_tTorre, newX,y_ped_mang,
                            WIDTH_PED_MANGIATA, WIDTH_PED_MANGIATA,0,0,
                            ORIGINAL_W_H_PEDINA,ORIGINAL_W_H_PEDINA,
                            false, MyGdxGame.pezziOpposti);
                    break;
                case 2:
                    batch.draw(b_tCavallo, newX, y_ped_mang,
                            WIDTH_PED_MANGIATA, WIDTH_PED_MANGIATA,0,0,
                            ORIGINAL_W_H_PEDINA,ORIGINAL_W_H_PEDINA,
                            false,  MyGdxGame.pezziOpposti);
                    break;
                case 3:
                    batch.draw(b_tAlfiere, newX, y_ped_mang,
                            WIDTH_PED_MANGIATA, WIDTH_PED_MANGIATA,0,0,
                            ORIGINAL_W_H_PEDINA,ORIGINAL_W_H_PEDINA,
                            false,  MyGdxGame.pezziOpposti);
                    break;
                case 4:
                    batch.draw(b_tRegina, newX, y_ped_mang,
                            WIDTH_PED_MANGIATA, WIDTH_PED_MANGIATA,0,0,
                            ORIGINAL_W_H_PEDINA,ORIGINAL_W_H_PEDINA,
                            false,  MyGdxGame.pezziOpposti);
                    break;
                case 6:
                    batch.draw(b_tPedone, newX, y_ped_mang,
                            WIDTH_PED_MANGIATA, WIDTH_PED_MANGIATA,0,0,
                            ORIGINAL_W_H_PEDINA,ORIGINAL_W_H_PEDINA,
                            false,  MyGdxGame.pezziOpposti);
                    break;
            }
            batch.end();
        }
    }

    private ArrayList<Integer> ordinaArrayPezziMangiati(ArrayList<Integer> array){
        //Ordina in modo crescente, poi rimuove tutti i 6 (dalla fine, è il num più grande)
        // e li mette all'inizio di un nuovo array, a cui aggiunge il resto dell'altro array ordinato

        int tmp;

        if(array.size() > 1) {
            for (int i = 0; i < array.size(); i++) {
                int cod = array.get(i);
                int index = i;

                while (index > 0 && cod < array.get(index - 1)) {
                    tmp = array.get(index - 1);
                    array.set(index - 1, cod);
                    array.set(index, tmp);

                    index--;
                }
            }

            int inizio = array.indexOf(6);
            int fine = array.lastIndexOf(6);

            ArrayList<Integer> array2 = new ArrayList<>();
            if(inizio != -1 && fine != -1) {
                for (int i = inizio; i <= fine; i++) {
                    array2.add(6);
                    //(nel mentre "array" rimpicciolisce quindi non
                    // posso aumentare l'indice)
                    array.remove(inizio);
                }
            }

            array2.addAll(array);

            return new ArrayList<>(array2);
        }else{
            return array;
        }
    }

    private Casella getCasellaInPos(int pos){
        //array è 0-based
        pos -= 1;
        return caselle.get(pos);
    }

    public void scelto_nuova_pedina(int codPedina){
        mostraSceltaNuovaPedina = false;
        getCasellaInPos(pos_nuova_pedina).codPezzoSopra = codPedina;

        //Controllo se fa scacco (per farlo devo "tornare" indietro di un turno)
        turno_bianchi = !turno_bianchi;
        controllaScacco();
        turno_bianchi = !turno_bianchi;

        if(bianco_in_scacco || nero_in_scacco) {
            controllaSeScaccoMatto();
        }

        if(scacco_matto){
            //(ho già invertito il flag)
            if(turno_bianchi){
                neriVincono = true;
            }else{
                bianchiVincono = true;
            }
        }else{
            bianchiVincono = false;
            neriVincono = false;
        }
    }

    public boolean clickScacchiera(float mx, float my){
        Rectangle rect = new Rectangle(x_scacchiera, y_scacchiera, width_scacchiera, width_scacchiera);
        return rect.contains(mx, my);
    }
    public void clickCasella(float mx, float my){
        Rectangle rect = new Rectangle(x_scacchiera, y_scacchiera, width_scacchiera, width_scacchiera);

        if(rect.contains(mx, my)){
            int colonna = controlloSulleX(mx), riga = controlloSulleY(my);

            int indiceCasella = (riga-1)*8 + colonna;
            Casella casCliccata = getCasellaInPos(indiceCasella);

            if(casCliccata.codPezzoSopra != -1
                    && casCliccata.pezzo_bianco == turno_bianchi){

                //ora seleziona il pezzo che intende spostare, quindi non mostro più
                // le mosse del prec. pezzo e mostro quelle di questo pezzo
                cancella_prec_mosse_disp();

                ultimoPezzoToccato = casCliccata;
                casCliccata.evidenzia = true;

                if((!bianco_in_scacco && !nero_in_scacco)) {
                    mostraMosse(casCliccata, indiceCasella);
                }else{
                    calcolaMosseDisponibili(casCliccata, indiceCasella, casCliccata.pezzo_bianco);
                }

            }else if(casCliccata.mosse || casCliccata.mossaMangia){
                //ora sposto il pezzo che ha selezionato sulla casella cliccata

                bianco_in_scacco = false;
                nero_in_scacco = false;
                for(Casella c: caselle){ c.scaccoAlRe = false; c.scacco = false; }

                if(casCliccata.mossaMangia){
                    if(casCliccata.pezzo_bianco){
                        pezzi_bianchi_mangiati.add(casCliccata.codPezzoSopra);
                        pezzi_bianchi_mangiati = ordinaArrayPezziMangiati(pezzi_bianchi_mangiati);
                    }else{
                        pezzi_neri_mangiati.add(casCliccata.codPezzoSopra);
                        pezzi_neri_mangiati = ordinaArrayPezziMangiati(pezzi_neri_mangiati);
                    }
                }

                if(ultimoPezzoToccato.codPezzoSopra == 6 && casCliccata.mossaMangia
                    && casCliccata.codPezzoSopra == -1){

                    //EnPassant

                    int movim;
                    if(ultimoPezzoToccato.pezzo_bianco) { movim = 8;
                    }else{ movim = -8; }
                    int nuovaPos = (casCliccata.numCasella-movim);
                    if(mostraMosseDisponibili.possibileEnPassantInPos(nuovaPos)){
                        getCasellaInPos(nuovaPos).codPezzoSopra = -1;
                        getCasellaInPos(nuovaPos).pezzo_bianco = false;
                        mostraMosseDisponibili.rimuoviPossEnPassant(nuovaPos);
                    }
                }
                //ora sposta effetivamente il pezzo
                //mette il pezzo nella casella cliccata
                casCliccata.codPezzoSopra = ultimoPezzoToccato.codPezzoSopra;
                casCliccata.pezzo_bianco = ultimoPezzoToccato.pezzo_bianco;
                //rimuove il pezzo dalla casella di partenza
                ultimoPezzoToccato.codPezzoSopra = -1;
                ultimoPezzoToccato.pezzo_bianco = false;

                if(casCliccata.codPezzoSopra == 6){
                    if(Math.abs(ultimoPezzoToccato.numCasella - casCliccata.numCasella) == 16){
                        mostraMosseDisponibili.addPossEnPassant(casCliccata.numCasella);
                    }
                    if(((casCliccata.numCasella >= 57 && casCliccata.numCasella <= 64)
                            || (casCliccata.numCasella >= 1 && casCliccata.numCasella <= 8))){

                        mostraSceltaNuovaPedina = true;
                        pos_nuova_pedina = casCliccata.numCasella;
                    }else{
                        mostraSceltaNuovaPedina = false;
                    }
                }else{
                    mostraSceltaNuovaPedina = false;
                }

                if(casCliccata.arroccoSx || casCliccata.arroccoDx){
                    Casella torre, nuovaCasTorreDx;
                    if(casCliccata.arroccoDx){
                        //sposta la torre
                        torre = getCasellaInPos(casCliccata.numCasella+1);
                        nuovaCasTorreDx = getCasellaInPos(casCliccata.numCasella-1);
                    }else{
                        //sposta la torre
                        torre = getCasellaInPos(casCliccata.numCasella-2);
                        nuovaCasTorreDx = getCasellaInPos(casCliccata.numCasella+1);
                    }

                    nuovaCasTorreDx.codPezzoSopra = 1;
                    nuovaCasTorreDx.pezzo_bianco = torre.pezzo_bianco;
                    //rimuove la torre
                    torre.codPezzoSopra = -1;
                    torre.pezzo_bianco = false;
                }


                //Devo sempre sapere la posizione del re. Quindi
                // quando lo sposta mi segno la nuova posizione
                // (uso casCliccata xke ho già spostato i pezzi)
                if(casCliccata.codPezzoSopra == 5){      //re
                    if(casCliccata.pezzo_bianco){
                        posRe_bianco = casCliccata.numCasella;
                        mostraMosseDisponibili.posRe_bianco = casCliccata.numCasella;
                        reBiancoNoArrocco = true;
                    }else{
                        posRe_nero = casCliccata.numCasella;
                        mostraMosseDisponibili.posRe_nero = casCliccata.numCasella;
                        reNeroNoArrocco = true;
                    }
                }

                cancella_prec_mosse_disp();

                controllaScacco();

                turno_bianchi = !turno_bianchi;

                if(bianco_in_scacco || nero_in_scacco) { controllaSeScaccoMatto();
                }else{ controllaSeStallo(); }

                if(scacco_matto){
                    //(ho già invertito il flag)
                    if(turno_bianchi){
                        neriVincono = true;
                    }else{
                        bianchiVincono = true;
                    }
                }else{
                    bianchiVincono = false;
                    neriVincono = false;
                }
            }else{
                //(cliccato in una casella in cui il pezzo selezionato non può andare)

                cancella_prec_mosse_disp();
            }
        }
    }

    private int controlloSulleX(float mx){
        int colonna;

        if(mx < (x_scacchiera + (float)width_scacchiera /2)){

            if(mx < ((x_scacchiera + (float)width_scacchiera /2/2))){
                if(mx < (((x_scacchiera + (float)width_scacchiera /2/2/2)))){
                    colonna = 1;
                }else{
                    colonna = 2;
                }
            }else{
                if(mx < ((((x_scacchiera + (float)width_scacchiera /2))) - WIDTH_CASELLA)){
                    colonna = 3;
                }else{
                    colonna = 4;
                }
            }

        }else{

            if(mx > ((x_scacchiera + (float)width_scacchiera /2) + WIDTH_CASELLA*2)){
                if(mx > (((x_scacchiera + (float)width_scacchiera /2) + WIDTH_CASELLA*2) + WIDTH_CASELLA)){
                    colonna = 8;
                }else{
                    colonna = 7;
                }
            }else{
                if(mx > ((x_scacchiera + (float)width_scacchiera /2) + WIDTH_CASELLA)){
                    colonna = 6;
                }else{
                    colonna = 5;
                }
            }

        }

        return colonna;
    }
    private int controlloSulleY(float my){
        int riga;

        if(my < (y_scacchiera + (float)width_scacchiera /2)){
            if(my < (y_scacchiera + (float)width_scacchiera /2/2)){
                if(my < (y_scacchiera + (float)width_scacchiera /2/2/2)){
                    riga = 1;
                }else{
                    riga = 2;
                }
            }else{
                if(my < (y_scacchiera + (float)width_scacchiera /2) - WIDTH_CASELLA){
                    riga = 3;
                }else{
                    riga = 4;
                }
            }
        }else{
            if(my > (y_scacchiera + (float)width_scacchiera /2) + WIDTH_CASELLA*2){
                if(my > ((y_scacchiera + (float)width_scacchiera /2) + WIDTH_CASELLA*2) + WIDTH_CASELLA ){
                    riga = 8;
                }else{
                    riga = 7;
                }
            }else{
                if(my > (y_scacchiera + (float)width_scacchiera /2) + WIDTH_CASELLA){
                    riga = 6;
                }else{
                    riga = 5;
                }
            }
        }

        return riga;
    }

    private void cancella_prec_mosse_disp(){
        //cancello le mosse precedenti
        for(Casella c: caselle){ c.rimuoviMossa(); }
    }

    private void controllaScacco(){
        //turno_bianchi = true se bianco è quello che ha mosso l'ultimo pezzo

        //Controlla in ogni direzione, a partire dalla pos del re,
        // se c'è un pezzo dell'altro colore a fare scacco

        resettaFlagScacco();

        int posRe;
        if(turno_bianchi) { posRe = posRe_nero;
        }else{ posRe = posRe_bianco; }

        int colonna = posRe%8;
        if(colonna == 0){ colonna = 8; }
        int riga = ((posRe - colonna)/8) +1;
        int posScacco;

        if((posScacco=mostraMosseDisponibili.controlloScaccoAltoSX(riga, colonna, turno_bianchi)) != -1){
            sAltoSx = true;
            if(getCasellaInPos(posScacco).codPezzoSopra == 6){
                //(flag che mi serve per permettere al re di muoversi
                // verso il basso DX, se non ci fosse tratterebbe il
                // pedone come un alfiere)
                scacco_pedone = true;
            }
            segnalaScacco(posScacco);
        }
        if((posScacco=mostraMosseDisponibili.controlloScaccoAltoSU(riga, colonna, turno_bianchi)) != -1){
            sAltoSu = true;
            segnalaScacco(posScacco);
        }
        if((posScacco=mostraMosseDisponibili.controlloScaccoAltoDX(riga, colonna, turno_bianchi)) != -1){
            sAltoDx = true;
            if(getCasellaInPos(posScacco).codPezzoSopra == 6){
                //(flag che mi serve per permettere al re di muoversi
                // verso il basso DX, se non ci fosse tratterebbe il
                // pedone come un alfiere)
                scacco_pedone = true;
            }
            segnalaScacco(posScacco);
        }
        if((posScacco=mostraMosseDisponibili.controlloScaccoDX(riga, colonna, turno_bianchi)) != -1){
            sDx = true;
            segnalaScacco(posScacco);
        }
        if((posScacco=mostraMosseDisponibili.controlloScaccoBassoDX(riga, colonna, turno_bianchi)) != -1){
            sBassoDx = true;
            segnalaScacco(posScacco);
        }
        if((posScacco=mostraMosseDisponibili.controlloScaccoBassoGIU(riga, colonna, turno_bianchi)) != -1){
            sBassoGiu = true;
            segnalaScacco(posScacco);
        }
        if((posScacco=mostraMosseDisponibili.controlloScaccoBassoSX(riga, colonna, turno_bianchi)) != -1){
            sBassoSx = true;
            segnalaScacco(posScacco);
        }
        if((posScacco=mostraMosseDisponibili.controlloScaccoSX(riga, colonna, turno_bianchi)) != -1){
            sSx = true;
            segnalaScacco(posScacco);
        }

        int[] scaccoCavallo = mostraMosseDisponibili.controlloScaccoCavallo(riga, colonna, turno_bianchi);
        if((scaccoCavallo[0]) != -1){
            segnalaScacco(scaccoCavallo[0]);
        }
    }


    private void segnalaScacco(int posScacco){
        mostraMosseDisponibili.pos_pezzo_scacco = posScacco;

        getCasellaInPos(posScacco).scaccoAlRe = true;

        if (turno_bianchi) {
            nero_in_scacco = true;
            bianco_in_scacco = false;
            getCasellaInPos(posRe_nero).scacco = true;
        } else {
            bianco_in_scacco = true;
            nero_in_scacco = false;
            getCasellaInPos(posRe_bianco).scacco = true;
        }
    }

    private void calcolaMosseDisponibili(Casella casCliccata, int indiceCas, boolean turno_bianchi){
        //Il re è sotto scacco. Quando clicchi su una pedina evidenzia le mosse
        // che difendono il re dallo scacco. Se lo scacco è da una sola parte no problem
        // MA se il re è sotto scacco da due parti allora può uscire dallo scacco SOLO
        // muovendo il re.

        int num_scacchi = 0;
        for(Casella c: caselle){
            if(c.scaccoAlRe){ num_scacchi++; }
        }

        int posRe;
        if(turno_bianchi) { posRe = posRe_bianco;
        }else{ posRe = posRe_nero; }
        int colonna = posRe%8;
        if(colonna == 0){ colonna = 8; }
        int riga = ((posRe - colonna)/8) +1;

        if(num_scacchi == 1){
            switch (casCliccata.codPezzoSopra){
                case 1:
                    mostraMosseDisponibili.mosse_torre(indiceCas);
                    break;
                case 2:
                    mostraMosseDisponibili.mosse_cavallo(indiceCas);
                    break;
                case 3:
                    mostraMosseDisponibili.mosse_alfiere(indiceCas);
                    break;
                case 4:
                    mostraMosseDisponibili.mosse_regina(indiceCas);
                    break;
                //case 5: re; lo faccio già dopo
                case 6:
                    mostraMosseDisponibili.mosse_pedone(indiceCas);
                    break;
                default:
                    break;
            }

        }
        if(casCliccata.codPezzoSopra == 5){
            mosse_re_sotto_scacco(riga, colonna, casCliccata);
        }
    }

    private boolean mosse_re_sotto_scacco(int riga, int colonna, Casella casCliccata){
        //Per OGNI SINGOLA mossa del re vedo se può farla, ovvero per OGNI mossa
        // vedo se su quella casella ce lo scacco da parte di qualche pezzo

        int r, col;

        int nuovaPos;
        boolean mossaDisponibile, almenoUnaMossaDisp = false;


        //-------------------ALTO SX------------------------
        if((!sAltoSx && !sBassoDx)){
            if(casCliccata.pezzo_bianco) {
                r = riga + 1;
                col = colonna - 1;
            }else{
                r = riga - 1;
                col = colonna + 1;
            }
            if(r > 0 && r <= 8 && col > 0 && col <= 8){
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if(mossaDisponibile) {
                    if(getCasellaInPos(nuovaPos).codPezzoSopra != -1) {
                        if(getCasellaInPos(nuovaPos).pezzo_bianco != casCliccata.pezzo_bianco) {
                            getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                            almenoUnaMossaDisp = true;
                        }
                    }else{
                        getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                        almenoUnaMossaDisp = true;
                    }
                }
            }
        }else{
            //Vedo se può mangiare il pezzo che gli sta dando scacco

            if(casCliccata.pezzo_bianco) {
                r = riga + 1;
                col = colonna - 1;
            }else{
                r = riga - 1;
                col = colonna + 1;
            }
            if(r > 0 && r <= 8 && col > 0 && col <= 8) {
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if (mossaDisponibile
                        && getCasellaInPos(nuovaPos).pezzo_bianco != turno_bianchi
                        && getCasellaInPos(nuovaPos).codPezzoSopra != -1) {

                    getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                    almenoUnaMossaDisp = true;
                }
            }
        }
        //___________________ALTO SX________________

        //-------------------ALTO SU------------------------
        if(!sAltoSu && !sBassoGiu){
            col = colonna;
            if(casCliccata.pezzo_bianco) {
                r = riga + 1;
            }else{
                r = riga - 1;
            }
            if(r > 0 && r <= 8){
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if(mossaDisponibile) {
                    if(getCasellaInPos(nuovaPos).codPezzoSopra != -1) {
                        if(getCasellaInPos(nuovaPos).pezzo_bianco != casCliccata.pezzo_bianco) {
                            getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                            almenoUnaMossaDisp = true;
                        }
                    }else{
                        getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                        almenoUnaMossaDisp = true;
                    }
                }
            }
        }else{
            //Vedo se può mangiare il pezzo che gli sta dando scacco

            col = colonna;
            if(casCliccata.pezzo_bianco) {
                r = riga + 1;
            }else{
                r = riga - 1;
            }
            if(r > 0 && r <= 8) {
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if (mossaDisponibile
                        && getCasellaInPos(nuovaPos).pezzo_bianco != turno_bianchi
                        && getCasellaInPos(nuovaPos).codPezzoSopra != -1) {

                    getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                    almenoUnaMossaDisp = true;
                }
            }
        }
        //___________________ALTO SU________________

        //-------------------ALTO DX------------------------
        if((!sAltoDx && !sBassoSx)){
            if(casCliccata.pezzo_bianco) {
                r = riga + 1;
                col = colonna + 1;
            }else{
                r = riga - 1;
                col = colonna - 1;
            }
            if(r > 0 && r <= 8 && col > 0 && col <= 8){
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if(mossaDisponibile) {
                    if(getCasellaInPos(nuovaPos).codPezzoSopra != -1) {
                        if(getCasellaInPos(nuovaPos).pezzo_bianco != casCliccata.pezzo_bianco) {
                            getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                            almenoUnaMossaDisp = true;
                        }
                    }else{
                        getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                        almenoUnaMossaDisp = true;
                    }
                }
            }
        }else{
            //Vedo se può mangiare il pezzo che gli sta dando scacco

            if(casCliccata.pezzo_bianco) {
                r = riga + 1;
                col = colonna + 1;
            }else{
                r = riga - 1;
                col = colonna - 1;
            }
            if(r > 0 && r <= 8 && col > 0 && col <= 8) {
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if (mossaDisponibile
                        && getCasellaInPos(nuovaPos).pezzo_bianco != turno_bianchi
                        && getCasellaInPos(nuovaPos).codPezzoSopra != -1) {

                    getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                    almenoUnaMossaDisp = true;
                }
            }
        }
        //___________________ALTO DX________________

        //-------------------DX------------------------
        if(!sDx && !sSx){
            r = riga;
            if(casCliccata.pezzo_bianco) {
                col = colonna + 1;
            }else{
                col = colonna - 1;
            }
            if(col > 0 && col <= 8){
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if(mossaDisponibile) {
                    if(getCasellaInPos(nuovaPos).codPezzoSopra != -1) {
                        if(getCasellaInPos(nuovaPos).pezzo_bianco != casCliccata.pezzo_bianco) {
                            getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                            almenoUnaMossaDisp = true;
                        }
                    }else{
                        getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                        almenoUnaMossaDisp = true;
                    }
                }
            }
        }else{
            //Vedo se può mangiare il pezzo che gli sta dando scacco

            r = riga;
            if(casCliccata.pezzo_bianco) {
                col = colonna + 1;
            }else{
                col = colonna - 1;
            }
            if(col > 0 && col <= 8) {
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if (mossaDisponibile
                        && getCasellaInPos(nuovaPos).pezzo_bianco != turno_bianchi
                        && getCasellaInPos(nuovaPos).codPezzoSopra != -1) {

                    getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                    almenoUnaMossaDisp = true;
                }
            }
        }
        //___________________DX________________

        //-------------------BASSO DX------------------------
        if((!sBassoDx && !sAltoSx) || scacco_pedone){
            if(casCliccata.pezzo_bianco) {
                r = riga - 1;
                col = colonna + 1;
            }else{
                r = riga + 1;
                col = colonna - 1;
            }
            if(r > 0 && r <= 8 && col > 0 && col <= 8){
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if(mossaDisponibile) {
                    if(getCasellaInPos(nuovaPos).codPezzoSopra != -1) {
                        if(getCasellaInPos(nuovaPos).pezzo_bianco != casCliccata.pezzo_bianco) {
                            getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                            almenoUnaMossaDisp = true;
                        }
                    }else{
                        getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                        almenoUnaMossaDisp = true;
                    }
                }
            }
        }else{
            //Vedo se può mangiare il pezzo che gli sta dando scacco

            if(casCliccata.pezzo_bianco) {
                r = riga - 1;
                col = colonna + 1;
            }else{
                r = riga + 1;
                col = colonna - 1;
            }
            if(r > 0 && r <= 8 && col > 0 && col <= 8) {
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if (mossaDisponibile
                        && getCasellaInPos(nuovaPos).pezzo_bianco != turno_bianchi
                        && getCasellaInPos(nuovaPos).codPezzoSopra != -1) {

                    getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                    almenoUnaMossaDisp = true;
                }
            }
        }
        //___________________BASSO DX________________

        //-------------------BASSO GIU------------------------
        if(!sBassoGiu && !sAltoSu){
            col = colonna;
            if(casCliccata.pezzo_bianco) {
                r = riga - 1;
            }else{
                r = riga + 1;
            }
            if(r > 0 && r <= 8){
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if(mossaDisponibile) {
                    if(getCasellaInPos(nuovaPos).codPezzoSopra != -1) {
                        if(getCasellaInPos(nuovaPos).pezzo_bianco != casCliccata.pezzo_bianco) {
                            getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                            almenoUnaMossaDisp = true;
                        }
                    }else{
                        getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                        almenoUnaMossaDisp = true;
                    }
                }
            }
        }else{
            //Vedo se può mangiare il pezzo che gli sta dando scacco

            col = colonna;
            if(casCliccata.pezzo_bianco) {
                r = riga - 1;
            }else{
                r = riga + 1;
            }
            if(r > 0 && r <= 8){
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if (mossaDisponibile
                        && getCasellaInPos(nuovaPos).pezzo_bianco != turno_bianchi
                        && getCasellaInPos(nuovaPos).codPezzoSopra != -1) {

                    getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                    almenoUnaMossaDisp = true;
                }
            }
        }
        //___________________BASSO GIU________________

        //-------------------BASSO SX------------------------
        if((!sBassoSx && !sAltoDx) || scacco_pedone){
            if(casCliccata.pezzo_bianco) {
                r = riga - 1;
                col = colonna - 1;
            }else{
                r = riga + 1;
                col = colonna + 1;
            }
            if(r > 0 && r <= 8 && col > 0 && col <= 8){
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if(mossaDisponibile) {
                    if(getCasellaInPos(nuovaPos).codPezzoSopra != -1) {
                        if(getCasellaInPos(nuovaPos).pezzo_bianco != casCliccata.pezzo_bianco) {
                            getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                            almenoUnaMossaDisp = true;
                        }
                    }else{
                        getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                        almenoUnaMossaDisp = true;
                    }
                }
            }
        }else{
            //Vedo se può mangiare il pezzo che gli sta dando scacco

            if(casCliccata.pezzo_bianco) {
                r = riga - 1;
                col = colonna - 1;
            }else{
                r = riga + 1;
                col = colonna + 1;
            }
            if(r > 0 && r <= 8 && col > 0 && col <= 8) {
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if (mossaDisponibile
                        && getCasellaInPos(nuovaPos).pezzo_bianco != turno_bianchi
                        && getCasellaInPos(nuovaPos).codPezzoSopra != -1) {

                    getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                    almenoUnaMossaDisp = true;
                }
            }
        }
        //___________________BASSO SX________________

        //-------------------SX------------------------
        if(!sSx && !sDx){
            r = riga;
            if(casCliccata.pezzo_bianco) {
                col = colonna - 1;
            }else{
                col = colonna + 1;
            }

            if(col > 0 && col <= 8){
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if(mossaDisponibile) {
                    if(getCasellaInPos(nuovaPos).codPezzoSopra != -1) {
                        if(getCasellaInPos(nuovaPos).pezzo_bianco != casCliccata.pezzo_bianco) {
                            getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                            almenoUnaMossaDisp = true;
                        }
                    }else{
                        getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                        almenoUnaMossaDisp = true;
                    }
                }
            }
        }else{
            //Vedo se può mangiare il pezzo che gli sta dando scacco

            r = riga;
            if(casCliccata.pezzo_bianco) {
                col = colonna - 1;
            }else{
                col = colonna + 1;
            }
            if(col > 0 && col <= 8) {
                mossaDisponibile = mostraMosseDisponibili.scaccoInPos(r, col, turno_bianchi);

                nuovaPos = (r-1)*8 +col;
                if (mossaDisponibile
                        && getCasellaInPos(nuovaPos).pezzo_bianco != turno_bianchi
                        && getCasellaInPos(nuovaPos).codPezzoSopra != -1) {

                    getCasellaInPos(nuovaPos).mostraMossa(turno_bianchi);
                    almenoUnaMossaDisp = true;
                }
            }
        }
        //___________________SX________________

        return almenoUnaMossaDisp;
    }

    private void controllaSeScaccoMatto(){
        //Controlla OGNI SINGOLA pedina (del colore del re sotto scacco) e
        // controlla se ha ALMENO una mossa disponibile. Se ALMENO una pedina
        // ha ALMENO una mossa disponibile allora NON È SCACCO MATTO

        scacco_matto = true;

        int posRe = -1;
        if(bianco_in_scacco) { posRe = posRe_bianco;
        }else if(nero_in_scacco){ posRe = posRe_nero; }

        if(posRe != -1) {
            int colonna = posRe % 8;
            if (colonna == 0) {
                colonna = 8;
            }
            int riga = ((posRe - colonna) / 8) + 1;

            for (int i = 1; i <= caselle.size() && scacco_matto; i++) {
                if (getCasellaInPos(i).codPezzoSopra != -1) {
                    if ((bianco_in_scacco && getCasellaInPos(i).pezzo_bianco) ||
                            (nero_in_scacco && !getCasellaInPos(i).pezzo_bianco)) {

                        switch (getCasellaInPos(i).codPezzoSopra) {
                            case 1:
                                if(mostraMosseDisponibili.mosse_torre(i)) {
                                    scacco_matto = false;
                                }
                                break;
                            case 2:
                                if(mostraMosseDisponibili.mosse_cavallo(i)) {
                                    scacco_matto = false;
                                }
                                break;
                            case 3:
                                if(mostraMosseDisponibili.mosse_alfiere(i)) {
                                    scacco_matto = false;
                                }
                                break;
                            case 4:
                                if(mostraMosseDisponibili.mosse_regina(i)) {
                                    scacco_matto = false;
                                }
                                break;
                            case 5:
                                if(mosse_re_sotto_scacco(riga, colonna, getCasellaInPos(i))) {
                                    scacco_matto = false;
                                }
                                break;
                            case 6:
                                if(mostraMosseDisponibili.mosse_pedone(i)) {
                                    scacco_matto = false;
                                }
                                break;
                        }
                    }
                }
            }
        }//else qualcosa non quadra

        cancella_prec_mosse_disp();
    }
    private void controllaSeStallo(){
        //Controlla OGNI SINGOLA pedina (dello stesso colore) e
        // controlla se ha ALMENO una mossa disponibile. Se ALMENO una pedina
        // ha ALMENO una mossa disponibile allora NON È STALLO

        stallo = true;

        int posRe;
        if(turno_bianchi) { posRe = posRe_bianco;
        }else{ posRe = posRe_nero; }

        int colonna = posRe % 8;
        if (colonna == 0) {
            colonna = 8;
        }
        int riga = ((posRe - colonna) / 8) + 1;

        for (int i = 1; i <= caselle.size() && stallo; i++) {
            if (getCasellaInPos(i).codPezzoSopra != -1) {
                if (turno_bianchi == getCasellaInPos(i).pezzo_bianco) {

                    switch (getCasellaInPos(i).codPezzoSopra) {
                        case 1:
                            if(mostraMosseDisponibili.mosse_torre(i)) {
                                stallo = false;
                            }
                            break;
                        case 2:
                            if(mostraMosseDisponibili.mosse_cavallo(i)) {
                                stallo = false;
                            }
                            break;
                        case 3:
                            if(mostraMosseDisponibili.mosse_alfiere(i)) {
                                stallo = false;
                            }
                            break;
                        case 4:
                            if(mostraMosseDisponibili.mosse_regina(i)) {
                                stallo = false;
                            }
                            break;
                        case 5:
                            if(mosse_re_sotto_scacco(riga, colonna, getCasellaInPos(i))) {
                                stallo = false;
                            }
                            break;
                        case 6:
                            if(mostraMosseDisponibili.mosse_pedone(i)) {
                                stallo = false;
                            }
                            break;
                    }
                }
            }
        }

        cancella_prec_mosse_disp();
    }

    private void mostraMosse(Casella casella, int posCas){
        switch (casella.codPezzoSopra){
            case 1:
                mostraMosseDisponibili.mosse_torre(posCas);
                break;
            case 2:
                mostraMosseDisponibili.mosse_cavallo(posCas);
                break;
            case 3:
                mostraMosseDisponibili.mosse_alfiere(posCas);
                break;
            case 4:
                mostraMosseDisponibili.mosse_regina(posCas);
                break;
            case 5:
                mostraMosseDisponibili.mosse_re(posCas, reBiancoNoArrocco, reNeroNoArrocco);
                break;
            case 6:
                mostraMosseDisponibili.mosse_pedone(posCas);
                break;
            default:
                break;
        }
    }
}
