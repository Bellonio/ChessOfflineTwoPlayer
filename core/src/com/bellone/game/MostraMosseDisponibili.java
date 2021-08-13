package com.bellone.game;

import java.util.ArrayList;

public class MostraMosseDisponibili {

    private final ArrayList<Casella> caselle;
    public int posRe_bianco;
    public int posRe_nero;
    public int pos_pezzo_scacco;

    private final ArrayList<Integer> possibiliEnPassant;
    public void addPossEnPassant(int pos){ possibiliEnPassant.add(pos); }
    public boolean possibileEnPassantInPos(int pos){ return possibiliEnPassant.contains(pos); }
    public void rimuoviPossEnPassant(int pos){
        possibiliEnPassant.remove((Integer) pos);
    }

    public MostraMosseDisponibili(ArrayList<Casella> c) {
        caselle = c;
        pos_pezzo_scacco = -1;
        possibiliEnPassant = new ArrayList<>();
    }

    private Casella getCasellaInPos(int pos) {
        //array è 0-based
        pos -= 1;
        return caselle.get(pos);
    }


    /*Come ho calcolato a partire dalla posizione la riga e la colonna ?
        es. posPartenza = 26
        colonna ==> 2  <= 26 modulo 8 RESTO 2
        riga ==> 4  <= 26-2=  24/8=  3+1=  4

      Come ho calcolato a partire da riga e colonna la posizione ?
        es. riga 2 colonna 4 ==> 12
        Xke tolgo 1 alla riga ? Xke moltiplico per 8 (il num di colonne)
        ma l'ultima riga non la devo prendere "piena", e poi per l'ultima
        riga ho il numero di colonna che mi permette di prendere la pos giusta

        nuovaPos = (riga-1)*8 + colonna;
          */

    public boolean mosse_torre(int posPartenza) {
        //Per segnalare una mossa disponibile guarda prima di tutto
        // se nella nuova posizione NON c'è già un pezzo, e poi
        // guarda SE spostandosi nella nuova posizione va a CREARE SCACCO
        // per il proprio re ALLORA non può muoversi lì

        boolean flagPezzoInMezzo = false,
                col_pezzo_siMuove = getCasellaInPos(posPartenza).pezzo_bianco,
                almenoUnaMossaDisp = false;
        int nuovaPos = posPartenza;

        //--- mosse sopra ---
        while (nuovaPos <= caselle.size() && !flagPezzoInMezzo) {
            nuovaPos += 8;
            if (nuovaPos <= caselle.size()) {
                boolean ce_pezzo = getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
                if(ce_pezzo){
                    flagPezzoInMezzo = true;
                }

                int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

                mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

                mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
                if (ceScacco) {
                    getCasellaInPos(nuovaPos).rimuoviMossa();
                }else{
                    almenoUnaMossaDisp = true;
                }
            }
        }

        //--- mosse sotto ---
        flagPezzoInMezzo = false;
        nuovaPos = posPartenza;

        while (nuovaPos > 0 && !flagPezzoInMezzo) {
            nuovaPos -= 8;
            if (nuovaPos > 0) {
                boolean ce_pezzo = getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
                if(ce_pezzo){
                    flagPezzoInMezzo = true;
                }

                int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

                mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

                mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
                if (ceScacco) {
                    getCasellaInPos(nuovaPos).rimuoviMossa();
                }else{
                    almenoUnaMossaDisp = true;
                }
            }
        }

        //--- mosse destra ---
        flagPezzoInMezzo = false;
        nuovaPos = posPartenza;
        while (nuovaPos % 8 != 0 && !flagPezzoInMezzo) {
            nuovaPos++;
            if (nuovaPos <= caselle.size()) {
                boolean ce_pezzo = getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
                if(ce_pezzo){
                    flagPezzoInMezzo = true;
                }

                int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

                mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

                mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
                if (ceScacco) {
                    getCasellaInPos(nuovaPos).rimuoviMossa();
                }else{
                    almenoUnaMossaDisp = true;
                }
            }
        }

        //--- mosse sinistra ---
        flagPezzoInMezzo = false;
        nuovaPos = posPartenza;
        while (nuovaPos % 8 != 1 && !flagPezzoInMezzo) {
            nuovaPos--;
            if (nuovaPos > 0) {
                boolean ce_pezzo = getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
                if(ce_pezzo){
                    flagPezzoInMezzo = true;
                }

                int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

                mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

                mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
                if (ceScacco) {
                    getCasellaInPos(nuovaPos).rimuoviMossa();
                }else{
                    almenoUnaMossaDisp = true;
                }
            }
        }

        return almenoUnaMossaDisp;
    }

    public boolean mosse_cavallo(int posPartenza) {
        //Per segnalare una mossa disponibile guarda prima di tutto
        // se nella nuova posizione NON c'è già un pezzo, e poi
        // guarda SE spostandosi nella nuova posizione va a CREARE SCACCO
        // per il proprio re ALLORA non può muoversi lì

        int colonna = posPartenza % 8;
        if(colonna == 0){ colonna = 8; }
        int riga = ((posPartenza - colonna) / 8) + 1;
        boolean col_pezzo_siMuove = getCasellaInPos(posPartenza).pezzo_bianco,
                almenoUnaMossaDisp = false;

        int nuovaPos;

        //----- EMISFERO ALTO -----
        //L bassa verso SX
        if (riga < 8 && colonna > 2) {
            nuovaPos = (riga-1 + 1) * 8 + colonna - 2;
            getCasellaInPos(nuovaPos).mostraMossa(getCasellaInPos(posPartenza).pezzo_bianco);

            int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
            boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

            mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
            boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
            mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

            mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
            if (ceScacco) {
                getCasellaInPos(nuovaPos).rimuoviMossa();
            }else{
                almenoUnaMossaDisp = true;
            }
        }

        //L alta verso SX
        if (riga < 7 && colonna > 1) {
            nuovaPos = (riga-1 + 2) * 8 + colonna - 1;
            getCasellaInPos(nuovaPos).mostraMossa(getCasellaInPos(posPartenza).pezzo_bianco);

            int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
            boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

            mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
            boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
            mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

            mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
            if (ceScacco) {
                getCasellaInPos(nuovaPos).rimuoviMossa();
            }else{
                almenoUnaMossaDisp = true;
            }
        }

        //L alta verso DX
        if(riga < 7 && colonna < 8) {
            nuovaPos = (riga-1 + 2) * 8 + colonna + 1;
            getCasellaInPos(nuovaPos).mostraMossa(getCasellaInPos(posPartenza).pezzo_bianco);

            int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
            boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

            mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
            boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
            mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

            mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
            if (ceScacco) {
                getCasellaInPos(nuovaPos).rimuoviMossa();
            }else{
                almenoUnaMossaDisp = true;
            }
        }

        //L bassa verso DX
        if(riga < 8 && colonna < 7) {
            nuovaPos =(riga-1 + 1)*8+colonna +2;
            getCasellaInPos(nuovaPos).mostraMossa(getCasellaInPos(posPartenza).pezzo_bianco);

            int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
            boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

            mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
            boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
            mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

            mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
            if (ceScacco) {
                getCasellaInPos(nuovaPos).rimuoviMossa();
            }else{
                almenoUnaMossaDisp = true;
            }
        }


        //----- EMISFERO BASSO -----
        //L bassa verso SX
        if(riga > 1 && colonna > 2) {
            nuovaPos = (riga-1 - 1)*8 + colonna -2;
            getCasellaInPos(nuovaPos).mostraMossa(getCasellaInPos(posPartenza).pezzo_bianco);

            int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
            boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

            mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
            boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
            mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

            mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
            if (ceScacco) {
                getCasellaInPos(nuovaPos).rimuoviMossa();
            }else{
                almenoUnaMossaDisp = true;
            }
        }

        //L alta verso SX
        if(riga > 2 && colonna > 1) {
            nuovaPos = (riga-1 - 2)*8 + colonna -1;
            getCasellaInPos(nuovaPos).mostraMossa(getCasellaInPos(posPartenza).pezzo_bianco);

            int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
            boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

            mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
            boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
            mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

            mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
            if (ceScacco) {
                getCasellaInPos(nuovaPos).rimuoviMossa();
            }else{
                almenoUnaMossaDisp = true;
            }
        }

        //L alta verso DX
        if(riga > 2 && colonna < 8) {
            nuovaPos = (riga-1 - 2)*8 + colonna +1;
            getCasellaInPos(nuovaPos).mostraMossa(getCasellaInPos(posPartenza).pezzo_bianco);

            int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
            boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

            mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
            boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
            mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

            mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
            if (ceScacco) {
                getCasellaInPos(nuovaPos).rimuoviMossa();
            }else{
                almenoUnaMossaDisp = true;
            }
        }

        //L bassa verso DX
        if(riga > 1 && colonna < 7) {
            nuovaPos = (riga-1 - 1)*8 + colonna +2;
            getCasellaInPos(nuovaPos).mostraMossa(getCasellaInPos(posPartenza).pezzo_bianco);

            int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
            boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

            mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
            boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
            mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

            mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
            if (ceScacco) {
                getCasellaInPos(nuovaPos).rimuoviMossa();
            }else{
                almenoUnaMossaDisp = true;
            }
        }

        return almenoUnaMossaDisp;
    }

    public boolean mosse_alfiere(int posPartenza){
        //Per segnalare una mossa disponibile guarda prima di tutto
        // se nella nuova posizione NON c'è già un pezzo, e poi
        // guarda SE spostandosi nella nuova posizione va a CREARE SCACCO
        // per il proprio re ALLORA non può muoversi lì

        int colonna = posPartenza % 8;
        if(colonna == 0){ colonna = 8; }
        int riga = ((posPartenza - colonna) / 8) + 1;

        int nuovaPos, r = riga, col = colonna;
        boolean flagPezzoSopra = false,
                col_pezzo_siMuove = getCasellaInPos(posPartenza).pezzo_bianco,
                almenoUnaMossaDisp = false;

        //Diagonale verso ALTO-DX
        while(col <= 8 && r <= 8 && !flagPezzoSopra){
            r++;
            col++;
            if(col <= 8 && r <= 8){
                nuovaPos = (r-1)*8 + col;
                int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

                if(getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove)) {
                    flagPezzoSopra = true;
                }

                mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

                mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
                if (ceScacco) {
                    getCasellaInPos(nuovaPos).rimuoviMossa();
                }else{
                    almenoUnaMossaDisp = true;
                }
            }
        }

        r = riga;
        col = colonna;
        flagPezzoSopra = false;
        //Diagonale verso ALTO-SX
        while(col > 0 && r <= 8 && !flagPezzoSopra){
            r++;
            col--;
            if(col > 0 && r <= 8){
                nuovaPos = (r-1)*8 + col;
                int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

                if (getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove)) {
                    flagPezzoSopra = true;
                }
                mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

                mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
                if (ceScacco) {
                    getCasellaInPos(nuovaPos).rimuoviMossa();
                }else{
                    almenoUnaMossaDisp = true;
                }
            }
        }

        r = riga;
        col = colonna;
        flagPezzoSopra = false;
        //Diagonale verso BASSO-DX
        while(col <= 8 && !flagPezzoSopra){
            r--;
            col++;
            if(col <= 8 && r > 0){
                nuovaPos = (r-1)*8 + col;
                int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

                if (getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove)) {
                    flagPezzoSopra = true;
                }
                mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

                mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
                if (ceScacco) {
                    getCasellaInPos(nuovaPos).rimuoviMossa();
                }else{
                    almenoUnaMossaDisp = true;
                }
            }
        }

        r = riga;
        col = colonna;
        flagPezzoSopra = false;
        //Diagonale verso BASSO-SX
        while(col > 0 && !flagPezzoSopra){
            r--;
            col--;
            if(col > 0 && r > 0){
                nuovaPos = (r-1)*8 + col;
                int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

                if (getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove)) {
                    flagPezzoSopra = true;
                }
                mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

                mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
                if (ceScacco) {
                    getCasellaInPos(nuovaPos).rimuoviMossa();
                }else{
                    almenoUnaMossaDisp = true;
                }
            }
        }

        return almenoUnaMossaDisp;
    }

    public boolean mosse_regina(int pos){
        //Richiamo sia mosse_torre() che mosse_alfiere()

        boolean almenoUnaMossaDisp;
        almenoUnaMossaDisp = mosse_torre(pos);
        if(mosse_alfiere(pos)){
            almenoUnaMossaDisp = true;
        }

        return almenoUnaMossaDisp;
    }

    public void mosse_re(int pos, boolean noArroccoB, boolean noArroccoN){
        //Per segnalare una mossa disponibile guarda prima di tutto
        // se nella nuova posizione NON c'è già un pezzo, e poi
        // guarda SE nella nuova posizione non sarei in scacco

        int colonna = pos % 8;
        if(colonna == 0){ colonna = 8; }
        int riga = ((pos - colonna) / 8) + 1;

        int nuovaPos, r=riga, col=colonna;
        boolean mossaDisponibile, col_pezzo_siMuove = getCasellaInPos(pos).pezzo_bianco;

        //ALTO SX
        if(col > 1 && r < 8){
            r++;
            col--;
            mossaDisponibile = scaccoInPos(r, col, col_pezzo_siMuove);

            nuovaPos = (r-1)*8 +col;
            if(mossaDisponibile) {
                getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
            }
        }
        r = riga;
        col = colonna;

        //ALTO SU
        if(r < 8){
            r++;
            mossaDisponibile = scaccoInPos(r, col, col_pezzo_siMuove);

            nuovaPos = (r-1)*8 +col;
            if(mossaDisponibile) {
                getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
            }
        }
        r = riga;
        col = colonna;
        //ALTO DX
        if(col < 8 && r < 8){
            r++;
            col++;
            mossaDisponibile = scaccoInPos(r, col, col_pezzo_siMuove);

            nuovaPos = (r-1)*8 +col;
            if(mossaDisponibile) {
                getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
            }
        }
        r = riga;
        col = colonna;

        //SX
        if(col > 1){
            col--;
            mossaDisponibile = scaccoInPos(r, col, col_pezzo_siMuove);

            nuovaPos = (r-1)*8 +col;
            if(mossaDisponibile) {
                getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
            }
        }
        r = riga;
        col = colonna;
        //DX
        if(col < 8){
            col++;
            mossaDisponibile = scaccoInPos(r, col, col_pezzo_siMuove);

            nuovaPos = (r-1)*8 +col;
            if(mossaDisponibile) {
                getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
            }
        }
        r = riga;
        col = colonna;

        //BASSO SX
        if(col > 1 && r > 1){
            r--;
            col--;
            mossaDisponibile = scaccoInPos(r, col, col_pezzo_siMuove);

            nuovaPos = (r-1)*8 +col;
            if(mossaDisponibile) {
                getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
            }
        }
        r = riga;
        col = colonna;
        //BASSO GIU
        if(r > 1){
            r--;
            mossaDisponibile = scaccoInPos(r, col, col_pezzo_siMuove);

            nuovaPos = (r-1)*8 +col;
            if(mossaDisponibile) {
                getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
            }
        }
        r = riga;
        col = colonna;
        //BASSO DX
        if(col < 8 && r > 1){
            r--;
            col++;
            mossaDisponibile = scaccoInPos(r, col, col_pezzo_siMuove);

            nuovaPos = (r-1)*8 +col;
            if(mossaDisponibile) {
                getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);
            }
        }
        r = riga;
        col = colonna;

        //ARROCCO
        if((r == 1 && !noArroccoB) || (r == 8 && !noArroccoN)){
            if(col == 5){
                mossaDisponibile = scaccoInPos(r, col+1, col_pezzo_siMuove);

                nuovaPos = (r-1)*8 +col+1;
                if(getCasellaInPos(nuovaPos).codPezzoSopra == -1){
                    if(mossaDisponibile) {
                        mossaDisponibile = scaccoInPos(r, col+2, col_pezzo_siMuove);
                        nuovaPos = (r-1)*8 +col+2;
                        if(getCasellaInPos(nuovaPos).codPezzoSopra == -1){
                            if(mossaDisponibile) {

                                nuovaPos = (r-1)*8 +col+3;
                                if(getCasellaInPos(nuovaPos).codPezzoSopra == 1){
                                    //controllo che non ci siano pezzi tra lui e la torre
                                    if(getCasellaInPos(nuovaPos-1).codPezzoSopra == -1
                                            && getCasellaInPos(nuovaPos-2).codPezzoSopra == -1){

                                        getCasellaInPos(nuovaPos-1).mosse = true;
                                        getCasellaInPos(nuovaPos-1).arroccoDx = true;
                                    }
                                }

                            }
                        }
                    }
                }

                mossaDisponibile = scaccoInPos(r, col-1, col_pezzo_siMuove);

                nuovaPos = (r-1)*8 +col-1;
                if(getCasellaInPos(nuovaPos).codPezzoSopra == -1){
                    if(mossaDisponibile) {
                        mossaDisponibile = scaccoInPos(r, col-2, col_pezzo_siMuove);
                        nuovaPos = (r-1)*8 +col-2;
                        if(getCasellaInPos(nuovaPos).codPezzoSopra == -1){
                            if(mossaDisponibile) {

                                nuovaPos = (r-1)*8 +col-4;
                                if(getCasellaInPos(nuovaPos).codPezzoSopra == 1){
                                    //controllo che non ci siano pezzi tra lui e la torre
                                    if(getCasellaInPos(nuovaPos+1).codPezzoSopra == -1
                                            && getCasellaInPos(nuovaPos+2).codPezzoSopra == -1
                                            && getCasellaInPos(nuovaPos+3).codPezzoSopra == -1
                                    ){

                                        getCasellaInPos(nuovaPos+2).mosse = true;
                                        getCasellaInPos(nuovaPos+2).arroccoSx = true;
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public boolean mosse_pedone(int posPartenza){
        //Per segnalare una mossa disponibile guarda prima di tutto
        // se nella nuova posizione NON c'è già un pezzo, e poi
        // guarda SE spostandosi nella nuova posizione va a CREARE SCACCO
        // per il proprio re ALLORA non può muoversi lì

        int colonna = posPartenza % 8;
        if(colonna == 0){ colonna = 8; }
        int riga = ((posPartenza - colonna) / 8) + 1, r = riga;

        int nuovaPos, limite, rigaInizio, movim, col = colonna;
        boolean col_pezzo_siMuove = getCasellaInPos(posPartenza).pezzo_bianco,
            almenoUnaMossaDisp = false;

        if(getCasellaInPos(posPartenza).pezzo_bianco){
            limite = 8;
            rigaInizio = 2;
            movim = 1;
        }else{
            limite = 1;
            rigaInizio = 7;
            movim = -1;
        }

        if(riga != limite) {
            //prima mossa
            if (riga == rigaInizio) {

                r += movim;
                nuovaPos = (r - 1) * 8 + colonna;
                if(getCasellaInPos(nuovaPos).codPezzoSopra == -1) {
                    r += movim;
                    nuovaPos = (r - 1) * 8 + colonna;
                    if (getCasellaInPos(nuovaPos).codPezzoSopra == -1) {
                        getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);

                        if(getCasellaInPos(nuovaPos).codPezzoSopra == -1) {
                            mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                            boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                            mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));
                            if (ceScacco) {
                                getCasellaInPos(nuovaPos).rimuoviMossa();
                            }else{
                                almenoUnaMossaDisp = true;
                            }
                        }
                    }
                }
            }
            r = riga + movim;
            nuovaPos = (r-1)*8 +colonna;
            if(getCasellaInPos(nuovaPos).codPezzoSopra == -1) {
                getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);

                if(getCasellaInPos(nuovaPos).codPezzoSopra == -1) {
                    mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                    boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                    mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));
                    if (ceScacco) {
                        getCasellaInPos(nuovaPos).rimuoviMossa();
                    }else{
                        almenoUnaMossaDisp = true;
                    }
                }
            }

            //----- mosse per mangiare -----
            col++;
            if(col <= 8) {
                nuovaPos = (r - 1) * 8 + col;
                if (getCasellaInPos(nuovaPos).codPezzoSopra != -1) {
                    getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);

                    int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
                    boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

                    mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                    boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                    mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

                    mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
                    if (ceScacco) {
                        getCasellaInPos(nuovaPos).rimuoviMossa();
                    }else{
                        almenoUnaMossaDisp = true;
                    }
                }
            }

            col = colonna;
            col--;
            if(col > 0) {
                nuovaPos = (r - 1) * 8 + col;
                if (getCasellaInPos(nuovaPos).codPezzoSopra != -1) {
                    getCasellaInPos(nuovaPos).mostraMossa(col_pezzo_siMuove);

                    int pezzoTolto = getCasellaInPos(nuovaPos).codPezzoSopra;
                    boolean pezzoToltoBianco = getCasellaInPos(nuovaPos).pezzo_bianco;

                    mettiPezzoInPosizione(getCasellaInPos(posPartenza), getCasellaInPos(nuovaPos));
                    boolean ceScacco = controllaSeCeScacco(col_pezzo_siMuove);
                    mettiPezzoInPosizione(getCasellaInPos(nuovaPos), getCasellaInPos(posPartenza));

                    mettiPezzoInPosizione(pezzoTolto, pezzoToltoBianco, getCasellaInPos(nuovaPos));
                    if (ceScacco) {
                        getCasellaInPos(nuovaPos).rimuoviMossa();
                    }else{
                        almenoUnaMossaDisp = true;
                    }
                }
            }
        }


        //EN PASSANT: Questa mossa (pedone mangia pedone alla sua sx / dx)
        // si può fare quando il pedone si trova a fianco di un pedone che
        // si è mosso di DUE

        //Primo. Controllo se a sx / dx ho un pedone dell'altro colore
        r = riga;
        col = colonna;
        nuovaPos = (r-1)*8 +col;
        if((getCasellaInPos(nuovaPos-1).codPezzoSopra == 6
                && getCasellaInPos(nuovaPos-1).pezzo_bianco != col_pezzo_siMuove)){

            //Controllo se in quella posizione ce un pedone che ha fatto una mossa da DUE
            // (controllo se la posizione ce nell'array)
            boolean enPassant = possibiliEnPassant.contains((nuovaPos-1));
            if(enPassant) {
                col--;
                if (col_pezzo_siMuove) { r++;
                } else { r--; }
                nuovaPos = (r - 1) * 8 + col;
                if (getCasellaInPos(nuovaPos).codPezzoSopra == -1) {
                    getCasellaInPos(nuovaPos).mossaMangia = true;
                }
            }
        }
        r = riga;
        col = colonna;
        nuovaPos = (r-1)*8 +col;
        if(getCasellaInPos(nuovaPos+1).codPezzoSopra == 6
                && getCasellaInPos(nuovaPos+1).pezzo_bianco != col_pezzo_siMuove){

            //Controllo se in quella posizione ce un pedone che ha fatto una mossa da DUE
            // (controllo se la posizione ce nell'array)
            boolean enPassant = possibiliEnPassant.contains((nuovaPos+1));
            if(enPassant) {
                col++;
                if (col_pezzo_siMuove) { r++;
                } else { r--; }
                nuovaPos = (r - 1) * 8 + col;
                if (getCasellaInPos(nuovaPos).codPezzoSopra == -1) {
                    getCasellaInPos(nuovaPos).mossaMangia = true;
                }
            }
        }

        return almenoUnaMossaDisp;
    }

    public boolean scaccoInPos(int rigaRe, int colonnaRe, boolean turno_bianchi){
        //Basta uno scacco e la mossa non è disponibile. Inoltre ne basta
        // uno solo anche xke quando sposti un tuo pezzo non riusciresti a mettere
        // il re sotto scacco da due parti

        int posScacco;

        posScacco = controlloScaccoAltoSX(rigaRe, colonnaRe, !turno_bianchi);
        if(posScacco == -1) {
            posScacco = controlloScaccoAltoSU(rigaRe, colonnaRe, !turno_bianchi);
        }
        if(posScacco == -1) {
            posScacco = controlloScaccoAltoDX(rigaRe, colonnaRe, !turno_bianchi);
        }
        if(posScacco == -1) {
            posScacco = controlloScaccoDX(rigaRe, colonnaRe, !turno_bianchi);
        }
        if(posScacco == -1) {
            posScacco = controlloScaccoBassoDX(rigaRe, colonnaRe, !turno_bianchi);
        }
        if(posScacco == -1) {
            posScacco = controlloScaccoBassoGIU(rigaRe, colonnaRe, !turno_bianchi);
        }
        if(posScacco == -1) {
            posScacco = controlloScaccoBassoSX(rigaRe, colonnaRe, !turno_bianchi);
        }
        if(posScacco == -1) {
            posScacco = controlloScaccoSX(rigaRe, colonnaRe, !turno_bianchi);
        }
        if(posScacco == -1) {
            posScacco = controlloScaccoCavallo(rigaRe, colonnaRe, !turno_bianchi)[0];
        }

        if(posScacco != -1){
            pos_pezzo_scacco = posScacco;
        }

        return posScacco == -1;
    }

    public int controlloScaccoAltoSX(int rigaRe, int colonnaRe, boolean forseScaccoDelBianco){
        //Controllo se nella diagonale del re c'è un alfiere o una regina

        int r = rigaRe;
        int col = colonnaRe;
        boolean flagPezzoSopra = false, flagPrimoGiro = true, flagScacco = false;
        int nuovaPos = -1, codPezzoScacco;

        while (col > 0 && col <= 8 && r > 0 && r <= 8 && !flagPezzoSopra) {
            if(forseScaccoDelBianco) {
                r--;
                col++;
            }else{
                r++;
                col--;
            }
            if (col > 0 && col <= 8 && r > 0 && r <= 8) {
                nuovaPos = (r - 1) * 8 + col;
                codPezzoScacco = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean colPezzo = getCasellaInPos(nuovaPos).pezzo_bianco;

                if(codPezzoScacco != -1){
                    flagPezzoSopra = true;

                    if (colPezzo == forseScaccoDelBianco) {
                        if(flagPrimoGiro && codPezzoScacco == 6){
                            flagScacco = true;
                        }else if (codPezzoScacco == 3 || codPezzoScacco == 4) {
                            flagScacco = true;
                        }
                    }
                }
            }

            flagPrimoGiro = false;
        }
        if(!flagScacco){ nuovaPos = -1; }

        return nuovaPos;
    }

    public int controlloScaccoAltoDX(int rigaRe, int colonnaRe, boolean forseScaccoDelBianco){
        //Controllo se nella diagonale del re c'è un alfiere o una regina

        int r = rigaRe;
        int col = colonnaRe;
        boolean flagPezzoSopra = false, flagPrimoGiro = true, flagScacco = false;
        int nuovaPos = -1, codPezzoScacco;

        while(col > 0 && col <= 8 && r > 0 && r <= 8 && !flagPezzoSopra){
            if(forseScaccoDelBianco) {
                r--;
                col--;
            }else{
                r++;
                col++;
            }
            if(col > 0 && col <= 8 && r > 0 && r <= 8){
                nuovaPos = (r-1)*8 + col;
                codPezzoScacco = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean colPezzo = getCasellaInPos(nuovaPos).pezzo_bianco;

                if(codPezzoScacco != -1){
                    flagPezzoSopra = true;

                    if (colPezzo == forseScaccoDelBianco) {
                        if(flagPrimoGiro && codPezzoScacco == 6){
                            flagScacco = true;
                        }else if (codPezzoScacco == 3 || codPezzoScacco == 4) {
                            flagScacco = true;
                        }
                    }
                }
            }
            flagPrimoGiro = false;
        }
        if(!flagScacco){ nuovaPos = -1; }

        return nuovaPos;
    }

    public int controlloScaccoBassoSX(int rigaRe, int colonnaRe, boolean forseScaccoDelBianco){
        //Controllo se nella diagonale del re c'è un alfiere o una regina

        int r = rigaRe;
        int col = colonnaRe;
        boolean flagPezzoSopra = false, flagScacco = false;
        int nuovaPos = -1, codPezzoScacco;

        while (col > 0 && col <= 8 && r > 0 && r <= 8 && !flagPezzoSopra) {
            if(forseScaccoDelBianco) {
                r++;
                col++;
            }else{
                r--;
                col--;
            }
            if (col > 0 && col <= 8 && r > 0 && r <= 8) {
                nuovaPos = (r - 1) * 8 + col;
                codPezzoScacco = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean colPezzo = getCasellaInPos(nuovaPos).pezzo_bianco;

                if(codPezzoScacco != -1){
                    flagPezzoSopra = true;

                    if (colPezzo == forseScaccoDelBianco) {
                        if (codPezzoScacco == 3 || codPezzoScacco == 4) {
                            flagScacco = true;
                        }
                    }
                }
            }
        }
        if(!flagScacco){ nuovaPos = -1; }

        return nuovaPos;
    }

    public int controlloScaccoBassoDX(int rigaRe, int colonnaRe, boolean forseScaccoDelBianco){
        //Controllo se nella diagonale del re c'è un alfiere o una regina

        int r = rigaRe;
        int col = colonnaRe;
        boolean flagPezzoSopra = false, flagScacco = false;
        int nuovaPos = -1, codPezzoScacco;

        while (col > 0 && col <= 8 && r > 0 && r <= 8 && !flagPezzoSopra) {
            if(forseScaccoDelBianco) {
                r++;
                col--;
            }else{
                r--;
                col++;
            }

            if (col > 0 && col <= 8 && r > 0 && r <= 8) {
                nuovaPos = (r - 1) * 8 + col;
                codPezzoScacco = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean colPezzo = getCasellaInPos(nuovaPos).pezzo_bianco;

                if(codPezzoScacco != -1){
                    flagPezzoSopra = true;

                    if (colPezzo == forseScaccoDelBianco) {
                        if (codPezzoScacco == 3 || codPezzoScacco == 4) {
                            flagScacco = true;
                        }
                    }
                }
            }
        }
        if(!flagScacco){ nuovaPos = -1; }

        return nuovaPos;
    }

    public int controlloScaccoAltoSU(int rigaRe, int colonnaRe, boolean forseScaccoDelBianco){
        //Controllo se sopra al re c'è una torre o una regina

        int r = rigaRe;
        boolean flagPezzoSopra = false, flagScacco = false;
        int nuovaPos = -1, codPezzoScacco;

        while (r > 0 && r <= 8 && !flagPezzoSopra) {
            if(forseScaccoDelBianco) { r--;
            }else{ r++; }
            if (r > 0 && r <= 8) {
                nuovaPos = (r - 1) * 8 + colonnaRe;
                codPezzoScacco = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean colPezzo = getCasellaInPos(nuovaPos).pezzo_bianco;

                if(codPezzoScacco != -1){
                    flagPezzoSopra = true;

                    if (colPezzo == forseScaccoDelBianco) {
                        if (codPezzoScacco == 1 || codPezzoScacco == 4) {
                            flagScacco = true;
                        }
                    }
                }
            }
        }
        if(!flagScacco){ nuovaPos = -1; }

        return nuovaPos;
    }

    public int controlloScaccoBassoGIU(int rigaRe, int colonnaRe, boolean forseScaccoDelBianco){
        //Controllo se sopra al re c'è una torre o una regina

        int r = rigaRe;
        boolean flagPezzoSopra = false, flagScacco = false;
        int nuovaPos = -1, codPezzoScacco;

        while (r > 0 && r <= 8 && !flagPezzoSopra) {
            if(forseScaccoDelBianco) {
                r++;
            }else{
                r--;
            }
            if (r > 0 && r <= 8) {
                nuovaPos = (r - 1) * 8 + colonnaRe;
                codPezzoScacco = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean colPezzo = getCasellaInPos(nuovaPos).pezzo_bianco;

                if (codPezzoScacco != -1) {
                    flagPezzoSopra = true;

                    if (colPezzo == forseScaccoDelBianco) {
                        if (codPezzoScacco == 1 || codPezzoScacco == 4) {
                            flagScacco = true;
                        }
                    }
                }
            }
        }
        if(!flagScacco){ nuovaPos = -1; }

        return nuovaPos;
    }

    public int controlloScaccoSX(int rigaRe, int colonnaRe, boolean forseScaccoDelBianco){
        //Controllo se a sinistra del re c'è una torre o una regina

        int col = colonnaRe;
        boolean flagPezzoSopra = false, flagScacco = false;
        int nuovaPos = -1, codPezzoScacco;

        while (col > 0 && col <= 8 && !flagPezzoSopra) {
            if(forseScaccoDelBianco) {
                col++;
            }else{
                col--;
            }
            if (col > 0 && col <= 8) {
                nuovaPos = (rigaRe - 1) * 8 + col;
                codPezzoScacco = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean colPezzo = getCasellaInPos(nuovaPos).pezzo_bianco;

                if (codPezzoScacco != -1) {
                    flagPezzoSopra = true;

                    if (colPezzo == forseScaccoDelBianco) {
                        if (codPezzoScacco == 1 || codPezzoScacco == 4) {
                            flagScacco = true;
                        }
                    }
                }
            }
        }
        if(!flagScacco){ nuovaPos = -1; }

        return nuovaPos;
    }

    public int controlloScaccoDX(int rigaRe, int colonnaRe, boolean forseScaccoDelBianco){
        //Controllo se a destra del re c'è una torre o una regina

        int col = colonnaRe;
        boolean flagPezzoSopra = false, flagScacco = false;
        int nuovaPos = -1, codPezzoScacco;


        while (col > 0 && col <= 8 && !flagPezzoSopra) {
            if(forseScaccoDelBianco) {
                col--;
            }else{
                col++;
            }
            if (col > 0 && col <= 8) {
                nuovaPos = (rigaRe - 1) * 8 + col;
                codPezzoScacco = getCasellaInPos(nuovaPos).codPezzoSopra;
                boolean colPezzo = getCasellaInPos(nuovaPos).pezzo_bianco;

                if (codPezzoScacco != -1) {
                    flagPezzoSopra = true;

                    if (colPezzo == forseScaccoDelBianco) {
                        if (codPezzoScacco == 1 || codPezzoScacco == 4) {
                            flagScacco = true;
                        }
                    }
                }
            }
        }
        if(!flagScacco){ nuovaPos = -1; }

        return nuovaPos;
    }

    public int[] controlloScaccoCavallo(int rigaRe, int colonnaRe, boolean forseScaccoDelBianco){
        //Controllo se nelle varie posizioni a L vicino al re c'è un cavallo

        boolean flagScacco = false;
        int nuovaPos = -1, posCavallo = -1;

        //Non posso fare scacco con due cavalli contemporaneamente no ?
        // quindi se trovo che in una posizione ce un cavallo allora è già scacco
        // per questo uso il flagScacco anche in questo metodo

        //----- EMISFERO ALTO -----
        //L bassa verso SX
        if (rigaRe < 8 && colonnaRe > 2) {
            nuovaPos = (rigaRe-1 + 1) * 8 + colonnaRe - 2;

            if(getCasellaInPos(nuovaPos).pezzo_bianco == forseScaccoDelBianco
                    && getCasellaInPos(nuovaPos).codPezzoSopra == 2) {
                flagScacco = true;
                posCavallo = 1;
            }
        }

        //L alta verso SX
        if (!flagScacco && rigaRe < 7 && colonnaRe > 1) {
            nuovaPos = (rigaRe-1 + 2) * 8 + colonnaRe - 1;

            if(getCasellaInPos(nuovaPos).pezzo_bianco == forseScaccoDelBianco
                    && getCasellaInPos(nuovaPos).codPezzoSopra == 2) {
                flagScacco = true;
                posCavallo = 2;
            }
        }

        //L alta verso DX
        if(!flagScacco && rigaRe < 7 && colonnaRe < 8) {
            nuovaPos = (rigaRe-1 + 2) * 8 + colonnaRe + 1;

            if(getCasellaInPos(nuovaPos).pezzo_bianco == forseScaccoDelBianco
                    && getCasellaInPos(nuovaPos).codPezzoSopra == 2) {
                flagScacco = true;
                posCavallo = 3;
            }
        }

        //L bassa verso DX
        if(!flagScacco && rigaRe < 8 && colonnaRe < 7) {
            nuovaPos =(rigaRe-1 + 1)*8+colonnaRe +2;

            if(getCasellaInPos(nuovaPos).pezzo_bianco == forseScaccoDelBianco
                    && getCasellaInPos(nuovaPos).codPezzoSopra == 2) {
                flagScacco = true;
                posCavallo = 4;
            }
        }


        //----- EMISFERO BASSO -----
        //L bassa verso SX
        if(!flagScacco && rigaRe > 1 && colonnaRe > 2) {
            nuovaPos = (rigaRe-1 - 1)*8 + colonnaRe -2;

            if(getCasellaInPos(nuovaPos).pezzo_bianco == forseScaccoDelBianco
                    && getCasellaInPos(nuovaPos).codPezzoSopra == 2) {
                flagScacco = true;
                posCavallo = 5;
            }
        }

        //L alta verso SX
        if(!flagScacco && rigaRe > 2 && colonnaRe > 1) {
            nuovaPos = (rigaRe-1 - 2)*8 + colonnaRe -1;

            if(getCasellaInPos(nuovaPos).pezzo_bianco == forseScaccoDelBianco
                    && getCasellaInPos(nuovaPos).codPezzoSopra == 2) {
                flagScacco = true;
                posCavallo = 6;
            }
        }

        //L alta verso DX
        if(!flagScacco && rigaRe > 2 && colonnaRe < 8) {
            nuovaPos = (rigaRe-1 - 2)*8 + colonnaRe +1;

            if(getCasellaInPos(nuovaPos).pezzo_bianco == forseScaccoDelBianco
                    && getCasellaInPos(nuovaPos).codPezzoSopra == 2) {
                flagScacco = true;
                posCavallo = 7;
            }
        }

        //L bassa verso DX
        if(!flagScacco && rigaRe > 1 && colonnaRe < 7) {
            nuovaPos = (rigaRe-1 - 1)*8 + colonnaRe +2;

            if(getCasellaInPos(nuovaPos).pezzo_bianco == forseScaccoDelBianco
                    && getCasellaInPos(nuovaPos).codPezzoSopra == 2) {
                flagScacco = true;
                posCavallo = 8;
            }
        }
        if(!flagScacco){ nuovaPos = -1; }

        return new int[]{nuovaPos, posCavallo};
    }


    private void mettiPezzoInPosizione(Casella partenza, Casella arrivo){
        //Metto i dati del pezzo alla casella di partenza nella casella di
        // arrivo e rimuovo i dati del pezzo dalla casella di partenza

        arrivo.codPezzoSopra = partenza.codPezzoSopra;
        arrivo.pezzo_bianco = partenza.pezzo_bianco;

        partenza.codPezzoSopra = -1;
        partenza.pezzo_bianco = false;
    }
    private void mettiPezzoInPosizione(int pezPart, boolean pezPartBianco, Casella arrivo){
        //(stessa cosa ma utilizzato quando ho già i dati del pezzo sotto mano
        // e non ho invece la casella di partenza)

        arrivo.codPezzoSopra = pezPart;
        arrivo.pezzo_bianco = pezPartBianco;
    }

    private boolean controllaSeCeScacco(boolean pezzo_mosso_bianco){
        //Metodo che richiamo quando devo vedere se la mossa che voglio segnalre
        // è disponibile, cioè non crei scacco (cioè si può fare)

        //NB: xke !pezzo_mosso_bianco, xke stiamo guardando se spostando quel determianto pezzo
        // (ad es. un pezzo bianco) andiamo a creare scacco al re bianco.
        //Non mi interessa sapere di chi è lo scacco. Mi interessa sapere se creo
        // o meno uno scacco. (uno basta e avanza tra l'altro)

        int posRe;
        if(pezzo_mosso_bianco) { posRe = posRe_bianco;
        }else{ posRe = posRe_nero; }

        int colonna = posRe%8;
        if(colonna == 0){ colonna = 8; }
        int riga = ((posRe - colonna)/8) +1;
        boolean scaccoCreato = false;

        scaccoCreato = controlloScaccoAltoSX(riga, colonna, !pezzo_mosso_bianco) != -1;

        if(!scaccoCreato)
            scaccoCreato = controlloScaccoAltoSU(riga, colonna, !pezzo_mosso_bianco) != -1;

        if(!scaccoCreato)
            scaccoCreato = controlloScaccoAltoDX(riga, colonna, !pezzo_mosso_bianco) != -1;

        if(!scaccoCreato)
            scaccoCreato = controlloScaccoDX(riga, colonna, !pezzo_mosso_bianco) != -1;

        if(!scaccoCreato)
            scaccoCreato = controlloScaccoBassoDX(riga, colonna, !pezzo_mosso_bianco) != -1;

        if(!scaccoCreato)
            scaccoCreato = controlloScaccoBassoGIU(riga, colonna, !pezzo_mosso_bianco) != -1;

        if(!scaccoCreato)
            scaccoCreato = controlloScaccoBassoSX(riga, colonna, !pezzo_mosso_bianco) != -1;

        if(!scaccoCreato)
            scaccoCreato = controlloScaccoSX(riga, colonna, !pezzo_mosso_bianco) != -1;

        if(!scaccoCreato)
            scaccoCreato = controlloScaccoCavallo(riga, colonna, !pezzo_mosso_bianco)[0] != -1;


        return scaccoCreato;
    }
}